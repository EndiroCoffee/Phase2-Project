use flyawaydb;

create table airline(
	a_id int NOT NULL AUTO_INCREMENT,
	airline varchar(255) NOT NULL,
	PRIMARY KEY (a_id)
);

create table flight(
	f_id int(11) NOT NULL AUTO_INCREMENT,
	number_of_seats int(11) NOT NULL,
	price decimal(10,2) NOT NULL,
    source varchar(255) NOT NULL,
    destination varchar(255) NOT NULL,
    date_of_departure date NOT NULL,
    date_of_arrival date NOT NULL,
    a_id int(11) NOT NULL,
	PRIMARY KEY (f_id),
	CONSTRAINT airline_fk FOREIGN KEY (a_id) REFERENCES airline (a_id)
);

create table person(
	per_id int(11) NOT NULL AUTO_INCREMENT,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
    birthday date NOT NULL,
    time_booked DATETIME default CURRENT_TIMESTAMP NOT NULL,
	PRIMARY KEY (per_id)
);


create table personflight(
	per_id int(11) NOT NULL,
	f_id int(11) NOT NULL,
	PRIMARY KEY (per_id, f_id),
	CONSTRAINT person_fk FOREIGN Key (per_id) REFERENCES person (per_id),
	CONSTRAINT flights_fk FOREIGN Key (f_id) REFERENCES flight (f_id)
);

create table payment(
	pay_id int(11) NOT NULL AUTO_INCREMENT,
	card_number varchar(255) NOT NULL,
	expiration DATE NOT NULL,
	security_code int(11) NOT NULL,
	card_fname varchar(255) NOT NULL,
	card_lname varchar(255) NOT NULL,
	per_id int(11) NOT NULL,
	PRIMARY KEY (pay_id),
	CONSTRAINT person_payment_fk FOREIGN KEY (per_id) REFERENCES person (per_id)
);

create table admin(
	admin_id int (11) NOT NULL AUTO_INCREMENT,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	PRIMARY KEY (admin_id)
);

INSERT INTO airline ( airline) 
	values ("Emirates"), ("Delta"), ("United Airlines"), ("Turkish Airlines"), ("Uganda Airlines");

INSERT INTO flight (number_of_seats, price, source, destination, date_of_departure, date_of_arrival, a_id) 
	values (180, 500, "ORD", "DBX", "2020-10-20", "2020-10-22", 
		(select a_id from airline where airline = "Emirates")),
	 (10, 1200, "EBB", "LAX", "2020-10-23", "2020-10-24", 
		(select a_id from airline where airline = "Uganda Airlines")),
	 (150, 200, "ORD", "LAX", "2020-10-21", "2020-10-21", 
		(select a_id from airline where airline = "Delta")),
	 (20, 150, "ORD", "LAX", "2020-10-20", "2020-10-21", 
		(select a_id from airline where airline = "United Airlines")),
	 (120, 700, "DBX", "IATA", "2020-10-24", "2020-10-25", 
		(select a_id from airline where airline = "Turkish Airlines")),
	 (123, 250, "DBX", "EBB", "2020-10-22", "2020-10-22", 
		(select a_id from airline where airline = "Emirates"));

INSERT INTO person (first_name, last_name, email, birthday) 
	values ("Pablo" , "Smith" , "pablo@gmail.com", "1990-02-01"),
		("Pablo" , "Tisasirana" , "pablo@gmail.com", "1990-02-01"),
		("Veronica" , "Villagomez" , "veronica@gmail.com", "1990-02-01"),
		("Shay" , "Finn" , "shay@gmail.com", "1990-02-01"),
		("Nick" , "Lorance" , "nick@gmail.com", "1990-02-01"),
		("John" , "Brook" , "john@gmail.com", "1990-02-01"),
		("Albert" , "Gaca" , "albert@gmail.com", "1990-02-01"),
		("Chris" , "Smith" , "chris@gmail.com", "1990-02-01");
		
INSERT INTO personflight (per_id, f_id)
	values (1, 3),
		(2, 2),
		(3, 1),
		(4, 5),
		(5, 2),
		(6, 3),
		(7, 6),
		(8, 7);
		
INSERT INTO payment (card_number, expiration, security_code, card_fname, card_lname, per_id) 
	values ( "1234 5678 1021 9182" , "2022-10-31", 123, "Pablo", "Smith", 1),
		( "1234 5678 1021 9182" , "2022-10-31", 123, "Pablo", "Smith", 2),
		( "1234 5678 1021 9182" , "2022-10-31", 123, "Pablo", "Smith", 3),
		( "1234 5678 1021 9182" , "2022-10-31", 123, "Pablo", "Smith", 4),
		( "1234 5678 1021 9182" , "2022-10-31", 123, "Pablo", "Smith", 5),
		( "1234 5678 1021 9182" , "2022-10-31", 123, "Pablo", "Smith", 6),
		( "1234 5678 1021 9182" , "2022-10-31", 123, "Pablo", "Smith", 7),
		( "1234 5678 1021 9182" , "2022-10-31", 123, "Pablo", "Smith", 8);
		
INSERT INTO admin ( first_name, last_name, email, password) 
	values ("Bryan", "Finn", "admin@gmail.com", "admin");


