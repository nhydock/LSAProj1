package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import data.gateway.*;
import data.gateway.interfaces.Gateway;
import domain.IdentityMap;
import domain.UnitOfWork;
import domain.mappers.*;
import domain.model.DomainModelObject;
import domain.model.User;

public class Session {
    
    Connection connection;
    HashMap<Class<? extends DataMapper<?>>, DataMapper<?>> mappers;
    HashMap<Class<? extends Gateway>, Gateway> gateways;
    HashMap<Class<? extends DomainModelObject>, IdentityMap<?>> identityMaps;
    UnitOfWork unitOfWork;
    
    //logged in user of the session
    User user;
    
    private Session()
    {
        // attempt to connect to the ODBC database
        String db = "fitness5"; // ODBC database name
        String host = "lsagroup5.cbzhjl6tpflt.us-east-1.rds.amazonaws.com";
        String user = "lsagroup5";
        String pass = "lsagroup5";
        System.out.println("Attempting to open database " + db + "...");
        try {
            String connectFormat = "jdbc:mysql://%s/%s?user=%s&password=%s";
            String connectURL = String.format(connectFormat, host, db, user, pass);
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectURL);
        } catch (Exception ex) {
            // if not successful, quit
            System.err.println("Cannot open database -- make sure MySQL JDBC is configured properly.");
            connection = null;
        }
        
        mappers = new HashMap<Class<? extends DataMapper<?>>, DataMapper<?>>();
        gateways = new HashMap<Class<? extends Gateway>, Gateway>();
        identityMaps = new HashMap<Class<? extends DomainModelObject>, IdentityMap<?>>();
        unitOfWork = new UnitOfWork();
        
        addMappers();
        addGateways();
    }
    
    private void addMappers() {
        mappers.put(UserMapper.class, new UserMapper());
        mappers.put(FriendsMapper.class, new FriendsMapper());
        mappers.put(PendingFriendsMapper.class, new PendingFriendsMapper());
    }
    
    private void addGateways() {
        gateways.put(FriendGateway.class, new FriendGateway());
        gateways.put(PendingFriendsGateway.class, new PendingFriendsGateway());
        gateways.put(UserGateway.class, new UserGateway());
        
    }

    private static final ThreadLocal<Session> session = new ThreadLocal<Session>() {
        public Session initialValue() {
            return new Session();
        }
    };

    /**
     * @return get the current thread's connection to the database
     */
    private static Session get() {
        return session.get();
    }
    
    public static Connection getConnection()
    {
        return Session.get().connection;
    }
    
    public static DataMapper<?> getMapper(Class<?> cls)
    {
        if (DomainModelObject.class.isAssignableFrom(cls))
        {
            for (DataMapper<?> mapper : Session.get().mappers.values())
            {
                if (mapper.getType() == cls)
                {
                    return mapper;
                }
            }
            
            //try again looking for super types
            for (DataMapper<?> mapper : Session.get().mappers.values())
            {
                if (mapper.getType().isAssignableFrom(cls))
                {
                    return mapper;
                }
            }
            
            throw (new NullPointerException("No mapper for type " + cls.getName() + " exists"));
        }
        else if (DataMapper.class.isAssignableFrom(cls))
        {
            return Session.get().mappers.get(cls);
        }
        return null;
    }
    
    public static UnitOfWork getUnitOfWork()
    {
        return Session.get().unitOfWork;
    }

    public static <T extends Gateway> T getGateway(Class<T> cls) {
        if (!Session.get().gateways.containsKey(cls))
        {
            try {
                T gate = cls.newInstance();
                Session.get().gateways.put(cls, gate);
                return gate;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return cls.cast(Session.get().gateways.get(cls));
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends DomainModelObject> IdentityMap<T> getIdentityMap(Class<T> cls) {
        IdentityMap<T> map = (IdentityMap<T>)Session.get().identityMaps.get(cls);
        if (map == null)
        {
            map = new IdentityMap<T>(cls);
            Session.get().identityMaps.put(cls, map);
        }
        return map;
    }

    /**
     * Destroy the current session by replacing it with a new one
     */
    public static void kill() {
    	session.get().unitOfWork.commit();
    	try {
			session.get().connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
        session.set(new Session());
    }
    
    public static void setUser(User user) {
        session.get().user = user;
    }
}
