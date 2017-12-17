package com.example.todolists;

public class Task {

	private String subject;
	private String duedate;
	private String priority;
	private String status;
	private int id;

	public Task(int id, String subject2, String duedate2, String priority2,String status2) {
		this.setId(id);
		this.setSubject(subject2);
		this.setDuedate(duedate2);
		this.setPriority(priority2);
		this.setStatus(status2);
		
	}

	public Task(String subject, String duedate, String priority) {
		// TODO Auto-generated constructor stub
		this.setSubject(subject);
		this.setDuedate(duedate);
		this.setPriority(priority);
		this.setStatus(status);
	}

	public Task() {
		// TODO Auto-generated constructor stub
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
