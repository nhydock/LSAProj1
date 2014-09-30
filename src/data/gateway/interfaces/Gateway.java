package data.gateway.interfaces;

import data.keys.Key;

/**
 * Interface for generic row/table gateways
 * @author nhydock
 *
 * @param
 */
public interface Gateway<T> {
	public T find(Key key);
	public void update(T object);
	public void delete(T object);
	public void persist(T object);
}
