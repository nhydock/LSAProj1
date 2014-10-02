package domain.model;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

public class TestFriendList {

    private Friend friend;
    private FriendList friends;
    private Friend newFriend;

    @Before
    public void setup() {
        friends = new FriendList();
        friend = new Friend("Username1", "Jesus");
        newFriend = new Friend("Username123456", "Zeus");

    }

    @Test
    public void testInitialization() {
        assertNotNull(friends.getFriends());
    }

    @Test
    public void testInsertingFriend() {
        friends.insert(friend);
        assertEquals(friends.getFriends().size(), 1);
    }

    @Test
    public void testDeletingFriend() {
        friends.insert(friend);
        friends.delete(0);
        assertEquals(friends.getFriends().size(), 0);
    }

    @Test
    public void testUpdatingFriend() {
        friends.insert(friend);
        friends.update(newFriend, 0);
        assertEquals(friends.getFriends().get(0).getUserName(), "Username123456");

    }
}
