package action.scheme;

import itf.pub.IConstant;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.basedoc.BaseDocVO;
import model.proclass.ProclassVO;
import model.scheme.SchemeBVO;
import model.scheme.SchemeVO;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import util.CommUtil;
import util.ObjectToJSON;
import utils.JsonUtils;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

public class SchemeAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private SchemeVO schemeVO;
	private SchemeBVO schemeBVO;
	
	/*********************************定制价方案-start**************************/
	/**
	 * 列表界面
	 */
	public String listScheme(){
		
		this.setUrl("../files/scheme/sch-list-scheme.jsp");
		return SUCCESS;
	}
	
	/**
	 * 根据sql获得count
	 * @param sql
	 * @return
	 */
	public Integer getCountBySQL(String sql){
		return iHibernateDAO.getCountBySQL("select count(*) from ("+sql+") tb", null);
	}
	
	/**
	 * 
	* @Description: 获得定价方案Json串
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getSchemeJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//输入查询
		String search_input = request.getParameter("searchinput");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,vcode,vname from fz_scheme where type = '"+IConstant.SCH_DZ+"'");
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and vname like '"+search_input+"%'");
		}
		
		Integer total = getCountBySQL(sql.toString());
		List<SchemeVO> schlist = findSchemeBySQL(sql.toString(),dgpage, rows);

		List<SchemeVO> list = new ArrayList<SchemeVO>();
		if(schlist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(schlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SchemeVO dto = new SchemeVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				list.add(dto);
			}
		}
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("total", total);
		jsonmap.put("rows", list);
		renderJson(jsonmap);
	}
	
	/**
	 * @Description: 分页
	 * @param @param sql
	 * @param @param dgpage
	 * @param @param rows
	 * @param @return
	 * @return List<StinfoVO>
	 */
	public List<SchemeVO> findSchemeBySQL(String sql, Integer dgpage,Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<SchemeVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
	}
	
	/**
	 * @Description: 根据主表id获取子表数据
	 * @param 
	 * @return void
	 */
	public void getSchemeInfoJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//输入查询
		String schemeId = request.getParameter("schemeId");
		
		StringBuffer sql = new StringBuffer();
		
		if(schemeId != null && !"".equals(schemeId)){
			sql.append(" schemeid = "+schemeId);
		}else{
			sql.append(" 1=2");
		}
		
		Integer total = iHibernateDAO.getCountByHQL(SchemeBVO.class, sql.toString());
		List<SchemeBVO> list = (List<SchemeBVO>)iHibernateDAO.findAll(SchemeBVO.class, sql.toString());
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("total", total);
		jsonmap.put("rows", list);
		renderJson(jsonmap);
	}
	

	/**
	 * @Description: 获得工艺数据
	 * @param 
	 * @return void
	 */
	public void getProcessComb(){
		
		List<BaseDocVO> list = iHibernateDAO.findAll(BaseDocVO.class, "vdoctype='"+IConstant.DOC_TECHNOLOGY+"'");
		
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5","dprocefeemny","dprocecoefmny","vdoctype","vcode","vmemo"};
		renderJson(list,JsonUtils.configJson(excludes));
	}
	
	/**
	* @Description: 新增界面
	* @param @return
	* @return String
	 */
	public String addScheme(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			schemeVO = iMyHibernateDao.selectBean(SchemeVO.class," where id ="+id);
		}
		request.setAttribute("sizeStList",getDZListProclassVO());
		this.setUrl("../files/scheme/sch-add-scheme.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 过滤成品价方案-品类ListVO
	 * @param @return
	 * @return ProclassVO
	 */
	public List<ProclassVO> getDZListProclassVO(){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" p.id,p.vname");
		sql.append(" from fz_tem_proclass p");
		sql.append(" where ifnull(p.dr,0)=0");
		sql.append(" and p.id not in(select proclassid from fz_scheme s where ifnull(s.dr,0)=0 and s.type = 'sch_cp')");
		
		Integer total = getCountBySQL(sql.toString());
		List<ProclassVO> plist = (List<ProclassVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);
		
		List<ProclassVO> list = new ArrayList<ProclassVO>();
		if(plist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(plist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				ProclassVO dto = new ProclassVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVname(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				list.add(dto);
			}
		}
		
		return list;
	}
	
	/**
	 * @Description: 保存
	 * @return void
	 */
	public void saveScheme(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//子表
			String subdata = request.getParameter("subdata");
			
			Integer id = schemeVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "保存失败";
			
			if(id != null){
				iMyHibernateDao.updateBean(schemeVO);
				msg = "更新成功";
			}else{
				schemeVO.setType(IConstant.SCH_DZ);
				id = iHibernateDAO.saveReInt(schemeVO);
				msg = "新增成功";
			}
			
			//构建子表
			List<SchemeBVO> list_b = JsonUtils.getListFromJson(subdata, SchemeBVO.class);
			if(list_b != null){
				for (SchemeBVO schemeBVO : list_b) {
					schemeBVO.setSchemeid(id);
				}
			}
			iHibernateDAO.deleteAllByCondition(SchemeBVO.class, " schemeid="+id);
			iHibernateDAO.saveAll(list_b);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	* @Description: 删除
	* @param @throws IOException
	* @return void
	 */
	public void delScheme() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		try {
			SchemeVO schemeVO = iMyHibernateDao.selectBean(SchemeVO.class," where id= "+id);
			iMyHibernateDao.deleteBean(schemeVO);
			iHibernateDAO.deleteAllByCondition(SchemeBVO.class, " schemeid="+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("删除成功");
	}
	/**定制价方案-end**/
	/*********************************成品价方案-start**************************/
	/**
	 * 列表界面
	 */
	public String listSchemeCP(){
		
		this.setUrl("../files/scheme/sch-list-scheme-cp.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 获得定价方案Json串
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getSchemeCPJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String search_input = request.getParameter("searchinput");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,vcode,vname from fz_scheme where 1=1 and type = '"+IConstant.SCH_CP+"'");
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and vname like '"+search_input+"%'");
		}
		
		Integer total = getCountBySQL(sql.toString());
		List<SchemeVO> schlist = findSchemeBySQL(sql.toString(),dgpage, rows);

		List<SchemeVO> list = new ArrayList<SchemeVO>();
		if(schlist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(schlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SchemeVO dto = new SchemeVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				list.add(dto);
			}
		}
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("total", total);
		jsonmap.put("rows", list);
		renderJson(jsonmap);
	}
	
	
	/**
	 * @Description: 根据主表id获取子表数据
	 * @param 
	 * @return void
	 */
	public void getSchemeCPInfoJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String schemeId = request.getParameter("schemeId");
		
		StringBuffer sql = new StringBuffer();
		
		if(schemeId != null && !"".equals(schemeId)){
			sql.append(" schemeid = "+schemeId);
		}else{
			sql.append(" 1=2");
		}
		
		Integer total = iHibernateDAO.getCountByHQL(SchemeBVO.class, sql.toString());
		List<SchemeBVO> list = (List<SchemeBVO>)iHibernateDAO.findAll(SchemeBVO.class, sql.toString());
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("total", total);
		jsonmap.put("rows", list);
		renderJson(jsonmap);
	}
	
	/**
	* @Description: 新增界面
	* @param @return
	* @return String
	 */
	public String addSchemeCP(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			schemeVO = iMyHibernateDao.selectBean(SchemeVO.class," where id ="+id);
		}
		request.setAttribute("sizeStList",getCPListProclassVO());
		this.setUrl("../files/scheme/sch-add-scheme-cp.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 过滤定制价方案-品类ListVO
	 * @param @return
	 * @return ProclassVO
	 */
	public List<ProclassVO> getCPListProclassVO(){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" p.id,p.vname");
		sql.append(" from fz_tem_proclass p");
		sql.append(" where ifnull(p.dr,0)=0");
		sql.append(" and p.id not in(select proclassid from fz_scheme s where ifnull(s.dr,0)=0 and s.type = 'sch_dz')");
		
		Integer total = getCountBySQL(sql.toString());
		List<ProclassVO> plist = (List<ProclassVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);
		
		List<ProclassVO> list = new ArrayList<ProclassVO>();
		if(plist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(plist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				ProclassVO dto = new ProclassVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVname(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				list.add(dto);
			}
		}
		
		return list;
	}
	
	/**
	 * @Description: 保存
	 * @return void
	 */
	public void saveSchemeCP(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//子表
			String subdata = request.getParameter("subdata");
			
			Integer id = schemeVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "保存失败";
			
			if(id != null){
				iMyHibernateDao.updateBean(schemeVO);
				msg = "更新成功";
			}else{
				schemeVO.setType(IConstant.SCH_CP);
				id = iHibernateDAO.saveReInt(schemeVO);
				msg = "新增成功";
			}
			
			//构建子表
			List<SchemeBVO> list_b = JsonUtils.getListFromJson(subdata, SchemeBVO.class);
			if(list_b != null){
				for (SchemeBVO schemeBVO : list_b) {
					schemeBVO.setSchemeid(id);
				}
			}
			iHibernateDAO.deleteAllByCondition(SchemeBVO.class, " schemeid="+id);
			iHibernateDAO.saveAll(list_b);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**成品价方案-end**/
	
	public IHibernateDAO getiHibernateDAO() {
		return iHibernateDAO;
	}
	public void setiHibernateDAO(IHibernateDAO iHibernateDAO) {
		this.iHibernateDAO = iHibernateDAO;
	}
	public IMyHibernateDao getiMyHibernateDao() {
		return iMyHibernateDao;
	}
	public void setiMyHibernateDao(IMyHibernateDao iMyHibernateDao) {
		this.iMyHibernateDao = iMyHibernateDao;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public SchemeVO getSchemeVO() {
		return schemeVO;
	}
	public void setSchemeVO(SchemeVO schemeVO) {
		this.schemeVO = schemeVO;
	}

	public SchemeBVO getSchemeBVO() {
		return schemeBVO;
	}

	public void setSchemeBVO(SchemeBVO schemeBVO) {
		this.schemeBVO = schemeBVO;
	}
}
