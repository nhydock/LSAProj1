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

    public void insertFriend(Friend friend) {
        proxyObject().insert(friend);
        Session.getUnitOfWork().markChanged(proxyObject());
    }

    public void removeFriend(Friend friend) {
        proxyObject().remove(friend);
        Session.getUnitOfWork().markChanged(proxyObject());
    }

    public ArrayList<Friend> getFriends() {
        return proxyObject().getFriends();
    }

    @Override
    public long getUserID() {
        return 0;
    }

    @Override
    public void insert(Friend friend) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ArrayList<Friend> getAsArrayList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean remove(Friend friend) {
        // TODO Auto-generated method stub
        return false;
    }

}
