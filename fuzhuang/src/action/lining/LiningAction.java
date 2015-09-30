package action.lining;

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
import model.materials.MateChildVO;
import model.materials.MaterialsVO;
import model.parts.SubPartVO;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import util.CommUtil;
import util.ObjectToJSON;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

public class LiningAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private AuxiliaryVO auxiliaryVO;
	
	/********************************里料-start***************************/
	/**
	 * 里料列表
	 */
	public String listLining(){
		
		this.setUrl("../files/lining/lin-list-lining.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 获得里料Json串
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
		sql.append(" f.id,f.vcode,p.vname as patternName,i.vname as ingredientName,c.vname as colourName,f.bisupload,f.vname");
		sql.append(" from fz_auxiliary f");
		sql.append(" left join fz_base_doc p ");
		sql.append(" on f.patternid = p.id");
		sql.append(" left join fz_base_doc_so i");
		sql.append(" on f.ingredientid = i.id");
		sql.append(" left join fz_base_doc c");
		sql.append(" on f.colourid = c.id");
		sql.append(" where ifnull(f.dr,0)=0");
		sql.append(" and i.vmoduletype='"+IConstant.MOD_LINING+"'");
		sql.append(" and i.vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		sql.append(" and p.vdoctype='"+IConstant.DOC_PATTERN+"'");
		sql.append(" and c.vdoctype='"+IConstant.DOC_COLOUR+"'");
		sql.append(" and f.vmoduletype='"+IConstant.MOD_LINING+"'");
		
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and f.vcode like '%"+search_input+"%'");
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
	 * @Description: 新增里料
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
		request.setAttribute("patternlist",iMyHibernateDao.selectBeanList(BaseDocVO.class," where ifnull(dr,0)=0 and matechildid=2 and vdoctype='"+IConstant.DOC_PATTERN+"'"));
		request.setAttribute("colourlist",iMyHibernateDao.selectBeanList(BaseDocVO.class," where ifnull(dr,0)=0 and vdoctype='"+IConstant.DOC_COLOUR+"'"));
		request.setAttribute("ingredientlist",iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_LINING+"'"));
		request.setAttribute("materialslist",iMyHibernateDao.selectBeanList(MaterialsVO.class," where ifnull(dr,0)=0"));
		//子部件
		request.setAttribute("subpartlist",getAllSubPart());
		this.setUrl("../files/lining/lin-add-lining.jsp");
		return SUCCESS;
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
	* @Description: 保存里料
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
				
				String pattercode = null;
				String ingrecode = null;
				String colourcode = null;
				String matecode = null;
				String mateChildcode = null;
				
				//花型code
				if(patternid != null){
					pattercode = iMyHibernateDao.selectBean(PatternVO.class," where ifnull(dr,0)=0 and id="+patternid).getVcode();
				}
				//成分
				if(ingredientid != null){
					ingrecode = iMyHibernateDao.selectBean(BaseDocSoVO.class," where ifnull(dr,0)=0 and id="+ingredientid).getVcode();
				}
				//颜色
				if(ingredientid != null){
					colourcode = iMyHibernateDao.selectBean(ColourVO.class," where ifnull(dr,0)=0 and id="+colourid).getVcode();
				}
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
				
				List<AuxiliaryVO> lininglist = iMyHibernateDao.selectBeanList(AuxiliaryVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_LINING+"'");
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
				auxiliaryVO.setVmoduletype(IConstant.MOD_LINING);
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
	
	//删除里料
	public void delLining() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		AuxiliaryVO liningVO = iMyHibernateDao.selectBean(AuxiliaryVO.class," where vmoduletype='"+IConstant.MOD_LINING+"' and id= "+id);
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
	
	
	/********************************里料-end***************************/
	
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
