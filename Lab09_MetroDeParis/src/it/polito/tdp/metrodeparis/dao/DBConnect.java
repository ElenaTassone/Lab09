package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;
public class DBConnect {

	static private final String jdbcUrl = "jdbc:mysql://localhost/metroparis?user=root";
	
	private static DataSource ds;
	static private DBConnect instance = null;
	private DBConnect() {
		instance = this;
	}

	public static DBConnect getInstance() {
		if (instance == null)
			return new DBConnect();
		else {
			return instance;
		}
	}

	public Connection getConnection() {
		try {
			
			Connection conn = DriverManager.getConnection(jdbcUrl);
			return conn;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al database");
		}
	}
//}



//	public static Connection getConnection() {
//
//		if (ds == null) {
//			// crea il DataSource
//			try {
//				ds = DataSources.pooledDataSource(
//					DataSources.unpooledDataSource(jdbcUrl));
//			} catch (SQLException e) {
//				e.printStackTrace();
//				System.exit(1);
//			}
//		}
//
//		try {
//			Connection c = ds.getConnection();
//			return c;
//			} catch (SQLException e) {
//					e.printStackTrace();
//					return null;
//				}
//
//	}
}