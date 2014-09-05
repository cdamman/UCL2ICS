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

@SuppressWarnings("serial")
public class Set extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		
		Text codes = new Text(req.getParameter("codes"));
		Text courses = new Text(req.getParameter("courses"));
		String semaines = req.getParameter("weeks");
		String projectIDString = req.getParameter("project");
		String STFUString = req.getParameter("dh");
		String email = req.getParameter("email");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if ((codes == null) || (courses == null) || (semaines == null) || (projectIDString == null)) {
				resp.getWriter().println("Error null");
			} else if ((codes.toString().isEmpty()) || (courses.toString().isEmpty()) || (semaines.isEmpty()) || (projectIDString.isEmpty())) {
				resp.getWriter().println("Error empty");
			} else {
	        	Query query = pm.newQuery(StudentAgenda.class);
	        	query.setFilter("email == emailParam && email != null");
	        	query.setOrdering("email asc");
	        	query.declareParameters("String emailParam");
	            try {
					List<StudentAgenda> results = (List<StudentAgenda>) query.execute(email);
					if (!results.isEmpty()) {
					    for (StudentAgenda e : results) {
					    	e.setCodes(codes);
                			e.setCourses(courses);
                			e.setSemaines(semaines);
                			e.setProjectID(Integer.valueOf(projectIDString));
                			e.setSTFU(BoolValueOf(STFUString));
            				resp.getWriter().println(e.getKey());
					    }
					} else {
						StudentAgenda e = new StudentAgenda(pm.getObjectById(Counter.class, "Counter").getNextKey(), codes, courses, semaines, Integer.valueOf(projectIDString), BoolValueOf(STFUString), email);
						pm.makePersistent(e);
						resp.getWriter().println(e.getKey());
					}
	            } finally {
	                query.closeAll();
	            }
			}
		} finally {
			pm.close();
		}
	}
	
	private int BoolValueOf(String booleanString) {
		if(booleanString == null)
			return 0;
		else if(booleanString.isEmpty())
			return 0;
		else if(booleanString.equals("1"))
			return 1;
		else
			return 0;
	}
}