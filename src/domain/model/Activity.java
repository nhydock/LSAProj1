package domain.model;

import java.sql.Date;

public class Activity extends DomainModelObject {
    
    private long id;
    private String type;
    private Date date;
    private int minutesSpent;
    private int caloriesBurned;
    private Person person;
    
    public Activity(long id, String type, Date date, int minutesSpent, int caloriesBurned, Person person, long sessionID) {
	super(sessionID);
	this.id = id;
	this.type = type;
	this.date =  date;
	this.minutesSpent = minutesSpent;
	this.caloriesBurned = caloriesBurned;
	this.person = person;
    }
    
    public long getId() {
	return id;
    }
    
    public String getType() {
	return type;
    }
    
    public Date getDate() {
	return date;
    }
    
    public int getMinutesSpent() {
	return minutesSpent;
    }
    
    public int getCaloriesBurned() {
	return caloriesBurned;
    }
    
    public Person getPerson() {
	return person;
    }
    

}
