package utils;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import util.StringHelper;

public class DaoHelper {
	
	public static String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().lastIndexOf("from");
		return hql.substring(beginPos);
	}

	public static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	public static String insertAlias(String hql, Class<?> clasz) {
		hql = " " + hql;
		Field[] fields = clasz.getDeclaredFields();
		
		
		
		
		for (Field field : fields) {
			String name = field.getName();
			hql = hql.replaceAll(" " + name, " e." + name);
			hql = hql.replaceAll("\\(" + name, "\\(e." + name);
		}
		
		
		
		Class superClass = clasz.getSuperclass();
		if(superClass != null  &&  !superClass.getName().equals(Object.class.getName())) {
			Field[] fields1 = superClass.getDeclaredFields();
			for (Field field : fields1) {
				String name = field.getName();
				hql = hql.replaceAll(" " + name, " e." + name);
				hql = hql.replaceAll("\\(" + name, "\\(e." + name);
			}
		}
		
		
		hql = hql.substring(1);
		return hql;
	}
	public static String insertAlias(String hql, Class<?> clasz,String alias) {
		hql = " " + hql;
		Field[] fields = clasz.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			hql = hql.replaceAll(" " + name, " "+alias+"." + name);
			hql = hql.replaceAll("\\(" + name, "\\("+alias+"." + name);
		}
		
		hql = hql.substring(1);
		return hql;
	}
	public static String insertJoin(String... includes) {
		String hql = "";
		for (String include : includes) {
			hql += " left outer join  fetch e." + include + " as " + include
					+ " ";
		}
		return hql;
	}
	
	public static <E> RowMapper<E> resultBeanMapper(Class<E> clazz) {
		return ParameterizedBeanPropertyRowMapper.newInstance(clazz);
	}
	
	public static String getDeleteIds(Object... ids) {
		for(Object id : ids){
			if(id instanceof String)
				return StringHelper.join(ids, ",","'");
			else
				return StringHelper.join(ids, ",");
		}
		return null;
	}
}
