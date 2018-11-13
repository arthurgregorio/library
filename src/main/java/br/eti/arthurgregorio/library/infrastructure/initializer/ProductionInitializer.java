package br.eti.arthurgregorio.library.infrastructure.initializer;

import org.apache.deltaspike.core.api.exclude.Exclude;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.deltaspike.core.api.projectstage.ProjectStage.Development;

/**
 * The Production {@link EnvironmentInitializer}
 * 
 * This one use database migrations strategy provided by FlywayDB
 *
 * @author Arthur Gregorio
 *
 * @version 1.1.0
 * @since 1.3.1, 20/08/2018
 */
@RequestScoped
@Exclude(ifProjectStage = Development.class)
public class ProductionInitializer implements EnvironmentInitializer {

    @Inject
    private Logger logger;
    
    @Resource(lookup = "java:/datasources/LibraryDS")
    private DataSource dataSource;
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void initialize() {
        
        this.logger.warn("Initializing application in production mode");

        checkNotNull(this.dataSource, "No datasource found for migrations");
 
        final Flyway flyway = Flyway.configure().baselineOnMigrate(true)
                .baselineVersion("0")
                .dataSource(this.dataSource)
                .locations("db/migrations").load();
        
        final MigrationInfo migrationInfo = flyway.info().current();
 
        if (migrationInfo == null) {
            this.logger.info("No existing database at the actual datasource");
        }
        else {
            this.logger.info("Current version: {}", migrationInfo.getVersion() + " : " + migrationInfo.getDescription());
        }
        
        flyway.migrate();

        this.logger.info("Successfully migrated to version: {}", flyway.info().current().getVersion());
    }
}
