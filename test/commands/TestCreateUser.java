package commands;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.model.Person;

public class TestCreateUser {

    @Test
    public void test() {
        String username = "Dam";
        String password = "it";
        String displayName = "Bobby";
        CommandToCreateUser comm = new CommandToCreateUser(username, password, displayName);
        comm.execute();
        Person p = comm.getResult();
        
        assertNotNull(p);
        assertEquals(p.getDisplayName(), displayName);
        assertEquals(p.getUserName(), username);
        assertEquals(p.getPassword(), password);
    }

}
