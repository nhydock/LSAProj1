package domain.model;

import java.util.ArrayList;

import system.Session;

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
        Session.getUnitOfWork().markChanged(this);
    }

    public void removeFriend(Friend friend) {
        friends.remove(friend);
        Session.getUnitOfWork().markChanged(this);
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public long getUserID() {
        return id;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void rollbackValues() {
        this.id = (long) values.get("id");
        this.friends = (ArrayList<Friend>) values.get("friends");
    }

    @Override
    public void saveValues() {
        values.put("id", id);
        values.put("friends", friends);
    }

}
