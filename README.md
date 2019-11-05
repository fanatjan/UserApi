### User Api 
#### Author: Hayk Hovhannisyan

#### Guides
A simple Restful api, which one contains CRUD operation for users and their own notes:

###### Api request map
```
* [url}/users - GET/POST/PUT/DELETE all users
* {url}/users/{id} - get by id
* {url}/notes - GET all notes
* {url}/users/{id}/notes/ - GET/POST/PUT/DELETE get all notes by user id 
```
#### Created
- spring boot
- h2 database
- maven

##### Building and running

 -  You can run the application directly from Maven using the Spring Boot  
Maven plugin:
```
$ ./mvnw spring-boot:run
```

 - Or you can build the application with Maven and then run the resulting
JAR file:

```
$ ./mvnw package
$ java -jar target/UserApi-0.0.1-SNAPSHOT.jar
```

Or you may import the project into your IDE of choice and run it from there.

Once the application is running, the authorization server will be listening
for requests on port 8081
can be checked [localhost:8081/users](https://localhost:8081/users).

####Obtaining an access token
This authorization server uses in-memory stores for both users and clients.
In-memory stores were chosen to simplify the example. In a real production-ready
scenario, it should use a different store (JDBC-based, for example).

#####default user is
```
username: admin
password: password
```

#####There is only one client:
```
Client ID: myclient
Client Secret: secret
 ```
 
If you have the curl command line, you can get the token by:
```
curl http://localhost:9999/oauth/token \
     -d"grant_type=password&username=habuma&password=password" \
     -H"Content-type:application/x-www-form-urlencoded; charset=utf-8" \
     -u myclient:secret --silent"
```
token Validity is 24 hour
Authorization URL, for browser:
[URL](http://localhost:8081/oauth/authorize?client_id=myclient&response_type=token&redirect_uri=http://localhost:8081/x)
