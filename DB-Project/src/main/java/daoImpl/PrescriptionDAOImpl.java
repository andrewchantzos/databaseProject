package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.PrescriptionDAO;
import model.Prescription;
import util.DBUtil;

public class PrescriptionDAOImpl implements PrescriptionDAO {

	private Connection conn;

	public PrescriptionDAOImpl() {
		conn = DBUtil.getConnection();
	}

	@Override
	public void insert(Prescription prescription) throws SQLIntegrityConstraintViolationException{
		try {
			String query = "insert into `prescriptions` (`drug_id`,  `patient_id`, `doctor_id`, `date`, `quantity`) values (?,?,?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, prescription.getDrugId());
			preparedStatement.setInt(2, prescription.getPatientId());
			preparedStatement.setInt(3, prescription.getDoctorId());
			preparedStatement.setDate(4, prescription.getDate());
			preparedStatement.setInt(5, prescription.getQuantity());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Prescription prescription) throws SQLIntegrityConstraintViolationException {
		try {
			String query = "update prescriptions set date=?, quantity=? where doctor_id=? and patient_id=? and drug_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, prescription.getDate());
			preparedStatement.setInt(2, prescription.getQuantity());
			preparedStatement.setInt(3, prescription.getDoctorId());
			preparedStatement.setInt(4, prescription.getPatientId());
			preparedStatement.setInt(5, prescription.getDrugId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int patientId, int doctorId, int drugId) {
		try {
			String query = "delete from prescriptions where doctor_id=? and patient_id=? and drug_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setInt(2, patientId);
			preparedStatement.setInt(3, drugId);

			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Prescription> findAll() {
		List<Prescription> prescriptions = new ArrayList<Prescription>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from prescriptions");
			while (resultSet.next()) {
				Prescription prescription = new Prescription();
				prescription.setDoctorId(resultSet.getInt("doctor_id"));
				prescription.setDrugId(resultSet.getInt("drug_id"));
				prescription.setPatientId(resultSet.getInt("patient_id"));
				prescription.setDate(resultSet.getDate("date"));
				prescription.setQuantity(resultSet.getInt("quantity"));
				prescriptions.add(prescription);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prescriptions;
	}
	
	@Override
	public List<Prescription> findAllFilter(String search) {
		List<Prescription> prescriptions = new ArrayList<Prescription>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from prescriptions");
			while (resultSet.next()) {
				Prescription prescription = new Prescription();
				prescription.setDoctorId(resultSet.getInt("doctor_id"));
				prescription.setDrugId(resultSet.getInt("drug_id"));
				prescription.setPatientId(resultSet.getInt("patient_id"));
				prescription.setDate(resultSet.getDate("date"));
				prescription.setQuantity(resultSet.getInt("quantity"));
				if (prescription.toString().toLowerCase().contains(search.toLowerCase()))
					prescriptions.add(prescription);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prescriptions;
	}
	@Override
	public Prescription findById(int patientId, int doctorId, int drugId) {
		Prescription prescription = new Prescription();
		try {
			String query = "select * from prescriptions where doctor_id=? and patient_id=? and drug_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				prescription.setDoctorId(resultSet.getInt("doctor_id"));
				prescription.setDrugId(resultSet.getInt("drug_id"));
				prescription.setPatientId(resultSet.getInt("patient_id"));
				prescription.setDate(resultSet.getDate("date"));
				prescription.setQuantity(resultSet.getInt("quantity"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prescription;
	}

}
