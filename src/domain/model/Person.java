package domain.model;

public class Person {

    private ActivityList activitys;
    private FriendList friends;
    private GoalList goals;

    private long id;
    private String name;

    public Person(long id, String name) {
	this.id = id;
	this.name = name;
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
}
