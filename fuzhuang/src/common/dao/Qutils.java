package dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


public class Qutils {
	public static final String COUNT_QUERY_STRING = "select count(%s) from %s x";
	public static final String DELETE_ALL_QUERY_STRING = "delete from %s x";

	private static final String DEFAULT_ALIAS = "x";
	private static final String COUNT_REPLACEMENT_TEMPLATE = "select count(%s) $5$6$7";
	private static final String SIMPLE_COUNT_VALUE = "$2";
	private static final String COMPLEX_COUNT_VALUE = "$3$6";
	private static final String ORDER_BY_PART = "(?iu)\\s+order\\s+by\\s+.*$";

	private static final Pattern ALIAS_MATCH;
	private static final Pattern COUNT_MATCH;

	private static final String IDENTIFIER = "[\\p{Alnum}._$]+";
	private static final String IDENTIFIER_GROUP = String.format("(%s)", IDENTIFIER);

	private static final String LEFT_JOIN = "left (outer )?join " + IDENTIFIER + " (as )?" + IDENTIFIER_GROUP;
	private static final Pattern LEFT_JOIN_PATTERN = Pattern.compile(LEFT_JOIN, Pattern.CASE_INSENSITIVE);

	private static final String EQUALS_CONDITION_STRING = "%s.%s = :%s";
	private static final Pattern ORDER_BY = Pattern.compile(".*order\\s+by\\s+.*", Pattern.CASE_INSENSITIVE);


	static {

		StringBuilder builder = new StringBuilder();
		builder.append("(?<=from)"); // from as starting delimiter
		builder.append("(?: )+"); // at least one space separating
		builder.append(IDENTIFIER_GROUP); // Entity name, can be qualified (any
		builder.append("(?: as)*"); // exclude possible "as" keyword
		builder.append("(?: )+"); // at least one space separating
		builder.append("(\\w*)"); // the actual alias

		ALIAS_MATCH = Pattern.compile(builder.toString(), Pattern.CASE_INSENSITIVE);

		builder = new StringBuilder();
		builder.append("(select\\s+((distinct )?(.+?)?)\\s+)?(from\\s+");
		builder.append(IDENTIFIER);
		builder.append("(?:\\s+as)?\\s+)");
		builder.append(IDENTIFIER_GROUP);
		builder.append("(.*)");

		COUNT_MATCH = Pattern.compile(builder.toString(), Pattern.CASE_INSENSITIVE);


	}

	/**
	 * Creates a count projected query from the given original query.
	 * 
	 * @param originalQuery must not be {@literal null} or empty.
	 * @return
	 */
	public static String createCountQueryFor(String originalQuery) {
		return createCountQueryFor(originalQuery, null);
	}

	/**
	 * Creates a count projected query from the given original query.
	 * 
	 * @param originalQuery must not be {@literal null}.
	 * @param countProjection may be {@literal null}.
	 * @return
	 * @since 1.6
	 */
	public static String createCountQueryFor(String originalQuery, String countProjection) {

	
		Matcher matcher = COUNT_MATCH.matcher(originalQuery);
		String countQuery = null;

		if (countProjection == null) {

			String variable = matcher.matches() ? matcher.group(4) : null;
			boolean useVariable = variable != null && StringUtils.isNotEmpty(variable) && !variable.startsWith("new")
					&& !variable.startsWith("count(") && !variable.contains(",");

			String replacement = useVariable ? SIMPLE_COUNT_VALUE : COMPLEX_COUNT_VALUE;
			countQuery = matcher.replaceFirst(String.format(COUNT_REPLACEMENT_TEMPLATE, replacement));
		} else {
			countQuery = matcher.replaceFirst(String.format(COUNT_REPLACEMENT_TEMPLATE, countProjection));
		}

		return countQuery.replaceFirst(ORDER_BY_PART, "");
	}

	
	
	public static void main(String[] args){
		String sql = "select {c.*} from tf_ct c start with c.id = 1 connect by prior c.parent_id = c.id";
	
		String r = createCountQueryFor(sql,"c.id");
		System.out.println(r);
	}
}
