package gestion;

import java.sql.Connection;
import java.sql.DriverManager;

public class EleveConnection {
	public Connection getConnection() {
		Connection connect = null;
		try {
		// charger le pilote
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Driver etablit !!!");
	    connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/livre","root","");
	    //System.out.println("CONNECTION ETABLITE");
	}catch(Exception e) {
		System.out.println("connection failed");
		System.out.println(e);
	}
		return connect;
	}
}
