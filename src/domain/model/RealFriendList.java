package domain.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import system.Session;

public class RealFriendList extends DomainModelObject implements FriendList {

    private long id;
    private Set<User> friends;
    private Set<User> removed;

    /**
     * @param id
     *            - user this list belongs to
     * @param friends
     *            - friends of the user
     */
    public RealFriendList(long id, Set<User> friends) {
        super();
        this.id = id;
        this.friends = friends;
        removed = new HashSet<User>();
    }

    @Override
    public void insertFriend(User friend) {
        if (friends.add(friend)){
        	Session.getUnitOfWork().markChanged(this);
        }
    }
    @Override
    public void removeFriend(User friend) {
        if (friends.remove(friend)) {
        	removed.add(friend);
        	Session.getUnitOfWork().markChanged(this);
        }
    }
    @Override
    public Set<User> getFriends() {
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
        friends.addAll((Set<User>) values.get("friends"));
    }

    @Override
    public void saveValues() {
        ArrayList<User> clonedList = new ArrayList<User>(friends);
        values.put("friends", clonedList);
        removed.clear();
    }
    
    /**
     * Get the tmp set of removed friends.
     * Should only be referenced by tightly coupled systems, such as data mappers
     * @return
     */
    public Set<User> getRemovedFriends() {
    	return removed;
    }
}
