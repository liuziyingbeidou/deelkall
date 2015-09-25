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
	
	/**����-��ʼ**/
	/**
	* @Description: �����б�
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
				currentpage, "basedoc!listPattern", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listPattern");
		
		this.setUrl("../files/basedoc/listpattern.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ��������
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
	//ɾ������
	public void delPattern() throws IOException {
		deleteDoc(IConstant.DOC_PATTERN);
	}
	
	/**
	 * 
	* @Description: ����-����
	* @param 
	* @return void
	 */
	public void savePattern(){
		saveOrUpdateDoc(baseDocVO,IConstant.DOC_PATTERN);
	}
	/**����-����**/
	/**ɫϵ-��ʼ**/
	/**
	* @Description: ɫϵ�б�
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
				currentpage, "basedoc!listColour", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listColour");
		
		this.setUrl("../files/basedoc/listcolour.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ����ɫϵ
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
	//ɾ��ɫϵ
	public void delColour() throws IOException {
		deleteDoc(IConstant.DOC_COLOUR);
	}
	
	/**
	 * 
	* @Description: ����-ɫϵ
	* @param 
	* @return void
	 */
	public void saveColour(){
		saveOrUpdateDoc(baseDocVO,IConstant.DOC_COLOUR);
	}
	/**ɫϵ-����**/
	/**Ʒ��-��ʼ**/
	/**
	* @Description: Ʒ���б�
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
				currentpage, "basedoc!listBrand", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listBrand");
		
		this.setUrl("../files/basedoc/listbrand.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ����Ʒ��
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
	//ɾ��Ʒ��
	public void delBrand() throws IOException {
		deleteDoc(IConstant.DOC_BRAND);
	}
	
	/**
	 * 
	* @Description: ����-Ʒ��
	* @param 
	* @return void
	 */
	public void saveBrand(){
		saveOrUpdateDoc(baseDocVO,IConstant.DOC_BRAND);
	}
	/**Ʒ��-����**/
	/**����Ʒ��-��ʼ**/
	/**
	* @Description: Ʒ���б�
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
				currentpage, "basedoc!listFarBrand", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listFarBrand");
		
		this.setUrl("../files/fabric/list-far-brand.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ����Ʒ��
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
	//ɾ��Ʒ��
	public void delFarBrand() throws IOException {
		deleteDoc(IConstant.DOC_FAR_BRAND);
	}
	
	/**
	 * 
	* @Description: ����-Ʒ��
	* @param 
	* @return void
	 */
	public void saveFarBrand(){
		saveOrUpdateDoc(baseDocVO,IConstant.DOC_FAR_BRAND);
	}
	/**����Ʒ��-����**/
	/**����-��ʼ**/
	/**
	* @Description: �����б�
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
				currentpage, "basedoc!listProcess", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listProcess");
		
		this.setUrl("../files/basedoc/listprocess.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ��������
	 * @param @return
	 * @return String
	 */
	public String addProcess(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			baseDocVO = iMyHibernateDao.selectBean(BaseDocVO.class," where id ="+id+ " and vdoctype='"+IConstant.DOC_TECHNOLOGY+"'");
		}
		//Ʒ��
		request.setAttribute("proclasslist",iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0"));
		this.setUrl("../files/basedoc/addprocess.jsp");
		return SUCCESS;
	}
	//ɾ������
	public void delProcess() throws IOException {
		deleteDoc(IConstant.DOC_TECHNOLOGY);
	}
	
	/**
	 * 
	* @Description: ����-����
	* @param 
	* @return void
	 */
	public void saveProcess(){
		saveOrUpdateDoc(baseDocVO,IConstant.DOC_TECHNOLOGY);
	}
	/**����-����**/
	
	/**����-�ɷݵ���-start**/
	/**
	* @Description: ����-�ɷݵ����б�
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
				currentpage, "basedoc!listFarIngredient", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listFarIngredient");
		
		this.setUrl("../files/fabric/far-list-ingredient.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ����-�ɷݵ���
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
	//ɾ������-�ɷݵ���
	public void delFarIngredient() throws IOException {
		deleteDocSo(IConstant.MOD_FABRIC,IConstant.DOC_INGREDIENTS);
	}
	
	/**
	 * 
	* @Description: ����-����-�ɷݵ���
	* @param 
	* @return void
	 */
	public void saveFarIngredient(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_FABRIC,IConstant.DOC_INGREDIENTS);
	}
	/**����-�ɷݵ���-end**/
	
	
	/**����-�ɷݵ���-start**/
	/**
	* @Description: ����-�ɷݵ����б�
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
				currentpage, "basedoc!listLinIngredient", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listLinIngredient");
		
		this.setUrl("../files/lining/lin-list-ingredient.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ����-�ɷݵ���
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
	//ɾ������-�ɷݵ���
	public void delLinIngredient() throws IOException {
		deleteDocSo(IConstant.MOD_LINING,IConstant.DOC_INGREDIENTS);
	}
	
	/**
	 * 
	* @Description: ����-����-�ɷݵ���
	* @param 
	* @return void
	 */
	public void saveLinIngredient(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_LINING,IConstant.DOC_INGREDIENTS);
	}
	/**����-�ɷݵ���-end**/
	
	/**********************************����-start*****************************/
	/***Ʒ��-start***/
	/**
	* @Description: ����-Ʒ�ֵ����б�
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
				currentpage, "basedoc!listAccDocVariety", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listAccDocVariety");
		
		this.setUrl("../files/accessories/acc-list-docvariety.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ����-Ʒ�ֵ���
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
	//ɾ������-Ʒ�ֵ���
	public void delAccDocVariety() throws IOException {
		deleteDocVariety(IConstant.MOD_ACCESSORIES);
	}
	
	/**
	 * 
	* @Description: ����-����-Ʒ�ֵ���
	* @param 
	* @return void
	 */
	public void saveAccDocVariety(){
		saveOrUpdateDocVariety(docVarietyVO,IConstant.MOD_ACCESSORIES);
	}
	/***Ʒ��-end***/
	
	/****�ɷ�-start****/
	/**
	* @Description: ����-�ɷݵ����б�
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
				currentpage, "basedoc!listAccIngredient", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listAccIngredient");
		
		this.setUrl("../files/accessories/acc-list-ingredient.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ����-�ɷݵ���
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
	//ɾ������-�ɷݵ���
	public void delAccIngredient() throws IOException {
		deleteDocSo(IConstant.MOD_ACCESSORIES,IConstant.DOC_INGREDIENTS);
	}
	
	/**
	* @Description: ����-����-�ɷݵ���
	* @param 
	* @return void
	 */
	public void saveAccIngredient(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_ACCESSORIES,IConstant.DOC_INGREDIENTS);
	}
	/****�ɷ�-end*****/
	/****��;-start****/
	/**
	* @Description: ����-��;�����б�
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
				currentpage, "basedoc!listAccUse", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listAccUse");
		
		this.setUrl("../files/accessories/acc-list-use.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ����-��;����
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
	//ɾ������-��;����
	public void delAccUse() throws IOException {
		deleteDocSo(IConstant.MOD_ACCESSORIES,IConstant.DOC_USE);
	}
	
	/**
	* @Description: ����-����-��;����
	* @param 
	* @return void
	 */
	public void saveAccUse(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_ACCESSORIES,IConstant.DOC_USE);
	}
	/****��;-end****/
	
	/*********************************************����-end***************************************/
	
	/**********************************��װ����-start*****************************/
	/***Ʒ��-start***/
	/**
	* @Description: ��װ����-Ʒ�������б�
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
				currentpage, "basedoc!listPaccDocVariety", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listPaccDocVariety");
		
		this.setUrl("../files/packing/pac-list-docvariety.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ��װ����-Ʒ������
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
	//ɾ����װ����-Ʒ������
	public void delPacDocVariety() throws IOException {
		deleteDocVariety(IConstant.MOD_PACKING);
	}
	
	/**
	 * 
	* @Description: ����-��װ����-Ʒ������
	* @param 
	* @return void
	 */
	public void savePacDocVariety(){
		saveOrUpdateDocVariety(docVarietyVO,IConstant.MOD_PACKING);
	}
	/***Ʒ��-end***/
	
	/****����-start****/
	/**
	* @Description: ��װ����-���ʵ����б�
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
				currentpage, "basedoc!listPacIngredient", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listPacIngredient");
		
		this.setUrl("../files/packing/pac-list-ingredient.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ��װ����-���ʵ���
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
	//ɾ����װ����-���ʵ���
	public void delPacIngredient() throws IOException {
		deleteDocSo(IConstant.MOD_PACKING,IConstant.DOC_MATERIAL);
	}
	
	/**
	* @Description: ����-��װ����-���ʵ���
	* @param 
	* @return void
	 */
	public void savePacIngredient(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_PACKING,IConstant.DOC_MATERIAL);
	}
	/****����-end*****/
	/****���-start****/
	/**
	* @Description: ��װ����-��񵵰��б�
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
				currentpage, "basedoc!listPacSpec", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listPacSpec");
		
		this.setUrl("../files/packing/pac-list-spec.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ��װ����-��񵵰�
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
	//ɾ����װ����-��񵵰�
	public void delPacSpec() throws IOException {
		deleteDocSo(IConstant.MOD_PACKING,IConstant.DOC_SPEC);
	}
	
	/**
	* @Description: ����-��װ����-��񵵰�
	* @param 
	* @return void
	 */
	public void savePacSpec(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_PACKING,IConstant.DOC_SPEC);
	}
	/****���-end****/
	/****************************************��װ����-end*******************************/
	
	/****************************************���⵵��-start*****************************/
	/***���-start***/
	/**
	* @Description: ���⵵��-��𵵰��б�
	* @param 
	* @return void
	 */
	public String listSpeDocVariety(){
		this.setUrl("../files/special/spe-list-docvariety.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ������⵵��Json��
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getSpecialTypeJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//�����ѯ
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
			 * ����Զ���SQL����List<E>
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
	* @Description: ��ҳ
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
	 * ����sql���count
	 * @param sql
	 * @return
	 */
	public Integer getCountBySQL(String sql){
		return iHibernateDAO.getCountBySQL("select count(*) from ("+sql+") tb", null);
	}
	
	/**
	 * @Description: ���⵵��-��𵵰�
	 * @param @return
	 * @return String
	 */
	public String addSpeDocVariety(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			docVarietyVO = iMyHibernateDao.selectBean(DocVarietyVO.class," where id ="+id+ " and vmoduletype='"+IConstant.MOD_SPECIAL+"'");
		}
		//Ʒ��
		request.setAttribute("proclasslist",iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0"));
		this.setUrl("../files/special/spe-add-docvariety.jsp");
		return SUCCESS;
	}
	//ɾ�����⵵��-��𵵰�
	public void delSpeDocVariety() throws IOException {
		deleteDocVariety(IConstant.MOD_SPECIAL);
	}
	
	/**
	 * 
	* @Description: ����-���⵵��-��𵵰�
	* @param 
	* @return void
	 */
	public void saveSpeDocVariety(){
		saveOrUpdateDocVariety(docVarietyVO,IConstant.MOD_SPECIAL);
	}
	/***���-end***/
	/****************************************���⵵��-end*****************************/
	
	/**********************************�⹺��Ʒ-start*****************************/
	/***Ʒ��-start***/
	/**
	* @Description: �⹺��Ʒ-Ʒ�ֵ����б�
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
				currentpage, "basedoc!listOutDocVariety", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listOutDocVariety");
		
		this.setUrl("../files/outsource/out-list-docvariety.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: �⹺��Ʒ-Ʒ�ֵ���
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
	//ɾ���⹺��Ʒ-Ʒ�ֵ���
	public void delOutDocVariety() throws IOException {
		deleteDocVariety(IConstant.MOD_OUTSOURCE);
	}
	
	/**
	 * 
	* @Description: ����-�⹺��Ʒ-Ʒ�ֵ���
	* @param 
	* @return void
	 */
	public void saveOutDocVariety(){
		saveOrUpdateDocVariety(docVarietyVO,IConstant.MOD_OUTSOURCE);
	}
	/***Ʒ��-end***/
	
	/****�ɷ�-start****/
	/**
	* @Description: �⹺��Ʒ-�ɷݵ����б�
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
				currentpage, "basedoc!listOutIngredient", "����" + total + "����¼"));
		request.setAttribute("url", "basedoc!listOutIngredient");
		
		this.setUrl("../files/outsource/out-list-ingredient.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: �⹺��Ʒ-�ɷݵ���
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
	//ɾ���⹺��Ʒ-�ɷݵ���
	public void delOutIngredient() throws IOException {
		deleteDocSo(IConstant.MOD_OUTSOURCE,IConstant.DOC_INGREDIENTS);
	}
	
	/**
	* @Description: ����-�⹺��Ʒ-�ɷݵ���
	* @param 
	* @return void
	 */
	public void saveOutIngredient(){
		saveOrUpdateDocSo(baseDocSoVO,IConstant.MOD_OUTSOURCE,IConstant.DOC_INGREDIENTS);
	}
	/****�ɷ�-end*****/
	
	/**********************************�⹺��Ʒ-end*****************************/
	
	/**��������-start**/
	/**
	 * ����
	 */
	public void saveOrUpdateDoc(BaseDocVO vo,String doctype){
		try {
			Integer id = vo.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			if(id != null){
				iMyHibernateDao.updateBean(vo);
				msg = "���³ɹ�";
			}else{
				//��ʶ-�������
				vo.setVdoctype(doctype);
				iMyHibernateDao.insertBean(vo);
				msg = "�����ɹ�";
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: ɾ��
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
		
		//ɾ������У��-start
		StringBuffer wh = new StringBuffer();
		wh.append(" patternid="+ id +" or colourid="+id +" or brandsid="+id);
		Integer count = iHibernateDAO.getCountByHQL(AuxiliaryVO.class, wh.toString());
		if(count <= 0){
			BaseDocVO baseDocVO = iMyHibernateDao.selectBean(BaseDocVO.class," where id= "+id + " and vdoctype='"+doctype+"'");
			iMyHibernateDao.deleteBean(baseDocVO);
			out.print("ɾ���ɹ�");
		}else{
			out.print("�ѱ����ã�����ɾ��!");
		}
		//ɾ������У��-end
	}
	/**
	 * @Description: ɾ��
	 * @param @param vo
	 * @param @param moduletype
	 * @param @param doctype
	 * @return void
	 */
	public void saveOrUpdateDocSo(BaseDocSoVO vo,String moduletype,String doctype){
		try {
			Integer id = vo.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			if(id != null){
				iMyHibernateDao.updateBean(vo);
				msg = "���³ɹ�";
			}else{
				//ģ��
				vo.setVmoduletype(moduletype);
				//��ʶ-�������
				vo.setVdoctype(doctype);
				iMyHibernateDao.insertBean(vo);
				msg = "�����ɹ�";
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: ɾ��
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
		
		//ɾ������У��-start
		StringBuffer wh = new StringBuffer();
		wh.append(" ingredientid="+ id +" or useid="+id +" or specid="+id);
		Integer count = iHibernateDAO.getCountByHQL(AuxiliaryVO.class, wh.toString());
		if(count <= 0){
			BaseDocSoVO baseDocSoVO = iMyHibernateDao.selectBean(BaseDocSoVO.class," where id= "+id + " and vmoduletype='"+moduletype+"' and vdoctype='"+doctype+"'");
			iMyHibernateDao.deleteBean(baseDocSoVO);
			out.print("ɾ���ɹ�");
		}else{
			out.print("�ѱ����ã�����ɾ��!");
		}
		//ɾ������У��-end
	}
	/**
	 * @Description: ��������
	 * @param @param vo
	 * @param @param moduletype
	 * @return void
	 */
	public void saveOrUpdateDocVariety(DocVarietyVO vo,String moduletype){
		try {
			Integer id = vo.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			if(id != null){
				iMyHibernateDao.updateBean(vo);
				msg = "���³ɹ�";
			}else{
				//ģ��
				vo.setVmoduletype(moduletype);
				iMyHibernateDao.insertBean(vo);
				msg = "�����ɹ�";
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: Ʒ�֡�Ʒ��ɾ��
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
		
		//ɾ������У��-start
		StringBuffer wh = new StringBuffer();
		wh.append(" docvarietyid="+ id);
		Integer count_p = iHibernateDAO.getCountByHQL(AuxiliaryVO.class, wh.toString());
		Integer count_d = iHibernateDAO.getCountByHQL(BaseDocSoVO.class, " varietyid="+id);
		
		if(count_p <= 0 && count_d <= 0){
			DocVarietyVO docVarietyVO = iMyHibernateDao.selectBean(DocVarietyVO.class," where id= "+id + " and vmoduletype='"+moduletype+"'");
			iMyHibernateDao.deleteBean(docVarietyVO);
			out.print("ɾ���ɹ�");
		}else{
			out.print("�ѱ����ã�����ɾ��!");
		}
		//ɾ������У��-end
	}
	/**��������-end**/
	
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
