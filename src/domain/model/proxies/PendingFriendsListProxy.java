package domain.model.proxies;

import java.util.ArrayList;

import data.keys.PendingFriendsListKey;
import domain.model.*;

public class PendingFriendsListProxy extends LazyDomainObject<PendingFriendsList> implements IPendingFriendsList {
    
    private long id;
    
    /**
     * @param id
     *            - user this list belongs to
     * @param friends
     *            - friends of the user
     */
    public PendingFriendsListProxy(long id) {
        super(PendingFriendsList.class, new PendingFriendsListKey(id));
        this.id = id;
    }

    @Override
    public long getUserID() {
        return id;
    }

    @Override
    public ArrayList<Friend> getAllRequests() {
        return proxyObject().getAllRequests();
    }

    @Override
    public boolean requestFriend(Friend friend) {
        return proxyObject().requestFriend(friend);
    }

    @Override
    public ArrayList<Friend> getIncomingRequests() {
        return proxyObject().getIncomingRequests();
    }

    @Override
    public ArrayList<Friend> getOutgoingRequests() {
        return proxyObject().getOutgoingRequests();
    }

    @Override
    public boolean denyFriend(Friend friend) {
        return proxyObject().denyFriend(friend);
    }

}
