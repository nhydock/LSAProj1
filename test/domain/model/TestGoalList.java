package domain.model;

import static org.junit.Assert.*;

import java.sql.Date;

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
	
	@Test
	public void testInsert()
	{
		GoalList gl = new GoalList();
		Goal testG = new Goal(123456789, new Date(123456), new Date(231654897),	new Person(1, "name"));
		
		gl.insert(testG);
		assertEquals(gl.getGoalList().get(0), testG);
		assertEquals(gl.getGoalList().size(),1);
	}
	
	@Test
	public void testDelete()
	{
		GoalList gl = new GoalList();
		Goal testG = new Goal(123456789, new Date(123456), new Date(231654897),	new Person(1, "name"));
		
		gl.insert(testG);
		gl.delete(0);
		assertEquals(gl.getGoalList().size(),0);
		
	}
	
	@Test
	public void testUpdate()
	{
		GoalList gl = new GoalList();
		Goal testG = new Goal(123456789, new Date(123456), new Date(231654897),	new Person(1, "name"));
		Goal newG = new Goal(85, new Date(2), new Date(5835),	new Person(35, "name"));
		
		gl.insert(testG);
		gl.update(newG, 0);
		
		assertEquals(gl.getGoalList().get(0), newG);
		assertEquals(gl.getGoalList().size(),1);
	}

}
