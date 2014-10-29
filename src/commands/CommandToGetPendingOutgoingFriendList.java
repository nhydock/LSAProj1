package commands;

import java.util.ArrayList;

import system.Session;
import data.keys.PersonKey;
import domain.model.Friend;
import domain.model.Person;

/**
 * Cause the list of pending friend requests from this user to other users to be
 * fetched from the domain model (may or may not cause reading from the DB
 * depending on the state of the domain model)
 * 
 * @author merlin
 *
 */
public class CommandToGetPendingOutgoingFriendList implements Command {
    private ArrayList<Friend> pendingFriendsList;
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
    	for(Friend f : pendingFriendsList) {
    		if(string.equals("")) {
        		string = f.getUserName();
    		} else {
        		string += " " + f.getUserName();
    		}
    	}
        return string;
    }

}
