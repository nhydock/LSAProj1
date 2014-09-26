package domain.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestFriendList
{
	
	private FriendList friendList;
	private Friend friend;
	private Friend newFriend;
	
	@Before
	public void setup() {
		friendList = new FriendList();
		friend = new Friend(0, "Frankenstein");
		newFriend = new Friend(1, "Dr. Frankenstein");
	}

	@Test
	public void testInitialization() {
		assertNotNull(friendList.getFriends());
	}
	
	@Test
	public void addFriend() {
		friendList.insert(friend);
		assertEquals(friendList.getFriends().get(0), friend);
	}
	
	@Test 
	public void deleteFriend() {
		friendList.getFriends().add(friend);
		friendList.delete(0);
		assertEquals(friendList.getFriends().size(), 0);
	}
	
	@Test
	public void updateFriend() {
		friendList.getFriends().add(friend);
		friendList.update(newFriend, 0);
		assertEquals(friendList.getFriends().get(0), newFriend);
	}

}
