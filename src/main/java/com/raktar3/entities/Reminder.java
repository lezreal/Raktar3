package com.raktar3.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Reminder {

	public Reminder() {}
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	Integer id;
	
	@OneToOne
	Machine machine;

	@Column(columnDefinition="tinyint(1) default 1")
	boolean status=true;
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Column(nullable=false)
	String comment;
	
	@Column(nullable=false)
	String date;    // lejárati idő
	
	@Column(nullable=false)
	int alerttime;

	public int getAlerttime() {
		return alerttime;
	}

	public void setAlerttime(int alerttime) {
		this.alerttime = alerttime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
