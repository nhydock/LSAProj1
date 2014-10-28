package domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import system.Session;

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
        Session.getUnitOfWork().markChanged(this);
        return added;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void rollbackValues() {
        this.id = (int) values.get("id");
        requests.clear();
        incomingRequests.clear();
        outgoingRequests.clear();
        requests.addAll((ArrayList<Friend>) values.get("requests"));
        incomingRequests.addAll((ArrayList<Friend>) values.get("incomingRequests"));
        outgoingRequests.addAll((ArrayList<Friend>) values.get("outgoingRequests"));

    }

    @Override
    public void saveValues() {
        values.put("int", id);
        ArrayList<Friend> clonedList = new ArrayList<>(requests);
        values.put("requests", clonedList);
        clonedList = new ArrayList<>(incomingRequests);
        values.put("incomingRequests", clonedList);
        clonedList = new ArrayList<>(outgoingRequests);
        values.put("outgoingRequests", clonedList);
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
        boolean removed = incomingRequests.remove(friend);
        if (removed)
        {
            Session.getUnitOfWork().markChanged(this);
        }
        return removed;
    }

}
