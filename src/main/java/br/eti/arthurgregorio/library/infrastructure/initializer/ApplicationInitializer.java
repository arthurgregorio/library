package br.eti.arthurgregorio.library.infrastructure.initializer;

import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Comparator;

/**
 * Default initializer for the application. This class will automatic discovery for all tasks to be performed at
 * initialization time
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.1.0, 22/05/2019
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class ApplicationInitializer {

    @Inject
    private Logger logger;

    @Inject
    private ProjectStage projectStage;

    @Any
    @Inject
    private Instance<InitializationTask> tasks;

    /**
     * Call the tasks and log the execution status
     */
    @PostConstruct
    public void initialize() {
        this.logger.info("The application is now preparing all initialization tasks...");
        this.tasks.stream()
                .sorted(Comparator.comparingInt(InitializationTask::getPriority))
                .forEach(InitializationTask::run);
        this.logger.info("{} initialization tasks performed and the applications is now running in {} mode",
                this.tasks.stream().count(), this.projectStage);
    }
}