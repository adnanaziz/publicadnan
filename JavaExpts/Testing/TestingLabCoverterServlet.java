package guestbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestingLabConverterServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String farTemp = request.getParameter("farenheitTemperature");
		
		if (farTemp == null) {
			out.println("<html><head><title>No Temperature</title>"
					+ "</head><body><h2>Need to enter a temperature!"
					+ "</h2></body></html>");
			out.close();
			return;
		}
		farTemp = farTemp.trim();
		
		Double farTempDouble = null;
		try {
	      farTempDouble = Double.parseDouble(farTemp);
		} catch (NumberFormatException e) {
		  out.println("<html><head><title>Bad Temperature</title>"
					+ "</head><body><h2>Need to enter a valid temperature!"
				    + "Got a NumberFormatException on " 
					+ farTemp 
					+ "</h2></body></html>");
		  out.close();
		  return;
		}
		
		Double celTempDouble = 100.0*(farTempDouble - 32.0)/180.0;
		DecimalFormat df = new DecimalFormat("#.##");
		String celTemp = df.format(celTempDouble);
        out.println("<html><head><title>Temperature Converter Result</title>"
					+ "</head><body><h2>" + farTemp + " Farenheit = " + celTemp + " Celsius "
					+ "</h2></body></html>");
		out.close();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}