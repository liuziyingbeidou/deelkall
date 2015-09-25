package action.fabric;

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
import model.auxiliary.AuxiliaryVO;
import model.basedoc.BaseDocSoVO;
import model.basedoc.BaseDocVO;
import model.basedoc.DocVarietyVO;
import model.materials.MateChildVO;
import model.materials.MaterialsVO;
import model.proclass.ProclassBVO;
import model.proclass.ProclassVO;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import util.CommUtil;
import util.ObjectToJSON;
import utils.JsonUtils;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

public class FabricAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private AuxiliaryVO auxiliaryVO;
	
	/********************************面料-start***************************/
	/**
	 * 面料列表
	 */
	public String listFabric(){
		
		this.setUrl("../files/fabric/far-list-fabric.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 获得面料Json串
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getFabricJson(){
		//List<FabricVO> fabriclist = fabricDao.selectBeanList(" where 1=1 and ifnull(dr,0)=0");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//输入查询
		String search_input = request.getParameter("searchinput");
		//是否有图
		String bisupload = request.getParameter("bisupload");
		/*//是否默认数据
		String bisdefault = request.getParameter("bisdefault");*/
		//数据流向
		String deviceType = request.getParameter("deviceType");
		//是否前展
		String isClient = request.getParameter("isClient");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" f.id,f.vcode,p.vname as patternName,i.vname as ingredientName,c.vname as colourName,f.bisupload,f.vname,f.deviceType");
		sql.append(" from fz_auxiliary f");
		sql.append(" left join fz_base_doc p ");
		sql.append(" on f.patternid = p.id");
		sql.append(" left join fz_base_doc_so i");
		sql.append(" on f.ingredientid = i.id");
		sql.append(" left join fz_base_doc c");
		sql.append(" on f.colourid = c.id");
		sql.append(" where ifnull(f.dr,0)=0");
		sql.append(" and i.vmoduletype='"+IConstant.MOD_FABRIC+"'");
		sql.append(" and i.vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		sql.append(" and p.vdoctype='"+IConstant.DOC_PATTERN+"'");
		sql.append(" and c.vdoctype='"+IConstant.DOC_COLOUR+"'");
		sql.append(" and f.vmoduletype='"+IConstant.MOD_FABRIC+"'");
		
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and f.vcode like '%"+search_input+"%'");
		}
		if(bisupload != null && !"".equals(bisupload)){
			if("all".equals(bisupload)){
				sql.append(" and f.bisupload in(1,0)");
			}else if("yes".equals(bisupload)){
				sql.append(" and f.bisupload = 1");
			}else if("no".equals(bisupload)){
				sql.append(" and f.bisupload = 0");
			}
		}
		
		if(deviceType != null && !"".equals(deviceType)){
			if("yes".equals(deviceType)){
				sql.append(" and f.deviceType in(1)");
			}
		}
		
		if(isClient != null && !"".equals(isClient)){
			if("all".equals(isClient)){
				sql.append(" and (f.isClient in(1,0) or ifnull(f.isClient,0)=0)");
			}else if("yes".equals(isClient)){
				sql.append(" and f.isClient = 1");
			}else if("no".equals(isClient)){
				sql.append(" and f.isClient = 0");
			}
		}
		
		Integer total = getCountBySQL(sql.toString());
		List<AuxiliaryVO> lininglist = findFabricPage(sql.toString(), dgpage,rows);
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
				dto.setPatternName(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setIngredientName(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				dto.setColourName(CommUtil.isNullOrEm(arry[4]) ? null : arry[4].toString());
				if(!CommUtil.isNullOrEm(arry[5])){
					dto.setBisupload(Integer.valueOf(arry[5].toString()));
					if("1".equals(arry[5].toString())){//有图
						dto.setVdef1("有");
					}else{
						dto.setVdef1("无");
					}
				}
				dto.setVname(CommUtil.isNullOrEm(arry[6]) ? null : arry[6].toString());
				if(!CommUtil.isNullOrEm(arry[7])){
					dto.setDeviceType(Integer.valueOf(arry[7].toString()));
					if("1".equals(arry[7].toString())){//含手机端
						dto.setVdef2("含手机端");
					}else{
						dto.setVdef2("全部");
					}
				}else{
					dto.setVdef2("全部");
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
	 * @Description: 根据面料主键获得所有品类的顺色关联数据
	 * @param 关联表。里料、特殊档案、顺色关联表
	 * @return void
	 */
	public void getFarCisJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String auxiliaryId = request.getParameter("auxiliaryId");
		List<AuxiliaryBVO> list = new ArrayList<AuxiliaryBVO>();
		if(auxiliaryId != null && !"".equals(auxiliaryId)){
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("select ");
			sql.append(" b.id,b.auxiliaryId,b.proclassid,b.iflag,b.icisLine,CONCAT(s1.vcode,':',s1.vname) as icisLineNM");
			sql.append(",b.icisButton,CONCAT(s2.vcode,':',s2.vname) as icisButtonNM" +
					",b.icisLining,CONCAT(a3.vcode,':',a3.vname) as icisLiningNM" +
					",b.icisXLining,CONCAT(a4.vcode,':',a4.vname) as icisXLiningNM" +
					",b.icisHBLining,CONCAT(a5.vcode,':',a5.vname) as icisHBLiningNM" +
					",b.icisBagging,CONCAT(s6.vcode,':',s6.vname) as icisBaggingNM" +
					",b.icisComponent,CONCAT(s7.vcode,':',s7.vname) as icisComponentNM" +
					",b.iciszipper,CONCAT(s8.vcode,':',s8.vname) as iciszipperNM");
			sql.append(" from fz_auxiliary_b b");
			sql.append(" left join fz_auxiliary s1");
			sql.append(" on b.icisLine=s1.id");
			sql.append(" left join fz_auxiliary s2");
			sql.append(" on b.icisButton=s2.id");
			sql.append(" left join fz_auxiliary a3");
			sql.append(" on b.icisLining=a3.id");
			sql.append(" left join fz_auxiliary a4");
			sql.append(" on b.icisXLining=a4.id");
			sql.append(" left join fz_auxiliary a5");
			sql.append(" on b.icisHBLining=a5.id");
			sql.append(" left join fz_auxiliary s6");
			sql.append(" on b.icisBagging=s6.id");
			sql.append(" left join fz_auxiliary s7");
			sql.append(" on b.icisComponent=s7.id");
			sql.append(" left join fz_auxiliary s8");
			sql.append(" on b.iciszipper=s8.id");
			sql.append(" where b.iflag=0");
			if(auxiliaryId != null && !"".equals(auxiliaryId)){
				sql.append(" and b.auxiliaryId="+auxiliaryId);
			}else{
				sql.append(" and 1=2");
			}
			
			Integer total = getCountBySQL(sql.toString());
			List<AuxiliaryBVO> cislist = (List<AuxiliaryBVO>) iHibernateDAO.findListBySQL(sql.toString(),null,0,total);
			
			if(cislist != null){
				/*
				 * 多表自定义SQL重组List<E>
				 */
				String jsonstr = ObjectToJSON.writeListJSON(cislist);
				JSONArray jsonArr = JSONArray.fromObject(jsonstr);
				int size = jsonArr.size();
				for (int i = 0; i < size; i++) {
					AuxiliaryBVO dto = new AuxiliaryBVO();
					Object[] arry = jsonArr.getJSONArray(i).toArray();
					dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
					dto.setAuxiliaryId(CommUtil.isNullOrEm(arry[1]) ? null : Integer.valueOf(arry[1].toString()));
					dto.setProclassid(CommUtil.isNullOrEm(arry[2]) ? null : Integer.valueOf(arry[2].toString()));
					dto.setIflag(CommUtil.isNullOrEm(arry[3]) ? null : Integer.valueOf(arry[3].toString()));
					dto.setIcisLine(CommUtil.isNullOrEm(arry[4]) ? null : Integer.valueOf(arry[4].toString()));
					dto.setIcisLineNM(CommUtil.isNullOrEm(arry[5]) ? null : arry[5].toString());
					dto.setIcisButton(CommUtil.isNullOrEm(arry[6]) ? null : Integer.valueOf(arry[6].toString()));
					dto.setIcisButtonNM(CommUtil.isNullOrEm(arry[7]) ? null : arry[7].toString());
					dto.setIcisLining(CommUtil.isNullOrEm(arry[8]) ? null : Integer.valueOf(arry[8].toString()));
					dto.setIcisLiningNM(CommUtil.isNullOrEm(arry[9]) ? null : arry[9].toString());
					dto.setIcisXLining(CommUtil.isNullOrEm(arry[10]) ? null : Integer.valueOf(arry[10].toString()));
					dto.setIcisXLiningNM(CommUtil.isNullOrEm(arry[11]) ? null : arry[11].toString());
					dto.setIcisHBLining(CommUtil.isNullOrEm(arry[12]) ? null : Integer.valueOf(arry[12].toString()));
					dto.setIcisHBLiningNM(CommUtil.isNullOrEm(arry[13]) ? null : arry[13].toString());
					dto.setIcisBagging(CommUtil.isNullOrEm(arry[14]) ? null : Integer.valueOf(arry[14].toString()));
					dto.setIcisBaggingNM(CommUtil.isNullOrEm(arry[15]) ? null : arry[15].toString());
					dto.setIcisComponent(CommUtil.isNullOrEm(arry[16]) ? null : Integer.valueOf(arry[16].toString()));
					dto.setIcisComponentNM(CommUtil.isNullOrEm(arry[17]) ? null : arry[17].toString());
					dto.setIciszipper(CommUtil.isNullOrEm(arry[18]) ? null : Integer.valueOf(arry[18].toString()));
					dto.setIciszipperNM(CommUtil.isNullOrEm(arry[19]) ? null : arry[19].toString());
					
					list.add(dto);
				}
			}
		}
		Map<String, Object> jsonmap = new HashMap<String, Object>();
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
	public List<AuxiliaryVO> findFabricPage(String sql, Integer dgpage,
			Integer rows) {

		int start = (dgpage - 1) * rows;
		int limit = rows;
	
		return (List<AuxiliaryVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
	}
	
	/**
	 * @Description: 新增面料
	 * @param @return
	 * @return String
	 */
	public String addFabric(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		//String type = request.getParameter("type");
		request.setAttribute("mateChildlist",iMyHibernateDao.selectBeanList(MateChildVO.class," where ifnull(dr,0)=0"));
		if(id != null){
			auxiliaryVO = iMyHibernateDao.selectBean(AuxiliaryVO.class," where ifnull(dr,0)=0 and id ="+id);
			Integer mateid = auxiliaryVO.getMaterialsid();
			request.setAttribute("mateChildlist",iMyHibernateDao.selectBeanList(MateChildVO.class," where ifnull(dr,0)=0 and mateid="+mateid));
		}
		request.setAttribute("patternlist",iMyHibernateDao.selectBeanList(BaseDocVO.class," where ifnull(dr,0)=0 and matechildid=1 and vdoctype='"+IConstant.DOC_PATTERN+"'"));
		request.setAttribute("colourlist",iMyHibernateDao.selectBeanList(BaseDocVO.class," where ifnull(dr,0)=0 and vdoctype='"+IConstant.DOC_COLOUR+"'"));
		request.setAttribute("ingredientlist",iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_FABRIC+"'"));
		request.setAttribute("farbrandlist",iMyHibernateDao.selectBeanList(BaseDocVO.class," where ifnull(dr,0)=0 and vdoctype='"+IConstant.DOC_FAR_BRAND+"'"));
		request.setAttribute("materialslist",iMyHibernateDao.selectBeanList(MaterialsVO.class," where ifnull(dr,0)=0"));
		//品类
		request.setAttribute("proclasslist",iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0"));
		this.setUrl("../files/fabric/far-add-fabric.jsp");
		return SUCCESS;
	}
	
	/**
	* @Description: 保存面料
	* @param 
	* @return void
	 */
	@Transactional
	public void saveFabric(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//顺色数据
		String cisdata = request.getParameter("cisdata");
		//构建子表
		List<AuxiliaryBVO> list_b = JsonUtils.getListFromJson(cisdata, AuxiliaryBVO.class);
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
				if(CommUtil.isNullOrEm(auxiliaryVO.getAmbient())){
					auxiliaryVO.setAmbient("1");
				}
				if(CommUtil.isNullOrEm(auxiliaryVO.getSpecular())){
					auxiliaryVO.setSpecular("0.1");
				}
				iMyHibernateDao.updateBean(auxiliaryVO);
				msg = "更新成功";
			}else{
				auxiliaryVO.setVmoduletype(IConstant.MOD_FABRIC);
				auxiliaryVO.setAmbient("1");
				auxiliaryVO.setSpecular("0.1");
				id = iHibernateDAO.saveReInt(auxiliaryVO);
				msg = "新增成功";
			}
			
			iHibernateDAO.deleteAllByCondition(AuxiliaryBVO.class, " auxiliaryId="+id);
			
			if(list_b != null && !list_b.isEmpty()){
				for (AuxiliaryBVO auxiliaryBVO : list_b) {
					auxiliaryBVO.setAuxiliaryId(id);
					auxiliaryBVO.setIflag(0);
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
	
	//删除面料
	public void delFabric() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		AuxiliaryVO liningVO = iMyHibernateDao.selectBean(AuxiliaryVO.class," where vmoduletype='"+IConstant.MOD_FABRIC+"' and id= "+id);
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
	
	
	/********************************面料-end***************************/
	
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
