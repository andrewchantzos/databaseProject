package dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import model.Pharmacy;

public interface PharmacyDAO {
	public void insert(Pharmacy pharmacy) throws SQLIntegrityConstraintViolationException;
	public void insertWithId(Pharmacy pharmacy);
	
	public void update(Pharmacy pharmacy) throws SQLIntegrityConstraintViolationException;
	public void delete(int pharmacyId);
	public List<Pharmacy> findAll();
	public Pharmacy findById(int pharmacyId);
	public List<Pharmacy> findAllFilter(String filter);

}
