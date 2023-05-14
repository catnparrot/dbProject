package ch20.oracle.sec07;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoardUpdateEx {
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
			
			//매개변수화된 SQL문 작성
			String sql = new StringBuilder()
					.append("UPDATE boards SET ")
					.append("btitle=?, ")
					.append("bcontent=?, ")
					.append("bfilename=?, ")
					.append("bfiledata=? ")
					.append("WHERE bno=?")
					.toString();
			
			//PreparedStatement 얻기 및 값 지정
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "눈사람");
			pst.setString(2, "눈으로 만든 사람");
			pst.setString(3, "snowman.jpg");
			pst.setBlob(4, new FileInputStream("src/ch20/oracle/sec07/snowman.jpg"));
			pst.setInt(5, 3);
			
			//SQL문 실행
			int rows = pst.executeUpdate();
			System.out.println("수정된 행 수: " + rows);
			
			//PreparedStatement 닫기
			pst.close();
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
