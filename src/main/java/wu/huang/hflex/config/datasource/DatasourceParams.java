package wu.huang.hflex.config.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.datasource.db")
public class DatasourceParams {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private HikariProps hikari = new HikariProps();

    @Data
    public static class HikariProps {
        // Pool size settings
        private Integer maximumPoolSize;           // Maximum number of connections in the pool
        private Integer minimumIdle;               // Minimum number of idle connections in the pool
        private Integer initializationFailTimeout; // Initialization fail timeout (ms, <0 = fail immediately, 0 = do not fail)

        // Timeouts & lifetime
        private Long connectionTimeout;            // Maximum wait time to get a connection from the pool (ms)
        private Long validationTimeout;            // Timeout for validation query (ms)
        private Long idleTimeout;                  // Maximum time a connection is allowed to sit idle in the pool (ms)
        private Long maxLifetime;                   // Maximum lifetime of a connection in the pool (ms)
        private Long keepaliveTime;                 // Keep-alive interval to prevent connections from being closed by the DB (ms)
        private Long leakDetectionThreshold;        // Threshold to detect connection leaks (ms, 0 = disabled)

        // Connection information
        private String poolName;                    // Pool name (for debugging/monitoring purposes)
        private String catalog;                     // Default catalog for connections
        private String schema;                      // Default schema for connections
        private String connectionInitSql;           // SQL to execute when a new connection is created
        private String connectionTestQuery;         // Query to validate connections (if JDBC4 isValid is not used)

        // Transaction & auto-commit settings
        private Boolean autoCommit;                 // Default auto-commit behavior for connections
        private Boolean readOnly;                   // Whether connections are read-only
        private String transactionIsolation;        // Transaction isolation level (e.g., "TRANSACTION_READ_COMMITTED")

        // Driver & classloader settings
        private String dataSourceClassName;         // Fully qualified DataSource class name (if using DataSource class)
        private String driverClassName;             // JDBC driver class name
        private String jdbcUrl;                     // JDBC connection URL (if not using DataSource class)
        private String username;                    // Database username
        private String password;                    // Database password

        // DataSource & properties
        private String dataSourceJndi;              // JNDI name if looking up DataSource from an application server
        // Example: Map<String, String> dataSourceProperties; // Additional DataSource properties

        // JMX & metrics
        private Boolean registerMbeans;             // Enable/disable JMX MBeans for monitoring

        // Thread factory / executor
        private String threadFactoryClassName;      // Custom ThreadFactory class name
        private String scheduledExecutorServiceClassName; // Custom ScheduledExecutorService class name

        // Misc
        private Boolean isolateInternalQueries;     // Whether to isolate internal queries
        private Boolean allowPoolSuspension;        // Whether to allow pool suspension
    }

}
