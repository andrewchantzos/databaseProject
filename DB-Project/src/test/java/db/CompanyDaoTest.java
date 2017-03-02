package db;

import static org.junit.Assert.assertTrue;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.junit.Test;

import dao.PharmaceuticalCompanyDAO;
import daoImpl.PharmaceuticalCompanyDAOImpl;
import model.PharmaceuticalCompany;

public class CompanyDaoTest {

	PharmaceuticalCompanyDAO companyDao = new PharmaceuticalCompanyDAOImpl();
	
	@Test
	public void insertTest() throws SQLIntegrityConstraintViolationException {
		PharmaceuticalCompany company = new PharmaceuticalCompany(10, "Chantzos AE", "9245041");
		
		companyDao.insert(company);
	}
	
	@Test
	public void updateTest() throws SQLIntegrityConstraintViolationException {

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
