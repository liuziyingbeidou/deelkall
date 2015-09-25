package util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import utils.JsonUtils;


public class WebUtils { 

	public static String getServetPath(HttpServletRequest httpServletRequest) {
		String serverPath = httpServletRequest.getScheme() + "://"
				+ httpServletRequest.getServerName() + ":"
				+ httpServletRequest.getServerPort()
				+ httpServletRequest.getContextPath();

		return serverPath;
	}

/*	public static void initServerInfo(HttpServletRequest request) {
		request.setAttribute("contextPath", request.getContextPath());
		request.setAttribute("serverPath", getServetPath(request));
		request.setAttribute("serverPort", String.valueOf(request
				.getServerPort()));
		request.setAttribute("serverName", request.getServerName());

		request.setAttribute("requestURI", request.getRequestURI());
		request.setAttribute("requestURL", request.getRequestURL().toString());
		if (StringUtils.isEmpty(Constant.CONFIG_PATH))
			Constant.CONFIG_PATH = ServletActionContext.getServletContext()
					.getRealPath("/");
	}*/

	public static void renderFusionchartXML(HttpServletResponse response,
			String xml) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml; charset=UTF-8");
			OutputStream outs = response.getOutputStream();
			outs.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
			outs.flush();
			outs.write(xml.getBytes("UTF-8"));
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void render(HttpServletResponse response, String text,
			String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {

		}
	}

	/**
	 * 直接输出字符串.
	 */
	public static void renderText(HttpServletResponse response, String text) {
		render(response, text, "text/plain;charset=UTF-8");
	}

	/**
	 * 直接输出HTML.
	 */
	public static void renderHtml(HttpServletResponse response, String html) {
		render(response, html, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出XML.
	 */
	public static void renderXML(HttpServletResponse response, String xml) {
		render(response, xml, "text/xml;charset=UTF-8");
	}

	/**
	 * 直接输出JSON.
	 */
	public static void renderJson(HttpServletResponse response, String json) {
		render(response, json, "text/json;charset=UTF-8");
	}

	/**
	 * 直接输出JS.
	 */
	public static void renderJs(HttpServletResponse response, String jsText) {
		render(response, jsText, "application/x-javascript;charset=utf-8");
	}

	/**
	 * 设置客户端缓存过期时间 Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response,
			long expiresSeconds) {
		// Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis()
				+ expiresSeconds * 1000);
		// Http 1.1 header
		response.setHeader("Cache-Control", "max-age=" + expiresSeconds);
	}

	/**
	 * 设置客户端无缓存Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 0);
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Cache-control", "private, no-cache, no-store");
		response.setHeader("Expires", "0");
		response.setStatus(HttpServletResponse.SC_OK);

		setLastModifiedHeader(response, System.currentTimeMillis() - 10000);
		setExpiresHeader(response, 1);
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response,
			long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName
	 *            下载后的文件名.
	 * @throws UnsupportedEncodingException
	 */
	public static void setDownloadableHeader(HttpServletResponse response,
			String fileName) throws Exception {
		fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
	}

	/**
	 * 取得带相同前缀的Request Parameters.
	 * 
	 * 返回的结果Parameter名已去除前缀.
	 */

	public static Map<String, Object> getParametersStartingWith(
			HttpServletRequest request, String prefix) {
		return org.springframework.web.util.WebUtils.getParametersStartingWith(
				request, prefix);
	}

/*	public static void renderJson(HttpServletResponse response, Page page) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("items", page.getItems());
		jsonMap.put("total", page.getTotal());
		renderJson(response, jsonMap);
	}*/

	public static void renderJson(HttpServletResponse response,
			Map<String, Object> jsonMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonMap, JsonUtils
				.configJson("yyyy-MM-dd"));
		renderJson(response, jsonObject.toString());
	}

	public static void renderJson(HttpServletResponse response,
			Map<String, Object> jsonMap, JsonConfig config) {

		JSONObject jsonObject = JSONObject.fromObject(jsonMap, config);
		renderJson(response, jsonObject.toString());
	}

	public static void renderJson(HttpServletResponse response, Object object) {
		JSONObject jsonObject = JSONObject.fromObject(object, JsonUtils
				.configJson("yyyy-MM-dd"));
		renderJson(response, jsonObject.toString());
	}

	public static void renderJson(HttpServletResponse response, Object object,
			JsonConfig config) {
		JSONObject jsonObject = JSONObject.fromObject(object, config);
		renderJson(response, jsonObject.toString());
	}

	public static void renderJson(HttpServletResponse response, List<?> list) {
		JSONArray JSONArray = net.sf.json.JSONArray.fromObject(list, JsonUtils
				.configJson("yyyy-MM-dd"));
		renderJson(response, JSONArray.toString());
	}

/*	public static String getParameter(HttpServletRequest request,
			String paramName, boolean setAttribute) {
		return getParameter(request, paramName, null, setAttribute);
	}*/


/*	public static void CheckNotNull(Object... objects) {
		if (objects != null) {
			for (Object o : objects) {
				if (o == null) {
					throw new JException("你访问的链接不存在或者参数输入错误！");

				}
			}
		}

	}

	public static void CheckInValidSQLChars(String... objects) {
		if (objects != null) {
			for (String o : objects) {
				if (o != null && o.indexOf("%") != -1) {
					throw new JException("请求中发现非法字符！");

				}
			}
		}

	}

	public static void CheckInValidChars(String... Strings) {
		if (Strings != null) {
			for (String o : Strings) {

//				if (o != null
//						&& (o.startsWith("http://") || Pattern.compile(
//								"\\S*\\d+=\\d+\\S*").matcher(o).matches())) {
//					throw new JException("请求中发现非法字符！");
//				}

				for (String def : BtcConstant.checkHtmlMarkers) {
					if (o != null && (o.toLowerCase().indexOf(def) != -1)) {
						throw new JException("请求中发现非法字符！");
					}
				}
			}
		}

	}

	public static String getParameter(HttpServletRequest request,
			String paramName, String defaultValue, boolean setAttribute) {

		String paramValue = request.getParameter(paramName);

		String pageEncodingString = request.getParameter("pageEncoding");

		boolean pageEncoding = StringHelper.parseBoolean(pageEncodingString)
				|| StringUtils.isEmpty(pageEncodingString)
				&& Constant.PAGE_ENCODING;

		paramValue = paramValue == null && defaultValue != null ? defaultValue
				: paramValue;
		if (paramValue != null) {
			paramValue = paramValue.trim();
			if ("GET".equalsIgnoreCase(request.getMethod())) {
				try {
					if (pageEncoding) {
						if (!Constant.SERVER_ENCODING) {
							paramValue = new String(paramValue
									.getBytes("ISO8859-1"), "GBK");
						}
					} else {
						if (!Constant.SERVER_ENCODING) {
							paramValue = new String(paramValue
									.getBytes("ISO8859-1"), "UTF-8");
						}
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		if (setAttribute)
			request.setAttribute(paramName, paramValue);

		if (StringUtils.isNotEmpty(paramValue)) {
			CheckInValidChars(paramValue);
		}
		return paramValue;
	}*/

}
