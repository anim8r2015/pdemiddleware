package co.ke.predictiveanalytics.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import co.ke.predictiveanalytics.model.Data;
import co.ke.predictiveanalytics.model.DataModel;

public class DataBaseHelper {

	public DataBaseHelper() {

	}

	public Connection cn() {

		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pde_db", "root", "");

		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;
	}

	public List<Data> getTableData() {
		List<Data> dataList = new ArrayList<>();
		List<DataModel> dataModelList = new ArrayList<>();
		String stmt = "SELECT recordid, recdatetime, reccategory, recdirection," +
				"reccontactname, reccontactnumber, recsyncdatetime, reclatitude," +
				"reclongitude, recsyncedbool, recduration, recurl, recvisits," +
				"recvisitdate, recbookmark, rectitle, recmessage, recbrowsercreated," +
				"recphoneid from pde_phone_data";
		try {
			PreparedStatement st = null;
			ResultSet rs = null;

			st = cn().prepareStatement(stmt);
			rs = st.executeQuery();
			
			
			while (rs.next()) {
				DataModel dm = new DataModel();	
				dm.setRecordid(rs.getString("recordid"));
				dm.setRecdatetime(rs.getString("recdatetime"));
				dm.setReccategory(rs.getString("reccategory"));
				dm.setRecdirection(rs.getString("recdirection"));
				dm.setReccontactname(rs.getString("reccontactname"));
				dm.setReccontactnumber(rs.getString("reccontactnumber"));
				dm.setRecsyncdatetime(rs.getString("recsyncdatetime"));
				dm.setReclatitude(rs.getString("reclatitude"));
				dm.setReclongitude(rs.getString("reclongitude"));
				dm.setRecsyncedbool(rs.getString("recsyncedbool"));
				dm.setRecduration(rs.getString("recduration"));
				dm.setRecurl(rs.getString("recurl"));
				dm.setRecvisits(rs.getString("recvisits"));
				dm.setRecvisitdate(rs.getString("recvisitdate"));
				dm.setRecbookmark(rs.getString("recbookmark"));
				dm.setRectitle(rs.getString("rectitle"));
				dm.setRecmessage(rs.getString("recmessage"));
				dm.setRecbrowsercreated(rs.getString("recbrowsercreated"));
				dm.setRecphoneid(rs.getString("recphoneid"));
				dataModelList.add(dm);
				
			}
			Data data = new Data();
			data.setAllData(dataModelList);
			
			data.setErrorMessage("");
			dataList.add(data);
			rs.close();

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
			st.close();
			Data data = new Data();
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
	
	public void doDML(String transaction) {
		PreparedStatement st;

		try {
			st = cn().prepareStatement(transaction);
			st.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public List<Data> insertUpdateDataObject(DataModel dataModel) {
		List<Data> processData = new ArrayList<>();
		PreparedStatement st;
		Date dateBrowserCreated = null, dateRecDateTime = null, dateRecVisitDateTime = null, dateSyncDateTime = null;

		try {
			if(dataModel.getRecbrowsercreated() != null) {
				if (dataModel.getRecbrowsercreated().length() > 0) {
				
					//date1 = new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(dataModel.get).getTime());
					dateBrowserCreated = convertLongtoDate(dataModel.getRecbrowsercreated());
				}
			}
			
			if(dataModel.getRecdatetime() != null) {
				if (dataModel.getRecdatetime().length() > 0) {
					dateRecDateTime = convertLongtoDate(dataModel.getRecdatetime());
				}
			}
			
			if(dataModel.getRecsyncdatetime() != null) {
				if (dataModel.getRecsyncdatetime().length() > 0) {
					dateSyncDateTime = convertLongtoDate(dataModel.getRecsyncdatetime());
				}
			}
			
			if(dataModel.getRecvisitdate() != null) {
				if (dataModel.getRecvisitdate().length() > 0) {
					dateRecVisitDateTime = convertLongtoDate(dataModel.getRecvisitdate());
				}
			}
			
			st = cn().prepareStatement("INSERT INTO pde_phone_data (recordid, recdatetime, reccategory, "
					+ "recdirection, reccontactname, reccontactnumber, recsyncdatetime, reclatitude, "
					+ "reclongitude, recsyncedbool, recduration, recurl, recvisits, recvisitdate, "
					+ "recbookmark, rectitle, recmessage, recbrowsercreated, recphoneid) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				st.setString(1, dataModel.getRecordid());
				st.setDate(2, dateRecDateTime);
				st.setString(3, dataModel.getReccategory());
				st.setString(4, dataModel.getRecdirection());
				st.setString(5, dataModel.getReccontactname());
				st.setString(6, dataModel.getReccontactnumber());
				st.setDate(7, dateSyncDateTime);
				st.setString(8, dataModel.getReclatitude());
				st.setString(9, dataModel.getReclongitude());
				st.setString(10,"Y"); // Y for synced
				st.setString(11, dataModel.getRecduration());
				st.setString(12, dataModel.getRecurl());
				st.setString(13, dataModel.getRecvisits());
				st.setDate(14, dateRecVisitDateTime);
				st.setString(15, dataModel.getRecbookmark());
				st.setString(16, dataModel.getRectitle());
				st.setString(17, dataModel.getRecmessage());			
				st.setDate(18, dateBrowserCreated);
				st.setString(19, dataModel.getRecphoneid());
			
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
	
	private java.sql.Date convertLongtoDate(String date){
		return new java.sql.Date(Long.valueOf(date));
	}
	
	public List<Data> deleteData(String transactionId) {
		List<Data> processData = new ArrayList<>();
		PreparedStatement st;
	
		try {
			
			st = cn().prepareStatement("DELETE FROM pde_phone_data WHERE recordid = '" + transactionId + "'");
			
			st.executeUpdate();
			st.close();
			Data data = new Data();
			data.setResponseId(transactionId);
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
	
	
}
