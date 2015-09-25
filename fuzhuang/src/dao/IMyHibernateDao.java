package dao;

import java.util.List;

/**
 * 
* @Title: 服装系统
* @ClassName: IMyHibernateDao 
* @Description: DAO接口
* @author liuzy
* @date 2015-6-16 上午10:37:12
 */
public interface IMyHibernateDao  {
	
	public void insertBean(Object entity);
	
	public void deleteBean(Object entity);
	
	public void updateBean(Object entity);

	public <E> List<E> selectBeanList(Class<E> clasz,String where);
	
	public <E> E selectBean(Class<E> clasz,String where);
	
	public <E> List<E> selectBeanList(Class<E> clasz,final int start, final int limit,final String where);
	
	public Integer selectBeanCount(Class<?> clasz,final String where);
	
	
}
