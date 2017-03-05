package sqlQueries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Patient;
import util.DBUtil;

public class ViewQueries {
	
	private Connection conn;

	public ViewQueries() {
		conn = DBUtil.getConnection();
	}
	
	public List<Patient> findElderPatients() {
		
		List<Patient> patients = new ArrayList<Patient>();

		String query = "SELECT * FROM elderPatients";
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
	
	public List<Patient> findElderPatients(String filter) {
		
		List<Patient> patients = new ArrayList<Patient>();

		String query = "SELECT * FROM elderPatients";
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
