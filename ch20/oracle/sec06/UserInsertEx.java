package ch20.oracle.sec06;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserInsertEx {
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
			
			
			//매개변수화된 SQL문
			String sql = "" +
			"INSERT INTO users (userid, username, userpassword, userage, useremail) "
					+ "VALUES (?, ?, ?, ?, ?)";
			
			
			//PreparedStatement 얻기 및 지정
			//레코드 입력
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "winter");
			pst.setString(2, "한겨울");
			pst.setString(3, "12345");
			pst.setInt(4, 25);
			pst.setString(5, "winter@mycompany.com");
			
			//SQL 문 실행
			int rows = pst.executeUpdate();
			System.out.println("저장된 행 수: " + rows);
			
			//PreparedStatement 닫기
			pst.close();
			
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e) {
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
