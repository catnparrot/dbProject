package ch20.oracle.sec12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BoardEx9 {
	private Scanner sc = new Scanner(System.in);
	private Connection conn;
	
	public BoardEx9() {
		try {
			//JDBC Driver 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//연결하기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/xe",
					"parrot",
					"12345"
					);

		}catch(Exception e) {
			e.printStackTrace();
			exit();
		}
	}
	
	public void list() {
		System.out.println();
		System.out.println("[게시물 목록]");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-6s%-12s%-16s%-40s\n", "no", "writer", "date", "title");
        System.out.println("-------------------------------------------------");
        
        try {
			String sql = ""+
					"SELECT bno, btitle, bcontent, bwriter, bdate " +
					"FROM boards "+
					"ORDER BY bno DESC";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Board board = new Board();
				board.setBno(rs.getInt("bno"));
				board.setBtitle(rs.getString("btitle"));
				board.setBcontent(rs.getString("bcontent"));
				board.setBwriter(rs.getString("bwriter"));
				board.setBdate(rs.getDate("bdate"));
		        System.out.printf("%-6s%-12s%-16s%-40s\n",
		        		board.getBno(),
		        		board.getBwriter(),
		        		board.getBdate(),
		        		board.getBtitle()
		        		);

			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			exit();
		}
     
        mainMenu();
	}
	
	public void mainMenu() {
        System.out.println("-------------------------------------------------");
        System.out.println("메인 메뉴: 1. create | 2. read | 3. clear | 4. exit");
        System.out.print("메뉴 선택: ");
        String menuNo = sc.nextLine();
        System.out.println();
        
        switch(menuNo) {
        	case "1" -> create();
        	case "2" -> read();
        	case "3" -> clear();
        	case "4" -> exit();
        }
	}
                     
        
	
//------------------------------------------------------------------------ 
    public void create() {
    	Board board = new Board();
    	System.out.println("[새 게시물 입력]");
    	System.out.print("제목: ");
    	board.setBtitle(sc.nextLine());
    	System.out.print("내용: ");
    	board.setBcontent(sc.nextLine());
    	System.out.print("작성자: ");
    	board.setBwriter(sc.nextLine());
    	
    	System.out.println("---------------------------------------");
    	System.out.println("보조 메뉴: 1.OK | 2.Cancel");
    	System.out.print("메뉴선택: ");
    	String menuNo = sc.nextLine();
    	if(menuNo.equals("1")) {
    		try {
    			String sql = "" +
        				"INSERT INTO boards (bno, btitle, bcontent, bwriter, bdate) "+
        				"VALUES (SEQ_BNO.NEXTVAL, ?, ?, ?, SYSDATE)";
    			PreparedStatement pst = conn.prepareStatement(sql);
    			pst.setString(1, board.getBtitle());
    			pst.setString(2, board.getBcontent());
    			pst.setString(3, board.getBwriter());
    			pst.executeUpdate();
    			pst.close();
			} catch (Exception e) {
				e.printStackTrace();
				exit();
			}
    	}
    	
    	list();
    }
        
        
//------------------------------------------------------------------------ 

        
    public void read() {
    	
    	System.out.println("[게시물 읽기]");
    	System.out.print("bno: ");
    	int bno = Integer.parseInt(sc.nextLine());
    	
    	try {
			String sql = ""+
					"SELECT bno, btitle, bcontent, bwriter, bdate " +
					"FROM boards "+
					"WHERE bno=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, bno);
			
			ResultSet rs = pst.executeQuery();
    		if(rs.next()) {
    			Board board = new Board();
				board.setBno(rs.getInt("bno"));
				board.setBtitle(rs.getString("btitle"));
				board.setBcontent(rs.getString("bcontent"));
				board.setBwriter(rs.getString("bwriter"));
				board.setBdate(rs.getDate("bdate"));
				
				
				//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
				
				
				System.out.println("--------------------");
				System.out.println("보조 메뉴: 1.Update | 2.Delete | 3.List");
				System.out.print("메뉴 선택: ");
				String menuNo = sc.nextLine();
				System.out.println();
				
				if(menuNo.equals("1")) {
					update(board);
				}else if(menuNo.equals("2")) {
					delete(board);
				}
				
				
				//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<				
				
				
				System.out.println("#############");
				System.out.println("번호: " + board.getBno());
				System.out.println("제목: " + board.getBtitle());
				System.out.println("내용: " + board.getBcontent());
				System.out.println("작성자: " + board.getBwriter());
				System.out.println("날짜: " + board.getBdate());
				System.out.println("#############");
    		}
    		rs.close();
    		pst.close();
		} catch (Exception e) {
			e.printStackTrace();
			exit();
		}

    	list();
    }
    

	public void update(Board board) {
		System.out.println("[수정 내용 입력]");
		System.out.print("제목: ");
		board.setBtitle(sc.nextLine());
		System.out.print("내용: ");
		board.setBcontent(sc.nextLine());
		System.out.print("작성자: ");
		board.setBwriter(sc.nextLine());
		
    	System.out.println("---------------------------------------");
    	System.out.println("보조 메뉴: 1.OK | 2.Cancel");
    	System.out.print("메뉴선택: ");
    	String menuNo = sc.nextLine();
    	if(menuNo.equals("1")) {
			try {
    			String sql = "" +
        				"UPDATE boards SET btitle=?, bcontent=?, bwriter=? "+
        				"WHERE bno=?";
    			PreparedStatement pst = conn.prepareStatement(sql);
    			pst.setString(1, board.getBtitle());
    			pst.setString(2, board.getBcontent());
    			pst.setString(3, board.getBwriter());
    			pst.setInt(4, board.getBno());
    			pst.executeUpdate();
    			pst.close();
			} catch (Exception e) {
				e.printStackTrace();
				exit();
			}
    	}
    	list();
	}
		
	public void delete(Board board) {
		try {
			String sql = "DELETE FROM boards WHERE bno=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, board.getBno());
			pst.executeUpdate();
			pst.close();
		}catch (Exception e) {
			e.printStackTrace();
			exit();
		}
		
		list();
	}

	
	
//----------------------------------------------------------------------


	public void clear() {
		System.out.println("[게시물 전체 삭제]");
		System.out.println("------------------");
		System.out.println("보조 메뉴: 1.OK | 2.Cancel");
		System.out.print("메뉴 선택: ");
		String menuNo = sc.nextLine();
		if(menuNo.equals("1")) {
			try {
				String sql = "TRUNCATE TABLE boards";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.executeUpdate();
				pst.close();
			}catch (Exception e) {
				e.printStackTrace();
				exit();
			}
		}
		
		list();
	}
	
	
//--------------------------------------------------------------------------
	

	public void exit() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		System.out.println("** 게시판 종료 **");
		System.exit(0);
	}
	
}
