package ch20.oracle.sec10;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class ProcedureCallEx {
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
			
			String sql = "{call user_create(?, ?, ?, ?, ?, ?)}";
			CallableStatement cst = conn.prepareCall(sql);
			
			cst.setString(1, "summer");
			cst.setString(2, "한여름");
			cst.setString(3, "12345");
			cst.setInt(4, 26);
			cst.setString(5, "summer@mycompany.com");
			cst.registerOutParameter(6, Types.INTEGER);
			
			cst.execute();
			int rows = cst.getInt(6);
			System.out.println("저장된 행 수: " + rows);
			
			cst.close();
			
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
