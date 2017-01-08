package sqlQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Doctor;
import queryModels.ValidContract;
import util.DBUtil;

public class Queries {
	
	private Connection conn;

	public Queries() {
		conn = DBUtil.getConnection();
	}
	
	public List<ValidContract> findValidContracts() {
		List<ValidContract> list = new ArrayList<ValidContract>();
		
		String query = "SELECT C.name, P.name, Con.Text, Con.supervisor, Con.END_DATE FROM pharmacies P " +
		"JOIN contracts Con ON P.pharmacy_id = Con.pharmacy_id " +
		"JOIN  companies C ON C.company_id = Con.company_id " +
		"WHERE Con.start_date < now() and Con.end_date > now()";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
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
		
		String query = "SELECT C.name, P.name, Con.Text, Con.supervisor, Con.END_DATE FROM pharmacies P " +
		"JOIN contracts Con ON P.pharmacy_id = Con.pharmacy_id " +
		"JOIN  companies C ON C.company_id = Con.company_id " +
		"WHERE Con.start_date < now() and Con.end_date > now()";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
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
		
		String query = "SELECT firstname, lastname, experience_years " +
				"FROM doctors " +
				"where speciality = '" + speciality +"' " +
				"ORDER BY experience_years DESC";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while(resultSet.next()) {
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
		
		String query = "SELECT firstname, lastname, experience_years " +
				"FROM doctors " +
				"where speciality = '" + speciality +"' " +
				"ORDER BY experience_years DESC";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while(resultSet.next()) {
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


}
