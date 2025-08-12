package wu.huang.hflex.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wu.huang.hflex.config.param.DatasourceParams;


@Configuration
public class DatasourceConfiguration {

    @Bean("dataSource")
    public HikariDataSource dataSource(DatasourceParams datasourceParams) {
        return this.buildHikari(datasourceParams);
    }

    private HikariDataSource buildHikari(DatasourceParams params) {
        var ds = new HikariDataSource();

        // ===== Basic connection info =====
        if (params.getDriverClassName() != null) ds.setDriverClassName(params.getDriverClassName());
        if (params.getUrl() != null) ds.setJdbcUrl(params.getUrl());
        if (params.getUsername() != null) ds.setUsername(params.getUsername());
        if (params.getPassword() != null) ds.setPassword(params.getPassword());

        DatasourceParams.HikariProps h = params.getHikari();
        if (h == null) return ds; // No Hikari settings provided

        // ===== Pool size =====
        if (h.getMaximumPoolSize() != null) ds.setMaximumPoolSize(h.getMaximumPoolSize());
        if (h.getMinimumIdle() != null) ds.setMinimumIdle(h.getMinimumIdle());
        if (h.getInitializationFailTimeout() != null) ds.setInitializationFailTimeout(h.getInitializationFailTimeout());

        // ===== Timeouts & lifetime =====
        if (h.getConnectionTimeout() != null) ds.setConnectionTimeout(h.getConnectionTimeout());
        if (h.getValidationTimeout() != null) ds.setValidationTimeout(h.getValidationTimeout());
        if (h.getIdleTimeout() != null) ds.setIdleTimeout(h.getIdleTimeout());
        if (h.getMaxLifetime() != null) ds.setMaxLifetime(h.getMaxLifetime());
        if (h.getKeepaliveTime() != null) ds.setKeepaliveTime(h.getKeepaliveTime());
        if (h.getLeakDetectionThreshold() != null) ds.setLeakDetectionThreshold(h.getLeakDetectionThreshold());

        // ===== Connection info =====
        if (h.getPoolName() != null) ds.setPoolName(h.getPoolName());
        if (h.getCatalog() != null) ds.setCatalog(h.getCatalog());
        if (h.getSchema() != null) ds.setSchema(h.getSchema());
        if (h.getConnectionInitSql() != null) ds.setConnectionInitSql(h.getConnectionInitSql());
        if (h.getConnectionTestQuery() != null) ds.setConnectionTestQuery(h.getConnectionTestQuery());

        // ===== Transaction & auto commit =====
        if (h.getAutoCommit() != null) ds.setAutoCommit(h.getAutoCommit());
        if (h.getReadOnly() != null) ds.setReadOnly(h.getReadOnly());
        if (h.getTransactionIsolation() != null) ds.setTransactionIsolation(h.getTransactionIsolation());

        // ===== Driver & JDBC class settings =====
        if (h.getDataSourceClassName() != null) ds.setDataSourceClassName(h.getDataSourceClassName());
        if (h.getJdbcUrl() != null) ds.setJdbcUrl(h.getJdbcUrl());
        if (h.getUsername() != null) ds.setUsername(h.getUsername());
        if (h.getPassword() != null) ds.setPassword(h.getPassword());

        // ===== JNDI =====
        if (h.getDataSourceJndi() != null) ds.addDataSourceProperty("dataSourceJNDI", h.getDataSourceJndi());

        // ===== JMX =====
        if (h.getRegisterMbeans() != null) ds.setRegisterMbeans(h.getRegisterMbeans());

        // ===== Misc =====
        if (h.getIsolateInternalQueries() != null) ds.setIsolateInternalQueries(h.getIsolateInternalQueries());
        if (h.getAllowPoolSuspension() != null) ds.setAllowPoolSuspension(h.getAllowPoolSuspension());

        // Note: ThreadFactory & ScheduledExecutorService can be set via setters but often not needed unless customizing threads.

        return ds;
    }
}
