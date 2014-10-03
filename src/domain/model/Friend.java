package domain.model;

import data.keys.FriendKey;
import domain.DataMapper;

/**
 * Holds enough information about a user to have them in a friend list
 * 
 * @author merlin
 *
 */
public class Friend extends User {

    private String userName;
    private String displayName;
    private long id;

    public Friend(String userName, String displayName) {
        this.userName = userName;
        this.displayName = displayName;
    }

    public Friend(String userName, String displayName, long id) {
        this.userName = userName;
        this.displayName = displayName;
        this.id = id;
    }

    /**
     * Get the friend's user name
     * 
     * @return the user name of the friend's login credentials
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Get the user's display name
     * 
     * @return the name the user has chosen to be known by
     */
    public String getDisplayName() {
        return displayName;
    }

    public static Friend findFriend(String name) {
        return DataMapper.get().get(Friend.class, new FriendKey(name));
    }
}
