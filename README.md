# Example 06

A small web application, that serves as a reference point for the concepts presented in the lecture.


## Structure

The application follows a basic 3 layer structure, separating the persistence, business and presentation layer.

* **persistence**: A maven module to manage the database entities of the application.

  Contrary to examples 04 and 05, the `persistence.xml` no longer contains any significant configuration, because the persistence provider will be managed by the application server rather than us.
  Its mere purpose of existence is to trigger the meta-model generator.
  The majority of the settings are applied to the application server and can be found in the `application.properties` file in the `example06-presentation` module.


* **business**: A maven module containing the RESTful application interface.

  The `NewsREST` class (in the `de.ls5.wt2.rest` package) is an exemplary class for handling resource requests using the tools of the Spring-Web specification presented in the lecture.
  The other packages contain other REST endpoints for the different topics of the lecture (auth and security).
  Additionally a startup bean (`StartupBean`) is provided, that allows to create some dummy data during the application startup.


* **presentation**: A maven module for the web resources accessed by the users browser:

  This module holds the resources for the Angular frontend.
  By default the Angular app will be ignored during the build, so that only the backend part of the application is built (for details on the Angular app, see the corresponding section below).
  However, this module will produce the final archive that will contain the application, inkl. the server.
  As such, it additionally holds the configuration for Shiro (`ShiroConfig.java`) and Angular (`AngularConfig.java`).


## Deployment


Spring Boot provides a Maven plugin (`spring-boot-maven-plugin`) that allows to build executable Java archives.
Thus, after running a `mvn package` on the parent project, there will be a .war file located at `example06-presentation/target/example06-presentation-0.1.SNAPSHOT.war`.
By simply running `java -jar /path/to/war` you are able to start the backend server.

Alternatively, you can also start the application from your IDE by simply invoking the `main` method of the `Application` class. This approach is useful when trying to debug your application, because it allows to conveniently attach you IDE debugger to the backend server process.


Once the application server successfully started, the provided resources should be available under e.g. [http://localhost:8080/rest/news](http://localhost:8080/rest/news), etc.


## Angular


The Angular project is located in `example06-presentation/src/main/angular`.
In order to build/develop the frontend, two alternatives exist:

* **development mode**:
  By running `npm install` once (to download all dependencies) followed by `npm run start` in the above folder, you can start a small live web server that serves your Angular application and recompiles it whenever changes to your source code are detected.
  This is useful during development of the frontend and may be used completely independent or paired with a running backend to test/develop the backend REST calls.

* **production mode**:
  Building the example application with the `with-frontend` profile (`-Pwith-frontend`) executes the above steps in the default Maven lifecycle and compiles the Angular project to the `target` directory of the `example06-presentation` module.
  Consequently, both backend and frontend code will be packaged in the `.war` archive for easy deployment in a single application server.

  **Note for production mode**: Since routing in Angular apps is handled by Angular itself, we need to make sure that any request that is not a REST request is redirect to the main `index.html` of the Angular app.
  This is achieved in the `AngularConfig.java` of the presentation module, which redirects any unmapped URL (be it static resources or REST calls) to the index.html.


## Shiro


The main configuration file (`ShiroConfig.java`) can be found in the presentation module. It is the programmatic version of the `shiro.ini` which you may sometimes find in other tutorials.
It contains two realm definitions:

* The `WT2Realm` is a very basic realm that will suffice for most people who want to use either session-based of basic authentication.
* The `JWTWT2Realm` serves as a starting point for people who want to dig further into custom authentication mechanisms such as JSON Web Tokens.

Both realms are located in the business module.
The `WT2Realm` extends the existing `AuthorizingRealm` class of the Shiro framework, so that only the two methods `doGetAuthenticationInfo` and `doGetAuthorizationInfo` need to be implemented, which should return the necessary information to Shiro to perform authentication and authorization tasks.
If no authorization is required for the web app, one may extend the `AuthenticatingRealm` class, which only requires to implement the `doGetAuthenticationInfo` method.

Since Spring Boot provides native integration of Shiro, you can easily access `@Autowired EntityManager`s to access the database when performing database look-ups.

As for authorization, this example grants every user with a `ReadNewsItemPermission` permission.
According to Shiro's idea of permissions, permissions owned by a subject may imply permissions required by objects.
According to the implementation of `ReadNewsItemPermission`'s implies method, it implies a `ViewFirstFiveNewsItemsPermission` permission.
However, it does not blindly implies the requested permission, but rather calls its check-method, to actually decide on the implication.
This allows to utilize Shiro's permission-system to implement an attribute-based access control system, because `ViewFirstFiveNewsItemsPermission`'s check-method may return `true` or `false` based on arbitrary variables.


## Security

* **XSS**: The `news-details` component was configured to bypass Angular's internal sanitation system, so that the content of the news item is rendered without sanitation.
  To exploit this security hole use e.g. the following code as the content of the news entry:
  ```html
  <div style="
      position: absolute;
      background-color: dodgerblue;
      top: 0px;
      width: 100%;
      height: 100%;
      z-index: 1;
      text-align: center;
      font-size: large;">
  YOUR SITE HAS BEEN COMPROMISED
  </div>
  ```

* **SQL injection**: the `create-news-security` component was extended to call a new, unsafe REST endpoint in the backend, that persist the news item using raw SQL and therefore being susceptible to SQL injections.
  To exploit this security hole use e.g. the following code as the content of the news entry:
  ```sql
  '; DROP TABLE DBNews; --
  ```
