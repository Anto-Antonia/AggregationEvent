# AggregationEvent

This is a project about aggregation event, meaning is a process where users are able to view current events and eventually attend to them.
Users with different roles have access to manipulate the program. All of them can do something except a user
with "GUEST" role.

Every change is made through postman using the specific url.

Please make sure to change everything what's needed in application.properties before starting the application.
Also don't forget to comment everything about security config because in this application, people with specific roles can do specific actions.
If you do not comment you'll get an error 401Unauthorized.

Before creating a user you must first create a role for him. The roles I used were (USER, GUEST, ADMIN, EVENT MANAGER)

Always check the "dto- request" to see how to properly make a request for anything you want to create.

After creating the roles you can freely start adding users with their roles. Once you have a user with Admin role, you can de-comment everything from security config and use the admin to add new users.
It is the same with a user with "event manager" role, he is the only one who has access to events, managing them.

Keep in mind that the password needs to be encrypted, so after a user has a password, for example "1234", you must visit this website https://bcrypt-generator.com/ 

There you'll type the password and encrypt it, copy it then go in the DataBase, select the password and replace the raw password with the encrypted one.
Don't forget to click on "Apply" to save the changes. After this, in postman when creating anything, select "Basic Auth" then in the username field enter the name of a user with admin role,
event manager or user and last but not least the raw password.

# Try saving somewhere the raw password before encrypting it, you will need it for postman!

This was a short brief of what this project does, of course there is the search engine where you can 
search events by keyword or title.

That's all from me, have a nice day coding!

