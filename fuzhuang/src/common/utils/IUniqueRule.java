/**   
*
* @version V1.0   
*/
package utils;

import java.lang.reflect.InvocationTargetException;

import dao.HibernateDAO;


/**
 * @author Administrator
 *唯一性校验
 */
public interface IUniqueRule {
	/**
	 * 返回唯一字段组合。<br>
	 *
	 * @return java.lang.String[] 唯一字段组合。<br>
	 */
	public java.lang.String[] getFields();
	/**
	 * 返回提示信息。<br>
	 *
	 * @return java.lang.String 提示信息。<br>
	 */
	String getHint();
	String getErrMsg();
	public void setFieldnames(String[] fieldnames) ;
	public boolean check(Object entity,HibernateDAO dao)throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	public void setWherePart(String wherePart);
}
