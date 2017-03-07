package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.ContractDAO;
import model.Contract;
import util.DBUtil;

public class ContractDAOImpl implements ContractDAO {

	private Connection conn;

	public ContractDAOImpl() {
		conn = DBUtil.getConnection();
	}
	
	
	@Override
	public void insert(Contract contract) throws SQLIntegrityConstraintViolationException {
		try {
			String query = "insert into `contracts` (`pharmacy_id`, `company_id`, `start_date`, `end_date`, `supervisor`, `text`) values (?,?,?,?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, contract.getPharmacyId());
			preparedStatement.setInt(2, contract.getPharmaceuticalCopmanyId());
			preparedStatement.setDate(3, contract.getStartDate());
			preparedStatement.setDate(4, contract.getEndDate());
			preparedStatement.setString(5, contract.getSupervisor());
			preparedStatement.setString(6, contract.getText());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();

			throw new SQLIntegrityConstraintViolationException();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Contract contract) throws SQLIntegrityConstraintViolationException {
		try {
			String query = "update contracts set start_date=?, end_date=?, supervisor=?, text=? where pharmacy_id=? and company_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);

			preparedStatement.setDate(1, contract.getStartDate());
			preparedStatement.setDate(2, contract.getEndDate());
			preparedStatement.setString(3, contract.getSupervisor());
			preparedStatement.setString(4, contract.getText());
			preparedStatement.setInt(5, contract.getPharmacyId());
			preparedStatement.setInt(6, contract.getPharmaceuticalCopmanyId());

			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void delete(int pharmacyId, int pharmaceuticalCompanyId) {
		try {
			String query = "delete from contracts where pharmacy_id=? and company_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, pharmacyId);
			preparedStatement.setInt(2, pharmaceuticalCompanyId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public List<Contract> findAll() {
		List<Contract> contracts = new ArrayList<Contract>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from contracts");
			while(resultSet.next()) {
				Contract contract = new Contract();
				contract.setStartDate(resultSet.getDate("start_date"));
				contract.setEndDate(resultSet.getDate("end_date"));
				contract.setSupervisor(resultSet.getString("supervisor"));
				contract.setText(resultSet.getString("text"));
				contract.setPharmacyId(resultSet.getInt("pharmacy_id"));
				contract.setPharmaceuticalCopmanyId(resultSet.getInt("company_id"));
				contracts.add(contract);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contracts;
	}

	
	@Override
	public List<Contract> findAllFilter(String search) {
		List<Contract> contracts = new ArrayList<Contract>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from contracts");
			while(resultSet.next()) {
				Contract contract = new Contract();
				contract.setStartDate(resultSet.getDate("start_date"));
				contract.setEndDate(resultSet.getDate("end_date"));
				contract.setSupervisor(resultSet.getString("supervisor"));
				contract.setText(resultSet.getString("text"));
				contract.setPharmacyId(resultSet.getInt("pharmacy_id"));
				contract.setPharmaceuticalCopmanyId(resultSet.getInt("company_id"));
				if (contract.toString().toLowerCase().contains(search.toLowerCase()))
						contracts.add(contract);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contracts;
	}
	
	@Override
	public Contract findById(int pharmacyId, int pharmaceuticalCompanyId) {
		Contract contract = new Contract();
		try {
			String query = "select * from contracts  where pharmacy_id=? and company_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				contract.setStartDate(resultSet.getDate("start_date"));
				contract.setEndDate(resultSet.getDate("end_date"));
				contract.setSupervisor(resultSet.getString("supervisor"));
				contract.setText(resultSet.getString("text"));
				contract.setPharmacyId(resultSet.getInt("pharmacy_id"));
				contract.setPharmaceuticalCopmanyId(resultSet.getInt("company_id"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contract;
	}

}
