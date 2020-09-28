package com.simplilearn.tables;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class Person {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="per_id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="birthday")
	private Date birthday;
	
	@Column(name="time_booked")
	private Date timeBooked;
	
	@OneToMany(mappedBy="person")
	private Set <Payment> payments;
	
	@ManyToMany
	@JoinTable(
	  name = "personflight", 
	  joinColumns = @JoinColumn(name = "per_id"), 
	  inverseJoinColumns = @JoinColumn(name = "f_id"))
	Set<Flight> flights;
	
	public Person () {}

	public Person(String firstName, String lastName, String email, Date birthday, Date timeBooked,
			Set<Payment> payments, Set<Flight> flights) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.birthday = birthday;
		this.timeBooked = timeBooked;
		this.payments = payments;
		this.flights = flights;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getTimeBooked() {
		return timeBooked;
	}

	public void setTimeBooked(Date timeBooked) {
		this.timeBooked = timeBooked;
	}

	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	public Set<Flight> getFlights() {
		return flights;
	}

	public void setFlights(Set<Flight> flights) {
		this.flights = flights;
	}
	
	

	
	
}
