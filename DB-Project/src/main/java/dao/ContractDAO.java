package dao;

import java.util.List;

import model.Contract;
import model.Doctor;

public interface ContractDAO {
	public void insert(Contract contract);
	
	public void update(Contract contract);
	public void delete(int pharmacyId, int pharmaceuticalCompanyId);
	public List<Contract> findAll();
	public Contract findById(int pharmacyId, int pharmaceuticalCompanyId);
	public List<Contract> findAllFilter(String filter);

}
