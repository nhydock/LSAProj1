package data.keys;

/**
 * Interface used to define the primary key used in fetching
 * objects through the gateways
 * 
 * @author nhydock
 */
public interface Key {
	@Override
	public boolean equals(Object key);
	
	@Override
	public int hashCode();
}
