package domain.model;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

public class TestGoalList {
    
    private Goal goal;
    private GoalList goals;
    private Goal newGoal;
    
    @Before
    public void setup() {
	goals = new GoalList(1);
	goal = new Goal(123, new Date(123), new Date(12345), new Person(0, "Peach", 1), 1);
	newGoal = new Goal(1234, new Date(123), new Date(12345), new Person(0, "Peach", 1), 1);

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
}
