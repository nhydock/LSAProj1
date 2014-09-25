package domain.model;

import java.util.ArrayList;

import domain.model.interfaces.IActivityList;

public class ActivityList implements IActivityList {
    
    private ArrayList<Activity> activities;
    
    public ActivityList() {
	this.activities = new ArrayList<Activity>();
    }
    
    /**
     * Inserts an activity into the activities list
     * @param activity activity to insert
     */
    public void insert(Activity activity) {
	activities.add(activity);
    }
    
    /**
     * Deletes an activity from the activities list
     * @param index position to delete it from
     */
    public void delete(int index) {
	activities.remove(index);
    }
    
    /**
     * Updates an activity based on index
     * @param activity activity to set in given index
     * @param index position to update activity
     */
    public void update(Activity activity, int index) {
	activities.set(index, activity);
    }
    
    /**
     * @return activitylist activities
     */
    public ArrayList<Activity> getActivities() {
	return activities;
    }

}
