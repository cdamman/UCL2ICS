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

import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class Modif extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if (user != null) {
			if (Util.checkAdministrator(user.getEmail())) {
				String keyString = req.getParameter("key");
				PersistenceManager pm = Util.get().getPersistenceManager();
				
				try {
					if (keyString == null) {
						resp.getWriter().println("Error null<br>");
					} else {
						try {
							Long key = Long.valueOf(keyString);
							if (key == -1) {
								String newValue = req.getParameter("newValue");
								if (newValue == null) {
									String page = "<BR><FORM METHOD=POST><table border=\"1\" align=\"center\"><tr><th>&nbsp;Variable&nbsp;</th><th>&nbsp;Value&nbsp;</th></tr><TR><TD>&nbsp;key&nbsp;</TD><TD><INPUT type=hidden name=\"key\" value=\"" + 
													key + "\" size=40>&nbsp;" + key + "&nbsp;</TD></TR>" + 
													"<TR><TD>&nbsp;Counter&nbsp;</TD><TD><INPUT type=text name=\"newValue\" value=\"" + String.valueOf(pm.getObjectById(Counter.class, "Counter").getKey()) + "\" size=40></TD></TR>" +
													"<TR><TD COLSPAN=2><INPUT type=\"submit\" value=\"Envoyer\"></TD></TR>" + 
													"</TABLE></FORM>";
									resp.getWriter().println(page);
								} else if (Long.valueOf(newValue) <= 0) {
									resp.getWriter().println("Error <=0<br>");
								} else {
									pm.getObjectById(Counter.class, "Counter").setKey(Long.valueOf(newValue));
									resp.getWriter().println("Counter modified !<br><meta http-equiv=\"Refresh\" content=\"1;URL=admin\">");
								}
							} else if (key == -2) {
				                pm.makePersistent(new Secure());
				                resp.getWriter().println("New private key generated !<br><meta http-equiv=\"Refresh\" content=\"1;URL=admin\">");
							} else if (key <= 0) {
								resp.getWriter().println("Error <=0<br>");
							} else {
								String codes = req.getParameter("codes");
								String courses = req.getParameter("courses");
								String semaines = req.getParameter("semaines");
								String projectIDString = req.getParameter("projectID");
								String STFUString = req.getParameter("STFU");
								String TPorCMString = req.getParameter("TPorCM");
								String counter = req.getParameter("counter");
								String email = req.getParameter("email");
								
								if ((courses == null) || (codes == null) || (semaines == null) || (projectIDString == null) || (STFUString == null)  || (TPorCMString == null) || (counter == null)) {
									Text codesOld = new Text("");
									Text coursesOld = new Text("");
									String semainesOld = "";
									String projectIDOld = "";
									String STFUOld = "";
									String TPorCMOld = "";
									String counterOld = "";
									String emailOld = "";
									
									Query query = pm.newQuery(StudentAgenda.class);
									query.setFilter("key == keySearch");
									query.setOrdering("key asc");
									query.declareParameters("Long keySearch");
									
									try {
										List<StudentAgenda> results = (List<StudentAgenda>) query.execute(key);
										if (!results.isEmpty())
											for (StudentAgenda e : results) {
												codesOld = e.getCodes();
												coursesOld = e.getCourses();
												semainesOld = e.getSemaines();
												projectIDOld = String.valueOf(e.getProjectID());
												STFUOld = String.valueOf(e.getSTFU());
												TPorCMOld = String.valueOf(e.getTPorCM());
												counterOld = String.valueOf(e.getCounter());
												emailOld = e.getEmail();
											} else resp.getWriter().println("No results<br>");
									} finally {
										query.closeAll();
									}
									
									String page = "<BR><FORM METHOD=POST><table border=\"1\" align=\"center\"><tr><th>&nbsp;Variable&nbsp;</th><th>&nbsp;Value&nbsp;</th></tr><TR><TD>&nbsp;key&nbsp;</TD><TD><INPUT type=hidden name=\"key\" value=\"" + 
									key + "\" size=40>&nbsp;" + key + "&nbsp;</TD></TR>" + 
									"<TR><TD>&nbsp;Codes&nbsp;</TD><TD><INPUT type=text name=\"codes\" value=\"" + codesOld.getValue().replaceAll(",", ", ") + "\" size=80></TD></TR>" + 
									"<TR><TD>&nbsp;Courses&nbsp;</TD><TD><textarea name=\"courses\" rows=\"15\" cols=\"75\">" + coursesOld.getValue().replaceAll(",", ", ") + "</textarea></TD></TR>" + 
									"<TR><TD>&nbsp;Semaines&nbsp;</TD><TD><INPUT type=text name=\"semaines\" value=\"" + semainesOld+ "\" size=100></TD></TR>" + 
									"<TR><TD>&nbsp;ProjectID&nbsp;</TD><TD><INPUT type=text name=\"projectID\" value=\"" + projectIDOld + "\" size=40></TD></TR>" + 
									"<TR><TD>&nbsp;STFU ?&nbsp;</TD><TD><INPUT type=text name=\"STFU\" value=\"" + STFUOld + "\" size=40></TD></TR>" + 
									"<TR><TD>&nbsp;TPorCM ?&nbsp;</TD><TD><INPUT type=text name=\"TPorCM\" value=\"" + TPorCMOld + "\" size=40></TD></TR>" + 
									"<TR><TD>&nbsp;Counter&nbsp;</TD><TD><INPUT type=text name=\"counter\" value=\"" + counterOld + "\" size=40></TD></TR>" + 
									"<TR><TD>&nbsp;Email&nbsp;</TD><TD><INPUT type=text name=\"email\" value=\"" + emailOld + "\" size=40></TD></TR>" + 
									"<TR><TD COLSPAN=2><INPUT type=\"submit\" value=\"Envoyer\"></TD></TR>" + 
									"</TABLE></FORM>";
									resp.getWriter().println(page);
								} else if ((codes.isEmpty()) || (courses.isEmpty()) || (semaines.isEmpty()) || (projectIDString.isEmpty()) || (STFUString.isEmpty())  || (TPorCMString.isEmpty()) || (counter.isEmpty())) {
									resp.getWriter().println("Error empty<br>");
								} else {
									Query query2 = pm.newQuery(StudentAgenda.class);
									query2.setFilter("key == keySearch");
									query2.setOrdering("key asc");
									query2.declareParameters("Long keySearch");
									
									try {
										List<StudentAgenda> results2 = (List<StudentAgenda>) query2.execute(key);
										if (!results2.isEmpty())
											for (StudentAgenda e2 : results2) {
												e2.setCodes(new Text(Util.unsplitVirgule(codes)));
												e2.setCourses(new Text(Util.unsplitVirgule(courses)));
												e2.setSemaines(Util.unsplitVirgule(semaines));
												e2.setProjectID(Integer.valueOf(projectIDString));
												e2.setSTFU(Integer.valueOf(STFUString));
												e2.setTPorCM(Integer.valueOf(TPorCMString));
												e2.setCounter(Integer.valueOf(counter));
												e2.setEmail(email);
												resp.getWriter().println("Student "+key+" modified !<br><meta http-equiv=\"Refresh\" content=\"1;URL=admin\">");
											} else resp.getWriter().println("No results<br>");
									} catch (NumberFormatException e) {
										resp.getWriter().println("Error: " + e.getMessage() + "<br>");
									} finally {
										query2.closeAll();
									}
								}
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