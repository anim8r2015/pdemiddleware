package co.ke.predictiveanalytics.helpers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import co.ke.predictiveanalytics.model.Data;
import co.ke.predictiveanalytics.model.DataModel;

public class OpenShiftDataBaseHelper {

	public OpenShiftDataBaseHelper() {

	}

	String dbURL = DBInfo.getDBURL();
	String user = DBInfo.getUser();
	String password = DBInfo.getPassword();

	public Connection cn() {

		Connection connection = null;

		try {
			Class.forName(DBInfo.getDriver());
		} catch (ClassNotFoundException ce) {
			System.out.println("Error. Driver class not found: " + ce);
		}

		try {
			connection = DriverManager.getConnection(dbURL, user, password);
		} catch (SQLException e) {
			System.out.println("Error. Connection problem: " + e);
		}
		return connection;
	}

	public List<Data> getTableData() {
		List<Data> dataList = new ArrayList<>();
		try {
			PreparedStatement st = null;
			ResultSet rs = null;

			st = cn().prepareStatement("SELECT * FROM pde_data");
			rs = st.executeQuery();

			while (rs.next()) {
				Data data = new Data();
				data.setDataId(rs.getInt("data_id"));
				data.setDataName(rs.getString("data_name"));
				data.setDataDate(rs.getDate("data_date"));
				data.setErrorMessage("");
				dataList.add(data);
			}

		} catch (Exception e) {
			Data data = new Data();
			data.setErrorMessage(e.getMessage());
			dataList.add(data);
		}
		return dataList;
	}

	public List<Data> insertUpdateData(String transaction, int Id, String name, String date) {
		List<Data> processData = new ArrayList<>();
		PreparedStatement st;
		Date date1 = null;

		try {
			date1 = new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(date).getTime());
			if (transaction.equalsIgnoreCase("A")) {
				st = cn().prepareStatement("INSERT INTO pde_data (data_id, data_name, data_date) " + "VALUES(?,?,?)");
				st.setInt(1, Id);
				st.setString(2, name);
				st.setDate(3, date1);
			} else { // edit
				st = cn().prepareStatement(
						"UPDATE pde_data SET data_name = '" + name + "', data_date = " + date + " WHERE data_id = " +
								Id + "");
			}
			st.executeUpdate();
			Data data = new Data();
			data.setDataId(Id);
			data.setErrorMessage("");
			processData.add(data);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			Data data = new Data();
			data.setErrorMessage("An error occured: " + e.getMessage());
			processData.add(data);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Data data = new Data();
			data.setErrorMessage("An error occured: " + e.getMessage());
			processData.add(data);
		}
		return processData;
	}
	
	public List<Data> doDML(String transaction) {
		List<Data> processData = new ArrayList<>();
		PreparedStatement st;

		try {
			st = cn().prepareStatement(transaction);
			Data data = new Data();
			data.setErrorMessage("");
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			Data data = new Data();
			data.setErrorMessage("An error occured: " + e.getMessage());
			processData.add(data);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Data data = new Data();
			data.setErrorMessage("An error occured: " + e.getMessage());
			processData.add(data);
		}
		return processData;
	}
	
	public List<Data> insertUpdateDataObject(DataModel dataModel) {
		List<Data> processData = new ArrayList<>();
		PreparedStatement st;
		Date date1 = null;

		try {
			if(dataModel.getRecbrowsercreated() != null) {
				//date1 = new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(dataModel.get).getTime());
				date1 = new java.sql.Date(Long.valueOf(dataModel.getRecbrowsercreated()));
			}
			
			
				st = cn().prepareStatement("INSERT INTO pde_data (data_id, data_name, data_date) " + "VALUES(?,?,?)");
				//st.setInt(1, Id);
				//st.setString(2, name);
				//st.setDate(3, date1);
			
			st.executeUpdate();
			Data data = new Data();
			data.setResponseId(dataModel.getRecordid());
			data.setSuccess(true);
			data.setErrorMessage("");
			processData.add(data);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			Data data = new Data();
			data.setSuccess(false);
			data.setResponseId(dataModel.getRecordid());
			data.setErrorMessage("An error occured: " + e.getMessage());
			processData.add(data);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Data data = new Data();
			data.setSuccess(false);
			data.setResponseId(dataModel.getRecordid());
			data.setErrorMessage("An error occured: " + e.getMessage());
			processData.add(data);
		}
		return processData;
	}

}