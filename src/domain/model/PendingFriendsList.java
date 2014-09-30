package domain.model;

import java.util.ArrayList;

import domain.model.interfaces.IPendingFriendsList;

public class PendingFriendsList extends DomainModelObject implements IPendingFriendsList {
    
    private ArrayList<Friend> friends;
    
    public PendingFriendsList(long sessionID) {
	super(sessionID);
	friends = new ArrayList<Friend>();
    }
    
    public void insert(Friend friend) {
	friends.add(friend);
    }

    public void declineFriend(int index) {
	friends.remove(index);
    }
    
    public void update(Friend friend, int index) {
	friends.set(index, friend);
    }
    
    public ArrayList<Friend> getFriends() {
	return friends;
    }
    
    public void acceptFriend(Person person, int index) {
	Friend friend = friends.get(index);
	friends.remove(index);
	person.getFriends().insert(friend);
    }
    

}
