package action.packing;

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

import model.auxiliary.AuxiliaryVO;
import model.basedoc.BaseDocSoVO;
import model.basedoc.BaseDocVO;
import model.basedoc.DocVarietyVO;
import model.materials.MateChildVO;
import model.materials.MaterialsVO;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import util.CommUtil;
import util.ObjectToJSON;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

public class PackingAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private AuxiliaryVO auxiliaryVO;
	
	/********************************包装材料-start***************************/
	/**
	 * 包装材料列表
	 */
	public String listLining(){
		
		this.setUrl("../files/packing/pac-list-packing.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 获得包装材料Json串
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getLiningJson(){
		//List<FabricVO> fabriclist = fabricDao.selectBeanList(" where 1=1 and ifnull(dr,0)=0");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String search_input = request.getParameter("searchinput");
		String bisupload = request.getParameter("bisupload");
		//是否默认数据
		String bisdefault = request.getParameter("bisdefault");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" f.id,f.vcode,f.docvarietyname,f.brandsname, f.usename,f.specname,f.bisupload");
		sql.append(" from fz_auxiliary f");
		sql.append(" where ifnull(f.dr,0)=0");
		sql.append(" and f.vmoduletype='"+IConstant.MOD_PACKING+"'");
		
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and f.docvarietyname like '"+search_input+"%'");
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
		if(bisdefault != null && !"".equals(bisdefault)){
			if("-1".equals(bisdefault)){
				sql.append(" and (bisdefault in(1,0) or bisdefault is null)");
			}else if("1".equals(bisdefault)){
				sql.append(" and bisdefault = 1");
			}
		}
		
		Integer total = getCountBySQL(sql.toString());
		List<AuxiliaryVO> lininglist = findLiningPage(sql.toString(), dgpage,rows);
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
				dto.setSpecname(CommUtil.isNullOrEm(arry[5]) ? null : arry[5].toString());
				if(!CommUtil.isNullOrEm(arry[6])){
					dto.setBisupload(Integer.valueOf(arry[6].toString()));
					if("1".equals(arry[6].toString())){//有图
						dto.setVdef1("有");
					}else{
						dto.setVdef1("无");
					}
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
	 * @Description: 新增包装材料
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
		//品名
		request.setAttribute("docvarietylist",iMyHibernateDao.selectBeanList(DocVarietyVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_PACKING+"'"));
		//材质
		request.setAttribute("useidlist",iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_PACKING+"' and vdoctype='"+IConstant.DOC_MATERIAL+"'"));
		//规格
		request.setAttribute("specidlist",iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_PACKING+"' and vdoctype='"+IConstant.DOC_SPEC+"'"));
		//品牌
		request.setAttribute("brandlist",iMyHibernateDao.selectBeanList(BaseDocVO.class," where ifnull(dr,0)=0 and  vdoctype='"+IConstant.DOC_BRAND+"'"));
		//料件大类
		request.setAttribute("materialslist",iMyHibernateDao.selectBeanList(MaterialsVO.class," where ifnull(dr,0)=0"));
		
		this.setUrl("../files/packing/pac-add-packing.jsp");
		return SUCCESS;
	}
	
	/**
	* @Description: 保存包装材料
	* @param 
	* @return void
	 */
	public void saveLining(){
		
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
				/***自动生成编码-start***//*
				Integer patternid = auxiliaryVO.getPatternid();
				Integer ingredientid = auxiliaryVO.getIngredientid();
				Integer colourid = auxiliaryVO.getColourid();
				Integer materialsid = auxiliaryVO.getMaterialsid();
				Integer mateChildid = auxiliaryVO.getMatechildid();
				
				String pattercode = "01";
				String ingrecode = "1";
				String colourcode = "02";
				String matecode = null;
				String mateChildcode = null;
				
				
				//料件大类
				if(materialsid != null){
					matecode = iMyHibernateDao.selectBean(MaterialsVO.class," where ifnull(dr,0)=0 and id="+materialsid).getVcode();
				}
				//料件子类
				if(mateChildid != null){
					mateChildcode = iMyHibernateDao.selectBean(MateChildVO.class," where ifnull(dr,0)=0 and id="+mateChildid).getVcode();
				}
				
				SelInfoVO selInfoVO = new SelInfoVO();
				selInfoVO.setPattern(pattercode);
				selInfoVO.setIngre(ingrecode);
				selInfoVO.setMate(matecode);
				selInfoVO.setMateChild(mateChildcode);
				selInfoVO.setColour(colourcode);
				
				String liningcode = Util.getCodeBy("0",selInfoVO);
				
				List<AuxiliaryVO> lininglist = iMyHibernateDao.selectBeanList(AuxiliaryVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_PACKING+"'");
				String vcode = null;
				if(lininglist != null){
					Map<String,String> map = new HashMap<String,String>();
					Integer size = lininglist.size();
					for(int i = 0; i < size; i++){
						AuxiliaryVO vo = lininglist.get(i);
						String code = vo.getVcode().substring(7, 10);
						map.put(code, code);
					}
					
					Object vl = getMaxValue(map);
					if(vl != null){
						String temcode = null;
						String strvl = vl.toString();
						Integer ivl = Integer.valueOf(strvl) + 1;
						String str2vl = ivl + "";
						Integer len = str2vl.length();
						if(len == 1){
							temcode = "00" + str2vl;
						}else if(len == 2){
							temcode = "0" + str2vl;
						}else if(len == 3){
							temcode = str2vl;
						}else{
							temcode = "001";
						}
						vcode = liningcode + temcode;
					}
				}else{
					vcode = liningcode + "001";
				}
				
				auxiliaryVO.setVcode(vcode);
				*//***自动生成编码-end***/
				auxiliaryVO.setVmoduletype(IConstant.MOD_PACKING);
				iMyHibernateDao.insertBean(auxiliaryVO);
				msg = "新增成功";
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//删除包装材料
	public void delLining() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		AuxiliaryVO liningVO = iMyHibernateDao.selectBean(AuxiliaryVO.class," where vmoduletype='"+IConstant.MOD_PACKING+"' and id= "+id);
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
	* @Description: 根据品名加载材质、规格
	* @param 
	* @return void
	 */
	public void getMatSpeByType(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//品名id
		String id = request.getParameter("docvarietyid");
		if(id == null){
			return;
		}
		//材质
		List<BaseDocSoVO> materiallist = iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_PACKING+"' and vdoctype='"+IConstant.DOC_MATERIAL+"' and varietyid="+id);
		//规格
		List<BaseDocSoVO> spelist = iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_PACKING+"' and vdoctype='"+IConstant.DOC_SPEC+"' and varietyid="+id);
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("doc_material", materiallist);
		jsonmap.put("doc_spec", spelist);
		renderJson(jsonmap);
	}
	
	
	/********************************包装材料-end***************************/
	
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
