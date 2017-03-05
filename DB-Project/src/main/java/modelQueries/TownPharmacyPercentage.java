package modelQueries;

public class TownPharmacyPercentage {

	private String town = "";
	
	private int count;
	private String percentage;
	
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	@Override
	public String toString() {
		return town + " " + count + " " + percentage;
	}
	
}
