package dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import utils.ICheckRule;
import utils.IUniqueRule;

import commonbeans.Page;


@SuppressWarnings("unchecked")


public interface IHibernateDAO {
	
	public Object fidId(Class entityClass,Integer id);
	
	/**
	 * 
	* @Description: 通过预备sql和值跟新数据库 
	* @param @param hql
	* @param @param valueList
	* @return void
	 */
	public void updateDateWithHql(String sql, List<Object> valueList);
	
	public Page findPageBySQL(Class entityClass, String eString,
			String countProjection, String sql, Object[] args, int start,
			int limit);
	
	public Page findPageBySQL(Class entityClass,String eString,String sql, Object[] args, int start, int limit);
	public int execute(String hql, Object... args);
	
	public void flush();
	
	public void refresh(Object entity);
	
	
	// 保存
	
	public void save(Object entity) throws Exception;
	
	// add by hezhh begin
	
	// 批量保存
	public void saveAll(List<?> entities);
	// 返回值为Internet的 保存
	public Integer saveReInt(Object entity) throws Exception;
	// 批量更新
	public void updateAll(List<?> entities);
	// 主子表保存  事物
	public void saveAll(String zbjson , String deleted , String inserted , String updated , Class<?> zhubiao , Class<?> zibiao , String parentid);
	// 主子表保存  事物
	public Integer saveAllReturnHeadId(String zbjson , String deleted , String inserted , String updated , Class<?> zhubiao , Class<?> zibiao , String parentid);
	
	// 主子表保存  事物 -- 刘忠庆
	public Integer saveAllReturnHeadIdByDelete(String zbjson , String deleted , String inserted , String updated , Class<?> zhubiao , Class<?> zibiao , String parentid);
		
	/**
	 * 分页查找 
	 * @param dgpage 当前页
	 * @param rows   每页几行数据
	 */
	public <E> List<E> findPage(Class<E> clasz , Integer dgpage , Integer rows);
	public <E> List<E> findPage(Class<E> clasz, Integer dgpage, Integer rows ,String conditions) ;
	public <E> List<E> findPageByDr(Class<E> clasz, Integer dgpage, Integer rows ,String conditions);
	public <E> List<E> findPage(Class<E> clasz, Integer dgpage, Integer rows ,String conditions,String order) ;
	public <E> List<E> findPage(Class<E> clasz, Integer dgpage, Integer rows ,String conditions,String order,String orderType) ;
	// 批量更新字段
	public void updateAttrs(Class<?> clasz , String[] attrname , Object[] attrval , String conditions);
	// 通过ids 批量更新字段
	public void updateAttrsByIDs(Class<?> clasz , String[] attrname , Object[] attrval , Integer[] ids);
	// 查询总共有多少条
	public Integer getCountByHQL( Class<?> clasz );
	// 查询总共有多少条 通过条件
	public Integer getCountByHQL( Class<?> clasz  , String conditions);
	//查询总条数，没有Dr的
	public Integer getCountByHQLByDr(Class<?> clasz, String conditions);
	
	// add by hezhh end
	
	// 更新
	
	public void update(Object entity) ;
	
	// 删除
	
	public void delete(Object entity) ;
	
	//查询 HQL
	public <E> List<E> findByHQL(Class<E> clasz, String hql, Object... args);
	
	public <E> E findUniqueByHQL(Class<E> clasz, String hql, Object... args);
	
	//查询hql
	public <E> List<E> findByHQLWithPamaList(Class<E> clasz, String hql, List<Object> pamaList);
	
