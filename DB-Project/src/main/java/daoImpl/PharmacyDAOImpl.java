package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.PharmacyDAO;
import model.Pharmacy;
import util.DBUtil;

public class PharmacyDAOImpl implements PharmacyDAO {

	private Connection conn;

	public PharmacyDAOImpl() {
		conn = DBUtil.getConnection();
	}
	
	@Override
	public void insert(Pharmacy pharmacy) throws SQLIntegrityConstraintViolationException {
		try {
			String query = "insert into `pharmacies` (`name`, `town`, `street`, `str_num`, `postal_code`, `phone`) values (?,?,?,?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(query, new String[]{"pharmacy_id"});
			preparedStatement.setString(1, pharmacy.getName());
			preparedStatement.setString(2, pharmacy.getTown());
			preparedStatement.setString(3, pharmacy.getStreetName());
			preparedStatement.setInt(4, pharmacy.getStreetNumber());
			preparedStatement.setString(5, pharmacy.getPostalCode());
			preparedStatement.setString(6, pharmacy.getPhoneNumber());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertWithId(Pharmacy pharmacy) {
		try {
			String query = "insert into `pharmacies` (`pharmacy_id`, `name`, `town`, `street`, `str_num`, `postal_code`, `phone`) values (?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, pharmacy.getPharmacyId());
			preparedStatement.setString(2, pharmacy.getName());
			preparedStatement.setString(3, pharmacy.getTown());
			preparedStatement.setString(4, pharmacy.getStreetName());
			preparedStatement.setInt(5, pharmacy.getStreetNumber());
			preparedStatement.setString(6, pharmacy.getPostalCode());
			preparedStatement.setString(7, pharmacy.getPhoneNumber());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Pharmacy pharmacy) throws SQLIntegrityConstraintViolationException {
		try {
			String query = "update pharmacies set name=?, town=?, street=?, str_num=?, postal_code=?, phone=? where pharmacy_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, pharmacy.getName());
			preparedStatement.setString(2, pharmacy.getTown());
			preparedStatement.setString(3, pharmacy.getStreetName());
			preparedStatement.setInt(4, pharmacy.getStreetNumber());
			preparedStatement.setString(5, pharmacy.getPostalCode());
			preparedStatement.setString(6, pharmacy.getPhoneNumber());
			preparedStatement.setInt(7, pharmacy.getPharmacyId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int pharmacyId) {
		try {
			String query = "delete from pharmacies where pharmacy_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, pharmacyId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Pharmacy> findAll() {
		List<Pharmacy> pharmacies = new ArrayList<Pharmacy>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from pharmacies");
			while(resultSet.next()) {
				Pharmacy pharmacy = new Pharmacy();
				pharmacy.setPharmacyId(resultSet.getInt("pharmacy_id"));
				pharmacy.setName(resultSet.getString("name"));
				pharmacy.setTown(resultSet.getString("town"));
				pharmacy.setStreetName(resultSet.getString("street"));
				pharmacy.setStreetNumber(resultSet.getInt("str_num"));
				pharmacy.setPostalCode(resultSet.getString("postal_code"));
				pharmacy.setPhoneNumber(resultSet.getString("phone"));
				pharmacies.add(pharmacy);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pharmacies;
	}

	
	@Override
	public List<Pharmacy> findAllFilter(String search) {
		List<Pharmacy> pharmacies = new ArrayList<Pharmacy>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from pharmacies");
			while(resultSet.next()) {
				Pharmacy pharmacy = new Pharmacy();
				pharmacy.setPharmacyId(resultSet.getInt("pharmacy_id"));
				pharmacy.setName(resultSet.getString("name"));
				pharmacy.setTown(resultSet.getString("town"));
				pharmacy.setStreetName(resultSet.getString("street"));
				pharmacy.setStreetNumber(resultSet.getInt("str_num"));
				pharmacy.setPostalCode(resultSet.getString("postal_code"));
				pharmacy.setPhoneNumber(resultSet.getString("phone"));
				if (pharmacy.toString().toLowerCase().contains(search.toLowerCase()))
					pharmacies.add(pharmacy);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pharmacies;
	}
	
	@Override
	public Pharmacy findById(int pharmacyId) {
		Pharmacy pharmacy = new Pharmacy();
		try {
			String query = "select * from pharmacies where pharmacy_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				pharmacy.setPharmacyId(resultSet.getInt("pharmacy_id"));
				pharmacy.setName(resultSet.getString("name"));
				pharmacy.setTown(resultSet.getString("town"));
				pharmacy.setStreetName(resultSet.getString("street"));
				pharmacy.setStreetNumber(resultSet.getInt("str_num"));
				pharmacy.setPostalCode(resultSet.getString("postal_code"));
				pharmacy.setPhoneNumber(resultSet.getString("phone"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pharmacy;
	}

}
