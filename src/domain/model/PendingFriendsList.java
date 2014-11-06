package domain.model;

import java.util.ArrayList;

import data.keys.PendingFriendsListKey;
import data.keys.PersonKey;

import system.Session;

public class PendingFriendsList extends DomainModelObject implements IPendingFriendsList{

	private PersonKey parentKey;
    private ArrayList<User> requests;
    private ArrayList<User> incomingRequests;
    private ArrayList<User> outgoingRequests;
    
    public PendingFriendsList(long id, ArrayList<User> in, ArrayList<User> out) {
        requests = new ArrayList<User>();
        requests.addAll(in);
        requests.addAll(out);
        incomingRequests = in;
        outgoingRequests = out;
        parentKey = new PersonKey(id);
        saveValues();
    }

    @Override
    public ArrayList<User> getAllRequests() {
        return requests;
    }
    
    @Override
    public boolean removeRequest(User friend) {
    	boolean removed = false;
    	removed = removed || incomingRequests.remove(friend);
    	removed = removed || outgoingRequests.remove(friend);
    	if (removed)
    	{
    		requests.remove(friend);
    		Session.getUnitOfWork().markChanged(this);
        
        }
    	return removed;
    }

    @Override
    public boolean requestFriend(User friend) {
    	if (friend == null || friend.getID() == -1)
    		return false;
    	
        boolean added = outgoingRequests.add(friend);
        added = added && requests.add(friend);
        if (added)
        {
	        Session.getUnitOfWork().markChanged(this);
	    }
        
        return added;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void rollbackValues() {
        requests.clear();
        incomingRequests.clear();
        outgoingRequests.clear();
        requests.addAll((ArrayList<User>) values.get("requests"));
        incomingRequests.addAll((ArrayList<User>) values.get("incomingRequests"));
        outgoingRequests.addAll((ArrayList<User>) values.get("outgoingRequests"));
    }

    @Override
    public void saveValues() {
        ArrayList<User> clonedList = new ArrayList<User>(requests);
        values.put("requests", clonedList);
        clonedList = new ArrayList<User>(incomingRequests);
        values.put("incomingRequests", clonedList);
        clonedList = new ArrayList<User>(outgoingRequests);
        values.put("outgoingRequests", clonedList);
    }

    @Override
    public long getUserID() {
        return parentKey.id;
    }

    @Override
    public ArrayList<User> getIncomingRequests() {
        return incomingRequests;
    }

    @Override
    public ArrayList<User> getOutgoingRequests() {
        return outgoingRequests;
    }

    @Override
    public boolean denyFriend(User friend) {
        boolean removed = incomingRequests.remove(friend);
        if (removed)
        {
        	requests.remove(friend);
        	Session.getUnitOfWork().markChanged(this);
        }
        return removed;
    }

}
