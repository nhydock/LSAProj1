package data.gateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.gateway.interfaces.IUserGateway;
import data.keys.FriendKey;
import data.keys.Key;
import data.keys.PersonKey;
import domain.model.DomainModelObject;
import domain.model.Friend;
import domain.model.Person;
import domain.model.User;

public class UserGateway extends IUserGateway {

    /**
     * Finds a person using a person key
     * 
     * @param key
     * @return a person, if one was found
     */
    private Person find(PersonKey key) {
        try {
            String sql = "SELECT * FROM persons p WHERE p.id = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setLong(1, key.id);
            ResultSet result = stmt.executeQuery();

            Person person = new Person(result.getString("name"),
                    result.getLong("password"), result.getInt("id"));
            return person;
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
    private Friend find(FriendKey key) {
        try {
            String sql = "SELECT * FROM friend_map f JOIN persons p ON f.fid = p.id WHERE p.name = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, key.name);
            ResultSet result = stmt.executeQuery();
            result.next();

            Friend friend = new Friend(result.getString("p.name"),
                    result.getString("p.name"));
            return friend;
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
    public void update(DomainModelObject dmo) {
        if (!(dmo instanceof Person))
        {
            return;
        }
        
        Person object = (Person)dmo;
        
        try {
            String sql = "UPDATE persons SET name=?,password=? WHERE id=?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, object.getUserName());
            stmt.setLong(2, object.getPassword());
            stmt.setLong(3, object.getID());

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
    public Result<?> insert(DomainModelObject dmo) {
        if (!(dmo instanceof Person))
        {
            return null;
        }
        
        Person object = (Person)dmo;
        
        try {
            String sql = "INSERT INTO persons (name, password) VALUES (?, ?)";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, object.getUserName());
            stmt.setLong(2, object.getPassword());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(
                        "Creating person failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);

                    // create a new person with the generated id to be put into
                    // our datamapper
                    Person generated = new Person(object.getUserName(),
                            object.getPassword(), id);
                    // update the object reference to now be the newly generated
                    // one
                    object = generated;

                    PersonKey key = new PersonKey(id);
                    Result<User> result = new Result<User>(generated, key);
                    return result;
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
    public Key<?> delete(DomainModelObject dmo) {
        if (!(dmo instanceof User))
        {
            return null;
        }
        
        User object = (User)dmo;
        
        try {
            String sql = "DELETE FROM persons WHERE id=?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setLong(1, object.getID());
            stmt.executeUpdate();

            PersonKey key = new PersonKey(object.getID());
            return key;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    /**
     * Delegating method for finding a person dependent on the type of key given
     */
    @Override
    public DomainModelObject find(Key<?> key) {
        if (key instanceof PersonKey) {
            return find((PersonKey) key);
        }
        if (key instanceof FriendKey) {
            return find((FriendKey) key);
        }
        return null;
    }

    @Override
    public Class<User> getType() {
        return User.class;
    }
}
