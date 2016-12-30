package db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import dao.SellDAO;
import daoImpl.SellDAOImpl;
import model.Sell;

public class SellDaoTest {

	SellDAO sellDao = new SellDAOImpl();
	
	@Test
	public void insertTest() {
		Sell sell = new Sell();
		sell.setCompanyId(1);
		sell.setDrugId(1);
		sell.setPharmacyId(2);
		sell.setPrice(45);
		sellDao.insert(sell);
	}
	
	@Test
	public void updateTest() {
		Sell sell = new Sell();
		sell.setCompanyId(1);
		sell.setDrugId(1);
		sell.setPharmacyId(2);
		sell.setPrice(50);
		sellDao.update(sell);
	}
	
	@Test
	public void findAllTest() {
		List<Sell> sells = sellDao.findAll();
		
		System.out.println(sells);
		assertTrue(null != sells);
	}
	
	@Test
	public void deleteTest() {
		sellDao.delete(2,1,1);
	}


}
