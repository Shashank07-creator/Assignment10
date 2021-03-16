package DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	private static Connection con;
	private	static String user = "System",password = "sys";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url,user,password);
		} 
		catch (ClassNotFoundException e) {
			System.out.println("Error in DBUtil.Connect :-"+e.getMessage());
		} 
		catch (SQLException e) {
			System.out.println("Error in DBUtil.Connect :-"+e.getMessage());
		}
	}
	
	public static Connection getConnection() {
		return con;
	}
}
