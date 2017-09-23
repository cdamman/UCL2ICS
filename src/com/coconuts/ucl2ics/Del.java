/*
 * UCL2ICS v3 APP ENGINE EDITION
 * By Corentin Damman
 */

package com.coconuts.ucl2ics;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class Del extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if (user != null) {
			if (Util.checkAdministrator(user.getEmail())) {
				String keyString = req.getParameter("key");
				String confirm = req.getParameter("confirm");
				PersistenceManager pm = Util.get().getPersistenceManager();
				
				try {
					if (keyString == null) {
						resp.getWriter().println("Error null<br>");
					} else {
						try {
							Long key = Long.valueOf(keyString);
							if (key <=0) {
								resp.getWriter().println("Error <=0<br>");
							} else if (confirm == null) {
								resp.getWriter().println("<script type=\"text/javascript\" language=\"javascript\">if (confirm(\"Delete " + 
								key + "?\")) { window.location.replace(\"del?key=" + key + "&confirm=true\");" + 
								"} else { window.location.replace(\"admin\"); } </script>");
							} else if (confirm.equals("true")) {
								Query query = pm.newQuery(StudentAgenda.class);
								query.setFilter("key == keySearch");
								query.setOrdering("key asc");
								query.declareParameters("Long keySearch");
									
								try {
									List<StudentAgenda> results = (List<StudentAgenda>) query.execute(key);
									if (!results.isEmpty())
										for (StudentAgenda e : results) {
											pm.deletePersistent(e);
											resp.getWriter().println("Student "+key+" deleted !<br><meta http-equiv=\"Refresh\" content=\"3;URL=admin\">");
										} else resp.getWriter().println("No results<br>");
								} finally {
									query.closeAll();
								}
							} else {
								resp.getWriter().println("Error<br>");
							}
						} catch(NumberFormatException exc) {
							resp.getWriter().println("Error number format<br>");
						}
					}
				} finally {
					resp.getWriter().println("<br><a href=\"admin\">Return</a>");
					pm.close();
				}
			} else {
				resp.getWriter().println("<h1>Unauthorized</h1>");
			}
		} else resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
	}
}