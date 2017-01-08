package dao;

import java.util.List;

import model.Sell;

public interface SellDAO {
	public void insert(Sell sell);
	
	public void update(Sell sell);
	public void delete(int pharmacyId, int drugId, int companyId);
	public List<Sell> findAll();
	public Sell findById(int pharmacyId, int drugId, int companyId);
	public List<Sell> findAllFilter(String filter);

}
