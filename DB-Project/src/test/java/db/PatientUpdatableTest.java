package db;

import static org.junit.Assert.assertTrue;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.junit.Test;

import dao.PatientUpdatableDAO;
import daoImpl.PatientsUpdatableDAOImpl;
import modelQueries.PatientsUpdatable;

public class PatientUpdatableTest {

	
	PatientUpdatableDAO patientDao = new PatientsUpdatableDAOImpl();
	
	@Test
	public void insertTest() throws SQLIntegrityConstraintViolationException {
		PatientsUpdatable patient = new PatientsUpdatable();

		patient.setFirstName("Andreas");
		patient.setLastName("Chantzos");

		patient.setTown("Athens");
		
		patientDao.insert(patient);
	}
	
	@Test
	public void updateTest() throws SQLIntegrityConstraintViolationException {
		PatientsUpdatable patient = new PatientsUpdatable();

		patient.setFirstName("Andreas");
		patient.setLastName("Chantzos");

		patient.setPatientId(8);
		patient.setTown("Athens");
		patientDao.update(patient);
	}
	
	@Test
	public void findAllTest() {
		List<PatientsUpdatable> patients = patientDao.findAll();
		
		System.out.println(patients);
		assertTrue(null != patients);
	}
	
	@Test
	public void deleteTest() {
		patientDao.delete(8);
	}

}
