package data.gateways;

import java.util.HashMap;

import data.gateway.interfaces.IPersonGateway;
import data.keys.Key;
import data.keys.PersonKey;
import domain.model.Person;

public class MockPersonGateway extends IPersonGateway {

    private static HashMap<Key<Person>, Person> mockList = new HashMap<Key<Person>, Person>();

    private static long nextID = 4;

    static {
        mockList.put(new PersonKey(0), new Person("Bob", 0L, 0));
        mockList.put(new PersonKey(1), new Person("Kennedy", 38421819L, 1));
        mockList.put(new PersonKey(2), new Person("Casey", 23184891L, 2));
        mockList.put(new PersonKey(3), new Person("Marvin", 83478210L, 3));
    }

    public static long getNextID() {
        return nextID;
    }

    @Override
    public Person find(Key<Person> key) {
        if (key instanceof PersonKey) {
            if (mockList.containsKey(key)) {
                return mockList.get(key);
            }
        }
        return null;
    }

    @Override
    public void update(Person object) {
        object.loaded();
    }

    @Override
    public Key<Person> delete(Person object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<Person> insert(Person object) {
        PersonKey key = new PersonKey(nextID);

        Person generated = new Person(object.getName(), object.getPassword(), nextID);

        nextID++;

        return new Result<Person>(generated, key);
    }
}
