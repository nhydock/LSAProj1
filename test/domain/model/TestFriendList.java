package domain.model;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestFriendList {

    private Friend friend;
    private RealFriendList friends;
    private Friend newFriend;

    @Before
    public void setup() {
        friends = new RealFriendList(1, new ArrayList<User>());
        friend = new Friend("YoYo", "Jesus", 12345);
        newFriend = new Friend("Ian", "Zeus", 1234);
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
    public void testDeletingFriend() {
        friends.insertFriend(friend);
        friends.removeFriend(friend);
        assertEquals(friends.getFriends().size(), 0);
        friends.removeFriend(friend);
        assertEquals(friends.getFriends().size(), 0);
    }
    
}
