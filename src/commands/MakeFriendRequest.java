package commands;

import data.keys.PersonKey;
import domain.DataMapper;
import domain.model.Person;

/**
 * Initiates a friend request from one user to another
 * 
 * @author merlin
 *
 */
public class MakeFriendRequest implements Command {

    private int userIDOfRequester;
    private String userNameOfRequestee;

    /**
     * 
     * @param userIDOfRequester
     *            the User ID of the user making the request
     * @param userNameOfRequestee
     *            the User Name of the user being friended
     */
    public MakeFriendRequest(int userIDOfRequester, String userNameOfRequestee) {
        this.userIDOfRequester = userIDOfRequester;
        this.userNameOfRequestee = userNameOfRequestee;

    }

    /**
     * 
     * @see Command#execute()
     */
    @Override
    public void execute() {
	Person p = Person.findPerson(userIDOfRequester);
	
	p.requestFriend(userNameOfRequestee);
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
