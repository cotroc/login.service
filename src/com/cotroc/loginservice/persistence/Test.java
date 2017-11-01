package com.cotroc.loginservice.persistence;

import com.cotroc.loginservice.extra.MailHandler;
import com.cotroc.loginservice.objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;

import org.json.*;

public class Test {

	public static void main(String[] args) {
		JSONObject result = new JSONObject();
		try {
		      String query = "insert into user (name, pass, mail, active) values (?, ?, ?, ?)";
		      Connection conn;
		      conn = ConnDB.getDataSource().getConnection();
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, "omar");
		      preparedStmt.setString (2, "password");
		      preparedStmt.setString(3, "cotroc@gmail.com");
		      preparedStmt.setBoolean(4, false);
		      preparedStmt.execute();
		      conn.close();
		      int numero = preparedStmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		      System.out.println(String.valueOf(numero));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
