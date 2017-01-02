package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DoctorDAO;
import model.Doctor;
import util.DBUtil;

public class DoctorDAOImpl implements DoctorDAO {

	private Connection conn;

	public DoctorDAOImpl() {
		conn = DBUtil.getConnection();
	}

	@Override
	public void insert(Doctor doctor) {
		try {
			String query = "insert into `doctors` (`firstname`, `lastname`, `speciality`, `experience_years`) values (?,?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(query, new String[] { "doctor_id" });
			preparedStatement.setString(1, doctor.getFirstName());
			preparedStatement.setString(2, doctor.getLastName());
			preparedStatement.setString(3, doctor.getSpeciality());
			preparedStatement.setInt(4, doctor.getExperience());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertWithId(Doctor doctor) {
		try {
			String query = "insert into `doctors` (`doctor_id`, `firstname`, `lastname`, `speciality`, `experience_years`) values (?,?,?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, doctor.getDoctorId());
			preparedStatement.setString(2, doctor.getFirstName());
			preparedStatement.setString(3, doctor.getLastName());
			preparedStatement.setString(4, doctor.getSpeciality());
			preparedStatement.setInt(5, doctor.getExperience());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Doctor doctor) {
		try {
			String query = "update doctors set firstname=?, lastname=?, speciality=?, experience_years=? where doctor_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, doctor.getFirstName());
			preparedStatement.setString(2, doctor.getLastName());
			preparedStatement.setString(3, doctor.getSpeciality());
			preparedStatement.setInt(4, doctor.getExperience());
			preparedStatement.setInt(5, doctor.getDoctorId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int doctorId) {
		try {
			String query = "delete from doctors where doctor_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Doctor> findAll() {
		List<Doctor> doctors = new ArrayList<Doctor>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from doctors");
			while (resultSet.next()) {
				Doctor doctor = new Doctor();
				doctor.setDoctorId(resultSet.getInt("doctor_id"));
				doctor.setFirstName(resultSet.getString("firstname"));
				doctor.setLastName(resultSet.getString("lastname"));
				doctor.setSpeciality(resultSet.getString("speciality"));
				doctor.setExperience(resultSet.getInt("experience_years"));
				doctors.add(doctor);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return doctors;
	}

	@Override
	public List<Doctor> findAllFilter(String filter) {
		List<Doctor> doctors = new ArrayList<Doctor>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from doctors");
			while (resultSet.next()) {
					Doctor doctor = new Doctor();
					doctor.setDoctorId(resultSet.getInt("doctor_id"));
					doctor.setFirstName(resultSet.getString("firstname"));
					doctor.setLastName(resultSet.getString("lastname"));
					doctor.setSpeciality(resultSet.getString("speciality"));
					doctor.setExperience(resultSet.getInt("experience_years"));
					if (doctor.toString().toLowerCase().contains(filter.toLowerCase()))
						doctors.add(doctor);
			}
			resultSet.close();
			statement.close();
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return doctors;
	}

	@Override
	public Doctor findById(int doctorId) {
		Doctor doctor = new Doctor();
		try {
			String query = "select * from doctors where doctor_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				doctor.setDoctorId(resultSet.getInt("doctor_id"));
				doctor.setFirstName(resultSet.getString("firstname"));
				doctor.setLastName(resultSet.getString("lastname"));
				doctor.setSpeciality(resultSet.getString("speciality"));
				doctor.setExperience(resultSet.getInt("experience_years"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return doctor;
	}

}
