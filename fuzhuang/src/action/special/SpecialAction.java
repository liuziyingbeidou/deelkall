package action.special;

import itf.pub.IConstant;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.JsonTreeVO;
import model.auxiliary.AuxiliaryVO;
import model.basedoc.DocVarietyVO;
import model.proclass.ProclassVO;
import model.special.SpecialVO;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import util.CommUtil;
import util.ObjectToJSON;
import utils.JsonUtils;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

public class SpecialAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private SpecialVO specialVO;
	
	/**特殊档案-start**/
	/**
	 * 列表界面
	 */
	public String listSpeSpecial(){
		
		this.setUrl("../files/special/spe-list-special.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 获得特殊档案Json串
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getSpecialJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//输入查询
		String search_input = request.getParameter("searchinput");
		//是否有图
		String bisupload = request.getParameter("bisupload");
		//是否默认数据
		String bisdefault = request.getParameter("bisdefault");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ");
		sql.append(" s.id,s.vcode,s.vname,s.vmemo,s.bisupload,s.bisdefault,d.vname as docvarietyname,s.vsname,s.vtransform");
		sql.append(" from fz_special s");
		sql.append(" left join fz_docvariety d");
		sql.append(" on s.docvarietyid=d.id");
		sql.append(" where 1=1");
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and (d.vname like '%"+search_input+"%' or s.vcode ='"+search_input+"' or s.vname like '%"+search_input+"%')");
		}
		if(bisupload != null && !"".equals(bisupload)){
			if("all".equals(bisupload)){
				sql.append(" and s.bisupload in(1,0)");
			}else if("yes".equals(bisupload)){
				sql.append(" and s.bisupload = 1");
			}else if("no".equals(bisupload)){
				sql.append(" and (s.bisupload = 0 or ifnull(s.bisupload,0)=0)");
			}
		}
		
		if(bisdefault != null && !"".equals(bisdefault)){
			if("-1".equals(bisdefault)){
				sql.append(" and (s.bisdefault in(1,0) or s.bisdefault is null)");
			}else if("1".equals(bisdefault)){
				sql.append(" and s.bisdefault = 1");
			}
		}
		
		//sql.append(" order by d.vname desc");
		
		Integer total = getCountBySQL(sql.toString());
		List<SpecialVO> speciallist = findSpePage(sql.toString(),dgpage,rows);
		List<SpecialVO> list = new ArrayList<SpecialVO>();
		
		if(speciallist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(speciallist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SpecialVO dto = new SpecialVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVmemo(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				dto.setBisupload(CommUtil.isNullOrEm(arry[4]) ? null : Integer.valueOf(arry[4].toString()));
				dto.setBisdefault(CommUtil.isNullOrEm(arry[5]) ? null : Integer.valueOf(arry[5].toString()));
				dto.setDocvarietyname(CommUtil.isNullOrEm(arry[6]) ? null : arry[6].toString());
				dto.setVsname(CommUtil.isNullOrEm(arry[7]) ? null : arry[7].toString());
				dto.setVtransform(CommUtil.isNullOrEm(arry[8]) ? null : arry[8].toString());
				//是否有图
				if("1".equals(arry[4]+"")){
					dto.setVdef1("有");
				}else{
					dto.setVdef1("无");
				}
				//是否默认数据
				if("1".equals(arry[5]+"")){
					dto.setVdef2("是");
				}else{
					dto.setVdef2("否");
				}
				
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
	* @param @param tal
	* @param @return
	* @return List<FabricVO>
	 */
	public List<SpecialVO> findSpePage(String sql, Integer dgpage,
			Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<SpecialVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
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
	* @Description: 新增界面
	* @param @return
	* @return String
	 */
	public String addSpeSpecial(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			specialVO = iMyHibernateDao.selectBean(SpecialVO.class," where id ="+id);
		}
		request.setAttribute("spedocvarietyList",iMyHibernateDao.selectBeanList(DocVarietyVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_SPECIAL+"'"));
		this.setUrl("../files/special/spe-add-special.jsp");
		return SUCCESS;
	}
	/**
	 * @Description: 保存
	 * @return void
	 */
	public void saveSpeSpecial(){
		try {
			Integer id = specialVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "保存失败";
			String furl = specialVO.getVfileupload();
			if(!CommUtil.isNullOrEm(furl)){
				specialVO.setBisupload(1);
			}else{
				specialVO.setBisupload(0);
				specialVO.setVfileupload(null);
			}
			if(id != null){
				
				iMyHibernateDao.updateBean(specialVO);
				updateSNMByNM(specialVO.getVname(),specialVO.getVsname());
				msg = "更新成功";
			}else{
				iMyHibernateDao.insertBean(specialVO);
				updateSNMByNM(specialVO.getVname(),specialVO.getVsname());
				msg = "新增成功";
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 特殊档案
	 * @Description: 根据当前名称-简称批量刷新其他相同名称简称
	 * @param 
	 * @return void
	 */
	private void updateSNMByNM(String name,String sname){
		String[] attrname = new String[]{"vsname"};
		String[] attrval = new String[]{sname};
		if((name != null && !"".equals(name)) && (sname != null && !"".equals(sname))){
			iHibernateDAO.updateAttrs(SpecialVO.class, attrname, attrval, " vname='"+name+"'");
		}
	}
	
	/**
	* @Description: 删除
	* @param @throws IOException
	* @return void
	 */
	public void delSpeSpecial() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		SpecialVO specialVO = iMyHibernateDao.selectBean(SpecialVO.class," where id= "+id);
		iMyHibernateDao.deleteBean(specialVO);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("删除成功");
	}
	/**特殊档案-end**/
	
	/**
	 * 获得特殊档案数据-Tree格式返回
	 * getSpecialJsonData
	 */
	public void getSpecialJsonData(){
		List<DocVarietyVO> docTypeList = iHibernateDAO.findAll(DocVarietyVO.class, " ifnull(dr,0)=0 and vmoduletype='mod_special'");
		
		if(docTypeList != null){
			List  listjson = new ArrayList();
			List<SpecialVO>  spcList = iHibernateDAO.findAll(SpecialVO.class, " ifnull(dr,0)=0");
			for (DocVarietyVO docVarietyVO : docTypeList) {
				Map<String, Object> jsonmap = new HashMap<String, Object>();
				Integer id = docVarietyVO.getId();
				jsonmap.put("id", Math.random()+"-"+id);
				jsonmap.put("text", docVarietyVO.getVname());
				jsonmap.put("state", "closed");
				List<JsonTreeVO> listChild = new ArrayList<JsonTreeVO>();
				if(spcList != null){
					for (SpecialVO specialVO : spcList) {
						Integer docvarietyid = specialVO.getDocvarietyid();
						if(id.equals(docvarietyid)){
							JsonTreeVO treeVO = new JsonTreeVO();
							treeVO.setId(specialVO.id);
							treeVO.setText(specialVO.getVname());
							listChild.add(treeVO);
						}
					}
				}
				
				if(listChild != null && !listChild.isEmpty()){
					jsonmap.put("children", listChild);
				}
				listjson.add(jsonmap);
			}
			//添加辅料扣子-start
			Map<String, Object> jsonmap = new HashMap<String, Object>();
			jsonmap.put("id", Math.random());
			jsonmap.put("text", IConstant.ACCES_BUTTON);
			jsonmap.put("state", "closed");
			List<JsonTreeVO> listChild = new ArrayList<JsonTreeVO>();
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(" a.id,a.vname,a.vcode");
			sql.append(" from fz_auxiliary a");
			sql.append(" left join fz_docvariety d");
			sql.append(" on a.docvarietyid=d.id");
			sql.append(" where a.vmoduletype='"+IConstant.MOD_ACCESSORIES+"'");
			sql.append(" and d.vname='"+IConstant.ACCES_BUTTON+"'");
			Integer total = getCountBySQL(sql.toString());
			List<AuxiliaryVO> btlist = (List<AuxiliaryVO>) iHibernateDAO.findListBySQL(sql.toString(),null,0,total);
			List<AuxiliaryVO> list = new ArrayList<AuxiliaryVO>();
			if(btlist != null){
				/*
				 * 多表自定义SQL重组List<E>
				 */
				String jsonstr = ObjectToJSON.writeListJSON(btlist);
				JSONArray jsonArr = JSONArray.fromObject(jsonstr);
				int size = jsonArr.size();
				for (int i = 0; i < size; i++) {
					AuxiliaryVO dto = new AuxiliaryVO();
					Object[] arry = jsonArr.getJSONArray(i).toArray();
					dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
					dto.setVname(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
					dto.setVcode(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
					list.add(dto);
				}
			}
			if(list != null){
				for (AuxiliaryVO auxiliaryVO : list) {
					JsonTreeVO treeVO = new JsonTreeVO();
					treeVO.setId((Integer.valueOf("777"+auxiliaryVO.id)));
					treeVO.setText(auxiliaryVO.getVname()+"-"+auxiliaryVO.getVcode());
					listChild.add(treeVO);
				}
			}
			if(listChild != null && !listChild.isEmpty()){
				jsonmap.put("children", listChild);
			}
			listjson.add(jsonmap);
			//添加辅料扣子-end
			
			String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
					"vdef3","vdef4","vdef5"};

			renderJson(listjson,JsonUtils.configJson(excludes));
		}else{
			renderJson("{\"text\":\"没有相关数据!\"}");
		}
	}
	
	/**
	 * 获得特殊档案数据-Tree格式返回
	 * 按品类进行树形
	 * getSpecialJsonDataByPro
	 * 不可用
	 */
	public void getSpecialJsonDataByPro(){
		
		List<ProclassVO> proList = iHibernateDAO.findAll(ProclassVO.class," ifnull(dr,0)=0 ");
		if(proList != null){
			List  json = new ArrayList();
			//特殊档案类型
			List<DocVarietyVO> docTypeList = iHibernateDAO.findAll(DocVarietyVO.class, " ifnull(dr,0)=0 and vmoduletype='mod_special'");
			//特殊档案内容
			List<SpecialVO>  spcList = iHibernateDAO.findAll(SpecialVO.class, " ifnull(dr,0)=0");
			//品类
			for (ProclassVO proclassVO : proList) {
				Map<String, Object> jsonmap = new HashMap<String, Object>();
				Integer id = proclassVO.getId();
				jsonmap.put("id", Math.random()+"-"+id);
				jsonmap.put("text", proclassVO.getVname());
				jsonmap.put("state", "closed");
				List<JsonTreeVO> listChild = new ArrayList<JsonTreeVO>();
				if(docTypeList != null){
					for (DocVarietyVO docVarietyVO : docTypeList) {
						Map<String, Object> tjsonmap = new HashMap<String, Object>();
						//特殊档案类别-品类id
						Integer proclassid = docVarietyVO.getProclassid();
						if(id.equals(proclassid)){
							Integer tid = proclassVO.getId();
							tjsonmap.put("id", Math.random()+"-"+tid);
							tjsonmap.put("text", docVarietyVO.getVname());
							tjsonmap.put("state", "closed");
							List<JsonTreeVO> tlistChild = new ArrayList<JsonTreeVO>();
							if(spcList != null){
								for (SpecialVO specialVO : spcList) {
									Integer docvarietyid = specialVO.getDocvarietyid();
									if(id.equals(docvarietyid)){
										JsonTreeVO treeVO = new JsonTreeVO();
										treeVO.setId(specialVO.id);
										treeVO.setText(specialVO.getVname());
										tlistChild.add(treeVO);
									}
								}
								if(tlistChild != null && !tlistChild.isEmpty()){
									tjsonmap.put("children", tlistChild);
								}
							}
						}
						if(tjsonmap != null && !tjsonmap.isEmpty()){
							jsonmap.put("children", tjsonmap);
						}
					}
					
					json.add(jsonmap);
				}
			}
			//添加辅料扣子-start
			Map<String, Object> jsonmap = new HashMap<String, Object>();
			jsonmap.put("id", Math.random());
			jsonmap.put("text", IConstant.ACCES_BUTTON);
			jsonmap.put("state", "closed");
			List<JsonTreeVO> listChild = new ArrayList<JsonTreeVO>();
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(" a.id,a.vname ");
			sql.append(" from fz_auxiliary a");
			sql.append(" left join fz_docvariety d");
			sql.append(" on a.docvarietyid=d.id");
			sql.append(" where a.vmoduletype='"+IConstant.MOD_ACCESSORIES+"'");
			sql.append(" and d.vname='"+IConstant.ACCES_BUTTON+"'");
			Integer total = getCountBySQL(sql.toString());
			List<AuxiliaryVO> btlist = (List<AuxiliaryVO>) iHibernateDAO.findListBySQL(sql.toString(),null,0,total);
			List<AuxiliaryVO> list = new ArrayList<AuxiliaryVO>();
			if(btlist != null){
				/*
				 * 多表自定义SQL重组List<E>
				 */
				String jsonstr = ObjectToJSON.writeListJSON(btlist);
				JSONArray jsonArr = JSONArray.fromObject(jsonstr);
				int size = jsonArr.size();
				for (int i = 0; i < size; i++) {
					AuxiliaryVO dto = new AuxiliaryVO();
					Object[] arry = jsonArr.getJSONArray(i).toArray();
					dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
					dto.setVname(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
					list.add(dto);
				}
			}
			if(list != null){
				for (AuxiliaryVO auxiliaryVO : list) {
					JsonTreeVO treeVO = new JsonTreeVO();
					treeVO.setId((Integer.valueOf("777"+auxiliaryVO.id)));
					treeVO.setText(auxiliaryVO.getVname());
					listChild.add(treeVO);
				}
			}
			if(listChild != null && !listChild.isEmpty()){
				jsonmap.put("children", listChild);
			}
			json.add(jsonmap);
			//添加辅料扣子-end
			
			String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
					"vdef3","vdef4","vdef5"};

			renderJson(json,JsonUtils.configJson(excludes));
		}else{
			renderJson("{\"text\":\"没有相关数据!\"}");
		}
	}
	
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
	
	public SpecialVO getSpecialVO() {
		return specialVO;
	}
	public void setSpecialVO(SpecialVO specialVO) {
		this.specialVO = specialVO;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
