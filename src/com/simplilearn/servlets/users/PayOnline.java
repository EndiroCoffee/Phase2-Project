package com.simplilearn.servlets.users;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.simplilearn.tables.Flight;
import com.simplilearn.tables.Payment;
import com.simplilearn.tables.Person;
import com.simplilearn.util.HibernateUtil;

/**
 * Servlet implementation class PayOnline
 */
@WebServlet("/pay-online")
public class PayOnline extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayOnline() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("payment.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		HttpSession httpsession = request.getSession(false);
		
		String personflightid = (String) httpsession.getAttribute("flightid");
		String personfirstname = (String) httpsession.getAttribute("firstname");
		String personlastname = (String) httpsession.getAttribute("lastname");
		String personemail = (String) httpsession.getAttribute("email");
		String personbirthday = (String) httpsession.getAttribute("birthday");
		long millis = System.currentTimeMillis();
		Date timeBooked = new Date(millis);
		Date dateSqlPerson = null;
		try {
			if ((dateSqlPerson=Date.valueOf(personbirthday)) == null) {
				out.println("<h1>You must enter the date correctly into yyyy-MM-dd format<h1>");
				out.print("<a href = \"payment.html\"> Re-enter Information </a><br/>");
			};
			
		} catch (Exception e1) {
			out.println("<h1>You must enter the date correctly into yyyy-MM-dd format<h1>");
			out.print("<a href = \"payment.html\"> Re-enter Information </a><br/>");
		}
		
		
		String fName = request.getParameter("firstname");
		String lName = request.getParameter("lastname");
		String cardNumber = request.getParameter("cardnumber").replaceAll("\\s", "");
		System.out.println(cardNumber);
		String expiration = request.getParameter("expiration");
		int securitycode = Integer.parseInt(request.getParameter("securitycode"));
		Date dateSqlPayment = null;
		try {
			dateSqlPayment=Date.valueOf(expiration);
			
		} catch (Exception e1) {
			out.println("<h1>You must enter the date correctly into yyyy-MM-dd format<h1>");
			out.print("<a href = \"payment.html\"> Re-enter Information </a><br/>");
		}
	
		
		if (!Pattern.matches("\\d{15,16}", cardNumber)) {
			out.print("<a href = \"payment.html\"> Re-enter Information </a><br/>");
			out.print("Make sure you are entering your card number in correctly");
			
		} else if (!Pattern.matches("\\d{3,4}", Integer.toString(securitycode))) {
			out.print("<a href = \"payment.html\"> Re-enter Information </a><br/>");
			out.print("Make sure you are entering your security code in correctly");
		} else if (dateSqlPayment.before(timeBooked)) {
			out.println("<h1>You must ensure your card is not expired<h1>");
			out.print("<a href = \"payment.html\"> Re-enter Information </a><br/>");
		}
		else {
		
		
		//step 1. Confirm Fields Match Criteria
			//Else redirect to payment
		//step 2. insert into person table
		//open a connection
		try {
			
			SessionFactory sFactory = HibernateUtil.buildSessionFactory();
			Session session = sFactory.openSession();
			session.beginTransaction();
			
			int personflightidConverted = Integer.parseInt(personflightid);
			
			String query1 = " from Flight where id = " + personflightidConverted;
			List<Flight> list = (List<Flight>) session.createQuery(query1).list();
			Set<Flight> flights = new HashSet<Flight>(list);  
			
			
			
			Person person = new Person();
			person.setFirstName(personfirstname);
			person.setLastName(personlastname);
			person.setEmail(personemail);
			person.setBirthday(dateSqlPerson);
			person.setTimeBooked(timeBooked);
			person.setFlights(flights);
			
			Payment payment = new Payment();
			payment.setFirstNameOnCard(fName);
			payment.setLastNameOnCard(lName);
			payment.setCardNumber(cardNumber);
			payment.setExpiration(dateSqlPayment);
			payment.setSecurityCode(securitycode);
			payment.setPerson(person);

			
			session.save(person);
			session.save(payment);
		
			String query = "select numberOfSeats from Flight f" + 
					" where id = " +  personflightidConverted;
			int resultFromQ1 = (int) session.createQuery(query).getSingleResult();
			Query q3=session.createQuery("update Flight set numberOfSeats=:n where id=:i");
			q3.setParameter("n",(resultFromQ1 - 1));  
			q3.setParameter("i",personflightidConverted);
			
			  
			int status=q3.executeUpdate();  
			System.out.println(status);  

			session.getTransaction().commit();
			session.close();
			response.sendRedirect("confirmation-page");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		//step 3. insert into payment table
		//step 4. reduce person count
		//step 5. Redirect to confirmation page
	
	}

}
