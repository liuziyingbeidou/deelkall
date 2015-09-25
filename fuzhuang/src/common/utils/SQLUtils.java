/**   
*
* @version V1.0   
*/
package utils;

/**
 * @author hezhh
 *
 */
public class SQLUtils {
	
	private static final int SQL_IN_LIST_LIMIT = 200;
	/**
	 * 构造In子句
	 */
	public static String buildSqlForIn(final String fieldname,
			final String[] fieldvalue) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("(" + fieldname + " IN ( ");
		int len = fieldvalue.length;
		// 循环写入条件
		for (int i = 0; i < len; i++) {
			if (fieldvalue[i] != null && fieldvalue[i].trim().length() > 0) {
				sbSQL.append("'").append(fieldvalue[i].toString()).append("'");
				// 单独处理 每个取值后面的",", 对于最后一个取值后面不能添加"," 并且兼容 oracle 的 IN 254 限制。每
				// 200 个 数据 or 一次。时也不能添加","
				if (i != (fieldvalue.length - 1)
						&& !(i > 0 && (i + 1) % SQL_IN_LIST_LIMIT == 0)) {
					sbSQL.append(",");
				}
			} else {
				return null;
			}

			// 兼容 oracle 的 IN 254 限制。每 200 个 数据 or 一次。
			if (i > 0
					&& (i + 1) % SQL_IN_LIST_LIMIT == 0
					&& i != (fieldvalue.length - 1)) {
				sbSQL.append(" ) OR ").append(fieldname).append(" IN ( ");
			}
		}
		sbSQL.append(" )) ");
		return sbSQL.toString();
	}
	public static String buildSqlForIn(final String fieldname,
			final Integer[] fieldvalue) {
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("(" + fieldname + " IN ( ");
		int len = fieldvalue.length;
		// 循环写入条件
		for (int i = 0; i < len; i++) {
			if (fieldvalue[i] != null && fieldvalue[i] > 0) {
				sbSQL.append(fieldvalue[i]);
				// 单独处理 每个取值后面的",", 对于最后一个取值后面不能添加"," 并且兼容 oracle 的 IN 254 限制。每
				// 200 个 数据 or 一次。时也不能添加","
				if (i != (fieldvalue.length - 1)
						&& !(i > 0 && (i + 1) % SQL_IN_LIST_LIMIT == 0)) {
					sbSQL.append(",");
				}
			} else {
				return null;
			}

			// 兼容 oracle 的 IN 254 限制。每 200 个 数据 or 一次。
			if (i > 0
					&& (i + 1) % SQL_IN_LIST_LIMIT == 0
					&& i != (fieldvalue.length - 1)) {
				sbSQL.append(" ) OR ").append(fieldname).append(" IN ( ");
			}
		}
		sbSQL.append(" )) ");
		return sbSQL.toString();
	}
}
