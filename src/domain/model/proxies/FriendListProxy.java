package domain.model.proxies;

import java.util.ArrayList;

import data.keys.FriendListKey;
import domain.UnitOfWork;
import domain.model.Friend;
import domain.model.FriendList;
import domain.model.LazyDomainObject;
import domain.model.RealFriendList;

public class FriendListProxy extends LazyDomainObject<RealFriendList> implements
        FriendList {

    /**
     * @param id
     *            - user this list belongs to
     * @param friends
     *            - friends of the user
     */
    public FriendListProxy(long id) {
        super(RealFriendList.class, new FriendListKey(id));
    }

    public void insertFriend(Friend friend) {
        proxyObject().insertFriend(friend);
        UnitOfWork.get().markChanged(proxyObject());
    }

    public void removeFriend(Friend friend) {
        proxyObject().removeFriend(friend);
        UnitOfWork.get().markChanged(proxyObject());
    }

    public ArrayList<Friend> getFriends() {
        return proxyObject().getFriends();
    }

    public long getUserID() {
        return proxyObject().getUserID();
    }

}
