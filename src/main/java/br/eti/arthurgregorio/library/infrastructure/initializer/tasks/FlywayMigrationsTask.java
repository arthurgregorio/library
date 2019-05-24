package br.eti.arthurgregorio.library.infrastructure.initializer.tasks;

import br.eti.arthurgregorio.library.infrastructure.initializer.InitializationTask;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationInfo;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.sql.DataSource;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.deltaspike.core.api.projectstage.ProjectStage.Development;

/**
 * {@link InitializationTask} for production environments, this one call {@link Flyway} to make the migrations at the
 * database
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.1.0, 22/05/2019
 */
@Dependent
@Exclude(ifProjectStage = Development.class)
public class FlywayMigrationsTask implements InitializationTask {

    @Inject
    private Logger logger;

    @Resource(lookup = "java:/datasources/LibraryDS")
    private DataSource dataSource;

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {

        checkNotNull(this.dataSource, "No datasource found for migrations");

        final Flyway flyway = Flyway.configure()
                .dataSource(this.dataSource)
                .locations("db/migrations")
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .sqlMigrationPrefix("")
                .load();

        final MigrationInfo migrationInfo = flyway.info().current();

        if (migrationInfo == null) {
            this.logger.info("No existing database at the actual datasource");
        } else {
            this.logger.info("Current version: {}", migrationInfo.getVersion() + " : " + migrationInfo.getDescription());
        }

        try {
            flyway.migrate();
            this.logger.info("Successfully migrated to version: {}", flyway.info().current().getVersion());
        } catch (FlywayException ex) {
            this.logger.info("Flyway migrations failed!", ex);
        }
    }
}