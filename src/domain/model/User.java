package domain.model;

public abstract class User extends DomainModelObject {

    protected String userName;
    protected String displayName;
    protected long id;
    
    public long getID() {
        return id;
    }
    
    /**
     * Get the friend's user name
     * 
     * @return the user name of the friend's login credentials
     */
    public String getUserName() {
    	return userName;
    }

    /**
     * Get the user's display name
     * 
     * @return the name the user has chosen to be known by
     */
    public String getDisplayName() {
    	return displayName;
    }
}
