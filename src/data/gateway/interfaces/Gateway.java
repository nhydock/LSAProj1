package data.gateway.interfaces;

import java.sql.ResultSet;

import data.containers.DataContainer;
import data.keys.Key;

/**
 * Interface for generic row/table gateways
 * 
 * @author nhydock
 *
 * @param
 */
public abstract class Gateway {
    abstract public ResultSet find(Key key);

    abstract public void update(DataContainer data);

    abstract public ResultSet insert(DataContainer data);

    abstract public boolean delete(Key object);
}
