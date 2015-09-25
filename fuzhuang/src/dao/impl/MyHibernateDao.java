package dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.IMyHibernateDao;

/**
 * 
* @Title: 服装系统
* @ClassName: MyHibernateDao 
* @Description: DAO
* @author liuzy
* @date 2015-6-16 上午10:36:57
 */
public class MyHibernateDao extends HibernateDaoSupport implements IMyHibernateDao {

	@Override
	public void insertBean(Object entity) {
		this.getHibernateTemplate().save(entity);

	}

	@Override
	public void deleteBean(Object entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public void updateBean(Object entity) {
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public <E> List<E> selectBeanList(Class<E> clasz,String where) {
		List list = this.getHibernateTemplate().find("from "+clasz.getName()+" " +where);
		if(list.size()==0){
			return null;
		}
		return list;
	}

	@Override
	public <E> E  selectBean(Class<E> clasz,String where) {
		List<E> list = this.getHibernateTemplate().find("from "+clasz.getName()+" " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}



	@Override
	public Integer selectBeanCount(Class<?> clasz,String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from "+clasz.getName() +" "+ where).get(0);
		return (int)count;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <E> List<E> selectBeanList(final Class<E> clasz, final int start,final int limit,
			final String where) {
		return (List<E>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<E> list = session.createQuery("from "+clasz.getName()+" "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

}
