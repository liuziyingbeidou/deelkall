package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.materials.MaterialsVO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.MaterialsDao;

public class MaterialsDaoImpl extends HibernateDaoSupport implements  MaterialsDao{


	public void deleteBean(MaterialsVO MaterialsVO) {
		this.getHibernateTemplate().delete(MaterialsVO);
		
	}

	public void insertBean(MaterialsVO MaterialsVO) {
		this.getHibernateTemplate().save(MaterialsVO);
		
	}

	@SuppressWarnings("unchecked")
	public MaterialsVO selectBean(String where) {
		List<MaterialsVO> list = this.getHibernateTemplate().find("from MaterialsVO " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<MaterialsVO> selectBeanList(String where) {
		List<MaterialsVO> list = this.getHibernateTemplate().find("from MaterialsVO " +where);
		if(list.size()==0){
			return null;
		}
		return list;
	}
	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from MaterialsVO "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<MaterialsVO> selectBeanList(final int start,final int limit,final String where) {
		return (List<MaterialsVO>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<MaterialsVO> list = session.createQuery("from MaterialsVO "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(MaterialsVO MaterialsVO) {
		this.getHibernateTemplate().update(MaterialsVO);
		
	}
	
	
}
