package dao;

import java.util.List;

import model.PharmaceuticalCompany;

public interface PharmaceuticalCompanyDAO {
	public void insert(PharmaceuticalCompany pharmaceuticalCompany);
	public void insertWithId(PharmaceuticalCompany pharmaceuticalCompany);
	
	public void update(PharmaceuticalCompany pharmaceuticalCompany);
	public void delete(int pharmaceuticalCompanyId);
	public List<PharmaceuticalCompany> findAll();
	public PharmaceuticalCompany findById(int pharmaceuticalCompanyId);
	public List<PharmaceuticalCompany> findAllFilter(String filter);

}
