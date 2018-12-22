# Library

A simple demo application to show how to work with the default technologies of JavaEE version 8. This conceptual architecture makes use of the following technologies:

- Java 11 and Java EE 8 with Wildfly 14+ and PostgreSQL 10+
- Hibernate 5 for JPA 2.2
- Weld 3.0 for CDI 2.0
- Mojarra for JSF 2.3 + Primefaces 6.2 + AdminLTE 2.4 + Bootstrap 3
- Apache Delta Spike JPA and Data Module for database querying and repositories management
- Apache Shiro 1.4 through [ShiroEE](https://github.com/arthurgregorio/shiro-ee) for Security with LDAP/AD and database authentication support
- Maven for dependency management and build  
- Flyway 5.2 for database migrations
- Database audit with Hibernate Envers 5
- Hibernate Validator for Bean Validation
- Omnifaces 3 and PrimefacesExt for JSF utilities
- Jackson for JSON support 
- Lombok, Google Guava and Apache Commons-lang for class level utilities
- Mustache for e-mail templating 
- Webservices with JAX-RS (RestEasy) 

The demo makes use of a custom implementation of [AdminLTE](https://adminlte.io/) integrated with Boostrap 3 and Primefaces for a better UI, modern features and mobile support.

Also, inside the application you can find (already functional) a simple CRUD of Users and User Groups with permission based authentication and LDAP/AD integration with local bind accounts, all of this provided by ShiroEE.

It's not much say, that if you want a **production ready architecture**, this is the project you are looking for.

## How to: configure

First of all, you will need to download the latest version of Wildfly application server. This is the homologated version, maybe, with a little bit of changes ~~or no~~ you can run this on Payara, Glassfish or any other JEE 7+ server.

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

> **Quick note**: the datasource will not work if you don't have the PostgreSQL driver enabled in the wildfly modules. To do this, follow [this blog post](https://bok.stenusys.com/index.php/2018/02/12/how_to_setup_postgresql_datasource_with_wildfly/).


If you want to send e-mails, these lines should be added to the mail subsystem (search for ```mail-session```):

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

After this, create the database on you local instance of PostgreSQL 10+ to match the Wildfly configurations and enable the deployment of the application:

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
 
 -- the databse
 CREATE DATABASE library
    WITH 
    OWNER = sa_library
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
```

The tables and the initial data (default user, group and authorizations) will be created by Flyway with the migrations strategy. If you want to run this application
in development mode, Hibernate will create the tables, but you should create the schemes by hands on PgAdmin or another similar software:

```sql
CREATE SCHEMA configuration AUTHORIZATION sa_library;
CREATE SCHEMA registration AUTHORIZATION sa_library;
CREATE SCHEMA configuration_audit AUTHORIZATION sa_library;
CREATE SCHEMA registration_audit AUTHORIZATION sa_library;
```

## How to: run on IDE

Just import the maven project and deploy to you already configured Wildfly server. Remember to first configure the infrastructure.

## How to: run by hands

Build the project. On the root folder run: 

```shell
mvn clean package -Prelease
```

If no profile is used, this will tell maven to build the development version with no migrations and the database need to be initialized manually like is said above. 

The build configuration also have other profiles for you to configure according to your need: 

- *BETA* for beta releases
- *RC* for release candidate releases 
- *RELEASE* for the final, production ready releases

After the build, open the wildfly admin console on the web browser and in the deployments section, upload the war file created by the build in the target folder (named *library-1.0.0-(selected-profile)*) inside the project and access it on the default URL: https://localhost:8443/, and you're done! Enjoy the demo.