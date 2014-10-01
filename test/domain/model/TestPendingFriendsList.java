package domain.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestPendingFriendsList {

	private Person person;
	private Friend friend;
	private PendingFriendsList pendingFriends;
	private Friend newFriend;

	@Before
	public void setup() {
		pendingFriends = new PendingFriendsList(1);
		friend = new Friend(123, "Athena", 1);
		newFriend = new Friend(1234, "Dinosaur", 1);
		person = new Person(1, "Velociraptor", 1);
	}

	@Test
	public void testInitialization() {
		assertNotNull(pendingFriends.getFriends());
	}

	@Test
	public void testInsertingFriend() {
		pendingFriends.insert(friend);
		assertEquals(pendingFriends.getFriends().size(), 1);
	}

	@Test
	public void testDecliningRequest() {
		pendingFriends.insert(friend);
		pendingFriends.declineFriend(0);
		assertEquals(pendingFriends.getFriends().size(), 0);
	}

	@Test
	public void testUpdatingFriend() {
		pendingFriends.insert(friend);
		pendingFriends.update(newFriend, 0);
		assertEquals(pendingFriends.getFriends().get(0).getID(), 1234);
	}

	@Test
	public void testAcceptingRequest() {
		pendingFriends.insert(friend);
		pendingFriends.acceptFriend(person, 0);
		assertEquals(pendingFriends.getFriends().size(), 0);
		assertEquals(person.getFriends().getFriends().get(0).getName(),
				"Athena");
	}

}
