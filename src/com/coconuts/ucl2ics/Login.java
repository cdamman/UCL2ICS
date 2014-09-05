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
public class Login extends HttpServlet {
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        if(user != null) {
        	resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
        	
        	PersistenceManager pm = PMF.get().getPersistenceManager();
        	boolean login = false;
        	
	        try {
	        	Query query = pm.newQuery(StudentAgenda.class);
	            try {
	                List<StudentAgenda> results = (List<StudentAgenda>) query.execute();
	                if (results.iterator().hasNext()) {
	                	for (StudentAgenda e : results) {
	                		if(user.getEmail().equals(e.getEmail())) {
	                			login = true;
	                			resp.getWriter().println(PMF.header());
	        	            	resp.getWriter().println("<p><div style=\"float: left;\">Bienvenue <b>" + user.getEmail() + "</b> ! Vous pouvez maintenant modifier votre horaire via le formulaire ci-dessous !</div>" +
	        	            			"<div style=\"float: right; margin-top: -0.5em;\"><a href=\"" + userService.createLogoutURL("/ucl2ics?begin=true") + "\" class=\"zocial google\" style=\"vertical-align:middle\">Se déconnecter</a></div></p><br><br>");
	        	        		resp.getWriter().println(PMF.beginFormCheckedCourses(e.getCodes().getValue(), e.getCourses().getValue(), e.getSemaines(), e.getProjectID(), user.getEmail()));
	        	
	        	        		resp.getWriter().println(PMF.footer());
	                		}
	                    }
	                }
	            } finally {
	                query.closeAll();
	            }
	        } finally {
	        	if(!login) {
	        		resp.getWriter().println(PMF.header());
	            	resp.getWriter().println("<div style=\"float: left;\">Compte non trouvé ! Vous pouvez le créer maintenant via le formulaire ci-dessous !</div>" +
	            			"<div style=\"float: right; margin-top: -0.5em;\"><a href=\"" + userService.createLogoutURL("/ucl2ics?begin=true") + "\" class=\"zocial google\">Se déconnecter</a></div><br><br>");
	        		resp.getWriter().println(PMF.beginForm("FSA13BA,minelec13,Majmeca13,LANGL1873", "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51", 6, user.getEmail()));
	
	        		resp.getWriter().println(PMF.footer());
	        	}
	            pm.close();
	        }
        } else {
            resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
    }
}