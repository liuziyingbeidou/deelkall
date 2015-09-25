package action.parts;

import itf.pub.EnumParam;
import itf.pub.IConstant;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.basedoc.DocVarietyVO;
import model.parts.MasterVO;
import model.parts.SubPartBVO;
import model.parts.SubPartVO;
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

public class PartAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private MasterVO masterVO;
	private SubPartVO subPartVO;
	private SubPartBVO subPartBVO;
	
	/********************************������-start***************************/
	/**
	 * @Description: �����������б�
	 * @param @return
	 * @return String
	 */
	public String listMainPart()  {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0 order by id desc");
		String where = sb.toString();
		sb2.append(" 0=0 ");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = iMyHibernateDao.selectBeanCount(MasterVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(MasterVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "part!listMainPart", "����" + total + "����¼"));
		request.setAttribute("url", "part!listMainPart");
		
		this.setUrl("../files/parts/pt-list-mainpart.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ����������
	 * @param @return
	 * @return String
	 */
	public String addMainPart(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			masterVO = iMyHibernateDao.selectBean(MasterVO.class," where id ="+id);
		}
		
		this.setUrl("../files/parts/pt-add-mainpart.jsp");
		return SUCCESS;
	}
	
	//��ת���޸�������ҳ��
	public String changeMainPart() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		masterVO = iMyHibernateDao.selectBean(MasterVO.class," where id ="+id);
		this.setUrl("temtype/parts!addMainPart?type=edit");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ����������
	* @param 
	* @return void
	 */
	public void saveMainPart(){
		
		try {
			Integer id = masterVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			if(id != null){
				iMyHibernateDao.updateBean(masterVO);
				msg = "���³ɹ�";
			}else{
				iHibernateDAO.save(masterVO);
				//colourDao.insertBean(colourVO);
				msg = "�����ɹ�";
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//ɾ��������
	public void delMainPart() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		MasterVO masterVO = iMyHibernateDao.selectBean(MasterVO.class," where id= "+id);
		iMyHibernateDao.deleteBean(masterVO);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("ɾ���ɹ�");
	}
	/********************************������-end***************************/
	
	/********************************�Ӳ���-start***************************/
	/**
	 * @Description: �Ӳ��������б�
	 * @param @return
	 * @return String
	 */
	public String listSubPart()  {
		
		this.setUrl("../files/parts/pt-list-subpart.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ����Ӳ���Json��
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getSubPartJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//�����ѯ
		String search_input = request.getParameter("searchinput");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ");
		sql.append(" s.id,s.vcode,s.vname,s.vmemo,p.vname as proclassName,s.isort,s.deviceType,s.vsname");
		sql.append(" from fz_tem_subpart s");
		sql.append(" left join fz_tem_proclass p");
		sql.append(" on s.proclassid=p.id");
		sql.append(" where 1=1");
		
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and s.vname like '%"+search_input+"%' or p.vname like '%"+search_input+"%'");
		}
		
		//sql.append(" order by p.vname desc");
		
		Integer total = getCountBySQL(sql.toString());
		List<SubPartVO> speTypelist = findSubPartPage(sql.toString(),dgpage,rows);
		List<SubPartVO> list = new ArrayList<SubPartVO>();
		
		if(speTypelist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(speTypelist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SubPartVO dto = new SubPartVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVmemo(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				dto.setProclassName(CommUtil.isNullOrEm(arry[4]) ? null : arry[4].toString());
				dto.setIsort(CommUtil.isNullOrEm(arry[5]) ? null : Integer.valueOf(arry[5].toString()));
				if(!CommUtil.isNullOrEm(arry[6])){
					if("1".equals(arry[6].toString())){
						dto.setVdef1("���ֻ���");
					}else{
						dto.setVdef1("ȫ��");
					}
				}else{
					dto.setVdef1("ȫ��");
				}
				dto.setVsname(CommUtil.isNullOrEm(arry[7]) ? null : arry[7].toString());
				
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
	public List<SubPartVO> findSubPartPage(String sql, Integer dgpage,
			Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<SubPartVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
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
	 * @Description: �����Ӳ���
	 * @param @return
	 * @return String
	 */
	public String addSubPart(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			subPartVO = iMyHibernateDao.selectBean(SubPartVO.class," where id ="+id);
			 List<SubPartBVO> list = iHibernateDAO.findAll(SubPartBVO.class, " subpartid="+id);
			 for (SubPartBVO vo : list) {
				String vmoduletype = vo.getVmoduletype();
				if(IConstant.MOD_FABRIC.equals(vmoduletype)){//����
					subPartVO.setVfabric(vo.getVcolorOrpatch());
				}else if(IConstant.MOD_LINING.equals(vmoduletype)){//����
					subPartVO.setVlining(vo.getVlin());
				}else if(IConstant.MOD_ACCESSORIES.equals(vmoduletype)){//����
					subPartVO.setIaccessories(vo.getDocvarietyid());
				}else if(IConstant.MOD_SPECIAL.equals(vmoduletype)){//���⵵��
					subPartVO.setIspecial(vo.getDocvarietyid());
				}else if(IConstant.MOD_OUTSOURCE.equals(vmoduletype)){//����
					subPartVO.setIoutorn(vo.getDocvarietyid());
				}
			}
			//�������--add by liuzy --�ĳɸ���ѡ��Ʒ�ද̬����
			request.setAttribute("speclist",getAllSpeType(subPartVO.getProclassid()));
		}
		//����-Ʒ��
		request.setAttribute("acclist",iMyHibernateDao.selectBeanList(DocVarietyVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_ACCESSORIES+"'"));
		//Ʒ��
		request.setAttribute("proclasslist",iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0"));
		//����-Ʒ��
		request.setAttribute("outlist",iMyHibernateDao.selectBeanList(DocVarietyVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_OUTSOURCE+"'"));
		
		this.setUrl("../files/parts/pt-add-subpart.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: ������⵵�����
	 * @param @return
	 * @return List<DocVarietyVO>
	 */
	public void getSpeTypeByPro(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String proclassid = request.getParameter("proclassid");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ");
		sql.append(" d.id,d.vcode,d.vname,p.vname as proclassName");
		sql.append(" from fz_docvariety d");
		sql.append(" left join fz_tem_proclass p");
		sql.append(" on d.proclassid=p.id");
		sql.append(" where ifnull(d.dr,0)=0");
		sql.append(" and vmoduletype='"+IConstant.MOD_SPECIAL+"'");
		if(!"-1".equals(proclassid) && proclassid != null && !"".equals(proclassid)){
			sql.append(" and d.proclassid="+proclassid);
		}
		
		Integer total = getCountBySQL(sql.toString());
		List<DocVarietyVO> sublist = (List<DocVarietyVO>) iHibernateDAO.findListBySQL(sql.toString(),null,0,total);
		List<DocVarietyVO> list = new ArrayList<DocVarietyVO>();
		if(sublist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(sublist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				DocVarietyVO dto = new DocVarietyVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setProclassName(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				
				list.add(dto);
			}
		}
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("rows", list);
		renderJson(jsonmap);
		//return list;
	}
	
	/**
	 * @Description: ������⵵�����
	 * @param @return
	 * @return List<DocVarietyVO>
	 */
	public List<DocVarietyVO> getAllSpeType(Integer proclassid){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ");
		sql.append(" d.id,d.vcode,d.vname,p.vname as proclassName");
		sql.append(" from fz_docvariety d");
		sql.append(" left join fz_tem_proclass p");
		sql.append(" on d.proclassid=p.id");
		sql.append(" where ifnull(d.dr,0)=0");
		sql.append(" and vmoduletype='"+IConstant.MOD_SPECIAL+"'");
		if(!"-1".equals(proclassid) && proclassid != null && !"".equals(proclassid)){
			sql.append(" and d.proclassid="+proclassid);
		}
		
		Integer total = getCountBySQL(sql.toString());
		List<DocVarietyVO> sublist = (List<DocVarietyVO>) iHibernateDAO.findListBySQL(sql.toString(),null,0,total);
		List<DocVarietyVO> list = new ArrayList<DocVarietyVO>();
		if(sublist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(sublist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				DocVarietyVO dto = new DocVarietyVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setProclassName(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				
				list.add(dto);
			}
		}
		
		return list;
	}
	//��ת���޸��Ӳ���ҳ��
	@Deprecated
	public String changeSubPart() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		subPartVO = iMyHibernateDao.selectBean(SubPartVO.class," where id ="+id);
		this.setUrl("parts/parts!addSubPart?type=edit");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: �����Ӳ���
	* @param 
	* @return void
	 */
	public void saveSubPart(){
		
		try {
			Integer id = subPartVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			//��������
			String vfabric = subPartVO.getVfabric();
			//����
			String vlining = subPartVO.getVlining();
			//����
			Integer iaccessories = subPartVO.getIaccessories();
			//���⵵��
			Integer ispecial = subPartVO.getIspecial();
			//����
			Integer ioutorn = subPartVO.getIoutorn();
			
			//����ʱ����ѡ��ļ�������������������-���ڼ���ģ��ʱ�ж�
			if(ispecial != null){
				subPartVO.setVlinktype(IConstant.MOD_SPECIAL);
			}else if(iaccessories != null){
				subPartVO.setVlinktype(IConstant.MOD_ACCESSORIES);
			}
			
			List<SubPartBVO> list = new ArrayList<SubPartBVO>();
			if(id != null){
				iMyHibernateDao.updateBean(subPartVO);
				updateSNMByNM(subPartVO.getVname(),subPartVO.getVsname());
				msg = "���³ɹ�";
			}else{
				id = iHibernateDAO.saveReInt(subPartVO);
				updateSNMByNM(subPartVO.getVname(),subPartVO.getVsname());
				msg = "�����ɹ�";
			}
			//�������ӱ�Ĵ���
			//�����ӱ�
			for (EnumParam e : EnumParam.values()) {
				SubPartBVO subPartBVO = new SubPartBVO();
				switch(e){
					case MOD_FABRIC:{
						if(vfabric != null && !"".equals(vfabric)){
							subPartBVO.setSubpartid(id);
							subPartBVO.setVmoduletype(IConstant.MOD_FABRIC);
							subPartBVO.setVcolorOrpatch(subPartVO.getVfabric());
							list.add(subPartBVO);
						}
					} ;break;
					case MOD_LINING:{
						if(vlining != null && !"".equals(vlining)){
							subPartBVO.setSubpartid(id);
							subPartBVO.setVmoduletype(IConstant.MOD_LINING);
							subPartBVO.setVlin(subPartVO.getVlining());
							list.add(subPartBVO);
						}
					};break;
					case MOD_ACCESSORIES:{
						if(iaccessories != null){
							subPartBVO.setSubpartid(id);
							subPartBVO.setVmoduletype(IConstant.MOD_ACCESSORIES);
							subPartBVO.setDocvarietyid(subPartVO.getIaccessories());
							list.add(subPartBVO);
						}
					};break;
					case MOD_SPECIAL:{
						if(ispecial != null){
							subPartBVO.setSubpartid(id);
							subPartBVO.setVmoduletype(IConstant.MOD_SPECIAL);
							subPartBVO.setDocvarietyid(subPartVO.getIspecial());
							list.add(subPartBVO);
						}
					};break;
					case MOD_OUTSOURCE:{
						if(ioutorn != null){
							subPartBVO.setSubpartid(id);
							subPartBVO.setVmoduletype(IConstant.MOD_OUTSOURCE);
							subPartBVO.setDocvarietyid(subPartVO.getIoutorn());
							list.add(subPartBVO);
						}
					};break;
				}
			}
			if(id != null){
				iHibernateDAO.deleteAllByCondition(SubPartBVO.class, " subpartid="+id);
			}
			iHibernateDAO.saveAll(list);
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �Ӳ���
	 * @Description: ���ݵ�ǰ����-�������ˢ��������ͬ���Ƽ��
	 * @param 
	 * @return void
	 */
	private void updateSNMByNM(String name,String sname){
		String[] attrname = new String[]{"vsname"};
		String[] attrval = new String[]{sname};
		if((name != null && !"".equals(name)) && (sname != null && !"".equals(sname))){
			iHibernateDAO.updateAttrs(SubPartVO.class, attrname, attrval, " vname='"+name+"'");
		}
	}

	//ɾ���Ӳ���
	public void delSubPart() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		try {
			SubPartVO subPartVO = iMyHibernateDao.selectBean(SubPartVO.class," where id= "+id);
			iMyHibernateDao.deleteBean(subPartVO);
			iHibernateDAO.deleteAllByCondition(SubPartBVO.class, " subpartid="+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("ɾ���ɹ�");
	}
	
	/********************************�Ӳ���-end***************************/
	
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
	public MasterVO getMasterVO() {
		return masterVO;
	}
	public void setMasterVO(MasterVO masterVO) {
		this.masterVO = masterVO;
	}

	public SubPartVO getSubPartVO() {
		return subPartVO;
	}

	public void setSubPartVO(SubPartVO subPartVO) {
		this.subPartVO = subPartVO;
	}

	public SubPartBVO getSubPartBVO() {
		return subPartBVO;
	}

	public void setSubPartBVO(SubPartBVO subPartBVO) {
		this.subPartBVO = subPartBVO;
	}
	
}
