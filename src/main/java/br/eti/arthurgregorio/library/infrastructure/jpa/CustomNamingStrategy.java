package br.eti.arthurgregorio.library.infrastructure.jpa;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.ImplicitUniqueKeyNameSource;

/**
 * Hibernate custom {@link ImplicitNamingStrategyJpaCompliantImpl} to make the FK and UK names more readable for humans
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.1.0, 26/12/2018
 */
public class CustomNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    /**
     * {@inheritDoc}
     *
     * @param source
     * @return
     */
    @Override
    public Identifier determineForeignKeyName(ImplicitForeignKeyNameSource source) {
        return this.toIdentifier("fk_" + source.getTableName().getCanonicalName()
                + "_" + source.getReferencedTableName().getCanonicalName(), source.getBuildingContext());
    }

    /**
     * {@inheritDoc}
     *
     * @implNote this is not working due to this bug in Hibernate implementation
     *
     * @see <a href="https://hibernate.atlassian.net/browse/HHH-11586">HHH-11586</>
     *
     * @param source
     * @return
     */
    @Override
    public Identifier determineUniqueKeyName(ImplicitUniqueKeyNameSource source) {
        return this.toIdentifier("uk_" + source.getTableName().getCanonicalName(), source.getBuildingContext());
    }
}
