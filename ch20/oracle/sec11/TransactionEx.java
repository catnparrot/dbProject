package ch20.oracle.sec11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionEx {
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
			
			//트랜잭션 시작------------------------------
				//자동 커밋 기능 끄기
			conn.setAutoCommit(false);
			
			//출금 작업
			String sql1 = "UPDATE accounts SET balance=balance-? WHERE ano=?";
			PreparedStatement pst1 = conn.prepareStatement(sql1);
			pst1.setInt(1, 10000);
			pst1.setString(2, "111-111-1111");
			int rows1 = pst1.executeUpdate();
			if(rows1 == 0) throw new Exception("출금되지 않았음");
			pst1.close();
			
			//입금작업
			String sql2 = "UPDATE accounts SET balance=balance+? WHERE ano=?";
			PreparedStatement pst2 = conn.prepareStatement(sql2);
			pst2.setInt(1, 10000);
			pst2.setString(2, "222-222-2222");
			int row2 = pst2.executeUpdate();
			if(row2 == 0) throw new Exception("입금되지 않았음");
			pst2.close();
			
			//수동커밋
			conn.commit();
			System.out.println("계좌 이체 성공");
			//트랜잭션 종료-----------------------------------
			
			
			
		}catch(Exception e) {
			try {
				//수동롤백 -> 모두 실패처리
				conn.rollback();
			}catch(SQLException e1) {}
			System.out.println("계좌 이체 실패");
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {
					//원래대로 자동 커밋 기능 켜기
					conn.setAutoCommit(true);
					//연결 끊기
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
}
