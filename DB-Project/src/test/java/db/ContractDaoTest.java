package db;

import static org.junit.Assert.assertTrue;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

import dao.ContractDAO;
import daoImpl.ContractDAOImpl;
import model.Contract;

public class ContractDaoTest {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	
	ContractDAO contractDAO = new ContractDAOImpl();
	
	@Test
	public void insertTest() throws SQLIntegrityConstraintViolationException {
		Contract contract = new Contract();
		contract.setPharmaceuticalCopmanyId(1);
		contract.setPharmacyId(1);
		contract.setStartDate(java.sql.Date.valueOf("2013-10-19"));
		contract.setEndDate(java.sql.Date.valueOf("2015-10-19"));
		contract.setSupervisor("andrew");
		contract.setText("Tha sas fero xapia");
		contractDAO.insert(contract);
	}
	
	@Test
	public void updateTest() throws SQLIntegrityConstraintViolationException {
		Contract contract = new Contract();
		contract.setPharmaceuticalCopmanyId(1);
		contract.setPharmacyId(1);
		contract.setStartDate(java.sql.Date.valueOf("2013-10-19"));
		contract.setEndDate(java.sql.Date.valueOf("2015-10-19"));
		contract.setSupervisor("andrew");
		contract.setText("Tha sas fero drogia");
		contractDAO.update(contract);
	}
	
	@Test
	public void findAllTest() {
		List<Contract> contracts = contractDAO.findAll();
		
		System.out.println(contracts);
		assertTrue(null != contracts);
	}
	
	@Test
	public void deleteTest() {
		contractDAO.delete(2,2);
	}

}
