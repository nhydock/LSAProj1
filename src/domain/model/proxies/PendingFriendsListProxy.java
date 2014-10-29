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
    public ArrayList<User> getAllRequests() {
        return proxyObject().getAllRequests();
    }

    @Override
    public boolean requestFriend(User friend) {
        return proxyObject().requestFriend(friend);
    }

    @Override
    public ArrayList<User> getIncomingRequests() {
        return proxyObject().getIncomingRequests();
    }

    @Override
    public ArrayList<User> getOutgoingRequests() {
        return proxyObject().getOutgoingRequests();
    }

    @Override
    public boolean denyFriend(User friend) {
        return proxyObject().denyFriend(friend);
    }

	@Override
	public boolean removeRequest(User friend) {
		return proxyObject().removeRequest(friend);
	}

}
