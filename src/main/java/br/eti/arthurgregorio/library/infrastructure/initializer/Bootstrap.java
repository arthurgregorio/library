package br.eti.arthurgregorio.library.infrastructure.initializer;

import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

/**
 * This class is the start point of the basic configurations for the application
 * 
 * Through here whe configure the default user and all the data that need to be initialized in the database before the
 * first start
 * 
 * This class is a EJB and runs on every start of the environment
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class Bootstrap {

    @Inject
    private Logger logger;

    @Inject
    private EnvironmentInitializer initializer;

    /**
     * Initialize and do the job
     */
    @PostConstruct
    protected void initialize() {
        this.logger.info("Initializing application, this may take a few minutes...");
        this.initializer.initialize();
        this.logger.info("Initialization finished!");
   }
}
