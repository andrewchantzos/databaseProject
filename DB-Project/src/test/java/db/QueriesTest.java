package db;

import java.util.List;

import org.junit.Test;

import model.Doctor;
import queryModels.DrugPriceInfo;
import queryModels.PharmacyWithAllDrugsInCity;
import queryModels.ValidContract;
import sqlQueries.Queries;

public class QueriesTest {

	Queries queries = new Queries();
	
	@Test
	public void validContracts() {
		List<ValidContract> contracts = queries.findValidContracts();
		System.out.println(contracts);
	}
	
	@Test
	public void specialityTest() {
		List<Doctor> doctors = queries.findDoctorsBySpecialtiy("Cardiology");
		System.out.println(doctors);
	}

	@Test
	public void drugPriceTest() {
		List<DrugPriceInfo> l = queries.drugMinAndAVG();
		System.out.println(l);
	}
	
	@Test
	public void pharmacyWithAllDrugsInCity() {
		List<PharmacyWithAllDrugsInCity> l = queries.pharmaciesWithAllDrugsInSameCity();
		System.out.println(l);
	}
}
