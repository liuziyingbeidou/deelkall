package test.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class loginCl {
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	
	/*
	 * �ر�����
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
	 * ��֤�û���¼
	 * @param username
	 * @param password
	 * @return flag(0:�û���/�������1:��¼�ɹ�)��־��֤״̬ 
	 */
	public boolean loginYz(String username,String password){
		//ע���ʱ�û�����Ψһ��
		boolean flag =false;
		String sql = "select * from user where r_username ='"+username +"'and r_pwd ='"+password+"'";
		//System.out.println("123"); 
		try {
			conn = new Conn().getConn();//�������ݿ�
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			//System.out.println("1234");
			if(rs.next()){
				//������ȷ����¼�ɹ�
				//System.out.println("��֤�ɹ�!");
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
			conn = new Conn().getConn();//�������ݿ�
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
