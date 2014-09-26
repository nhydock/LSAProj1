package domain.model;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

public class TestActivityList
{

	ActivityList activityList;
	Activity activity;

	@Before
	public void setup()
	{
		activityList = new ActivityList();
		activity = new Activity(1, "Running", new Date(12345), 40, 100,
				new Person(0, "Theordore"));

	}

	@Test
	public void testInitialization()
	{
		assertNotNull(activityList.getActivities());
		assertEquals(activityList.getActivities().size(), 0);

	}

	@Test
	public void testAddingActivity()
	{
		assertEquals(activityList.getActivities().size(), 0);
		activityList.insert(activity);
		assertEquals(activityList.getActivities().size(), 1);
		assertEquals(activityList.getActivities().get(0), activity);
	}

	@Test
	public void testRemovingActivity()
	{
		assertEquals(activityList.getActivities().size(), 0);
		activityList.insert(activity);
		activityList.delete(0);
		assertEquals(activityList.getActivities().size(), 0);
	}

	@Test
	public void testUpdatingActivity()
	{
		assertEquals(activityList.getActivities().size(), 0);
		activityList.insert(activity);
		Activity newActivity = new Activity(1, "Walking", new Date(12345), 40,
				100, new Person(0, "Theordore"));
		activityList.update(newActivity, 0);
		assertEquals(activityList.getActivities().get(0).getType(),
				newActivity.getType());
	}

}
