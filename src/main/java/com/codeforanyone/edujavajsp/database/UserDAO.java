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

	private void update(UserObj u) {
		// TODO Auto-generated method stub
		
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
}
