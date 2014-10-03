package commands;

import java.util.ArrayList;

import domain.model.Friend;

/**
 * Cause the list of pending friend requests from this user to other users to be
 * fetched from the domain model (may or may not cause reading from the DB
 * depending on the state of the domain model)
 * 
 * @author merlin
 *
 */
public class PendingOutgoingFriendList implements Command {

    private int userID;

    /**
     * The userID of the current user
     * 
     * @param userID
     *            unique
     */
    public PendingOutgoingFriendList(int userID) {
        this.userID = userID;
    }

    /**
     * 
     * @see Command#execute()
     */
    @Override
    public void execute() {

    }

    /**
     * A list of the friends associated with the given user a
     * 
     * @see Command#getResult()
     */
    @Override
    public ArrayList<Friend> getResult() {
        // TODO Auto-generated method stub
        return null;
    }

}
