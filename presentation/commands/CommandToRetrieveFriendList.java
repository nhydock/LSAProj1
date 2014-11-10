package commands;

import java.util.Iterator;
import java.util.Set;

import system.Session;
import data.keys.PersonKey;
import domain.model.Person;
import domain.model.User;

/**
 * Cause a user's friend list to be fetched from the domain model (may or may
 * not cause reading from the DB depending on the state of the domain model
 * 
 * @author merlin
 *
 */
public class CommandToRetrieveFriendList implements Command {

    private int userID;
    private Set<User> list;

    /**
     * The userID of the current user
     * 
     * @param userID
     *            unique
     */
    public CommandToRetrieveFriendList(int userID) {
        this.userID = userID;
    }

    /**
     * 
     * @see Command#execute()
     */
    @Override
    public void execute() {
        // TODO Auto-generated method stub
        Person p = (Person)Session.getMapper(Person.class).find(new PersonKey(userID));
        list = p.getFriends();
    }

    /**
     * A list of the friends associated with the given user
     * 
     * @see Command#getResult()
     */
    @Override
    public String getResult() {
    	String output = "";
    	Iterator<User> iter = list.iterator();
    	while (iter.hasNext())
    	{
    		User user = iter.next();
    		output += user.toString();
    		if (iter.hasNext())
    		{
    			output += ",";
    		}
    	}
        return output;
    }

}
