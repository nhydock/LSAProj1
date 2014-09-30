package domain.model;

import java.util.ArrayList;

import domain.model.interfaces.IFriendList;

public class FriendList extends DomainModelObject implements IFriendList {
    
    private ArrayList<Friend> friends;
    
    public FriendList(long sessionID) {
	super(sessionID);
	friends = new ArrayList<Friend>();
    }
    
    public void insert(Friend friend) {
	friends.add(friend);
    }

    public void delete(int index) {
	friends.remove(index);
    }
    
    public void update(Friend friend, int index) {
	friends.set(index, friend);
    }
    
    public ArrayList<Friend> getFriends() {
	return friends;
    }
    
}