package action.temtype;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.proclass.TemClassVO;
import model.proclass.TemSubclassVO;

import org.apache.struts2.ServletActionContext;

import util.JsonTreeHelper;
import util.Pager;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

public class TemtypeAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private TemClassVO temClassVO;
	private TemSubclassVO temSubclassVO;
	
	/********************************�ܷ���-start***************************/
	/**
	 * @Description: �ܷ��൵���б�
	 * @param @return
	 * @return String
	 */
	public String listTemtype()  {
		
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
		int total = iMyHibernateDao.selectBeanCount(TemClassVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(TemClassVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "temtype!listTemtype", "����" + total + "����¼"));
		request.setAttribute("url", "temtype!listTemtype");
		
		this.setUrl("../files/temtype/tem-list-type.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: �����ܷ���
	 * @param @return
	 * @return String
	 */
	public String addTemtype(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			temClassVO = iMyHibernateDao.selectBean(TemClassVO.class," where id ="+id);
		}
		
		this.setUrl("../files/temtype/tem-add-type.jsp");
		return SUCCESS;
	}
	
	//��ת���޸��ܷ���ҳ��
	public String changeTemtype() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		temClassVO = iMyHibernateDao.selectBean(TemClassVO.class," where id ="+id);
		this.setUrl("temtype/temtype!addTemtype?type=edit");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: �����ܷ���
	* @param 
	* @return void
	 */
	public void saveTemtype(){
		
		try {
			Integer id = temClassVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			if(id != null){
				iMyHibernateDao.updateBean(temClassVO);
				msg = "���³ɹ�";
			}else{
				iHibernateDAO.save(temClassVO);
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

	//ɾ���ܷ���
	public void delTemtype() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		TemClassVO temClassVO = iMyHibernateDao.selectBean(TemClassVO.class," where id= "+id);
		iMyHibernateDao.deleteBean(temClassVO);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("ɾ���ɹ�");
	}
	/********************************�ܷ���-end***************************/
	
	
	/********************************��Ʒ��-start***************************/
	/**
	 * @Description: ��Ʒ�൵���б�
	 * @param @return
	 * @return String
	 */
	public String listTemSubClass()  {
		
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
		int total = iMyHibernateDao.selectBeanCount(TemSubclassVO.class,where2);
		request.setAttribute("list", iMyHibernateDao.selectBeanList(TemSubclassVO.class,(currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "temtype!listTemSubClass", "����" + total + "����¼"));
		request.setAttribute("url", "temtype!listTemSubClass");
		
		this.setUrl("../files/temtype/tem-list-maintype.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ���ش�Ʒ��jsonstring
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public String getJsonString(){
		
		List subClassList = iMyHibernateDao.selectBeanList(TemClassVO.class," where ifnull(dr,0)=0");
		String[] members = new String[]{"id","vname","mateid"};
		String jsonstr = null;
		if(subClassList != null){
			try {
				jsonstr = JsonTreeHelper.getTreeJsonStrNormal(members, subClassList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonstr;
	}
	
	/**
	 * @Description: ������Ʒ��
	 * @param @return
	 * @return String
	 */
	public String addTemSubClass(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			temSubclassVO = iMyHibernateDao.selectBean(TemSubclassVO.class," where ifnull(dr,0)=0 and id ="+id);
		}
		request.setAttribute("typelist",iMyHibernateDao.selectBeanList(TemClassVO.class," where ifnull(dr,0)=0"));
		this.setUrl("../files/temtype/tem-add-maintype.jsp");
		return SUCCESS;
	}
	
	//��ת���޸Ĵ�Ʒ��ҳ��
	public String changeTemSubClass() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		temSubclassVO = iMyHibernateDao.selectBean(TemSubclassVO.class," where id ="+id);
		this.setUrl("temtype/temtype!addTemSubClass?type=edit");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: �����Ʒ��
	* @param 
	* @return void
	 */
	public void saveTemSubClass(){
		
		try {
			Integer id = temSubclassVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			if(id != null){
				iMyHibernateDao.updateBean(temSubclassVO);
				msg = "���³ɹ�";
			}else{
				iMyHibernateDao.insertBean(temSubclassVO);
				msg = "�����ɹ�";
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//ɾ����Ʒ��
	public void delTemSubClass() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		TemSubclassVO temSubclassVO = iMyHibernateDao.selectBean(TemSubclassVO.class," where id= "+id);
		iMyHibernateDao.deleteBean(temSubclassVO);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("ɾ���ɹ�");
	}
	
	/********************************��Ʒ��-end***************************/
	
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

	public TemClassVO getTemClassVO() {
		return temClassVO;
	}

	public void setTemClassVO(TemClassVO temClassVO) {
		this.temClassVO = temClassVO;
	}

	public TemSubclassVO getTemSubclassVO() {
		return temSubclassVO;
	}

	public void setTemSubclassVO(TemSubclassVO temSubclassVO) {
		this.temSubclassVO = temSubclassVO;
	}
	
}
