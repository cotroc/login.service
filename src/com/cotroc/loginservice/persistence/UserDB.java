package com.cotroc.loginservice.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.cotroc.loginservice.extra.MailHandler;
import com.cotroc.loginservice.objects.User;

public class UserDB {
	
	public UserDB() {

	}
	
	public String createUser(User u) {
	    JSONObject result = new JSONObject();
		try {
		      String query = "insert into user (name, pass, mail, active) values (?, ?, ?, ?)";
		      Connection conn;
		      conn = ConnDB.getDataSource().getConnection();
		      PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		      preparedStmt.setString (1, u.getName());
		      preparedStmt.setString (2, u.getPwd());
		      preparedStmt.setString(3, u.getMail());
		      preparedStmt.setBoolean(4, false);
		      preparedStmt.execute();
	          ResultSet generatedKeys = preparedStmt.getGeneratedKeys();
	          if (generatedKeys.next()) {
	        	  
	        	  result.put("id", generatedKeys.getLong(1));
	        	  String stringId = String.valueOf(result.getInt("id"));//
	        	  print(stringId,"string id");
	          }
		      conn.close();
		      result.put("status", "inserted");
		      result.put("name", u.getName());
		      result.put("mail", u.getMail());
		      MailHandler mailHandler = new MailHandler();
		      mailHandler.sendActivationLink(u.getName(), u.getMail());
		      return result.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return result.put("status", "failed").toString();
		}
	}
	
	public String userActivation(String userName, String mail) {
		JSONObject result = new JSONObject();
		print(userName, mail);
		try {
			String query = "UPDATE user SET active=true WHERE name =? AND mail =?";
			Connection conn;
		    conn = ConnDB.getDataSource().getConnection();
		    PreparedStatement preparedStmt = conn.prepareStatement(query);
		    preparedStmt.setString (1, userName);
		    preparedStmt.setString (2, mail);
			preparedStmt.execute();
			if(preparedStmt.executeUpdate() > 0) {
				result.put("active","true");
			} else {
				result.put("active", "false");
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public String updateUser(User u) {
		JSONObject result = new JSONObject();
		try {
		      String query = "UPDATE user SET name=?, pass=?, mail=? WHERE id=?";
		      Connection conn;
		      conn = ConnDB.getDataSource().getConnection();
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, u.getName());
		      preparedStmt.setString (2, u.getPwd());
		      preparedStmt.setString(3,  u.getMail());
		      preparedStmt.execute();
				if(preparedStmt.executeUpdate() > 0) {
					result.put("status","updated");
				} else {
					result.put("status", "failed");
				}
		      conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public String deleteUser(User u) {
		JSONObject result = new JSONObject();
		try {
		      String query = "DELETE FROM user WHERE id=?";
		      Connection conn;
		      conn = ConnDB.getDataSource().getConnection();
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setInt(1, u.getId());
		      preparedStmt.execute();
				if(preparedStmt.executeUpdate() > 0) {
					result.put("status","deleted");
				} else {
					result.put("status", "failed");
				}
		      conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public List<User> getAllUsers() {
		Connection conn;
		Statement stmt;
		ResultSet rs;
		List<User> list = new ArrayList<User>();
		try {
			conn = ConnDB.getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM User");
			while (rs.next()) {
				User u = new User(rs.getString("name"),
								rs.getString("pass"));
				u.setId(rs.getInt("id"));
				u.setMail(rs.getString("mail"));
				u.setActive(rs.getBoolean("active"));
				list.add(u);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public User findUserByName(String name) {
		Statement stmt = null;
		Connection conn;
		ResultSet rs;
		User u = null;
		try {
			
			conn = ConnDB.getDataSource().getConnection();
			String queryString = "SELECT * FROM User where name=" + "\"" + name + "\"";
			print(queryString, "queryString");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(queryString);
				u = new User();
				while (rs.next()) {
					u.setId(rs.getInt("id"));
					u.setName(rs.getString("name"));
					u.setPwd(rs.getString("pass"));
					u.setMail(rs.getString("mail"));
					u.setActive(rs.getBoolean("active"));
					}
			conn.close();
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}
	
	public boolean existUserByName(String name) {
		boolean existUser = false;
		User u = findUserByName(name);
		//u = findUserByName(name);
		print(u.toString(), "UserDB - existUserByName()");
		if(u.getId() != 0) {
			if(u.getName().equals(name)) {
				existUser = true;
			}
		}
		return existUser;
	}

	public User verifyUser2(User u) { //ResponseWrapper
		User user = findUserByName(u.getName());
		try {
			
			if(u.getPwd().equals(user.getPwd()) &&
					u.getName().equals(user.getName())) {
					return user;
				} else {
					user = new User();
				}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public void print(String message, String method) {
		System.out.println("MESSAGE: " + message + "; FROM: " + method);
	}
}
