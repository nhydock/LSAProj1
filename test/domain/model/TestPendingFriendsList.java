package domain.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestPendingFriendsList {

    private Person person;
    private Friend friend;
    private Friend newFriend;

    @Before
    public void setup() {
        friend = new Friend("asd", "Athena", 2);
        newFriend = new Friend("dfsadfsad", "Dinosaur", 3);
        person = new Person("qwertyyuiop", "Velociraptor", "Frank");
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
