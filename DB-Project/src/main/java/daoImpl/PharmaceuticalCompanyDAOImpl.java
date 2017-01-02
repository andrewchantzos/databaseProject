package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.PharmaceuticalCompanyDAO;
import model.PharmaceuticalCompany;
import util.DBUtil;

public class PharmaceuticalCompanyDAOImpl implements PharmaceuticalCompanyDAO {

	
	private Connection conn;

	public PharmaceuticalCompanyDAOImpl() {
		conn = DBUtil.getConnection();
	}
	
	@Override
	public void insert(PharmaceuticalCompany pharmaceuticalCompany) {
		try {
			String query = "insert into `companies` (`name`, `phone_number`) VALUES (?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(query, new String[]{"company_id"});
			preparedStatement.setString(1, pharmaceuticalCompany.getName());
			preparedStatement.setString(2, pharmaceuticalCompany.getPhoneNumber());

			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertWithId(PharmaceuticalCompany pharmaceuticalCompany) {
		try {
			String query = "insert into `companies` (`company_id`, `name`, `phone_number`) VALUES (?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, pharmaceuticalCompany.getPharmaceuticalCompanyId());
			preparedStatement.setString(2, pharmaceuticalCompany.getName());
			preparedStatement.setString(3, pharmaceuticalCompany.getPhoneNumber());

			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(PharmaceuticalCompany pharmaceuticalCompany) {
		try {
			String query = "update companies set name=?, phone_number=? where company_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, pharmaceuticalCompany.getName());
			preparedStatement.setString(2, pharmaceuticalCompany.getPhoneNumber());
			preparedStatement.setInt(3, pharmaceuticalCompany.getPharmaceuticalCompanyId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int pharmaceuticalCompanyId) {
		try {
			String query = "delete from companies where company_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, pharmaceuticalCompanyId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PharmaceuticalCompany> findAll() {
		List<PharmaceuticalCompany> pharmaceuticalCompanies = new ArrayList<PharmaceuticalCompany>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from companies");
			while(resultSet.next()) {
				PharmaceuticalCompany pharmaceuticalCompany = new PharmaceuticalCompany();
				pharmaceuticalCompany.setPharmaceuticalCompanyId(resultSet.getInt("company_id"));
				pharmaceuticalCompany.setName(resultSet.getString("name"));
				pharmaceuticalCompany.setPhoneNumber(resultSet.getString("phone_number"));
				pharmaceuticalCompanies.add(pharmaceuticalCompany);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pharmaceuticalCompanies;
	}
	
	@Override
	public List<PharmaceuticalCompany> findAllFilter(String search) {
		List<PharmaceuticalCompany> pharmaceuticalCompanies = new ArrayList<PharmaceuticalCompany>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from companies");
			while(resultSet.next()) {
				PharmaceuticalCompany pharmaceuticalCompany = new PharmaceuticalCompany();
				pharmaceuticalCompany.setPharmaceuticalCompanyId(resultSet.getInt("company_id"));
				pharmaceuticalCompany.setName(resultSet.getString("name"));
				pharmaceuticalCompany.setPhoneNumber(resultSet.getString("phone_number"));
				if (pharmaceuticalCompany.toString().toLowerCase().contains(search.toLowerCase()))
					pharmaceuticalCompanies.add(pharmaceuticalCompany);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pharmaceuticalCompanies;
	}

	@Override
	public PharmaceuticalCompany findById(int pharmaceuticalCompanyId) {
		PharmaceuticalCompany pharmaceuticalCompany = new PharmaceuticalCompany();
		try {
			String query = "select * from companies where company_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				pharmaceuticalCompany.setPharmaceuticalCompanyId(resultSet.getInt("company_id"));
				pharmaceuticalCompany.setName(resultSet.getString("name"));
				pharmaceuticalCompany.setPhoneNumber(resultSet.getString("phone_number"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pharmaceuticalCompany;
	}

}
