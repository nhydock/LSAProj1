package data.gateways;

import java.util.HashMap;

import data.gateway.interfaces.IUserGateway;
import data.keys.FriendKey;
import data.keys.Key;
import data.keys.PersonKey;
import domain.model.DomainModelObject;
import domain.model.Friend;
import domain.model.Person;
import domain.model.User;

public class MockUserGateway extends IUserGateway {

    private static HashMap<Key<User>, User> mockList = new HashMap<Key<User>, User>();

    private static long nextID = 4;

    static {
        mockList.put(new PersonKey(0), new Person("Bob", 0L, 0));
        mockList.put(new PersonKey(1), new Person("Kennedy", 38421819L, 1));
        mockList.put(new PersonKey(2), new Person("Casey", 23184891L, 2));
        mockList.put(new PersonKey(3), new Person("Marvin", 83478210L, 3));
        mockList.put(new FriendKey("Bob"), new Friend("Bob", "Bob", 0));
    }

    public static long getNextID() {
        return nextID;
    }

    @Override
    public DomainModelObject find(Key<?> key) {
        if ((key instanceof PersonKey) || (key instanceof FriendKey)) {
            if (mockList.containsKey(key)) {
                return mockList.get(key);
            }
        }
        return null;
    }

    @Override
    public void update(DomainModelObject object) {
        return;
    }

    @Override
    public Key<Person> delete(DomainModelObject object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<?> insert(DomainModelObject dmo) {
        if (!(dmo instanceof Person))
        {
            return null;
        }
        
        Person object = (Person)dmo;
        PersonKey key = new PersonKey(nextID);

        Person generated = new Person(object.getUserName(), object.getPassword(),
                nextID);

        nextID++;

        return new Result<User>(generated, key);
    }

    @Override
    public Class<User> getType() {
        return User.class;
    }
}
