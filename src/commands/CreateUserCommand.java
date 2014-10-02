package commands;

import domain.model.Person;

/**
 * Creates a new user in the system
 * 
 * @author merlin
 *
 */
public class CreateUserCommand implements Command {

    private String userName;
    private String password;
    private String displayName;
    private Person person;

    /**
     * Create a command that will add a new user to the system
     * 
     * @param userName
     *            the name of the user's login credentials
     * @param password
     *            that password of the user's login credentials
     * @param displayName
     *            the name by which the user wants to be referred
     */
    public CreateUserCommand(String userName, String password, String displayName) {
        this.userName = userName;
        this.password = password;
        this.displayName = displayName;
    }

    /**
     * @see Command#execute()
     */
    @Override
    public void execute() {
        Person person = new Person(userName, password, displayName);
        this.person = person;
    }

    /**
     * This should return the appropriate Person object from the domain model.
     * Null if the credentials of the user were invalid (userName not unique)
     * 
     * @see Command#getResult()
     */
    @Override
    public Person getResult() {
        return person;
    }

}
