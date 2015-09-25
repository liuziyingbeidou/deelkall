package action.bomtb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bomtb.BtcconfigBVO;
import model.bomtb.BtcconfigVO;
import model.bomtb.CfeedBVO;
import model.bomtb.CfeedVO;
import model.proclass.ProclassVO;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import util.CommUtil;
import util.ObjectToJSON;
import utils.JsonUtils;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

public class BomTbAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	//耗料主表
	private CfeedVO cfeedVO;
	//耗料子表
	private CfeedBVO cfeedBVO;
	//BOM主表
	private BtcconfigVO btcconfigVO;
	//BOM子表
	private BtcconfigBVO btcconfigBVO;
	
	
	/*********************************耗料表-start**************************/
	/**
	 * 列表界面
	 */
	public String listFeed(){
		
		this.setUrl("../files/bombt/bom-list-feed.jsp");
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
	* @Description: 获得耗料表Json串
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getFeedJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//输入查询
		String search_input = request.getParameter("searchinput");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" c.id,p.vname as proclassName");
		sql.append(" from fz_cfeed c");
		sql.append(" left join fz_tem_proclass p");
		sql.append(" on c.proclassid = p.id");
		sql.append(" where ifnull(c.dr,0)=0");
		
		Integer total = getCountBySQL(sql.toString());
		List<CfeedVO> schlist = findFeedBySQL(sql.toString(),dgpage, rows);

		List<CfeedVO> list = new ArrayList<CfeedVO>();
		if(schlist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(schlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				CfeedVO dto = new CfeedVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setProclassName(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				
				list.add(dto);
			}
		}
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("total", total);
		jsonmap.put("rows", list);
		renderJson(jsonmap);
	}
	
	/**
	 * 
	 * @Description: 分页
	 * @param @param sql
	 * @param @param dgpage
	 * @param @param rows
	 * @param @return
	 * @return List<CfeedVO>
	 */
	public List<CfeedVO> findFeedBySQL(String sql, Integer dgpage,Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<CfeedVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
	}
	
	/**
	 * @Description: 根据主表id获取子表数据
	 * @param 
	 * @return void
	 */
	public void getFeedInfoJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//输入查询
		String feedId = request.getParameter("feedId");
		
		StringBuffer sql = new StringBuffer();
		
		if(feedId != null && !"".equals(feedId)){
			sql.append(" feedid = "+feedId);
		}else{
			sql.append(" 1=2");
		}
		
		Integer total = iHibernateDAO.getCountByHQL(CfeedBVO.class, sql.toString());
		List<CfeedBVO> list = (List<CfeedBVO>)iHibernateDAO.findAll(CfeedBVO.class, sql.toString());
		
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
	public String addFeed(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			cfeedVO = iMyHibernateDao.selectBean(CfeedVO.class," where id ="+id);
		}
		request.setAttribute("proList",iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0 "));
		this.setUrl("../files/bombt/bom-add-feed.jsp");
		return SUCCESS;
	}
	
	
	/**
	 * @Description: 保存
	 * @return void
	 */
	public void saveFeed(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//子表
			String feeddata = request.getParameter("feeddata");
			
			Integer id = cfeedVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "保存失败";
			
			if(id != null){
				iMyHibernateDao.updateBean(cfeedVO);
				msg = "更新成功";
			}else{
				id = iHibernateDAO.saveReInt(cfeedVO);
				msg = "新增成功";
			}
			
			//构建子表
			List<CfeedBVO> list_b = JsonUtils.getListFromJson(feeddata, CfeedBVO.class);
			if(list_b != null){
				for (CfeedBVO cfeedBVO : list_b) {
					cfeedBVO.setFeedid(id);
				}
			}
			iHibernateDAO.deleteAllByCondition(CfeedBVO.class, " feedid="+id);
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
	public void delFeed() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		try {
			CfeedVO cfeedVO = iMyHibernateDao.selectBean(CfeedVO.class," where id= "+id);
			iMyHibernateDao.deleteBean(cfeedVO);
			iHibernateDAO.deleteAllByCondition(CfeedBVO.class, " feedid="+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("删除成功");
	}
	
	/**耗料表-end**/
	
	/*********************************BOM标配表-start**************************/
	/**
	 * 列表界面
	 */
	public String listConfig(){
		
		this.setUrl("../files/bombt/bom-list-config.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 获得BOM表Json串
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getConfigJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//输入查询
		String search_input = request.getParameter("searchinput");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" c.id,p.vname as proclassName");
		sql.append(" from fz_btcconfig c");
		sql.append(" left join fz_tem_proclass p");
		sql.append(" on c.proclassid = p.id");
		sql.append(" where ifnull(c.dr,0)=0");
		
		Integer total = getCountBySQL(sql.toString());
		List<BtcconfigVO> schlist = findConfigBySQL(sql.toString(),dgpage, rows);

		List<BtcconfigVO> list = new ArrayList<BtcconfigVO>();
		if(schlist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(schlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				BtcconfigVO dto = new BtcconfigVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setProclassName(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				
				list.add(dto);
			}
		}
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("total", total);
		jsonmap.put("rows", list);
		renderJson(jsonmap);
	}
	
	/**
	 * 
	 * @Description: 分页
	 * @param @param sql
	 * @param @param dgpage
	 * @param @param rows
	 * @param @return
	 * @return List<CfeedVO>
	 */
	public List<BtcconfigVO> findConfigBySQL(String sql, Integer dgpage,Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<BtcconfigVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
	}
	
	/**
	 * @Description: 根据主表id获取子表数据
	 * @param 
	 * @return void
	 */
	public void getConfigInfoJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//输入查询
		String btcId = request.getParameter("btcId");
		
		StringBuffer sql = new StringBuffer();
		
		if(btcId != null && !"".equals(btcId)){
			sql.append(" btcId = "+btcId);
		}else{
			sql.append(" 1=2");
		}
		
		Integer total = iHibernateDAO.getCountByHQL(BtcconfigBVO.class, sql.toString());
		List<BtcconfigBVO> list = (List<BtcconfigBVO>)iHibernateDAO.findAll(BtcconfigBVO.class, sql.toString());
		
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
	public String addConfig(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			btcconfigVO = iMyHibernateDao.selectBean(BtcconfigVO.class," where id ="+id);
		}
		request.setAttribute("proList",iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0 "));
		this.setUrl("../files/bombt/bom-add-config.jsp");
		return SUCCESS;
	}
	
	
	/**
	 * @Description: 保存
	 * @return void
	 */
	public void saveConfig(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//子表
			String configdata = request.getParameter("configdata");
			
			Integer id = btcconfigVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "保存失败";
			
			if(id != null){
				iMyHibernateDao.updateBean(btcconfigVO);
				msg = "更新成功";
			}else{
				id = iHibernateDAO.saveReInt(btcconfigVO);
				msg = "新增成功";
			}
			
			//构建子表
			List<BtcconfigBVO> list_b = JsonUtils.getListFromJson(configdata, BtcconfigBVO.class);
			if(list_b != null){
				for (BtcconfigBVO btcconfigBVO : list_b) {
					btcconfigBVO.setBtcId(id);
				}
			}
			iHibernateDAO.deleteAllByCondition(BtcconfigBVO.class, " btcId="+id);
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
	public void delConfig() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		try {
			BtcconfigVO btcconfigVO = iMyHibernateDao.selectBean(BtcconfigVO.class," where id= "+id);
			iMyHibernateDao.deleteBean(btcconfigVO);
			iHibernateDAO.deleteAllByCondition(BtcconfigBVO.class, " btcId="+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("删除成功");
	}
	/**BOM标配表-end**/
	
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
	public CfeedVO getCfeedVO() {
		return cfeedVO;
	}
	public void setCfeedVO(CfeedVO cfeedVO) {
		this.cfeedVO = cfeedVO;
	}
	public CfeedBVO getCfeedBVO() {
		return cfeedBVO;
	}
	public void setCfeedBVO(CfeedBVO cfeedBVO) {
		this.cfeedBVO = cfeedBVO;
	}
	public BtcconfigVO getBtcconfigVO() {
		return btcconfigVO;
	}
	public void setBtcconfigVO(BtcconfigVO btcconfigVO) {
		this.btcconfigVO = btcconfigVO;
	}
	public BtcconfigBVO getBtcconfigBVO() {
		return btcconfigBVO;
	}
	public void setBtcconfigBVO(BtcconfigBVO btcconfigBVO) {
		this.btcconfigBVO = btcconfigBVO;
	}
}
