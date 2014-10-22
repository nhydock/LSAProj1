package domain.model;

import java.util.ArrayList;

public interface IPendingFriendsList {

    public ArrayList<Friend> getAllRequests();
    public ArrayList<Friend> getIncomingRequests();
    public ArrayList<Friend> getOutgoingRequests();
    public boolean requestFriend(Friend friend);
    public boolean denyFriend(Friend friend);
    public void rollbackValues();
    public long getUserID();
}
