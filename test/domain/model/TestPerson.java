package domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPerson {

    @Test
    public void testInitialization() {
	long id = 0;
	String name = "Leonidas";
	Person person = new Person(id, name, 1);
	assertEquals(person.getID(), id);
	assertEquals(person.getName(), name);
	assertNotNull(person.getActivitys());
	assertNotNull(person.getFriends());
	assertNotNull(person.getGoals());
    }

}
