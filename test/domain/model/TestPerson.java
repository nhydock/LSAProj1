package domain.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.containers.PersonData;

import system.Session;
import system.TestSession;

public class TestPerson {
	
	PersonData dataA = new PersonData(0, "Leonidas", "Leo", "mypwd");
	PersonData dataB = new PersonData(1, "Jimmy", "Jimbo", "alsomypwd");

	@Before
	public void testSession()
	{
		TestSession session = new TestSession();
		Session.replaceSession(session);
	}
	
    @Test
    public void testInitialization() {
        Person person = new Person(dataA.name, dataA.password, dataA.displayName, dataA.id);
        Person person2 = new Person(dataB.name, dataB.password, dataB.displayName, dataB.id);
        assertEquals(person.getID(), dataA.id);
        assertEquals(person.getUserName(), dataA.name);
        assertEquals(person.getPassword(), dataA.password);
        assertEquals(person2.getDisplayName(), dataB.displayName);
        assertNotNull(person.getFriendList());
    }

    @Test
    public void testChangePassword() {
        Person person = new Person(dataA.name, dataA.password, dataA.displayName, dataA.id);
        
        String newpass = "qwerty";

        assertEquals(person.getPassword(), dataA.password);
        person.setPassword(newpass);
        assertEquals(person.getPassword(), newpass);
    }
    
    @Test
    public void testRollback() {
    	Person person = new Person(dataA.name, dataA.password, dataA.displayName, dataA.id);
        
        Session.getUnitOfWork().commit();
        
        assertEquals(person.getPassword(), dataA.password);
        String newpass = "qwerty";
        person.setPassword(newpass);
        assertEquals(person.getPassword(), newpass);
    	
        Session.getUnitOfWork().rollback();
        
        assertEquals(person.getPassword(), dataA.password);
    }

    @Test
    public void testAcceptFriend() {
    	Person person = new Person(dataA.name, dataA.password, dataA.displayName, dataA.id);
        Person person2 = new Person(dataB.name, dataB.password, dataB.displayName, dataB.id);
        
        assertEquals(0, person.getFriendList().getFriends().size());
        assertEquals(0, person.getPendingFriends().getAllRequests().size());
        
        person.requestFriend(person2);
        
        assertEquals(0, person.getFriendList().getFriends().size());
        assertEquals(1, person.getPendingFriends().getAllRequests().size());
        
        person.acceptFriendRequest(person2);
        assertEquals(0, person.getPendingFriends().getAllRequests().size());
        assertEquals(1, person.getFriendList().getFriends().size());
        
    }
    
    @Test
    public void testRemoveFriend() {
    	fail("Not yet tested");
    }
    
    @Test
    public void testRequestFriend() {
    	fail("Not yet tested");
    }
    
    @Test
    public void testDenyFriendRequest() {
    	fail("Not yet tested");
    }
}
