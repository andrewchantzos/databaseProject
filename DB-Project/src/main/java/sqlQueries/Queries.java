package sqlQueries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Doctor;
import queryModels.DrugPriceInfo;
import queryModels.PharmacyWithAllDrugsInCity;
import queryModels.ValidContract;
import util.DBUtil;

public class Queries {

	private Connection conn;

	public Queries() {
		conn = DBUtil.getConnection();
	}

	public List<ValidContract> findValidContracts() {
		List<ValidContract> list = new ArrayList<ValidContract>();

		String query = "SELECT C.name, P.name, Con.Text, Con.supervisor, Con.END_DATE FROM pharmacies P "
				+ "JOIN contracts Con ON P.pharmacy_id = Con.pharmacy_id "
				+ "JOIN  companies C ON C.company_id = Con.company_id "
				+ "WHERE Con.start_date < now() and Con.end_date > now()";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				ValidContract validContract = new ValidContract();
				validContract.setCompanyName(resultSet.getString(1));
				validContract.setPharmacyName(resultSet.getString(2));
				validContract.setEndDate(resultSet.getDate("end_date"));
				validContract.setSupervisor(resultSet.getString("supervisor"));
				validContract.setText(resultSet.getString("text"));
				list.add(validContract);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<ValidContract> findValidContractsFilter(String filter) {
		List<ValidContract> list = new ArrayList<ValidContract>();

		String query = "SELECT C.name, P.name, Con.Text, Con.supervisor, Con.END_DATE FROM pharmacies P "
				+ "JOIN contracts Con ON P.pharmacy_id = Con.pharmacy_id "
				+ "JOIN  companies C ON C.company_id = Con.company_id "
				+ "WHERE Con.start_date < now() and Con.end_date > now()";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				ValidContract validContract = new ValidContract();
				validContract.setCompanyName(resultSet.getString(1));
				validContract.setPharmacyName(resultSet.getString(2));
				validContract.setEndDate(resultSet.getDate("end_date"));
				validContract.setSupervisor(resultSet.getString("supervisor"));
				validContract.setText(resultSet.getString("text"));
				if (validContract.toString().toLowerCase().contains(filter.toLowerCase()))
					list.add(validContract);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Doctor> findDoctorsBySpecialtiy(String speciality) {
		List<Doctor> list = new ArrayList<Doctor>();

		String query = "SELECT firstname, lastname, experience_years " + "FROM doctors " + "where speciality = '"
				+ speciality + "' " + "ORDER BY experience_years DESC";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Doctor doctor = new Doctor();
				doctor.setFirstName(resultSet.getString(1));
				doctor.setLastName(resultSet.getString(2));
				doctor.setExperience(resultSet.getInt(3));
				list.add(doctor);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Doctor> findDoctorsBySpecialtiyFilter(String speciality, String filter) {
		List<Doctor> list = new ArrayList<Doctor>();

		String query = "SELECT firstname, lastname, experience_years " + "FROM doctors " + "where speciality = '"
				+ speciality + "' " + "ORDER BY experience_years DESC";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Doctor doctor = new Doctor();
				doctor.setFirstName(resultSet.getString(1));
				doctor.setLastName(resultSet.getString(2));
				doctor.setExperience(resultSet.getInt(3));
				String d = doctor.getFirstName() + " " + doctor.getLastName() + " " + doctor.getExperience();
				if (d.toLowerCase().contains(filter.toLowerCase()))
					list.add(doctor);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<DrugPriceInfo> drugMinAndAVG() {
		List<DrugPriceInfo> list = new ArrayList<DrugPriceInfo>();

		String query = "SELECT D.NAME, AVG(S.PRICE),  P.PHARMACY_ID, P.NAME ,MIN(S.PRICE) "
				+ "FROM PHARMACIES AS P, DRUGS AS D, SELLS AS S "
				+ "WHERE P.PHARMACY_ID = D.DRUG_ID AND S.DRUG_ID = D.DRUG_ID "
				+ "GROUP BY D.NAME, P.NAME, P.PHARMACY_ID";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				DrugPriceInfo info = new DrugPriceInfo();
				info.setAveragePrice(resultSet.getDouble(2));
				info.setDrugName(resultSet.getString(1));
				info.setPharmacyId(resultSet.getInt(3));
				info.setPharmacyName(resultSet.getString(4));
				info.setMinimumPrice(resultSet.getInt(5));
				list.add(info);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<DrugPriceInfo> drugMinAndAVGFilter(String filter) {
		List<DrugPriceInfo> list = new ArrayList<DrugPriceInfo>();

		String query = "SELECT D.NAME, AVG(S.PRICE),  P.PHARMACY_ID, P.NAME ,MIN(S.PRICE) "
				+ "FROM PHARMACIES AS P, DRUGS AS D, SELLS AS S "
				+ "WHERE P.PHARMACY_ID = D.DRUG_ID AND S.DRUG_ID = D.DRUG_ID "
				+ "GROUP BY D.NAME, P.NAME, P.PHARMACY_ID";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				DrugPriceInfo info = new DrugPriceInfo();
				info.setAveragePrice(resultSet.getDouble(2));
				info.setDrugName(resultSet.getString(1));
				info.setPharmacyId(resultSet.getInt(3));
				info.setPharmacyName(resultSet.getString(4));
				info.setMinimumPrice(resultSet.getInt(5));
				if (info.toString().toLowerCase().contains(filter.toLowerCase()))
					list.add(info);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PharmacyWithAllDrugsInCity> pharmaciesWithAllDrugsInSameCity() {
		List<PharmacyWithAllDrugsInCity> list = new ArrayList<PharmacyWithAllDrugsInCity>();

		String query = "SELECT PA.PATIENT_ID, PA.FIRSTNAME, PA.LASTNAME, PH.* "
				+"FROM pharmacies as PH, patients as PA "
				+"WHERE "
				+	"(SELECT D.DRUG_ID "
				+	"FROM drugs as D, sells as S "
				+   "WHERE D.DRUG_ID = S.DRUG_ID AND PH.PHARMACY_ID = S.PHARMACY_ID and PA.TOWN = PH.TOWN "
				+    ") "
				+"in "
				+    "(SELECT D.DRUG_ID "
				+   "FROM prescriptions as PR, drugs as D "
				+    "WHERE PR.PATIENT_ID = PA.PATIENT_ID "
				+    ")";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				PharmacyWithAllDrugsInCity pharmacy = new PharmacyWithAllDrugsInCity();
				pharmacy.setPatientId(resultSet.getInt(1));
				pharmacy.setPatientFirstName(resultSet.getString(2));
				pharmacy.setPatientLastName(resultSet.getString(3));

				pharmacy.setPharmacyId(resultSet.getInt("pharmacy_id"));
				pharmacy.setName(resultSet.getString("name"));
				pharmacy.setTown(resultSet.getString("town"));
				pharmacy.setStreetName(resultSet.getString("street"));
				pharmacy.setStreetNumber(resultSet.getInt("str_num"));
				pharmacy.setPostalCode(resultSet.getString("postal_code"));
				pharmacy.setPhoneNumber(resultSet.getString("phone"));
				list.add(pharmacy);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public List<PharmacyWithAllDrugsInCity> pharmaciesWithAllDrugsInSameCityFilter(String filter) {
		List<PharmacyWithAllDrugsInCity> list = new ArrayList<PharmacyWithAllDrugsInCity>();

		String query = "SELECT PA.PATIENT_ID, PA.FIRSTNAME, PA.LASTNAME, PH.* "
				+"FROM pharmacies as PH, patients as PA "
				+"WHERE "
				+	"(SELECT D.DRUG_ID "
				+	"FROM drugs as D, sells as S "
				+   "WHERE D.DRUG_ID = S.DRUG_ID AND PH.PHARMACY_ID = S.PHARMACY_ID and PA.TOWN = PH.TOWN "
				+    ") "
				+"in "
				+    "(SELECT D.DRUG_ID "
				+   "FROM prescriptions as PR, drugs as D "
				+    "WHERE PR.PATIENT_ID = PA.PATIENT_ID "
				+    ")";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				PharmacyWithAllDrugsInCity pharmacy = new PharmacyWithAllDrugsInCity();
				pharmacy.setPatientId(resultSet.getInt(1));
				pharmacy.setPatientFirstName(resultSet.getString(2));
				pharmacy.setPatientLastName(resultSet.getString(3));

				pharmacy.setPharmacyId(resultSet.getInt("pharmacy_id"));
				pharmacy.setName(resultSet.getString("name"));
				pharmacy.setTown(resultSet.getString("town"));
				pharmacy.setStreetName(resultSet.getString("street"));
				pharmacy.setStreetNumber(resultSet.getInt("str_num"));
				pharmacy.setPostalCode(resultSet.getString("postal_code"));
				pharmacy.setPhoneNumber(resultSet.getString("phone"));
				if (pharmacy.toString().toLowerCase().contains(filter.toLowerCase()))
					list.add(pharmacy);
			}			
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
