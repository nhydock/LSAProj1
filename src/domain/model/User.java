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
    
    @Override
    public int hashCode() {
    	return (int) id;
    }
    
    @Override
    public boolean equals(Object obj)
    {
    	if (obj == null)
    		return false;
    	if (obj == this)
    		return true;
    	if (!(obj instanceof User))
    		return false;
    	
    	User u = (User)obj;
    	return u.id == this.id;
    }
}
