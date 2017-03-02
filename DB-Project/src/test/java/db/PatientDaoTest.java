package db;

import static org.junit.Assert.assertTrue;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.junit.Test;

import dao.PatientDAO;
import daoImpl.PatientDAOImpl;
import model.Patient;

public class PatientDaoTest {

	
	PatientDAO patientDao = new PatientDAOImpl();
	
	@Test
	public void insertTest() throws SQLIntegrityConstraintViolationException {
		Patient patient = new Patient();
		patient.setAge(21);
		patient.setFirstName("Andreas");
		patient.setLastName("Chantzos");
		patient.setPhone("2109245041");
		patient.setPostalCode("11741");
		patient.setStreetName("Amynandrou");
		patient.setStreetNumber(10);
		patient.setPatientId(20);
		patient.setDoctorId(1);
		patient.setTown("Athens");
		
		patientDao.insert(patient);
	}
	
	@Test
	public void updateTest() throws SQLIntegrityConstraintViolationException {
		Patient patient = new Patient();
		patient.setAge(31);
		patient.setFirstName("Andreas");
		patient.setLastName("Chantzos");
		patient.setPhone("2109245041");
		patient.setPostalCode("11741");
		patient.setStreetName("Amynandrou");
		patient.setStreetNumber(10);
		patient.setPatientId(8);
		patient.setDoctorId(1);
		patient.setTown("Athens");
		patientDao.update(patient);
	}
	
	@Test
	public void findAllTest() {
		List<Patient> patients = patientDao.findAll();
		
		System.out.println(patients);
		assertTrue(null != patients);
	}
	
	@Test
	public void deleteTest() {
		patientDao.delete(8);
	}

}
