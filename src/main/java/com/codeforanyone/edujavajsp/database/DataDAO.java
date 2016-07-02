package com.codeforanyone.edujavajsp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.codeforanyone.edujavajsp.model.DataNotFoundException;
import com.codeforanyone.edujavajsp.model.DataObj;


/**
 * Data access object for a DataObj entity; this class understands how to load
 * and save DataObj entities in a SQL database.
 * 
 * Stack Overflow has a decent explanation of the Data Access Object design
 * pattern:
 * http://stackoverflow.com/questions/19154202/data-access-object-dao-in-java
 * 
 * We're not using an interface because we don't need to implement more than one
 * of these for different databases.
 * 
 * @author jennybrown and jacstone
 *
 */
public class DataDAO {
	DataSource ds;

	public DataDAO() {
		this.ds = DataSource.getInstance();
	}

	public void save(DataObj d) throws SQLException {
		System.out.println("Debug. About to save " + d);
		if (d.getId() != null) {
			update(d);
		} else {
			insert(d);
		}
	}

	private void insert(DataObj d) throws SQLException {
		String sql = "insert into data (petition_id, user_id, data_date, start_time, stop_time, signatures_collected, address, city, state, zipcode) values (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, d.getPetitionId());
			pstmt.setInt(2, d.getUserId());
			pstmt.setDate(3, d.getDate());
			pstmt.setTime(4, d.getStartTime());
			pstmt.setTime(5, d.getStopTime());
			pstmt.setInt(6, d.getSignatures());
			pstmt.setString(7, d.getAddress());
			pstmt.setString(8, d.getCity());
			pstmt.setString(9, d.getState());
			pstmt.setString(10, d.getZip());
			pstmt.executeUpdate();

			ResultSet primaryKeys = pstmt.getGeneratedKeys();
			if (primaryKeys.next()) {
				d.setId(primaryKeys.getInt(1));
			}
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}

	private void update(DataObj d) throws SQLException {
		String sql = "update data set petition_id=?, user_id=?, data_date=?, start_time=?, stop_time=?, signatures_collected=?, address=?, city=?, state=?, zipcode=? where data_id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, d.getPetitionId());
			pstmt.setInt(2, d.getUserId());
			pstmt.setDate(3, d.getDate());
			pstmt.setTime(4, d.getStartTime());
			pstmt.setTime(5, d.getStopTime());
			pstmt.setInt(6, d.getSignatures());
			pstmt.setString(7, d.getAddress());
			pstmt.setString(8, d.getCity());
			pstmt.setString(9, d.getState());
			pstmt.setString(10, d.getZip());
			pstmt.setInt(11, d.getId());
			pstmt.executeUpdate();
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}
	public void delete(DataObj d) throws SQLException {
		String sql = "delete from data where data_id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, d.getId());
			pstmt.executeUpdate();
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
	}
	public DataObj get(int id) throws SQLException, DataNotFoundException {
		String sql = "select * from data where data_id=?";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			res = pstmt.executeQuery();

			if (res.next()) {
				DataObj d = loadData(res);
				return d;
			} else {
				throw new DataNotFoundException("Error: Data not found with id " + id + "!");
			}
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}
	
	//lists all the data in a petition
	public List<DataObj> list(int id) throws SQLException {
		List<DataObj> datas = new ArrayList<DataObj>();
		String sql = "select * from data where petition_id=?";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			res = pstmt.executeQuery();

			while (res.next()) {
				DataObj d = loadData(res);
				datas.add(d);
			}
			return datas;
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}
	
	public List<DataObj> list(int petitionId, int userId) throws SQLException {
		List<DataObj> datas = new ArrayList<DataObj>();
		String sql = "select * from data where petition_id=? and user_id=?";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, petitionId);
			pstmt.setInt(2, userId);
			res = pstmt.executeQuery();

			while (res.next()) {
				DataObj d = loadData(res);
				datas.add(d);
			}
			return datas;
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}

	private DataObj loadData(ResultSet res) throws SQLException {
DataObj d = new DataObj();
d.setId(res.getInt("data_id"));
d.setPetitionId(res.getInt("petition_id"));
d.setUserId(res.getInt("user_id"));
d.setDate(res.getDate("data_date"));
d.setStartTime(res.getTime("start_time"));
d.setStopTime(res.getTime("stop_time"));
d.setSignatures(res.getInt("signatures_collected"));
d.setAddress(res.getString("address"));
d.setCity(res.getString("city"));
d.setState(res.getString("state"));
d.setZip(res.getString("zipcode"));
		return d;
	}

}
