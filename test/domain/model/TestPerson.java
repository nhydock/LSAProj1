package domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPerson {

    @Test
    public void testInitialization() {
        long id = 0;
        String name = "Leonidas";
        String password = "mypwd";
        String displayName = "Leo";
        Person person = new Person(name, password, displayName, id);
        Person person2 = new Person(name, "pass", "test");
        assertEquals(person.getID(), id);
        assertEquals(person.getUserName(), name);
        assertEquals(person.getPassword(), password);
        assertEquals(person2.getDisplayName(), "test");
        assertNotNull(person.getFriendList());
    }

    @Test
    public void testChangePassword() {
        long id = 0;
        String name = "Leonidas";
        String password = "mypwd";
        String displayName = "Leo";
        Person person = new Person(name, password, displayName, id);
        String newpass = "qwerty";

        person.setPassword(newpass);
        assertEquals(person.getPassword(), newpass.hashCode());
    }
    
    @Test
    public void testRollback() {
        fail("not yet tested");
    }

}
