package br.eti.arthurgregorio.library.infrastructure.shiro;

import br.eti.arthurgregorio.library.domain.services.AccountService;
import br.eti.arthurgregorio.shiroee.auth.AuthenticationMechanism;
import br.eti.arthurgregorio.shiroee.auth.DatabaseAuthenticationMechanism;
import br.eti.arthurgregorio.shiroee.config.RealmConfiguration;
import br.eti.arthurgregorio.shiroee.config.jdbc.UserDetails;
import br.eti.arthurgregorio.shiroee.config.ldap.LdapUserProvider;
import br.eti.arthurgregorio.shiroee.realm.JdbcSecurityRealm;
import br.eti.arthurgregorio.shiroee.realm.LdapSecurityRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The main security configuration of this application.
 *
 * With this class we configure all the realms to use and the data/cache providers for store or provide
 * authorization/authentication information
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/03/2018
 */
@ApplicationScoped
public class SecurityRealmConfiguration implements RealmConfiguration {

    private Set<Realm> realms;
    private CacheManager cacheManager;

    private AuthenticationMechanism<UserDetails> mechanism;

    @Inject
    private LdapUserProvider ldapUserProvider;
    @Inject
    private AccountService userAccountService;

    /**
     * Initialize the configuration with the default values
     */
    @PostConstruct
    protected void initialize() {

        this.realms = new HashSet<>();
        this.cacheManager = new EhCacheManager();

        this.mechanism = new DatabaseAuthenticationMechanism(this.userAccountService);

        this.configureJdbcRealm();
        this.configureLdapRealm();
    }

    /**
     * @return all the realms to use with this application
     */
    @Override
    public Set<Realm> configureRealms() {
        return Collections.unmodifiableSet(this.realms);
    }

    /**
     * Configure the JDBC (local database) authentication realm
     */
    private void configureJdbcRealm() {

        final JdbcSecurityRealm realm = new JdbcSecurityRealm(this.mechanism);

        realm.setCachingEnabled(true);
        realm.setCacheManager(this.cacheManager);

        this.realms.add(realm);
    }

    /**
     * Configure the LDAP authentication realm
     */
    private void configureLdapRealm() {

        final LdapSecurityRealm realm = new LdapSecurityRealm(this.ldapUserProvider, this.mechanism);

        realm.setCachingEnabled(true);
        realm.setCacheManager(this.cacheManager);

        this.realms.add(realm);
    }
}
