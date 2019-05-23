package br.eti.arthurgregorio.library.infrastructure.initializer;

/**
 * Interface to define the initialization task structure
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.1.0, 22/05/2019
 */
public interface InitializationTask {

    /**
     * Call this method to run this initialization task
     */
    void run();

    /**
     * Use this method to define a default priority to this task
     *
     * @return the int value representing the priority number, default is zero (highest priority)
     */
    default int getPriority() {
        return 0;
    }
}