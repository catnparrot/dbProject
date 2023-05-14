package ch20.oracle.sec06;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardWithFileInsertEx {
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
			
			String sql = "" +
			"INSERT INTO boards (bno, btitle, bcontent, bwriter, bdate, bfilename, bfiledata) "
					+ "VALUES(SEQ_BNO.NEXTVAL, ?, ?, ?, SYSDATE, ?, ?)";
			
			PreparedStatement pst = conn.prepareStatement(sql, new String[] {"bno"});
			pst.setString(1, "눈 오는 날");
			pst.setString(2, "함박눈이 내려요.");
			pst.setString(3, "winter");
			pst.setString(4, "snow.jpg");
			pst.setBlob(5, new FileInputStream("src/ch20/oracle/sec06/snow.jpg"));
			
			int rows = pst.executeUpdate();
			System.out.println("저장된 행의 수: "+rows);
			
			
			
			//bno 값 얻기
			if(rows == 1) {
				ResultSet rs = pst.getGeneratedKeys();
				if(rs.next()) {
					int bno = rs.getInt(1);
					System.out.println("저장된 bno: "+ bno);
				}
				rs.close();
			}
			
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
