package com.simplilearn.servlets.users;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.simplilearn.tables.Flight;
import com.simplilearn.util.HibernateUtil;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/registeration")
public class Registeration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registeration() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// print writer
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		HttpSession httpsession = request.getSession();
		String flightid = request.getParameter("flightid");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String birthday = request.getParameter("birthday");
		
		request.setAttribute("firstname", firstname);
		Date dateSqlBirthday = null;
		try {
			dateSqlBirthday = Date.valueOf(birthday);
		} catch (Exception e1) {
			out.print("<a href = \"registeration.html\"> Re-Enter Information </a><br/>");
			out.print("Make sure you are entering your date code in correctly (yyyy-MM-dd)");
		}
		
		//email
		if (!(Pattern.matches("[A-Za-z0-9]+(.+)@(.+)", email))) {
			out.print("<a href = \"registeration.html\"> Re-enter Information </a><br/>");
			out.print("Make sure you are entering in a valid email address");
		} else if (dateSqlBirthday==null){
			out.print("<a href = \"registeration.html\"> Re-enter Information </a><br/>");
			out.print("Make sure you are entering in a valid date format (yyyy-MM-dd)");
		} else {
		httpsession.setAttribute("flightid", flightid);
		httpsession.setAttribute("firstname", firstname);
		httpsession.setAttribute("lastname", lastname);
		httpsession.setAttribute("email", email);
		httpsession.setAttribute("birthday", birthday);
	
		
		//open a connection
		try {
			SessionFactory sFactory = HibernateUtil.buildSessionFactory();
			Session session = sFactory.openSession();
			session.beginTransaction();
			
			String query = "select f from Flight f" + 
					" inner join Airline a" + 
					" on f.airline=a.id" + 
					" and f.numberOfSeats > 0";
					if (flightid !=null && flightid.trim().length() > 0) {
						try {
							int flightIdParsed = Integer.parseInt(flightid);
							String flightIdQuery = " and f.id =" + flightIdParsed;
							query = query.concat(flightIdQuery);
						} catch (Exception e){
							out.println("<h1>You must enter a valid flight id<h1>");
						}
					}
					
			List<Flight> flights = session.createQuery(query).list();
			
			if (flights.size() > 0) {
			out.print("<a href=\"index.html\">Home</a>");
			out.println("<h1>Confirm Flight Details: </h1>");
			for(Flight i:flights) {
				if (i.getNumberOfSeats() > 0) {
				out.println("Flight ID: " + i.getId() + "<br/>");
				out.println("Airline: " + i.getAirline().getAirline().toString() + "<br/>");
				out.println("Number Of Seats: " + i.getNumberOfSeats() + "<br/>");
				out.println("Source " + i.getSource() + "<br/>");
				out.println("Desintation:" + i.getDestination() + "<br/>");
				out.println("Departure Date: "+i.getDateOfDeparture() + "<br/>");
				out.println("Arrival Date: " + i.getDateOfArrival() + "<br/>");
				out.println("<h1>Your Total is: $" + i.getPrice() + "<h1><br/>");
				out.println("<hr>");
				}
			}
			request.getRequestDispatcher("payment.html").include(request, response);
			} else {
				out.print("<a href=\"index.html\">Home</a>");
				out.print("<h1>You must enter a valid flight id!<h1>");
			}
			session.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		}

}
