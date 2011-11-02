package net.homeip.jtjang.MileageRunAppEngine;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class EverySixHoursServlet extends CronServlet {
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getCanonicalName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		logger.log(Level.INFO, "EverySixHoursServlet.doGet(): running");

		cronJobs(6);
		
		return;
	}
}
