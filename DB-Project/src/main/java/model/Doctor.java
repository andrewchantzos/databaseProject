package model;

/**
 * This class represents a Doctor in the database
 * @author Andreas
 *
 */
public class Doctor {

	private int doctorId;
	
	private String firstName;
	
	private String lastName;
	
	private String speciality;
	
	private int experience;

	
	public Doctor(){}
	
	public Doctor(int doctorId, String firstName, String lastName, String speciality, int experience) {
		super();
		this.doctorId = doctorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.speciality = speciality;
		this.experience = experience;
	}

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

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	@Override
	public String toString() {
		return "Doctor [doctorId=" + doctorId + ", firstName=" + firstName + ", lastName=" + lastName + ", speciality="
				+ speciality + ", experience=" + experience + "]";
	}
	
	
}
