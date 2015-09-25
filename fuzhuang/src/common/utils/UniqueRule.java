/**   
*
* @version V1.0   
*/
package utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import dao.HibernateDAO;
import dao.IHibernateDAO;


/**
 * @author Administrator
 *
 */
public class UniqueRule implements IUniqueRule{
	
	@Autowired @Qualifier("hibernateDAO") IHibernateDAO dao;
   
	public UniqueRule(){
		
	}
	public UniqueRule(String m_strHint,String[] fieldnames,String[] m_strFields){
		this.m_strHint=m_strHint;
		this.m_strFields=m_strFields;
		this.fieldnames=fieldnames;
	}
	
	public IHibernateDAO getDao() {
		return dao;
	}

	public void setDao(IHibernateDAO dao) {
		this.dao = dao;
	}
	//提示信息。
	private String m_strHint = null;
	//唯一字段组合
	private String[] m_strFields = null;
	
    private String errmsg;
    
    private String[] fieldnames=null;
    //增加查询条件
    private String wherePart=null;
	/* (non-Javadoc)
	 * @see com.xinleju.erp.sa.utils.IUniqueRule#getFields()
	 */
	//@Override
	public String[] getFields() {
		// TODO Auto-generated method stub
		return m_strFields;
	}

	/* (non-Javadoc)
	 * @see com.xinleju.erp.sa.utils.IUniqueRule#getHint()
	 */
	//@Override
	public String getHint() {
		// TODO Auto-generated method stub
		return m_strHint;
	}

	public void setM_strHint(String m_strHint) {
		this.m_strHint = m_strHint;
	}

	public void setM_strFields(String[] m_strFields) {
		this.m_strFields = m_strFields;
	}
	

	public String[] getFieldnames() {
		return fieldnames;
	}
	public void setFieldnames(String[] fieldnames) {
		this.fieldnames = fieldnames;
	}
	
	
	public String getWherePart() {
		return wherePart;
	}
	public void setWherePart(String wherePart) {
		this.wherePart = wherePart;
	}
	/* (non-Javadoc)
	 * @see com.xinleju.erp.sa.utils.IUniqueRule#check()
	 */
	//@Override
	public boolean check(Object entity,HibernateDAO dao) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
//		m_strFields
		String errmsg="";
		if(m_strFields==null)return true;
		for(int i=0;i<m_strFields.length;i++){
			String fields=m_strFields[i];
			Class entclass=entity.getClass();
			String getmethoedname="get"+fields.substring(0, 1).toUpperCase()+fields.substring(1);
			Object obj = null;
			try {
				Method setMethod = entclass.getMethod(getmethoedname);
				obj=setMethod.invoke(entity);
			} catch (Exception e) {
				//xuelin 如果没有此方法就代表不需要校验
				continue;
			}
			
			String condition=fields+"='"+obj+"'";
			if(wherePart!=null && wherePart.length()>0){
				condition+=" and "+wherePart;
			}
		    List valuelist= dao.findAll(entity.getClass(), condition);
		    if(valuelist!=null && valuelist.size()>0){
		    	errmsg+=fieldnames[i]+",";
		    }
		}
		if(errmsg!=null && errmsg.length()>0){
			errmsg+="不唯一";
			this.setErrmsg(errmsg);
		}else{
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	/* (non-Javadoc)
	 * @see com.xinleju.erp.sa.utils.IUniqueRule#getErrMsg()
	 */
	//@Override
	public String getErrMsg() {
		// TODO Auto-generated method stub
		return errmsg;
	}
	/* (non-Javadoc)
	 * @see com.xinleju.erp.sa.utils.IUniqueRule#setWherePart()
	 */

}
