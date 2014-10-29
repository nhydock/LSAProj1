package domain.model;

import java.util.ArrayList;

import system.Session;

public class RealFriendList extends DomainModelObject implements FriendList {

    private long id;
    private ArrayList<User> friends;
    private ArrayList<User> removed;

    /**
     * @param id
     *            - user this list belongs to
     * @param friends
     *            - friends of the user
     */
    public RealFriendList(long id, ArrayList<User> friends) {
        super();
        this.id = id;
        this.friends = friends;
        removed = new ArrayList<User>();
    }

    @Override
    public void insertFriend(User friend) {
        friends.add(friend);
        Session.getUnitOfWork().markChanged(this);
    }
    @Override
    public void removeFriend(User friend) {
        if (friends.remove(friend)) {
        	removed.add(friend);
        	Session.getUnitOfWork().markChanged(this);
        }
    }
    @Override
    public ArrayList<User> getFriends() {
        return friends;
    }
    @Override
    public long getUserID() {
        return id;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void rollbackValues() {
        friends.clear();
        removed.clear();
        friends.addAll((ArrayList<User>) values.get("friends"));
    }

    @Override
    public void saveValues() {
        ArrayList<User> clonedList = new ArrayList<User>(friends);
        values.put("friends", clonedList);
        removed.clear();
    }
    
    /**
     * Get the tmp array of removed friends.
     * Should only be referenced by tightly coupled systems, such as data mappers
     * @return
     */
    public ArrayList<User> getRemovedFriends() {
    	return removed;
    }
}
