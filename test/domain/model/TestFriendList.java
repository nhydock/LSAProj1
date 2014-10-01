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
        friends = new FriendList(1);
        friend = new Friend(123, "Jesus", 1);
        newFriend = new Friend(1234, "Zeus", 1);

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
        assertEquals(friends.getFriends().get(0).getID(), 1234);

    }
}
