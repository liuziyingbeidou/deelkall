package service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import commonbeans.Page;

import dao.IHibernateDAO;

import service.ICommonService;



@Service
public class CommonService implements ICommonService{
	@Autowired @Qualifier("hibernateDAO")
	IHibernateDAO dao;
	
	public boolean isExist(Class<?> entityClass,String whereClause, Object... values ){
		return dao.isExist(entityClass, whereClause,values);
	}
	
	
	public int execute(String hql, Object... args) {
		return dao.execute(hql, args);
	}
	public <E> E getByProperty(Class<E> clasz, String name, Object value,
			String... includes){
		return dao.getByProperty(clasz, name, value, includes);
	}

	public <E> E getById(Class<E> clasz, Serializable id, String... includes){
		return dao.getById(clasz, id, includes);
	}
	
	public void save(Object entity) throws Exception {
		dao.save(entity);
	}
	
	public void update(Object entity) {
		dao.update(entity);
	}
	
	public void saveOrUpdate(Object entity) throws Exception {
		dao.saveOrUpdate(entity);
	}
	
	/*
	public void delete(Object entity) {
		dao.delete(entity);
	}
	
	public void deleteAll(Collection<?> entities) {
		dao.deleteAll(entities);
	}
	
	public void delete(Class<?> clasz, Object id) {
		dao.delete(clasz,id);
		
	}
	
	public void delete(Class<?> clasz, Object[] ids) {
		dao.delete(clasz,ids);
	}
	*/
	
	// add by hezhh begin
	//@Override
	public void deleteByDr(Object entity) {
		dao.deleteByDr(entity);
	}
	
	//@Override
	public void deleteAllByDr(Collection<?> entities) {
		dao.deleteAllByDr(entities);
	}
	
	//@Override
	public void deleteByDr(Class<?> clasz, Object id) {
		dao.deleteByDr(clasz, id);
	}
	
	//@Override
	public void deleteByDrAry(Class<?> clasz, Integer[] ids) {
		dao.deleteByDrAry(clasz, ids);
	}
	
	//@Override
	public <E> List<E> findPage(Class<E> clasz, Integer dgpage, Integer rows) {
		return dao.findPage(clasz, dgpage, rows);
	}
	
	//@Override
	public <E> List<E> findPage(Class<E> clasz, Integer dgpage, Integer rows,
			String conditions) {
		return dao.findPage(clasz, dgpage, rows, conditions);
	}
	
	//@Override
	public <E> List<E> findPageByDr(Class<E> clasz, Integer dgpage, Integer rows,
			String conditions) {
		return dao.findPageByDr(clasz, dgpage, rows, conditions);
	}
	
	//@Override
	public <E> List<E> findPage(Class<E> clasz, Integer dgpage, Integer rows,
			String conditions,String order,String orderType) {
		return dao.findPage(clasz, dgpage, rows, conditions,order,orderType);
	}
	
	//@Override
	public void updateAttrs(Class<?> clasz, String[] attrname, Object[] attrval, String conditions) {
		dao.updateAttrs(clasz, attrname, attrval, conditions);
	}
	
	//@Override
	public void updateAttrsByIDs(Class<?> clasz, String[] attrname, Object[] attrval, Integer[] ids) {
		dao.updateAttrsByIDs(clasz, attrname, attrval, ids);
	}
	
	//@Override
	public Integer getCountByHQL(Class<?> clasz) {
		return dao.getCountByHQL(clasz);
	}
	
	/* (non-Javadoc)
	 * @see com.xinleju.erp.sa.service.ICommonService#getCountByHQL(java.lang.Class, java.lang.String)
	 */
	//@Override
	public Integer getCountByHQL(Class<?> clasz, String conditions) {
		return dao.getCountByHQL(clasz, conditions);
	}
	
	/* (non-Javadoc)
	 * @see com.xinleju.erp.sa.service.ICommonService#getCountByHQL(java.lang.Class, java.lang.String)
	 */
	//@Override
	public Integer getCountByHQLByDr(Class<?> clasz, String conditions) {
		return dao.getCountByHQLByDr(clasz, conditions);
	}
	
	// add by hezhh end
	
	public <E> E findFirst(Class<E> clasz) {
		return dao.findFirst(clasz);
	}
	
	public <E> E findFirst(Class<E> clasz, String[] includes) {
		return dao.findFirst(clasz,includes);
	}
	
	public <E> E findFirst(Class<E> clasz, String conditions) {
		return dao.findFirst(clasz,conditions);
	}
	
