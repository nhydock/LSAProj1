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
        assertEquals(person.getID(), id);
        assertEquals(person.getName(), name);
        assertEquals(person.getPassword(), password);
        
        assertNotNull(person.getFriendList());
    }

}
