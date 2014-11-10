package domain.model;

/**
 * Holds enough information about a user to have them in a friend list
 * 
 * @author merlin
 *
 */
public class Friend extends User {

    public Friend(String userName, String displayName, long id) {
        this.userName = userName;
        this.displayName = displayName;
        this.id = id;
    }

    @Override
    public void rollbackValues() {}

    @Override
    public void saveValues() {}
    
    @Override
    public String toString() {
    	return this.getDisplayName();
    }
}
