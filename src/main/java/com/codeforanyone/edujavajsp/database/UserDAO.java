package com.codeforanyone.edujavajsp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.codeforanyone.edujavajsp.model.UserNotFoundException;
import com.codeforanyone.edujavajsp.model.UserObj;

/**
 * Data access object for a LOLCat entity; this class understands how to load
 * and save LOLCat entities in a SQL database.
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
public class UserDAO {
	DataSource ds;

	public UserDAO() {
		this.ds = DataSource.getInstance();
	}

	public void save(UserObj u) throws SQLException {
		if (u.getId() != null) {
			update(u);
		} else {
			insert(u);
		}
	}

	private void insert(UserObj u) throws SQLException {
		String sql = "insert into user (user_name, user_password, user_first_name, user_last_name, user_phone, user_email, is_phone_private, is_email_private) values (?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, u.getUserName());
			pstmt.setString(2, u.getPW());
			pstmt.setString(3, u.getFirstName());
			pstmt.setString(4, u.getLastName());
			pstmt.setString(5, u.getPhone());
			pstmt.setString(6, u.getEmail());
			pstmt.setBoolean(7, u.getIsPhonePrivate());
			pstmt.setBoolean(8, u.getIsEmailPrivate());
			pstmt.executeUpdate();

			ResultSet primaryKeys = pstmt.getGeneratedKeys();
			if (primaryKeys.next()) {
				u.setId(primaryKeys.getInt(1));
			}

		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
	}

	private void update(UserObj u) throws SQLException {
		String sql = "update user set user_name=?, user_password=?, user_first_name=?, user_last_name=?, user_phone=?, user_email=?, is_phone_private=?, is_email_private=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getUserName());
			pstmt.setString(2, u.getPW());
			pstmt.setString(3, u.getFirstName());
			pstmt.setString(4, u.getLastName());
			pstmt.setString(5, u.getPhone());
			pstmt.setString(6, u.getEmail());
			pstmt.setBoolean(7, u.getIsPhonePrivate());
			pstmt.setBoolean(8, u.getIsEmailPrivate());
			pstmt.executeUpdate();

		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
	}

	public void delete(UserObj u) throws SQLException {
		String sql = "delete from user where user_id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, u.getId());
			pstmt.executeUpdate();
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
	}

	public UserObj get(int id) throws SQLException, UserNotFoundException {
		String sql = "select * from user where user_id=?";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			res = pstmt.executeQuery();
			if (res.next()) {
				UserObj u = loadUser(res);
				return u;
			} else {
				throw new UserNotFoundException("Error: User not found with id: " + id + ". Have a good day!");
			}
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
	}

	public List<UserObj> list() throws SQLException {
		List<UserObj> usrs = new ArrayList<UserObj>();
		String sql = "select * from user";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			res = pstmt.executeQuery();

			while (res.next()) {
				UserObj u = loadUser(res);
				usrs.add(u);
			}
			return usrs;
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}

	private UserObj loadUser(ResultSet res) throws SQLException {
		UserObj u = new UserObj();
		u.setId(res.getInt("user_id"));
		u.setPW(res.getString("user_password"));
		u.setUserName(res.getString("user_name"));
		u.setFirstName(res.getString("user_first_name"));
		u.setLastName(res.getString("user_last_name"));
		u.setPhone(res.getString("user_phone"));
		u.setEmail(res.getString("user_email"));
		u.setIsEmailPrivate(res.getBoolean("is_email_private"));
		u.setIsPhonePrivate(res.getBoolean("is_phone_private"));
		return u;
	}

	// ToDO: Work on password functions for retrieving, encrypting, and saving
	// passwords
	/*
	 * public boolean isCorrectPW(String provided, UserObj u) throws
	 * SQLException { // ToDO: finish function to verify if the password in the
	 * provided // String is the same as the one in the database String pw = new
	 * String(); boolean isSame = false;
	 * 
	 * return isSame; }
	 * 
	 * private String encryptPW(UserObj u) { // ToDO: finish function to create
	 * an encrypted string for the password String encrypted = new String();
	 * return encrypted; }
	 */
}
