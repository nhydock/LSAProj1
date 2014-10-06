package domain.model;

public abstract class User extends DomainModelObject {

    protected String userName;
    protected String displayName;
    protected long id;
    
    public long getID() {
        return id;
    }

}
