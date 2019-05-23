package br.eti.arthurgregorio.library.infrastructure.initializer;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

/**
 * Transactional implementation of the {@link InitializationTask}, this class use a template method to perform the
 * initialization inside an {@link UserTransaction}
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.1.0, 22/05/2019
 */
public abstract class TransactionalInitializationTask implements InitializationTask {

    @Resource
    private UserTransaction transaction;

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            this.transaction.begin();
            this.runInsideTransaction();
            this.transaction.commit();
        } catch (Exception commitException) {
            try {
                this.transaction.rollback();
            } catch (Exception rollbackException) {
                throw new EJBException(rollbackException);
            }
            throw new EJBException(commitException);
        }
    }

    /**
     * Use this method to run the task in a {@link Transactional} environment
     */
    public abstract void runInsideTransaction();
}