package action.mold;

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
import model.mold.ModelBVO;
import model.mold.ModelFolderVO;
import model.mold.ModelTypeVO;
import model.mold.ModelVO;
import model.proclass.ProclassVO;
import model.special.SpecialVO;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import util.CommUtil;
import util.ObjectToJSON;
import utils.JsonUtils;
import utils.ReflectionUtils;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

/**
 * 
* @Title: ��װϵͳ
* @ClassName: MoldAction 
* @Description: ģ�͹���
* @author liuzy
* @date 2015-6-29 ����11:21:27
 */
public class MoldAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	
	private ModelTypeVO modelTypeVO;
	private ModelFolderVO modelFolderVO;
	private ModelVO modelVO;
	private ModelBVO modelBVO;
	
	
	/**ģ������-start**/
	/**
	 * �б����
	 */
	public String listMoldType(){
		
		this.setUrl("../files/mold/mod-list-modelType.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ���ģ������Json��
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getMoldTypeJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//�����ѯ
		String search_input = request.getParameter("searchinput");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" 1=1");
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and vname like '%"+search_input+"%'");
		}
		
		Integer total = iHibernateDAO.getCountByHQL(ModelTypeVO.class, sql.toString());
		List<ModelTypeVO> list = (List<ModelTypeVO>)iHibernateDAO.findPage(ModelTypeVO.class, dgpage, rows,sql.toString());

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
	* @Description: ��������
	* @param @return
	* @return String
	 */
	public String addMoldType(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			modelTypeVO = iMyHibernateDao.selectBean(ModelTypeVO.class," where id ="+id);
		}
		this.setUrl("../files/mold/mod-add-modelType.jsp");
		return SUCCESS;
	}
	/**
	 * @Description: ����
	 * @return void
	 */
	public void saveMoldType(){
		try {
			Integer id = modelTypeVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			
			if(id != null){
				iMyHibernateDao.updateBean(modelTypeVO);
				msg = "���³ɹ�";
			}else{
				iMyHibernateDao.insertBean(modelTypeVO);
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
	public void delMoldType() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		ModelTypeVO modelTypeVO = iMyHibernateDao.selectBean(ModelTypeVO.class," where id= "+id);
		iMyHibernateDao.deleteBean(modelTypeVO);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("ɾ���ɹ�");
	}
	/**ģ������-end**/
	
	/**ģ�ʹ洢λ��-start**/
	/**
	 * �б����
	 */
	public String listMoldFolder(){
		
		this.setUrl("../files/mold/mod-list-modelFolder.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ���ģ�ʹ洢λ��Json��
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getMoldFolderJson(){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" f.id,f.proclassid,p.vname proclassName,f.vname ");
		sql.append(" from fz_model_folder f");
		sql.append(" left join fz_tem_proclass p");
		sql.append(" on f.proclassid=p.id");
		sql.append(" where ifnull(f.dr,0)=0");
		
		Integer total = getCountBySQL(sql.toString());
		List<ModelFolderVO> folderlist = (List<ModelFolderVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);

		List<ModelFolderVO> list = new ArrayList<ModelFolderVO>();
		if(folderlist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(folderlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				ModelFolderVO dto = new ModelFolderVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setProclassid(CommUtil.isNullOrEm(arry[1]) ? null : Integer.valueOf(arry[1].toString()));
				dto.setProclassName(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				list.add(dto);
			}
		}
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("total", total);
		jsonmap.put("rows", list);
		renderJson(jsonmap);
	}
	
	/**
	 * 
	* @Description: ���ģ�ʹ洢λ��List
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public List<ModelFolderVO> getListMoldFolder(){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" f.id,f.proclassid,p.vname proclassName,f.vname ");
		sql.append(" from fz_model_folder f");
		sql.append(" left join fz_tem_proclass p");
		sql.append(" on f.proclassid=p.id");
		sql.append(" where ifnull(f.dr,0)=0");
		
		Integer total = getCountBySQL(sql.toString());
		List<ModelFolderVO> folderlist = (List<ModelFolderVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);

		List<ModelFolderVO> list = new ArrayList<ModelFolderVO>();
		if(folderlist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(folderlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				ModelFolderVO dto = new ModelFolderVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setProclassid(CommUtil.isNullOrEm(arry[1]) ? null : Integer.valueOf(arry[1].toString()));
				dto.setProclassName(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				list.add(dto);
			}
		}
		
		return list;
	}
	
	/**
	* @Description: ��������
	* @param @return
	* @return String
	 */
	public String addMoldFolder(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			modelFolderVO = iMyHibernateDao.selectBean(ModelFolderVO.class," where id ="+id);
		}
		//Ʒ��
		request.setAttribute("proclasslist",iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0"));
		this.setUrl("../files/mold/mod-add-modelFolder.jsp");
		return SUCCESS;
	}
	/**
	 * @Description: ����
	 * @return void
	 */
	public void saveMoldFolder(){
		try {
			Integer id = modelFolderVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			
			if(id != null){
				iMyHibernateDao.updateBean(modelFolderVO);
				msg = "���³ɹ�";
			}else{
				iMyHibernateDao.insertBean(modelFolderVO);
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
	public void delMoldFolder() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		ModelFolderVO modelFolderVO = iMyHibernateDao.selectBean(ModelFolderVO.class," where id= "+id);
		iMyHibernateDao.deleteBean(modelFolderVO);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("ɾ���ɹ�");
	}
	/**ģ�ʹ洢λ��-end**/
	
	/*********************************ģ��-start**************************/
	/**
	 * �б����
	 */
	public String listModel(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//Ʒ��
		request.setAttribute("proclasslist",iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0"));
		this.setUrl("../files/mold/mod-list-model.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: ���ģ��Json��
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getModelJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//�����ѯ
		String search_input = request.getParameter("searchinput");
		//Ʒ��
		String pro = request.getParameter("pro");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ");
		sql.append(" m.id,t.vname as typeName,m.vname,m.vfileupload,p.vname as vdef1");
		sql.append(" from fz_model m");
		sql.append(" left join fz_model_type t");
		sql.append(" on m.typeid=t.id");
		/**��ʾƷ��-start**/
		sql.append(" left join fz_model_folder f");
		sql.append(" on f.id = m.folderid");
		sql.append(" left join fz_tem_proclass p");
		sql.append(" on p.id= f.proclassid");
		/**��ʾƷ��-end**/
		sql.append(" where 1=1 ");
		
		//Ʒ���
		if(!"-1".equals(pro) && pro != null && !"".equals(pro)){
			sql.append(" and f.proclassid = "+pro);
		}
		
		if(search_input != null && !"".equals(search_input)){
			sql.append(" and ((m.vname like '%"+search_input+"%') or (t.vname like '%"+search_input+"%'))");
		}
		
		sql.append(" order by p.vname desc,t.vname asc");
	
		Integer total = getCountBySQL(sql.toString());
		List<ModelVO> stlist = findModelPage(sql.toString(), dgpage, rows);
		
		List<ModelVO> list = new ArrayList<ModelVO>();
		if(stlist != null){
			/*
			 * ����Զ���SQL����List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(stlist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				ModelVO dto = new ModelVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setTypeName(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVfileupload(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				//Ʒ������
				dto.setVdef1(CommUtil.isNullOrEm(arry[4]) ? null : arry[4].toString());
				
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
	public List<ModelVO> findModelPage(String sql, Integer dgpage,Integer rows) {
		
		int start = (dgpage - 1) * rows;
		int limit = rows;
		
		return (List<ModelVO>) iHibernateDAO.findListBySQL(sql, null, start,limit);
	}
	
	/**
	* @Description: ��������
	* @param @return
	* @return String
	 */
	public String addModel(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			modelVO = iMyHibernateDao.selectBean(ModelVO.class," where id ="+id);
		}
		//�洢λ��
		request.setAttribute("moldFolderList",getListMoldFolder());
		request.setAttribute("moldTypeList",iMyHibernateDao.selectBeanList(ModelTypeVO.class," where ifnull(dr,0)=0 "));
		this.setUrl("../files/mold/mod-add-model.jsp");
		return SUCCESS;
	}
	
	
	/**
	 * 
	* @Description: ���ģ�͹�����ϢJson��
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public void getModelInfoJson(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//�����ѯ
		String modelid = request.getParameter("modelid");
		
		if(modelid == null || "".equals(modelid))
			return ;
		
		List<ModelBVO> list = new ArrayList<ModelBVO>();
		Integer total_spe = 0;
		Integer total_acc = 0;
		//���ҹ������⵵�����ݵ�ids
		ModelBVO vo = iHibernateDAO.findFirst(ModelBVO.class, " modelid="+modelid);
		
		if(vo == null){
			return ;
		}
		//��������
		String vlinktype = vo.getVlinktype();
		
		
		//===========================================���⵵��=========================================
		if("mod_special".equals(vlinktype)){
			//��������-����
			String typeName_spe = vo.getVlinktype();
			StringBuffer ids_spe = new StringBuffer();
			
			String vdef1_spe = vo.getVdef1();
			if(vdef1_spe != null && !"".equals(vdef1_spe)){
				ids_spe.append(vdef1_spe);
			}
			String vdef2_spe = vo.getVdef2();
			if(vdef2_spe != null && !"".equals(vdef2_spe)){
				ids_spe.append(",").append(vdef2_spe);
			}
			String vdef3_spe = vo.getVdef3();
			if(vdef3_spe != null && !"".equals(vdef3_spe)){
				ids_spe.append(",").append(vdef3_spe);
			}
			
			//���⵵��
			StringBuffer sql = new StringBuffer();
			sql.append(" select ");
			sql.append(" s.id,d.vname as docvarietyname,s.vname");
			sql.append(" from fz_special s");
			sql.append(" left join fz_docvariety d");
			sql.append(" on s.docvarietyid=d.id");
			sql.append(" where ifnull(s.dr,0)=0 ");
			if(ids_spe != null && !"".equals(ids_spe)){
				sql.append(" and s.id in("+ids_spe+")");
			}else{
				sql.append(" and 1=2");
			}
			
			//���⵵��
			total_spe = getCountBySQL(sql.toString());
			List<SpecialVO> mblist_spec = (List<SpecialVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total_spe);
			
			if(mblist_spec != null){
				/*
				 * ����Զ���SQL����List<E>
				 */
				String jsonstr = ObjectToJSON.writeListJSON(mblist_spec);
				JSONArray jsonArr = JSONArray.fromObject(jsonstr);
				int size = jsonArr.size();
				for (int i = 0; i < size; i++) {
					ModelBVO dto = new ModelBVO();
					Object[] arry = jsonArr.getJSONArray(i).toArray();
					dto.setSpecialid(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
					dto.setVspecialTypeName(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
					dto.setVspecialName(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
					dto.setVlinktype(typeName_spe);
					list.add(dto);
				}
			}
		}
		
		//=================================================����======================================================
		if("mod_accessories".equals(vlinktype)){
			//��������-����
			String typeName_acc = vo.getVlinktype();
			StringBuffer ids_acc = new StringBuffer();
			
			String vdef1_acc = vo.getVdef1();
			if(vdef1_acc != null && !"".equals(vdef1_acc)){
				ids_acc.append(vdef1_acc);
			}
			String vdef2_acc = vo.getVdef2();
			if(vdef2_acc != null && !"".equals(vdef2_acc)){
				ids_acc.append(",").append(vdef2_acc);
			}
			String vdef3_acc = vo.getVdef3();
			if(vdef3_acc != null && !"".equals(vdef3_acc)){
				ids_acc.append(",").append(vdef3_acc);
			}
			//����
			StringBuffer sql_ = new StringBuffer();
			sql_.append(" select ");
			sql_.append(" a.id,d.vname as docvarietyname,a.vname");
			sql_.append(" from fz_auxiliary a");
			sql_.append(" left join fz_docvariety d");
			sql_.append(" on a.docvarietyid=d.id");
			sql_.append(" where ifnull(a.dr,0)=0 ");
			if(ids_acc != null && !"".equals(ids_acc)){
				sql_.append(" and a.id in("+ids_acc+")");
			}else{
				sql_.append(" and 1=2");
			}
			//����
			total_acc = getCountBySQL(sql_.toString());
			List<AuxiliaryVO> mblist_acc = (List<AuxiliaryVO>)iHibernateDAO.findListBySQL(sql_.toString(), null, 0, total_acc);
			
			if(mblist_acc != null){
				/*
				 * ����Զ���SQL����List<E>
				 */
				String jsonstr = ObjectToJSON.writeListJSON(mblist_acc);
				JSONArray jsonArr = JSONArray.fromObject(jsonstr);
				int size = jsonArr.size();
				for (int i = 0; i < size; i++) {
					ModelBVO dto = new ModelBVO();
					Object[] arry = jsonArr.getJSONArray(i).toArray();
					dto.setSpecialid(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
					dto.setVspecialTypeName(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
					dto.setVspecialName(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
					dto.setVlinktype(typeName_acc);
					list.add(dto);
				}
			}
		}
		
		//====================================����+����=============================================
		if("mod_special-mod_accessories".equals(vlinktype)){
			//��������-����
			String typeName_spe = vo.getVlinktype();
			StringBuffer ids_spe = new StringBuffer();
			String vdef1_spe = vo.getVdef1();
			if(vdef1_spe != null && !"".equals(vdef1_spe)){
				ids_spe.append(vdef1_spe);
			}
			//���⵵��
			StringBuffer sql = new StringBuffer();
			sql.append(" select ");
			sql.append(" s.id,d.vname as docvarietyname,s.vname");
			sql.append(" from fz_special s");
			sql.append(" left join fz_docvariety d");
			sql.append(" on s.docvarietyid=d.id");
			sql.append(" where ifnull(s.dr,0)=0 ");
			if(ids_spe != null && !"".equals(ids_spe)){
				sql.append(" and s.id in("+ids_spe+")");
			}else{
				sql.append(" and 1=2");
			}
			
			//���⵵��
			total_spe = getCountBySQL(sql.toString());
			List<SpecialVO> mblist_spec = (List<SpecialVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total_spe);
			
			if(mblist_spec != null){
				/*
				 * ����Զ���SQL����List<E>
				 */
				String jsonstr = ObjectToJSON.writeListJSON(mblist_spec);
				JSONArray jsonArr = JSONArray.fromObject(jsonstr);
				int size = jsonArr.size();
				for (int i = 0; i < size; i++) {
					ModelBVO dto = new ModelBVO();
					Object[] arry = jsonArr.getJSONArray(i).toArray();
					dto.setSpecialid(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
					dto.setVspecialTypeName(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
					dto.setVspecialName(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
					dto.setVlinktype(typeName_spe);
					list.add(dto);
				}
			}
			
			//��������-����
			String typeName_acc = vo.getVlinktype();
			StringBuffer ids_acc = new StringBuffer();

			String vdef2_acc = vo.getVdef2();
			if(vdef2_acc != null && !"".equals(vdef2_acc)){
				ids_acc.append(vdef2_acc);
			}
	
			//����
			StringBuffer sql_ = new StringBuffer();
			sql_.append(" select ");
			sql_.append(" a.id,d.vname as docvarietyname,a.vname");
			sql_.append(" from fz_auxiliary a");
			sql_.append(" left join fz_docvariety d");
			sql_.append(" on a.docvarietyid=d.id");
			sql_.append(" where ifnull(a.dr,0)=0 ");
			if(ids_acc != null && !"".equals(ids_acc)){
				sql_.append(" and a.id in("+ids_acc+")");
			}else{
				sql_.append(" and 1=2");
			}
			//����
			total_acc = getCountBySQL(sql_.toString());
			List<AuxiliaryVO> mblist_acc = (List<AuxiliaryVO>)iHibernateDAO.findListBySQL(sql_.toString(), null, 0, total_acc);
			
			if(mblist_acc != null){
				/*
				 * ����Զ���SQL����List<E>
				 */
				String jsonstr = ObjectToJSON.writeListJSON(mblist_acc);
				JSONArray jsonArr = JSONArray.fromObject(jsonstr);
				int size = jsonArr.size();
				for (int i = 0; i < size; i++) {
					ModelBVO dto = new ModelBVO();
					Object[] arry = jsonArr.getJSONArray(i).toArray();
					dto.setSpecialid(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
					dto.setVspecialTypeName(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
					dto.setVspecialName(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
					dto.setVlinktype(typeName_acc);
					list.add(dto);
				}
			}
		}
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("total", total_spe+total_acc);
		jsonmap.put("rows", list);
		renderJson(jsonmap);
	}
	
	/**
	 * @Description: ����
	 * @return void
	 */
	@Transactional
	public void saveModel(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//�ӱ�
			String subdata = request.getParameter("subdata");
			
			Integer id = modelVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "����ʧ��";
			
			if(id != null){
				iMyHibernateDao.updateBean(modelVO);
				msg = "���³ɹ�";
			}else{
				id = iHibernateDAO.saveReInt(modelVO);
				msg = "�����ɹ�";
			}
			
			/**�����ӱ�**/
			ModelBVO bvo = new ModelBVO();
			String vlink_spe = null;
			String vlink_acc = null;
			//��������
			bvo.setModelid(id);
			
			//���ݽ�������ת��VOList����
			List<ModelBVO> list_b = JsonUtils.getListFromJson(subdata, ModelBVO.class);
			if(list_b != null){
				int num = 1;
				//���ڼ�¼�������-��Ӧ�洢���ֶ�
				Map<String,String> mapField = new HashMap<String,String>();
				//����
				for (ModelBVO modelBVO : list_b) {
					//��������-�������⵵���븨��
					String linkType = modelBVO.getVlinktype();
					if("mod_special".equals(linkType)){//����
						vlink_spe = linkType;
						//bvo.setVlinktype(linkType);
						String values = new String();
						values += modelBVO.getSpecialid();
						//�������
						String typeName = modelBVO.getVspecialTypeName();
						//�ж��Ƿ��Ѽ�¼�õ�������
						if(mapField.containsKey(typeName)){
							//��ǰ���Ͷ�Ӧ�ֶε�ֵ
							String field = mapField.get(typeName);
							Object fieldValue = ReflectionUtils.getFieldValue(bvo, field);
							//��ֵ+��ֵ
							String value = fieldValue + "," + values;
							ReflectionUtils.setFieldValue(bvo, field, value);
						}else{
							//��̬�и�ֵ
							String field = "vdef"+num;
							ReflectionUtils.setFieldValue(bvo, field, values);
							mapField.put(typeName, field);
						}
						num++;
					}
				}
				
				//����
				for (ModelBVO modelBVO : list_b) {
					//��������-�������⵵���븨��
					String linkType = modelBVO.getVlinktype();
					if("mod_accessories".equals(linkType)){//����
						vlink_acc = linkType;
						//bvo.setVlinktype(linkType);
						String values = new String();
						values += modelBVO.getSpecialid();
						//�������
						String typeName = modelBVO.getVspecialTypeName();
						//�ж��Ƿ��Ѽ�¼�õ�������
						if(mapField.containsKey(typeName)){
							//��ǰ���Ͷ�Ӧ�ֶε�ֵ
							String field = mapField.get(typeName);
							Object fieldValue = ReflectionUtils.getFieldValue(bvo, field);
							//��ֵ+��ֵ
							String value = fieldValue + "," + values;
							ReflectionUtils.setFieldValue(bvo, field, value);
						}else{
							//��̬�и�ֵ
							String field = "vdef"+num;
							ReflectionUtils.setFieldValue(bvo, field, values);
							mapField.put(typeName, field);
						}
						num++;
					}
				}
				mapField.clear();
			}
			
			String vlinktype = null;
			if(vlink_spe != null && vlink_acc != null){
				vlinktype = vlink_spe + "-" + vlink_acc;
			}else{
				if(vlink_spe != null){
					vlinktype = vlink_spe;
				}else{
					vlinktype = vlink_acc;
				}
			}
			
			bvo.setVlinktype(vlinktype);
			iHibernateDAO.deleteAllByCondition(ModelBVO.class, " modelid="+id);
			iHibernateDAO.save(bvo);
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
	public void delModel() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		try {
			ModelVO modelVO = iMyHibernateDao.selectBean(ModelVO.class," where id= "+id);
			iMyHibernateDao.deleteBean(modelVO);
			iHibernateDAO.deleteAllByCondition(ModelBVO.class, " modelid="+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("ɾ���ɹ�");
	}
	/**ģ��-end**/
	
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
	public ModelTypeVO getModelTypeVO() {
		return modelTypeVO;
	}
	public void setModelTypeVO(ModelTypeVO modelTypeVO) {
		this.modelTypeVO = modelTypeVO;
	}
	public ModelVO getModelVO() {
		return modelVO;
	}
	public void setModelVO(ModelVO modelVO) {
		this.modelVO = modelVO;
	}
	public ModelBVO getModelBVO() {
		return modelBVO;
	}
	public void setModelBVO(ModelBVO modelBVO) {
		this.modelBVO = modelBVO;
	}

	public ModelFolderVO getModelFolderVO() {
		return modelFolderVO;
	}

	public void setModelFolderVO(ModelFolderVO modelFolderVO) {
		this.modelFolderVO = modelFolderVO;
	}
	
}
