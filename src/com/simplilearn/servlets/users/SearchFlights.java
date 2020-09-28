package com.simplilearn.servlets.users;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.simplilearn.tables.Flight;
import com.simplilearn.util.HibernateUtil;

/**
 * Servlet implementation class SearchFlights
 */
@WebServlet("/search-flights")
public class SearchFlights extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchFlights() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "null", "unchecked" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// print writer
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String source = request.getParameter("source");
		String destination = request.getParameter("destination");
		String price = request.getParameter("price");
		String date = request.getParameter("date");		
		
		//open a connection
		try {
			SessionFactory sFactory = HibernateUtil.buildSessionFactory();
			Session session = sFactory.openSession();
			session.beginTransaction();
			
			String query = "select f from Flight f" + 
					" inner join Airline a" + 
					" on f.airline=a.id" + 
					" and f.numberOfSeats > 0";
					if (source !=null && source.trim().length() > 0) {
						String sourceQuery = " and f.source = '" + source + "'";
						query = query.concat(sourceQuery);
					}
					if (destination != null && destination.trim().length() > 0) {
						String destinationQuery = " and f.destination = '" + destination + "'";
						query = query.concat(destinationQuery);
					}
					if (price !=null && price.trim().length() > 0) {
						try {
							Double priceParsed = Double.parseDouble(price);
							String priceQuery = " and f.price <= " + priceParsed;
							query = query.concat(priceQuery);
						} catch (Exception e){
							out.println("<h1>You must enter a valid number for price<h1>");
						}
					}
					if (date !=null && date.trim().length()> 0) {
						try {
							Date dateSql=Date.valueOf(date);
							String dateQuery = " and f.dateOfDeparture >= '" + dateSql + "'";
							query = query.concat(dateQuery);
						} catch (Exception e1) {
							out.println("<h1>You must enter the date correctly into yyyy-MM-dd format<h1>");
						}
					}
					System.out.println(query);
			List<Flight> flights = session.createQuery(query).list();
			
			if (flights.size() > 0) {
			out.print("<a href=\"index.html\">Home</a>");
			out.println("<h1>List of all Flights: </h1>");
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
			request.getRequestDispatcher("registeration.html").include(request, response);
			} else {
				out.print("<a href=\"index.html\">Home</a>");
				out.print("<h1>Sorry there are no flights currently!<h1>");
			}

			session.close();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
