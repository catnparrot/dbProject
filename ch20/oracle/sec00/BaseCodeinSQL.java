package ch20.oracle.sec00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseCodeinSQL {
	
	
	public static void main(String[] args) {
		Connection conn = null;
		try {
			//JDBC Driver 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//연결하기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/xe",
					"parrot",
					"12345"
					);
			
			
			
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {
					//연결 끊기
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	

}
