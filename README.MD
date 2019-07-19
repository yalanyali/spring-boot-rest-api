# REST API Project for Java 2

A REST API to be used as a backend for an appointment management app in a doctor's office.


## Database

MYSQL is the database of choice. In order to test the project, please **create a database** called **db_rest** and make sure the user "springuser" has **correct permissions** on it.

>mysql> create database db_rest;

>mysql> create user 'springuser'@'%' identified by 'password';

>mysql> grant all on db_rest.* to 'springuser'@'%';

Or change the default database and credential settings in **application.properties** file.

Remember to change **spring.jpa.hibernate.ddl-auto** variable to create or update accordingly.
