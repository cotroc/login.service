package com.cotroc.loginservice.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import com.cotroc.loginservice.extra.MailHandler;
import com.cotroc.loginservice.objects.User;

public class MailDB {
	//falta terminar
	public String saveActivationData(User u) {
	    JSONObject result = new JSONObject();
		try {
		      String query = "insert into user (name, pass, mail, active) values (?, ?, ?, ?)";
		      Connection conn;
		      conn = ConnDB.getDataSource().getConnection();
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, u.getName());
		      preparedStmt.setString (2, u.getPwd());
		      preparedStmt.setString(3, u.getMail());
		      preparedStmt.setBoolean(4, false);
		      preparedStmt.execute();
		      conn.close();
		      result.put("status", "inserted");
		      result.put("name", u.getName());
		      result.put("mail", u.getMail());
		      MailHandler mailHandler = new MailHandler();
		      mailHandler.sendActivationLink(u.getMail(), u.getName());
		      return result.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return result.put("status", "failed").toString();
		}
	}

}
