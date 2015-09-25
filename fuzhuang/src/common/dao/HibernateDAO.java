package dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
//import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import utils.CheckRule;
import utils.DaoHelper;
import utils.DateUtils;
import utils.ICheckRule;
import utils.IUniqueRule;
import utils.JsonUtils;
import utils.ReflectionUtils;
import utils.SQLUtils;
import utils.TypeExchangeUtils;
import utils.UniqueRule;

import commonbeans.Page;

import exception.JException;
import exception.TxException;


@SuppressWarnings("unchecked")
public class HibernateDAO implements IHibernateDAO {

	SessionFactory sessionFactory;
	IUniqueRule uniquerule;
	ICheckRule checkrule;

	// 执行

	public int execute(String hql, Object... args) {

		int x = createQuery(hql, args).executeUpdate();

		// ######################不要删除这一行######################
		// getSession().flush();
		// getSession().clear();
		// ######################不要删除这一行######################
		return x;

	}

	public void flush() {
		getSession().flush();
	}

	public void refresh(Object entity) {
		getSession().refresh(entity);
	}

	// 保存
	public void save(Object entity) throws Exception {
		beforeSave(entity);
		getSession().save(entity);
		afterSave(entity);
	}

	// 保存前处理
	public void beforeSave(Object entity) throws Exception {
		uniquerule = this.getUniquerule();
		checkrule = this.getCheckrule();
		boolean flags = checkrule.check(entity, this);
		if (!flags) {
			throw new Exception(checkrule.getErrMsg());
		}
		boolean flag = uniquerule.check(entity, this);
		if (!flag) {
			throw new Exception(uniquerule.getErrMsg());
		}
	}

	// 保存前处理
	public void afterSave(Object entity) {

	}

	// add by hezhh begin
	// 批量保存
	public void saveAll(List<?> entities) {
		if(entities != null && entities.size()>0){
			for (Object entity : entities) {
				if (entity != null) {
					getSession().save(entity);
				}
			}
		}
	}

	// 返回值为Internet的 保存
	public Integer saveReInt(Object entity) throws Exception {
		beforeSave(entity);
		Integer id = (Integer) getSession().save(entity);
		afterSave(entity);
		return id;
	}

	// 批量更新
	public void updateAll(List<?> entities) {
		for (Object entity : entities) {
			try {
				if (tm.getClass().getSimpleName()
						.equals("HibernateTransactionManager")) {
					getSession().update(entity);
				} else {
					getSession().merge(entity);
				}
			} catch (Exception e) {
				logger.error("getSession().merge(entity); in HibernateDao"
						+ entity.getClass().getSimpleName() + " ERROR!");
			}
		}
	}

