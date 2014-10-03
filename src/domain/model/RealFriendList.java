package domain.model;

import java.util.ArrayList;

public class RealFriendList extends DomainModelObject implements FriendList {

    private long id;
    private ArrayList<Friend> friends;

    /**
     * @param id
     *            - user this list belongs to
     * @param friends
     *            - friends of the user
     */
    public RealFriendList(long id, ArrayList<Friend> friends) {
        super();
        this.id = id;
        this.friends = friends;
    }

    public void insertFriend(Friend friend) {
        friends.add(friend);
        getUnitOfWork().markChanged();
    }

    public void removeFriend(Friend friend) {
        friends.remove(friend);
        getUnitOfWork().markChanged();
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public long getUserID() {
        return id;
    }

}