package model;

/**
 * This class represents a Pharmacy in the database
 * @author Andreas
 *
 */
public class Pharmacy {

	private int pharmacyId;
	
	private String name = "";
	
	private String town = "";
	
	private String streetName = "";
	
	private int streetNumber;
	
	private String postalCode = "";
	
	private String phoneNumber = "";

	
	public Pharmacy(){}
	
	public Pharmacy(int pharmacyId, String name, String town, String streetName, int streetNumber, String postalCode,
			String phoneNumber) {
		super();
		this.pharmacyId = pharmacyId;
		this.name = name;
		this.town = town;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
	}

	public int getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(int pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return  pharmacyId + " " + name + " " + town + " " + streetName
				+ " " + streetNumber + " " + postalCode + " " + phoneNumber;
	}
	
	
}
