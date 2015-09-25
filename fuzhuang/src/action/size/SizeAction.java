package action.size;


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
import model.basedoc.DocVarietyVO;
import model.proclass.ProclassVO;
import model.size.LtinfoVO;
import model.size.SizeDocTypeVO;
import model.size.SizeDocVO;
import model.size.StinfoBVO;
import model.size.StinfoVO;
import model.special.SpecialVO;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import util.CommUtil;
import util.ObjectToJSON;
import util.Pager;
import utils.JsonUtils;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

public class SizeAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private LtinfoVO ltinfoVO;
	private SizeDocVO sizeDocVO;
	private StinfoVO stinfoVO;
	private SizeDocTypeVO sizeDocTypeVO;
	//�����������
	private String docname;
	private String doccode;
	
	/**������������-start**/
	
	/**��������-start**/
	/**
	* @Description: ��������б�
	* @param 
	* @return void
	 */
	public String listSizeDocType(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0 order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(SizeDocTypeVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(SizeDocTypeVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "size!listSizeDocType", "����" + total + "����¼"));
		request.setAttribute("url", "size!listSizeDocType");
		
		this.setUrl("../files/size/size-list-doctype.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: �������
	 * @param @return
	 * @return String
	 */
	public String addSizeDocType(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			sizeDocTypeVO = iMyHibernateDao.selectBean(SizeDocTypeVO.class," where id ="+id);
		}
		this.setUrl("../files/size/size-add-doctype.jsp");
		return SUCCESS;
	}
	//ɾ���������
	public void delSizeDocType() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//ɾ������У��-start
		StringBuffer wh = new StringBuffer();
		wh.append(" docvarietyid="+ id);
		Integer count_p = iHibernateDAO.getCountByHQL(SizeDocVO.class, wh.toString());
		
		if(count_p <= 0){
			SizeDocTypeVO sizeDocTypeVO = iMyHibernateDao.selectBean(SizeDocTypeVO.class," where id= "+id);
			iMyHibernateDao.deleteBean(sizeDocTypeVO);
			out.print("ɾ���ɹ�");
		}else{
			out.print("�ѱ����ã�����ɾ��!");
		}
		//ɾ������У��-end
	}
	
	/**
	 * 
	* @Description: ����-�������
	* @param 
	* @return void
	 */
	public void saveSizeDocType(){
		try {
			Integer id = sizeDocTypeVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			if(id != null){
				iMyHibernateDao.updateBean(sizeDocTypeVO);
				msg = "���³ɹ�";
			}else{
				//ģ��
				iMyHibernateDao.insertBean(sizeDocTypeVO);
				msg = "�����ɹ�";
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**��������-end**/
	
	/**
	 * ���û�����������
	 */
	private void setSizeDocTitle(String doctype){
		if(IConstant.SIZE_BASEB.equals(doctype)){
			//��������
			this.setDoccode(IConstant.SIZE_BASEB);
			this.setDocname(IConstant.SIZE_BASEB_TITLE);
		}else if(IConstant.SIZE_NEEDLE.equals(doctype)){
			//����
			this.setDoccode(IConstant.SIZE_NEEDLE);
			this.setDocname(IConstant.SIZE_NEEDLE_TITLE);
		}else if(IConstant.SIZE_KICKERX.equals(doctype)){
			//��б
			this.setDoccode(IConstant.SIZE_KICKERX);
			this.setDocname(IConstant.SIZE_KICKERX_TITLE);
		}else if(IConstant.SIZE_KICKERC.equals(doctype)){
			//���
			this.setDoccode(IConstant.SIZE_KICKERC);
			this.setDocname(IConstant.SIZE_KICKERC_TITLE);
		}else if(IConstant.SIZE_BACK.equals(doctype)){
			//��
			this.setDoccode(IConstant.SIZE_BACK);
			this.setDocname(IConstant.SIZE_BACK_TITLE);
		}else if(IConstant.SIZE_CHEST.equals(doctype)){
			//��
			this.setDoccode(IConstant.SIZE_CHEST);
			this.setDocname(IConstant.SIZE_CHEST_TITLE);
		}else if(IConstant.SIZE_ABDOMEN.equals(doctype)){
			//��
			this.setDoccode(IConstant.SIZE_ABDOMEN);
			this.setDocname(IConstant.SIZE_ABDOMEN_TITLE);
		}else if(IConstant.SIZE_WAISTLINE.equals(doctype)){
			//��
			this.setDoccode(IConstant.SIZE_WAISTLINE);
			this.setDocname(IConstant.SIZE_WAISTLINE_TITLE);
		}else if(IConstant.SIZE_BELT.equals(doctype)){
			//���
			this.setDoccode(IConstant.SIZE_BELT);
			this.setDocname(IConstant.SIZE_BELT_TITLE);
		}else if(IConstant.SIZE_BUTT.equals(doctype)){
			//��
			this.setDoccode(IConstant.SIZE_BUTT);
			this.setDocname(IConstant.SIZE_BUTT_TITLE);
		}else if(IConstant.SIZE_LEG.equals(doctype)){
			//�ȳ�����
			this.setDoccode(IConstant.SIZE_LEG);
			this.setDocname(IConstant.SIZE_LEG_TITLE);
		}else if(IConstant.SIZE_LOOSEDEG.equals(doctype)){
			//����
			this.setDoccode(IConstant.SIZE_LOOSEDEG);
			this.setDocname(IConstant.SIZE_LOOSEDEG_TITLE);
		}
	}
	
	/**
	 * �б����
	 */
	public String listSizeDoc(){
		//HttpServletRequest request = ServletActionContext.getRequest();
		//��������
		//String doctype = request.getParameter("doctype");
		//setSizeDocTitle(doctype);
		this.setUrl("../files/size/size-list-doc.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ���������������Json��
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getSizeDocJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//�����ѯ
		String search_input = request.getParameter("searchinput");
		//�Ƿ���ͼ
		String bisupload = request.getParameter("bisupload");
		/*//�Ƿ�Ĭ������
		String bisdefault = request.getParameter("bisdefault");
		//��������
		String doctype = request.getParameter("doctype");*/
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" d.id,d.vcode,d.vname,d.bisupload,t.vname as vdoctype");
		sql.append(" from fz_size_doc d");
		sql.append(" left join fz_size_doc_type t");
		sql.append(" on d.doctypeid=t.id");

		if(search_input != null && !"".equals(search_input)){
			sql.append(" and d.vname like '"+search_input+"%' and t.vname like '%"+search_input+"%'");
		}
		if(bisupload != null && !"".equals(bisupload)){
			if("all".equals(bisupload)){
				sql.append(" and d.bisupload in(1,0)");
			}else if("yes".equals(bisupload)){
				sql.append(" and d.bisupload = 1");
			}else if("no".equals(bisupload)){
				sql.append(" and d.bisupload = 0");
			}
		}
		
		/*if(bisdefault != null && !"".equals(bisdefault)){
			if("-1".equals(bisdefault)){
				sql.append(" and (bisdefault in(1,0) or bisdefault is null)");
			}else if("1".equals(bisdefault)){
				sql.append(" and bisdefault = 1");
			}
		}
		
		if(doctype != null && !"".equals(doctype)){
			sql.append(" and vdoctype='"+doctype+"'");
		}*/
		Integer total = getCountBySQL(sql.toString());
		List<SizeDocVO> sizedoclist = findSizeDocPage(sql.toString(),dgpage,rows);
		List<SizeDocVO> list = new ArrayList<SizeDocVO>();
		if(sizedoclist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(sizedoclist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SizeDocVO dto = new SizeDocVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				if(!CommUtil.isNullOrEm(arry[3])){
					dto.setBisupload(Integer.valueOf(arry[3].toString()));
					if("1".equals(arry[3].toString())){//��ͼ
						dto.setVdef1("��");
					}else{
						dto.setVdef1("��");
					}
				}
				dto.setVdoctype(CommUtil.isNullOrEm(arry[4]) ? null : arry[4].toString());
				list.add(dto);
			}
			
		}
		/*List<SizeDocVO> list = new ArrayList<SizeDocVO>();
		if(sizedoclist != null && sizedoclist.size() > 0){
			for(int i = 0; i < sizedoclist.size(); i++){
				SizeDocVO dto = sizedoclist.get(i);
				if(!CommUtil.isNullOrEm(dto.getBisupload())){
					if("1".equals(dto.getBisupload()+"")){//��ͼ
						dto.setVdef1("��");
					}else{
						dto.setVdef1("��");
					}
				}
				list.add(dto);
			}
		}*/
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("total", total);
		jsonmap.put("rows", list);
		renderJson(jsonmap);
	}
	
	/**
	 * @Description: �����б���Ϣ
	 * @param @param sql
	 * @param @param dgpage
	 * @param @param rows
	 * @param @return
	 * @return List<SizeDocVO>
	 */
	public List<SizeDocVO> findSizeDocPage(String sql, Integer dgpage,
			Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<SizeDocVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
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
	* @Description: ��������
	* @param @return
	* @return String
	 */
	public String addSizeDoc(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		//��������
		String doctype = request.getParameter("doctype");
		setSizeDocTitle(doctype);
		if(id != null){
			sizeDocVO = iMyHibernateDao.selectBean(SizeDocVO.class," where id ="+id);
		}
		//�������
		request.setAttribute("doctypelist",iMyHibernateDao.selectBeanList(SizeDocTypeVO.class," where ifnull(dr,0)=0"));
		this.setUrl("../files/size/size-add-doc.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ��ñ�׼������ϢJson��
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getStandardInfoJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//�����ѯ
		String stinfoid = request.getParameter("stinfoId");
		
		StringBuffer sql = new StringBuffer();
		
		if(stinfoid != null && !"".equals(stinfoid)){
			sql.append(" stinfoId = "+stinfoid);
		}else{
			sql.append(" 1=2");
		}
		
		Integer total = iHibernateDAO.getCountByHQL(StinfoBVO.class, sql.toString());
		List<StinfoBVO> list = (List<StinfoBVO>)iHibernateDAO.findAll(StinfoBVO.class, sql.toString());
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("total", total);
		jsonmap.put("rows", list);
		renderJson(jsonmap);
	}
	
	/**
	 * @Description: ����
	 * @return void
	 */
	public void saveSizeDoc(){
		try {
			Integer id = sizeDocVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			String furl = sizeDocVO.getVfileupload();
			if(!CommUtil.isNullOrEm(furl)){
				sizeDocVO.setBisupload(1);
			}else{
				sizeDocVO.setBisupload(0);
				sizeDocVO.setVfileupload(null);
			}
			if(id != null){
				iMyHibernateDao.updateBean(sizeDocVO);
				msg = "���³ɹ�";
			}else{
				sizeDocVO.setVdoctype(doccode);
				iMyHibernateDao.insertBean(sizeDocVO);
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
	* @param @throws IOException
	* @return void
	 */
	public void delSizeDoc() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		SizeDocVO sizeDocVO = iMyHibernateDao.selectBean(SizeDocVO.class," where id= "+id);
		iMyHibernateDao.deleteBean(sizeDocVO);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("ɾ���ɹ�");
	}
	/**������������-end**/
	
	
	/*********************************��׼����-start**************************/
	/**
	 * �б����
	 */
	public String listSizeStandard(){
		
		this.setUrl("../files/size/size-list-standard.jsp");
		return SUCCESS;
	}

	/**
	 * @Description: ͨ��Ʒ���ö�Ӧ���� 
	 * @param 
	 * @return void
	 */
	public void getBxByPro(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//Ʒ��id
		String id = request.getParameter("proclassid");
		if(id == null){
			return;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" s.id,s.vcode,s.vname ");
		sql.append(" from fz_docvariety d");
		sql.append(" left join fz_special s");
		sql.append(" on d.id=s.docvarietyid");
		sql.append(" where ifnull(d.dr,0)=0");
		sql.append(" and (d.proclassid="+ id + " or d.proclassids like '%"+id+"%')");
		sql.append(" and d.vname like '%"+IConstant.SPEC_BX+"%'");
		
		Integer total = getCountBySQL(sql.toString());
		List<SpecialVO> bxlist = (List<SpecialVO>) iHibernateDAO.findListBySQL(sql.toString(), null, 0,total);
		
		List<SpecialVO> list = new ArrayList<SpecialVO>();
		if(bxlist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(bxlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SpecialVO dto = new SpecialVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				list.add(dto);
			}
		}

		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("specbx", list);
		jsonmap.put("specqp", getInfoMoreByPro(id, IConstant.SPEC_QP));
		jsonmap.put("specxk", getInfoMoreByPro(id, IConstant.SPEC_XK));
		renderJson(jsonmap);
	}
	
	/**
	 * @Description: ͨ��Ʒ���ö�Ӧ���� 
	 * @param 
	 * @return void
	 */
	public List<SpecialVO> getBxByPro(String id){
		//Ʒ��id
		if(id == null){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" s.id,s.vcode,s.vname ");
		sql.append(" from fz_docvariety d");
		sql.append(" left join fz_special s");
		sql.append(" on d.id=s.docvarietyid");
		sql.append(" where ifnull(d.dr,0)=0");
		sql.append(" and (d.proclassid="+ id + " or d.proclassids like '%"+id+"%')");
		sql.append(" and d.vname like '%"+IConstant.SPEC_BX+"%'");
		
		Integer total = getCountBySQL(sql.toString());
		List<SpecialVO> bxlist = (List<SpecialVO>) iHibernateDAO.findListBySQL(sql.toString(), null, 0,total);
		
		List<SpecialVO> list = new ArrayList<SpecialVO>();
		if(bxlist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(bxlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SpecialVO dto = new SpecialVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				list.add(dto);
			}
		}
		return list;
	}
	
	/**
	 * @Description: �����ӦǰƬ
	 * @param @param id
	 * @param @return
	 * @return List<SpecialVO>
	 */
	public List<SpecialVO> getQpByPro(String id){
		return getInfoByPro(id,IConstant.SPEC_QP);
	}
	
	/**
	 * @Description: �����Ӧ�¿�
	 * @param @param id
	 * @param @return
	 * @return List<SpecialVO>
	 */
	public List<SpecialVO> getXkByPro(String id){
		return getInfoByPro(id,IConstant.SPEC_XK);
	}
	
	/**
	 * @Description: ͨ��Ʒ���ö�Ӧ�¿�/ǰƬ
	 * @param 
	 * @return void
	 */
	public List<SpecialVO> getInfoMoreByPro(String id,String wh){
		String key = "";
		if(IConstant.SPEC_QP.equals(wh)){
			key = "specqp";
		}else if(IConstant.SPEC_XK.equals(wh)){
			key = "specxk";
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" s.id,s.vcode,s.vname ");
		sql.append(" from fz_docvariety d");
		sql.append(" left join fz_special s");
		sql.append(" on d.id=s.docvarietyid");
		sql.append(" where ifnull(d.dr,0)=0");
		sql.append(" and (d.proclassid="+ id + " or d.proclassids like '%"+id+"%')");
		sql.append(" and d.vname like '%"+wh+"%'");
		
		Integer total = getCountBySQL(sql.toString());
		List<SpecialVO> bxlist = (List<SpecialVO>) iHibernateDAO.findListBySQL(sql.toString(), null, 0,total);
		
		List<SpecialVO> list = new ArrayList<SpecialVO>();
		if(bxlist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(bxlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SpecialVO dto = new SpecialVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				list.add(dto);
			}
		}

		return list;
	}
	
	/**
	 * @Description: ͨ��Ʒ���ö�Ӧ�¿�/ǰƬ
	 * @param 
	 * @return void
	 */
	public List<SpecialVO> getInfoByPro(String id,String wh){
		//Ʒ��id
		if(id == null){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" s.id,s.vcode,s.vname ");
		sql.append(" from fz_docvariety d");
		sql.append(" left join fz_special s");
		sql.append(" on d.id=s.docvarietyid");
		sql.append(" where ifnull(d.dr,0)=0");
		sql.append(" and d.proclassid="+id);
		sql.append(" and d.vname like '%"+wh+"%'");
		
		Integer total = getCountBySQL(sql.toString());
		List<SpecialVO> bxlist = (List<SpecialVO>) iHibernateDAO.findListBySQL(sql.toString(), null, 0,total);
		
		List<SpecialVO> list = new ArrayList<SpecialVO>();
		if(bxlist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(bxlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SpecialVO dto = new SpecialVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				list.add(dto);
			}
		}
		return list;
	}
	
	
	/**
	 * 
	* @Description: ��ñ�׼����Json��
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getSizeStandardJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//�����ѯ
		String search_input = request.getParameter("searchinput");
		
		StringBuffer sql = new StringBuffer();
	
		sql.append("select ");
		sql.append(" s.id,s.vsize,p.vname as proclassName");
		sql.append(" from fz_size_stinfo s");
		sql.append(" left join fz_tem_proclass p");
		sql.append(" on s.proclassid=p.id");
		sql.append(" where 1=1 ");
		
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and p.vname like '"+search_input+"%'");
		}
		
		//sql.append(" order by p.vname desc");
	
		Integer total = getCountBySQL(sql.toString());
		List<StinfoVO> stlist = findSizeStandardPage(sql.toString(), dgpage, rows);
		
		List<StinfoVO> list = new ArrayList<StinfoVO>();
		if(stlist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(stlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				StinfoVO dto = new StinfoVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVsize(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setProclassName(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
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
	public List<StinfoVO> findSizeStandardPage(String sql, Integer dgpage,Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<StinfoVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
	}
	
	/**
	* @Description: ��������
	* @param @return
	* @return String
	 */
	public String addSizeStandard(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			stinfoVO = iMyHibernateDao.selectBean(StinfoVO.class," where id ="+id);
			request.setAttribute("bxList",getBxByPro(stinfoVO.getProclassid()+""));//����
			request.setAttribute("qpList",getQpByPro(stinfoVO.getProclassid()+""));//ǰƬ
			request.setAttribute("xkList",getXkByPro(stinfoVO.getProclassid()+""));//�¿�
		}
		request.setAttribute("sizeStList",iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0 "));
		this.setUrl("../files/size/size-add-standard.jsp");
		return SUCCESS;
	}
	/**
	 * @Description: ����
	 * @return void
	 */
	public void saveSizeStandard(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//�ӱ�
			String subdata = request.getParameter("subdata");
			
			Integer id = stinfoVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			
			if(id != null){
				iMyHibernateDao.updateBean(stinfoVO);
				msg = "���³ɹ�";
			}else{
				id = iHibernateDAO.saveReInt(stinfoVO);
				msg = "�����ɹ�";
			}
			
			//�����ӱ�
			List<StinfoBVO> list_b = JsonUtils.getListFromJson(subdata, StinfoBVO.class);
			if(list_b != null){
				for (StinfoBVO stinfoBVO : list_b) {
					stinfoBVO.setStinfoId(id);
				}
			}
			iHibernateDAO.deleteAllByCondition(StinfoBVO.class, " stinfoId="+id);
			iHibernateDAO.saveAll(list_b);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	* @Description: ɾ��
	* @param @throws IOException
	* @return void
	 */
	public void delSizeStandard() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		try {
			StinfoVO stinfoVO = iMyHibernateDao.selectBean(StinfoVO.class," where id= "+id);
			iMyHibernateDao.deleteBean(stinfoVO);
			iHibernateDAO.deleteAllByCondition(StinfoBVO.class, " stinfoId="+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("ɾ���ɹ�");
	}
	/**��׼����-end**/
	
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
	public LtinfoVO getLtinfoVO() {
		return ltinfoVO;
	}
	public void setLtinfoVO(LtinfoVO ltinfoVO) {
		this.ltinfoVO = ltinfoVO;
	}
	public SizeDocVO getSizeDocVO() {
		return sizeDocVO;
	}
	public void setSizeDocVO(SizeDocVO sizeDocVO) {
		this.sizeDocVO = sizeDocVO;
	}
	public StinfoVO getStinfoVO() {
		return stinfoVO;
	}
	public void setStinfoVO(StinfoVO stinfoVO) {
		this.stinfoVO = stinfoVO;
	}
	public String getDocname() {
		return docname;
	}
	public void setDocname(String docname) {
		this.docname = docname;
	}

	public String getDoccode() {
		return doccode;
	}

	public void setDoccode(String doccode) {
		this.doccode = doccode;
	}

	public SizeDocTypeVO getSizeDocTypeVO() {
		return sizeDocTypeVO;
	}

	public void setSizeDocTypeVO(SizeDocTypeVO sizeDocTypeVO) {
		this.sizeDocTypeVO = sizeDocTypeVO;
	}
	
}
