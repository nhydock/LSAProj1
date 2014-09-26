package domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

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

}
