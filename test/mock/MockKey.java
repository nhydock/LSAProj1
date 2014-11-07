package mock;

import data.keys.Key;

public class MockKey implements Key {

    public final String name;
    
    public MockKey(String name)
    {
        this.name = name;
    }
    
    public boolean equals(Object obj) {
        // equals identity
        if (obj == this)
            return true;
        // not null
        if (obj == null)
            return false;
        // is a person key
        if (!(obj instanceof MockKey))
            return false;
        MockKey key = (MockKey) obj;

        // value matching
        boolean eq = true;
        eq = eq && (key.name.equals(this.name));
        return eq;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        return result;
    }
}
