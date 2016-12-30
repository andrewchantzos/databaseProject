package dao;

import java.util.List;

import model.Doctor;

public interface DoctorDAO {

	public void insert(Doctor doctor);
	public void insertWithId(Doctor doctor);
	
	public void update(Doctor doctor);
	public void delete(int doctorId);
	public List<Doctor> findAll();
	public Doctor findById(int doctorId);

}
