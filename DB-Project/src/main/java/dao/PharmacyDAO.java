package dao;

import java.util.List;

import model.Pharmacy;

public interface PharmacyDAO {
	public void insert(Pharmacy pharmacy);
	public void insertWithId(Pharmacy pharmacy);
	
	public void update(Pharmacy pharmacy);
	public void delete(int pharmacyId);
	public List<Pharmacy> findAll();
	public Pharmacy findById(int pharmacyId);
	public List<Pharmacy> findAllFilter(String filter);

}
