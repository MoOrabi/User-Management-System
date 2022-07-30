package com.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.usermanagement.bean.User;

public class UserDao {
	private String jdbcURL="jdbc:mysql://localhost:3306/usermanagementdb";
	private String jdbcUserName="root";
	private String jdbcPassword="Mm12345,";
	private String jdbcDriver="com.mysql.jdbc.Driver";
	
	private static final String INSERT_USERS="Insert into users"+" (name,email,country) values " + "(?, ?, ?);";
	private static final String SELECT_USER_BY_ID="select id,name,email,country from users where id=?";
	private static final String SELECT_ALL_USERS="select * from users ;";
	private static final String DELETE_USERS="delete from users where id=? ;";
	private static final String UPDATE_USERS="update users set name=?,email=?,country=? where id=? ;";
	public UserDao() {
		
	}
	protected Connection getConnection() {
		Connection conn=null;
		try {
			Class.forName(jdbcDriver);
			conn=DriverManager.getConnection(jdbcURL,jdbcUserName,jdbcPassword);
			
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public void insertUser(User user) throws SQLException{
		System.out.println(INSERT_USERS);
		try {
			Connection conn=getConnection();
			PreparedStatement ps=conn.prepareStatement(INSERT_USERS);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getCountry());
			System.out.println(ps);
			ps.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	public User selectUser(int id) {
		User user=null;
		try {
			Connection conn=getConnection();
			PreparedStatement ps=conn.prepareStatement(SELECT_USER_BY_ID);
			ps.setInt(1, id);
			System.out.println(ps);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				String name=rs.getString("name");
				String email=rs.getString("email");
				String country=rs.getString("country");
				user=new User(id,name, email, country);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}
	
	public List<User> selectAllUsers() {
		List<User> users=new ArrayList<>();
		try {
			Connection conn=getConnection();
			PreparedStatement ps=conn.prepareStatement(SELECT_ALL_USERS);
			System.out.println(ps);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
				String email=rs.getString("email");
				String country=rs.getString("country");
				users.add(new User(id,name, email, country));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;
	}
	
	public boolean deleteUser(int id)throws SQLException {
		boolean rowDeleted=false;
		try {
			Connection conn=getConnection();
			PreparedStatement ps=conn.prepareStatement(DELETE_USERS);
			
			ps.setInt(1, id);
			
			rowDeleted= ps.executeUpdate()>0;
		} finally {
			return rowDeleted;
		}
		
	}
	
	public boolean updateUser(User user) {
		boolean rowUpdated=false;
		try {
			Connection conn=getConnection();
			PreparedStatement ps=conn.prepareStatement(UPDATE_USERS);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getCountry());
			ps.setInt(4, user.getId());
			
			rowUpdated= ps.executeUpdate()>0;
		} catch (SQLException e) {
			printSQLException(e);
		}
		return rowUpdated;
	}
	
	
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if(e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: "+((SQLException) e).getSQLState());
				System.err.println("SQLError: "+((SQLException) e).getErrorCode());
				System.err.println("Message: "+ e.getMessage());
				Throwable t=ex.getCause();
				while(t !=null) {
					System.out.println("Cause "+t);
					t=t.getCause();
				}
			}
		}
	}
}
