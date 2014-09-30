package domain.model;

import static org.junit.Assert.*;

import java.sql.Date;

<<<<<<< HEAD
import org.junit.Before;
import org.junit.Test;

public class TestGoalList {
    
    private Goal goal;
    private GoalList goals;
    private Goal newGoal;
    
    @Before
    public void setup() {
	goals = new GoalList();
	goal = new Goal(123, new Date(123), new Date(12345), new Person(0, "Peach"));
	newGoal = new Goal(1234, new Date(123), new Date(12345), new Person(0, "Peach"));

    }

    @Test
    public void testInitialization() {
	assertNotNull(goals.getGoals());
    }
    
    @Test
    public void testInsertingGoal() {
	goals.insert(goal);
	assertEquals(goals.getGoals().size(), 1);
    }
    
    @Test
    public void testDeletingGoal() {
	goals.insert(goal);
	goals.delete(0);
	assertEquals(goals.getGoals().size(), 0);
    }
    
    @Test
    public void testUpdatingGoal() {
	goals.insert(goal);
	goals.update(newGoal, 0);
	assertEquals(goals.getGoals().get(0).getId(), 1234);

    }
=======
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
>>>>>>> 8dd7f5d90cef095704158691de7a027be0cbabbe

}
