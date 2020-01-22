# Library

A simple demo application to show how to work with the default technologies of JavaEE version 8 (or higher). This conceptual 
architecture makes use of the following technologies:

- Java 13+
- JavaEE 8 with Thorntail 2.6
- PostgreSQL 11+
- Hibernate 5 and JPA 2.2
- Weld 3.0 for CDI 2.0
- Mojarra for JSF 2.3 + Primefaces 7 + [AdminLTE](https://adminlte.io/) 2.4 + Bootstrap 3
- Apache Delta Spike JPA + QueryDSL
- Apache Shiro 1.4 provided by [ShiroEE](https://github.com/arthurgregorio/shiro-ee) extension
- LDAP and Active Diretory integration for user authentication 
- Maven for dependency management and build  
- Flyway 6 for database migrations
- Database audit with Hibernate Envers 5
- Hibernate Validator 6 for Bean Validation
- Omnifaces 3 and Primefaces Extensions for JSF utilities 
- Lombok, Google Guava and Apache Commons-lang for class level utilities
- Mustache for e-mail templating 
- Webservices with JAX-RS (RestEasy) 

It's not much say that if you want a **production ready architecture**, this is the project you are looking for.

### Create the database

Before the configuration step, create the database to used by the application:

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
```

The tables and the initial data (default user, group and authorizations) should be created by Flyway with the migrations 
strategy. If you want to run this application in development mode, Hibernate will create the tables, but you must create 
the schemes manually on PgAdmin or other similar software:

```sql
CREATE SCHEMA configuration AUTHORIZATION sa_library;
CREATE SCHEMA registration AUTHORIZATION sa_library;
```

### Configure the application

The application provides three profiles to build and configure the application:

- *ALPHA* -for development environments
- *BETA* - for testing environments
- *RELEASE_CANDIDATE* - for validation and final testing (approval of changes)
- *RELEASE* - used in production environments

Every profile has his own configurations, it means that you can have separated environments and to activate them you
just need to build the application again.

Below we will configure the RELEASE profile and build a production artifact:

```xml
<profile>
    <id>release</id>
    <properties>
        <application.version>${project.version}-RELEASE</application.version>
        <application.base-url>https://localhost:8443/</application.base-url>
        <skip.tests>false</skip.tests>
        <project.stage>Production</project.stage>
        <mail.host>my-mail-host</mail.host> 
        <mail.port>587</mail.port>
        <mail.debug>false</mail.debug>
        <mail.from-address>noreply@your-domain.com</mail.from-address>
        <mail.username>my@account.com</mail.username>
        <mail.password>secret</mail.password>
        <database.host>localhost</database.host>
        <database.port>5432</database.port>
        <database.name>library</database.name>
        <database.username>sa_library</database.username>
        <database.password>sa_library</database.password>
        <orm.show_sql>false</orm.show_sql>
        <orm.ddl_auto>none</orm.ddl_auto>
        <ws.base-url>https://my-webservice-server.com</ws.base-url>
        <ws.username>admin</ws.username>
        <ws.password>admin</ws.password>
        <ws.client-name>admin</ws.client-name>
        <ldap.enabled>false</ldap.enabled>
        <ldap.url>ldap://localhost</ldap.url>
        <ldap.baseDn>OU=Users,DC=arthurgregorio,DC=eti,DC=br</ldap.baseDn>
        <ldap.user>CN=bind-user,OU=Applications,DC=arthurgregorio,DC=eti,DC=br</ldap.user>
        <ldap.password>my-bind-password</ldap.password>
    </properties>
</profile>
```

As you can see, just fill the properties with your configurations and build with the command below:

```shell script
mvnw clean package -Prelease
```

> **Quick tip**!
>
> Windows users should use ```mvnw.bat```, Linux users ```./mvnw```

After the build process, a folder called ```target``` should appear and inside you will find the artifacts 
```library-x.x.x-RELEASE-thorntail.jar``` and ```library-x.x.x-RELEASE.war```, it means that you are ready to run the 
application.

To do that, just use:

```shell script
java -jar target/library-x.x.x-RELEASE-thorntail.jar 
```

If you have Java JDK installed, the application will start and should be available on ```http://localhost:8080/```, the 
username and password to access are ```admin```.

> **Quick tip!**
> 
> You can run the application directly from the build process using Thorntail Maven Plugin, just use
> ```mvn clean package -Prelease thorntail:run``` 

### Running inside the IDE

We use maven to build the application and almost all the IDE on the market have a feature to import maven projects.

By that, just import the project and you are ready to go!

### Contact

If you have any problem or want to contact me, send me an e-mail at: contato@arthurgregorio.eti.br
