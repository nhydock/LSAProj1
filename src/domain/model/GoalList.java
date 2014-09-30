package domain.model;

import java.util.ArrayList;

import domain.model.interfaces.IGoalList;

public class GoalList extends DomainModelObject implements IGoalList {

    private ArrayList<Goal> goals;
    
    public GoalList(long sessionID) {
	super(sessionID);
	goals = new ArrayList<Goal>();
    }
    
    public void insert(Goal goal) {
	goals.add(goal);
    }

    public void delete(int index) {
	goals.remove(index);
    }
    
    public void update(Goal goal, int index) {
	goals.set(index, goal);
    }
    
    public ArrayList<Goal> getGoals() {
	return goals;
    }
    
}

