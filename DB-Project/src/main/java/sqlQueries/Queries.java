package sqlQueries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Doctor;
import model.Patient;
import queryModels.DoctorWithOldPatients;
import queryModels.DrugPrescriptionCount;
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

	public List<Doctor> findDoctorsBySpeciality(String speciality) {
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

	public List<Doctor> findDoctorsBySpecialityFilter(String speciality, String filter) {
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
				+ "FROM ( SELECT  have.patient_id,  have.pharmacy_id "
				+ "FROM ( SELECT s.pharmacy_id, pr.patient_id, COUNT(*) AS ct " + " FROM sells AS s "
				+ " JOIN prescriptions AS pr  USING(drug_id) " + " GROUP BY  s.pharmacy_id, pr.patient_id ) AS have "
				+ "JOIN ( SELECT patient_id, COUNT(*) AS ct " + "  FROM prescriptions "
				+ "   GROUP BY patient_id ) AS need " + " ON need.patient_id = have.patient_id "
				+ " WHERE need.ct = have.ct ) AS x " + " JOIN pharmacies as PH  USING(pharmacy_id) "
				+ " JOIN patients as PA    USING(patient_id) " + "where PA.TOWN = PH.TOWN";
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
				+ "FROM ( SELECT  have.patient_id,  have.pharmacy_id "
				+ "FROM ( SELECT s.pharmacy_id, pr.patient_id, COUNT(*) AS ct " + " FROM sells AS s "
				+ " JOIN prescriptions AS pr  USING(drug_id) " + " GROUP BY  s.pharmacy_id, pr.patient_id ) AS have "
				+ "JOIN ( SELECT patient_id, COUNT(*) AS ct " + "  FROM prescriptions "
				+ "   GROUP BY patient_id ) AS need " + " ON need.patient_id = have.patient_id "
				+ " WHERE need.ct = have.ct ) AS x " + " JOIN pharmacies as PH  USING(pharmacy_id) "
				+ " JOIN patients as PA    USING(patient_id) " + "where PA.TOWN = PH.TOWN";
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

	public List<DoctorWithOldPatients> doctorsWithOldPatients() {
		List<DoctorWithOldPatients> list = new ArrayList<DoctorWithOldPatients>();

		String query = "SELECT D.DOCTOR_ID, D.firstname, D.lastname, D.speciality, avg(age) "
				+ "FROM doctors D, patients P " + "where P.DOCTOR_DOCTOR_ID = D.doctor_id " + "GROUP BY doctor_id "
				+ "HAVING AVG(age)>60";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				DoctorWithOldPatients doctor = new DoctorWithOldPatients();
				doctor.setDoctorId(resultSet.getInt("doctor_id"));
				doctor.setFirstName(resultSet.getString("firstname"));
				doctor.setLastName(resultSet.getString("lastname"));
				doctor.setSpeciality(resultSet.getString("speciality"));
				doctor.setAverageAge(resultSet.getDouble(5));
				list.add(doctor);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<DoctorWithOldPatients> doctorsWithOldPatientsFilter(String filter) {
		List<DoctorWithOldPatients> list = new ArrayList<DoctorWithOldPatients>();

		String query = "SELECT D.DOCTOR_ID, D.firstname, D.lastname, D.speciality, avg(age) "
				+ "FROM doctors D, patients P " + "where P.DOCTOR_DOCTOR_ID = D.doctor_id " + "GROUP BY doctor_id "
				+ "HAVING AVG(age)>60";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				DoctorWithOldPatients doctor = new DoctorWithOldPatients();
				doctor.setDoctorId(resultSet.getInt("doctor_id"));
				doctor.setFirstName(resultSet.getString("firstname"));
				doctor.setLastName(resultSet.getString("lastname"));
				doctor.setSpeciality(resultSet.getString("speciality"));
				doctor.setAverageAge(resultSet.getDouble(5));
				if (doctor.toString().toLowerCase().contains(filter.toLowerCase()))
					list.add(doctor);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<DrugPrescriptionCount> drugPrescriptionCount() {
		List<DrugPrescriptionCount> list = new ArrayList<DrugPrescriptionCount>();

		String query = "SELECT D.DRUG_ID, D.NAME, COUNT(*) " + "FROM DRUGS AS D, prescriptions AS PR "
				+ "WHERE PR.DRUG_ID = D.DRUG_ID " + "GROUP BY D.DRUG_ID, D.NAME " + "ORDER BY COUNT(*) DESC";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				DrugPrescriptionCount drug = new DrugPrescriptionCount();
				drug.setDrugId(resultSet.getInt("DRUG_ID"));
				drug.setDrugName(resultSet.getString("NAME"));
				drug.setCount(resultSet.getInt(3));
				list.add(drug);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<DrugPrescriptionCount> drugPrescriptionCountFilter(String filter) {
		List<DrugPrescriptionCount> list = new ArrayList<DrugPrescriptionCount>();

		String query = "SELECT D.DRUG_ID, D.NAME, COUNT(*) " + "FROM DRUGS AS D, prescriptions AS PR "
				+ "WHERE PR.DRUG_ID = D.DRUG_ID " + "GROUP BY D.DRUG_ID, D.NAME " + "ORDER BY COUNT(*) DESC";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				DrugPrescriptionCount drug = new DrugPrescriptionCount();
				drug.setDrugId(resultSet.getInt("DRUG_ID"));
				drug.setDrugName(resultSet.getString("NAME"));
				drug.setCount(resultSet.getInt(3));
				if (drug.toString().toLowerCase().contains(filter.toLowerCase()))
					list.add(drug);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Patient> doctorPatients(int doctorId) {
		List<Patient> patients = new ArrayList<Patient>();

		String query = "SELECT pa.* " + "FROM PATIENTS AS pa, DOCTORS AS d "
				+ "WHERE pa.DOCTOR_DOCTOR_ID = d.DOCTOR_ID and d.DOCTOR_ID =  " + doctorId + " "
				+ "ORDER BY pa.FIRSTNAME";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Patient patient = new Patient();
				patient.setDoctorId(resultSet.getInt("doctor_doctor_id"));
				patient.setFirstName(resultSet.getString("firstname"));
				patient.setLastName(resultSet.getString("lastname"));
				patient.setAge(resultSet.getInt("age"));
				patient.setPhone(resultSet.getString("phone"));
				patient.setPatientId(resultSet.getInt("patient_id"));
				patient.setTown(resultSet.getString("town"));
				patient.setStreetName(resultSet.getString("street"));
				patient.setStreetNumber(resultSet.getInt("str_num"));
				patient.setPostalCode(resultSet.getString("postal_code"));
				patients.add(patient);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patients;
	}

	public List<Patient> doctorPatientsFilter(int doctorId, String filter) {
		List<Patient> patients = new ArrayList<Patient>();

		String query = "SELECT pa.* " + "FROM PATIENTS AS pa, DOCTORS AS d "
				+ "WHERE pa.DOCTOR_DOCTOR_ID = d.DOCTOR_ID and d.DOCTOR_ID =  " + doctorId + " "
				+ "ORDER BY pa.FIRSTNAME";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Patient patient = new Patient();
				patient.setDoctorId(resultSet.getInt("doctor_doctor_id"));
				patient.setFirstName(resultSet.getString("firstname"));
				patient.setLastName(resultSet.getString("lastname"));
				patient.setAge(resultSet.getInt("age"));
				patient.setPhone(resultSet.getString("phone"));
				patient.setPatientId(resultSet.getInt("patient_id"));
				patient.setTown(resultSet.getString("town"));
				patient.setStreetName(resultSet.getString("street"));
				patient.setStreetNumber(resultSet.getInt("str_num"));
				patient.setPostalCode(resultSet.getString("postal_code"));
				if (patient.toString().toLowerCase().contains(filter.toLowerCase()))
					patients.add(patient);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patients;
	}

}