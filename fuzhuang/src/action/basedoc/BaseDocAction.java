package action.basedoc;

import itf.pub.IConstant;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.auxiliary.AuxiliaryVO;
import model.basedoc.BaseDocSoVO;
import model.basedoc.BaseDocVO;
import model.basedoc.DocVarietyVO;
import model.proclass.ProclassVO;
import model.special.SpecialVO;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import util.CommUtil;
import util.ObjectToJSON;
import util.Pager;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

public class BaseDocAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private BaseDocVO baseDocVO;
	private BaseDocSoVO baseDocSoVO;
	private DocVarietyVO docVarietyVO;
	
	/**花型-开始**/
	/**
	* @Description: 花型列表
	* @param 
	* @return void
	 */
	public String listPattern(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0  and vdoctype='"+IConstant.DOC_PATTERN+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vdoctype='"+IConstant.DOC_PATTERN+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(BaseDocVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(BaseDocVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listPattern", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listPattern");
		
		this.setUrl("../files/basedoc/listpattern.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 新增花型
	 * @param @return
	 * @return String
	 */
	public String addPattern(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocVO = iMyHibernateDao.selectBean(BaseDocVO.class," where id ="+id+ " and vdoctype='"+IConstant.DOC_PATTERN+"'");
		}
		this.setUrl("../files/basedoc/addpattern.jsp");
		return SUCCESS;
	}
	//删除花型
	public void delPattern() throws IOException {
		deleteDoc(IConstant.DOC_PATTERN);
	}
	
	/**
	 * 
	* @Description: 保存-花型
	* @param 
	* @return void
	 */
	public void savePattern(){
		saveOrUpdateDoc(baseDocVO,IConstant.DOC_PATTERN);
	}
	/**花型-结束**/
	/**色系-开始**/
	/**
	* @Description: 色系列表
	* @param 
	* @return void
	 */
	public String listColour(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0  and vdoctype='"+IConstant.DOC_COLOUR+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vdoctype='"+IConstant.DOC_COLOUR+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(BaseDocVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(BaseDocVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listColour", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listColour");
		
		this.setUrl("../files/basedoc/listcolour.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 新增色系
	 * @param @return
	 * @return String
	 */
	public String addColour(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocVO = iMyHibernateDao.selectBean(BaseDocVO.class," where id ="+id+ " and vdoctype='"+IConstant.DOC_COLOUR+"'");
		}
		this.setUrl("../files/basedoc/addcolour.jsp");
		return SUCCESS;
	}
	//删除色系
	public void delColour() throws IOException {
		deleteDoc(IConstant.DOC_COLOUR);
	}
	
	/**
	 * 
	* @Description: 保存-色系
	* @param 
	* @return void
	 */
	public void saveColour(){
		saveOrUpdateDoc(baseDocVO,IConstant.DOC_COLOUR);
	}
	/**色系-结束**/
	/**品牌-开始**/
	/**
	* @Description: 品牌列表
	* @param 
	* @return void
	 */
	public String listBrand(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0  and vdoctype='"+IConstant.DOC_BRAND+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vdoctype='"+IConstant.DOC_BRAND+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(BaseDocVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(BaseDocVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listBrand", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listBrand");
		
		this.setUrl("../files/basedoc/listbrand.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 新增品牌
	 * @param @return
	 * @return String
	 */
	public String addBrand(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocVO = iMyHibernateDao.selectBean(BaseDocVO.class," where id ="+id+ " and vdoctype='"+IConstant.DOC_BRAND+"'");
		}
		this.setUrl("../files/basedoc/addbrand.jsp");
		return SUCCESS;
	}
	//删除品牌
	public void delBrand() throws IOException {
		deleteDoc(IConstant.DOC_BRAND);
	}
	
	/**
	 * 
	* @Description: 保存-品牌
	* @param 
	* @return void
	 */
	public void saveBrand(){
		saveOrUpdateDoc(baseDocVO,IConstant.DOC_BRAND);
	}
	/**品牌-结束**/
	/**面料品牌-开始**/
	/**
	* @Description: 品牌列表
	* @param 
	* @return void
	 */
	public String listFarBrand(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0  and vdoctype='"+IConstant.DOC_FAR_BRAND+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vdoctype='"+IConstant.DOC_FAR_BRAND+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(BaseDocVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(BaseDocVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listFarBrand", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listFarBrand");
		
		this.setUrl("../files/fabric/list-far-brand.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 新增品牌
	 * @param @return
	 * @return String
	 */
	public String addFarBrand(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocVO = iMyHibernateDao.selectBean(BaseDocVO.class," where id ="+id+ " and vdoctype='"+IConstant.DOC_FAR_BRAND+"'");
		}
		this.setUrl("../files/fabric/add-far-brand.jsp");
		return SUCCESS;
	}
	//删除品牌
	public void delFarBrand() throws IOException {
		deleteDoc(IConstant.DOC_FAR_BRAND);
	}
	
	/**
	 * 
	* @Description: 保存-品牌
	* @param 
	* @return void
	 */
	public void saveFarBrand(){
		saveOrUpdateDoc(baseDocVO,IConstant.DOC_FAR_BRAND);
	}
	/**面料品牌-结束**/
	/**工艺-开始**/
	/**
	* @Description: 工艺列表
	* @param 
	* @return void
	 */
	public String listProcess(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0  and vdoctype='"+IConstant.DOC_TECHNOLOGY+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vdoctype='"+IConstant.DOC_TECHNOLOGY+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(BaseDocVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(BaseDocVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listProcess", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listProcess");
		
		this.setUrl("../files/basedoc/listprocess.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 新增工艺
	 * @param @return
	 * @return String
	 */
	public String addProcess(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocVO = iMyHibernateDao.selectBean(BaseDocVO.class," where id ="+id+ " and vdoctype='"+IConstant.DOC_TECHNOLOGY+"'");
		}
		//品类
		request.setAttribute("proclasslist",iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0"));
		this.setUrl("../files/basedoc/addprocess.jsp");
		return SUCCESS;
	}
	//删除工艺
	public void delProcess() throws IOException {
		deleteDoc(IConstant.DOC_TECHNOLOGY);
	}
	
	/**
	 * 
	* @Description: 保存-工艺
	* @param 
	* @return void
	 */
	public void saveProcess(){
		saveOrUpdateDoc(baseDocVO,IConstant.DOC_TECHNOLOGY);
	}
	/**工艺-结束**/
	
	/**面料-成份档案-start**/
	/**
	* @Description: 面料-成份档案列表
	* @param 
	* @return void
	 */
	public String listFarIngredient(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0 and vmoduletype='"+IConstant.MOD_FABRIC+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vmoduletype='"+IConstant.MOD_FABRIC+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(BaseDocSoVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(BaseDocSoVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listFarIngredient", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listFarIngredient");
		
		this.setUrl("../files/fabric/far-list-ingredient.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 面料-成份档案
	 * @param @return
	 * @return String
	 */
	public String addFarIngredient(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocSoVO = iMyHibernateDao.selectBean(BaseDocSoVO.class," where id ="+id+ " and vmoduletype='"+IConstant.MOD_FABRIC+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		}
		this.setUrl("../files/fabric/far-add-ingredient.jsp");
		return SUCCESS;
	}
	//删除面料-成份档案
	public void delFarIngredient() throws IOException {
		deleteDocSo(IConstant.MOD_FABRIC,IConstant.DOC_INGREDIENTS);
	}
	
	/**
	 * 
	* @Description: 保存-面料-成份档案
	* @param 
	* @return void
	 */
	public void saveFarIngredient(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_FABRIC,IConstant.DOC_INGREDIENTS);
	}
	/**面料-成份档案-end**/
	
	
	/**里料-成份档案-start**/
	/**
	* @Description: 里料-成份档案列表
	* @param 
	* @return void
	 */
	public String listLinIngredient(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0 and vmoduletype='"+IConstant.MOD_LINING+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vmoduletype='"+IConstant.MOD_LINING+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(BaseDocSoVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(BaseDocSoVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listLinIngredient", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listLinIngredient");
		
		this.setUrl("../files/lining/lin-list-ingredient.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 里料-成份档案
	 * @param @return
	 * @return String
	 */
	public String addLinIngredient(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocSoVO = iMyHibernateDao.selectBean(BaseDocSoVO.class," where id ="+id+ " and vmoduletype='"+IConstant.MOD_LINING+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		}
		this.setUrl("../files/lining/lin-add-ingredient.jsp");
		return SUCCESS;
	}
	//删除里料-成份档案
	public void delLinIngredient() throws IOException {
		deleteDocSo(IConstant.MOD_LINING,IConstant.DOC_INGREDIENTS);
	}
	
	/**
	 * 
	* @Description: 保存-里料-成份档案
	* @param 
	* @return void
	 */
	public void saveLinIngredient(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_LINING,IConstant.DOC_INGREDIENTS);
	}
	/**里料-成份档案-end**/
	
	/**********************************辅料-start*****************************/
	/***品种-start***/
	/**
	* @Description: 辅料-品种档案列表
	* @param 
	* @return void
	 */
	public String listAccDocVariety(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(DocVarietyVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(DocVarietyVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listAccDocVariety", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listAccDocVariety");
		
		this.setUrl("../files/accessories/acc-list-docvariety.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 辅料-品种档案
	 * @param @return
	 * @return String
	 */
	public String addAccDocVariety(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			docVarietyVO = iMyHibernateDao.selectBean(DocVarietyVO.class," where id ="+id+ " and vmoduletype='"+IConstant.MOD_ACCESSORIES+"'");
		}
		this.setUrl("../files/accessories/acc-add-docvariety.jsp");
		return SUCCESS;
	}
	//删除辅料-品种档案
	public void delAccDocVariety() throws IOException {
		deleteDocVariety(IConstant.MOD_ACCESSORIES);
	}
	
	/**
	 * 
	* @Description: 保存-辅料-品种档案
	* @param 
	* @return void
	 */
	public void saveAccDocVariety(){
		saveOrUpdateDocVariety(docVarietyVO,IConstant.MOD_ACCESSORIES);
	}
	/***品种-end***/
	
	/****成份-start****/
	/**
	* @Description: 辅料-成份档案列表
	* @param 
	* @return void
	 */
	public String listAccIngredient(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(BaseDocSoVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(BaseDocSoVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listAccIngredient", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listAccIngredient");
		
		this.setUrl("../files/accessories/acc-list-ingredient.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 辅料-成份档案
	 * @param @return
	 * @return String
	 */
	public String addAccIngredient(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocSoVO = iMyHibernateDao.selectBean(BaseDocSoVO.class," where id ="+id+ " and vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		}
		request.setAttribute("docVarietyList",iMyHibernateDao.selectBeanList(DocVarietyVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"'"));
		this.setUrl("../files/accessories/acc-add-ingredient.jsp");
		return SUCCESS;
	}
	//删除辅料-成份档案
	public void delAccIngredient() throws IOException {
		deleteDocSo(IConstant.MOD_ACCESSORIES,IConstant.DOC_INGREDIENTS);
	}
	
	/**
	* @Description: 保存-辅料-成份档案
	* @param 
	* @return void
	 */
	public void saveAccIngredient(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_ACCESSORIES,IConstant.DOC_INGREDIENTS);
	}
	/****成份-end*****/
	/****用途-start****/
	/**
	* @Description: 辅料-用途档案列表
	* @param 
	* @return void
	 */
	public String listAccUse(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and vdoctype='"+IConstant.DOC_USE+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and vdoctype='"+IConstant.DOC_USE+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(BaseDocSoVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(BaseDocSoVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listAccUse", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listAccUse");
		
		this.setUrl("../files/accessories/acc-list-use.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 辅料-用途档案
	 * @param @return
	 * @return String
	 */
	public String addAccUse(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocSoVO = iMyHibernateDao.selectBean(BaseDocSoVO.class," where id ="+id+ " and vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and vdoctype='"+IConstant.DOC_USE+"'");
		}
		request.setAttribute("docVarietyList",iMyHibernateDao.selectBeanList(DocVarietyVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"'"));
		this.setUrl("../files/accessories/acc-add-use.jsp");
		return SUCCESS;
	}
	//删除辅料-用途档案
	public void delAccUse() throws IOException {
		deleteDocSo(IConstant.MOD_ACCESSORIES,IConstant.DOC_USE);
	}
	
	/**
	* @Description: 保存-辅料-用途档案
	* @param 
	* @return void
	 */
	public void saveAccUse(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_ACCESSORIES,IConstant.DOC_USE);
	}
	/****用途-end****/
	
	/*********************************************辅料-end***************************************/
	
	/**********************************包装材料-start*****************************/
	/***品种-start***/
	/**
	* @Description: 包装材料-品名档案列表
	* @param 
	* @return void
	 */
	public String listPaccDocVariety(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0 and vmoduletype='"+IConstant.MOD_PACKING+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vmoduletype='"+IConstant.MOD_PACKING+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(DocVarietyVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(DocVarietyVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listPaccDocVariety", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listPaccDocVariety");
		
		this.setUrl("../files/packing/pac-list-docvariety.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 包装材料-品名档案
	 * @param @return
	 * @return String
	 */
	public String addPacDocVariety(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			docVarietyVO = iMyHibernateDao.selectBean(DocVarietyVO.class," where id ="+id+ " and vmoduletype='"+IConstant.MOD_PACKING+"'");
		}
		this.setUrl("../files/packing/pac-add-docvariety.jsp");
		return SUCCESS;
	}
	//删除包装材料-品名档案
	public void delPacDocVariety() throws IOException {
		deleteDocVariety(IConstant.MOD_PACKING);
	}
	
	/**
	 * 
	* @Description: 保存-包装材料-品名档案
	* @param 
	* @return void
	 */
	public void savePacDocVariety(){
		saveOrUpdateDocVariety(docVarietyVO,IConstant.MOD_PACKING);
	}
	/***品名-end***/
	
	/****材质-start****/
	/**
	* @Description: 包装材料-材质档案列表
	* @param 
	* @return void
	 */
	public String listPacIngredient(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0 and vmoduletype='"+IConstant.MOD_PACKING+"' and vdoctype='"+IConstant.DOC_MATERIAL+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vmoduletype='"+IConstant.MOD_PACKING+"' and vdoctype='"+IConstant.DOC_MATERIAL+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(BaseDocSoVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(BaseDocSoVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listPacIngredient", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listPacIngredient");
		
		this.setUrl("../files/packing/pac-list-ingredient.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 包装材料-材质档案
	 * @param @return
	 * @return String
	 */
	public String addPacIngredient(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocSoVO = iMyHibernateDao.selectBean(BaseDocSoVO.class," where id ="+id+ " and vmoduletype='"+IConstant.MOD_PACKING+"' and vdoctype='"+IConstant.DOC_MATERIAL+"'");
		}
		request.setAttribute("docVarietyList",iMyHibernateDao.selectBeanList(DocVarietyVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_PACKING+"'"));
		this.setUrl("../files/packing/pac-add-ingredient.jsp");
		return SUCCESS;
	}
	//删除包装材料-材质档案
	public void delPacIngredient() throws IOException {
		deleteDocSo(IConstant.MOD_PACKING,IConstant.DOC_MATERIAL);
	}
	
	/**
	* @Description: 保存-包装材料-材质档案
	* @param 
	* @return void
	 */
	public void savePacIngredient(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_PACKING,IConstant.DOC_MATERIAL);
	}
	/****材质-end*****/
	/****规格-start****/
	/**
	* @Description: 包装材料-规格档案列表
	* @param 
	* @return void
	 */
	public String listPacSpec(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0 and vmoduletype='"+IConstant.MOD_PACKING+"' and vdoctype='"+IConstant.DOC_SPEC+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vmoduletype='"+IConstant.MOD_PACKING+"' and vdoctype='"+IConstant.DOC_SPEC+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(BaseDocSoVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(BaseDocSoVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listPacSpec", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listPacSpec");
		
		this.setUrl("../files/packing/pac-list-spec.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 包装材料-规格档案
	 * @param @return
	 * @return String
	 */
	public String addPacSpec(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocSoVO = iMyHibernateDao.selectBean(BaseDocSoVO.class," where id ="+id+ " and vmoduletype='"+IConstant.MOD_PACKING+"' and vdoctype='"+IConstant.DOC_SPEC+"'");
		}
		request.setAttribute("docVarietyList",iMyHibernateDao.selectBeanList(DocVarietyVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_PACKING+"'"));
		this.setUrl("../files/packing/pac-add-spec.jsp");
		return SUCCESS;
	}
	//删除包装材料-规格档案
	public void delPacSpec() throws IOException {
		deleteDocSo(IConstant.MOD_PACKING,IConstant.DOC_SPEC);
	}
	
	/**
	* @Description: 保存-包装材料-规格档案
	* @param 
	* @return void
	 */
	public void savePacSpec(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_PACKING,IConstant.DOC_SPEC);
	}
	/****规格-end****/
	/****************************************包装材料-end*******************************/
	
	/****************************************特殊档案-start*****************************/
	/***类别-start***/
	/**
	* @Description: 特殊档案-类别档案列表
	* @param 
	* @return void
	 */
	public String listSpeDocVariety(){
		this.setUrl("../files/special/spe-list-docvariety.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 获得特殊档案Json串
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getSpecialTypeJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//输入查询
		String search_input = request.getParameter("searchinput");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ");
		sql.append(" d.id,d.vcode,d.vname,d.vmemo,p.vname as proclassName");
		sql.append(" from fz_docvariety d");
		sql.append(" left join fz_tem_proclass p");
		sql.append(" on d.proclassid=p.id");
		sql.append(" where vmoduletype='"+IConstant.MOD_SPECIAL+"'");
		
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and d.vname like '%"+search_input+"%' or p.vname like '%"+search_input+"%'");
		}
		
		sql.append(" order by p.vname desc");
		
		Integer total = getCountBySQL(sql.toString());
		List<DocVarietyVO> speTypelist = findSpeTypePage(sql.toString(),dgpage,rows);
		List<DocVarietyVO> list = new ArrayList<DocVarietyVO>();
		
		if(speTypelist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(speTypelist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				DocVarietyVO dto = new DocVarietyVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVmemo(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				dto.setProclassName(CommUtil.isNullOrEm(arry[4]) ? null : arry[4].toString());
				
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
	public List<DocVarietyVO> findSpeTypePage(String sql, Integer dgpage,
			Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<DocVarietyVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
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
	 * @Description: 特殊档案-类别档案
	 * @param @return
	 * @return String
	 */
	public String addSpeDocVariety(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			docVarietyVO = iMyHibernateDao.selectBean(DocVarietyVO.class," where id ="+id+ " and vmoduletype='"+IConstant.MOD_SPECIAL+"'");
		}
		//品类
		request.setAttribute("proclasslist",iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0"));
		this.setUrl("../files/special/spe-add-docvariety.jsp");
		return SUCCESS;
	}
	//删除特殊档案-类别档案
	public void delSpeDocVariety() throws IOException {
		deleteDocVariety(IConstant.MOD_SPECIAL);
	}
	
	/**
	 * 
	* @Description: 保存-特殊档案-类别档案
	* @param 
	* @return void
	 */
	public void saveSpeDocVariety(){
		saveOrUpdateDocVariety(docVarietyVO,IConstant.MOD_SPECIAL);
	}
	/***类别-end***/
	/****************************************特殊档案-end*****************************/
	
	/**********************************外购商品-start*****************************/
	/***品种-start***/
	/**
	* @Description: 外购商品-品种档案列表
	* @param 
	* @return void
	 */
	public String listOutDocVariety(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0 and vmoduletype='"+IConstant.MOD_OUTSOURCE+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vmoduletype='"+IConstant.MOD_OUTSOURCE+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(DocVarietyVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(DocVarietyVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listOutDocVariety", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listOutDocVariety");
		
		this.setUrl("../files/outsource/out-list-docvariety.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 外购商品-品种档案
	 * @param @return
	 * @return String
	 */
	public String addOutDocVariety(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			docVarietyVO = iMyHibernateDao.selectBean(DocVarietyVO.class," where id ="+id+ " and vmoduletype='"+IConstant.MOD_OUTSOURCE+"'");
		}
		this.setUrl("../files/outsource/out-add-docvariety.jsp");
		return SUCCESS;
	}
	//删除外购商品-品种档案
	public void delOutDocVariety() throws IOException {
		deleteDocVariety(IConstant.MOD_OUTSOURCE);
	}
	
	/**
	 * 
	* @Description: 保存-外购商品-品种档案
	* @param 
	* @return void
	 */
	public void saveOutDocVariety(){
		saveOrUpdateDocVariety(docVarietyVO,IConstant.MOD_OUTSOURCE);
	}
	/***品种-end***/
	
	/****成份-start****/
	/**
	* @Description: 外购商品-成份档案列表
	* @param 
	* @return void
	 */
	public String listOutIngredient(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0 and vmoduletype='"+IConstant.MOD_OUTSOURCE+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"' order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 and vmoduletype='"+IConstant.MOD_OUTSOURCE+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(BaseDocSoVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(BaseDocSoVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "basedoc!listOutIngredient", "共有" + total + "条记录"));
		request.setAttribute("url", "basedoc!listOutIngredient");
		
		this.setUrl("../files/outsource/out-list-ingredient.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 外购商品-成份档案
	 * @param @return
	 * @return String
	 */
	public String addOutIngredient(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocSoVO = iMyHibernateDao.selectBean(BaseDocSoVO.class," where id ="+id+ " and vmoduletype='"+IConstant.MOD_OUTSOURCE+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		}
		request.setAttribute("docVarietyList",iMyHibernateDao.selectBeanList(DocVarietyVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_OUTSOURCE+"'"));
		this.setUrl("../files/outsource/out-add-ingredient.jsp");
		return SUCCESS;
	}
	//删除外购商品-成份档案
	public void delOutIngredient() throws IOException {
		deleteDocSo(IConstant.MOD_OUTSOURCE,IConstant.DOC_INGREDIENTS);
	}
	
	/**
	* @Description: 保存-外购商品-成份档案
	* @param 
	* @return void
	 */
	public void saveOutIngredient(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_OUTSOURCE,IConstant.DOC_INGREDIENTS);
	}
	/****成份-end*****/
	
	/**********************************外购商品-end*****************************/
	
	/**基础档案-start**/
	/**
	 * 保存
	 */
	public void saveOrUpdateDoc(BaseDocVO vo,String doctype){
		try {
			Integer id = vo.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "保存失败";
			if(id != null){
				iMyHibernateDao.updateBean(vo);
				msg = "更新成功";
			}else{
				//标识-档案类别
				vo.setVdoctype(doctype);
				iMyHibernateDao.insertBean(vo);
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
	 * @Description: 删除
	 * @param @param doctype
	 * @param @throws IOException
	 * @return void
	 */
	public void deleteDoc(String doctype) throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//删除引用校验-start
		StringBuffer wh = new StringBuffer();
		wh.append(" patternid="+ id +" or colourid="+id +" or brandsid="+id);
		Integer count = iHibernateDAO.getCountByHQL(AuxiliaryVO.class, wh.toString());
		if(count <= 0){
			BaseDocVO baseDocVO = iMyHibernateDao.selectBean(BaseDocVO.class," where id= "+id + " and vdoctype='"+doctype+"'");
			iMyHibernateDao.deleteBean(baseDocVO);
			out.print("删除成功");
		}else{
			out.print("已被引用，不可删除!");
		}
		//删除引用校验-end
	}
	/**
	 * @Description: 删除
	 * @param @param vo
	 * @param @param moduletype
	 * @param @param doctype
	 * @return void
	 */
	public void saveOrUpdateDocSo(BaseDocSoVO vo,String moduletype,String doctype){
		try {
			Integer id = vo.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "保存失败";
			if(id != null){
				iMyHibernateDao.updateBean(vo);
				msg = "更新成功";
			}else{
				//模块
				vo.setVmoduletype(moduletype);
				//标识-档案类别
				vo.setVdoctype(doctype);
				iMyHibernateDao.insertBean(vo);
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
	 * @Description: 删除
	 * @param @param moduletype
	 * @param @param doctype
	 * @param @throws IOException
	 * @return void
	 */
	public void deleteDocSo(String moduletype,String doctype) throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//删除引用校验-start
		StringBuffer wh = new StringBuffer();
		wh.append(" ingredientid="+ id +" or useid="+id +" or specid="+id);
		Integer count = iHibernateDAO.getCountByHQL(AuxiliaryVO.class, wh.toString());
		if(count <= 0){
			BaseDocSoVO baseDocSoVO = iMyHibernateDao.selectBean(BaseDocSoVO.class," where id= "+id + " and vmoduletype='"+moduletype+"' and vdoctype='"+doctype+"'");
			iMyHibernateDao.deleteBean(baseDocSoVO);
			out.print("删除成功");
		}else{
			out.print("已被引用，不可删除!");
		}
		//删除引用校验-end
	}
	/**
	 * @Description: 公共保存
	 * @param @param vo
	 * @param @param moduletype
	 * @return void
	 */
	public void saveOrUpdateDocVariety(DocVarietyVO vo,String moduletype){
		try {
			Integer id = vo.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "保存失败";
			if(id != null){
				iMyHibernateDao.updateBean(vo);
				msg = "更新成功";
			}else{
				//模块
				vo.setVmoduletype(moduletype);
				iMyHibernateDao.insertBean(vo);
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
	 * @Description: 品种、品名删除
	 * @param @param moduletype
	 * @param @throws IOException
	 * @return void
	 */
	public void deleteDocVariety(String moduletype) throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//删除引用校验-start
		StringBuffer wh = new StringBuffer();
		wh.append(" docvarietyid="+ id);
		Integer count_p = iHibernateDAO.getCountByHQL(AuxiliaryVO.class, wh.toString());
		Integer count_d = iHibernateDAO.getCountByHQL(BaseDocSoVO.class, " varietyid="+id);
		
		if(count_p <= 0 && count_d <= 0){
			DocVarietyVO docVarietyVO = iMyHibernateDao.selectBean(DocVarietyVO.class," where id= "+id + " and vmoduletype='"+moduletype+"'");
			iMyHibernateDao.deleteBean(docVarietyVO);
			out.print("删除成功");
		}else{
			out.print("已被引用，不可删除!");
		}
		//删除引用校验-end
	}
	/**基础档案-end**/
	
	public IHibernateDAO getiHibernateDAO() {
		return iHibernateDAO;
	}
	public BaseDocVO getBaseDocVO() {
		return baseDocVO;
	}
	
	public DocVarietyVO getDocVarietyVO() {
		return docVarietyVO;
	}

	public void setDocVarietyVO(DocVarietyVO docVarietyVO) {
		this.docVarietyVO = docVarietyVO;
	}

	public BaseDocSoVO getBaseDocSoVO() {
		return baseDocSoVO;
	}

	public void setBaseDocSoVO(BaseDocSoVO baseDocSoVO) {
		this.baseDocSoVO = baseDocSoVO;
	}

	public void setBaseDocVO(BaseDocVO baseDocVO) {
		this.baseDocVO = baseDocVO;
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
}
