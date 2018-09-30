/*
 * UCL2ICS v3 APP ENGINE EDITION
 * By Corentin Damman
 */

package com.coconuts.ucl2ics;

import java.io.IOException;
import java.security.GeneralSecurityException;
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
public class UCL2ICS extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String loginAction = req.getParameter("login");
		if(Util.isset(loginAction)) {
			if(loginAction.equals("true")) {
		        UserService userService = UserServiceFactory.getUserService();
		        User user = userService.getCurrentUser();
		        
				if(user != null) {
		        	resp.setContentType("text/html");
					resp.setCharacterEncoding("UTF-8");
		        	
		        	PersistenceManager pm = Util.get().getPersistenceManager();
		        	boolean login = false;
		        	
			        try {
			        	Query query = pm.newQuery(StudentAgenda.class);
			        	query.setFilter("email == emailParam");
			        	query.setOrdering("email asc");
			        	query.declareParameters("String emailParam");
			            try {
							List<StudentAgenda> results = (List<StudentAgenda>) query.execute(user.getEmail());
							if (!results.isEmpty()) {
								for (StudentAgenda e : results) {
		                			login = true;
		                			resp.getWriter().println(Util.header());
		        	            	resp.getWriter().println("<p><div style=\"float: left;\">Bienvenue <b>" + user.getEmail() + "</b> ! Vous pouvez maintenant modifier votre horaire via le formulaire ci-dessous !</div>" +
		        	            			"<div style=\"float: right; margin-top: -0.5em;\"><a href=\"" + userService.createLogoutURL("/ucl2ics?login=false") + "\" class=\"zocial google\" style=\"vertical-align:middle\">Se déconnecter</a></div></p><br><br>");
		        	        		resp.getWriter().println(Util.beginFormCheckedCourses(e.getCodes().getValue(), e.getCourses().getValue(), e.getSemaines(), e.getProjectID(), Util.encrypt(e.getEmail(), pm.getObjectById(Secure.class, "PrivateKey").getPrivateKey())));		        	
		        	        		resp.getWriter().println(Util.footer());
			                    }
			                }
			            } catch (GeneralSecurityException e) {
			            	resp.getWriter().println("<div class=\"likeform\"><center><p><b>Attention:</b> il semblerait qu'il y ait une <b>erreur</b><br>" +
			            			"Merci de bien vouloir <b>réessayer</b> dans quelques instants, ou si les problèmes persistent, d'<b>envoyer un message</b> au développeur</p>" +
			            			"<a href=\"javascript:history.back()\" class=\"zocial secondary\">Oups, revenir en arrière !</a> ou <a href=\"http://dammanco.appspot.com/sendMail\" class=\"zocial secondary\">Envoyer un message !</a><br><br>" + 
                					e.getLocalizedMessage() + "</center>" + 
			            			"</div>");
			            	resp.getWriter().println(Util.footer());
							e.printStackTrace();
						} finally {
			                query.closeAll();
			            }
			        } finally {
			        	if(!login) {
			        		try {
			        			resp.getWriter().println(Util.header());
			        			resp.getWriter().println("<div style=\"float: left;\">Compte non trouvé ! Vous pouvez le créer maintenant via le formulaire ci-dessous !</div>" +
			            			"<div style=\"float: right; margin-top: -0.5em;\"><a href=\"" + userService.createLogoutURL("/ucl2ics?login=false") + "\" class=\"zocial google\">Se déconnecter</a></div><br><br>");
			        		
								resp.getWriter().println(Util.beginForm("FSA13BA,minelec13,Majmeca13,LANGL1873", "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51", 2, Util.encrypt(user.getEmail(), pm.getObjectById(Secure.class, "PrivateKey").getPrivateKey())));
				        		resp.getWriter().println(Util.footer());
							} catch (GeneralSecurityException e) {
				            	resp.getWriter().println("<div class=\"likeform\"><center><p><b>Attention:</b> il semblerait qu'il y ait une <b>erreur</b><br>" +
				            			"Merci de bien vouloir <b>réessayer</b> dans quelques instants, ou si les problèmes persistent, d'<b>envoyer un message</b> au développeur</p>" +
				            			"<a href=\"javascript:history.back()\" class=\"zocial secondary\">Oups, revenir en arrière !</a> ou <a href=\"http://dammanco.appspot.com/sendMail\" class=\"zocial secondary\">Envoyer un message !</a><br><br>" + 
	                					e.getLocalizedMessage() + "</center>" + 
				            			"</div>");
				            	resp.getWriter().println(Util.footer());
								e.printStackTrace();
							}
			
			        	}
			            pm.close();
			        }
		        } else {
		            resp.sendRedirect(userService.createLoginURL("/ucl2ics?login=true"));
		        }
			} else {
				resp.setContentType("text/html");
				resp.setCharacterEncoding("UTF-8");
				
				resp.getWriter().println(Util.header());
				resp.getWriter().println("<div style=\"float: left;\">Vous pouvez vous connecter pour pouvoir modifier votre synchonisation horaire ICS dans le futur !</div>" +
		    			"<div style=\"float: right; margin-top: -0.5em;\"><a href=\"/ucl2ics?login=true\" class=\"zocial google\">Se connecter</a></div><br><br>");
				
				resp.getWriter().println(Util.beginFormAlert("FSA13BA,minelec13,Majmeca13,LANGL1873", "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51", 2, "null"));

				resp.getWriter().println(Util.footer());
			}
		} else {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			
			resp.getWriter().println(Util.header());
			resp.getWriter().println(Util.beginHome());
			resp.getWriter().println(Util.footer());			
		}
	}
}