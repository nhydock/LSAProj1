package domain.model;

import java.util.ArrayList;

public class PendingFriendsList extends DomainModelObject implements IPendingFriendsList{

    private ArrayList<Friend> friends;

    public PendingFriendsList() {
        friends = new ArrayList<Friend>();
    }

    @Override
    public void insert(Friend friend) {
        friends.add(friend);
    }

    @Override
    public ArrayList<Friend> getAsArrayList() {
        return friends;
    }

    @Override
    public boolean remove(Friend friend) {
        return friends.remove(friend);
    }

    @Override
    public void rollbackValues() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void saveValues() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ArrayList<Friend> getFriends() {
        return friends;
    }

    @Override
    public long getUserID() {
        // TODO Auto-generated method stub
        return 0;
    }

}
