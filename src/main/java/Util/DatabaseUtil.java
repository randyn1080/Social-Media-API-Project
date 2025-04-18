package Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for common database operations
 */
public class DatabaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);
    
    /**
     * Safely closes a JDBC resource (Connection, Statement, ResultSet)
     * @param resource The AutoCloseable resource to close
     */
    public static void closeResource(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                logger.warn("Error closing resource: {}", e.getMessage());
            }
        }
    }
    
    // Private constructor to prevent instantiation
    private DatabaseUtil() {}
}
