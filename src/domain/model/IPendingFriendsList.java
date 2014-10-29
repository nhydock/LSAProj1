package domain.model;

import java.util.ArrayList;

public interface IPendingFriendsList {

    public ArrayList<User> getAllRequests();
    public ArrayList<User> getIncomingRequests();
    public ArrayList<User> getOutgoingRequests();
    public boolean requestFriend(User friend);
    public boolean denyFriend(User friend);
    public boolean removeRequest(User friend);
    public void rollbackValues();
    public long getUserID();
}
