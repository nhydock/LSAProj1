package domain.model;

import domain.model.proxies.ActivityListProxy;

public class Person extends DomainModelObject {

    private ActivityList activitys;
    private FriendList friends;
    private GoalList goals;
    private PendingFriendsList pendingFriends;

    private long id;
    private String name;

    public Person(long id, String name, long sessionID) {
	super(sessionID);
	this.id = id;
	this.name = name;
	activitys = new ActivityListProxy();
	friends = new FriendListProxy();
	goals = new GoalListProxy();
	pendingFriends = new PendingFriendsListProxy();
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
