package mock.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import system.Session;
import data.containers.DataContainer;
import data.containers.PersonData;
import data.gateway.UserGateway;
import data.keys.FriendKey;
import data.keys.Key;
import data.keys.LoginKey;
import data.keys.PersonKey;
import domain.mappers.DataMapper;
import domain.model.Friend;
import domain.model.Person;
import domain.model.User;

public class MockUserMapper implements DataMapper<User> {

	private HashMap<Key, DataContainer> mockData = new HashMap<Key, DataContainer>();
	
	public MockUserMapper()
	{
		
	}
	
	public User find(Key key) {
    	
        if ((key instanceof PersonKey) || (key instanceof FriendKey) || (key instanceof LoginKey)) {
            User obj = null;

            if (key instanceof PersonKey && Session.getIdentityMap(Person.class).containsKey(key))
            {
                return Session.getIdentityMap(Person.class).get(key);
            }
            else if (key instanceof FriendKey && Session.getIdentityMap(Friend.class).containsKey(key))
            {
            	return Session.getIdentityMap(Friend.class).get(key);
            }
            
            DataContainer user = mockData.get(key);
            
            if (user == null)
            	return null;
            
            if (key instanceof PersonKey)
            {
            	PersonData data = (PersonData)user;
                obj = new Person(data.name, data.displayName, data.password, data.id);
                Session.getIdentityMap(Person.class).put(key, obj);
            }
            //make sure person getting mapped in is good to go
            else if (key instanceof LoginKey)
            {
            	PersonData data = (PersonData)user;
            	obj = new Person(data.name, data.displayName, data.password, data.id);
                key = new PersonKey(obj.getID());

                Session.getIdentityMap(Person.class).put(key, obj);
            }
            else
            {
            	PersonData data = (PersonData)user;
            	obj = new Friend(data.name, data.displayName, data.id);
                
                Session.getIdentityMap(Friend.class).put(key, obj);
            }
            return obj;
        }
        return null;
    }

    public void update(User[] user) {
        if (user instanceof Person[]) {
            for (int i = 0; i < user.length; i++)
            {
            	Person person = (Person)user[i];
                PersonData data = new PersonData(person.getID(), person.getUserName(), person.getDisplayName(), person.getPassword());
            
            	PersonData p = data;
            	PersonKey key = new PersonKey(p.id);
            	LoginKey lKey = new LoginKey(p.name, p.password);
            
            	mockData.put(key, p);
            	mockData.put(lKey, p);
            }
        }
    }

    public void insert(User[] user) {
        if (user instanceof Person[]) {
        	for (int i = 0; i < user.length; i++)
            {
            	Person person = (Person)user[i];
                PersonData data = new PersonData(mockData.size()+1, person.getUserName(), person.getDisplayName(), person.getPassword());
            
                Person obj = new Person(data.name, data.password, data.displayName, data.id);
			    
            	PersonKey key = new PersonKey(data.id);
            	LoginKey lKey = new LoginKey(data.name, data.password);
            
            	mockData.put(key, data);
            	mockData.put(lKey, data);
            	
                Session.getIdentityMap(Person.class).put(key, obj);
            }
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
