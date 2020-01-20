package br.eti.arthurgregorio.library.infrastructure.shiro.ldap;

import br.eti.arthurgregorio.library.domain.exception.BusinessLogicException;
import br.eti.arthurgregorio.shiroee.config.ConfigurationFactory;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple LDAP/AD repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.3.0, 20/01/2020
 */
@ApplicationScoped
public class LdapRepository {

    private String baseDN;
    private JndiLdapContextFactory factory;

    /**
     * Initializer method for this repository
     */
    @PostConstruct
    protected void initialize() {

        final PropertiesConfiguration configuration = ConfigurationFactory.get();

        this.factory = new JndiLdapContextFactory();

        this.baseDN = configuration.getString("ldap.baseDn");

        this.factory.setUrl(configuration.getString("ldap.url"));
        this.factory.setSystemUsername(configuration.getString("ldap.user"));
        this.factory.setSystemPassword(configuration.getString("ldap.password"));

        this.factory.setPoolingEnabled(true);
    }

    /**
     * Generic query method with automatic mapping of the objects against the attributes, for that a
     * {@link LdapObjectMapper} should be provided as parameter
     *
     * @param searchOption {@link LdapSearchOption} to be used
     * @param filter to be applied
     * @param mapper the {@link LdapObjectMapper} to map the attributes of the result
     * @param clazz type of the resulting value
     * @param <T> the generic type that this method will work on
     * @return a {@link List} of objects typed by the type defined by clazz attribute
     */
    public <T> List<T> listBy(LdapSearchOption searchOption, String filter, LdapObjectMapper<T> mapper, Class<T> clazz) {
        final List<Attributes> attributes = this.listBy(searchOption, filter);
        return mapper.map(attributes, clazz);
    }

    /**
     * Simple version of {@link #listBy(LdapSearchOption, String, LdapObjectMapper, Class)} but this one will not map
     * the return attributes and let you do that
     *
     * @param searchOption {@link LdapSearchOption} to be used
     * @param filter to be applied
     * @param parameters to be applied to the filter
     * @return a {@link List} of {@link Attributes} found
     */
    public List<Attributes> listBy(LdapSearchOption searchOption, String filter, Object... parameters) {
        return this.listBy(searchOption.build(filter), parameters);
    }

    /**
     * Simple version of {@link #listBy(LdapSearchOption, String, Object...)}  but this one will not map the return
     * attributes and let you do that and will not take an {@link LdapSearchOption} as template for search
     *
     * @param filter to be applied
     * @param parameters to be applied to the filter
     * @return a {@link List} of {@link Attributes} found
     */
    public List<Attributes> listBy(String filter, Object... parameters) {

        final SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        final List<Attributes> attributes = new ArrayList<>();

        try {
            final LdapContext context = this.factory.getSystemLdapContext();

            final NamingEnumeration<SearchResult> answer = context.search(this.baseDN, filter, parameters, searchControls);

            while (answer.hasMoreElements()) {
                final SearchResult searchResult = answer.nextElement();
                attributes.add(searchResult.getAttributes());
            }
        } catch (NamingException ex) {
            throw new BusinessLogicException("error.ldap.cant-search-for-users", ex);
        }
        return attributes;
    }
}