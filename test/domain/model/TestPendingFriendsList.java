package domain.model;

import static org.junit.Assert.*;

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
        newFriend = new Person("dfsadfsad", "Dinosaur", "Tim");
        person = new Person("qwertyyuiop", "Velociraptor", "Frank");
        Session.getUnitOfWork().commit();
        person = (Person) Session.getMapper(Person.class).find(new LoginKey(person.getUserName(), person.getPassword()));
    }

    @Test
    public void testInitialization() {
        assertNotNull(person.getPendingFriends().getAllRequests());
    }

    @Test
    public void testInsertingFriend() {
        person.getPendingFriends().requestFriend(friend);
        assertEquals(person.getPendingFriends().getAllRequests().size(), 1);
    }

    @Test
    public void testDecliningRequest() {
        person.getPendingFriends().requestFriend(friend);
        person.declineFriendRequest(friend);
        assertEquals(person.getFriendList().getFriends().size(), 0);
    }

    @Test
    public void testAcceptingRequest() {
        person.getPendingFriends().requestFriend(friend);
        person.acceptFriendRequest(friend);
        assertEquals(person.getPendingFriends().getAllRequests().size(), 0);
        assertEquals(person.getFriends().get(0).getDisplayName(), "Athena");
    }

    @Test
    public void testAcceptingRequestNonExistant() {
        person.acceptFriendRequest(friend);
        assertEquals(person.getPendingFriends().getAllRequests().size(), 0);
        assertEquals(person.getFriends().size(), 0);
    }

}
