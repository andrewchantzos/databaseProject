package dao;

import java.util.List;

import model.Patient;

public interface PatientDAO {
	public void insert(Patient patient);
	public void insertWithId(Patient patient);
	
	public void update(Patient patient);
	public void delete(int patientId);
	public List<Patient> findAll();
	public Patient findById(int patientId);
	public List<Patient> findAllFilter(String filter);

}
