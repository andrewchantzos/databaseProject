package db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import dao.PrescriptionDAO;
import daoImpl.PrescriptionDAOImpl;
import model.Prescription;

public class PrescriptionDaoTest {


	PrescriptionDAO prescriptionDAO = new PrescriptionDAOImpl();
	
	@Test
	public void insertTest() {
		Prescription prescription = new Prescription();
		prescription.setDrugId(1);
		prescription.setDoctorId(1);
		prescription.setPatientId(1);
		prescription.setDate(java.sql.Date.valueOf("2015-10-19"));
		prescription.setQuantity(10);
		prescriptionDAO.insert(prescription);
	}
	
	@Test
	public void updateTest() {
		Prescription prescription = new Prescription();
		prescription.setDrugId(1);
		prescription.setDoctorId(1);
		prescription.setPatientId(1);
		prescription.setDate(java.sql.Date.valueOf("2015-10-19"));
		prescription.setQuantity(11);
		prescriptionDAO.update(prescription);
	}
	
	@Test
	public void findAllTest() {
		List<Prescription> prescriptions = prescriptionDAO.findAll();
		
		System.out.println(prescriptions);
		assertTrue(null != prescriptions);
	}
	
	@Test
	public void deleteTest() {
		prescriptionDAO.delete(1,1,1);
	}


}
