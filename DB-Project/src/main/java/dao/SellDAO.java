package dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import model.Sell;

public interface SellDAO {
	public void insert(Sell sell) throws SQLIntegrityConstraintViolationException;
	
	public void update(Sell sell)throws SQLIntegrityConstraintViolationException;
	
	public void delete(int pharmacyId, int drugId, int companyId);
	public List<Sell> findAll();
	public Sell findById(int pharmacyId, int drugId, int companyId);
	public List<Sell> findAllFilter(String filter);

}