	public <E> E findFirstByDr(Class<E> clasz, String conditions) {
		return dao.findFirstByDr(clasz,conditions);
	}
	
	
	public <E> E findFirst(Class<E> clasz, String conditions, String[] includes) {
		return dao.findFirst(clasz,conditions,includes);
	}
	
	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args) {
		return dao.findFirst(clasz,conditions,args);
	}
	
	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args,
			String[] includes) {
		return dao.findFirst(clasz,conditions,args,includes);
	}
	
	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args,
			String order) {
		return dao.findFirst(clasz,conditions,args,order);
	}
	
	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args,
			String order, String[] includes) {
		return dao.findFirst(clasz,conditions,args,order,includes);
	}
	

	
	public <E> List<E> findAll(Class<E> clasz) {
		return dao.findAll(clasz);
	}
	
	public <E> List<E> findAll(Class<E> clasz, String[] includes) {
		return dao.findAll(clasz,includes);
	}
	
	public <E> List<E> findAll(Class<E> clasz, String conditions) {
		return dao.findAll(clasz,conditions);
	}
	
	public <E> List<E> findAllByDr(Class<E> clasz, String conditions) {
		return dao.findAllByDr(clasz,conditions);
	}
	
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			String[] includes) {
		return dao.findAll(clasz,conditions,includes);
	}
	
	public <E> List<E> findAll(Class<E> clasz, String conditions, Object[] args) {
		return dao.findAll(clasz,conditions,args);
	}
	public Page findPageBySQL(Class entityClass, String eString,
			String countProjection, String sql, Object[] args, int start,
			int limit){
		return dao.findPageBySQL(entityClass, eString, countProjection, sql, args, start, limit);
	}
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String[] includes) {
		return dao.findAll(clasz,conditions,args,includes);
	}
	
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order) {
		return dao.findAll(clasz,conditions,args,order);
	}
	
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order, String[] includes) {
		return dao.findAll(clasz,conditions,args,order,includes);
	}
	
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order, int limit) {
		return dao.findAll(clasz,conditions,args,order,limit);
	}
	
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order, int limit, String[] includes) {
		return dao.findAll(clasz,conditions,args,order,limit,includes);
	}
	
	public <E> E findFirstByHQL(Class<E> clasz, String hql, Object... args) {
		return dao.findFirstByHQL(clasz,hql,args);
	}
	
	public <E> List<E> findByHQL(Class<E> clasz, String hql, Object... args) {
		return dao.findByHQL(clasz,hql,args);
	}
	
	public <E> List<E> findByHQL(Class<E> clasz, String hql, Object[] args,
			int limit) {
		return dao.findByHQL(clasz,hql,args,limit);
	}
	
	public Integer findInt(String hql, Object... args) {
		return dao.findInt(hql,args);
	}
	
	public Long findLong(String hql, Object... args) {
		return dao.findLong(hql,args);
	}
	
	public Double findDouble(String hql, Object... args) {
		return dao.findDouble(hql,args);
	}
	
	public BigDecimal findBigDecimal(String hql, Object... args) {
		return dao.findBigDecimal(hql,args);
	}
	
	public String findString(String hql, Object... args) {
		return dao.findString(hql,args);
	}
	
	public  Page findPage(DetachedCriteria dc, int start, int limit) {
		return dao.findPage(dc,start,limit);
	}
	
	public Page findPageByHQL(Class<?> clasz, String hql, Object[] args,
			int start, int limit) {
		return dao.findPageByHQL(clasz,hql,args,start,limit);
	}
	public Page findPageBySQL(String sql, Object[] args,
			int start, int limit) {
		return dao.findPageBySQL(sql,args,start,limit);
	}
	public void saveOrUpdateAll(List<?> entities) throws Exception {
		dao.saveOrUpdateAll(entities);
		
	}
	public <E> List<E> findByCriteria(DetachedCriteria dc) {
		return dao.findByCriteria(dc);
	}


	
	public Object getMax(Class<?> entityClass, String propertyName,
			String scope, Object value) {
		
		return dao.getMax(entityClass, propertyName, scope, value);
	}


	
	public Object getMin(Class<?> entityClass, String propertyName,
			String scope, Object value) {
		return dao.getMin(entityClass, propertyName, scope, value);
	}


	
	public void down(Class<?> mappingClass, Serializable id, String groupBy) {
		dao.down(mappingClass, id, groupBy);
		
	}


	
	public void up(Class<?> mappingClass, Serializable id, String groupBy) {
		dao.up(mappingClass, id, groupBy);
		
	}


	public Page findPageBySQL(String sql, String count_sql, Object[] args, int start, int limit) {
		
		return dao.findPageBySQL(sql, count_sql, args, start, limit);
	}


	public int executeSQL(String sql, Object... args) {
		
		return dao.executeSQL(sql, args);
	}


	public int findIntBySQL(String sql, Object... args) {
		return dao.findIntBySQL(sql, args);
	}


	public IHibernateDAO getDao() {
		return dao;
	}


	public void setDao(IHibernateDAO dao) {
		this.dao = dao;
	}


	public List callProc(String name) {
		
		return dao.callProc(name);
	}


	public List<?> findListBySQL(String sql, Object[] args, int start, int limit) {
		// TODO Auto-generated method stub
		return dao.findListBySQL(sql, args, start, limit);
	}


	//@Override
	public Page findPageBySQL(Class entityClass, String eString, String sql,
			Object[] args, int start, int limit) {
		return dao.findPageBySQL(entityClass,eString,sql, args, start, limit);
	}


	//@Override
	public int saveReID(Object entity) throws Exception {
		return dao.saveReInt(entity);
	}

	//@Override
	public Object saveReDTO(Object entity) throws Exception {
		int id=dao.saveReInt(entity);
		return dao.fidId(entity.getClass(), id);
	}

}
