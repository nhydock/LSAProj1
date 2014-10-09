package data.containers;


public class PersonData implements DataContainer {

    public final String name;
    public final String displayName;
    public final long id;
    public final long password;
    
    public PersonData(long id, String userName, String displayName, long password)
    {
        this.id = id;
        this.name = userName;
        this.displayName = displayName;
        this.password = password;
    }
    
}
