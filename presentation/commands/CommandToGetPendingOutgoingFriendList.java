package commands;

import java.util.Iterator;
import java.util.Set;

import system.Session;
import data.keys.PersonKey;
import domain.model.Person;
import domain.model.User;

/**
 * Cause the list of pending friend requests from this user to other users to be
 * fetched from the domain model (may or may not cause reading from the DB
 * depending on the state of the domain model)
 * 
 * @author merlin
 *
 */
public class CommandToGetPendingOutgoingFriendList implements Command {
    private Set<User> pendingFriendsList;
    private int userID;

    /**
     * The userID of the current user
     * 
     * @param userID
     *            unique
     */
    public CommandToGetPendingOutgoingFriendList(int userID) {
        this.userID = userID;
    }

    /**
     * 
     * @see Command#execute()
     */
    @Override
    public void execute() {
        Person p = (Person)Session.getMapper(Person.class).find(new PersonKey(userID));
        pendingFriendsList = p.getPendingFriends().getOutgoingRequests();
    }

    /**
     * A list of the friends associated with the given user a
     * 
     * @see Command#getResult()
     */
    @Override
    public String getResult() {
    	String string = "";
    	Iterator<User> iter = pendingFriendsList.iterator();
    	while (iter.hasNext())
    	{
    		User user = iter.next();
    		string += user.toString();
    		if (iter.hasNext())
    		{
    			string += ",";
    		}
    	}
    	return string.trim();
    }

}
