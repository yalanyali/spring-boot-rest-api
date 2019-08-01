# REST API Project for Java 2

A REST API to be used as a backend for an appointment management app in a doctor's office.


## Database

MYSQL is the database of choice. In order to test the project, please **create a database** called **db_rest** and make sure the user "springuser" has **correct permissions** on it.

>mysql> create database db_rest;

>mysql> create user 'springuser'@'%' identified by 'password';

>mysql> grant all on db_rest.* to 'springuser'@'%';

Or change the default database and credential settings in **application.properties** file.

Remember to change **spring.jpa.hibernate.ddl-auto** variable to create or update accordingly.

## Documentation

The [documentation](https://termin-api.docs.stoplight.io/) allows you to see and test the endpoints.

## Test Server

A [test server](http://termin-api.chickenkiller.com:8080/api) is provided with the [documentation](https://termin-api.docs.stoplight.io/).

Username: `testuser`
Password: `testpassword`

**Note on test server:**
Test server is implemented to be reset in half hour intervals for security purposes.
Don't panic if you experience data loss, you will and that's intended.