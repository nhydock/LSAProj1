package commands;

import system.Session;
import data.keys.FriendKey;
import data.keys.PersonKey;
import domain.model.Friend;
import domain.model.Person;

/**
 * Initiates a friend request from one user to another
 * 
 * @author merlin
 *
 */
public class CommandToMakeFriendRequest implements Command {

    private int userIDOfRequester;
    private String userNameOfRequestee;

    /**
     * 
     * @param userIDOfRequester
     *            the User ID of the user making the request
     * @param userNameOfRequestee
     *            the User Name of the user being friended
     */
    public CommandToMakeFriendRequest(int userIDOfRequester, String userNameOfRequestee) {
        this.userIDOfRequester = userIDOfRequester;
        this.userNameOfRequestee = userNameOfRequestee;
    }

    /**
     * 
     * @see Command#execute()
     */
    @Override
    public void execute() {
        Person p = (Person)Session.getMapper(Person.class).find(new PersonKey(userIDOfRequester));
        Friend f = (Friend)Session.getMapper(Friend.class).find(new FriendKey(userNameOfRequestee));
        p.requestFriend(f);
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
