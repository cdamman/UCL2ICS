/*
 * UCL2ICS v3 APP ENGINE EDITION
 * By Corentin Damman
 */

package com.coconuts.ucl2ics;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class Admin extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if (user != null) {
			if (Util.checkAdministrator(user.getEmail())) {
				PersistenceManager pm = Util.get().getPersistenceManager();
				
				resp.setContentType("text/html");
				resp.setCharacterEncoding("UTF-8");
				
				resp.getWriter().println("<html><head>" +
					"<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">" +
					"<title>Admin</title><style>td { width:80px; max-width:200px; } tr:nth-child(even) {background: #CCC} tr:nth-child(odd) {background: #FFF} </style>" +
					"</head><body><div style=\"float: left;\"><font size=\"6\" face=\"Comic Sans MS\">Admin</font></div><div style=\"float: right;\"><a href=\"" + 
					userService.createLogoutURL(req.getRequestURI()) + "\">Log Out</a></div>  <br><br><center><h4>Available Students :</h4></center>" + 
					"  <table border=\"1\" align=\"center\"><tr><th>&nbsp;Key&nbsp;</th><th width=20%>&nbsp;Codes&nbsp;</th><th width=40%>&nbsp;Courses&nbsp;</th><th width=20%>&nbsp;Semaines&nbsp;</th><th>&nbsp;Projet&nbsp;<br>&nbsp;STFU&nbsp;<br>&nbsp;TPorCM&nbsp;</th><th>&nbsp;Count&nbsp;<br>&nbsp;Email&nbsp;</th><th>&nbsp;Outils&nbsp;</th></tr>");

				Counter counter;
				try {
					counter = pm.getObjectById(Counter.class, "Counter");
				} catch(JDOObjectNotFoundException e) {
					counter = new Counter();
					pm.makePersistent(counter);
					resp.getWriter().println("<script type=\"text/javascript\" language=\"javascript\">alert(\"Bienvenue :-) DB init counter !\");</script>");
				}
				Secure privateKey;
		        try {
		        	privateKey = pm.getObjectById(Secure.class, "PrivateKey");
		        } catch (JDOObjectNotFoundException e) {
		        	privateKey = new Secure();
		        	pm.makePersistent(privateKey);
		        	resp.getWriter().println("<script type=\"text/javascript\" language=\"javascript\">alert(\"Bienvenue :-) DB init secure !\");</script>");
		        }

				int number = 0;
				try {
					Query query = pm.newQuery(StudentAgenda.class);
					query.setOrdering("key asc");
					
					try {
						List<StudentAgenda> results = (List<StudentAgenda>) query.execute();
						if (results.iterator().hasNext()) {
							for (StudentAgenda e : results) {
								number++;
								resp.getWriter().println("    <tr>      " + e.getAll() + "    </tr>");
							}
						} else {
							resp.getWriter().println("    <tr>      <td>No clients</td><td></td><td></td><td></td><td></td><td></td><td></td>    </tr>");
						}
					} finally {
						query.closeAll();
					}
				} finally {
					resp.getWriter().println("  </table>  <center><h4>Number of Students: "+String.valueOf(number)+"/<a href=\"modif?key=-1\">"+String.valueOf(counter.getKey())+"</a><br/>Private Key: <a href=\"modif?key=-2\">" + String.valueOf(privateKey.getPrivateKey()) + "</a></h4></h4></center>    <br></body></html>");
					pm.close();
				}
			} else {
				resp.setContentType("text/html");
				resp.getWriter().println("<h1>Unauthorized</h1><a href=\"" + userService.createLogoutURL(req.getRequestURI()) + "\">Log Out</a>");
			}
		} else resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
	}
}