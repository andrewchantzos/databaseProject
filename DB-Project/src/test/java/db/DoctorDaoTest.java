package db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import dao.DoctorDAO;
import daoImpl.DoctorDAOImpl;
import model.Doctor;

public class DoctorDaoTest {

	
	DoctorDAO doctorDao = new DoctorDAOImpl();
	
	@Test
	public void insertTest() {
		Doctor doctor = new Doctor();
		doctor.setDoctorId(10);
		doctor.setExperience(10);
		doctor.setFirstName("Klark");
		doctor.setLastName("Kent");
		doctor.setSpeciality("urologist");
		doctorDao.insert(doctor);
	}
	
	@Test
	public void updateTest() {
		Doctor doctor = new Doctor();
		doctor.setDoctorId(10);
		doctor.setExperience(10);
		doctor.setFirstName("Klark");
		doctor.setLastName("Kent");
		doctor.setSpeciality("cardiologist");
		doctorDao.update(doctor);
	}
	
	@Test
	public void findAllTest() {
		List<Doctor> doctors = doctorDao.findAll();
		
		System.out.println(doctors);
		assertTrue(null != doctors);
	}
	
	@Test
	public void deleteTest() {
		doctorDao.delete(10);
	}

}
