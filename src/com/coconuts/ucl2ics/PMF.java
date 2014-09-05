/*
 * UCL2ICS v3 APP ENGINE EDITION
 * By Corentin Damman
 */

package com.coconuts.ucl2ics;

import java.util.Calendar;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
	
	public static boolean checkAdministrator(String email) {
		if ((email.equals("cocod.tm@gmail.com")) || (email.equals("dev.coconuts@gmail.com") || (email.equals("guillaumederval@gmail.com"))))
			return true;
		else return false;
	}
	
	public static boolean isset(String str) {
		if (str == null)
			return false;
		else if (str.isEmpty())
			return false;
		else return true;
	}
	
	public static String header() {
		return 	"<html>" +
				"	<head>" +
				"		<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">" +
				"		<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"http://ucl2icsphp.appspot.com/favicon.ico\">" +
				"		<link rel=\"stylesheet\" type=\"text/css\" href=\"http://ucl2ics.appspot.com/css/zocial.css\">" +
				"		<link rel=\"stylesheet\" type=\"text/css\" href=\"http://ucl2icsphp.appspot.com/ade.css\">" +
				"		<title>UCL ADE to ICS</title>" +
				"	</head>" +
				"	<body>" +
				"		<h1><a href=\"http://ucl2ics.appspot.com\" style=\"text-decoration:none; color:#000;\">UCL ADE to ICS</a> <span id=\"version\">2014-2015 App Engine EDITION (v6)</span></h1>";
	}
	
	public static String footer() {
		return 	"		<div id=\"footer\">Par <a href=\"http://www.guillaumederval.be\" target=\"_blank\">Guillaume Derval</a>, ré-écrit sur Google App Engine par <a href=\"http://www.cdamman.tk\" target=\"_blank\">Corentin Damman</a>, basé sur le code de Ploki<br>" +
				"		Voir le <a href=\"https://gist.github.com/GuillaumeDerval/6574088\" target=\"_blank\">code source PHP</a> ou le <a href=\"http://ucl2ics.appspot.com/source.zip\" target=\"_blank\">code source App Engine</a> ! Utiliser <a href=\"http://ucl2ics.appspot.com/API.pdf\" target=\"_blank\">l'API</a></div>" +
				"	</body>" +
				"</html>";
	}
	
	public static String beginForm(String codes, String semaines, int projectID, String email) {
		return 	"<form class=\"top\" id=\"formulaire\" method=\"post\" action=\"http://ucl2icsphp.appspot.com/ade.php\">" +
				"<center><p><label for=\"codes\"><b>Codes cours</b> (séparés par virgules) ou <b>lien ADE</b> (donné par <a href=\"https://www.uclouvain.be/horaires-epl.html\" target=\"_blank\">l'outil horaire de l'EPL</a> ou celui de votre FAC): </label><br/>" +
				"<input type=\"text\" name=\"codes\" id=\"codes\" size=\"130\" value=\"" + codes + "\"/></p>" + 
				"<p><label for=\"semaines\"><b>Semaines</b> désirées (séparées par virgules): </label><br/>" + 
				"<input type=\"text\" name=\"semaines\" id=\"semaines\" value=\"" + semaines + "\" size=\"130\"/><br/>" + 
				"<input type=\"button\" value=\"Sélectionner le 1er quadrimestre\" onClick=\"this.form.semaines.value='0,1,2,3,4,5,6,7,8,9,10,11,12,13'\">" + 
				"<input type=\"button\" value=\"Sélectionner le 2ème quadrimestre\" onClick=\"this.form.semaines.value='19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34'\">" + 
				"<input type=\"button\" value=\"Sélectionner cette semaine\" onClick=\"this.form.semaines.value='"+String.valueOf((Calendar.getInstance().get(3) + 14) % 51)+"'\">" + 
				"<input type=\"button\" value=\"Sélectionner toutes les semaines\" onClick=\"this.form.semaines.value='0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51'\"><br/>" + 
				"Nous sommes en S" + String.valueOf((Calendar.getInstance().get(3) + 14) % 51) + ". La première semaine du premier quadrimestre est la semaine 0, et celle du second quadrimestre est la semaine 19</p>" +
				"<p><label for=\"projet\"><b>ID</b> du projet (pour 2014-2015, c'est 6): </label>" + 
				"<input type=\"text\" name=\"projet\" id=\"projet\" value=\"" + String.valueOf(projectID) + "\"/></p>" + 
				"<p><input type=\"checkbox\" name=\"deshurler\" id=\"deshurler\" checked=\"checked\"/><label for=\"deshurler\"><b>dé-HURLER</b> le nom des cours</label></p>" + 
				"<input type=\"hidden\" name=\"email\" id=\"email\" value=\"" + email + "\"/>" + 
				"<input type=\"submit\" class=\"zocial secondary\" value=\"Lancer\" /></center>" + 
				"</form>";
	}
	
	public static String beginFormAlert(String codes, String semaines, int projectID, String email) {
		return 	"<form class=\"top\" id=\"formulaire\" method=\"post\" action=\"http://ucl2icsphp.appspot.com/ade.php\">" +
				"<center><p><label for=\"codes\"><b>Codes cours</b> (séparés par virgules) ou <b>lien ADE</b> (donné par <a href=\"https://www.uclouvain.be/horaires-epl.html\" target=\"_blank\">l'outil horaire de l'EPL</a> ou celui de votre FAC): </label><br/>" +
				"<input type=\"text\" name=\"codes\" id=\"codes\" size=\"130\" value=\"" + codes + "\"/></p>" + 
				"<p><label for=\"semaines\"><b>Semaines</b> désirées (séparées par virgules): </label><br/>" + 
				"<input type=\"text\" name=\"semaines\" id=\"semaines\" value=\"" + semaines + "\" size=\"130\"/><br/>" + 
				"<input type=\"button\" value=\"Sélectionner le 1er quadrimestre\" onClick=\"this.form.semaines.value='0,1,2,3,4,5,6,7,8,9,10,11,12,13'\">" + 
				"<input type=\"button\" value=\"Sélectionner le 2ème quadrimestre\" onClick=\"this.form.semaines.value='19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34'\">" + 
				"<input type=\"button\" value=\"Sélectionner cette semaine\" onClick=\"this.form.semaines.value='"+String.valueOf((Calendar.getInstance().get(3) + 14) % 51)+"'\">" + 
				"<input type=\"button\" value=\"Sélectionner toutes les semaines\" onClick=\"this.form.semaines.value='0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51'\"><br/>" + 
				"Nous sommes en S" + String.valueOf((Calendar.getInstance().get(3) + 14) % 51) + ". La première semaine du premier quadrimestre est la semaine 0, et celle du second quadrimestre est la semaine 19</p>" +
				"<p><label for=\"projet\"><b>ID</b> du projet (pour 2014-2015, c'est 6): </label>" + 
				"<input type=\"text\" name=\"projet\" id=\"projet\" value=\"" + String.valueOf(projectID) + "\"/></p>" + 
				"<p><input type=\"checkbox\" name=\"deshurler\" id=\"deshurler\" checked=\"checked\"/><label for=\"deshurler\"><b>dé-HURLER</b> le nom des cours</label></p>" + 
				"<input type=\"hidden\" name=\"email\" id=\"email\" value=\"" + email + "\"/>" + 
				"<input type=\"submit\" class=\"zocial secondary\" value=\"Lancer\"  onclick=\"return confirm('Etes vous sur de vouloir continuer sans vous connecter ?\\nVotre horaire ICS ne sera alors plus modifiable dans le futur.\\nSi vous souhaitez vous connectez, appuyez sur Annuler et puis cliquez sur Se connecter en haut à droite.\\nPour continuer sans vous connecter, appuyez sur OK');\"/></center>" + 
				"</form>";
	}
	
	public static String beginFormCheckedCourses(String codes, String checkedCourses, String semaines, int projectID, String email) {
		return 	"<form class=\"top\" id=\"formulaire\" method=\"post\" action=\"http://ucl2icsphp.appspot.com/ade.php\">" +
				"<center><p><label for=\"codes\"><b>Codes cours</b> (séparés par virgules) ou <b>lien ADE</b> (donné par <a href=\"https://www.uclouvain.be/horaires-epl.html\" target=\"_blank\">l'outil horaire de l'EPL</a> ou celui de votre FAC): </label><br/>" +
				"<input type=\"text\" name=\"codes\" id=\"codes\" size=\"130\" value=\"" + codes + "\"/></p>" + 
				"<p><label for=\"semaines\"><b>Semaines</b> désirées (séparées par virgules): </label><br/>" + 
				"<input type=\"text\" name=\"semaines\" id=\"semaines\" value=\"" + semaines + "\" size=\"130\"/><br/>" + 
				"<input type=\"button\" value=\"Sélectionner le 1er quadrimestre\" onClick=\"this.form.semaines.value='0,1,2,3,4,5,6,7,8,9,10,11,12,13'\">" + 
				"<input type=\"button\" value=\"Sélectionner le 2ème quadrimestre\" onClick=\"this.form.semaines.value='19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34'\">" + 
				"<input type=\"button\" value=\"Sélectionner cette semaine\" onClick=\"this.form.semaines.value='"+String.valueOf((Calendar.getInstance().get(3) + 14) % 51)+"'\">" + 
				"<input type=\"button\" value=\"Sélectionner toutes les semaines\" onClick=\"this.form.semaines.value='0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51'\"><br/>" + 
				"Nous sommes en S" + String.valueOf((Calendar.getInstance().get(3) + 14) % 51) + ". La première semaine du premier quadrimestre est la semaine 0, et celle du second quadrimestre est la semaine 19</p>" +
				"<p><label for=\"projet\"><b>ID</b> du projet (pour 2014-2015, c'est 6): </label>" + 
				"<input type=\"text\" name=\"projet\" id=\"projet\" value=\"" + String.valueOf(projectID) + "\"/></p>" + 
				"<p><input type=\"checkbox\" name=\"deshurler\" id=\"deshurler\" checked=\"checked\"/><label for=\"deshurler\"><b>dé-HURLER</b> le nom des cours</label></p>" + 
				"<input type=\"hidden\" name=\"email\" id=\"email\" value=\"" + email + "\"/>" + 
				"<input type=\"hidden\" name=\"checkedCourses\" id=\"checkedCourses\" value=\"" + checkedCourses + "\"/>" + 
				"<input type=\"submit\" class=\"zocial secondary\" value=\"Lancer\" /></center>" + 
				"</form>";
	}
	
	public static String unsplitVirgule(String longTexte) {
		String longTextUnsplitted = longTexte.replaceAll(", ", ",");
		if(longTextUnsplitted.startsWith(" "))
			longTextUnsplitted = longTextUnsplitted.substring(1);
		if(longTextUnsplitted.endsWith(" "))
			longTextUnsplitted = longTextUnsplitted.substring(0,longTextUnsplitted.length()-1);
		return longTextUnsplitted;
	}
}