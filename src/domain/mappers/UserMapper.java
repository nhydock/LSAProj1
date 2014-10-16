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

    public void update(User[] user) {
        if (user instanceof Person[]) {
            DataContainer[] data = new PersonData[user.length];
            for (int i = 0; i < user.length; i++)
            {
                Person person = (Person)user[i];
                data[i] = new PersonData(-1, person.getUserName(), person.getDisplayName(), person.getPassword());
            }
            Session.getGateway(UserGateway.class).update(data);
        }
    }

    public void insert(User[] user) {
        if (user instanceof Person[]) {
            DataContainer[] data = new PersonData[user.length];
            for (int i = 0; i < user.length; i++)
            {
                Person person = (Person)user[i];
                data[i] = new PersonData(-1, person.getUserName(), person.getDisplayName(), person.getPassword());
            }
            Session.getGateway(UserGateway.class).insert(data);
        }
    }

    @Override
    public void delete(User[] user) {
        if (user instanceof Person[]) {
            Key[] keys = new PersonKey[user.length];
            for (int i = 0; i < user.length; i++)
            {
                User u = user[i];
                keys[i] = new PersonKey(u.getID());
            }
            Session.getGateway(UserGateway.class).delete(keys);
        }
    }

    @Override
    public Class<User> getType() {
        return User.class;
    }
}
