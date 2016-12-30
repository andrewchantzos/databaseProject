package model;

/**
 * This class represents a Drug in the database
 * @author Andreas
 *
 */
public class Drug {

	private int drugId;
	
	private String name;
	
	private String formula;
	
	private int pharmaceuticalCompanyId;

	
	public Drug(){}
	
	public Drug(int drugId, String name, String formula, int pharmaceuticalCompanyId) {
		super();
		this.drugId = drugId;
		this.name = name;
		this.formula = formula;
		this.pharmaceuticalCompanyId = pharmaceuticalCompanyId;
	}

	public int getDrugId() {
		return drugId;
	}

	public void setDrugId(int drugId) {
		this.drugId = drugId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public int getPharmaceuticalCompanyId() {
		return pharmaceuticalCompanyId;
	}

	public void setPharmaceuticalCompanyId(int pharmaceuticalCompanyId) {
		this.pharmaceuticalCompanyId = pharmaceuticalCompanyId;
	}

	@Override
	public String toString() {
		return "Drug [drugId=" + drugId + ", name=" + name + ", formula=" + formula + ", pharmaceuticalCompanyId="
				+ pharmaceuticalCompanyId + "]";
	}
	
	
}
