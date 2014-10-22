package domain.model;

import java.util.ArrayList;

public interface IPendingFriendsList {

    public void insert(Friend friend);
    public ArrayList<Friend> getIncomingAsArrayList();
    public boolean removeIncoming(Friend friend);
    public void rollbackValues();
    public ArrayList<Friend> getIncomingFriends();
    public long getUserID();
	public ArrayList<Friend> getOutgoingAsArrayList();
	public boolean removeOutgoing(Friend friend);
	public ArrayList<Friend> getOutgoingFriends();
}
