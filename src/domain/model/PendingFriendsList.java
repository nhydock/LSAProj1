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

    public ArrayList<Friend> getAsArrayList() {
        return friends;
    }

    public boolean remove(Friend friend) {
        return friends.remove(friend);
    }

    @Override
    protected void restoreValues() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void saveValues() {
        // TODO Auto-generated method stub
        
    }

}
