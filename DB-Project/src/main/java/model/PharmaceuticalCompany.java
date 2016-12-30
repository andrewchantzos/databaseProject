package model;

/**
 * This class represents a pharmaceutical company in the database
 * @author Andreas
 *
 */
public class PharmaceuticalCompany {

	private int pharmaceuticalCompanyId;
	
	private String name;
	
	private String phoneNumber;

	public PharmaceuticalCompany(){}
	
	public PharmaceuticalCompany(int pharmaceuticalCompanyId, String name, String phoneNumber) {
		super();
		this.pharmaceuticalCompanyId = pharmaceuticalCompanyId;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public int getPharmaceuticalCompanyId() {
		return pharmaceuticalCompanyId;
	}

	public void setPharmaceuticalCompanyId(int pharmaceuticalCompanyId) {
		this.pharmaceuticalCompanyId = pharmaceuticalCompanyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "PharmaceuticalCompany [pharmaceuticalCompanyId=" + pharmaceuticalCompanyId + ", name=" + name
				+ ", phoneNumber=" + phoneNumber + "]";
	}
	
	
}
