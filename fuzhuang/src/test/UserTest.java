package test;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

import model.User;

public class UserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		query("admin","admin");
	}
	
	public static void query(String name,String password){
		Session s=null;
		  try{
		   s=HibernateUtil.getSesstion();
		   
		   Criteria c=s.createCriteria(User.class);
		   c.add(Restrictions.eq("username",name));//eq是等于，gt是大于，lt是小于,or是或
		   c.add(Restrictions.eq("password", password));
		   
		   List<User> list=c.list();
		   for(User user:list){
		    System.out.println(user.getUsername());
		   }
		  }finally{
		   if(s!=null)
		   s.close();
		  }
	}
	
	public static void insert(){
		User u = new User();
		//u.setPk_user(1);
		u.setUsername("admin");
		u.setPassword("admin");
         
		Session s = null;
		Transaction tx = null;

		try {

		     s = HibernateUtil.getSesstion();
		     tx = s.beginTransaction();
		     s.save(u);
		     tx.commit ();
		} catch (HibernateException e) {         // catch 可以省略
		     if (tx != null) {
		           tx.rollback ();
		     }
		     throw e;
		} finally {
		     if (s != null) {
		         s.close ();
		     }
		}
	}
}
