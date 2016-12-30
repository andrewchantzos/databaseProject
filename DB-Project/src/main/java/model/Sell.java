package model;

/**
 * This class represents a Sell in the database
 * @author Andreas
 *
 */
public class Sell {

	private int pharmacyId;
	
	private int drugId;
	
	private int price;

	private int companyId;
	
	public Sell(){}

	
	
	public Sell(int pharmacyId, int drugId, int price, int companyId) {
		super();
		this.pharmacyId = pharmacyId;
		this.drugId = drugId;
		this.price = price;
		this.companyId = companyId;
	}



	public int getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(int pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public int getDrugId() {
		return drugId;
	}

	public void setDrugId(int drugId) {
		this.drugId = drugId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "Sell [pharmacyId=" + pharmacyId + ", drugId=" + drugId + ", price=" + price + ", companyId=" + companyId
				+ "]";
	}
	
	
}