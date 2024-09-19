package vn.iotstar.configs;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectSQLServer {
	private final String serverName = "localhost";
	private final String dbName = "ltwebst2";
	private final String portNumber = "1433";
	private final String instance = "SQLEXPRESS_SP3";
	private final String userID = "sa";
	private final String password = "trantiendat1679@";
	
	public Connection getConnection() {
		Connection conn = null;
		
		try {
			String url = "jbdc:sqlserver://" + serverName + ":" + portNumber + "\\" + instance + ";databaseName=" + dbName;
			
			if (instance == null || instance.trim().isEmpty())
				url = "jbdc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;
			
			conn = DriverManager.getConnection(url, userID, password);
			
			if (conn != null) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: " + dm.getDatabaseProductName());
				System.out.println("Product version: " + dm.getDatabaseProductVersion());
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		try {
			System.out.println(new DBConnectSQLServer().getConnection());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
