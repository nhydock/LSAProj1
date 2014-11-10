package domain.model.proxies;

import java.util.Set;

import data.keys.FriendListKey;
import domain.model.FriendList;
import domain.model.LazyDomainObject;
import domain.model.RealFriendList;
import domain.model.User;

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
    @Override
    public void insertFriend(User friend) {
        proxyObject().insertFriend(friend);
    }
    @Override
    public void removeFriend(User friend) {
        proxyObject().removeFriend(friend);
    }
    @Override
    public Set<User> getFriends() {
        return proxyObject().getFriends();
    }
    @Override
    public long getUserID() {
        return proxyObject().getUserID();
    }

}
