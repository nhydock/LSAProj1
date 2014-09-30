package domain.model;

import java.util.ArrayList;

import domain.model.interfaces.IGoalList;

public class GoalList implements IGoalList {
<<<<<<< HEAD
    
    private ArrayList<Goal> goals;
    
    public GoalList() {
	goals = new ArrayList<Goal>();
    }
    
    public void insert(Goal goal) {
	goals.add(goal);
    }
=======
	
	ArrayList<Goal>  goalList;
	
	public GoalList()
	{
		// TODO Auto-generated constructor stub
		goalList = new ArrayList<Goal>();
	}
	
	public void insert(Goal goal)
	{
		goalList.add(goal);
	}
	
	public void delete(int index)
	{
		goalList.remove(index);
	}
	
	public void update(Goal newGoal, int index)
	{
		goalList.set(index, newGoal);
	}
	
	public ArrayList<Goal> getGoalList()
	{
		return goalList;
	}
>>>>>>> 8dd7f5d90cef095704158691de7a027be0cbabbe

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

