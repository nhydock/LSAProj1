package domain.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.gateways.MockFriendGateway;
import data.gateways.MockRealFriendGateway;
import domain.DataMapper;

public class TestPendingFriendsList {

    private Person person;
    private Friend friend;
    private Friend newFriend;

    @Before
    public void setup() {
        friend = new Friend("asd", "Athena");
        newFriend = new Friend("dfsadfsad", "Dinosaur");
        person = new Person("qwertyyuiop", "Velociraptor", "Frank");

        DataMapper.get().register(RealFriendList.class,
                new MockRealFriendGateway());
    }

    @Test
    public void testInitialization() {
        assertNotNull(person.getPendingFriends().getAsArrayList());
    }

    @Test
    public void testInsertingFriend() {
        person.getPendingFriends().insert(friend);
        assertEquals(person.getPendingFriends().getAsArrayList().size(), 1);
    }

    @Test
    public void testDecliningRequest() {
        person.getPendingFriends().insert(friend);
        person.declineFriendRequest(friend);
        assertEquals(person.getFriendList().getFriends().size(), 0);
    }

    @Test
    public void testAcceptingRequest() {
        person.getPendingFriends().insert(friend);
        person.acceptFriendRequest(friend);
        assertEquals(person.getPendingFriends().getAsArrayList().size(), 0);
        assertEquals(person.getFriends().get(0).getDisplayName(), "Athena");
    }

    @Test
    public void testAcceptingRequestNonExistant() {
        person.acceptFriendRequest(friend);
        assertEquals(person.getPendingFriends().getAsArrayList().size(), 0);
        assertEquals(person.getFriends().size(), 0);
    }

}
