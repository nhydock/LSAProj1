package data.containers;

/**
 * Data container for pending friends gateway interactions.
 * <p/>
 * We only care about outgoing requests because this is mainly used
 * for insertion of new data.
 * @author nhydock
 *
 */
public class PendingFriendsListData implements DataContainer {

    public final long userID;
    public final long[] outgoingRequests;
    
    public PendingFriendsListData(long userID, long[] outgoingRequests)
    {
        this.userID = userID;
        this.outgoingRequests = outgoingRequests;
    }
    
}
