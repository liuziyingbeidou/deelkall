package action.shop;

import itf.pub.IConstant;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import util.CommUtil;
import util.ObjectToJSON;

import model.scheme.SchemeVO;
import model.sp.system.SpUserVO;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

/**
 * @Title: ��װϵͳ
 * @ClassName: SpUserAction 
 * @Description: �ŵ�-��Ա����
 * @author liuzy
 * @date 2015-8-27 ����03:53:44
 */
public class SpUserAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private SpUserVO spUserVO;
	
	
	/**��Ա�û�-start**/
	/**
	 * �б����
	 */
	public String listSpUser(){
		
		this.setUrl("../files/shop/sp-user/sp-list-user.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ���ģ������Json��
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getSpUserJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//�����ѯ
		String search_input = request.getParameter("searchinput");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select id,vusername,vtruename,vpwd,islock from fz_sp_user where 1=1");
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and vusername like '%"+search_input+"%'");
		}

		Integer total = getCountBySQL(sql.toString());
		List<SpUserVO> schlist = findSpUserBySQL(sql.toString(),dgpage, rows);

		List<SpUserVO> list = new ArrayList<SpUserVO>();
		if(schlist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(schlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SpUserVO dto = new SpUserVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVusername(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVtruename(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVpwd(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				if(!CommUtil.isNullOrEm(arry[4])){
					if("1".equals(arry[4]+"")){
						dto.setVdef1("��");
					}else{
						dto.setVdef1("��");
					}
				}else{
					dto.setVdef1("��");
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
	 * @Description: ��ҳ
	 * @param @param sql
	 * @param @param dgpage
	 * @param @param rows
	 * @param @return
	 * @return List<StinfoVO>
	 */
	public List<SpUserVO> findSpUserBySQL(String sql, Integer dgpage,Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<SpUserVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
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
	public String addSpUser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			spUserVO = iMyHibernateDao.selectBean(SpUserVO.class," where id ="+id);
		}
		this.setUrl("../files/shop/sp-user/sp-add-user.jsp");
		return SUCCESS;
	}
	/**
	 * @Description: ����
	 * @return void
	 */
	public void saveSpUser(){
		try {
			Integer id = spUserVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			
			if(id != null){
				iMyHibernateDao.updateBean(spUserVO);
				msg = "���³ɹ�";
			}else{
				iMyHibernateDao.insertBean(spUserVO);
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
	public void delSpUser() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		SpUserVO spUserVO = iMyHibernateDao.selectBean(SpUserVO.class," where id= "+id);
		iMyHibernateDao.deleteBean(spUserVO);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("ɾ���ɹ�");
	}
	/**��Ա�û�-end**/
	
	
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
	public SpUserVO getSpUserVO() {
		return spUserVO;
	}
	public void setSpUserVO(SpUserVO spUserVO) {
		this.spUserVO = spUserVO;
	}

}
