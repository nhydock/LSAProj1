package commands;

import system.Session;
import data.keys.FriendKey;
import data.keys.PersonKey;
import domain.model.Friend;
import domain.model.Person;

/**
 * Cancels a friend request between two users
 * 
 * @author merlin
 *
 */
public class CommandToUnFriend implements Command {

    private int userIDOfRequester;
    private String userNameOfRequestee;

    /**
     * 
     * @param userIDOfRequester
     *            the User ID of the user cancel the relationship
     * @param userNameOfRequestee
     *            the User Name of the user being unfriended
     */
    public CommandToUnFriend(int userIDOfRequester, String userNameOfRequestee) {
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
        Friend f = (Friend)Session.getMapper(Person.class).find(new FriendKey(userNameOfRequestee));
        p.removeFriend(f);
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
