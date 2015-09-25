package test.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class Conn {
	private Connection conn = null;//
	private static String driver = null;
	private static String url = null;
	private static String user = null;
	private static String pass = null;
	static{
		ResourceBundle bundle = ResourceBundle.getBundle("db");
		driver = bundle.getString("db.driver");
	    url = bundle.getString("db.url");
	    user = bundle.getString("db.name");
	    pass = bundle.getString("db.pass");
	}
	
	public Connection getConn(){
		try {
			System.out.println(driver+"/n"+url+"/n"+user+pass);
			Class.forName(driver);//��������
			conn = DriverManager.getConnection(url, user, pass);//�������
		} catch (Exception ex) {
			ex.printStackTrace();
//			throws new Exception("�������ݿ�");
		}
		return conn;
	}
}
