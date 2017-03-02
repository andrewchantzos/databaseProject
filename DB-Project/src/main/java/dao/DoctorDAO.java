package dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import model.Doctor;

public interface DoctorDAO {

	public void insert(Doctor doctor) throws SQLIntegrityConstraintViolationException;;
	public void insertWithId(Doctor doctor);
	
	public void update(Doctor doctor) throws SQLIntegrityConstraintViolationException;;
	public void delete(int doctorId);
	public List<Doctor> findAll();
	public Doctor findById(int doctorId);
	public List<Doctor> findAllFilter(String filter);
}
