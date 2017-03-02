package dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import model.Contract;

public interface ContractDAO {
	public void insert(Contract contract) throws SQLIntegrityConstraintViolationException;
	
	public void update(Contract contract) throws SQLIntegrityConstraintViolationException;
	public void delete(int pharmacyId, int pharmaceuticalCompanyId);
	public List<Contract> findAll();
	public Contract findById(int pharmacyId, int pharmaceuticalCompanyId);
	public List<Contract> findAllFilter(String filter);

}
