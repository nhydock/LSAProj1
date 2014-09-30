package domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestFriend {

    @Test
    public void testInitialization() {
	Friend friend = new Friend(1, "Frank", 1);
	assertEquals(friend.getID(), 1);
	assertEquals(friend.getName(), "Frank");
    }

}
