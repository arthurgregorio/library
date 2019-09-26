package br.eti.arthurgregorio.library.application.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * The {@link EntityManager} producer of the application
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 14/12/2017
 */
@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    /**
     * @return the producer of the {@link EntityManager}
     */
    @Produces
    @RequestScoped
    EntityManager produce() {
        return this.entityManagerFactory.createEntityManager();
    }

    /**
     * The dispose method to close the instances os the {@link EntityManager}
     *
     * @param entityManager the entity manager to be closed
     */
    void close(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
