package com.codeforanyone.edujavajsp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.codeforanyone.edujavajsp.model.MemberNotFoundException;
import com.codeforanyone.edujavajsp.model.MemberObj;

/**
 * Data access object for a MemberObj entity; this class understands how to load
 * and save MemberObj entities in a SQL database.
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
public class MemberDAO {
	DataSource ds;

	public MemberDAO() {
		this.ds = DataSource.getInstance();
	}

	public void save(MemberObj m) throws SQLException {
		System.out.println("Debug. About to save " + m);
		if (m.getId() != null) {
			update(m);
		} else {
			insert(m);
		}
	}

	private void insert(MemberObj m) throws SQLException {
		String sql = "insert into member (user_id, role, petition_id) values (?,?,?)";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, m.getUserId());
			pstmt.setInt(2, m.getRoleId());
			pstmt.setInt(3, m.getPetitionId());
			pstmt.executeUpdate();

			ResultSet primaryKeys = pstmt.getGeneratedKeys();
			if (primaryKeys.next()) {
				m.setId(primaryKeys.getInt(1));
			}
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}

	private void update(MemberObj m) throws SQLException {
		String sql = "update member set user_id=?, role=?, petition_id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, m.getUserId());
			pstmt.setInt(2, m.getRoleId());
			pstmt.setInt(3, m.getPetitionId());
			pstmt.executeUpdate();
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
	}

	public void delete(MemberObj m) throws SQLException {
		String sql = "delete from member where id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, m.getId());
			pstmt.executeUpdate();
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}
	public MemberObj get(int id) throws SQLException, MemberNotFoundException {
		String sql = "select * from member where id=?";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			res = pstmt.executeQuery();

			if (res.next()) {
				MemberObj m = loadMember(res);
				return m;
			} else {
				throw new MemberNotFoundException("Error: Member not found with id " + id + "!");
			}
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}
	public List<MemberObj> list() throws SQLException {
		List<MemberObj> members = new ArrayList<MemberObj>();
		String sql = "select * from member";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			res = pstmt.executeQuery();

			while (res.next()) {
				MemberObj m = loadMember(res);
				members.add(m);
			}
			return members;
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}

	public List<MemberObj> search(String text) throws SQLException {
		List<MemberObj> members = new ArrayList<MemberObj>();
		String sql = "select * from member, user where user.user_name like ? and member.user_id=user.user_id";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);

			// % is the wildcard character, so "user names like %ppl%" would match on applesauce
			pstmt.setString(1, "%" + text + "%");
				res = pstmt.executeQuery();

			while (res.next()) {
				MemberObj m = loadMember(res);
				members.add(m);
			}
			return members;
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}


	}


	private MemberObj loadMember(ResultSet res) throws SQLException{
		MemberObj m = new MemberObj();
		m.setId(res.getInt("member_id"));
		m.setUserId(res.getInt("user_id"));
		m.setRoleId(res.getInt("role_id"));
		m.setPetitionId(res.getInt("petition_id"));
		return m;
	}


}
