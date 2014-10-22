package domain.model;

import java.util.ArrayList;

public class PendingFriendsList extends DomainModelObject implements IPendingFriendsList{

    private ArrayList<Friend> incomingFriendsList;
    private ArrayList<Friend> outgoingFriendsList;

    public PendingFriendsList() {
        incomingFriendsList = new ArrayList<Friend>();
        outgoingFriendsList = new ArrayList<Friend>();
    }

    @Override
    public void insert(Friend friend) {
        outgoingFriendsList.add(friend);
    }

    @Override
    public ArrayList<Friend> getIncomingAsArrayList() {
        return incomingFriendsList;
    }
    
    @Override
    public ArrayList<Friend> getOutgoingAsArrayList() {
        return outgoingFriendsList;
    }

    @Override
    public boolean removeIncoming(Friend friend) {
        return incomingFriendsList.remove(friend);
    }
    
    @Override
    public boolean removeOutgoing(Friend friend) {
        return outgoingFriendsList.remove(friend);
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
    public ArrayList<Friend> getIncomingFriends() {
        return incomingFriendsList;
    }
    
    @Override
    public ArrayList<Friend> getOutgoingFriends() {
        return outgoingFriendsList;
    }

    @Override
    public long getUserID() {
        // TODO Auto-generated method stub
        return 0;
    }

}
