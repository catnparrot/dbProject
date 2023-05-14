package ch20.oracle.sec09.exam01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSelectEx {
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
					"SELECT userid, username, userpassword, userage, useremail " +
					"FROM users " +
					"WHERE userid=?";
			
			//PreparedStatement 얻기 및 값 지정
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "winter");
			
			//SQL문 실행 후, ResultSet을 통해 데이터 읽기
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				User user = new User();
				user.setUserId(rs.getString("userid"));
				user.setUserName(rs.getString("username"));
				user.setUserPassword(rs.getString(3));
				user.setUserAge(rs.getInt(4));
				user.setUserEmail(rs.getString(5));
	
				System.out.println(user);
				
			} else {
				System.out.println("사용자 아이디가 존재하지 않음");
			}
			rs.close();
			
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
