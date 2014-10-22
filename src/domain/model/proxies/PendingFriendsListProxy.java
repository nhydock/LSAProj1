package domain.model.proxies;

import java.util.ArrayList;

import system.Session;
import data.keys.PendingFriendsListKey;
import domain.model.*;

public class PendingFriendsListProxy extends LazyDomainObject<PendingFriendsList> implements IPendingFriendsList {
    /**
     * @param id
     *            - user this list belongs to
     * @param friends
     *            - friends of the user
     */
    public PendingFriendsListProxy(long id) {
        super(PendingFriendsList.class, new PendingFriendsListKey(id));
    }

    @Override
    public void insert(Friend friend) {
        proxyObject().insert(friend);
        Session.getUnitOfWork().markChanged(proxyObject());
    }

    @Override
    public boolean removeIncoming(Friend friend) {
        boolean removed = proxyObject().removeIncoming(friend);
        Session.getUnitOfWork().markChanged(proxyObject());
        
        return removed;
    }

    @Override
    public ArrayList<Friend> getIncomingFriends() {
        return proxyObject().getIncomingFriends();
    }

    @Override
    public long getUserID() {
        return 0;
    }


    @Override
    public ArrayList<Friend> getIncomingAsArrayList() {
        return proxyObject().getIncomingAsArrayList();
    }

	@Override
	public ArrayList<Friend> getOutgoingAsArrayList()
	{
		return proxyObject().getOutgoingAsArrayList();
	}

	@Override
	public boolean removeOutgoing(Friend friend)
	{
		boolean removed = proxyObject().removeOutgoing(friend);
        Session.getUnitOfWork().markChanged(proxyObject());
        
        return removed;
	}

	@Override
	public ArrayList<Friend> getOutgoingFriends()
	{
		return proxyObject().getOutgoingFriends();
	}

}
