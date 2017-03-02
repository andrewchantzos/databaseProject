package dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import model.Patient;

public interface PatientDAO {
	public void insert(Patient patient) throws SQLIntegrityConstraintViolationException;
	public void insertWithId(Patient patient);
	
	public void update(Patient patient) throws SQLIntegrityConstraintViolationException;
	public void delete(int patientId);
	public List<Patient> findAll();
	public Patient findById(int patientId);
	public List<Patient> findAllFilter(String filter);

}
