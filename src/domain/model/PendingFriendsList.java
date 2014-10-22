package domain.model;

import java.util.ArrayList;

public class PendingFriendsList extends DomainModelObject implements IPendingFriendsList{

    private int id;
    private ArrayList<Friend> requests;
    private ArrayList<Friend> incomingRequests;
    private ArrayList<Friend> outgoingRequests;
    
    public PendingFriendsList(long id, ArrayList<Friend> in, ArrayList<Friend> out) {
        requests = new ArrayList<Friend>();
        requests.addAll(in);
        requests.addAll(out);
        incomingRequests = in;
        outgoingRequests = out;
    }

    @Override
    public ArrayList<Friend> getAllRequests() {
        return requests;
    }

    @Override
    public boolean requestFriend(Friend friend) {
        boolean added = outgoingRequests.add(friend);
        added = added && requests.add(friend);
        return added;
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
    public long getUserID() {
        return id;
    }

    @Override
    public ArrayList<Friend> getIncomingRequests() {
        return incomingRequests;
    }

    @Override
    public ArrayList<Friend> getOutgoingRequests() {
        return outgoingRequests;
    }

    @Override
    public boolean denyFriend(Friend friend) {
        return incomingRequests.remove(friend);
    }

}
