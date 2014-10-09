package commands;

import system.Session;
import data.keys.FriendKey;
import data.keys.PersonKey;
import domain.model.Friend;
import domain.model.Person;

/**
 * Reject a friend request from one user to another
 * 
 * @author merlin
 *
 */
public class RejectFriendRequest implements Command {

    private int userIDOfRequestee;
    private String userNameOfRequester;

    /**
     * 
     * @param userIDOfRequestee
     *            the User ID of the user accepting the request
     * @param userNameOfRequester
     *            the User Name of the user who initiated the friend request
     */
    public RejectFriendRequest(int userIDOfRequestee, String userNameOfRequester) {
        this.userIDOfRequestee = userIDOfRequestee;
        this.userNameOfRequester = userNameOfRequester;

    }

    /**
     * 
     * @see Command#execute()
     */
    @Override
    public void execute() {
        Person p = (Person)Session.getMapper(Person.class).find(new PersonKey(userIDOfRequestee));
        Friend f = (Friend)Session.getMapper(Friend.class).find(new FriendKey(userNameOfRequester));
        p.declineFriendRequest(f);
    }

    /**
     * Nothing needs to be retrieved from this command
     * 
     * @see Command#getResult()
     */
    @Override
    public Object getResult() {
        return null;
    }

}