	//查询 所有
	
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order, int start, int limit, String[] includes);
	
	
	public int executeSQL(String sql, Object... args);
	
	
	public boolean isExist(Class<?> entityClass, String whereClause,
			Object... values);
	
	public void saveOrUpdate(Object entity) throws Exception;
	
	public void deleteAll(Collection<?> entities);
	
	
	public void delete(Class<?> clasz, Object id) ;
	public void delete(Class<?> clasz, Object[] ids);
	
	// add by hezhh begin
	public void deleteByDr(Object entity) ;
	public void deleteAllByDr(Collection<?> entities);
	public void deleteByDr(Class<?> clasz, Object id) ;
	public void deleteByDrAry(Class<?> clasz, Integer[] ids);
	// add by hezhh end
	
	//xuelin 批量删除enties 可以传多个entities
	public void deleteAllEntiesByDr(Collection<?> entities) throws Exception;
	
	//根据条件删除 -- 刘忠庆 2015-5-29
	public void deleteByCondition(Class<?> clasz, Object condition) throws Exception;
	
	//根据条件物理删除
	public void deleteAllByCondition(Class<?> clasz, Object condition) throws Exception;
	
	public void delete(Class<?> clasz, String property, Object[] values);

	// 基本查找
	
	public <E> E getByProperty(Class<E> clasz, String name, Object value,
			String... includes);
	
	public <E> E getById(Class<E> clasz, Serializable id, String... includes);
	// Hibernate查找一个
	
	public <E> E findFirst(Class<E> clasz);
	
	public <E> E findFirst(Class<E> clasz, String[] includes);
	
	public <E> E findFirst(Class<E> clasz, String conditions);
	
	public <E> E findFirst(Class<E> clasz, String conditions, String[] includes);
	
	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args);
	public <E> E findFirstByDr(Class<E> clasz, String conditions) ;
	
	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args,
			String[] includes);
	
	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args,
			String order);
	
	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args,
			String order, String[] includes);
	
	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args,
			String order, int start, int limit, String[] includes);
	
	// Hibernate查找全部
	
	public <E> List<E> findAll(Class<E> clasz) ;
	
	public <E> List<E> findAll(Class<E> clasz, String[] includes);
	
	public <E> List<E> findAll(Class<E> clasz, String conditions);
	/** 查询，并根据order字段按照ordertype排序 */
	public <E> List<E> findAll(Class<E> clasz, String conditions,String order,String orderType);
	public <E> List<E> findAllByDr(Class<E> clasz, String conditions);
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			String[] includes);
	
	public <E> List<E> findAll(Class<E> clasz, String conditions, Object[] args);
	
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String[] includes) ;
	
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order);
	
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order, String[] includes);
	
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order, int limit) ;
	
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order, int limit, String[] includes);
	public <E> List<E> findAllByDr(Class<E> clasz, String conditions,
			Object[] args, String order, int start, int limit, String[] includes);

	
	public <E> E findFirstByHQL(Class<E> clasz, String hql, Object... args) ;
	
	
	public <E> List<E> findByHQL(Class<E> clasz, String hql, Object[] args,
			int limit);
	
	
	// 标量
	
	public Integer findInt(String hql, Object... args) ;

	
	public Long findLong(String hql, Object... args) ;
	
	public Double findDouble(String hql, Object... args);
	
	// 增加支持按sql 查询金额
	public Double findDoublebySQL(String sql, Object... args);
	
	public BigDecimal findBigDecimal(String hql, Object... args) ;
	
	public String findString(String hql, Object... args) ;


	
	// 分页查询
	
	public Page findPage(final DetachedCriteria dc, final int start,
			final int limit) ;

	
	public Page findPageByHQL(Class<?> clasz, String hql, Object[] args,
			int start, int limit) ;
	
	
	public Page findPageBySQL(String sql, Object[] args, int start, int limit);

	
	public List<?> findListBySQL(String sql, Object[] args, int start, int limit);
	
	
	public Integer getCountBySQL(String count_sql, Object... args) ;
	
	
	public Object getFirstBySQL(String sql, Object... args);
	
	
	public <E> List<E> findByCriteria(DetachedCriteria dc);
	
	
	public Page findPageBySQL(String sql, String count_sql, Object[] args,
			int start, int limit) ;
	
	public int findIntBySQL(String sql, Object... args);
	
	public void saveOrUpdateAll(List<?> entities) throws Exception ;
	
	public List findBySql(String sql,List<Object> valueList) ;
	
	
	public Object getMax(Class<?> entityClass, String propertyName,
			String scope, Object value) ;
	
	public Object getMin(Class<?> entityClass, String propertyName,
			String scope, Object value);
	
	public void down(Class<?> mappingClass, Serializable id, String groupBy) ;
	public void up(Class<?> mappingClass, Serializable id, String groupBy);
	public void orderNumUpOrDown(Class<?> mappingClass, Serializable id,
			String groupBy, boolean isDown);

	public List callProc(String name);
    // 给唯一性判断注入校验类
	public void setUniquerule(IUniqueRule uniquerule);
	  //  注入必输项校验类。
	public void setCheckrule(ICheckRule checkrule);
	//查询 所有
	
	public <E> List<E> findALLByCond(Class<E> clasz, String conditions,
			Object[] args, String order);
	public <E> List<E> findALLByCond(Class<E> clasz, String conditions);
	//根据sql和条件查询；
	public List findBySqlAndConditions(String sql,List<Object> valueList);
	//根据sql和条件查询；
	public List findBySqlAndConditions(String sql,List<Object> valueList,Class clazz);
}
