package domain.model;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

public class TestGoal {
    
    private Goal goal;
    private Date startDate;
    private Date goalDate;
    private Person person;
    
    @Before
    public void setup() {
	startDate = new Date(12345);
	goalDate = new Date(123456789);
	person = new Person(0, "Cristof", 1);
	goal = new Goal(0, startDate, goalDate, person, 1);
    }

    @Test
    public void testInitialization() {
	assertEquals(goal.getId(), 0);
	assertEquals(goal.getStartDate(), startDate);
	assertEquals(goal.getGoalDate(), goalDate);
	assertEquals(goal.getPerson(), person);
    }
    
    @Test
    public void testDateCompleted()
    {
    	goal.setCompleted();
    	
    	assertTrue(goal.getCompletedDate().equals(new Date(123456789)));
    }

}
