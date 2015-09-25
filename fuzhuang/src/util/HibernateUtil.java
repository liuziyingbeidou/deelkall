package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
       
      private static SessionFactory sessionFactory;

      private HibernateUtil () {

      }
      
      static {
            Configuration cfg = new Configuration ();
            cfg.configure ();      // 此处缺省调用配置文件hibernate.cfg.xml，如果文件名不是这个，需要在这个地方进行配置
            sessionFactory = cfg.buildSessionFactory ();
      }

      public static SessionFactory getSesstionFactory () {

             return sessionFactory;
      }

      public static Session getSesstion () {
    	  return sessionFactory.openSession();
      }

}