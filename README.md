# Phase2-Project
FlyAway

Steps to use
------------------------
1. Open in IDE with Tomcat and SQL
2. Create new database called "flyawaydb"
2. Load in tables/data with information from db with setUpDB.sql file
3. Right Click Project Name and Run on Server
4. Follow the prompts

User Stories
----------------------
As a user,
I want to be able to search for available flights (Date of travel, source, destination, number of seats available, price),
So that I can select a flight easily

As a user,
I want to see the avialable flights with their ticket prices,
So that I can choose the best option for me

As a ticket-booking manager,
I want to be able users to record their person details,
So that I know who is on what flight

As a ticket-booking manager,
I want users to confirm their flight details prior to payment,
So that we can avoid refunds

As a ticket-booking manager,
I want users to pay online,
So we can receive payment immediately

As a user,
I want a confirmation page with proof of payment and details of my booking,
So that I can remember the finer details on the day of my flight

As an admin,
I want to be able to change my password after login,
So that I can keep my account secure

As an admin,
I want to view a master list of places for source and destination,
So that I know all of the places my booking portal offers

As an admin,
I want a view a list of airlines,
So that I know what airlines are available to my customers

As an admin,
I want to view a list of all flights where each flight has a source, destination, airline, ticket price, and number of seats empty,
So that I know which flights to advertise more heavily



Tables for FlyAwayDB
------------
Airline (a_id, airline)

Flight (f_id, number_of_seats, price, source, destination,date_of_departure, date_of_arrival, airline)

Person (per_id, first_name, last_name, email, birthday, timebooked)

personflight (per_id, f_id, flight_details)

Payment (pay_id, card_number, expiration_month, expiration_year, security_code, card_fname, card_lname, per_id)

Admin (admin_id, first_name, last_name, email, password)

Concepts
------------------
Servlets
Session Management
Hibernate CRUD operations
SQL/DDL Queries
Many to Many, Many to One Relationships
Table Joins, Filters, Subqueries
Transaction Control
Maven
