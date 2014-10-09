package data.gateway;

import java.security.InvalidParameterException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import system.Session;
import data.containers.DataContainer;
import data.containers.PersonData;
import data.gateway.interfaces.IUserGateway;
import data.keys.FriendKey;
import data.keys.Key;
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
            result.next();
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
            String sql = "SELECT * FROM friend_map f JOIN persons p ON f.fid = p.id WHERE p.name = ?";
            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
            stmt.setString(1, key.name);
            ResultSet result = stmt.executeQuery();
            result.next();

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
    public void update(DataContainer data) {
        if (!(data instanceof PersonData))
        {
            return;
        }
        
        PersonData object = (PersonData)data;
        
        try {
            String sql = "UPDATE persons SET name=?,password=? WHERE id=?";
            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
            stmt.setString(1, object.name);
            stmt.setLong(2, object.password);
            stmt.setLong(3, object.id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Inserst a new person into the database
     */
    @Override
    public ResultSet insert(DataContainer data) {
        if (!(data instanceof PersonData))
        {
            return null;
        }

        PersonData object = (PersonData)data;
        
        try {
            String sql = "INSERT INTO persons (name, password) VALUES (?, ?)";
            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
            stmt.setString(1, object.name);
            stmt.setLong(2, object.password);

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
    public boolean delete(Key key) {
        if (!(key instanceof PersonKey))
        {
            throw (new InvalidParameterException("You can only delete users by using Person Keys"));
        }
        
        PersonKey person = (PersonKey)key;
        
        try {
            String sql = "DELETE FROM persons WHERE id=?";
            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
            stmt.setLong(1, person.id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return false;
    }

}
