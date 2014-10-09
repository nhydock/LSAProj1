package commands;

import java.util.ArrayList;

import system.Session;
import data.keys.PersonKey;
import domain.model.Friend;
import domain.model.Person;

/**
 * Cause the list of friend requests from other user to this user to be fetched
 * from the domain model (may or may not cause reading from the DB depending on
 * the state of the domain model)
 * 
 * @author merlin
 *
 */
public class PendingIncomingFriendList implements Command {

    private int userID;
    ArrayList<Friend> pendingFriendsList;

    /**
     * The userID of the current user
     * 
     * @param userID
     *            unique
     */
    public PendingIncomingFriendList(int userID) {
        this.userID = userID;
    }

    /**
     * 
     * @see Command#execute()
     */
    @Override
    public void execute() {
        Person p = (Person)Session.getMapper(Person.class).find(new PersonKey(userID));
        pendingFriendsList = p.getPendingFriends().getAsArrayList();
    }

    /**
     * A list of the friends associated with the given user
     * 
     * @see Command#getResult()
     */
    @Override
    public ArrayList<Friend> getResult() {
        // TODO Auto-generated method stub
        return pendingFriendsList;
    }

}
