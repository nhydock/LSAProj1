package data.containers;

public class FriendListData implements DataContainer {

    public final long id;
    public final long[] friends;
    public final long[] toRemove;
    
    public FriendListData(long id, long[] friends, long[] remove)
    {
        this.id = id;
        this.friends = friends;
        this.toRemove = remove;
    }
    
}
