package action.proclass;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.parts.MasterVO;
import model.parts.SubPartVO;
import model.proclass.ProclassBBVO;
import model.proclass.ProclassBVO;
import model.proclass.ProclassVO;
import model.proclass.TemClassVO;
import model.proclass.TemSubclassVO;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import util.CommUtil;
import util.ObjectToJSON;
import utils.JsonUtils;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

/**
 * 
* @Title: ��װϵͳ
* @ClassName: ProClassAction 
* @Description: Ʒ��
* @author liuzy
* @date 2015-6-23 ����04:51:29
 */
public class ProClassAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private ProclassVO proclassVO;
	private ProclassBVO proclassBVO;
	private ProclassBBVO proclassBBVO;
	
	
	/********************************Ʒ��-start***************************/
	/**
	 * @Description: Ʒ�൵���б�
	 * @param @return
	 * @return String
	 */
	public String listProClass()  {
		
		this.setUrl("../files/proclass/pro-list-proclass.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ���Ʒ��Json��
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getProClassJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String search_input = request.getParameter("searchinput");
		
		StringBuffer wh = new StringBuffer();
		wh.append(" 1=1");
		if(search_input != null && !"".equals(search_input)){
			wh.append(" and p.vname like '"+search_input+"%'");
		}
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ");
		sql.append(" p.id,p.vcode,p.vname,c.vname as className,s.vname as subclassName,p.isort,p.vsname");
		sql.append(" from fz_tem_proclass p");
		sql.append(" left join fz_tem_class c");
		sql.append(" on p.classid=c.id");
		sql.append(" left join fz_tem_subclass s");
		sql.append(" on p.subclassid=s.id ");
		
		sql.append( " where " + wh);
		//sql.append(" order by c.vname asc,s.vname asc");
		
//		Integer total = iHibernateDAO.getCountByHQL(ProclassVO.class, wh.toString());
//		List<ProclassVO> prolist = (List<ProclassVO>)iHibernateDAO.findPage(ProclassVO.class, dgpage, rows,wh.toString());
		
		Integer total = getCountBySQL(sql.toString());
		List<ProclassVO> prolist = findProClassPage(sql.toString(),dgpage,rows);
		List<ProclassVO> list = new ArrayList<ProclassVO>();
		if(prolist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(prolist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				ProclassVO dto = new ProclassVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setClassName(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				dto.setSubclassName(CommUtil.isNullOrEm(arry[4]) ? null : arry[4].toString());
				dto.setIsort(CommUtil.isNullOrEm(arry[5]) ? null : Integer.valueOf(arry[5].toString()));
				dto.setVsname(CommUtil.isNullOrEm(arry[6]) ? null : arry[6].toString());
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
	public List<ProclassVO> findProClassPage(String sql, Integer dgpage,Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<ProclassVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
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
	 * @Description: ����Ʒ��
	 * @param @return
	 * @return String
	 */
	public String addProClass(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			proclassVO = iMyHibernateDao.selectBean(ProclassVO.class," where id ="+id);
			request.getSession().setAttribute("proclassid", Integer.valueOf(id));
		}else{
			//���ν���
			if(proclassVO == null){
				request.getSession().removeAttribute("proclassid");
			}
		}
		//�ܷ���
		request.setAttribute("classlist",iMyHibernateDao.selectBeanList(TemClassVO.class," where ifnull(dr,0)=0"));
		if(proclassVO != null && proclassVO.getClassid() != null){
			//��Ʒ��
			request.setAttribute("subclasslist",iMyHibernateDao.selectBeanList(TemSubclassVO.class," where ifnull(dr,0)=0 and classid="+proclassVO.getClassid()));
		}
		//������
		request.setAttribute("mainpartlist",iMyHibernateDao.selectBeanList(MasterVO.class," where ifnull(dr,0)=0 "));
		//�Ӳ���
		request.setAttribute("subpartlist",getAllSubPart());
		
		this.setUrl("../files/proclass/pro-add-proclass.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Description: ���ȫ���Ӳ���
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
			 * ����Զ���SQL����List<E>
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
	 * 
	* @Description: ��������id�����ӱ�����
	* @param 
	* @return void
	 */
	public void getMainPartByProClassJson(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Object proclassid = request.getSession().getAttribute("proclassid");
		List<ProclassBVO> subClasslist = new ArrayList<ProclassBVO>();
		if(proclassid != null){
			subClasslist = iMyHibernateDao.selectBeanList(ProclassBVO.class," where ifnull(dr,0)=0 and proclassid="+proclassid);
		}
		//request.getSession().removeAttribute("proclassid");
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		if(subClasslist == null){
			subClasslist = new ArrayList<ProclassBVO>();;
		}
		
		jsonmap.put("rows", subClasslist);
		renderJson(jsonmap);
	}
	
	/**
	 * 
	* @Description: ��������id�����������
	* @param 
	* @return void
	 */
	public void getSubPartByProClassJson(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Object proclassid = request.getSession().getAttribute("proclassid");
		List<ProclassBBVO> subClasslist = new ArrayList<ProclassBBVO>();
		if(proclassid != null){
			subClasslist = iMyHibernateDao.selectBeanList(ProclassBBVO.class," where ifnull(dr,0)=0 and proclassid="+proclassid);
		}
		//request.getSession().removeAttribute("proclassid");
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		if(subClasslist == null){
			subClasslist = new ArrayList<ProclassBBVO>();;
		}
		
		jsonmap.put("rows", subClasslist);
		renderJson(jsonmap);
	}
	
	/**
	* @Description: �ж��ӱ�ĳ�������Ƿ��Ӧ���������
	* @param 
	* @return void
	 */
	public void isHaveGrandchildren(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Object proclass_bid = request.getParameter("proclass_bid");
		List<ProclassBBVO> subClasslist = new ArrayList<ProclassBBVO>();
		if(proclass_bid != null){
			subClasslist = iMyHibernateDao.selectBeanList(ProclassBBVO.class," where ifnull(dr,0)=0 and proclass_bid="+proclass_bid);
		}
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		if(subClasslist == null){
			subClasslist = new ArrayList<ProclassBBVO>();;
		}
		
		jsonmap.put("rows", subClasslist);
		renderJson(jsonmap);
	}
	
	/**
	 * 
	* @Description: �����ܷ�id���ش�Ʒ��
	* @param 
	* @return void
	 */
	public void getProClass(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String classid = request.getParameter("classid");
		if(classid == null){
			return;
		}
		List<TemSubclassVO> subClasslist = iMyHibernateDao.selectBeanList(TemSubclassVO.class," where ifnull(dr,0)=0 and classid="+classid);
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("rows", subClasslist);
		renderJson(jsonmap);
	}
	
	/**
	* @Description: ����Ʒ��
	* @param 
	* @return void
	 */
	@Transactional
	public void saveProClass(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//�ӱ�
		String mainpartlist = request.getParameter("mainpartlist");
		//���
		String subpartlist = request.getParameter("subpartlist");
		try {
			Integer id = proclassVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			
			if(id != null){
				iMyHibernateDao.updateBean(proclassVO);
				msg = "���³ɹ�";
			}else{
				id = iHibernateDAO.saveReInt(proclassVO);
				request.getSession().setAttribute("proclassid", Integer.valueOf(id));
				msg = "�����ɹ�";
			}
			
			//ɾ���ӱ����ͨ����������
			iHibernateDAO.deleteAllByCondition(ProclassBBVO.class, " proclassid="+id);
			iHibernateDAO.deleteAllByCondition(ProclassBVO.class, " proclassid="+id);
			
			//�����ӱ�
			List<ProclassBVO> list_b = JsonUtils.getListFromJson(mainpartlist, ProclassBVO.class);
			if(list_b!=null){
				//�������
				List<ProclassBBVO > list_bb = JsonUtils.getListFromJson(subpartlist, ProclassBBVO.class);
				for(ProclassBVO bvo : list_b){
					/**********�ӱ���********/
					//��������
					bvo.setProclassid(id);
					//�ӱ�����
					Integer bid = iHibernateDAO.saveReInt(bvo);
					//������id
					Integer mainid = bvo.getMasterid();
					
					//�����
					if(list_bb != null){
						List<ProclassBBVO > list_bb_temp = new ArrayList<ProclassBBVO>();
						for (ProclassBBVO bbvo : list_bb) {
							//������id
							Integer sub_mainid = bbvo.getMasterid();
							if(mainid.equals(sub_mainid)){
								//��������
								bbvo.setProclassid(id);
								//�ӱ�����
								bbvo.setProclass_bid(bid);
								list_bb_temp.add(bbvo);
							}
						}
						//�������
						iHibernateDAO.saveAll(list_bb_temp);
					}
				}
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

	//ɾ��Ʒ��
	public void delProClass() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		try {
			ProclassVO proclassVO = iMyHibernateDao.selectBean(ProclassVO.class," where id= "+id);
			iMyHibernateDao.deleteBean(proclassVO);
			
			//ɾ���ӱ����ͨ����������
			iHibernateDAO.deleteAllByCondition(ProclassBBVO.class, " proclassid="+id);
			iHibernateDAO.deleteAllByCondition(ProclassBVO.class, " proclassid="+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("ɾ���ɹ�");
	}
	
	/********************************Ʒ��-end***************************/
	
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
	public ProclassVO getProclassVO() {
		return proclassVO;
	}
	public void setProclassVO(ProclassVO proclassVO) {
		this.proclassVO = proclassVO;
	}
	public ProclassBVO getProclassBVO() {
		return proclassBVO;
	}
	public void setProclassBVO(ProclassBVO proclassBVO) {
		this.proclassBVO = proclassBVO;
	}
	public ProclassBBVO getProclassBBVO() {
		return proclassBBVO;
	}
	public void setProclassBBVO(ProclassBBVO proclassBBVO) {
		this.proclassBBVO = proclassBBVO;
	}

}
