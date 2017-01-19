package queryModels;

public class DrugPriceInfo {

	private String drugName = "";
	private double averagePrice;
	private int pharmacyId;
	private String pharmacyName = "";
	private int minimumPrice;
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public double getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}
	public int getPharmacyId() {
		return pharmacyId;
	}
	public void setPharmacyId(int pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	public String getPharmacyName() {
		return pharmacyName;
	}
	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	public int getMinimumPrice() {
		return minimumPrice;
	}
	public void setMinimumPrice(int minPrice) {
		this.minimumPrice = minPrice;
	}
	@Override
	public String toString() {
		return drugName + " " + averagePrice + " " + pharmacyId
				+ " " + pharmacyName + " " + minimumPrice;
	}

	
}
