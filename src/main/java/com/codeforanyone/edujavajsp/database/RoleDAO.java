package com.codeforanyone.edujavajsp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.codeforanyone.edujavajsp.model.RoleNotFoundException;
import com.codeforanyone.edujavajsp.model.RoleObj;

/**
 * Data access object for a RoleObj entity; this class understands how to load
 * and save RoleObj entities in a SQL database.
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
public class RoleDAO {
	DataSource ds;

	public RoleDAO() {
		this.ds = DataSource.getInstance();
	}

	public void save(RoleObj r) throws SQLException {
		System.out.println("Debug. About to save " + r);
		if (r.getId() != null) {
			update(r);
		} else {
			insert(r);
		}
	}

	private void insert(RoleObj r) throws SQLException{
		String sql = "insert into role (role_name, role_is_public, role_petition_id, role_creator) values (?,?,?,?)";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, r.getName());
			pstmt.setBoolean(2, r.getIsPublic());
			pstmt.setInt(3, r.getPetitionId());
			pstmt.setInt(4, r.getCreator());
			pstmt.executeUpdate();

			ResultSet primaryKeys = pstmt.getGeneratedKeys();
			if (primaryKeys.next()) {
				r.setId(primaryKeys.getInt(1));
			}
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
		
	}

	private void update(RoleObj r) throws SQLException{
		String sql = "update role set role_name=?, role_is_public=?, role_petition_id=?, role_creator=? where role_id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, r.getName());
			pstmt.setBoolean(2, r.getIsPublic());
			pstmt.setInt(3, r.getPetitionId());
			pstmt.setInt(4, r.getCreator());
			pstmt.setInt(5, r.getId());
			pstmt.executeUpdate();
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
		
	}

	public void delete(RoleObj r) throws SQLException {
		String sql = "delete from role where role_id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, r.getId());
			pstmt.executeUpdate();
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
	}

	public RoleObj get(int id) throws SQLException, RoleNotFoundException {
		String sql = "select * from role where role_id=?";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			res = pstmt.executeQuery();

			if (res.next()) {
				RoleObj r = loadRole(res);
				return r;
			} else {
				throw new RoleNotFoundException("Error: Role not found with id " + id + "!");
			}
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}

	public List<RoleObj> list() throws SQLException {
		List<RoleObj> roles = new ArrayList<RoleObj>();
		String sql = "select * from role";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			res = pstmt.executeQuery();

			while (res.next()) {
				RoleObj f = loadRole(res);
				roles.add(f);
			}
			return roles;
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}

	public List<RoleObj> search(String text) throws SQLException {
		List<RoleObj> roles = new ArrayList<RoleObj>();
		String sql = "select * from role where role_name like ?";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);

			// % is the wildcard character, so "role_name like %ppl%" would match on applesauce
			pstmt.setString(1, "%" + text + "%");
			res = pstmt.executeQuery();

			while (res.next()) {
				RoleObj r = loadRole(res);
				roles.add(r);
			}
			return roles;
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}


	}

	private RoleObj loadRole(ResultSet res) throws SQLException {
		RoleObj r = new RoleObj();
		r.setId(res.getInt("role_id"));
		r.setName(res.getString("role_name"));
		r.setIsPublic(res.getBoolean("role_is_public"));
		r.setPetitionId(res.getInt("role_petition_id"));
		r.setCreator(res.getInt("role_creator"));
		return r;
	}

}
