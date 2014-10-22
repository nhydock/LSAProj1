package commands;

import system.Session;
import data.keys.FriendKey;
import domain.model.Person;

/**
 * Retrieve a specified user from the database into the domain model
 */
public class CommandToSelectUser implements Command {
    
    private String userName;
    private String password;
    private Person person;

    /**
     * @param userName
     *            the username from the user's credentials
     * @param password
     *            the password from the user's credentials
     */
    public CommandToSelectUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Create the domain model object for the specified user (retrieve that user
     * from the database)
     * 
     * @see Command#execute()
     */
    @Override
    public void execute() {
        Person p = (Person) Session.getMapper(Person.class).find(new FriendKey(userName));
        if(person.getPassword() == password.hashCode()) {
            this.person = p;
        }
    }

    /**
     * This should return the appropriate Person object from the domain model.
     * Null if the credentials of the user were invalid
     * 
     * @see Command#getResult()
     */
    @Override
    public Person getResult() {
        return person;
    }

}
