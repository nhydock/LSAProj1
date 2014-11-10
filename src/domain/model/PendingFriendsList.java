package domain.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import data.keys.PersonKey;
import system.Session;

public class PendingFriendsList extends DomainModelObject implements IPendingFriendsList{

	private PersonKey parentKey;
    private Set<User> requests;
    private Set<User> incomingRequests;
    private Set<User> outgoingRequests;
    
    public PendingFriendsList(long id, Set<User> in, Set<User> out) {
        requests = new HashSet<User>();
        requests.addAll(in);
        requests.addAll(out);
        incomingRequests = in;
        outgoingRequests = out;
        parentKey = new PersonKey(id);
        saveValues();
    }

    @Override
    public Set<User> getAllRequests() {
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
        requests.addAll((Set<User>) values.get("requests"));
        incomingRequests.addAll((Set<User>) values.get("incomingRequests"));
        outgoingRequests.addAll((Set<User>) values.get("outgoingRequests"));
    }

    @Override
    public void saveValues() {
        Set<User> clonedList = new HashSet<User>(requests);
        values.put("requests", clonedList);
        clonedList = new HashSet<User>(incomingRequests);
        values.put("incomingRequests", clonedList);
        clonedList = new HashSet<User>(outgoingRequests);
        values.put("outgoingRequests", clonedList);
    }

    @Override
    public long getUserID() {
        return parentKey.id;
    }

    @Override
    public Set<User> getIncomingRequests() {
        return incomingRequests;
    }

    @Override
    public Set<User> getOutgoingRequests() {
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
