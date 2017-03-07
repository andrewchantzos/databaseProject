package model;

import java.sql.Date;

/**
 * This class represents a Contract
 * @author Andreas
 *
 */
public class Contract {

	
	private int pharmaceuticalCompanyId;
	
	private int pharmacyId;
	
	private Date startDate;
	
	private Date endDate;
	
	private String text = "";
	
	private String supervisor = "";

	
	public Contract(){}
	
	
	public Contract(int pharmaceuticalCopmanyId, int pharmacyId, Date startDate, Date endDate, String text,
			String supervisor) {
		super();
		this.pharmaceuticalCompanyId = pharmaceuticalCopmanyId;
		this.pharmacyId = pharmacyId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.text = text;
		this.supervisor = supervisor;
	}

	public int getPharmaceuticalCopmanyId() {
		return pharmaceuticalCompanyId;
	}

	public void setPharmaceuticalCopmanyId(int pharmaceuticalCopmanyId) {
		this.pharmaceuticalCompanyId = pharmaceuticalCopmanyId;
	}

	public int getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(int pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}


	@Override
	public String toString() {
		return pharmaceuticalCompanyId + " " + pharmacyId
				+ " " + startDate + " " + endDate + ", text=" + text + " " + supervisor;
	}	
	
	
}
