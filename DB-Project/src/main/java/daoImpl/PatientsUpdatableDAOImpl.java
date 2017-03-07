package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.PatientUpdatableDAO;
import modelQueries.PatientsUpdatable;
import util.DBUtil;


public class PatientsUpdatableDAOImpl implements PatientUpdatableDAO {

	private Connection conn;

	public PatientsUpdatableDAOImpl() {
		conn = DBUtil.getConnection();
	}

	@Override
	public void insert(PatientsUpdatable patient)  {
		try {
			String query = "insert into `noagepatients` (`town`, `firstname`, `lastname`) values (?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(query, new String[] { "patient_id"});
			preparedStatement.setString(1, patient.getTown());
			
			preparedStatement.setString(2, patient.getFirstName());
			preparedStatement.setString(3, patient.getLastName());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void update(PatientsUpdatable patient) {
		try {
			String query = "update NoAgePatients set firstname=?, lastname=?, town=? where patient_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, patient.getFirstName());
			preparedStatement.setString(2, patient.getLastName());
			preparedStatement.setString(3, patient.getTown());
		
			preparedStatement.setInt(4, patient.getPatientId());

			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int patientId) {
		try {
			String query = "delete from NoAgePatients where patient_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, patientId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PatientsUpdatable> findAll() {
		List<PatientsUpdatable> patients = new ArrayList<PatientsUpdatable>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from noagepatients");
			while (resultSet.next()) {
				PatientsUpdatable patient = new PatientsUpdatable();
				patient.setFirstName(resultSet.getString("firstname"));
				patient.setLastName(resultSet.getString("lastname"));
				patient.setPatientId(resultSet.getInt("patient_id"));

				patient.setTown(resultSet.getString("town"));


				patients.add(patient);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patients;
	}
	
	@Override
	public List<PatientsUpdatable> findAllFilter(String search) {
		List<PatientsUpdatable> patients = new ArrayList<PatientsUpdatable>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from NoAgePatients");
			while (resultSet.next()) {
				PatientsUpdatable patient = new PatientsUpdatable();
				patient.setFirstName(resultSet.getString("firstname"));
				patient.setLastName(resultSet.getString("lastname"));
				patient.setPatientId(resultSet.getInt("patient_id"));

				patient.setTown(resultSet.getString("town"));

				if (patient.toString().toLowerCase().contains(search.toLowerCase()))
					patients.add(patient);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patients;
	}

	@Override
	public PatientsUpdatable findById(int patientId) {
		PatientsUpdatable patient = new PatientsUpdatable();
		try {
			String query = "select * from NoAgePatients where patient_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				patient.setFirstName(resultSet.getString("firstname"));
				patient.setLastName(resultSet.getString("lastname"));

				patient.setTown(resultSet.getString("town"));

			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patient;
	}


}
