package domain.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.containers.PersonData;
import domain.model.proxies.FriendListProxy;
import domain.model.proxies.PendingFriendsListProxy;
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
        Session.getUnitOfWork().commit();
        
        Person person2 = new Person(dataB.name, dataB.password, dataB.displayName, dataB.id);
        Session.getUnitOfWork().commit();
        
        assertEquals(person.getID(), dataA.id);
        assertEquals(person.getUserName(), dataA.name);
        assertEquals(person.getPassword(), dataA.password);
        assertEquals(person2.getDisplayName(), dataB.displayName);
        assertNotNull(person.getFriendList());
    }
    
    @Test
    public void testInitialization2()
    {
        Person person = new Person(dataA.name, dataA.password, dataA.displayName);
        Person person2 = new Person(dataB.name, dataB.password, dataB.displayName);
        assertEquals(person.getID(), -1);
        assertEquals(person.getUserName(), dataA.name);
        assertEquals(person.getPassword(), dataA.password);
        assertEquals(person2.getDisplayName(), dataB.displayName);
        assertNull(person.getFriendList());
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
                
        person.requestFriend(person2);
        Session.getUnitOfWork().commit();

        ((PendingFriendsListProxy)person2.getPendingFriends()).unload();
        
        assertEquals(1, person2.getPendingFriends().getAllRequests().size());
        person2.acceptFriendRequest(person);
        assertEquals(0, person2.getPendingFriends().getAllRequests().size());
        Session.getUnitOfWork().commit();
        
        
        ((FriendListProxy)person.getFriendList()).unload();
        
        assertEquals(1, person.getFriendList().getFriends().size()); 
    }
    
    @Test 
    public void testRemoveFriend() {
        Person person = new Person(dataA.name, dataA.password, dataA.displayName, dataA.id);
        Person person2 = new Person(dataB.name, dataB.password, dataB.displayName, dataB.id);
        
        person.requestFriend(person2);
        Session.getUnitOfWork().commit();
        
        ((PendingFriendsListProxy)person2.getPendingFriends()).unload();
        
        person2.acceptFriendRequest(person);
        Session.getUnitOfWork().commit();
        
        ((FriendListProxy)person.getFriendList()).unload(); 
        
        person.removeFriend(person2);
        Session.getUnitOfWork().commit();
       
        ((FriendListProxy)person2.getFriendList()).unload();
        
        
        assertEquals(0, person.getFriendList().getFriends().size());
        assertEquals(0, person2.getFriendList().getFriends().size());
    }
    
    @Test
    public void testRequestFriend() {
        Person person = new Person(dataA.name, dataA.password, dataA.displayName, dataA.id);
        Person person2 = new Person(dataB.name, dataB.password, dataB.displayName, dataB.id);
        
        assertEquals(0, person.getFriendList().getFriends().size());
        assertEquals(0, person.getPendingFriends().getAllRequests().size());
                
        person.requestFriend(person2);
        Session.getUnitOfWork().commit();
        
        ((FriendListProxy)person.getFriendList()).unload(); 
        ((PendingFriendsListProxy)person2.getPendingFriends()).unload();
        
        
        assertEquals(0, person.getFriendList().getFriends().size());
        assertEquals(1, person.getPendingFriends().getAllRequests().size());
        
        assertEquals(0, person2.getFriendList().getFriends().size());
        assertEquals(1, person2.getPendingFriends().getAllRequests().size());
    }
    
    @Test
    public void testDeclineFriendRequest() {
        Person person = new Person(dataA.name, dataA.password, dataA.displayName, dataA.id);
        Person person2 = new Person(dataB.name, dataB.password, dataB.displayName, dataB.id);
                
        person.requestFriend(person2);
        Session.getUnitOfWork().commit();
        person2.declineFriendRequest(person);
        Session.getUnitOfWork().commit();
        
        ((FriendListProxy)person.getFriendList()).unload(); 
        ((PendingFriendsListProxy)person.getPendingFriends()).unload();
        
        assertEquals(0, person.getFriendList().getFriends().size());
        assertEquals(0, person.getPendingFriends().getAllRequests().size());
        
        assertEquals(0, person2.getFriendList().getFriends().size());
        assertEquals(0, person2.getPendingFriends().getAllRequests().size());
    }
    
    @Test
    public void testSetDisplayName()
    {
        Person person = new Person(dataA.name, dataA.password, dataA.displayName, dataA.id);
        assertEquals(person.getDisplayName(), dataA.displayName);
        
        String newName = "New name";
        
        person.setDisplayName(newName);
        assertEquals(person.getDisplayName(), newName);
    }
    
    @Test
    public void testGetFriendsList()
    {
        Person person = new Person(dataA.name, dataA.password, dataA.displayName, dataA.id);
        Person person2 = new Person(dataB.name, dataB.password, dataB.displayName, dataB.id);
        
        assertEquals(person.getFriends().size(), 0);
        assertEquals(person.getFriendList().getFriends().size(), 0);
        
        person.requestFriend(person2);
        Session.getUnitOfWork().commit();
        person2.acceptFriendRequest(person);
        Session.getUnitOfWork().commit();
        
        ((FriendListProxy)person.getFriendList()).unload(); 
        
        assertEquals(person.getFriends().size(), 1);
        assertEquals(person.getFriendList().getFriends().size(), 1);
    }
    
    @Test
    public void testToString()
    {
        Person person = new Person(dataA.name, dataA.password, dataA.displayName, dataA.id);
        
        assertEquals(person.toString(), dataA.name + ":" + dataA.password + ":" + dataA.displayName);
    }
}
