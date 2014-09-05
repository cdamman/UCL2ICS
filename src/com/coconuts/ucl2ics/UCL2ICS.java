/*
 * UCL2ICS v3 APP ENGINE EDITION
 * By Corentin Damman
 */

package com.coconuts.ucl2ics;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class UCL2ICS extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {		
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().println(PMF.header());
		resp.getWriter().println("<div style=\"float: left;\">Vous pouvez vous connecter pour pouvoir modifier votre synchonisation horaire ICS dans le futur !</div>" +
    			"<div style=\"float: right; margin-top: -0.5em;\"><a href=\"/login?login=true\" class=\"zocial google\">Se connecter</a></div><br><br>");
		
		resp.getWriter().println(PMF.beginFormAlert("FSA13BA,minelec13,Majmeca13,LANGL1873", "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51", 6, "null"));

		resp.getWriter().println(PMF.footer());
	}
}