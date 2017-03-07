package modelQueries;

public class PatientsUpdatable {
	
	private int patientId;
	
	private String firstName = "";
	
	private String lastName = "";
	
	private String town = "";

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

	@Override
	public String toString() {
		return patientId + " " + firstName + " " + lastName
				+ " " + town;
	}
	
	
}
