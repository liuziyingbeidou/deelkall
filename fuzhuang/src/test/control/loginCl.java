package test.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class loginCl {
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	
	/*
	 * 关闭连接
	 */
	public void close(){
		
			try {
				if(conn!=null){
					conn.close();
				}
				if(st!=null){
					st.close();
				}
				if(rs!=null){
					rs.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * 验证用户登录
	 * @param username
	 * @param password
	 * @return flag(0:用户名/密码错误；1:登录成功)标志验证状态 
	 */
	public boolean loginYz(String username,String password){
		//注意此时用户名是唯一的
		boolean flag =false;
		String sql = "select * from user where r_username ='"+username +"'and r_pwd ='"+password+"'";
		//System.out.println("123"); 
		try {
			conn = new Conn().getConn();//连接数据库
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			//System.out.println("1234");
			if(rs.next()){
				//密码正确，登录成功
				//System.out.println("验证成功!");
				flag =true;
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}finally{
			this.close();
		}
		return flag;
	}
	public int getLeve(String uname){
		int leve=3;
		String sql = "select * from r_admin where r_username ='"+uname +"'";
		//System.out.println("123"); 
		try {
			conn = new Conn().getConn();//连接数据库
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			//System.out.println("1234");
			while(rs.next()){
				
				leve = rs.getInt("r_leve");
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}finally{
			this.close();
		}
		return leve;
	}
}
