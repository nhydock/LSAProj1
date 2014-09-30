package domain.model;

public class Person extends DomainModelObject {

    private ActivityList activitys;
    private FriendList friends;
    private GoalList goals;

    private long id;
    private String name;

    public Person(long id, String name, long sessionID) {
	super(sessionID);
	this.id = id;
	this.name = name;
	activitys = new ActivityList(sessionID);
	friends = new FriendList(sessionID);
	goals = new GoalList(sessionID);
    }

    /**
     * @return person id
     */
    public long getID() {
	return id;
    }
    
    /**
     * @return person name
     */
    public String getName() {
	return name;
    }
    
    public ActivityList getActivitys() {
	return activitys;
    }
    
    public FriendList getFriends() {
	return friends;
    }
    
    public GoalList getGoals() {
	return goals;
    }
}
