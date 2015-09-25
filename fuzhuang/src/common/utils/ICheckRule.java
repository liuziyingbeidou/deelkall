/**   
*
* @version V1.0   
*/
package utils;

import java.lang.reflect.InvocationTargetException;

import dao.HibernateDAO;


/**
 * @author Administrator
 *  必输项检查
 */
public interface ICheckRule {

	/**
	 * 返回字段是否可空。<br>
	 *
	 * @return boolean 可空返回true，否则返回false，缺省为false。<br>
	 */
	boolean canNull();
	/**
	 * 返回字段显示名称。<br>
	 *
	 * @return java.lang.String<br>
	 */
	String[] getDisplayName();
	/**
	 * 返回字段名称。<br>
	 *
	 * @return java.lang.String<br>
	 */
	String[] getFieldName();
	
	String getErrMsg();
	
	public boolean check(Object entity,HibernateDAO dao)throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;


}
