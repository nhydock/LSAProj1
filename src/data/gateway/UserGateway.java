package data.gateway;

import java.security.InvalidParameterException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import system.Session;
import data.containers.DataContainer;
import data.containers.PersonData;
import data.gateway.interfaces.IUserGateway;
import data.keys.FriendKey;
import data.keys.Key;
import data.keys.LoginKey;
import data.keys.PersonKey;

public class UserGateway extends IUserGateway {

    /**
     * Finds a person using a person key
     * 
     * @param key
     * @return a person, if one was found
     */
    protected ResultSet find(PersonKey key) {
        try {
            String sql = "SELECT * FROM persons p WHERE p.id = ?";
            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
            stmt.setLong(1, key.id);
            ResultSet result = stmt.executeQuery();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    /**
     * Finds a person using a person key
     * 
     * @param key
     * @return a person, if one was found
     */
    protected ResultSet find(FriendKey key) {
        try {
            String sql = "SELECT * FROM persons p WHERE p.name = ?";
            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
            stmt.setString(1, key.name);
            ResultSet result = stmt.executeQuery();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
    
    /**
     * Finds a person using login credentials
     * @param key
     * @return
     */
    protected ResultSet find(LoginKey key) {
        try {
            String sql = "SELECT * FROM persons p WHERE p.name = ? AND p.password = ?";
            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
            stmt.setString(1, key.username);
            stmt.setString(2, key.password);
            ResultSet result = stmt.executeQuery();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
    
    /**
     * Updates the data values of an existing person
     */
    @Override
    public void update(DataContainer[] data) {
        if (!(data instanceof PersonData[]))
        {
            return;
        }
        
        PersonData[] objects = (PersonData[])data;
        
        try {
            for (int i = 0; i < data.length; i++)
            {
                PersonData object = objects[i];
                
                String sql = "UPDATE persons SET name=?,password=? WHERE id=?";
                PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
                stmt.setString(1, object.name);
                stmt.setString(2, object.password);
                stmt.setLong(3, object.id);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Inserst a new person into the database
     */
    @Override
    public ResultSet insert(DataContainer[] data) {
        if (!(data instanceof PersonData[]))
        {
            return null;
        }

        PersonData[] objects = (PersonData[])data;
        try {
            String sql = "INSERT INTO persons (name, password, display_name) VALUES ";
            sql += "(?, ?, ?)";
            for (int i = 1; i < data.length; i++)
            {
                sql += ",(?,?,?)";
            }
            
            PreparedStatement stmt = Session.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0, x = 1; i < data.length; i++, x += 2)
            {
                stmt.setString(x, objects[i].name);
                stmt.setString(x+1, objects[i].password);
                stmt.setString(x+2, objects[i].displayName);
            }
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(
                        "Creating person failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys;
                } else {
                    throw new SQLException(
                            "Creating Person failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }

    /**
     * Deletes a person from the database
     */
    @Override
    public boolean delete(Key[] key) {
        if (!(key instanceof PersonKey[]))
        {
            throw (new InvalidParameterException("You can only delete users by using Person Keys"));
        }
        
        PersonKey[] persons = (PersonKey[])key;
        
        try {
            String sql = "DELETE FROM persons WHERE id IN (?";
            
            for (int i = 1; i < key.length; i++){ sql += ",?"; }
            sql += ")";
            
            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
            for (int i = 0, n = 1; i < persons.length; i++, n++) {
                stmt.setLong(n, persons[i].id);
            }
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return false;
    }

}
