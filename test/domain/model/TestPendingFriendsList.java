package domain.model;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import data.keys.LoginKey;
import system.Session;
import system.TestSession;

public class TestPendingFriendsList {

    private Person person;
    private Person friend;
    private Person newFriend;

    @Before
    public void setup() {
    	TestSession session = new TestSession();
		Session.replaceSession(session);
        friend = new Person("asd", "Athena", "Ian");
        Session.getUnitOfWork().commit();
        newFriend = new Person("dfsadfsad", "Dinosaur", "Tim");
        Session.getUnitOfWork().commit();
        person = new Person("qwertyyuiop", "Velociraptor", "Frank");
        Session.getUnitOfWork().commit();
        
        //update people to what's now in the mapper
        person = (Person) Session.getMapper(Person.class).find(new LoginKey(person.getUserName(), person.getPassword()));
        friend = (Person) Session.getMapper(Person.class).find(new LoginKey(friend.getUserName(), friend.getPassword()));
        newFriend = (Person) Session.getMapper(Person.class).find(new LoginKey(newFriend.getUserName(), newFriend.getPassword()));
        
    }

    @Test
    public void testInitialization() {
    	IPendingFriendsList list = person.getPendingFriends();
    	assertNotNull(list);
    	
    	Set<User> requests = list.getAllRequests();
        assertNotNull(requests);
        assertEquals(0, requests.size());
    }

    @Test
    public void testInsertingFriend() {
    	IPendingFriendsList list = person.getPendingFriends();
    	assertEquals(0, list.getAllRequests().size());
        
        list.requestFriend(friend);
        
        assertEquals(1, person.getPendingFriends().getOutgoingRequests().size());
        assertEquals(0, person.getPendingFriends().getIncomingRequests().size());
        
        assertEquals(1, friend.getPendingFriends().getIncomingRequests().size());
        assertEquals(0, friend.getPendingFriends().getOutgoingRequests().size());
    }

    @Test
    public void testDecliningRequest() {
    	IPendingFriendsList list = person.getPendingFriends();
    	IPendingFriendsList flist = friend.getPendingFriends();
    	
    	Set<User> myRequests = list.getAllRequests();
    	Set<User> theirRequests = flist.getAllRequests();
        
    	list.requestFriend(friend);
        assertEquals(1, myRequests.size());
        assertEquals(1, theirRequests.size());
        
        flist.denyFriend(person);
        assertEquals(0, myRequests.size());
        assertEquals(0, theirRequests.size());
    }

    @Test
    public void testRequestingNonExistantUser() {
    	IPendingFriendsList list = person.getPendingFriends();
    	
    	Person newPerson = new Person("bobbo", "test", "Kevin");
    	
    	assertEquals(0, list.getAllRequests().size());
    	
        list.requestFriend(newPerson);
        
        //should deny people with -1 id
        assertEquals(0, list.getAllRequests().size());
        
        list.requestFriend(null);
        
        //should deny null requests
        assertEquals(0, list.getAllRequests().size());
        
        
    }
 
    @Test
    public void testRemove()
    {
        person.requestFriend(friend);
        friend.acceptFriendRequest(person);
        
        assertEquals(person.getFriends().size(), 1);
        assertEquals(friend.getFriends().size(), 1);
        
        person.removeFriend(friend);
        
        assertEquals(person.getFriends().size(), 0);
        assertEquals(friend.getFriends().size(), 0);
    }
}
