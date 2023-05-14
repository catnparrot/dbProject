package ch20.oracle.sec05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDriverEx {
    public static void main(String[] args) {
    	try {
    		Class.forName("oracle.jdbc.driver.OracleDriver");
    		System.out.println("good");
		} catch (Exception e) {
			System.out.println("fxxx");
		};
		
    }
}