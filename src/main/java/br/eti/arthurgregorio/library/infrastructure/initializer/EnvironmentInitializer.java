package br.eti.arthurgregorio.library.infrastructure.initializer;

/**
 * Abstraction for initializing the environment, this class is a contract to provide a simple way to inject any
 * initialization strategy in the {@link Bootstrap} phase of the application
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
public interface EnvironmentInitializer {

    /**
     * Call the initialization process
     */
    void initialize();
}
