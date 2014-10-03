package domain.model;

import java.util.ArrayList;

import data.keys.PersonKey;
import domain.DataMapper;
import domain.model.proxies.*;

public class Person extends User {

    private FriendList friends;
    private PendingFriendsList pendingFriends;

    private long id;
    private String name;
    private long password;
    private String displayName;

    /**
     * Construct a new person
     * 
     * @param name
     */
    public Person(String name, String password, String displayName) {
        this.name = name;
        this.id = -1;
        this.password = password.hashCode();
        this.displayName = displayName;
        friends = new FriendListProxy(id);
        pendingFriends = new PendingFriendsListProxy();
    }

    /**
     * Construct a person from data loaded from a gateway
     * 
     * @param name
     * @param id
     */
    public Person(String name, long password, long id) {
        this.id = id;
        this.name = name;
        this.password = password;
        friends = new FriendListProxy(id);
        pendingFriends = new PendingFriendsListProxy();
    }
    
//    public Person(long id) {
//        this.id = id;
//        DataMapper.get().get(Person.class, new PersonKey(id));
//        friends = new FriendListProxy();
//        pendingFriends = new PendingFriendsListProxy();
//    }

    /**
     * @return person id
     */
    public long getID() {
        return id;
    }

    /**
     * @return person name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the person's hashed password
     */
    public long getPassword() {
        return password;
    }
    
    /**
     * @return person display name
     */
    public String getDisplayName() {
	return displayName;
    }

    /**
     * Update's the user's password
     * 
     * @param newPass
     *            - text version of the new password
     */
    public void setPassword(String newPass) {
        password = newPass.hashCode();
        getUnitOfWork().markChanged();
    }

    /**
     * Changes this user's name
     * 
     * @param newName
     *            - their new identity
     */
    public void setName(String newName) {
	displayName = newName;
        getUnitOfWork().markChanged();
    }

    /**
     * @return data representation of a friends list
     */
    public ArrayList<Friend> getFriends() {
        return friends.getFriends();
    }

    /**
     * @return data reference of the list of friends
     */
    protected FriendList getFriendList() {
        return friends;
    }

    public void acceptFriendRequest(Friend friend) {

        boolean removed = pendingFriends.remove(friend);
        if (removed) {
            friends.insertFriend(friend);
        }
    }
    
    public void acceptFriendRequest(String friendUsername) {
	//TODO fix this also
//        boolean removed = pendingFriends.remove(friend);
//        if (removed) {
//            friends.insert(friend);
//        }
    }
    
    public void declineFriendRequest(Friend friend) {
        pendingFriends.remove(friend);
    }
    
    public PendingFriendsList getPendingFriends()
    {
	return pendingFriends;
    }
    
    public static Person findPerson(long id)
    {
	Person p = DataMapper.get().get(Person.class, new PersonKey(id));
	
	return p;
    }
    
    public void requestFriend(String username)
    {
	//TODO fix this shit
//	pendingFriends.insert(friend);
    }

    public void removeFriend(String friendUserName) {
	// TODO Auto-generated method stub
	Friend toRemove = null;
	for(Friend f : friends.getFriends())
	{
	    if(f.getUserName() == friendUserName)
	    {
		toRemove = f;
	    }
	}
	if(toRemove != null)
	{
	    friends.removeFriend(toRemove);
	}
    }
}
