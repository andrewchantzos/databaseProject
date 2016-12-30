package db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import dao.PharmacyDAO;
import daoImpl.PharmacyDAOImpl;
import model.Doctor;
import model.Pharmacy;

public class PharmacyDaoTest {

	PharmacyDAO pharmacyDAO = new PharmacyDAOImpl();
	
	@Test
	public void insertTest() {
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setName("Kitsos");
		pharmacy.setPharmacyId(5);
		pharmacy.setPhoneNumber("2109892842");
		pharmacy.setPostalCode("11741");
		pharmacy.setStreetName("Servias");
		pharmacy.setTown("Athens");
		pharmacy.setStreetNumber(10);
		pharmacyDAO.insert(pharmacy);
	}
	
	@Test
	public void updateTest() {
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setName("Kitsos");
		pharmacy.setPharmacyId(4);
		pharmacy.setPhoneNumber("2109892842");
		pharmacy.setPostalCode("11741");
		pharmacy.setStreetName("Servias");
		pharmacy.setTown("Athens");
		pharmacy.setStreetNumber(11);
		pharmacyDAO.update(pharmacy);
	}
	
	@Test
	public void findAllTest() {
		List<Pharmacy> pharmacies = pharmacyDAO.findAll();
		
		System.out.println(pharmacies);
		assertTrue(null != pharmacies);
	}
	
	@Test
	public void deleteTest() {
		pharmacyDAO.delete(4);
	}
}
