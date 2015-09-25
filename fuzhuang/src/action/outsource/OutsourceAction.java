package action.outsource;

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
import model.materials.MateChildVO;
import model.materials.MaterialsVO;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import util.CommUtil;
import util.ObjectToJSON;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

/**
 * 
 * @Title: ��װϵͳ
 * @ClassName: OutsourceAction 
 * @Description: �⹺��Ʒ
 * @author liuzy
 * @date 2015-7-9 ����07:04:58
 */
public class OutsourceAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private AuxiliaryVO auxiliaryVO;
	
	/********************************�⹺��Ʒ-start***************************/
	/**
	 * �����б�
	 */
	public String listOutsource(){
		
		this.setUrl("../files/outsource/out-list-outsource.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: �������Json��
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getOutsourceJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//�����ѯ
		String search_input = request.getParameter("searchinput");
		//�Ƿ���ͼ
		String bisupload = request.getParameter("bisupload");
		//�Ƿ�Ĭ������
		String bisdefault = request.getParameter("bisdefault");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" f.id,f.vcode,t.vname as docvarietyname,c.vname as colourName,f.bisupload,i.vname as ingredientName,f.vname");
		sql.append(" from fz_auxiliary f");
		sql.append(" left join fz_base_doc c");
		sql.append(" on f.colourid=c.id");
		sql.append(" left join fz_base_doc_so i");
		sql.append(" on f.ingredientid=i.id");
		sql.append(" left join fz_docvariety t");
		sql.append(" on f.docvarietyid=t.id");
		sql.append(" where ifnull(f.dr,0)=0");
		sql.append(" and f.vmoduletype='"+IConstant.MOD_OUTSOURCE+"'");
		sql.append(" and t.vmoduletype='"+IConstant.MOD_OUTSOURCE+"'");
		sql.append(" and i.vmoduletype='"+IConstant.MOD_OUTSOURCE+"'");
		sql.append(" and i.vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		sql.append(" and c.vdoctype='"+IConstant.DOC_COLOUR+"'");
		
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
		List<AuxiliaryVO> lininglist = findLiningPage(sql.toString(),dgpage,rows);
		List<AuxiliaryVO> list = new ArrayList<AuxiliaryVO>();
		if(lininglist != null){
			/*
			 * ����Զ���SQL����List<E>
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
				dto.setColourName(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				if(!CommUtil.isNullOrEm(arry[4])){
					dto.setBisupload(Integer.valueOf(arry[4].toString()));
					if("1".equals(arry[4].toString())){//��ͼ
						dto.setVdef1("��");
					}else{
						dto.setVdef1("��");
					}
				}
				dto.setIngredientName(CommUtil.isNullOrEm(arry[5]) ? null : arry[5].toString());
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
	 * ����sql���count
	 * @param sql
	 * @return
	 */
	public Integer getCountBySQL(String sql){
		return iHibernateDAO.getCountBySQL("select count(*) from ("+sql+") tb", null);
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
	public List<AuxiliaryVO> findLiningPage(String sql, Integer dgpage,
			Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<AuxiliaryVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
	}
	
	/**
	 * @Description: ��������
	 * @param @return
	 * @return String
	 */
	public String addOutsource(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		request.setAttribute("mateChildlist",iMyHibernateDao.selectBeanList(MateChildVO.class," where ifnull(dr,0)=0"));
		if(id != null){
			auxiliaryVO = iMyHibernateDao.selectBean(AuxiliaryVO.class," where ifnull(dr,0)=0 and id ="+id);
			Integer mateid = auxiliaryVO.getMaterialsid();
			request.setAttribute("mateChildlist",iMyHibernateDao.selectBeanList(MateChildVO.class," where ifnull(dr,0)=0 and mateid="+mateid));
		}
		//Ʒ��
		request.setAttribute("docvarietylist",iMyHibernateDao.selectBeanList(DocVarietyVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_OUTSOURCE+"'"));
		//�ɷ�
		request.setAttribute("ingredientlist",iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_OUTSOURCE+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"'"));
		//��ɫ
		request.setAttribute("colourlist",iMyHibernateDao.selectBeanList(BaseDocVO.class," where ifnull(dr,0)=0 and vdoctype='"+IConstant.DOC_COLOUR+"'"));
		//�ϼ�����
		request.setAttribute("materialslist",iMyHibernateDao.selectBeanList(MaterialsVO.class," where ifnull(dr,0)=0"));
		
		this.setUrl("../files/outsource/out-add-outsource.jsp");
		return SUCCESS;
	}
	
	/**
	* @Description: ��������
	* @param 
	* @return void
	 */
	public void saveOutsource(){
		
		try {
			Integer id = auxiliaryVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			String furl = auxiliaryVO.getVfileupload();
			if(!CommUtil.isNullOrEm(furl)){
				auxiliaryVO.setBisupload(1);
			}else{
				auxiliaryVO.setBisupload(0);
				auxiliaryVO.setVfileupload(null);
			}
			if(id != null){
				iMyHibernateDao.updateBean(auxiliaryVO);
				msg = "���³ɹ�";
			}else{
				auxiliaryVO.setVmoduletype(IConstant.MOD_OUTSOURCE);
				iMyHibernateDao.insertBean(auxiliaryVO);
				msg = "�����ɹ�";
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//ɾ������
	public void delOutsource() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		AuxiliaryVO liningVO = iMyHibernateDao.selectBean(AuxiliaryVO.class," where vmoduletype='"+IConstant.MOD_OUTSOURCE+"' and id= "+id);
		iMyHibernateDao.deleteBean(liningVO);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("ɾ���ɹ�");
	}
	
	/**
	 * 
	* @Description: ���ݴ���id��������
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
	* @Description: ����Ʒ�ּ��سɷ�
	* @param 
	* @return void
	 */
	public void getIngByType(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//Ʒ��id
		String id = request.getParameter("docvarietyid");
		if(id == null){
			return;
		}
		
		List<BaseDocSoVO> materiallist = new ArrayList<BaseDocSoVO>();
		//�ɷ�
		materiallist = iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_OUTSOURCE+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"' and varietyid="+id);
		if(materiallist == null){
			materiallist = new ArrayList<BaseDocSoVO>();
		}
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("doc_ingredients", materiallist);
		renderJson(jsonmap);
	}
	
	
	/********************************�⹺��Ʒ-end***************************/
	
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
