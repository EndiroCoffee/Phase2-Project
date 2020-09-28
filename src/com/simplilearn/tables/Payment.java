package com.simplilearn.tables;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="payment")
public class Payment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pay_id")
	private int id;
	
	@Column(name="card_number")
	private String cardNumber;
	
	@Column(name="expiration")
	private Date expiration;

	@Column(name="security_code")
	private int securityCode;
	
	@Column(name="card_fname")
	private String firstNameOnCard;
	
	@Column(name="card_lname")
	private String lastNameOnCard;
	
	@ManyToOne()
	@JoinColumn(name="per_id", nullable=false)
	private Person person;
	
	public Payment() {}

	public Payment(String cardNumber, Date expiration, int securityCode, String firstNameOnCard, String lastNameOnCard,
			Person person) {
		super();
		this.cardNumber = cardNumber;
		this.expiration = expiration;
		this.securityCode = securityCode;
		this.firstNameOnCard = firstNameOnCard;
		this.lastNameOnCard = lastNameOnCard;
		this.person = person;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public int getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(int securityCode) {
		this.securityCode = securityCode;
	}

	public String getFirstNameOnCard() {
		return firstNameOnCard;
	}

	public void setFirstNameOnCard(String firstNameOnCard) {
		this.firstNameOnCard = firstNameOnCard;
	}

	public String getLastNameOnCard() {
		return lastNameOnCard;
	}

	public void setLastNameOnCard(String lastNameOnCard) {
		this.lastNameOnCard = lastNameOnCard;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}	
}