	// 主子表保存 事物
	/**
	 * hezhh 主子表保存 事物
	 * 
	 * @param zbjson
	 *            主表的json
	 * @param deleted
	 *            子表需要删除的json
	 * @param inserted
	 *            子表需要新增的json
	 * @param updated
	 *            子表需要更新的json
	 * @param zhubiao
	 *            主表的model class
	 * @param zibiao
	 *            子表的model class
	 * @param parentid
	 *            子表中主表主键名称
	 */
	@Transactional
	public void saveAll(String zbjson, String deleted, String inserted,
			String updated, Class<?> zhubiao, Class<?> zibiao, String parentid) {
		Integer zbid = null;
		StringBuffer pid = new StringBuffer();
		if (parentid != null && !"".equals(parentid)) {
			pid.append(parentid.substring(0, 1).toUpperCase());
			pid.append(parentid.substring(1, parentid.length()).toLowerCase());
		}
		try {

			if (zbjson != null) {
				Object dsf = JsonUtils.getObject4JsonString(zbjson, zhubiao);
				Method getId = zhubiao.getMethod("getId");
				if (getId.invoke(dsf) == null) {
					zbid = saveReInt(dsf);
				} else {
					zbid = (Integer) getId.invoke(dsf);
					update(dsf);
				}
			}

			if (deleted != null) {
				List<Object> dellist = JsonUtils.getList4Json(deleted, zibiao);
				Integer[] idary = new Integer[dellist.size()];
				for (int i = 0; i < dellist.size(); i++) {
					Method getId = zibiao.getMethod("getId");
					idary[i] = (Integer) getId.invoke(dellist.get(i));
				}
				deleteByDrAry(zibiao, idary);
			}

			if (inserted != null) {
				List<Object> inslist = JsonUtils.getList4Json(inserted, zibiao);
				for (int i = 0; i < inslist.size(); i++) {
					if (zbid != null && pid != null) {
						Method setDsftzid = zibiao.getMethod(
								"set" + pid.toString(), Integer.class);
						setDsftzid.invoke(inslist.get(i), zbid);
					}
				}
				saveAll(inslist);
			}

			if (updated != null) {
				List<Object> updatelist = JsonUtils.getList4Json(updated,
						zibiao);
				for (int i = 0; i < updatelist.size(); i++) {
					if (zbid != null && pid != null) {
						Method setDsftzid = zibiao.getMethod(
								"set" + pid.toString(), Integer.class);
						setDsftzid.invoke(updatelist.get(i), zbid);
					}
				}
				updateAll(updatelist);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * hezhh 主子表保存 事物
	 * 
	 * @param zbjson
	 *            主表的json
	 * @param deleted
	 *            子表需要删除的json
	 * @param inserted
	 *            子表需要新增的json
	 * @param updated
	 *            子表需要更新的json
	 * @param zhubiao
	 *            主表的model class
	 * @param zibiao
	 *            子表的model class
	 * @param parentid
	 *            子表中主表主键名称
	 * @return 主表主键
	 */
	@Transactional
	public Integer saveAllReturnHeadId(String zbjson, String deleted,
			String inserted, String updated, Class<?> zhubiao, Class<?> zibiao,
			String parentid) {
		Integer zbid = null;
		StringBuffer pid = new StringBuffer();
		if (parentid != null && !"".equals(parentid)) {
			pid.append(parentid.substring(0, 1).toUpperCase());
			pid.append(parentid.substring(1, parentid.length()).toLowerCase());
		}
		try {

			if (zbjson != null) {
				Object dsf = JsonUtils.getObject4JsonString(zbjson, zhubiao);
				Method getId = zhubiao.getMethod("getId");
				if (getId.invoke(dsf) == null) {
					zbid = saveReInt(dsf);
				} else {
					zbid = (Integer) getId.invoke(dsf);
					update(dsf);
				}
			}

			if (deleted != null) {
				List<Object> dellist = JsonUtils.getList4Json(deleted, zibiao);
				Integer[] idary = new Integer[dellist.size()];
				for (int i = 0; i < dellist.size(); i++) {
					Method getId = zibiao.getMethod("getId");
					idary[i] = (Integer) getId.invoke(dellist.get(i));
				}
				deleteByDrAry(zibiao, idary);
			}

			if (inserted != null) {
				List<Object> inslist = JsonUtils.getList4Json(inserted, zibiao);
				for (int i = 0; i < inslist.size(); i++) {
					if (zbid != null && pid != null) {
						Method setDsftzid = zibiao.getMethod(
								"set" + pid.toString(), Integer.class);
						setDsftzid.invoke(inslist.get(i), zbid);
					}
				}
				saveAll(inslist);
			}

			if (updated != null) {
				List<Object> updatelist = JsonUtils.getList4Json(updated,
						zibiao);
				for (int i = 0; i < updatelist.size(); i++) {
					if (zbid != null && pid != null) {
						Method setDsftzid = zibiao.getMethod(
								"set" + pid.toString(), Integer.class);
						setDsftzid.invoke(updatelist.get(i), zbid);
					}
				}
				updateAll(updatelist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zbid;

	}

	/**
	 * hezhh 主子表保存 事物 ：特殊逻辑-- 把子表的数据删除，然后全部按照插入处理；
	 * 
	 * @param zbjson
	 *            主表的json
	 * @param deleted
	 *            子表需要删除的json
	 * @param inserted
	 *            子表需要新增的json
	 * @param updated
	 *            子表需要更新的json
	 * @param zhubiao
	 *            主表的model class
	 * @param zibiao
	 *            子表的model class
	 * @param parentid
	 *            子表中主表主键名称
	 * @return 主表主键
	 */
	@Transactional
	public Integer saveAllReturnHeadIdByDelete(String zbjson, String deleted,
			String inserted, String updated, Class<?> zhubiao, Class<?> zibiao,
			String parentid) {
		Integer zbid = null;
		StringBuffer pid = new StringBuffer();
		if (parentid != null && !"".equals(parentid)) {
			pid.append(parentid.substring(0, 1).toUpperCase());
			pid.append(parentid.substring(1, parentid.length()).toLowerCase());
		}
		try {

			if (zbjson != null) {
				Object dsf = JsonUtils.getObject4JsonString(zbjson, zhubiao);
				Method getId = zhubiao.getMethod("getId");
				if (getId.invoke(dsf) == null) {
					zbid = saveReInt(dsf);
				} else {
					zbid = (Integer) getId.invoke(dsf);
					update(dsf);
				}
			}

			// 子表数据处理
			if (deleted != null) {
				List<Object> dellist = JsonUtils.getList4Json(deleted, zibiao);
				Integer[] idary = new Integer[dellist.size()];
				for (int i = 0; i < dellist.size(); i++) {
					Method getId = zibiao.getMethod("getId");
					idary[i] = (Integer) getId.invoke(dellist.get(i));
				}
				deleteByDrAry(zibiao, idary);
			}

			if (inserted != null) {
				List<Object> inslist = JsonUtils.getList4Json(inserted, zibiao);
				Integer[] idary = new Integer[inslist.size()];
				for (int i = 0; i < inslist.size(); i++) {
					Method getId = zibiao.getMethod("getId");
					idary[i] = (Integer) getId.invoke(inslist.get(i));

					if (zbid != null && pid != null) {
						Method setDsftzid = zibiao.getMethod(
								"set" + pid.toString(), Integer.class);
						setDsftzid.invoke(inslist.get(i), zbid);
					}
				}
				deleteByDrAry(zibiao, idary);
				saveAll(inslist);
			}

			if (updated != null) {
				List<Object> updatelist = JsonUtils.getList4Json(updated,
						zibiao);
				Integer[] idary = new Integer[updatelist.size()];
				for (int i = 0; i < updatelist.size(); i++) {
					Method getId = zibiao.getMethod("getId");
					idary[i] = (Integer) getId.invoke(updatelist.get(i));

					if (zbid != null && pid != null) {
						Method setDsftzid = zibiao.getMethod(
								"set" + pid.toString(), Integer.class);
						setDsftzid.invoke(updatelist.get(i), zbid);
					}
				}
				deleteByDrAry(zibiao, idary);
				saveAll(updatelist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zbid;

	}

	/**
	 * 分页查找
	 * 
	 * @param dgpage
	 *            当前页
	 * @param rows
	 *            每页几行数据
	 * @see com.xinleju.erp.sa.dao.IHibernateDAO#findPage(java.lang.Class,
	 *      java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("rawtypes")
	public <E> List<E> findPage(Class<E> clasz, Integer dgpage, Integer rows) {
		int start = (dgpage - 1) * rows;
		int limit = rows;
		List ll = findAll(clasz, null, null, null, start, limit, null);
		return ll;
	}

	/**
	 * 按条件分页查找
	 * 
	 * @param dgpage
	 *            当前页
	 * @param rows
	 *            每页几行数据
	 * @see com.xinleju.erp.sa.dao.IHibernateDAO#findPage(java.lang.Class,
	 *      java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("rawtypes")
	public <E> List<E> findPage(Class<E> clasz, Integer dgpage, Integer rows,
			String conditions) {
		int start = 0;
		int limit = 0;
		if (dgpage != null && rows != null) {
			start = (dgpage - 1) * rows;
			limit = rows;
		}
		;
		List ll = findAll(clasz, conditions, null, null, start, limit, null);
		return ll;
	}

	/**
	 * 按条件分页查找 ,没有设置dr的
	 * 
	 * @param dgpage
	 *            当前页
	 * @param rows
	 *            每页几行数据
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public <E> List<E> findPageByDr(Class<E> clasz, Integer dgpage,
			Integer rows, String conditions) {
		int start = 0;
		int limit = 0;
		if (dgpage != null && rows != null) {
			start = (dgpage - 1) * rows;
			limit = rows;
		}
		;
		List ll = findAllByDr(clasz, conditions, null, null, start, limit, null);
		return ll;
	}

	public <E> List<E> findPage(Class<E> clasz, Integer dgpage, Integer rows,
			String conditions, String order) {
		int start = (dgpage - 1) * rows;
		int limit = rows;
		List ll = findAll(clasz, conditions, null, order, start, limit, null);
		return ll;
	}

	public <E> List<E> findPage(Class<E> clasz, Integer dgpage, Integer rows,
			String conditions, String order, String orderType) {
		int start = 0;
		int limit = 0;
		if (dgpage != null && rows != null) {
			start = (dgpage - 1) * rows;
			limit = rows;
		}
		;
		List ll = findAll(clasz, conditions, null, order, orderType, start,
				limit, null);
		return ll;
	}

	/**
	 * 根据ID数组 批量更新
	 * 
	 * @param clasz
	 * @param attrname
	 * @param attrval
	 * @param ids
	 */
	public void updateAttrsByIDs(Class<?> clasz, String[] attrname,
			Object[] attrval, Integer[] ids) {
		String conditions = SQLUtils.buildSqlForIn("id",
				TypeExchangeUtils.IntAryToStrAry(ids));
		updateAttrs(clasz, attrname, attrval, conditions);
	}

	/**
	 * 根据条件批量更新某些字段
	 * 
	 * @param clasz
	 * @param attrname
	 * @param attrval
	 * @param conditions
	 */
	public void updateAttrs(Class<?> clasz, String[] attrname,
			Object[] attrval, String conditions) {
		String hql = "update " + clasz.getName() + " as e set e.ts = '"
				+ DateUtils.formatDateTime(new Date()).toString() + "'";
		// 需要更新的字段
		if (attrname != null && attrname.length > 0) {
			for (int i = 0; i < attrname.length; i++) {
				hql += ", " + attrname[i] + " = ?";
			}
			hql = DaoHelper.insertAlias(hql, clasz);
		}
		// 更新条件
		if (StringUtils.isNotEmpty(conditions)) {
			hql += " where (" + DaoHelper.insertAlias(conditions, clasz)
					+ " and ifnull(e.dr,0)=0 )";
		} else {
			hql += " where ( ifnull(e.dr,0)=0 )";
		}

		execute(hql, attrval);
	}

	public Integer getCountByHQL(Class<?> clasz) {
		// String hql = "select count(id) from " + clasz.getName() ;
		// Query query = getSession().createQuery(hql);
		// Integer count = ((Integer) query.uniqueResult()).intValue();
		return getCountByHQL(clasz, null);
	}

	//@Override
	public Integer getCountByHQLByDr(Class<?> clasz, String conditions) {
		String hql = "select count(id) from " + clasz.getName() + " as e";
		// 删除条件
		if (StringUtils.isNotEmpty(conditions)) {
			hql += " where " + DaoHelper.insertAlias(conditions, clasz);
		}
		Query query = getSession().createQuery(hql);
		// Integer count = ((Integer) query.uniqueResult()).intValue();
		return ((Long) query.iterate().next()).intValue();
	}

	//@Override
	public Integer getCountByHQL(Class<?> clasz, String conditions) {
		String hql = "select count(id) from " + clasz.getName() + " as e";
		// 删除条件
		if (StringUtils.isNotEmpty(conditions)) {
			hql += " where (" + DaoHelper.insertAlias(conditions, clasz)
					+ " and ifnull(e.dr,0)=0 )";
		} else {
			hql += " where ( ifnull(e.dr,0)=0 )";
		}
		Query query = getSession().createQuery(hql);
		// Integer count = ((Integer) query.uniqueResult()).intValue();
		return ((Long) query.iterate().next()).intValue();
	}

	// add by hezhh end

	// 更新

	public void update(Object entity) {
		try {

			if (tm.getClass().getSimpleName().equals("HibernateTransactionManager")) {
				getSession().update(entity);
			} else {
				getSession().merge(entity);
			}

		} catch (Exception e) {
			logger.error("getSession().merge(entity); in HibernateDao"
					+ entity.getClass().getSimpleName() + " ERROR!");
		}

	}

	static final Log logger = LogFactory.getLog(HibernateDAO.class);

	// 删除

	public void delete(Object entity) {
		getSession().delete(entity);
	}

	// 查询 HQL

	public <E> List<E> findByHQL(Class<E> clasz, String hql, Object... args) {
		Session session = getSession();
		// 查询
		Query listQuery = session.createQuery(hql);
		for (int i = 0; args != null && i < args.length; i++) {
			if (args[i] == null) {
				throw new TxException("查询参数有误！");
			}
			listQuery.setParameter(i, args[i]);
		}
		List list = listQuery.list();
		return list;
		// return new ArrayList<E>();
	}

	public <E> List<E> findByHQLWithPamaList(Class<E> clasz, String hql,
			List<Object> pamaList) {
		Session session = getSession();
		// 查询
		Query listQuery = session.createQuery(hql);
		for (int i = 0; pamaList != null && i < pamaList.size(); i++) {
			if (pamaList.get(i) == null) {
				throw new TxException("查询参数有误！");
			}
			listQuery.setParameter(i, pamaList.get(i));
		}
		List list = listQuery.list();
		return list;
	}

	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order, int start, int limit, String[] includes) {
		String hql = "from " + clasz.getName() + " as e";
		if (includes != null && includes.length > 0) {
			for (String je : includes) {
				hql += " left outer join  fetch e." + je + " as "
						+ (je.replaceAll("\\.", "")) + " ";
			}
		}
		// change by hezhh begin
		if (StringUtils.isNotEmpty(conditions)) {
			hql += " where (" + DaoHelper.insertAlias(conditions, clasz)
					+ " and ifnull(e.dr,0)=0 )";
		} else {
			hql += " where ( ifnull(e.dr,0)=0 )";
		}
		// change by hezhh end
		hql = hql.replaceAll(" id", " e.id");
		hql = hql.replaceAll("\\(id", "\\(e.id");
		if (StringUtils.isNotEmpty(order)) {
			if (!order.contains(",") && order.indexOf(".") == -1
					&& order.indexOf("LENGTH") == -1) {
				hql += " order by e." + order;
			} else {
				Field[] fields = clasz.getDeclaredFields();
				for (Field field : fields) {
					String name = field.getName();
					order = order.replaceAll(name, " e." + name);
				}
				hql += " order by " + order;
			}
		}

		Query q = createQuery(hql, args);
		// add by hezhh begin
		if (start < 0) {
			start = 0;
		}
		if (limit > 0) {
			q.setFirstResult(start);
			q.setMaxResults(limit);
		}
		/*
		 * if (limit > 0) { q.setFirstResult(0); q.setMaxResults(limit); }
		 */
		// add by hezhh end
		@SuppressWarnings("rawtypes")
		List l = q.list();

		return l;
		// return new ArrayList<E>();
	}

	/**
	 * 查询所有的，没有dr的限制条件 郭静
	 * 
	 * @param clasz
	 * @param conditions
	 * @param args
	 * @param order
	 * @param start
	 * @param limit
	 * @param includes
	 * @return
	 */
	public <E> List<E> findAllByDr(Class<E> clasz, String conditions,
			Object[] args, String order, int start, int limit, String[] includes) {
		String hql = "from " + clasz.getName() + " as e";
		if (includes != null && includes.length > 0) {
			for (String je : includes) {
				hql += " left outer join  fetch e." + je + " as "
						+ (je.replaceAll("\\.", "")) + " ";
			}
		}
		// change by hezhh begin
		if (StringUtils.isNotEmpty(conditions)) {
			hql += " where " + DaoHelper.insertAlias(conditions, clasz);
		}
		// change by hezhh end
		hql = hql.replaceAll(" id", " e.id");
		hql = hql.replaceAll("\\(id", "\\(e.id");
		if (StringUtils.isNotEmpty(order)) {
			if (!order.contains(",") && order.indexOf(".") == -1
					&& order.indexOf("LENGTH") == -1) {
				hql += " order by e." + order;
			} else {
				Field[] fields = clasz.getDeclaredFields();
				for (Field field : fields) {
					String name = field.getName();
					order = order.replaceAll(name, " e." + name);
				}
				hql += " order by " + order;
			}
		} else {
			hql += " order by e.id desc ";
		}

		Query q = createQuery(hql, args);
		// add by hezhh begin
		if (start < 0) {
			start = 0;
		}
		if (limit > 0) {
			q.setFirstResult(start);
			q.setMaxResults(limit);
		}
		/*
		 * if (limit > 0) { q.setFirstResult(0); q.setMaxResults(limit); }
		 */
		// add by hezhh end
		@SuppressWarnings("rawtypes")
		List l = q.list();

		return l;
		// return new ArrayList<E>();
	}

	/**
	 * add by wangjf 新增一个order by的升序、降序参数
	 * 
	 * @param clasz
	 * @param conditions
	 * @param args
	 * @param order
	 * @param ordType
	 *            asc 升序 desc 降序
	 * @param start
	 * @param limit
	 * @param includes
	 * @return
	 */
	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order, String ordType, int start, int limit,
			String[] includes) {
		String hql = "from " + clasz.getName() + " as e";
		if (includes != null && includes.length > 0) {
			for (String je : includes) {
				hql += " left outer join  fetch e." + je + " as "
						+ (je.replaceAll("\\.", "")) + " ";
			}
		}
		// change by hezhh begin
		if (StringUtils.isNotEmpty(conditions)) {
			hql += " where (" + DaoHelper.insertAlias(conditions, clasz)
					+ " and ifnull(e.dr,0)=0 )";
		} else {
			hql += " where ( ifnull(e.dr,0)=0 )";
		}
		// change by hezhh end
		hql = hql.replaceAll(" id", " e.id");
		hql = hql.replaceAll("\\(id", "\\(e.id");
		if (StringUtils.isNotEmpty(order)) {
			if (!order.contains(",") && order.indexOf(".") == -1
					&& order.indexOf("LENGTH") == -1) {
				hql += " order by e." + order + " " + ordType;
			} else {
				Field[] fields = clasz.getDeclaredFields();
				for (Field field : fields) {
					String name = field.getName();
					order = order.replaceAll(name, " e." + name);
				}
				hql += " order by " + order + " " + ordType;
			}
		}

		Query q = createQuery(hql, args);
		// add by hezhh begin
		if (start < 0) {
			start = 0;
		}
		if (limit > 0) {
			q.setFirstResult(start);
			q.setMaxResults(limit);
		}
		/*
		 * if (limit > 0) { q.setFirstResult(0); q.setMaxResults(limit); }
		 */
		// add by hezhh end
		@SuppressWarnings("rawtypes")
		List l = q.list();

		return l;
		// return new ArrayList<E>();
	}

	@SuppressWarnings("rawtypes")
	public Page findPageBySQL(Class entityClass, String eString,
			String countProjection, String sql, Object[] args, int start,
			int limit) {

		List list = createSQLQuery2(sql, entityClass, eString, args)
				.setFirstResult(0).setMaxResults(limit).list();
		Integer count = getCountBySQL(
				Qutils.createCountQueryFor(sql, countProjection), args);

		return new Page(start, limit, Integer.parseInt(String.valueOf(count)),
				list);
	}

	public int executeSQL(String sql, Object... args) {
		int i = createSQLQuery(sql, args).executeUpdate();
		return i;
	}

	public boolean isExist(Class<?> entityClass, String whereClause,
			Object... values) {
		String selectHQL = "select count(id) from " + entityClass.getName()
				+ "  where " + whereClause + "";
		return findLong(selectHQL, values) > 0;
	}

	public void saveOrUpdate(Object entity) throws Exception {
		if (ReflectionUtils
				.getFieldValue(entity, "id") == null) {
			save(entity);
		} else {
			update(entity);
		}
	}

	public void deleteAll(Collection<?> entities) {
		for (Object entity : entities) {
			getSession().delete(entity);
		}
	}

	public void delete(Class<?> clasz, Object id) {
		String findHql = "from " + clasz.getName() + " where id=?";
		List<?> result = findByHQL(clasz, findHql, id);
		if (result != null && result.size() > 0) {
			deleteAll(result);
		}
	}

	public void delete(Class<?> clasz, Object[] ids) {
		String findHql = "from " + clasz.getName() + " where id in ("
				+ DaoHelper.getDeleteIds(ids) + ")";
		List<?> result = findByHQL(clasz, findHql);
		if (result != null && result.size() > 0) {
			deleteAll(result);
		}
	}

	// add by hezhh begin
	@SuppressWarnings("rawtypes")
	public void deleteByDr(Object entity) {
		try {
			Class clasz = entity.getClass();
			Method getId = clasz.getMethod("getId");
			Object id = getId.invoke(entity);
			deleteByDr(clasz, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void deleteAllByDr(Collection<?> entities) {
		try {
			if (entities.size() > 0) {
				List<Integer> ids = new ArrayList<Integer>();
				Class clasz = null;
				for (Object entity : entities) {
					clasz = entity.getClass();
					Method getId = clasz.getMethod("getId");
					ids.add((Integer) getId.invoke(entity));
				}
				if (clasz != null) {
					deleteByDr(clasz, ids.toArray(new Integer[0]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteByDr(Class<?> clasz, Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				Integer id = ids[i];
				String updatehql = " update " + clasz.getName()
						+ " set dr = 1 , ts = '"
						+ DateUtils.formatDateTime(new Date()).toString()
						+ "' where id = ?";
				execute(updatehql, id);
			}
		}
	}

	public void deleteByDrAry(Class<?> clasz, Integer[] ids) {
		String updatehql = " update "
				+ clasz.getName()
				+ " set dr = 1 , ts = '"
				+ DateUtils.formatDateTime(new Date()).toString()
				+ "' where "
				+ SQLUtils.buildSqlForIn("id",
						TypeExchangeUtils.IntAryToStrAry(ids));
		execute(updatehql);
	}

	// add by hezhh begin

	public void delete(Class<?> clasz, String property, Object[] values) {
		execute("delete " + clasz.getName() + " where " + property + " in ("
				+ DaoHelper.getDeleteIds(values) + ")");
	}

	// 基本查找

	public <E> E getByProperty(Class<E> clasz, String name, Object value,
			String... includes) {
		if (StringUtils.isEmpty(name) || value == null) {
			throw new JException("查询参数不能为空！");
		}
		return findFirst(clasz, name + "=?", new Object[] { value }, includes);
	}

	public <E> E getById(Class<E> clasz, Serializable id, String... includes) {
		if (id == null) {
			throw new JException("查询参数（ID）不能为空！");
		}
		return findFirst(clasz, "e.id=?", new Object[] { id }, includes);
	}

	// Hibernate查找一个

	public <E> E findFirst(Class<E> clasz) {
		return findFirst(clasz, null, null, null, 0, 1, null);
	}

	public <E> E findFirst(Class<E> clasz, String[] includes) {
		return findFirst(clasz, null, null, null, 0, 1, includes);
	}

	public <E> E findFirst(Class<E> clasz, String conditions) {
		return findFirst(clasz, conditions, null, null, 0, 1, null);
	}

	public <E> E findFirstByDr(Class<E> clasz, String conditions) {
		return findFirstByDr(clasz, conditions, null, null, 0, 1, null);
	}

	public <E> E findFirst(Class<E> clasz, String conditions, String[] includes) {
		return findFirst(clasz, conditions, null, null, 0, 1, includes);
	}

	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args) {
		return findFirst(clasz, conditions, args, null, 0, 1, null);
	}

	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args,
			String[] includes) {
		return findFirst(clasz, conditions, args, null, 0, 1, includes);
	}

	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args,
			String order) {
		return findFirst(clasz, conditions, args, order, 0, 1, null);
	}

	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args,
			String order, String[] includes) {
		return findFirst(clasz, conditions, args, order, 0, 1, includes);
	}

	public <E> E findFirst(Class<E> clasz, String conditions, Object[] args,
			String order, int start, int limit, String[] includes) {
		List<E> results = findAll(clasz, conditions, args, order, start, limit,
				includes);
		return results.size() == 0 ? null : results.get(0);
	}

	public <E> E findFirstByDr(Class<E> clasz, String conditions,
			Object[] args, String order, int start, int limit, String[] includes) {
		List<E> results = findAllByDr(clasz, conditions, args, order, start,
				limit, includes);
		return results.size() == 0 ? null : results.get(0);
	}

	// Hibernate查找全部

	public <E> List<E> findAll(Class<E> clasz) {
		return findAll(clasz, null, null, null, 0, 0, null);
	}

	public <E> List<E> findAll(Class<E> clasz, String[] includes) {
		return findAll(clasz, null, null, null, 0, 0, includes);
	}

	public <E> List<E> findAll(Class<E> clasz, String conditions) {
		return findAll(clasz, conditions, null, null, 0, 0, null);
	}

	public <E> List<E> findAll(Class<E> clasz, String conditions, String order,
			String orderType) {
		return findAll(clasz, conditions, null, order, orderType, 0, 0, null);
	}

	public <E> List<E> findAllByDr(Class<E> clasz, String conditions) {
		return findAllByDr(clasz, conditions, null, null, 0, 0, null);
	}

	public <E> List<E> findAll(Class<E> clasz, String conditions,
			String[] includes) {
		return findAll(clasz, conditions, null, null, 0, 0, includes);
	}

	public <E> List<E> findAll(Class<E> clasz, String conditions, Object[] args) {
		return findAll(clasz, conditions, args, null, 0, 0, null);
	}

	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String[] includes) {
		return findAll(clasz, conditions, args, null, 0, 0, includes);
	}

	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order) {
		return findAll(clasz, conditions, args, order, 0, 0, null);
	}

	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order, String[] includes) {
		return findAll(clasz, conditions, args, order, 0, 0, includes);
	}

	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order, int limit) {
		return findAll(clasz, conditions, args, order, limit, 0, null);
	}

	public <E> List<E> findAll(Class<E> clasz, String conditions,
			Object[] args, String order, int limit, String[] includes) {
		return findAll(clasz, conditions, args, order, 0, limit, includes);
	}

	public <E> E findFirstByHQL(Class<E> clasz, String hql, Object... args) {
		List<E> list = findByHQL(clasz, hql, args);
		return (list != null && list.size() > 0 ? list.get(0) : null);
	}

	public <E> List<E> findByHQL(Class<E> clasz, String hql, Object[] args,
			int limit) {

		return findPageByHQL(clasz, hql, args, 0, limit).getItems();
	}

	// 标量

	public Integer findInt(String hql, Object... args) {
		Integer intValue = findFirstByHQL(Integer.class, hql, args);
		return intValue == null ? 0 : intValue;
	}

	public Long findLong(String hql, Object... args) {
		Long longValue = findFirstByHQL(Long.class, hql, args);
		return longValue == null ? 0L : longValue;
	}

	public Double findDouble(String hql, Object... args) {
		Double doubleValue = findFirstByHQL(Double.class, hql, args);
		return doubleValue == null ? 0.00 : doubleValue;
	}

	public BigDecimal findBigDecimal(String hql, Object... args) {
		BigDecimal bigDecimalValue = findFirstByHQL(BigDecimal.class, hql, args);
		return bigDecimalValue == null ? new BigDecimal(0) : bigDecimalValue;
	}

	public String findString(String hql, Object... args) {
		String stringValue = findFirstByHQL(String.class, hql, args);
		return stringValue;
	}

	// 分页查询

	/*public Page findPage(final DetachedCriteria dc, final int start,
			final int limit) {
		Session session = getSession();
		Criteria c = dc.getExecutableCriteria(session);
		CriteriaImpl impl = (CriteriaImpl) c;
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = (List<CriteriaImpl.OrderEntry>) ReflectionUtils
				.getFieldValue(impl, "orderEntries");
		ReflectionUtils.setFieldValue(impl, "orderEntries",
				new ArrayList<CriteriaImpl.OrderEntry>());

		// 执行Count查询
		c.setResultTransformer(CriteriaImpl.DISTINCT_ROOT_ENTITY);
		Long total = (Long) c.setProjection(Projections.countDistinct("id"))
				.uniqueResult();

		total = total == null ? 0 : total;
		// 将之前的Projection和OrderBy条件重新设回去
		c.setProjection(projection);
		c.setResultTransformer(transformer);
		ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		// 如果start大于total
		int startInt = start;
		if (start >= total) {
			// 开始位置退到上一页
			startInt = Integer.parseInt(String.valueOf(total)) - limit;
			// 开始为负数时，直接置为0
			if (startInt < 0) {
				startInt = 0;
			}
		}

		c.setFirstResult(startInt);
		c.setMaxResults(limit);

		List<?> list = c.list();

		return new Page(startInt, limit,
				Integer.parseInt(String.valueOf(total)),
				(list == null ? new ArrayList() : list));
	}*/

	public Page findPageByHQL(Class<?> clasz, String hql, Object[] args,
			int start, int limit) {

		Session session = getSession();

		// 计算数量
		String countQueryString = "select count (*) "
				+ DaoHelper.removeSelect(DaoHelper.removeOrders(hql));
		Query countQuery = session.createQuery(countQueryString);
		for (int i = 0; args != null && i < args.length; i++) {
			countQuery.setParameter(i, args[i]);
		}
		List count_list = countQuery.list();
		long count = (count_list != null && count_list.size() > 0) ? Long
				.parseLong((String.valueOf(count_list.get(0)))) : 0L;

		// 如果start大于total
		if (start >= count) {
			// 开始位置退到上一页
			start = Integer.parseInt(String.valueOf(count)) - limit;
			// 开始为负数时，直接置为0
			if (start < 0) {
				start = 0;
			}
		}

		// 查询
		Query listQuery = session.createQuery(hql);
		for (int i = 0; args != null && i < args.length; i++) {
			listQuery.setParameter(i, args[i]);
		}
		List<?> list = listQuery.setFirstResult(start).setMaxResults(limit)
				.list();

		return new Page(start, limit, Integer.parseInt(String.valueOf(count)),
				list);
	}

	@SuppressWarnings("rawtypes")
	public Page findPageBySQL(Class entityClass, String eString, String sql,
			Object[] args, int start, int limit) {

		List list = createSQLQuery2(sql, entityClass, eString, args)
				.setFirstResult(0).setMaxResults(limit).list();
		Integer count = getCountBySQL(Qutils.createCountQueryFor(sql), args);

		return new Page(start, limit, Integer.parseInt(String.valueOf(count)),
				list);
	}

	private SQLQuery createSQLQuery2(final String queryString, Class<?> entity,
			String eString, final Object... args) {
		SQLQuery query = getSession().createSQLQuery(queryString).addEntity(
				eString, entity);
		for (int i = 0; args != null && i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query;
	}

	public Page findPageBySQL(String sql, Object[] args, int start, int limit) {

		Session session = getSession();
		// 计算数量
		String countQueryString = "select count(*) "
				+ DaoHelper.removeSelect(DaoHelper.removeOrders(sql));

		Query countQuery = session.createSQLQuery(countQueryString);
		for (int i = 0; args != null && i < args.length; i++) {
			countQuery.setParameter(i, args[i]);
		}
		List count_list = countQuery.list();
		long count = (count_list != null && count_list.size() > 0) ? Long
				.parseLong((String.valueOf(count_list.get(0)))) : 0L;

		// 如果start大于total
		if (start >= count) {
			// 开始位置退到上一页
			start = Integer.parseInt(String.valueOf(count)) - limit;
			// 开始为负数时，直接置为0
			if (start < 0) {
				start = 0;
			}
		}

		// 查询
		Query listQuery = session.createSQLQuery(sql);
		for (int i = 0; args != null && i < args.length; i++) {
			listQuery.setParameter(i, args[i]);
		}
		List<?> list = listQuery.setFirstResult(start).setMaxResults(limit)
				.list();

		return new Page(start, limit, Integer.parseInt(String.valueOf(count)),
				list);
	}

	public List<?> findListBySQL(String sql, Object[] args, int start, int limit) {
		List<?> list = createSQLQuery(sql, args).setFirstResult(start)
				.setMaxResults(limit).list();
		return list;
	}

	public Integer getCountBySQL(String count_sql, Object... args) {

		Object count = createSQLQuery(count_sql, args).uniqueResult();
		return count == null ? 0 : Integer.parseInt(String.valueOf(count));
	}

	public Object getFirstBySQL(String sql, Object... args) {
		List<?> list = createSQLQuery(sql, args).list();
		return list.size() > 0 ? list.get(0) : null;
	}

	public <E> List<E> findByCriteria(DetachedCriteria dc) {
		Criteria c = dc.getExecutableCriteria(getSession());
		return c.list();
	}

	public Page findPageBySQL(String sql, String count_sql, Object[] args,
			int start, int limit) {

		List list = findListBySQL(sql, args, start, limit);
		Integer count = getCountBySQL(count_sql, args);

		return new Page(start, limit, Integer.parseInt(String.valueOf(count)),
				list);
	}

	public int findIntBySQL(String sql, Object... args) {
		Object i = getFirstBySQL(sql, args);
		return i == null ? 0 : Integer.parseInt(String.valueOf(i));
	}

	public void saveOrUpdateAll(List<?> entities) throws Exception {
		for (Object entity : entities) {
			saveOrUpdate(entity);
		}
	}

	public Object getMax(Class<?> entityClass, String propertyName,
			String scope, Object value) {
		String selectHql = "select max(a." + propertyName + ") from "
				+ entityClass.getName() + " a ";
		if (value != null && value instanceof Long
				&& ((Long) value).longValue() == 0L) {
			value = null;
		}
		if (!StringUtils.isEmpty(scope)) {
			if (value != null || String.valueOf(value).equals("0")) {
				selectHql += " where " + scope + " = ?";
				Object result = findFirstByHQL(Object.class, selectHql,
						new Object[] { value });
				return result == null ? 0 : result;
			}

			else {
				selectHql += " where " + scope + " is NULL";
				Object result = findFirstByHQL(Object.class, selectHql);
				return result == null ? 0 : result;
			}
		}
		Object result = findFirstByHQL(Object.class, selectHql,
				new Object[] { value });

		return result == null ? 0 : result;
	}

	public Object getMin(Class<?> entityClass, String propertyName,
			String scope, Object value) {
		String selectHql = "select min(a." + propertyName + ") from "
				+ entityClass.getName() + " a ";

		if (!StringUtils.isEmpty(scope)) {
			selectHql += " where " + scope + " = ?";
		}
		Object result = findFirstByHQL(Object.class, selectHql,
				new Object[] { value });

		return result == null ? 0 : result;
	}

	public void down(Class<?> mappingClass, Serializable id, String groupBy) {
		orderNumUpOrDown(mappingClass, id, groupBy, true);
	}

	public void up(Class<?> mappingClass, Serializable id, String groupBy) {
		orderNumUpOrDown(mappingClass, id, groupBy, false);
	}

	public void orderNumUpOrDown(Class<?> mappingClass, Serializable id,
			String groupBy, boolean isDown) {

		String orderBy = "position";
		Object curObject = getById(mappingClass, id);
		Integer orderNum = (Integer) ReflectionUtils.getFieldValue(curObject,
				"position");
		if (orderNum == null)
			orderNum = 0;
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(mappingClass);
		Object groupValue = null;

		if (!StringUtils.isBlank(groupBy)) {
			String[] props = groupBy.split("\\.");
			if (props.length == 2) {
				curObject = getById(mappingClass, id, props[0]);
				Object groupObject = ReflectionUtils.getFieldValue(curObject,
						props[0]);
				if (groupObject != null) {
					groupValue = ReflectionUtils.getFieldValue(groupObject,
							props[1]);
				}
			} else {
				groupValue = ReflectionUtils.getFieldValue(curObject, groupBy);
			}
			if (groupValue != null) {
				detachedCriteria.add(Restrictions.eq(groupBy, groupValue));
			}
		}
		if (isDown) {
			detachedCriteria.add(Restrictions.ge(orderBy, orderNum));
			detachedCriteria.addOrder(Order.asc(orderBy));
		} else {
			detachedCriteria.add(Restrictions.le(orderBy, orderNum));
			detachedCriteria.addOrder(Order.desc(orderBy));
		}

		List<?> objlist = findByCriteria(detachedCriteria);
		if (objlist.size() > 1) {
			Object v0 = ReflectionUtils.getFieldValue(objlist.get(0), orderBy);
			Object v1 = ReflectionUtils.getFieldValue(objlist.get(1), orderBy);
			Object v0_id = ReflectionUtils.getFieldValue(objlist.get(0), "id");
			Object v1_id = ReflectionUtils.getFieldValue(objlist.get(1), "id");

			String hql = "update " + mappingClass.getName() + " set " + orderBy
					+ "=" + v1 + " where id=?";
			execute(hql, v0_id);
			hql = "update " + mappingClass.getName() + " set " + orderBy + "="
					+ v0 + " where id=?";

			execute(hql, v1_id);
		}
	}

	AbstractPlatformTransactionManager tm;

	public AbstractPlatformTransactionManager getTm() {
		return tm;
	}

	public void setTm(AbstractPlatformTransactionManager tm) {
		this.tm = tm;
	}

	boolean isSimpleServer = true;
	private Session getSession() {
		// if(tm.getClass().getSimpleName().equals("HibernateTransactionManager")){
		if (isSimpleServer) {
			return sessionFactory.getCurrentSession();
			
		} else {
			return sessionFactory.openSession();
		}
	}

	private Query createQuery(final String queryString, final Object... args) {
		Query query = getSession().createQuery(queryString);
		for (int i = 0; args != null && i < args.length; i++) {

			query.setParameter(i, args[i]);
		}

		return query;
	}

	public SQLQuery createSQLQuery(final String queryString,
			final Object... args) {
		SQLQuery query = getSession().createSQLQuery(queryString);
		for (int i = 0; args != null && i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private SimpleJdbcCall JdbcCall;

	public Map<String, Object> callProcedureQueryOut(String procedureName,
			Map<String, Object> inParameters) {

		JdbcCall = JdbcCall.withProcedureName(procedureName);
		return JdbcCall.execute(inParameters);
	}

	public List callProc(String call) {
		Query q = createSQLQuery(call);
		List l = q.list();
		return l;
	}

	//@Override
	public <E> E findUniqueByHQL(Class<E> clasz, String hql, Object... args) {
		List<E> list = this.findByHQL(clasz, hql, args);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xinleju.erp.sa.dao.IHibernateDAO#fidId(java.lang.Class,
	 * java.lang.Integer)
	 */
	//@Override
	public Object fidId(Class entityClass, Integer id) {
		Object o = getSession().get(entityClass, id);
		return o;
	}

	public IUniqueRule getUniquerule() {
		if (uniquerule == null)
			return new UniqueRule();
		return uniquerule;
	}

	public void setUniquerule(IUniqueRule uniquerule) {
		this.uniquerule = uniquerule;
	}

	public ICheckRule getCheckrule() {
		if (checkrule == null)
			return new CheckRule();
		return checkrule;
	}

	//@Override
	public void setCheckrule(ICheckRule checkrule) {
		this.checkrule = checkrule;
	}

	/**
	 * 根据值list和预备sql 更新数据库
	 */
	//@Override
	public void updateDateWithHql(String sql, List<Object> valueList) {
		Session session = getSession();
		Query listQuery = session.createQuery(sql);
		for (int i = 0; i < valueList.size(); i++) {
			listQuery.setParameter(i, valueList.get(i));
		}
		listQuery.executeUpdate();
	}

	/**
	 * 
	 * @param clasz
	 * @param conditions
	 * @param args
	 * @param order
	 * @param start
	 * @param limit
	 * @param includes
	 * @return 可满足嵌套子sql查询
	 */
	public <E> List<E> findALLByCond(Class<E> clasz, String conditions,
			Object[] args, String order) {
		String hql = "from " + clasz.getName();
		// change by hezhh begin
		if (StringUtils.isNotEmpty(conditions)) {
			hql += " where (" + conditions + " and ifnull(dr,0)=0 )";
		} else {
			hql += " where ( ifnull(dr,0)=0 )";
		}
		// change by hezhh end
		// hql = hql.replaceAll(" id", " e.id");
		// hql = hql.replaceAll("\\(id", "\\(e.id");
		if (StringUtils.isNotEmpty(order)) {
			if (!order.contains(",") && order.indexOf(".") == -1
					&& order.indexOf("LENGTH") == -1) {
				hql += " order by " + order;
			} else {
				Field[] fields = clasz.getDeclaredFields();
				for (Field field : fields) {
					String name = field.getName();
					order = order.replaceAll(name, name);
				}
				hql += " order by " + order;
			}
		}

		Query q = createQuery(hql, args);

		// add by hezhh end
		@SuppressWarnings("rawtypes")
		List l = q.list();

		return l;
		// return new ArrayList<E>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xinleju.erp.sa.dao.IHibernateDAO#findALLByCond(java.lang.Class,
	 * java.lang.String)
	 */
	//@Override
	public <E> List<E> findALLByCond(Class<E> clasz, String conditions) {
		// TODO Auto-generated method stub
		return findALLByCond(clasz, conditions, null, null);
	}

	/**
	 * @Description: 根据sql和条件查询；--刘忠庆
	 * @param @param sql
	 * @param @param valueList
	 * @param @return
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findBySqlAndConditions(String sql, List<Object> valueList) {

		Query query = getSession().createQuery(sql);
		if (valueList != null && valueList.size() > 0) {
			for (int i = 0; i < valueList.size(); i++) {
				query.setParameter(i, valueList.get(i));
			}
		}
		List list = query.list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List<?> findBySqlAndConditions(String sql, List<Object> valueList,
			Class clazz) {

		Query query = getSession().createSQLQuery(sql).addEntity(clazz);
		if (valueList != null && valueList.size() > 0) {
			for (int i = 0; i < valueList.size(); i++) {
				query.setParameter(i, valueList.get(i));
			}
		}
		List<?> list = query.list();
		return list;
	}

	/**
	 * 多子表查询用 -- 刘忠庆
	 */
	public List findBySql(String sql, List<Object> valueList) {

		Query query = getSession().createSQLQuery(sql);
		if (valueList != null && valueList.size() > 0) {
			for (int i = 0; i < valueList.size(); i++) {
				query.setParameter(i, valueList.get(i));
			}
		}
		List list = query.list();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xinleju.erp.sa.dao.IHibernateDAO#deleteByDr(java.lang.Class,
	 * java.lang.Object)
	 */
	//@Override
	public void deleteByDr(Class<?> clasz, Object id) {
		String updatehql = " update " + clasz.getName()
				+ " set dr = 1 , ts = '"
				+ DateUtils.formatDateTime(new Date()).toString()
				+ "' where id = ?";
		execute(updatehql, id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xinleju.erp.sa.dao.IHibernateDAO#deleteByDr(java.lang.Class,
	 * java.lang.Object)
	 */
	//@Override
	public void deleteByCondition(Class<?> clasz, Object condition) throws Exception {
		if(condition != null){
			StringBuffer buffer = new StringBuffer();
			buffer.append(" update ");
			buffer.append(clasz.getName());
			buffer.append(" set dr = 1 , ts = '");
			buffer.append(DateUtils.formatDateTime(new Date()).toString());
			buffer.append("' where ifnull(dr,0)=0 ");
			buffer.append(condition.toString());
			
			execute(buffer.toString());
		}else{
			throw new Exception("查询参数不能为空！");
		}
	}
	
	public void deleteAllByCondition(Class<?> clasz, Object condition) throws Exception {
		if(condition != null){
			StringBuffer buffer = new StringBuffer();
			buffer.append(" delete from ");
			buffer.append(clasz.getName());
			buffer.append(" where ");
			buffer.append(condition.toString());
			execute(buffer.toString());
		}else{
			throw new Exception("查询参数不能为空！");
		}
	}

	//@Override
	public Double findDoublebySQL(String sql, Object... args) {
		Double doubleValue = findFirstBySQL(Double.class, sql, args);
		return doubleValue == null ? 0.00 : doubleValue;
	}

	/**
	 * @Description: 根据传入sql查询数据
	 * @param @param class1
	 * @param @param sql
	 * @param @param args
	 * @param @return
	 * @return Double
	 */
	private <E> E findFirstBySQL(Class<E> clasz, String sql, Object[] args) {
		List<E> list = findBySqlArgs(clasz, sql, args);
		return (list != null && list.size() > 0 ? list.get(0) : null);
	}

	// 查询 HQL

	public <E> List<E> findBySqlArgs(Class<E> clasz, String hql, Object... args) {
		Session session = getSession();
		// 查询
		Query listQuery = session.createSQLQuery(hql);
		for (int i = 0; args != null && i < args.length; i++) {
			if (args[i] == null) {
				throw new TxException("查询参数有误！");
			}
			listQuery.setParameter(i, args[i]);
		}
		List list = listQuery.list();
		return list;
		// return new ArrayList<E>();
	}

	@Transactional
	//@Override
	public void deleteAllEntiesByDr(Collection<?> entities) throws Exception {
		try {
			if (entities.size() > 0) {
				Class clasz = null;
				for (Object entity : entities) {
					if (entity != null) {
						clasz = entity.getClass();
						Method getId = clasz.getMethod("getId");
						String updatehql = " update "
								+ clasz.getName()
								+ " set dr = 1 , ts = '"
								+ DateUtils.formatDateTime(new Date())
										.toString() + "' where id = ?";
						execute(updatehql, (Integer) getId.invoke(entity));
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}

	}

	public Page findPage(DetachedCriteria dc, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}


}
