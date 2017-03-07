package model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class represents a Prescription in the database
 * @author Andreas
 *
 */
public class Prescription {

	private int patientId;
	
	private int doctorId;
	
	private int drugId;
	
	private Date date;
	
	private int quantity;

	
	public Prescription(){}
	
	
	public Prescription(int patientId, int doctorId, int drugId, Date date, int quantity) {
		super();
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.drugId = drugId;
		this.date = date;
		this.quantity = quantity;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getDrugId() {
		return drugId;
	}

	public void setDrugId(int drugId) {
		this.drugId = drugId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		try {
			this.date = (Date) sdf1.parse(date.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return patientId + " " + doctorId + " " + drugId + " "
				+ date + " " + quantity;
	}
	
	
}
