package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.SellDAO;
import model.Sell;
import util.DBUtil;

public class SellDAOImpl implements SellDAO {

	
	
	private Connection conn;

	public SellDAOImpl() {
		conn = DBUtil.getConnection();
	}
	
	
	@Override
	public void insert(Sell sell) throws SQLIntegrityConstraintViolationException
	{
		try {
			String query = "insert into `sells` (`pharmacy_id`, `drug_id`, `drug_company_id`, `price`) values (?,?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, sell.getPharmacyId());
			preparedStatement.setInt(2, sell.getDrugId());
			preparedStatement.setInt(3, sell.getCompanyId());
			preparedStatement.setInt(4, sell.getPrice());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	@Override
	public void update(Sell sell) throws SQLIntegrityConstraintViolationException
	{
		try {
			String query = "update sells set price=? where pharmacy_id=? and drug_id=? and drug_company_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, sell.getPrice());
			preparedStatement.setInt(2, sell.getPharmacyId());
			preparedStatement.setInt(3, sell.getDrugId());
			preparedStatement.setInt(4, sell.getCompanyId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int pharmacyId, int drugId, int companyId) {
		try {
			String query = "delete from sells where pharmacy_id=? and drug_id=? and drug_company_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, pharmacyId);
			preparedStatement.setInt(2, drugId);
			preparedStatement.setInt(3, companyId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Sell> findAll() {
		List<Sell> sells = new ArrayList<Sell>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from sells");
			while(resultSet.next()) {
				Sell sell = new Sell();
				sell.setPrice(resultSet.getInt("price"));
				sell.setCompanyId(resultSet.getInt("drug_company_id"));
				sell.setDrugId(resultSet.getInt("drug_id"));
				sell.setPharmacyId(resultSet.getInt("pharmacy_id"));
				sells.add(sell);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sells;
	}

	@Override
	public List<Sell> findAllFilter(String search) {
		List<Sell> sells = new ArrayList<Sell>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from sells");
			while(resultSet.next()) {
				Sell sell = new Sell();
				sell.setPrice(resultSet.getInt("price"));
				sell.setCompanyId(resultSet.getInt("drug_company_id"));
				sell.setDrugId(resultSet.getInt("drug_id"));
				sell.setPharmacyId(resultSet.getInt("pharmacy_id"));
				if (sell.toString().toLowerCase().contains(search.toLowerCase()))
					sells.add(sell);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sells;
	}
	
	@Override
	public Sell findById(int pharmacyId, int drugId, int companyId) {
		Sell sell = new Sell();
		try {
			String query = "select * from sells where pharmacy_id=? and drug_id=? and drug_company_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				sell.setPrice(resultSet.getInt("price"));
				sell.setCompanyId(resultSet.getInt("drug_company_id"));
				sell.setDrugId(resultSet.getInt("drug_id"));
				sell.setPharmacyId(resultSet.getInt("pharmacy_id"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sell;
	}

}
