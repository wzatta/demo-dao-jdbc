package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJdbc implements SellerDao {

	private Connection conn;
	
	
	
	public SellerDaoJdbc(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		
		PreparedStatement st = null;
		String sql = "INSERT INTO seller "
				+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
				+ "VALUES"
				+ "(?, ?, ?, ?, ?)";
		
		try {
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setObject(3, obj.getBirthDate());
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! no rows affected!");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
		}		
	}

	@Override
	public void update(Seller obj) {
		
		PreparedStatement st = null;
		
		String sql = "UPDATE seller "
				+ "SET Name=?,Email=?,BirthDate=?,BaseSalary=?,DepartmentId=? "
				+ "WHERE id = ?";
		
		try {
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setObject(3, obj.getBirthDate());
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
		}		
	}

		
	

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		
		String sql = "DELETE FROM seller "
				+ "WHERE id=?";
		
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Seller findById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.departmentId = department.id "
					+ "WHERE seller.id =?";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Department dep = instatiateDepartment(rs);
				Seller seller = instatiateSeller(rs,dep);
			return seller;
			}
			
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
	}

	private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("name"));
		seller.setEmail(rs.getString("email"));
		seller.setBirthDate(rs.getDate("BirthDate").toLocalDate());
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);
		return seller;
	}

	private Department instatiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.departmentId = department.id "
					+ "ORDER BY Name";
			st = conn.prepareStatement(sql);
			
			rs = st.executeQuery();

			List<Seller> listSeller = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep==null) {
					dep = instatiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instatiateSeller(rs,dep);
				listSeller.add(seller);
			}
			
			return listSeller;
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
	}
	

	@Override
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.departmentId = department.id "
					+ "WHERE DepartmentId =? "
					+ "ORDER BY Name";
			st = conn.prepareStatement(sql);
			
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();

			List<Seller> listSeller = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep==null) {
					dep = instatiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instatiateSeller(rs,dep);
				listSeller.add(seller);
			}
			
			return listSeller;
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
	}

}
