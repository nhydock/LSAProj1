package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import data.gateway.interfaces.Gateway;
import domain.IdentityMap;
import domain.UnitOfWork;
import domain.mappers.DataMapper;
import domain.mappers.UserMapper;
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
        String pass = "lsagroupt5";
        System.out.println("Attempting to open database " + db + "...");
        try {
            String connectFormat = "jdbc:mysql:%s?user=%s&password=%s";
            
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(String.format(connectFormat, host, user, pass));
        } catch (Exception ex) {
            // if not successful, quit
            System.out.println("Cannot open database -- make sure MySQL JDBC is configured properly.");
            connection = null;
        }
        
        mappers = new HashMap<Class<? extends DataMapper<?>>, DataMapper<?>>();
        identityMaps = new HashMap<Class<? extends DomainModelObject>, IdentityMap<?>>();
        unitOfWork = new UnitOfWork();
        
        addMappers();
    }
    
    private void addMappers() {
        mappers.put(UserMapper.class, new UserMapper());
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
        session.set(new Session());
    }
    
    public static void setUser(User user) {
        session.get().user = user;
    }
}
