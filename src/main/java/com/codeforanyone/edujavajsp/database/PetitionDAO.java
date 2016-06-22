package com.codeforanyone.edujavajsp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.codeforanyone.edujavajsp.model.PetitionNotFoundException;
import com.codeforanyone.edujavajsp.model.PetitionObj;

/**
 * Data access object for a PetitionObj entity; this class understands how to
 * load and save PetitionObj entities in a SQL database.
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

public class PetitionDAO {

	DataSource ds;

	public PetitionDAO() {
		this.ds = DataSource.getInstance();
	}

	public void save(PetitionObj p) throws SQLException {
		System.out.println("Debug. About to save " + p);
		if (p.getId() != null) {
			update(p);
		} else {
			insert(p);
		}
	}

	private void update(PetitionObj p) throws SQLException {
		String sql = "update petition set petition_name=?, petition_admin=? where id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p.getName());
			pstmt.setInt(2, p.getAdmin());
			pstmt.executeUpdate();
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}

	private void insert(PetitionObj p) throws SQLException {
		String sql = "insert into petition (petition_name, petition_admin) values (?,?)";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, p.getName());
			pstmt.setInt(2, p.getAdmin());
			pstmt.executeUpdate();

			ResultSet primaryKeys = pstmt.getGeneratedKeys();
			if (primaryKeys.next()) {
				p.setId(primaryKeys.getInt(1));
			}
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}
	
	public void delete(PetitionObj p) throws SQLException {
		String sql = "delete from petition where petition_id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p.getId());
			pstmt.executeUpdate();
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}
	}
	public PetitionObj get(int id) throws SQLException, PetitionNotFoundException {
		String sql = "select * from petition where petition_id=?";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			res = pstmt.executeQuery();

			if (res.next()) {
				PetitionObj p = loadPetition(res);
				return p;
			} else {
				throw new PetitionNotFoundException("Error: Petition not found with id " + id + "!");
			}
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}

	private PetitionObj loadPetition(ResultSet res) throws SQLException{
		PetitionObj p = new PetitionObj();
		p.setId(res.getInt("petition_id"));
		p.setName(res.getString("petition_name"));
		p.setAdmin(res.getInt("petition_admin"));
		return p;
	}
	
	public List<PetitionObj> list() throws SQLException {
		List<PetitionObj> petitions = new ArrayList<PetitionObj>();
		String sql = "select * from petition";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			res = pstmt.executeQuery();

			while (res.next()) {
				PetitionObj p = loadPetition(res);
				petitions.add(p);
			}
			return petitions;
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}

	}
	public List<PetitionObj> search(String text) throws SQLException {
		List<PetitionObj> petitions = new ArrayList<PetitionObj>();
		String sql = "select * from petition where petition_name like ? ";

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet res = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);

			// % is the wildcard character, so "title like %ppl%" would match on applesauce
			pstmt.setString(1, "%" + text + "%");
			res = pstmt.executeQuery();

			while (res.next()) {
				PetitionObj p = loadPetition(res);
				petitions.add(p);
			}
			return petitions;
		} finally {
			DataSource.silentClose(pstmt);
			DataSource.silentClose(conn);
		}


	}

}
