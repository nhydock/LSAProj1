package domain.model;

import java.util.ArrayList;

import domain.model.interfaces.IGoalList;

public class GoalList implements IGoalList {
	
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

}
