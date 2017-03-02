package dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import model.Prescription;

public interface PrescriptionDAO {
	public void insert(Prescription prescription);
	
	public void update(Prescription prescription);
	public void delete(int patientId, int doctorId, int drugId);
	public List<Prescription> findAll();
	public Prescription findById(int patientId, int doctorId, int drugId);
	public List<Prescription> findAllFilter(String filter);

}
