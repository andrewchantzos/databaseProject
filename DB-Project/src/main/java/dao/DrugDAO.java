package dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import model.Drug;

public interface DrugDAO {
	public void insert(Drug drug) throws SQLIntegrityConstraintViolationException;
	public void insertWithId(Drug drug);
	
	public void update(Drug drug) throws SQLIntegrityConstraintViolationException;
	public void delete(int drugId);
	public List<Drug> findAll();
	public Drug findById(int drugId);
	public List<Drug> findAllFilter(String filter);

}
