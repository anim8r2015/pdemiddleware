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
				dataList.add(data);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	public void insertUpdateData(String transaction, int Id, String name, String date) {
		PreparedStatement st;

		Date date1 = null;

		try {

			date1 = new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(date).getTime());
			if (transaction.equalsIgnoreCase("A")) {
				st = cn().prepareStatement("INSERT INTO pde_data (data_id, data_name, data_date) " + "VALUES(?,?,?)");
				st.setString(1, String.valueOf(Id));
				st.setString(2, name);
				st.setDate(3, date1);

			} else { // edit
				st = cn().prepareStatement(
						"UPDATE pde_data SET data_name = '" + name + "', data_date = " + date + " WHERE data_id = " +

								Id + "");
			}

			st.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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

}
