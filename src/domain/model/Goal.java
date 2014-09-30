package domain.model;

import java.sql.Date;

public class Goal extends DomainModelObject {
    
    private long id;
    private Date startDate;
    private Date completedDate;
    private Date goalDate;
    private Person person;
    
    public Goal(long id, Date startDate, Date goalDate, Person person, long sessionID) {
	super(sessionID);
	this.id = id;
	this.startDate = startDate;
	this.goalDate = goalDate;
	this.person = person;
    }
    
    public long getId() {
	return id;
    }
    
    public Date getStartDate() {
	return startDate;
    }
    
    public Date getCompletedDate() {
	return completedDate;
    }
    
    public void setCompleted()
    {
    	completedDate = new Date(123456789);
    }
    
    public Date getGoalDate() {
	return goalDate;
    }
    
    public Person getPerson() {
	return person;
    }
    

}
