package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import system.Session;
import data.keys.PersonKey;
import domain.model.Person;
import domain.model.User;

/**
 * Cause the list of friend requests from other user to this user to be fetched
 * from the domain model (may or may not cause reading from the DB depending on
 * the state of the domain model)
 * 
 * @author merlin
 *
 */
public class CommandToGetPendingIncomingFriendList implements Command {

    private int userID;
    Set<User> pendingFriendsList;

    /**
     * The userID of the current user
     * 
     * @param userID
     *            unique
     */
    public CommandToGetPendingIncomingFriendList(int userID) {
        this.userID = userID;
    }

    /**
     * 
     * @see Command#execute()
     */
    @Override
    public void execute() {
        Person p = (Person)Session.getMapper(Person.class).find(new PersonKey(userID));
        pendingFriendsList = p.getPendingFriends().getIncomingRequests();
    }

    /**
     * A list of the friends associated with the given user
     * 
     * @see Command#getResult()
     */
    @Override
    public List<User> getResult() {
    	return new ArrayList<User>(pendingFriendsList);
    }

}
