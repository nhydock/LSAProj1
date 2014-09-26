package domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestGoalList
{

	@Test
	public void testInit()
	{
		GoalList gl = new GoalList();
		assertNotNull(gl.getGoalList());
		
		assertEquals(gl.getGoalList().size(), 0);
	}

}
