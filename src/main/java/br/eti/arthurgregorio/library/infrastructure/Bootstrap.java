package br.eti.arthurgregorio.library.infrastructure;

import static com.google.common.base.Preconditions.checkNotNull;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.slf4j.Logger;

/**
 * This class is the start point of the basic configurations for the application
 * 
 * Through here whe configure the default user and all the data that need to be
 * initialized in the database before the first start
 * 
 * This class is a EJB and runs on every start of the environment
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 27/02/2018
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class Bootstrap {

    @Inject
    private Logger logger;

    @Resource(name = "java:/datasources/LibraryDS")
    private DataSource dataSource;

    /**
     * Initialize and do the job
     */
    @PostConstruct
    protected void initialize() {
        this.logger.info("Bootstraping application....");
        this.checkDatabase();
        this.logger.info("Bootstraping finished...");
    }
    
    /**
     * 
     */
    private void checkDatabase() {
        
        checkNotNull(this.dataSource, "No datasource found for migrations");
 
        final Flyway flyway = new Flyway();
        
        flyway.setLocations("db/migration");
        flyway.setBaselineOnMigrate(true);
        
        flyway.setDataSource(this.dataSource);
        
        final MigrationInfo migrationInfo = flyway.info().current();
 
        if (migrationInfo == null) {
            this.logger.info("No existing database at the actual datasource");
        }
        else {
            this.logger.info("Found a database with the version: {}", migrationInfo.getVersion() 
                    + " : " + migrationInfo.getDescription());
        }
        
        flyway.migrate();

        this.logger.info("Successfully migrated to database version: {}", 
                flyway.info().current().getVersion());
    }
}
