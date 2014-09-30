package domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

<<<<<<< HEAD
public class TestFriend {

    @Test
    public void testInitialization() {
	Friend friend = new Friend(1, "Frank");
	assertEquals(friend.getID(), 1);
	assertEquals(friend.getName(), "Frank");
    }
=======
public class TestFriend
{

	@Test
	public void testInitialization()
	{
		int id = 0;
		String name = "Smitty Jaegermanjenssen";
		Friend friend = new Friend(id, name);
		assertEquals(friend.getID(), id);
		assertEquals(friend.getName(), name);
		
	}
>>>>>>> 8dd7f5d90cef095704158691de7a027be0cbabbe

}
