package socketmachines.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Servlet implementation class SocketQuoteServlet
 */
@WebServlet("/quote")
public class SocketQuoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Socket Machines Quote Application</h2>");
        out.println("<p>Please submit your request using the form below.</p>");
        out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Get form data
        String socketType = request.getParameter("socketType");
        String quantityStr = request.getParameter("quantity");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        // Validate if quantity is numeric
        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            quantity = -1;
        }

        // Validate input data
        if (socketType == null || socketType.isEmpty() || quantity <= 0 || name == null || name.isEmpty() || email == null || email.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input. Please make sure all fields are filled correctly.");
            return;
        }

        // Calculate price quote
        double pricePerSocket = calculatePrice(socketType);
        double totalPrice = pricePerSocket * quantity;

        // Generate response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Quote Details</h2>");
        out.println("<p>Thank you, " + name + " (" + email + ").</p>");
        out.println("<p>You requested " + quantity + " sockets of type " + socketType + ".</p>");
        out.println("<p>The total price is: $" + totalPrice + "</p>");
        out.println("</body></html>");
    }
        
     // Calculate price per socket type
        private double calculatePrice(String socketType) {
            switch (socketType) {
                case "Type A":
                    return 5.0;
                case "Type B":
                    return 9.5;
                case "Type C":
                    return 15.0;
                default:
                    return 0.0;
            }
	}

}
