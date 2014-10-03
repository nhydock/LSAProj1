package commands;

import java.util.ArrayList;

import domain.model.Friend;
import domain.model.Person;

/**
 * Cause a user's friend list to be fetched from the domain model (may or may
 * not cause reading from the DB depending on the state of the domain model
 * 
 * @author merlin
 *
 */
public class RetrieveFriendList implements Command {

    private int userID;
    private ArrayList<Friend> list;

    /**
     * The userID of the current user
     * 
     * @param userID
     *            unique
     */
    public RetrieveFriendList(int userID) {
        this.userID = userID;
    }

    /**
     * 
     * @see Command#execute()
     */
    @Override
    public void execute() {
        // TODO Auto-generated method stub
	list = Person.findPerson(userID).getFriends();
    }

    /**
     * A list of the friends associated with the given user
     * 
     * @see Command#getResult()
     */
    @Override
    public ArrayList<Friend> getResult() {
        // TODO Auto-generated method stub
        return list;
    }

}
