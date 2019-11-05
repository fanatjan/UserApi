### User Api 
#### Author: Hayk Hovhannisyan

#### Guides
A simple Restful api, which one contains CRUD operation for users and their own notes:

###### Api request map
* {url}/users - GET/POST/PUT/DELETE all users
* {url}/users/{id} - get by id
* {url}/notes - GET all notes
* {url}/users/{id}/notes/ - GET/POST/PUT/DELETE get all notes by user id 

###### possible responses
* 200 - OK
* 400 - Bad Request
* 500 - Internal Server Error

#### Created
- spring boot
- h2 database
- maven