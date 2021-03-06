package model;

/**
 * This class represents a patient in the database
 * @author Andreas
 *
 */
public class Patient {

	private int patientId;
	
	private String firstName = "";
	
	private String lastName = "";
	
	private String town = "";
	
	private String streetName = "";
	
	private int streetNumber;
	
	private String phone = "";
	
	private String postalCode = "";
	
	private int age;
	
	private int doctorId;

	
	public Patient(){}


	public Patient(int patientId, String firstName, String lastName, String town, String streetName, int streetNumber,
			String phone, String postalCode, int age, int doctorId) {
		super();
		this.patientId = patientId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.town = town;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.phone = phone;
		this.postalCode = postalCode;
		this.age = age;
		this.doctorId = doctorId;
	}

	public int getPatientId() {
		return patientId;
	}


	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
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


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public int getDoctorId() {
		return doctorId;
	}


	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}


	@Override
	public String toString() {
		return patientId + " " + firstName + " " + lastName + " "
				+ town + " " + streetName + " " + streetNumber + " " + phone
				+ " " + postalCode + " " + age + " " + doctorId;
	}



}