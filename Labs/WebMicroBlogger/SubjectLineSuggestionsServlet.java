package guestbook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SubjectLineSuggestionsServlet extends HttpServlet {

	String[] subjectLines = { "Hi!", "Hello!", "Good morning!",
			"How do you do? ", "How are you?", "Greetings!",
			"Nice to meet you!", "Salutations!", "Welcome!", "Good Day!",
			"Good Evening!", "Good Night! ", "Greetings and Salutations!",
			"Good afternoon!", "Ladies and Gentlemen", "Hey!", "What's up?",
			"Wassup'", "What's going down!", "How's it going?",
			"How YOU doing?", "G'day!", "'Sup'", "What?s shakin??", "Howdy!",
			"Aloha!", "Hulloo!", "Halloa! ", "What?s new?", "Halloo", "Hullo",
			"Hail!", "Shalom!", "Peace!", "Yoo hoo!", "Yo!", "Hey there!",
			"Word!", "Alright!", "Ay-up!", "Eh up! ", "A-ight!",
			"Top of the morning to ya!", "G'day Mate!", "Dude!", "Hiya!",
			"Hello!", "Hey ya!", "Hey baby!", "Yoyoyo!", "Whattup?",
			"Whazzzzzzzzzzzzup?", "Hi hi! ", "What?s happening?", "Cheers!",
			"Ciao!", "What's the good word?", "What's the haps?", "Howzit?" };

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String queryString = request.getParameter("substring");
		String typeString = request.getParameter("type");
		if (queryString != null) {
			queryString = queryString.toLowerCase();
		}
		if (typeString != null) {
			typeString = typeString.toLowerCase();
		}
		String checkString = null;
		if (typeString != null) {
			checkString = typeString;
		}
		if (queryString != null) {
			checkString = queryString;
		}
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		sb.append("{[ ");
		boolean first = true;
		for (int i = 0; i < subjectLines.length; i++) {
			if (subjectLines[i].toLowerCase().indexOf(checkString) != -1) {
				if (!first) {
					sb.append(" , ");
					first = false;
				}
				first = false;
				sb.append("\"" + subjectLines[i] + "\"");
			}
		}
		sb.append("]} ");
		out.println(sb.toString());
		out.close();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}

