/*
 * UCL2ICS v3 APP ENGINE EDITION
 * By Corentin Damman
 */

package com.coconuts.ucl2ics;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Get extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {		
		String keyString = req.getParameter("key");
		PersistenceManager pm = Util.get().getPersistenceManager();
		
		try {
			if (keyString == null) {
				resp.setContentType("text/html");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().println("Error null<br>");
			} else {
				try {
					Long key = Long.valueOf(keyString);
					if (key <= 0) {
						resp.setContentType("text/html");
						resp.getWriter().println("Error <=0<br>");
					} else {
						boolean verified = false;
						
						Query query = pm.newQuery(StudentAgenda.class);
						query.setFilter("key == keySearch");
						query.declareParameters("Long keySearch");
						
						try {
							List<StudentAgenda> results = (List<StudentAgenda>) query.execute(key);
							if (results.iterator().hasNext())
								for (StudentAgenda e : results) {
									verified = true;
									resp.setContentType("text/calendar");
									resp.setCharacterEncoding("UTF-8");
									resp.setHeader("Content-Disposition", "inline; filename=ade.ics");
									try {
										resp.sendRedirect("http://ucl2icsphp.appspot.com/ade.php?codes="+URLEncoder.encode(e.getCodes().getValue(), "UTF-8")+
											"&courses="+URLEncoder.encode(e.getCourses().getValue(), "UTF-8")+"&weeks="+e.getSemaines()+"&project="+e.getProjectID()+"&dh="+e.getSTFU()+"&TPorCM="+e.getTPorCM());
							        } catch (IOException ioe) {
										resp.setContentType("text/html");
										resp.getWriter().println("Error while getting agenda !<br>"+ioe.getMessage());
							        }
									e.addCount();
								}
						} finally {
							query.closeAll();
						}
		
						if (!verified) {
							resp.setContentType("text/html");
							resp.getWriter().println("404 Not found");
						}
					}
				} catch(NumberFormatException exc) {
					resp.getWriter().println("Error number format<br>");
				}
			}
		} finally {
			pm.close();
		}
	}
}