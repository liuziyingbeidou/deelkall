/**   
*
* @version V1.0   
*/
package utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dao.HibernateDAO;


/**
 * @author Administrator
 *
 */
public class CheckRule implements ICheckRule{

	 String[] displayname=null;
	 String[] fieldname=null; 
	 private String errmsg;
	 
	/**
	 * 
	 */
	public CheckRule() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CheckRule( String[] displayname, String[] fieldname) {
	this.displayname=displayname;
	this.fieldname=fieldname;
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see com.xinleju.erp.sa.utils.ICheckRule#canNull()
	 */
	//@Override
	public boolean canNull() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.xinleju.erp.sa.utils.ICheckRule#getDisplayName()
	 */
	//@Override
	public String[] getDisplayName() {
		// TODO Auto-generated method stub
		return displayname;
	}

	/* (non-Javadoc)
	 * @see com.xinleju.erp.sa.utils.ICheckRule#getFieldName()
	 */
	//@Override
	public String[] getFieldName() {
		// TODO Auto-generated method stub
		return fieldname;
	}
	public void setDisplayname(String[] displayname) {
		this.displayname = displayname;
	}
	public void setFieldname(String[] fieldname) {
		this.fieldname = fieldname;
	}
	public boolean check(Object entity,HibernateDAO dao)throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{

//		m_strFields
		String errmsg="";
		if(fieldname==null)return true;
		
		for(int i=0;i<fieldname.length;i++){
			String fields=fieldname[i];
			Class classtype=entity.getClass();
			String getmethoedname="get"+fields.substring(0, 1).toUpperCase()+fields.substring(1);
			Object obj = null;
			try {
				Method setMethod = classtype.getMethod(getmethoedname);
				obj=setMethod.invoke(entity);
			} catch (Exception e) {
				//xuelin 如果没有此方法就代表不需要校验
				continue;
			}
			 if(obj==null || obj.equals("")){
			    	errmsg+=displayname[i]+",";
			    }
			
		}
		if(errmsg!=null && errmsg.length()>0){
			errmsg+="不能为空";
			this.setErrmsg(errmsg);
		}else{
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.xinleju.erp.sa.utils.ICheckRule#getErrMsg()
	 */
	//@Override
	public String getErrMsg() {
		// TODO Auto-generated method stub
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	};
	

}
