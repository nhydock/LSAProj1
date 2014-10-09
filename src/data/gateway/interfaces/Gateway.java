package data.gateway.interfaces;

import java.sql.Connection;
import java.sql.DriverManager;

import data.keys.Key;
import domain.model.DomainModelObject;

/**
 * Interface for generic row/table gateways
 * 
 * @author nhydock
 *
 * @param
 */
public abstract class Gateway<T extends DomainModelObject> {
    abstract public DomainModelObject find(Key<?> key);

    abstract public void update(DomainModelObject object);

    abstract public Result<?> insert(DomainModelObject object);

    abstract public Key<?> delete(DomainModelObject object);
    
    abstract public Class<T> getType();
    
    private static final ThreadLocal<Connection> dbConnection = new ThreadLocal<Connection>() {
        public Connection initialValue() {
            // attempt to connect to the ODBC database
            String db = "myDatabase"; // ODBC database name
            System.out.println("Attempting to open database " + db + "...");
            Connection con = null;
            try {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                con = DriverManager.getConnection("jdbc:odbc:" + db);
                return con;
            } catch (Exception ex) {
                // if not successful, quit
                System.out
                        .println("Cannot open database -- make sure ODBC is configured properly.");
                System.exit(1);
            }
            return null;
        }
    };

    /**
     * @return get the current thread's connection to the database
     */
    public static Connection getConnection() {
        return dbConnection.get();
    }

    /**
     * Result given upon inserting a value. This is necessary so the Data Mapper
     * can map the generated object with proper id into an identity map.
     * 
     * @author nhydock
     *
     * @param <T>
     * @param <Key>
     */
    public static final class Result<T extends DomainModelObject> {
        public final T object;
        public final Key<T> key;

        public Result(T obj, Key<T> key) {
            this.object = obj;
            this.key = key;
        }
    }
}
