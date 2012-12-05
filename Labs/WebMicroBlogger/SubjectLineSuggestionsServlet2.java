package guestbook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class SubjectLineSuggestionsServlet2 extends HttpServlet {

    String[] subjectLines = {
"Hi!",
"Hello!",
"Good morning!",
"How do you do? ",
"How are you?",
"Greetings!",
"Nice to meet you!",
"Salutations!",
"Welcome!",
"Good Day!",
"Good Evening!",
"Good Night! ",
"Greetings and Salutations!",
"Good afternoon!",
"Ladies and Gentlemen",
"Hey!",
"What's up?",
"Wassup'",
"What's going down!",
"How's it going?",
"How YOU doing?",
"G'day!",
"'Sup'",
"What?s shakin??",
"Howdy!",
"Aloha!",
"Hulloo!",
"Halloa! ",
"What?s new?",
"Halloo",
"Hullo",
"Hail!",
"Shalom!",
"Peace!",
"Yoo hoo!",
"Yo!",
"Hey there!",
"Word!",
"Alright!",
"Ay-up!",
"Eh up! ",
"A-ight!",
"Top of the morning to ya!",
"G'day Mate!",
"Dude!",
"Hiya!",
"Hello!",
"Hey ya!",
"Hey baby!",
"Yoyoyo!",
"Whattup?",
"Whazzzzzzzzzzzzup?",
"Hi hi! ",
"What?s happening?",
"Cheers!",
"Ciao!",
"What's the good word?",
"What's the haps?",
"Howzit?"
  };


    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String queryString = request.getParameter("substring");
        Gson gson = new Gson();

        if (queryString.length() == 0) {
            return;
        }
        queryString = queryString.toLowerCase();

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        StringBuffer sb = new StringBuffer();
        //changed "{[" to "["
        sb.append("[ ");				
        boolean first = true;
        for (int i = 0; i < subjectLines.length; i++) {
            if (subjectLines[i].toLowerCase().indexOf(queryString) != -1) {
                if ( first ) {
                    sb.append("\"" + subjectLines[i] + "\"" );
                    first = false;
                    continue;
                }
                sb.append(" , ");
                sb.append("\"" + subjectLines[i] + "\"" );

            }
        }
        //changed " ]}" to " ]"
        sb.append(" ]");
        String sbToString = sb.toString();
        // String gsonReturn = gson.toJson(sbToString);
        String gsonReturn = sbToString;
        
        //System.out.println(sbToString);
        //System.out.println(gsonReturn);
        //returning a json object
        out.println(gsonReturn);
        out.close();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}