# Library

A simple demo application to show how to work with the default technologies of JavaEE version 7.

This conceptual architecture makes use of the following frameworks:

The core technologies:

- Hibernate 5 for JPA 2.1
- Weld 2.4 for CDI 1.1
- Mojarra for JSF 2.2 + Primefaces 6.2 + AdminLTE 2.4 + Bootstrap 3
- Apache Delta Spike Data Module for database querying and repositories functionality
- Apache Shiro 1.4 through [ShiroEE](https://github.com/arthurgregorio/shiro-ee) for Security with LDAP/AD and database authentication support
- Maven for building and dependency management

The extras:

- Database audit with Hibernate Envers 5
- Hiberante Validator for Bean Validation
- Omnifaces and PrimefacesExt for JSF utilities
- Jackson for JSON support
- Lombok, Google Guava and Coomons Lang for class level utilities
- Mustache for e-mail templating 

The demo utilizes custom implementation of [AdminLTE](https://adminlte.io/) integrated with Boostrap 3 and Primefaces for a better UI with modern features and mobile support.

## How to: configure

First of all, you will need to download the latest version of Wildfly application server. This is the homologated version, maybe with a little bit of changes ~~or no~~ you can run the this on Payara, Glassfish or any other JEE 7 server.

Download Wildfly [here](http://wildfly.org/downloads/) and configure the datasource for the application by editing the ``` standalone.xml``` or ```standalone-full.xml``` (you will know which one to change) to add this lines to the datasource section of the file:

```xml
<datasource jta="true" jndi-name="java:/datasources/LibraryDS" pool-name="LibraryDS" enabled="true" use-ccm="false">
    <connection-url>jdbc:postgresql://localhost:5432/library</connection-url>
    <driver-class>org.postgresql.Driver</driver-class>
    <driver>postgresql</driver>
    <pool>
        <min-pool-size>10</min-pool-size>
        <initial-pool-size>5</initial-pool-size>
        <max-pool-size>30</max-pool-size>
        <prefill>true</prefill>
        <flush-strategy>AllInvalidIdleConnections</flush-strategy>
    </pool>
    <security>
        <user-name>sa_library</user-name>
        <password>sa_library</password>
    </security>
    <validation>
        <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
        <check-valid-connection-sql>SELECT 1</check-valid-connection-sql>
        <background-validation>true</background-validation>
        <use-fast-fail>true</use-fast-fail>
        <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter"/>
    </validation>
</datasource>
```

And these lines to the mail subsystem (search for ```mail-session```) to enable the demo sending e-mail messages:

```xml
<mail-session name="my-email" debug="true" jndi-name="java:/mail/library" from="no-reply@my-email-account.com">
    <smtp-server outbound-socket-binding-ref="my-email-socket" username="my@email-account.com" password="my-secret"/>
</mail-session>
```

And the e-mail socket to the ```socket-binding-group``` at the end of the file:

```xml
<outbound-socket-binding name="my-email-socket">
    <remote-destination host="my-email-server.com" port="587"/>
</outbound-socket-binding>
```

After this, create the database on you local instance of PostgreSQL 9+ to match the Wildfly configurations and enable the deployment of the application:

```sql
-- the user
CREATE USER sa_library WITH
  LOGIN
  NOSUPERUSER
  INHERIT
  NOCREATEDB
  NOCREATEROLE
  NOREPLICATION
  ENCRYPTED PASSWORD 'sa_library';

-- the database
CREATE DATABASE library
    WITH 
    OWNER = sa_library
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    
-- the schemes
CREATE SCHEMA audit
    AUTHORIZATION sa_library;
CREATE SCHEMA security
    AUTHORIZATION sa_library;
CREATE SCHEMA security_audit
    AUTHORIZATION sa_library;
```

## How to: run on IDE

Just import the maven project, and deploy to you already configured Wildfly server. Remember to first configure the infrastructure.

## How to: run by hands

Build the project. On the root folder run: 

```shell
mvn clean package
```

This will trigger the maven build to work with the default profile, *ALPHA*, with the following configurations available on the *pom.xml* file:

```xml
<profile>
    <id>ALPHA</id>
    <activation>
        <activeByDefault>true</activeByDefault>
    </activation>
    <properties>
        <application.version>
            ${project.version}-ALPHA
        </application.version>
        <skip.tests>false</skip.tests>
        <jsf.stage>Development</jsf.stage>
        <ldap.enabled>false</ldap.enabled>
        <ldap.url>ldap://localhost</ldap.url>
        <ldap.baseDn>OU=Usuarios,DC=arthurgregorio,DC=eti,DC=br</ldap.baseDn>
        <ldap.user>CN=usuaribind,OU=Aplicacoes,DC=arthurgregorio,DC=eti,DC=br</ldap.user>
        <ldap.password>minha-senha</ldap.password>
    </properties>
</profile>
```

The configuration also have other profiles for you to configure according to your need: 

- *BETA* for beta releases
- *RC* for release candidate releases 
- *RELEASE* for the final, production ready releases

To use a specific profile, run the maven build with:

```shell
mvn -P(the-profile) clean package 
```

After the build, open the wildfly admin console on the web browser and in the deployments section, upload the war file created by the build in the target folder (named *library-1.0.0-(selected-profile)*) inside the project and access it on the default URL: https://localhost:8080/, and you're done! Enjoy the demo.
