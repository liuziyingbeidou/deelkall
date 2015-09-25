package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.materials.MateChildVO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.MateChildDao;

public class MateChildDaoImpl extends HibernateDaoSupport implements  MateChildDao{


	public void deleteBean(MateChildVO MateChildVO) {
		this.getHibernateTemplate().delete(MateChildVO);
		
	}

	public void insertBean(MateChildVO MateChildVO) {
		this.getHibernateTemplate().save(MateChildVO);
		
	}

	@SuppressWarnings("unchecked")
	public MateChildVO selectBean(String where) {
		List<MateChildVO> list = this.getHibernateTemplate().find("from MateChildVO " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<MateChildVO> selectBeanList(String where) {
		List<MateChildVO> list = this.getHibernateTemplate().find("from MateChildVO " +where);
		if(list.size()==0){
			return null;
		}
		return list;
	}
	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from MateChildVO "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<MateChildVO> selectBeanList(final int start,final int limit,final String where) {
		return (List<MateChildVO>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<MateChildVO> list = session.createQuery("from MateChildVO "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(MateChildVO MateChildVO) {
		this.getHibernateTemplate().update(MateChildVO);
		
	}
	
	
}
