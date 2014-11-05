package domain.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import system.Session;
import system.TestSession;
import commands.CommandToSelectUser;
import data.keys.LoginKey;

public class TestUser {

	@Before
	public void testSession()
	{
		TestSession session = new TestSession();
		Session.replaceSession(session);
	}
	@Test
	public void testInitializationOfUsers() 
	{
		Person user1 = new Person("Pickle Shorts", "pickleshorts", "Pickle Shorts");
		assertEquals("Pickle Shorts",  user1.getUserName());
		assertEquals("Pickle Shorts", user1.getDisplayName());
		
		Person user2 = new Person("Snapple", "madefromthebest", "Snapple");
		assertEquals("Snapple",  user2.getUserName());
		assertEquals("Snapple", user2.getDisplayName());
		
		Person user3 = new Person("Nicholas", "nhydock", "Hydock");
		assertEquals("Nicholas", user3.getUserName());
		assertEquals("Hydock", user3.getDisplayName());

		Session.getUnitOfWork().commit();
		
		LoginKey user1Key = new LoginKey(user1.getUserName(), user1.getPassword());
        Person load1 = (Person) Session.getMapper(Person.class).find(user1Key);
        assertEquals(user1.getUserName(), load1.getUserName());
        assertTrue(load1.getID() != -1);
        
        LoginKey user2Key = new LoginKey(user2.getUserName(), user2.getPassword());
        Person load2 = (Person) Session.getMapper(Person.class).find(user2Key);
        assertEquals(user2.getUserName(), load2.getUserName());
        assertTrue(load2.getID() != -1);
        
        LoginKey user3Key = new LoginKey(user3.getUserName(), user3.getPassword());
        Person load3 = (Person) Session.getMapper(Person.class).find(user3Key);
        assertEquals(user3.getUserName(), load3.getUserName());
        assertTrue(load3.getID() != -1);
		
	}

}
