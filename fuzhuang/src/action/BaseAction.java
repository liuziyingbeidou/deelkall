package action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import util.WebUtils;
import utils.JsonUtils;

public class BaseAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	//分页-start
	protected Integer dgpage=30;
	protected Integer rows;
	//分页-end
	
	protected void renderJson(Map<String, ?> jsonMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonMap,JsonUtils.configJson("yyyy-MM-dd"));
		String s = jsonObject.toString();
		renderJson(s);
	}
	protected void renderJson(Map<String, ?> jsonMap, JsonConfig config) {
		JSONObject jsonObject = JSONObject.fromObject(jsonMap, config);
		String s = jsonObject.toString();
		renderJson(s);
	}
	protected void renderJson(List<?> list) {
		JSONArray JSONArray = net.sf.json.JSONArray.fromObject(list,JsonUtils.configJson("yyyy-MM-dd"));
		renderJson(JSONArray.toString());
	}
	
	protected void renderJson(List<?> list, JsonConfig config) {
		JSONArray JSONArray = net.sf.json.JSONArray.fromObject(list, config);
		renderJson(JSONArray.toString());
	}

	protected void renderJson(List<?> list, String entityKey, JsonConfig config) {
		List<Map<String, Object>> jsonList = JsonUtils.toMapList(list, entityKey);
		renderJson(jsonList, config);
	}
	
	protected void renderJson(Object object) {
		JSONObject jsonObject = JSONObject.fromObject(object);
		renderJson(jsonObject.toString());
	}
	protected void renderJson(Object object, JsonConfig config) {
		JSONObject jsonObject = JSONObject.fromObject(object, config);
		renderJson(jsonObject.toString());
	}
	
	/**
	 * 直接输出json字符串.
	 * @param json
	 * @return
	 */
	protected void renderJson(String json) {
		render(json, "text/json;charset=UTF-8");
	}
	/**
	 * 直接输出JS字符串.
	 * @param jsText
	 * @return
	 */
	protected void renderJs(String jsText) {
		render(jsText, "application/x-javascript;charset=utf-8");
	}
	
	/**
	 * 直接输出字符串.
	 */
	protected void renderText(String text) {
		WebUtils.setNoCacheHeader(ServletActionContext.getResponse());
		render(text, "text/plain;charset=UTF-8");
	}

	/**
	 * 直接输出HTML.
	 */
	protected void renderHtml(String html) {
		render(html, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出XML.
	 */
	protected void renderXML(String xml) {
		
		render(xml, "text/xml;charset=UTF-8");
	}
	protected void render(String text, String contentType) {
		try {
			WebUtils.setNoCacheHeader(ServletActionContext.getResponse());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			response.getWriter().write(text);
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Integer getDgpage() {
		return dgpage;
	}

	public void setDgpage(Integer dgpage) {
		this.dgpage = dgpage;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
}
