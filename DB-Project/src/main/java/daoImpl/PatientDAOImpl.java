package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.PatientDAO;
import model.Patient;
import util.DBUtil;

public class PatientDAOImpl implements PatientDAO {

	private Connection conn;

	public PatientDAOImpl() {
		conn = DBUtil.getConnection();
	}

	@Override
	public void insert(Patient patient)  {
		try {
			String query = "insert into `patients` (`doctor_doctor_id`, `town`, `street`, `str_num`, `postal_code`, `phone`, `firstname`, `lastname`, `age`) values (?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(query, new String[] { "patient_id"});
			preparedStatement.setInt(1, patient.getDoctorId());
			preparedStatement.setString(2, patient.getTown());
			preparedStatement.setString(3, patient.getStreetName());
			preparedStatement.setInt(4, patient.getStreetNumber());
			preparedStatement.setString(5, patient.getPostalCode());
			preparedStatement.setString(6, patient.getPhone());
			preparedStatement.setString(7, patient.getFirstName());
			preparedStatement.setString(8, patient.getLastName());
			preparedStatement.setInt(9, patient.getAge());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertWithId(Patient patient) {
		try {
			String query = "insert into `patients` (`patient_id`, `doctor_doctor_id`, `town`, `street`, `str_num`, `postal_code`, `phone`, `firstname`, `lastname`, `age`) values (?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, patient.getPatientId());
			preparedStatement.setInt(2, patient.getDoctorId());
			preparedStatement.setString(3, patient.getTown());
			preparedStatement.setString(4, patient.getStreetName());
			preparedStatement.setInt(5, patient.getStreetNumber());
			preparedStatement.setString(6, patient.getPostalCode());
			preparedStatement.setString(7, patient.getPhone());
			preparedStatement.setString(8, patient.getFirstName());
			preparedStatement.setString(9, patient.getLastName());
			preparedStatement.setInt(10, patient.getAge());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Patient patient) {
		try {
			String query = "update patients set firstname=?, lastname=?, age=?, doctor_doctor_id=?, town=?, street=?, str_num=?, postal_code=?, phone=? where patient_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, patient.getFirstName());
			preparedStatement.setString(2, patient.getLastName());
			preparedStatement.setInt(3, patient.getAge());
			preparedStatement.setInt(4, patient.getDoctorId());
			preparedStatement.setString(5, patient.getTown());
			preparedStatement.setString(6, patient.getStreetName());
			preparedStatement.setInt(7, patient.getStreetNumber());
			preparedStatement.setString(8, patient.getPostalCode());
			preparedStatement.setString(9, patient.getPhone());
			preparedStatement.setInt(10, patient.getPatientId());

			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int patientId) {
		try {
			String query = "delete from patients where patient_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, patientId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Patient> findAll() {
		List<Patient> patients = new ArrayList<Patient>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from patients");
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
	
	@Override
	public List<Patient> findAllFilter(String search) {
		List<Patient> patients = new ArrayList<Patient>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from patients");
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
	public Patient findById(int patientId) {
		Patient patient = new Patient();
		try {
			String query = "select * from patients where patient_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				patient.setDoctorId(resultSet.getInt("doctor_id"));
				patient.setFirstName(resultSet.getString("firstname"));
				patient.setLastName(resultSet.getString("lastname"));
				patient.setAge(resultSet.getInt("age"));
				patient.setPhone(resultSet.getString("phone"));
				patient.setPatientId(resultSet.getInt("patient_id"));
				patient.setTown(resultSet.getString("town"));
				patient.setStreetName(resultSet.getString("street"));
				patient.setStreetNumber(resultSet.getInt("str_num"));
				patient.setPostalCode(resultSet.getString("postal_code"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patient;
	}

}
