package ch20.oracle.sec05;

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