package domain.model;

import java.util.ArrayList;

public class PendingFriendsList extends DomainModelObject {

    private ArrayList<Friend> friends;

    public PendingFriendsList() {
        friends = new ArrayList<Friend>();
    }

    public void insert(Friend friend) {
        friends.add(friend);
    }

    public void update(Friend friend, int index) {
        friends.set(index, friend);
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public boolean remove(Friend friend) {
        return friends.remove(friend);
    }

}
