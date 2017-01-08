package queryModels;

import java.sql.Date;

public class ValidContract {

	private String companyName = "";
	
	private String pharmacyName = "";
	
	private String supervisor = "";
	
	private String text = "";
	
	private Date endDate;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return companyName + " " + pharmacyName + " "
				+ supervisor + " " + text + " " + endDate;
	}
	
	
	
}
