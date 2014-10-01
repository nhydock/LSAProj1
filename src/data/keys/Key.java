package data.keys;

import domain.model.DomainModelObject;

/**
 * Interface used to define the primary key used in fetching
 * objects through the gateways
 * 
 * @author nhydock
 */
public interface Key<T extends DomainModelObject> {
	@Override
	public boolean equals(Object key);
	
	@Override
	public int hashCode();
}
