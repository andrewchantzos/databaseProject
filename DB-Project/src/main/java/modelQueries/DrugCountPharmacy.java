package modelQueries;

public class DrugCountPharmacy {


	private int pharmacyId;
	
	private String name = "";
	
	private String town = "";
	
	private String streetName = "";
	
	private int streetNumber;
	
	private String postalCode = "";
	
	private String phoneNumber = "";

	private int numberOfDrugs = 0;


	@Override
	public String toString() {
		return pharmacyId + " " + name + " " + town + " "
				+ streetName + " " + streetNumber + " " + postalCode + " "
				+ phoneNumber + " " + numberOfDrugs;
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

	public int getNumberOfDrugs() {
		return numberOfDrugs;
	}

	public void setNumberOfDrugs(int numberOfDrugs) {
		this.numberOfDrugs = numberOfDrugs;
	}

	
	
}
