package dataaccess;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * DBUtil class that creates a connection with the database to be used by the
 * data brokers.
 *
 * @author Thomas Skiffington
 */
public class DBUtil {

    /**
     * Creates the database connection
     */
    private static final EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("Schedule-MDPU");

    /**
     * getEmFactory getter for the database connection
     *
     * @return emf
     */
    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}
