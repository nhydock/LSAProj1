package domain.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import system.Session;
import data.containers.DataContainer;
import data.containers.PersonData;
import data.gateway.UserGateway;
import data.keys.FriendKey;
import data.keys.Key;
import data.keys.PersonKey;
import domain.mappers.DataMapper;
import domain.model.Friend;
import domain.model.Person;
import domain.model.User;

public class UserMapper implements DataMapper<User> {
    
    public User find(Key key) {
        if (Session.getIdentityMap(User.class).containsKey(key))
        {
            return Session.getIdentityMap(User.class).get(key);
        }
        
        try {
            if ((key instanceof PersonKey) || (key instanceof FriendKey)) {
                ResultSet result = Session.getGateway(UserGateway.class).find(key);
                User obj = null;
                if (key instanceof PersonKey)
                {
                    obj = new Person(result.getString("name"), result.getLong("password"), result.getLong("id"));
                }
                else
                {
                    obj = new Friend(result.getString("name"), result.getString("name"));
                }
                Session.getIdentityMap(User.class).put(key, obj);
                return obj; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        if (user instanceof Person) {
            Person person = (Person) user;
            DataContainer data = new PersonData(person.getID(), person.getUserName(), person.getDisplayName(), person.getPassword());
            Session.getGateway(UserGateway.class).update(data);
        }
    }

    public void insert(User user) {
        if (user instanceof Person) {
            Person person = (Person) user;
            DataContainer data = new PersonData(-1, person.getUserName(), person.getDisplayName(), person.getPassword());
            Session.getGateway(UserGateway.class).insert(data);
        }
    }

    @Override
    public void delete(User user) {
        if (user instanceof Person) {
            Person person = (Person) user;
            PersonKey key = new PersonKey(person.getID());
            Session.getGateway(UserGateway.class).delete(key);
        }
    }

    @Override
    public Class<User> getType() {
        return User.class;
    }
}
