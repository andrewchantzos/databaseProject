package queryModels;

public class DoctorWithOldPatients {

	private int doctorId;
	
	private String firstName = "";
	
	private String lastName = "";
	
	private String speciality = "";
	
	private double averageAge;

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
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

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public double getAverageAge() {
		return averageAge;
	}

	public void setAverageAge(double averageAge) {
		this.averageAge = averageAge;
	}

	@Override
	public String toString() {
		return doctorId + " " + firstName + " " + lastName + " " + speciality+ " " + averageAge;
	}
	
	
}
