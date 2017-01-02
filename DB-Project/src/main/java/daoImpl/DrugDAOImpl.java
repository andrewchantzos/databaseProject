package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DrugDAO;
import model.Drug;
import util.DBUtil;

public class DrugDAOImpl implements DrugDAO {

	
	
	private Connection conn;

	public DrugDAOImpl() {
		conn = DBUtil.getConnection();
	}
	
	@Override
	public void insert(Drug drug) {
		try {
			String query = "insert into `drugs` (`company_company_id`, `name`, `formula`)  values (?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(query, new String[]{"drug_id"});
			preparedStatement.setInt(1, drug.getPharmaceuticalCompanyId());
			preparedStatement.setString(2, drug.getName());
			preparedStatement.setString(3, drug.getFormula());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void insertWithId(Drug drug) {
		try {
			String query = "insert into `drugs` (`drug_id`, `company_company_id`, `name`, `formula`)  values (?,?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, drug.getDrugId());
			preparedStatement.setInt(2, drug.getPharmaceuticalCompanyId());
			preparedStatement.setString(3, drug.getName());
			preparedStatement.setString(4, drug.getFormula());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update(Drug drug) {
		try {
			String query = "update drugs set company_company_id=?, name=?, formula=? where drug_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, drug.getPharmaceuticalCompanyId());
			preparedStatement.setString(2, drug.getName());
			preparedStatement.setString(3, drug.getFormula());
			preparedStatement.setInt(4, drug.getDrugId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void delete(int drugId) {
		try {
			String query = "delete from drugs where drug_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, drugId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public List<Drug> findAll() {
		List<Drug> drugs = new ArrayList<Drug>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from drugs");
			while(resultSet.next()) {
				Drug drug = new Drug();
				drug.setDrugId(resultSet.getInt("drug_id"));
				drug.setName(resultSet.getString("name"));
				drug.setFormula(resultSet.getString("formula"));
				drug.setPharmaceuticalCompanyId(resultSet.getInt("company_company_id"));
				drugs.add(drug);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return drugs;
	}
	
	@Override
	public List<Drug> findAllFilter(String search) {
		List<Drug> drugs = new ArrayList<Drug>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from drugs");
			while(resultSet.next()) {
				Drug drug = new Drug();
				drug.setDrugId(resultSet.getInt("drug_id"));
				drug.setName(resultSet.getString("name"));
				drug.setFormula(resultSet.getString("formula"));
				drug.setPharmaceuticalCompanyId(resultSet.getInt("company_company_id"));
				if (drug.toString().toLowerCase().contains(search.toLowerCase()))
						drugs.add(drug);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return drugs;
	}

	@Override
	public Drug findById(int drugId) {
		Drug drug = new Drug();
		try {
			String query = "select * from drugs where drug_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				drug.setDrugId(resultSet.getInt("drug_id"));
				drug.setName(resultSet.getString("name"));
				drug.setFormula(resultSet.getString("formula"));
				drug.setPharmaceuticalCompanyId(resultSet.getInt("company_company_id"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return drug;
	}

}
