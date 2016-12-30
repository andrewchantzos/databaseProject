package db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import dao.DrugDAO;
import daoImpl.DrugDAOImpl;
import model.Drug;

public class DrugDaoTest {
	
	DrugDAO drugDao = new DrugDAOImpl();
	
	@Test
	public void insertTest() {
		Drug drug = new Drug();
		drug.setDrugId(5);
		drug.setName("Tantum Verde");
		drug.setFormula("2 stagones coke");
		drug.setPharmaceuticalCompanyId(1);
		drugDao.insert(drug);
	}
	
	@Test
	public void updateTest() {
		Drug drug = new Drug();
		drug.setDrugId(2);
		drug.setName("Tantum Verde");
		drug.setFormula("4 stagones coke");
		drug.setPharmaceuticalCompanyId(1);
		drugDao.update(drug);
	}
	
	@Test
	public void findAllTest() {
		List<Drug> drugs = drugDao.findAll();
		
		System.out.println(drugs);
		assertTrue(null != drugs);
	}
	
	@Test
	public void deleteTest() {
		drugDao.delete(3);
	}


}
