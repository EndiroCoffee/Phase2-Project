package com.simplilearn.servlets.users;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
 * Servlet implementation class ConfirmationPage
 */
@WebServlet("/confirmation-page")
public class ConfirmationPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmationPage() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// print writer
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");	
		
		HttpSession httpsession = request.getSession();
		String flightid = (String) httpsession.getAttribute("flightid");
		String firstName = (String) httpsession.getAttribute("firstname");
		int flightIdConverted = Integer.parseInt(flightid);
		//open a connection
		try {
			SessionFactory sFactory = HibernateUtil.buildSessionFactory();
			Session session = sFactory.openSession();
			session.beginTransaction();
			
			String query = "select f from Flight f" + 
					" inner join Airline a" + 
					" on f.airline=a.id" + 
					" and f.numberOfSeats > 0" + 
					" and f.id = " + flightIdConverted;
			@SuppressWarnings("unchecked")
			List<Flight> flights = session.createQuery(query).list();
			
			if (flights.size() > 0) {
			out.print("<a href=\"index.html\">Home</a><br/>");
			out.println("<h1>Congratulations! You're all set, "  + firstName + "!</h1><br/><br/>");
			out.println("<h1>List of Confirmed Flights for "  + firstName + "</h1><hr>");
			out.println("<style> table,th,td { border : 1px solid black ; padding :15px;} </style>");
			out.println("<table>");
			out.println("<tr>");
				out.println("<th>"); out.println("Flight ID"); out.println("</th>");
				out.println("<th>"); out.println("Airline"); out.println("</th>");
				out.println("<th>"); out.println("Number Of Seats"); out.println("</th>");
				out.println("<th>"); out.println("Source"); out.println("</th>");
				out.println("<th>"); out.println("Desintation"); out.println("</th>");
				out.println("<th>"); out.println("Departure Date"); out.println("</th>");
				out.println("<th>"); out.println("Arrival Date"); out.println("</th>");
				out.println("<th>"); out.println("Price"); out.println("</th>");
			out.println("</tr>");
			for(Flight i:flights) {
				if (i.getNumberOfSeats() > 0) {
					out.println("<tr>");
						out.println("<td>"); out.println(i.getId()); out.println("</td>");
						out.println("<td>"); out.println(i.getAirline().getAirline().toString()); out.println("</td>");
						out.println("<td>"); out.println(i.getNumberOfSeats()); out.println("</td>");
						out.println("<td>"); out.println(i.getSource()); out.println("</td>");
						out.println("<td>"); out.println(i.getDestination()); out.println("</td>");
						out.println("<td>"); out.println(i.getDateOfDeparture()); out.println("</td>");
						out.println("<td>"); out.println(i.getDateOfArrival()); out.println("</td>");
						out.println("<td>"); out.println(i.getPrice()); out.println("</td>");
					out.println("</tr>");
				}
			}
			out.println("</table>");
			} else {
				out.print("<a href=\"index.html\">Home</a>");
				out.print("<h1>Sorry You don't have any are no flights currently!<h1>");
			}

			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
