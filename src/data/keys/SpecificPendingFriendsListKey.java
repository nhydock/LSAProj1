package data.keys;

public class SpecificPendingFriendsListKey implements Key {
    
    public final long userID;
    public final long friendID;

    /**
     * @param id
     *            - person we want the list of friends from
     */
    public SpecificPendingFriendsListKey(long userID, long friendID) {
        this.userID = userID;
        this.friendID = friendID;
    }

    public boolean equals(Object obj) {
        // equals identity
        if (obj == this)
            return true;
        // not null
        if (obj == null)
            return false;
        // is a person key
        if (!(obj instanceof SpecificPendingFriendsListKey))
            return false;
        SpecificPendingFriendsListKey key = (SpecificPendingFriendsListKey) obj;

        // value matching
        boolean eq = true;
        eq = eq && (key.userID == this.userID);
        eq = eq && (key.friendID == this.friendID);
        return eq;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (userID ^ (userID >>> 32));
        result = prime * result + (int) (friendID ^ (friendID >>> 32));
        return result;
    }
}
