/*
 * UCL2ICS v3 APP ENGINE EDITION
 * By Corentin Damman
 */

package com.coconuts.ucl2ics;
 
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
 
@PersistenceCapable
public class StudentAgenda {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long key;
	 
	@Persistent
	private Text codes;
	 
	@Persistent
	private Text courses;
	 
	@Persistent
	private String semaines;
	 
	@Persistent
	private int projectID;
	 
	@Persistent
	private int STFU;
	 
	@Persistent
	private int TPorCM;
	 
	@Persistent
	private long counter;
	
	@Persistent
	private String email;
	 
	public StudentAgenda(Long key, Text codes, Text courses, String semaines, int projectID, int STFU, int TPorCM, String email) {
		this.key = key;
		this.codes = codes;
		this.courses = courses;
		this.semaines = semaines;
		this.projectID = projectID;
		this.STFU = STFU;
		this.TPorCM = TPorCM;
		this.counter = 0;
		this.email = email;
	}
	 
	public Long getKey() {
	    return key;
	}
	 
	public void setCodes(Text codes) {
		this.codes = codes;
	}
 
	public Text getCodes() {
		return codes;
	}
	 
	public void setCourses(Text courses) {
		this.courses = courses;
	}

	public Text getCourses() {
		return courses;
	}
 
	public void setSemaines(String semaines) {
		this.semaines = semaines;
	}
 
	public String getSemaines() {
		return semaines;
	}
 
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
 
	public int getProjectID() {
		return projectID;
	}
 
	public void setSTFU(int STFU) {
		this.STFU = STFU;
	}
 
	public int getSTFU() {
		return STFU;
	}
	 
	public void setTPorCM(int TPorCM) {
		this.TPorCM = TPorCM;
	}
	 
	public int getTPorCM() {
		return TPorCM;
	}
	
	public String getEmail() {
	    return email;
	}
	
	public void setEmail(String email) {
	    this.email = email;
	}
 
	public String getAll() {
		return 	"<td><center>&nbsp;<b>" + key + "</b>&nbsp;</center></td>" + 
				"<td><center>&nbsp;" + codes.getValue().replaceAll(",", ", ") + "&nbsp;</center></td>" + 
				"<td><center>&nbsp;" + courses.getValue().replaceAll(",", ", ") + "&nbsp;</center></td>" + 
				"<td><center>&nbsp;" + semaines.replaceAll(",", ", ") + "&nbsp;</center></td>" + 
				"<td><center>&nbsp;" + String.valueOf(projectID) + "&nbsp;<br>" + 
							"&nbsp;" + OnValueOf(STFU) + "&nbsp;<br>" + 
							"&nbsp;" + OnValueOf(TPorCM) + "&nbsp;</center></td>" + 
				"<td><center>&nbsp;" + String.valueOf(counter) + "&nbsp;<br>" +
							"&nbsp;" + email + "&nbsp;<br>" + 
							"&nbsp;&nbsp;</center></td>" + 
				"<td><center>&nbsp;<a href=\"get?key=" + key + "\" target=\"_blank\">Agenda</a>&nbsp;<br>" +
							"&nbsp;<a href=\"modif?key=" + key + "\">Modifier</a>&nbsp;<br>" +
							"&nbsp;<a href=\"del?key=" + key + "\">Supprimer</a>&nbsp;</center></td>";
	}
 
	public void addCount() {
		this.counter++;
	}
	
	public void setCounter(long counter) {
		this.counter = counter;
	}
	
	public long getCounter() {
		return counter;
	}
	
	private String OnValueOf(int bool) {
		return (bool==1) ? "On" : "Off";
	}
}