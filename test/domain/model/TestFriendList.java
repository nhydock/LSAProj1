package domain.model;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class TestFriendList {

    private Friend friend;
    private RealFriendList friends;

    @Before
    public void setup() {
        friends = new RealFriendList(1, new HashSet<User>());
        friend = new Friend("YoYo", "Jesus", 12345); 
    }

    @Test
    public void testInitialization() {
        assertNotNull(friends.getFriends());
    }

    @Test
    public void testInsertingFriend() {
        friends.insertFriend(friend);
        assertEquals(friends.getFriends().size(), 1);
    }

    @Test
    public void testRemovingFriend() {
        friends.insertFriend(friend);
        friends.removeFriend(friend);
        assertEquals(friends.getFriends().size(), 0);
        friends.removeFriend(friend);
        assertEquals(friends.getFriends().size(), 0);
        
        assertTrue(friends.getRemovedFriends().contains(friend));
    }
    
    @Test
    public void testGetID()
    {
        assertEquals(friends.getUserID(), 1);
    }
    
}
