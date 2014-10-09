package data.containers;

public class FriendListData implements DataContainer {

    public final long id;
    public final long[] friends;
    
    public FriendListData(long id, long[] friends)
    {
        this.id = id;
        this.friends = friends;
    }
    
}
