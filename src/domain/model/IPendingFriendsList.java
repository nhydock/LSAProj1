package domain.model;

import java.util.Set;

public interface IPendingFriendsList {

    public Set<User> getAllRequests();
    public Set<User> getIncomingRequests();
    public Set<User> getOutgoingRequests();
    public boolean requestFriend(User friend);
    public boolean denyFriend(User friend);
    public boolean removeRequest(User friend);
    public void rollbackValues();
    public long getUserID();
}
