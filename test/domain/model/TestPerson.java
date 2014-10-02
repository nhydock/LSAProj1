package domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPerson {

    @Test
    public void testInitialization() {
        long id = 0;
        String name = "Leonidas";
        long password = 12384319L;
        Person person = new Person(name, password, id);
        Person person2 = new Person(name, "pass", "test");
        assertEquals(person.getID(), id);
        assertEquals(person.getName(), name);
        assertEquals(person.getPassword(), password);
        assertEquals(person2.getDisplayName(), "test");
        assertNotNull(person.getFriendList());
    }
    
    @Test
    public void testChangePassword()
    {
	long id = 0;
        String name = "Leonidas";
        long password = 12384319L;
        Person person = new Person(name, password, id);
        String newpass = "qwerty";
        
        person.setPassword(newpass);
        assertEquals(person.getPassword(), newpass.hashCode());
    }

}
