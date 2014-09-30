package domain.model;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

public class TestActivity {

    @Test
    public void testInitialization() {
	int id = 1;
	String type = "Running";
	Date date = new Date(12345);
	int minutesSpent = 40;
	int caloriesBurned = 100;
	Person person = new Person(0, "Bobbo", 1);
	Activity activity = new Activity(id, type, date, minutesSpent, caloriesBurned, person, 1);
	assertEquals(activity.getPerson(), person);
	assertEquals(activity.getId(), id);
	assertEquals(activity.getType(), type);
	assertEquals(activity.getDate(), date);
	assertEquals(activity.getMinutesSpent(), minutesSpent);
	assertEquals(activity.getCaloriesBurned(), caloriesBurned);
    }

}
