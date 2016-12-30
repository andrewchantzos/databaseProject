package dao;

import java.util.List;

import model.Drug;

public interface DrugDAO {
	public void insert(Drug drug);
	public void insertWithId(Drug drug);
	
	public void update(Drug drug);
	public void delete(int drugId);
	public List<Drug> findAll();
	public Drug findById(int drugId);
}
