package action.accessories;

import itf.pub.IConstant;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.auxiliary.AuxiliaryBVO;
import model.auxiliary.AuxiliaryDefBVO;
import model.auxiliary.AuxiliaryVO;
import model.basedoc.BaseDocSoVO;
import model.basedoc.BaseDocVO;
import model.basedoc.DocVarietyVO;
import model.materials.MateChildVO;
import model.materials.MaterialsVO;
import model.parts.SubPartVO;
import model.proclass.ProclassVO;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import util.CommUtil;
import util.ObjectToJSON;
import utils.JsonUtils;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

public class AccessoriesAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private AuxiliaryVO auxiliaryVO;
	
	/********************************辅料-start***************************/
	/**
	 * 辅料列表
	 */
	public String listLining(){
		
		this.setUrl("../files/accessories/acc-list-accessories.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 获得辅料Json串
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getLiningJson(){
		//List<FabricVO> fabriclist = fabricDao.selectBeanList(" where 1=1 and ifnull(dr,0)=0");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//输入查询
		String search_input = request.getParameter("searchinput");
		//是否有图
		String bisupload = request.getParameter("bisupload");
		//是否默认数据
		String bisdefault = request.getParameter("bisdefault");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" f.id,f.vcode,t.vname as docvarietyname,b.vname as brandsname, u.vname as usename,c.vname as colourName,f.bisupload,i.vname as ingredientName,f.vname");
		sql.append(" from fz_auxiliary f");
		sql.append(" left join fz_docvariety t");
		sql.append(" on f.docvarietyid=t.id");
		sql.append(" left join fz_base_doc c");
		sql.append(" on f.colourid=c.id");
		sql.append(" left join fz_base_doc b");
		sql.append(" on f.brandsid=b.id");
		sql.append(" left join fz_base_doc_so i");
		sql.append(" on f.ingredientid=i.id");
		sql.append(" left join fz_base_doc_so u");
		sql.append(" on f.useid=u.id");
		sql.append(" where ifnull(f.dr,0)=0");
		sql.append(" and f.vmoduletype='"+IConstant.MOD_ACCESSORIES+"'");
	/*	sql.append(" and t.vmoduletype='"+IConstant.MOD_ACCESSORIES+"'");
		sql.append(" and c.vdoctype='"+IConstant.DOC_COLOUR+"'");
		sql.append(" and b.vdoctype='"+IConstant.DOC_BRAND+"'");
		sql.append(" and i.vmoduletype='"+IConstant.MOD_ACCESSORIES+"'");
		sql.append(" and i.vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		sql.append(" and u.vmoduletype='"+IConstant.MOD_ACCESSORIES+"'");
		sql.append(" and u.vdoctype='"+IConstant.DOC_USE+"'");*/
		
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and (t.vname like '%"+search_input.trim()+"%' or f.vcode like '%"+search_input.trim()+"%')");
		}
		if(bisupload != null && !"".equals(bisupload)){
			if("all".equals(bisupload)){
				sql.append(" and f.bisupload in(1,0)");
			}else if("yes".equals(bisupload)){
				sql.append(" and f.bisupload = 1");
			}else if("no".equals(bisupload)){
				sql.append(" and (f.bisupload = 0 or ifnull(f.bisupload,0)=0)");
			}
		}
		
		if(bisdefault != null && !"".equals(bisdefault)){
			if("-1".equals(bisdefault)){
				sql.append(" and (bisdefault in(1,0) or ifnull(bisdefault,0)=0)");
			}else if("1".equals(bisdefault)){
				sql.append(" and bisdefault = 1");
			}
		}
		
		Integer total = getCountBySQL(sql.toString());
		List<AuxiliaryVO> lininglist = findLiningPage(sql.toString(),dgpage,rows);
		List<AuxiliaryVO> list = new ArrayList<AuxiliaryVO>();
		if(lininglist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(lininglist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				AuxiliaryVO dto = new AuxiliaryVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setDocvarietyname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setBrandsname(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				dto.setUsename(CommUtil.isNullOrEm(arry[4]) ? null : arry[4].toString());
				dto.setColourName(CommUtil.isNullOrEm(arry[5]) ? null : arry[5].toString());
				if(!CommUtil.isNullOrEm(arry[6])){
					dto.setBisupload(Integer.valueOf(arry[6].toString()));
					if("1".equals(arry[6].toString())){//有图
						dto.setVdef1("有");
					}else{
						dto.setVdef1("无");
					}
				}
				dto.setIngredientName(CommUtil.isNullOrEm(arry[7]) ? null : arry[7].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[8]) ? null : arry[8].toString());
				list.add(dto);
			}
			
		}
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("total", total);
		jsonmap.put("rows", list);
		renderJson(jsonmap);
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
	* @Description: 分页
	* @param @param sql
	* @param @param dgpage
	* @param @param rows
	* @param @param tal
	* @param @return
	* @return List<FabricVO>
	 */
	public List<AuxiliaryVO> findLiningPage(String sql, Integer dgpage,
			Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<AuxiliaryVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
	}
	
	/**
	 * @Description: 新增辅料
	 * @param @return
	 * @return String
	 */
	public String addLining(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		//String type = request.getParameter("type");
		request.setAttribute("mateChildlist",iMyHibernateDao.selectBeanList(MateChildVO.class," where ifnull(dr,0)=0"));
		if(id != null){
			auxiliaryVO = iMyHibernateDao.selectBean(AuxiliaryVO.class," where ifnull(dr,0)=0 and id ="+id);
			Integer mateid = auxiliaryVO.getMaterialsid();
			request.setAttribute("mateChildlist",iMyHibernateDao.selectBeanList(MateChildVO.class," where ifnull(dr,0)=0 and mateid="+mateid));
		}
		//品种
		request.setAttribute("docvarietylist",iMyHibernateDao.selectBeanList(DocVarietyVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"'"));
		//成份
		request.setAttribute("ingredientlist",iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"'"));
		//用途
		request.setAttribute("uselist",iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and vdoctype='"+IConstant.DOC_USE+"'"));
		//颜色
		request.setAttribute("colourlist",iMyHibernateDao.selectBeanList(BaseDocVO.class," where ifnull(dr,0)=0 and vdoctype='"+IConstant.DOC_COLOUR+"'"));
		//品牌
		request.setAttribute("brandlist",iMyHibernateDao.selectBeanList(BaseDocVO.class," where ifnull(dr,0)=0 and  vdoctype='"+IConstant.DOC_BRAND+"'"));
		//料件大类
		request.setAttribute("materialslist",iMyHibernateDao.selectBeanList(MaterialsVO.class," where ifnull(dr,0)=0"));
		
		//品类
		request.setAttribute("proclasslist",iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0"));
		//子部件
		request.setAttribute("subpartlist",getAllSubPart());
		
		this.setUrl("../files/accessories/acc-add-accessories.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 默认数据
	 * @param 
	 * @return void
	 */
	public void getDefAccBy(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String auxiliaryId = request.getParameter("auxiliaryId");
		List<AuxiliaryDefBVO> listdef = iHibernateDAO.findAll(AuxiliaryDefBVO.class, " auxiliaryId="+auxiliaryId);	
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("rows", listdef);
		renderJson(jsonmap);
		
	}
	
	/**
	 * 
	 * @Description: 获得全部子部件
	 * @param @return
	 * @return List<SubPartVO>
	 */
	public List<SubPartVO> getAllSubPart(){
	
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ");
		sql.append(" s.id,s.vcode,s.vname,p.vname as vdef1");
		sql.append(" from fz_tem_subpart s");
		sql.append(" left join fz_tem_proclass p");
		sql.append(" on s.proclassid=p.id");
		sql.append(" where ifnull(s.dr,0)=0");
		
		Integer total = getCountBySQL(sql.toString());
		List<SubPartVO> sublist = (List<SubPartVO>) iHibernateDAO.findListBySQL(sql.toString(),null,0,total);
		List<SubPartVO> list = new ArrayList<SubPartVO>();
		if(sublist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(sublist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SubPartVO dto = new SubPartVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVdef1(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				
				list.add(dto);
			}
		}
		
		return list;
	}
	
	/**
	* @Description: 保存辅料
	* @param 
	* @return void
	 */
	public void saveLining(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//默认数据
		String defdata = request.getParameter("defdata");
		//构建子表
		List<AuxiliaryDefBVO> list_b = JsonUtils.getListFromJson(defdata, AuxiliaryDefBVO.class);
		try {
			Integer id = auxiliaryVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "保存失败";
			String furl = auxiliaryVO.getVfileupload();
			if(!CommUtil.isNullOrEm(furl)){
				auxiliaryVO.setBisupload(1);
			}else{
				auxiliaryVO.setBisupload(0);
				auxiliaryVO.setVfileupload(null);
			}
			if(id != null){
				iMyHibernateDao.updateBean(auxiliaryVO);
				msg = "更新成功";
			}else{
			
				auxiliaryVO.setVmoduletype(IConstant.MOD_ACCESSORIES);
				iMyHibernateDao.insertBean(auxiliaryVO);
				msg = "新增成功";
			}
			
			iHibernateDAO.deleteAllByCondition(AuxiliaryDefBVO.class, " auxiliaryId="+id);
			
			if(list_b != null && !list_b.isEmpty()){
				for (AuxiliaryDefBVO auxiliaryDefBVO : list_b) {
					auxiliaryDefBVO.setAuxiliaryId(id);
					auxiliaryDefBVO.setIflag(2);
				}
				iHibernateDAO.saveAll(list_b);
			}
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//删除辅料
	public void delLining() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		AuxiliaryVO liningVO = iMyHibernateDao.selectBean(AuxiliaryVO.class," where vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and id= "+id);
		iMyHibernateDao.deleteBean(liningVO);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("删除成功");
	}
	
	
	/**
	 * @Description: 获得Map中最大值
	 * @param @param map
	 * @param @return
	 * @return Object
	 */
	public Object getMaxValue(Map map){
		if(map == null) return null; 
		
		Collection<Integer> c = map.values();
        Object[] obj = c.toArray();
        Arrays.sort(obj);
        return obj[obj.length-1];
	}
	
	/**
	 * 
	* @Description: 根据大类id加载子类
	* @param 
	* @return void
	 */
	public void getmateChild(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String id = request.getParameter("vmaterialsid");
		if(id == null){
			return;
		}
		List<MateChildVO> mateChildlist = iMyHibernateDao.selectBeanList(MateChildVO.class," where ifnull(dr,0)=0 and mateid="+id);
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("rows", mateChildlist);
		renderJson(jsonmap);
	}
	
	/**
	 * 
	* @Description: 根据品种加载成份、用途
	* @param 
	* @return void
	 */
	public void getIngUseByType(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//品种id
		String id = request.getParameter("docvarietyid");
		if(id == null){
			return;
		}
		
		List<BaseDocSoVO> materiallist = new ArrayList<BaseDocSoVO>();
		List<BaseDocSoVO> spelist = new ArrayList<BaseDocSoVO>();
		//成份
		materiallist = iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"' and varietyid="+id);
		//用途
		spelist = iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and vdoctype='"+IConstant.DOC_USE+"' and varietyid="+id);
		
		if(materiallist == null){
			materiallist = new ArrayList<BaseDocSoVO>();
		}
		if(spelist == null){
			spelist = new ArrayList<BaseDocSoVO>();
		}
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("doc_ingredients", materiallist);
		jsonmap.put("doc_use", spelist);
		renderJson(jsonmap);
	}
	
	
	/********************************辅料-end***************************/
	
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

	public AuxiliaryVO getAuxiliaryVO() {
		return auxiliaryVO;
	}

	public void setAuxiliaryVO(AuxiliaryVO auxiliaryVO) {
		this.auxiliaryVO = auxiliaryVO;
	}
}
