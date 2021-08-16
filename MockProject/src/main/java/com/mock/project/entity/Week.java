package com.mock.project.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Week")
public class Week implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "weekID", nullable = false)
	private long weekID;
	
	public Week() {
		// TODO Auto-generated constructor stub
	}

	public long getWeekID() {
		return weekID;
	}

	public void setWeekID(long weekID) {
		this.weekID = weekID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}