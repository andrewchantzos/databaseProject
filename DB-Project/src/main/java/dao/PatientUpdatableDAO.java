package dao;

import java.util.List;

import modelQueries.PatientsUpdatable;

public interface PatientUpdatableDAO {

	public void insert(PatientsUpdatable patient) ;
	
	public void update(PatientsUpdatable patient);
	public void delete(int patientId);
	public List<PatientsUpdatable> findAll();
	public PatientsUpdatable findById(int patientId);
	public List<PatientsUpdatable> findAllFilter(String filter);
}
