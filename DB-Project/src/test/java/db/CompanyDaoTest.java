package db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import dao.DoctorDAO;
import dao.PharmaceuticalCompanyDAO;
import daoImpl.DoctorDAOImpl;
import daoImpl.PharmaceuticalCompanyDAOImpl;
import model.Doctor;
import model.PharmaceuticalCompany;

public class CompanyDaoTest {

	PharmaceuticalCompanyDAO companyDao = new PharmaceuticalCompanyDAOImpl();
	
	@Test
	public void insertTest() {
		PharmaceuticalCompany company = new PharmaceuticalCompany(10, "Chantzos AE", "9245041");
		
		companyDao.insert(company);
	}
	
	@Test
	public void updateTest() {

		PharmaceuticalCompany company = new PharmaceuticalCompany(4, "Chantzos AE", "9245042");
		companyDao.update(company);
	}
	
	@Test
	public void findAllTest() {
		List<PharmaceuticalCompany> companies = companyDao.findAll();
		
		System.out.println(companies);
		assertTrue(null != companies);
	}
	
	@Test
	public void deleteTest() {
		companyDao.delete(4);
	}
}
