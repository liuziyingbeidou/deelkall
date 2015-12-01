package action;

import itf.pub.IConstant;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import model.BomVO;
import model.auxiliary.AuxiliaryVO;
import model.basedoc.BaseDocSoVO;
import model.basedoc.BaseDocVO;
import model.bomtb.BtcconfigBVO;
import model.bomtb.CfeedBVO;
import model.mold.ModelVO;
import model.parts.MasterVO;
import model.parts.SubPartBVO;
import model.parts.SubPartVO;
import model.proclass.ProclassVO;
import model.proclass.TemClassVO;
import model.proclass.TemSubclassVO;
import model.scheme.SchemeBVO;
import model.scheme.SchemeVO;
import model.size.LtinfoVO;
import model.size.SizeDocVO;
import model.size.StinfoBVO;
import model.size.StinfoVO;
import model.sp.system.DiyInfoVO;
import model.sp.system.SpUserVO;
import model.special.SpecialVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.tools.ant.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import util.CommUtil;
import util.HttpTools;
import util.ObjectToJSON;
import utils.DateUtil;
import utils.JsonUtils;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;


public class InterfaceAction extends BaseAction implements Serializable {

	private static final long serialVersionUID = 2898642732111884884L;
	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";	
	
	/**
	 * 3．获取色系列表（getColorList）
	 */
	public void getColorList() {
		List<BaseDocVO> colourlist = iMyHibernateDao.selectBeanList(BaseDocVO.class," where ifnull(dr,0)=0 and vdoctype='"+IConstant.DOC_COLOUR+"'");
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5","dprocefeemny","dprocecoefmny","vdoctype"};
		renderJson(colourlist,JsonUtils.configJson(excludes));
	}

	/**
	 * 5.获取面料列表（getFabricList）
	 * pid , tid , cid : -1/null代表全部
	 */
	@SuppressWarnings("unchecked")
	public void getFabricList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//辨别是否为手机端请求
		String deviceType = request.getParameter("deviceType");
		//过滤普通用户与店员
		String user = request.getParameter("user");
		//品类id
		String typeId = request.getParameter("typeId");
		//花型
		String pid = request.getParameter("patternId");
		//成份
		String tid = request.getParameter("ingredientId");
		//色系
		String cid = request.getParameter("colourId");
		/**20151022 add by liuzy 价格并列关系**/
		//查询类型
		String type = request.getParameter("type");
		//查询内容/起始价格
		String inputText = request.getParameter("inputText");
		//末价格
		String inputText_ = request.getParameter("inputText_");
		//工艺id
		String processId = request.getParameter("processId");
		StringBuffer wh = new StringBuffer(" and 1=1");
		if(typeId != null && !"".equals(typeId)){
			wh.append(" and f.proclassids like '%"+typeId+"%'");
		}
		if(!"-1".equals(pid) && pid != null){
			wh.append(" and f.patternid="+pid);
		}
		if(!"-1".equals(tid) && tid != null){
			wh.append(" and f.ingredientid="+tid);
		}
		if(!"-1".equals(cid) && cid != null){
			wh.append(" and f.colourid="+cid);
		}
		if(deviceType != null && !"".equals(deviceType)){
			wh.append(" and f.deviceType="+deviceType);
		}
		//价格区间 add by liuzy 2015-10-22
		if(inputText != null && !"".equals(inputText)){
			if("pattern".equals(type)){//花型
				wh.append(" and p.vname like '%"+inputText+"%'");
			}else
			if("ingredient".equals(type)){//成份
				wh.append(" and i.vname like '%"+inputText+"%'");
			}else
			if("color".equals(type)){//色系
				wh.append(" and c.vname like '%"+inputText+"%'");
			}else 
			if("code".equals(type)){
				wh.append(" and f.vcode like '%"+inputText+"%'");
			}else
			if("brand".equals(type)){//品牌 add by liuzy 2015-08-28
				wh.append(" and b.vname like '%"+inputText+"%'");
			}else 
			if("price".equals(type)){//价格区间 add by liuzy 2015-08-28
				//采购价
				Double[] min_max = calAccount(typeId, processId, inputText, inputText_);
				if(min_max.length > 0){
					wh.append(" and f.dpurchasemny >="+min_max[0]+" and f.dpurchasemny <="+min_max[1]);
				}
			}
		}
			
		if(!"1".equals(deviceType)){
			if(user == null || "".equals(user)){
				wh.append(" and f.isClient=1");
			}
			wh.append(" and f.isRelease=1");
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" f.id,f.vcode,p.vname as patternName,i.vname as ingredientName,c.vname as colourName,f.bisupload,f.ambient,f.specular,f.vrank,f.vfileupload,f.specname,f.vname,f.dpurchasemny,f.iautype");
		//添加成分、色系、花型编码
		sql.append(",p.vcode as patternCode,i.vcode as ingredientCode,c.vcode as colourCode");
		sql.append(" from fz_auxiliary f");
		sql.append(" left join fz_base_doc p ");
		sql.append(" on f.patternid = p.id");
		sql.append(" left join fz_base_doc_so i");
		sql.append(" on f.ingredientid = i.id");
		sql.append(" left join fz_base_doc c");
		sql.append(" on f.colourid = c.id");
		sql.append(" where ifnull(f.dr,0)=0");
		sql.append(" and i.vmoduletype='"+IConstant.MOD_FABRIC+"'");
		sql.append(" and i.vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		sql.append(" and p.vdoctype='"+IConstant.DOC_PATTERN+"'");
		sql.append(" and c.vdoctype='"+IConstant.DOC_COLOUR+"'");
		sql.append(" and f.vmoduletype='"+IConstant.MOD_FABRIC+"'");
		sql.append(wh);
		
		Integer total = getCountBySQL(sql.toString());
		List<AuxiliaryVO> fabriclist = (List<AuxiliaryVO>) iHibernateDAO.findListBySQL(sql.toString(),null,0,total);
		List<AuxiliaryVO> list = new ArrayList<AuxiliaryVO>();
		if(fabriclist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(fabriclist);
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
				dto.setBisupload(CommUtil.isNullOrEm(arry[5]) ? null : Integer.valueOf(arry[5].toString()));
				//f.ambient,f.specular,f.vrank,f.daccountmny,f.dcoefficientmny
				dto.setAmbient(CommUtil.isNullOrEm(arry[6]) ? null : arry[6].toString());
				dto.setSpecular(CommUtil.isNullOrEm(arry[7]) ? null : arry[7].toString());
				dto.setVrank(CommUtil.isNullOrEm(arry[8]) ? null : arry[8].toString());
				//f.vfileupload,f.specname,f.vname
				dto.setVfileupload(CommUtil.isNullOrEm(arry[9]) ? null : arry[9].toString());
				dto.setSpecname(CommUtil.isNullOrEm(arry[10]) ? null : arry[10].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[11]) ? null : arry[11].toString());
				//f.dpurchasemny
				dto.setDpurchasemny(CommUtil.isNullOrEm(arry[12]) ? 0.00 : Double.valueOf(arry[12].toString()));
				//liuzy-151015面料细分类
				dto.setIautype(CommUtil.isNullOrEm(arry[13]) ? null : Integer.valueOf(arry[13].toString()));
				if("1".equals(arry[13]+"")){
					dto.setVdef3("梭织面料");
				}else if("2".equals(arry[13]+"")){
					dto.setVdef3("针织面料");
				}else if("3".equals(arry[13]+"")){
					dto.setVdef3("毛皮类");
				}else if("4".equals(arry[13]+"")){
					dto.setVdef3("皮革类");
				}
				dto.setPatternCode(CommUtil.isNullOrEm(arry[14]) ? null : arry[14].toString());
				dto.setIngredientCode(CommUtil.isNullOrEm(arry[15]) ? null : arry[15].toString());
				dto.setColourCode(CommUtil.isNullOrEm(arry[16]) ? null : arry[16].toString());
				
				list.add(dto);
			}
		}
		
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef4","vdef5","icisBagging","icisButton","icisComponent","icisLine","icisLining","specid","subpartids","useid","usename","vcisBaggingName","vcisButtonName","vcisLiningName","vfrom","vmemo","vmoduletype","daccountmny"};
		renderJson(list,JsonUtils.configJson(excludes));
	}

	/**
	 * 获取面料工艺列表（getFabricProcessList）
	 */
	public void getFabricProcessList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//品类id
		String typeId = request.getParameter("typeId");
		StringBuffer wh = new StringBuffer();
		wh.append(" where ifnull(dr,0)=0 and vdoctype='"+IConstant.DOC_TECHNOLOGY+"'");
		if(typeId != null && !"".equals(typeId)){
			wh.append(" and proclassids like '%"+typeId+"%'");
		}
		List<BaseDocVO> processlist = iMyHibernateDao.selectBeanList(BaseDocVO.class,wh.toString());
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(processlist,JsonUtils.configJson(excludes));
	}

	/**
	 * 2.获取成分列表（getIngredientList）
	 */
	public void getIngredientList() {
		List<BaseDocSoVO> ingredientlist = iMyHibernateDao.selectBeanList(BaseDocSoVO.class," where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_FABRIC+"' and vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5","vmoduletype","vdoctype","varietyid","varietyName"};
		renderJson(ingredientlist,JsonUtils.configJson(excludes));
	}

	/**
	 * 1.获取花型列表（getPatternList）
	 */
	public void getPatternList() {
		List<BaseDocVO> patternlist = iMyHibernateDao.selectBeanList(BaseDocVO.class," where ifnull(dr,0)=0 and matechildid=1 and vdoctype='"+IConstant.DOC_PATTERN+"'");
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(patternlist,JsonUtils.configJson(excludes));
	}
	
	/**
	 * 
	* @Description: 通过面料code获得面料数据（高光与环境光等）
	* @param 
	* @return void
	 */
	public void searchFabricByCode(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String vcode = request.getParameter("vcode");
		StringBuffer sql = new StringBuffer();
		sql.append(" where ifnull(dr,0)=0 and vmoduletype='"+IConstant.MOD_FABRIC+"'");
		if(vcode != null){
			sql.append(" and vcode='"+vcode+"'");
		}else{
			sql.append(" and 1=2");
		}
		List<AuxiliaryVO> fabriclist = iMyHibernateDao.selectBeanList(AuxiliaryVO.class,sql.toString());
		String[] excludes = new String[]{"patternName","ingredientName","colourName","vmemo","class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(fabriclist,JsonUtils.configJson(excludes));
	}
	
	/**
	 * @Description: 根据定价区间求取核算价区间
	 * @param @param typeId 品类id
	 * @param @param processId 工艺id
	 * @param @param inputText 起始价格
	 * @param @param inputText_ 末价格
	 * @return Double[]
	 */
	public Double[] calAccount(String typeId,String processId,String inputText,String inputText_){
		Double[] min_max = new Double[]{0.0,0.0};
		if(typeId != null && !"".equals(typeId)){
			SchemeVO schemeVO = iHibernateDAO.findFirst(SchemeVO.class, " proclassid="+typeId);
			if(schemeVO != null){
				//区别是定制价or成品价
				String type = schemeVO.getType();
				Integer schemeid = schemeVO.getId();
				String wh = " schemeid="+schemeid;
				if(processId != null && !"".equals(processId)){
					wh += " and basedocid="+processId;
				}
				//定制
				if("sch_dz".equals(type)){
					 List<SchemeBVO> schList = iHibernateDAO.findAll(SchemeBVO.class, wh);
					 if(schList != null){
						 TreeMap<Double, String>  mapPrice = getAllDZPrice(schList);
						 if(mapPrice != null){
							 //最小值
							 min_max[0] = Double.valueOf(getDZAccountPMIN(mapPrice,inputText))-99;
							//最大值
							 min_max[1] = Double.valueOf(getDZAccountPMAX(mapPrice,inputText_));
						 }
					 }
				}else if("sch_cp".equals(type)){
					List<SchemeBVO> schList = iHibernateDAO.findAll(SchemeBVO.class, wh);
					if(schList != null){
						TreeMap<Double, String> mapcp = new TreeMap<Double, String>();
						for(int i = 0; i < schList.size(); i++){
							SchemeBVO vo = schList.get(i);
							mapcp.put(vo.getDfproductmny(),vo.getDsmall_purchasemny()+"-"+vo.getDbig_purchasemny());
						}
						//最小值
						 min_max[0] = Double.valueOf(getDZAccountPMIN(mapcp,inputText).split("-")[0]);
						//最大值
						 min_max[1] = Double.valueOf(getDZAccountPMAX(mapcp,inputText_).split("-")[1]);
					}
				}
			}
		}
		return min_max;
	}
	
	/**
	 * @Description: 获得核算价(最小面料采购价) by 价格
	 * @param @return
	 * @return Double
	 */
	public String getDZAccountPMIN(TreeMap<Double, String>  mapPrice,String sprice){
		 String upPrice = "0";
		 for(int i = 0; i < mapPrice.size(); i++){
			 Iterator it = mapPrice.entrySet().iterator();
			 while(it.hasNext()){
				Entry kv = (Entry)it.next();
				if(kv.getValue() != null){
					Double k = (Double)kv.getKey();
					int c = Double.compare(Double.valueOf(sprice),k);
					if(c > 0){
						upPrice = kv.getValue()+"";
					}else{
						return kv.getValue()+"";
					}
				}
			 }
		 }
		 return upPrice;
	}
	
	/**
	 * @Description: 获得核算价(最大面料采购价) by 价格
	 * @param @return
	 * @return Double
	 */
	public String getDZAccountPMAX(TreeMap<Double, String>  mapPrice,String sprice){
		 String upPrice = "0";
		 for(int i = 0; i < mapPrice.size(); i++){
			 Iterator it = mapPrice.entrySet().iterator();
			 while(it.hasNext()){
				Entry kv = (Entry)it.next();
				if(kv.getValue() != null){
					Double k = (Double)kv.getKey();
					int c = Double.compare(Double.valueOf(sprice),k);
					if(c > 0){
						upPrice = kv.getValue()+"";
					}else{
						return upPrice;
					}
				}
			 }
		 }
		 
		 return upPrice;
	}
	
	/**
	 * 
	* @Description: 面料搜索获得面料数据
	* @param 
	* @return void
	 */
	public void searchFabricByType(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//品类id
		String typeId = request.getParameter("typeId");
		//查询类型
		String type = request.getParameter("type");
		//查询内容/起始价格
		String inputText = request.getParameter("inputText");
		//末价格
		String inputText_ = request.getParameter("inputText_");
		//工艺id
		String processId = request.getParameter("processId");
		//登录用户--用于过滤普通用户和门店店员
		String user = request.getParameter("user");
		
		StringBuffer wh = new StringBuffer(" and 1=1");
		if(typeId != null && !"".equals(typeId)){
			wh.append(" and f.proclassids like '%"+typeId+"%'");
		}
		if(inputText != null && !"".equals(inputText)){
			if("pattern".equals(type)){//花型
				wh.append(" and p.vname like '%"+inputText+"%'");
			}else
			if("ingredient".equals(type)){//成份
				wh.append(" and i.vname like '%"+inputText+"%'");
			}else
			if("color".equals(type)){//色系
				wh.append(" and c.vname like '%"+inputText+"%'");
			}else 
			if("code".equals(type)){
				wh.append(" and f.vcode like '%"+inputText+"%'");
			}else
			if("brand".equals(type)){//品牌 add by liuzy 2015-08-28
				wh.append(" and b.vname like '%"+inputText+"%'");
			}else 
			if("price".equals(type)){//价格区间 add by liuzy 2015-08-28
				//采购价
				Double[] min_max = calAccount(typeId, processId, inputText, inputText_);
				if(min_max.length > 0){
					wh.append(" and f.dpurchasemny >="+min_max[0]+" and f.dpurchasemny <="+min_max[1]);
				}
			}
		}
		// add by liuzy 2015-08-31
		if(user == null || "".equals(user)){
			wh.append(" and f.isClient=1");
		}
		wh.append(" and f.isRelease=1");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" f.id,f.vcode,p.vname as patternName,i.vname as ingredientName,c.vname as colourName,f.bisupload,f.ambient,f.specular,f.vrank,f.vfileupload,f.specname,f.vname,f.dpurchasemny,f.iautype");
		//添加成分、色系、花型编码
		sql.append(",p.vcode as patternCode,i.vcode as ingredientCode,c.vcode as colourCode");
		sql.append(" from fz_auxiliary f");
		sql.append(" left join fz_base_doc p ");
		sql.append(" on f.patternid = p.id");
		sql.append(" left join fz_base_doc_so i");
		sql.append(" on f.ingredientid = i.id");
		sql.append(" left join fz_base_doc c");
		sql.append(" on f.colourid = c.id");
		/**品牌-start**/
		if("brand".equals(type)){//品牌 add by liuzy 2015-08-28
			sql.append(" left join fz_base_doc b");
			sql.append(" on f.basedocId = b.id");
		}
		/**品牌-end**/
		sql.append(" where ifnull(f.dr,0)=0");
		sql.append(" and i.vmoduletype='"+IConstant.MOD_FABRIC+"'");
		sql.append(" and i.vdoctype='"+IConstant.DOC_INGREDIENTS+"'");
		sql.append(" and p.vdoctype='"+IConstant.DOC_PATTERN+"'");
		sql.append(" and c.vdoctype='"+IConstant.DOC_COLOUR+"'");
		sql.append(" and f.vmoduletype='"+IConstant.MOD_FABRIC+"'");
		if("brand".equals(type)){//品牌 add by liuzy 2015-08-28
			sql.append(" and b.vdoctype='"+IConstant.DOC_FAR_BRAND+"'");
		}
		sql.append(wh);
		
		Integer total = getCountBySQL(sql.toString());
		List<AuxiliaryVO> farlist = (List<AuxiliaryVO>) iHibernateDAO.findListBySQL(sql.toString(),null,0,total);
		List<AuxiliaryVO> list = new ArrayList<AuxiliaryVO>();
		if(farlist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(farlist);
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
				dto.setBisupload(CommUtil.isNullOrEm(arry[5]) ? null : Integer.valueOf(arry[5].toString()));
				//f.ambient,f.specular,f.vrank
				dto.setAmbient(CommUtil.isNullOrEm(arry[6]) ? null : arry[6].toString());
				dto.setSpecular(CommUtil.isNullOrEm(arry[7]) ? null : arry[7].toString());
				dto.setVrank(CommUtil.isNullOrEm(arry[8]) ? null : arry[8].toString());
				//f.vfileupload,f.specname,f.vname
				dto.setVfileupload(CommUtil.isNullOrEm(arry[9]) ? null : arry[9].toString());
				dto.setSpecname(CommUtil.isNullOrEm(arry[10]) ? null : arry[10].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[11]) ? null : arry[11].toString());
				//f.dpurchasemny
				dto.setDpurchasemny(CommUtil.isNullOrEm(arry[12]) ? 0.00 : Double.valueOf(arry[12].toString()));
				//liuzy-151015面料细分类
				dto.setIautype(CommUtil.isNullOrEm(arry[13]) ? null : Integer.valueOf(arry[13].toString()));
				if("1".equals(arry[13]+"")){
					dto.setVdef3("梭织面料");
				}else if("2".equals(arry[13]+"")){
					dto.setVdef3("针织面料");
				}else if("3".equals(arry[13]+"")){
					dto.setVdef3("毛皮类");
				}else if("4".equals(arry[13]+"")){
					dto.setVdef3("皮革类");
				}
				
				dto.setPatternCode(CommUtil.isNullOrEm(arry[14]) ? null : arry[14].toString());
				dto.setIngredientCode(CommUtil.isNullOrEm(arry[15]) ? null : arry[15].toString());
				dto.setColourCode(CommUtil.isNullOrEm(arry[16]) ? null : arry[16].toString());
				list.add(dto);
			}
		}
		
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5","icisBagging","icisButton","icisComponent","icisLine","icisLining","specid","subpartids","useid","usename","vcisBaggingName","vcisButtonName","vcisLiningName","vfrom","vmemo","vmoduletype","daccountmny"};
		renderJson(list,JsonUtils.configJson(excludes));
	}
	
	/**
	 * 
	* @Description: 根据面料id保存参数数据
	* @param fabricId，ambient，specular
	* @return 返回值0（失败）,1（成功）
	 */
	public void saveFabricParam(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String fabricId = request.getParameter("fabricId");
		String ambient = request.getParameter("ambient");
		String specular = request.getParameter("specular");
		
		try {
			if(fabricId != null){
				AuxiliaryVO fabricVO = iMyHibernateDao.selectBean(AuxiliaryVO.class," where vmoduletype='"+IConstant.MOD_FABRIC+"' and id ="+fabricId);
				if(CommUtil.isNullOrEm(ambient)){
					ambient="1";
				}
				if(CommUtil.isNullOrEm(specular)){
					specular="0.1";
				}
				fabricVO.setAmbient(ambient);
				fabricVO.setSpecular(specular);
				iMyHibernateDao.updateBean(fabricVO);
				renderText("1");
			}else{
				renderText("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderText("0");
		}
	}
	
	/******************************************/
	
	
	/**
	* @Description: 获取主品类列表（getMainTypeList）上衣、裤、衬衣
	* @param String classId 总分类
	* @return String
	 */
	public void getMainTypeList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String classId = request.getParameter("classId");
		
		TemClassVO vo = iHibernateDAO.findFirst(TemClassVO.class, " vname='"+IConstant.MENSWEAR_zh_CH+"'");
		List<TemSubclassVO> processlist = iMyHibernateDao.selectBeanList(TemSubclassVO.class," where ifnull(dr,0)=0 and classid="+vo.getId());
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(processlist,JsonUtils.configJson(excludes));
	}
	
	/**
	* @Description: 获取品类列表（getTypeList） 
	* @param  classId 总分类,mainTypeId 大品类
	* @return String
	 */
	public void getTypeList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String mainTypeId = request.getParameter("mainTypeId");
		
		TemClassVO vo = iHibernateDAO.findFirst(TemClassVO.class, " vname='"+IConstant.MENSWEAR_zh_CH+"'");
		List<ProclassVO> processlist = iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0 and classid="+vo.getId()+" and subclassid="+mainTypeId);
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(processlist,JsonUtils.configJson(excludes));
	}
	
	/**
	* @Description: 获取品类列表（getFullTypeList） 
	* @des deviceType : 0 （PC端）、1（移动端）
	* @param @return
	* @return String
	 */
	public void getFullTypeList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//String mainTypeId = request.getParameter("mainTypeId");
		//辨别是否为手机端请求
		String deviceType = request.getParameter("deviceType");
		
		TemClassVO vo = iHibernateDAO.findFirst(TemClassVO.class, " vname='"+IConstant.MENSWEAR_zh_CH+"'");
		//大品类
		List<TemSubclassVO> subClasslist = iMyHibernateDao.selectBeanList(TemSubclassVO.class," where ifnull(dr,0)=0 and classid="+vo.getId() +" order by isort asc");
		//品类
		List<ProclassVO> processlist = iMyHibernateDao.selectBeanList(ProclassVO.class," where ifnull(dr,0)=0 and classid="+vo.getId());
		
		List jsonlist = new ArrayList();
		
		if(subClasslist != null && subClasslist.size() > 0){
			
			for(int i = 0; i < subClasslist.size(); i++){
				Map<String, Object> jsonmap = new HashMap<String, Object>();
				TemSubclassVO subVO = subClasslist.get(i);
				Integer id = subVO.getId();
				jsonmap.put("id", id);
				jsonmap.put("vcode", subVO.getVcode());
				jsonmap.put("vname", subVO.getVname());
				if(processlist != null && processlist.size() > 0){
					List temlist = new ArrayList();
					for(int j = 0; j < processlist.size(); j++){
						ProclassVO proVO = processlist.get(j);
						Integer subclssid = proVO.getSubclassid();
						if(subclssid == null){
							continue;
						}
						if(subclssid.equals(id)){
							temlist.add(proVO);
							//subClasslist.remove(i);
						}
					}
//					if(temlist.size() > 0){
//						jsonmap.put("child", temlist);
//					}
					jsonmap.put("child", temlist);
				}
				jsonlist.add(jsonmap);
			}
		}
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(jsonlist,JsonUtils.configJson(excludes));
	}
	
	/**
	* @Description: 获取主部件表（getMainPartList）型款、外袋、开衩
	* @param typeId 品类id
	* @return String
	 */
	public void getMainPartList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeId = request.getParameter("typeId");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" m.id,m.vcode,m.vname ");
		sql.append(" from fz_tem_proclass_b pb");
		sql.append(" left join ");
		sql.append(" fz_tem_master m");
		sql.append(" on pb.masterid=m.id");
		sql.append(" where pb.proclassid="+typeId);
		sql.append(" order by m.isort asc");
		
		Integer total = getCountBySQL(sql.toString());
		List<MasterVO> mainPartList = (List<MasterVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);
		List<MasterVO> list = new ArrayList<MasterVO>();
		if(mainPartList != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(mainPartList);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				MasterVO dto = new MasterVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				
				list.add(dto);
			}
		}
		
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(list,JsonUtils.configJson(excludes));
	}
	/**
	* @Description: 获取子部件（getSubPartList）
	* @param typeId 品类，mainPartId 主部件
	* @return String
	 */
	public void getSubPartList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeId = request.getParameter("typeId");
		String mainPartId = request.getParameter("mainPartId");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" sub.id,sub.vcode,sub.vname,sub.vdatasource,sub.vlinktype,sub.bisgyxj,sub.bisbom ");
		sql.append(" from fz_tem_proclass_bb pbb");
		/*sql.append(" left join ");
		sql.append(" fz_tem_proclass_b pb");
		sql.append(" on pbb.proclass_bid=pb.id");*/
		sql.append(" left join fz_tem_subpart sub");
		sql.append(" on pbb.subpartid=sub.id");
		sql.append(" where pbb.proclassid="+typeId);
		sql.append(" and pbb.masterid="+mainPartId);
		sql.append(" and ifnull(pbb.dr,0)=0");
		sql.append(" and ifnull(sub.dr,0)=0");
		sql.append(" order by sub.isort asc");
		
		Integer total = getCountBySQL(sql.toString());
		List<SubPartVO> subPartList = (List<SubPartVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);
		List<SubPartVO> list = new ArrayList<SubPartVO>();
		if(subPartList != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(subPartList);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SubPartVO dto = new SubPartVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVdatasource(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				dto.setVlinktype(CommUtil.isNullOrEm(arry[4]) ? null : arry[4].toString());
				dto.setBisgyxj(CommUtil.isNullOrEm(arry[5]) ? null : Integer.valueOf(arry[5].toString()));
				dto.setBisbom(CommUtil.isNullOrEm(arry[6]) ? null : Integer.valueOf(arry[6].toString()));
				
				list.add(dto);
			}
		}
		
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(list,JsonUtils.configJson(excludes));
	}
	/**
	* @Description: 获取子部件类型（getSubPartType）
	* @param @return
	* @return String
	 */
	public void getSubPartType(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeId = request.getParameter("typeId");
		String mainPartId = request.getParameter("mainPartId");
		String subPartId = request.getParameter("subPartId");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ");
		sql.append(" spb.id,spb.vmoduletype,spb.docvarietyid,spb.vcolorOrpatch,spb.vlin ,spb.vdef2");
		sql.append(" from fz_tem_proclass_bb pbb");
		sql.append(" left join ");
		sql.append(" fz_tem_subpart_b spb");
		sql.append(" on spb.subpartid=pbb.subpartid");
		/**add by liuzy 2015-08-10关联品类-start**/
		//在新增品类的时候选择的关联关系，孙表中保存当前品类与相关联的子部件
		//sql.append(" left join fz_tem_subpart sp");
		//sql.append(" on sp.id = spb.subpartid");
		/**add by liuzy 2015-08-10关联品类-end**/
		sql.append(" where ifnull(pbb.dr,0)=0");
		sql.append(" and ifnull(spb.dr,0)=0");
		if(typeId != null){
			sql.append(" and pbb.proclassid="+typeId);//+ " or sp.proclassids like '%"+typeId+"%')"
		}
		if(mainPartId != null){
			sql.append(" and pbb.masterid="+mainPartId);
		}
		if(subPartId != null){
			sql.append(" and pbb.subpartid="+subPartId);
		}
		if(typeId == null && mainPartId == null && subPartId == null){
			sql.append(" and 1=2");
		}
		
		Integer total = getCountBySQL(sql.toString());
		List<SubPartBVO> subPartTypeList = (List<SubPartBVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);
		
		List<SubPartBVO> list = new ArrayList<SubPartBVO>();
		if(subPartTypeList != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(subPartTypeList);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SubPartBVO dto = new SubPartBVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVmoduletype(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setDocvarietyid(CommUtil.isNullOrEm(arry[2]) ? null : Integer.valueOf(arry[2].toString()));
				dto.setVcolorOrpatch(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				dto.setVlin(CommUtil.isNullOrEm(arry[4]) ? null : arry[4].toString());
				dto.setVdef2(CommUtil.isNullOrEm(arry[5]) ? null : arry[5].toString());
				list.add(dto);
			}
		}
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		
		if(list != null){
			int size = list.size();
			for(int i = 0 ; i < size; i++){
				SubPartBVO subPartBVO = list.get(i);
				String moduletype = subPartBVO.getVmoduletype();
				//所选档案类别
				int doctypeid;
				//里料选择
				String selLin;
				//面料选择
				String selFra;
				//add by liuzy 20150930线用途
				String use;
				if(IConstant.MOD_ACCESSORIES.equals(moduletype)){//辅料
					doctypeid = subPartBVO.getDocvarietyid();
					use = subPartBVO.getVdef2();
					
					String wh = " vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and docvarietyid="+doctypeid+ " and proclassids like '%"+typeId+"%'";
					if((!CommUtil.isNull(use)) && !"-1".equals(use)){
						wh += " and useid ="+use;
					}
					
					//add by liuzy 2015-08-08 添加品类的过滤 typeId 
					List<AuxiliaryVO> acclist = iHibernateDAO.findAll(AuxiliaryVO.class, wh);
					jsonmap.put("mod_accessories", acclist);
				}else if(IConstant.MOD_SPECIAL.equals(moduletype)){//特殊档案
					doctypeid = subPartBVO.getDocvarietyid();
					List<SpecialVO> speclist = iHibernateDAO.findAll(SpecialVO.class, " docvarietyid="+doctypeid);
					jsonmap.put("mod_special", speclist);
				}else if(IConstant.MOD_LINING.equals(moduletype)){//里料
					selLin = subPartBVO.getVlin();
					String wh = null;
					if("all".equals(selLin)){//全部
						wh = " and 1=1";
					}else{//无
						wh = " and 1=2";
					}
					List<AuxiliaryVO> speclist = iHibernateDAO.findAll(AuxiliaryVO.class, " vmoduletype='"+IConstant.MOD_LINING+"' and subpartids like '%"+subPartId+"%'" +wh);
					jsonmap.put("mod_lining", speclist);
				}else if(IConstant.MOD_FABRIC.equals(moduletype)){//面料
					selFra = subPartBVO.getVcolorOrpatch();
					String wh = null;
					
					if("all".equals(selFra)){//全部
						wh = " and 1=1";
					}else if("c".equals(selFra)){//撞色
						wh = " and biscontrastcolor=1";// and ifnull(bispatch,0)=0
					}else if("p".equals(selFra)){//贴布
						wh = " and bispatch=1";// ifnull(biscontrastcolor,0)=0
					}else if("cp".equals(selFra)){//撞色and贴布
						wh = " and biscontrastcolor=1 and bispatch=1";
					}else{
						wh = " and 1=2";
					}
					
					//add by liuzy 2015-08-08 添加品类的过滤 typeId 
					List<AuxiliaryVO> speclist = iHibernateDAO.findAll(AuxiliaryVO.class, " vmoduletype='"+IConstant.MOD_FABRIC+"' and proclassids like '%"+typeId+"%'" +wh);
					jsonmap.put("mod_fabric", speclist);
				}else if(IConstant.MOD_OUTSOURCE.equals(moduletype)){//配饰
					doctypeid = subPartBVO.getDocvarietyid();
					List<AuxiliaryVO> outlist = iHibernateDAO.findAll(AuxiliaryVO.class, " vmoduletype='"+IConstant.MOD_OUTSOURCE+"' and docvarietyid="+doctypeid);
					jsonmap.put("mod_outsource", outlist);
				}
			}
		}
		List templist = new ArrayList();
		if(!jsonmap.containsKey("mod_accessories")){
			jsonmap.put("mod_accessories", templist);
		}
		if(!jsonmap.containsKey("mod_special")){
			jsonmap.put("mod_special", templist);
		}
		if(!jsonmap.containsKey("mod_lining")){
			jsonmap.put("mod_lining", templist);
		}
		if(!jsonmap.containsKey("mod_fabric")){
			jsonmap.put("mod_fabric", templist);
		}
		if(!jsonmap.containsKey("mod_outsource")){
			jsonmap.put("mod_outsource", templist);
		}
		
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5","icisBagging","icisButton","icisComponent","icisLine","icisLining","specid",
				"subpartids","useid","usename","vcisBaggingName","vcisButtonName","vcisLiningName","vfrom","vmemo",
				"vmoduletype","icisLine","vcisLineName","icisButton","vcisButtonName","icisComponent","vcisComponentName",
				"icisLining","vcisLiningName","icisBagging","vcisBaggingName"};
		renderJson(jsonmap,JsonUtils.configJson(excludes));
		
	}
	
	/**
	* @Description: 
	* 				根据品类id首先获得品类对应的子部件，
	* 				然后根据子部件查找对应的加载数据
	* @param typeId
	* @return void
	 */
	@SuppressWarnings("unchecked")
	public void getDefaultStyle(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeId = request.getParameter("typeId");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ");
		sql.append(" s.id,s.vname,s.vsname,s.vdatasource,s.bisgyxj,s.bisbom");
		sql.append(" from fz_tem_proclass_bb pbb");
		sql.append(" left join ");
		sql.append(" fz_tem_subpart s");
		sql.append(" on s.id=pbb.subpartid");
		//过滤主部件-start
		sql.append(" left join fz_tem_proclass_b pb");
		sql.append(" on pb.id=pbb.proclass_bid");
		sql.append(" left join fz_tem_master m");
		sql.append(" on pb.masterid=m.id");
		//过滤主部件-end
		sql.append(" where ");
		sql.append(" ifnull(pbb.dr,0)=0");
		sql.append(" and ifnull(s.dr,0)=0");
		sql.append(" and m.vname not in('"+IConstant.MAIN_XZ+"','"+IConstant.MAIN_SPEC+"','"+IConstant.MAIN_DP+"')");
		if(typeId != null){
			sql.append(" and pbb.proclassid="+typeId);
		}
		
		if(typeId == null){
			sql.append(" and 1=2");
		}
		
		Integer total = getCountBySQL(sql.toString());
		List<SubPartVO> sublist_temp = (List<SubPartVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);
		
		List jsonlist = new ArrayList();
		
		List<SubPartVO> sublist = new ArrayList<SubPartVO>();
		if(sublist_temp != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(sublist_temp);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SubPartVO dto = new SubPartVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVname(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVsname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVdatasource(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				dto.setBisgyxj(CommUtil.isNullOrEm(arry[4]) ? null : Integer.valueOf(arry[4].toString()));
				dto.setBisbom(CommUtil.isNullOrEm(arry[5]) ? null : Integer.valueOf(arry[5].toString()));
				
				sublist.add(dto);
			}
		}
		
		if(sublist != null){
			//品类对应的子部件
			for (SubPartVO subPartVO : sublist) {
				Map<String, Object> jsonmap = new HashMap<String, Object>();
				//子部件id
				Integer subid = subPartVO.getId();
				//子部件名称
				String vname = subPartVO.getVname();
				//add by liuzy 2015-08-01 子部件简称
				String vsname = subPartVO.getVsname();
				//子部件内容加载
				String vdatasource = subPartVO.getVdatasource();
				//是否工艺细节 add by liuzy 2015-08-13
				Integer bisgyxj = subPartVO.getBisgyxj();
				//是否BOM表信息 add by liuzy 2015-08-13
				Integer bisbom = subPartVO.getBisbom();
				jsonmap.put("id", subid);
				jsonmap.put("vname", vname);
				jsonmap.put("vsname", vsname);
				jsonmap.put("vdatasource", vdatasource);
				jsonmap.put("bisgyxj", bisgyxj);
				jsonmap.put("bisbom", bisbom);
				
				//根据子部件主表id查找子表数据
				List<SubPartBVO> sublist_b = (List<SubPartBVO>)iHibernateDAO.findAll(SubPartBVO.class, " ifnull(dr,0)=0 and subpartid="+subid);
				List listjson = new ArrayList();
				if(sublist_b != null){
					int size = sublist_b.size();
					for(int i = 0 ; i < size; i++){
						SubPartBVO subPartBVO = sublist_b.get(i);
						String moduletype = subPartBVO.getVmoduletype();
						//所选档案类别
						int doctypeid;
						//里料选择
						String selLin;
						//面料选择
						String selFra;
						if(IConstant.MOD_ACCESSORIES.equals(moduletype)){//辅料
							doctypeid = subPartBVO.getDocvarietyid();
							if(isSpec(sublist_b)){
								continue;
							}
							//add by liuzy 2015-08-08 添加品类的过滤 typeId 
							//AuxiliaryVO acclist = iHibernateDAO.findFirst(AuxiliaryVO.class, " bisdefault=1 and  vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and docvarietyid="+doctypeid + " and proclassids like '%"+typeId+"%'");
							
							//add by liuzy 2015-09-01 设置默认数据的
							StringBuffer sql_def = new StringBuffer();
							sql_def.append("select ");
							sql_def.append(" a.id,a.vname,a.vcode,a.vsname,a.proclassids,a.specname,a.useid,a.docvarietyid,a.bisupload,a.vfileupload");
							sql_def.append(" from fz_auxiliary a");
							sql_def.append(" left join fz_auxiliarydef_b fb");
							sql_def.append(" on a.id=fb.auxiliaryId");
							sql_def.append(" where a.vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and a.docvarietyid="+doctypeid + " and a.proclassids like '%"+typeId+"%'");
							sql_def.append(" and fb.proclassid="+typeId);
							
							List<AuxiliaryVO> temp = (List<AuxiliaryVO>)iHibernateDAO.findListBySQL(sql_def.toString(), null, 0, 1);
							
							AuxiliaryVO acclist = new AuxiliaryVO();
							if(temp != null){
								/*
								 * 多表自定义SQL重组List<E>
								 */
								String jsonstr_ = ObjectToJSON.writeListJSON(temp);
								JSONArray jsonArr = JSONArray.fromObject(jsonstr_);
								int isize = jsonArr.size();
								for (int j = 0; j < isize; j++) {
									Object[] arry = jsonArr.getJSONArray(i).toArray();
									acclist.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
									acclist.setVname(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
									acclist.setVcode(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
									acclist.setVsname(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
									acclist.setProclassids(CommUtil.isNullOrEm(arry[4]) ? null : arry[4].toString());
									acclist.setSpecname(CommUtil.isNullOrEm(arry[5]) ? null : arry[5].toString());
									acclist.setUseid(CommUtil.isNullOrEm(arry[6]) ? null : Integer.valueOf(arry[6].toString()));
									acclist.setDocvarietyid(CommUtil.isNullOrEm(arry[7]) ? null : Integer.valueOf(arry[7].toString()));
									acclist.setBisupload(CommUtil.isNullOrEm(arry[8]) ? null : Integer.valueOf(arry[8].toString()));
									acclist.setVfileupload(CommUtil.isNullOrEm(arry[9]) ? null : arry[9].toString());
								}
							}
							
							if(acclist != null){
								listjson.add(acclist);
							}
						}else if(IConstant.MOD_SPECIAL.equals(moduletype)){//特殊档案
							doctypeid = subPartBVO.getDocvarietyid();
							SpecialVO speclist = iHibernateDAO.findFirst(SpecialVO.class, " bisdefault=1 and  docvarietyid="+doctypeid);
							if(speclist != null){
								listjson.add(speclist);
							}
						}else if(IConstant.MOD_LINING.equals(moduletype)){//里料
							selLin = subPartBVO.getVlin();
							if(isSpec(sublist_b)){
								continue;
							}
							//liuzy add by 2015-08-07  里料与子部件绑定
							AuxiliaryVO linlist = iHibernateDAO.findFirst(AuxiliaryVO.class, " bisdefault=1 and  vmoduletype='"+IConstant.MOD_LINING+"' and subpartids like '%"+subid+"%'");
							if(linlist != null){
								listjson.add(linlist);
							}
						}else if(IConstant.MOD_FABRIC.equals(moduletype)){//面料
							selFra = subPartBVO.getVcolorOrpatch();
							if(isSpec(sublist_b)){
								continue;
							}
							//add by liuzy 2015-08-08 添加品类的过滤 typeId 
							AuxiliaryVO fralist = iHibernateDAO.findFirst(AuxiliaryVO.class, " vmoduletype='"+IConstant.MOD_FABRIC+"'" + " and proclassids like '%"+typeId+"%'");
							if(fralist != null){
								listjson.add(fralist);
							}
							//jsonmap.put("mod_fabric", speclist);
						}
					}
					jsonmap.put("defValue", listjson);
				}
				jsonlist.add(jsonmap);
			}
		}
		
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5","icisBagging","icisButton","icisComponent","icisLine","icisLining","specid","subpartids","useid","usename","vcisBaggingName","vcisButtonName","vcisLiningName","vfrom","vmemo","vmoduletype"};
		renderJson(jsonlist,JsonUtils.configJson(excludes));
		
	}
	
	/**
	 * 
	* @Description: 判断子部件是否有绑定特殊档案
	* @param @return
	* @return boolean
	 */
	private boolean isSpec(List<SubPartBVO> sublist_b){
		boolean flag = false;
		if(sublist_b != null){
			for (SubPartBVO subPartBVO : sublist_b) {
				String moduletype = subPartBVO.getVmoduletype();
				if(IConstant.MOD_SPECIAL.equals(moduletype)){//特殊档案
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	* @Description: 获得尺码的基础档案数据
	* @param 
	* @return void
	 */
	public void getSizeDoc(){
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		List<SizeDocVO> list = iHibernateDAO.findAll(SizeDocVO.class, " ifnull(dr,0)=0 ");
		List<SizeDocVO> base_list = new ArrayList<SizeDocVO>();
		List<SizeDocVO> needle_list = new ArrayList<SizeDocVO>();
		List<SizeDocVO> kickerx_list = new ArrayList<SizeDocVO>();
		List<SizeDocVO> kickerc_list = new ArrayList<SizeDocVO>();
		List<SizeDocVO> back_list = new ArrayList<SizeDocVO>();
		List<SizeDocVO> chest_list = new ArrayList<SizeDocVO>();
		List<SizeDocVO> abdomen_list = new ArrayList<SizeDocVO>();
		List<SizeDocVO> waistline_list = new ArrayList<SizeDocVO>();
		List<SizeDocVO> belt_list = new ArrayList<SizeDocVO>();
		List<SizeDocVO> butt_list = new ArrayList<SizeDocVO>();
		List<SizeDocVO> leg_list = new ArrayList<SizeDocVO>();
		List<SizeDocVO> loosedeg_list = new ArrayList<SizeDocVO>();
		for (SizeDocVO sizeDocVO : list) {
			String doctype = sizeDocVO.getVdoctype();
			if(IConstant.SIZE_BASEB.equals(doctype)){
				//基本体型
				base_list.add(sizeDocVO);
			}else if(IConstant.SIZE_NEEDLE.equals(doctype)){
				//颈长
				needle_list.add(sizeDocVO);
			}else if(IConstant.SIZE_KICKERX.equals(doctype)){
				//肩斜
				kickerx_list.add(sizeDocVO);
			}else if(IConstant.SIZE_KICKERC.equals(doctype)){
				//肩冲
				kickerc_list.add(sizeDocVO);
			}else if(IConstant.SIZE_BACK.equals(doctype)){
				//背
				back_list.add(sizeDocVO);
			}else if(IConstant.SIZE_CHEST.equals(doctype)){
				//胸
				chest_list.add(sizeDocVO);
			}else if(IConstant.SIZE_ABDOMEN.equals(doctype)){
				//腹
				abdomen_list.add(sizeDocVO);
			}else if(IConstant.SIZE_WAISTLINE.equals(doctype)){
				//腰
				waistline_list.add(sizeDocVO);
			}else if(IConstant.SIZE_BELT.equals(doctype)){
				//裤带
				belt_list.add(sizeDocVO);
			}else if(IConstant.SIZE_BUTT.equals(doctype)){
				//臀
				butt_list.add(sizeDocVO);
			}else if(IConstant.SIZE_LEG.equals(doctype)){
				//腿长比例
				leg_list.add(sizeDocVO);
			}else if(IConstant.SIZE_LOOSEDEG.equals(doctype)){
				//宽松
				loosedeg_list.add(sizeDocVO);
			}
		}
		jsonmap.put(IConstant.SIZE_BASEB, base_list);
		jsonmap.put(IConstant.SIZE_NEEDLE, needle_list);
		jsonmap.put(IConstant.SIZE_KICKERX, kickerx_list);
		jsonmap.put(IConstant.SIZE_KICKERC, kickerc_list);
		jsonmap.put(IConstant.SIZE_BACK, back_list);
		jsonmap.put(IConstant.SIZE_CHEST, chest_list);
		jsonmap.put(IConstant.SIZE_ABDOMEN, abdomen_list);
		jsonmap.put(IConstant.SIZE_WAISTLINE, waistline_list);
		jsonmap.put(IConstant.SIZE_BELT, belt_list);
		jsonmap.put(IConstant.SIZE_BUTT, butt_list);
		jsonmap.put(IConstant.SIZE_LEG, leg_list);
		jsonmap.put(IConstant.SIZE_LOOSEDEG, loosedeg_list);
		
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(jsonmap,JsonUtils.configJson(excludes));
	}
	
	/**
	 * 
	* @Description: 根据品类获得标准的尺码
	* @param 
	* @return void
	 */
	public void getSizeStandard(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//品类
		String typeId = request.getParameter("typeId");
		//版型id
		String specid = request.getParameter("specId");
		//前片
		String specQpId = request.getParameter("specQpId");
		//下口
		String specXkId = request.getParameter("specXkId");
		
		String wh = " 1=2";
		if((typeId != null && !"".equals(typeId)) && (specid != null && !"".equals(specid))){
			wh = " proclassid="+typeId + " and specid="+specid;
		}
		if(!CommUtil.isNull(specQpId)){
			wh += " and spec_qpid ="+specQpId;
		}
		if(!CommUtil.isNull(specXkId)){
			wh += " and spec_xkid ="+specXkId;
		}
		List<StinfoVO> list = iHibernateDAO.findAll(StinfoVO.class, wh,"vsize","asc");
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(list,JsonUtils.configJson(excludes));
	}
	
	/**
	 * 
	* @Description: 根据品类和尺码获得尺码的标准信息
	* @param 
	* @return void
	 */
	public void getSizeStandardInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//品类id
		//String typeId = request.getParameter("typeId");
		//标准尺码id
		String stinfoId = request.getParameter("stinfoId");
		//版型id
		//String specid = request.getParameter("specId");
		
		StringBuffer sql =  new StringBuffer();
		
		sql.append(" select ");
		sql.append(" b.id,b.vname,b.standarmny,b.bisadjust,b.downmny,b.upmny,b.vcode ");
		sql.append(" from fz_size_stinfo s");
		sql.append(" left join fz_size_stinfo_b b");
		sql.append(" on s.id=b.stinfoId");
		sql.append(" where 1=1 ");
		
		if(stinfoId != null && !"".equals(stinfoId)){
			//sql.append(" and s.proclassid="+typeId);
			sql.append(" and b.stinfoId="+stinfoId);
			//sql.append(" and s.specid="+specid);
		}else{
			sql.append(" and 1=2");
		}
		
		Integer total = getCountBySQL(sql.toString());
		List<StinfoBVO> list = (List<StinfoBVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);
		
		List<StinfoBVO> sublist = new ArrayList<StinfoBVO>();
		if(list != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(list);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size_ = jsonArr.size();
			for (int i = 0; i < size_; i++) {
				StinfoBVO dto = new StinfoBVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVname(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setStandarmny(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setBisadjust(CommUtil.isNullOrEm(arry[3]) ? 1 : Integer.valueOf(arry[3].toString()));
				dto.setDownmny(CommUtil.isNullOrEm(arry[4]) ? 0 : Double.valueOf(arry[4].toString()));
				dto.setUpmny(CommUtil.isNullOrEm(arry[5]) ? 0 : Double.valueOf(arry[5].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[6]) ? null : arry[6].toString());
				sublist.add(dto);
			}
		}
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(sublist,JsonUtils.configJson(excludes));
	}
	
	/**
	 * 
	* @Description: 保存量身定制的数据
	* @param 
	* @return void
	 */
	public void saveTailorData(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String tailorData = request.getParameter("tailorData");
		
		if(tailorData != null){
			try {
				List<LtinfoVO> list_b = JsonUtils.getListFromJson(tailorData, LtinfoVO.class);
				iHibernateDAO.saveAll(list_b);
				renderText("1");
			} catch (Exception e) {
				renderText("0");
			}
		}else{
			renderText("0");
		}
		
	}
	
	/**
	 * @Description: 根据ids获得模型数据
	 * @param ids 1,2,3
	 * @return json
	 */
	public void getModelDataByIds(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeId = request.getParameter("typeId");//品类id
		String type = request.getParameter("type");//区别连接类型
		String ids = request.getParameter("ids");//传入模型关联id
		String findType = request.getParameter("findType");//用于判断查找方式-（single-）
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct ");
		sql.append(" m.id,t.vname as typeName,m.vname,m.vfileupload,m.bisupload");
		sql.append(" from fz_model m ");
		sql.append(" left join fz_model_b b ");
		sql.append(" on m.id=b.modelid");
		sql.append(" left join fz_model_type t");
		sql.append(" on m.typeid=t.id");
		/**模型存储位置-start-多个品类绑定同一部件**/
		if(typeId != null && !"".equals(typeId)){
			sql.append(" left join fz_model_folder f");
			sql.append(" on f.id = m.folderid");
		}
		/**模型存储位置-end**/
		sql.append(" where ifnull(b.dr,0)=0 and ifnull(m.dr,0)=0");
		/**模型存储位置-start-多个品类绑定同一部件**/
		if(typeId != null && !"".equals(typeId)){
			sql.append(" and f.proclassid="+typeId);
		}
		/**模型存储位置-end**/
		if(ids != null && type != null){
			sql.append(" and b.vlinktype='"+type+"'");
			//以逗号分割
			String[] id = ids.split(",");
			Integer len = id.length;
			String idStr = "";
			for(int k = 0; k < len; k++){//添加单引号
				idStr += "'";
				idStr += id[k];
				idStr += "',";
			}
			if(idStr != null && !"".equals(idStr)){
				idStr = idStr.substring(0, idStr.length()-1);
			}
			if(len == 1){
				if("more".equals(findType)){
					sql.append(" and b.vdef1 like '%"+ids+"%' and b.vdef2 is null and b.vdef3 is null");//存在bug-用于解决多个模型和此项联动
				}else{
					sql.append(" and b.vdef1 ="+idStr+" and b.vdef2 is null and b.vdef3 is null");
				}
			}else if(len == 2){
				for(int i = 1; i <= len; i++){
					sql.append(" and b.vdef"+ i +" in("+idStr+") and b.vdef3 is null");
				}
			}else{
				for(int i = 1; i <= len; i++){
					sql.append(" and b.vdef"+ i +" in("+idStr+")");
				}
			}
		}else{
			sql.append(" and 1=2");
		}
		
		Integer total = getCountBySQL(sql.toString());
		List<ModelVO> list = (List<ModelVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);
		
		List<ModelVO> sublist = new ArrayList<ModelVO>();
		if(list != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(list);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size_ = jsonArr.size();
			for (int i = 0; i < size_; i++) {
				ModelVO dto = new ModelVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setTypeName(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVfileupload(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				dto.setBisupload(CommUtil.isNullOrEm(arry[4]) ? null : Integer.valueOf(arry[4].toString()));
				
				sublist.add(dto);
			}
		}
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(sublist,JsonUtils.configJson(excludes));
		
	}
	
	/**
	 * @Description: 获得标准工艺id
	 * @param @return
	 * @return Integer
	 */
	public Integer getProcessId(){
		String sql = "select id from fz_base_doc where vname like '%标准%'";
		Integer processId = iHibernateDAO.findIntBySQL(sql, null);
		return processId;
	}
	/**
	 * @Description: 获得西服上衣id
	 * @param @return
	 * @return Integer
	 */
	public Integer getTypeId(){
		String sql = "select id from fz_tem_proclass where vsname ='"+IConstant.PRO_XIFUSHANGYI_ID+"'";
		Integer typeId = iHibernateDAO.findIntBySQL(sql, null);
		return typeId;
	}
	
	/**
	 * @Description: 面料价格定价方案 
	 * @param  参数：{品类id：typeId，工艺id：processId，面料采购单价：purPrice，面料系数：coef}
	 * @return void
	 */
	public void getPriceByCont(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//品类id
		String typeId = request.getParameter("typeId");
		//工艺id
		String processId = request.getParameter("processId");
		//标准工艺id
		Integer stId = getProcessId();
		//西服上衣Id
		Integer xfsyId = getTypeId();
		//面料采购单价
		String purPrice = request.getParameter("purPrice");
//		//面料系数
//		String coef = request.getParameter("coef");
		
		if(typeId == null || "".equals(typeId)){
			renderJs("-1:"+IConstant.MSG_SCH);
			return ;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" b.daccountmny,b.dunitmny,b.dprocefeemny,b.dprocecoefmny,b.ddiscountmny,b.dcoefficientmny,b.dfproductmny,s.proclassid,b.basedocid");
		sql.append(" from fz_scheme_b b");
		sql.append(" left join fz_scheme s");
		sql.append(" on b.schemeid=s.id");
		sql.append(" where ifnull(b.dr,0)=0 and ifnull(s.dr,0)=0");
		sql.append(" and s.proclassid in("+typeId+")");
		if(processId != null && !"".equals(processId)){
			sql.append(" and b.basedocid in("+processId+","+stId+")");
		}
		if(purPrice != null && !"".equals(purPrice)){
			sql.append(" and b.dsmall_purchasemny <= "+purPrice+" and b.dbig_purchasemny >= "+purPrice);
		}else{
			sql.append(" and 1=2");
		}
		
		Integer total = getCountBySQL(sql.toString());
		List<SchemeBVO> sclist = (List<SchemeBVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);
		
		List<SchemeBVO> list = new ArrayList<SchemeBVO>();
		if(sclist != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(sclist);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size_ = jsonArr.size();
			for (int i = 0; i < size_; i++) {
				SchemeBVO dto = new SchemeBVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				
				//过滤掉不是当前选中工艺的西服
				if((arry[7].toString().equals(xfsyId+"") && !arry[8].toString().equals(processId))){
					continue;
				}
				
				dto.setDaccountmny(CommUtil.isNullOrEm(arry[0]) ? null : Double.valueOf(arry[0].toString()));
				dto.setDunitmny(CommUtil.isNullOrEm(arry[1]) ? null : Double.valueOf(arry[1].toString()));
				dto.setDprocefeemny(CommUtil.isNullOrEm(arry[2]) ? null : Double.valueOf(arry[2].toString()));
				dto.setDprocecoefmny(CommUtil.isNullOrEm(arry[3]) ? null : Double.valueOf(arry[3].toString()));
				dto.setDdiscountmny(CommUtil.isNullOrEm(arry[4]) ? null : Double.valueOf(arry[4].toString()));
				dto.setDcoefficientmny(CommUtil.isNullOrEm(arry[5]) ? null : Double.valueOf(arry[5].toString()));
				//成品价
				dto.setDfproductmny(CommUtil.isNullOrEm(arry[6]) ? null : Double.valueOf(arry[6].toString()));
				//品类id
				dto.setVdef1(CommUtil.isNullOrEm(arry[7]) ? null : arry[7].toString());
				//工艺id
				dto.setVdef2(CommUtil.isNullOrEm(arry[8]) ? null : arry[8].toString());
				
				list.add(dto);
			}
		}
		
		if(list != null && !list.isEmpty()){
			renderJs(getCantinal(list));
		}else{
			String[] id = typeId.split(",");
			if(id != null){
				StringBuffer iid = new StringBuffer("");
				for(int i = 0; i < id.length; i++){
					iid.append(id[i]+":"+IConstant.MSG_SCH).append(",");
				}
				iid.append("t:"+IConstant.MSG_SCH);
				renderJs(iid.substring(0, iid.length()));
			}
		}
	}
	
	/**
	 * 根据品类获得对应的价格
	 * @param @param list
	 * @param @return
	 * @return String
	 */
	public String getCantinal(List<SchemeBVO> list){
		
		StringBuffer prices = new StringBuffer("");
		
		//核算价
		Double daccountmny = new Double("0.00");
		//单耗
		Double dunitmny = new Double("0.00");
		//工艺加工费(单位元)
		Double dprocefeemny = new Double("0.00");
		//工艺加工系数	
		Double dprocecoefmny = new Double("1");
		//折扣(%)
		Double ddiscountmny = new Double("100");
		//面料系数
		Double dcoef = new Double("1");
		//成品价
		Double fproductmny = new Double("0.00");
		//套装价
		Double tzmny = new Double("0.00");
		
		Double price = new Double("0.00");
		
		for (SchemeBVO schemeBVO : list) {
			daccountmny = schemeBVO.getDaccountmny();
			dunitmny = schemeBVO.getDunitmny();
			dprocefeemny = schemeBVO.getDprocefeemny();
			dprocecoefmny = schemeBVO.getDprocecoefmny();
			ddiscountmny = schemeBVO.getDdiscountmny();
			dcoef = schemeBVO.getDcoefficientmny();
			//成品价
			fproductmny = schemeBVO.getDfproductmny();
			//计算价格
			SchemeVO svo = iHibernateDAO.findFirst(SchemeVO.class, " proclassid="+schemeBVO.getVdef1());
			if(svo != null){
				String type = svo.getType();
				if("sch_dz".equals(type)){
					price = (daccountmny * dunitmny * dcoef + dprocefeemny * dprocecoefmny) * ddiscountmny/100 ;	
					tzmny = tzmny + price;//Double.valueOf(CommUtil.toTurnPrice(price));
					//renderJs("0".equals(CommUtil.toTurnPrice(price)) ? IConstant.MSG_SCH :CommUtil.toTurnPrice(price));
					prices.append(schemeBVO.getVdef1() + ":").append("0".equals(CommUtil.toTurnPrice(price)) ? IConstant.MSG_SCH :CommUtil.toTurnPrice(price)).append(",");
				}else if("sch_cp".equals(type)){
					price = fproductmny;
					tzmny = tzmny + price;
					//renderJs("0".equals(CommUtil.toInteger(price)) ? IConstant.MSG_SCH : CommUtil.toInteger(price));
					prices.append(schemeBVO.getVdef1() + ":").append("0".equals(CommUtil.toInteger(price)) ? IConstant.MSG_SCH : CommUtil.toInteger(price)).append(",");
				}
			}else{
				//renderJs(IConstant.MSG_SCH);
				prices.append(schemeBVO.getVdef1() + ":").append(IConstant.MSG_SCH).append(",");
			}
		}
		prices.append("t:" + CommUtil.toTurnPrice(tzmny * 0.95)).append(",");
		
		return prices.toString().substring(0, prices.length()-1);
	}
	
	/**
	 * 定制价格
	 * @param @param list
	 * @param @return
	 * @return String
	 */
	public TreeMap<Double, String> getAllDZPrice(List<SchemeBVO> list){
		
		TreeMap<Double, String> mapPrice = new TreeMap<Double, String>();
		
		//核算价
		Double daccountmny = new Double("0.00");
		//单耗
		Double dunitmny = new Double("0.00");
		//工艺加工费(单位元)
		Double dprocefeemny = new Double("0.00");
		//工艺加工系数	
		Double dprocecoefmny = new Double("1");
		//折扣(%)
		Double ddiscountmny = new Double("100");
		//面料系数
		Double dcoef = new Double("1");
		Double price = new Double("0.00");
		
		for (SchemeBVO schemeBVO : list) {
			daccountmny = schemeBVO.getDaccountmny();
			dunitmny = schemeBVO.getDunitmny();
			dprocefeemny = schemeBVO.getDprocefeemny();
			dprocecoefmny = schemeBVO.getDprocecoefmny();
			ddiscountmny = schemeBVO.getDdiscountmny();
			dcoef = schemeBVO.getDcoefficientmny();
			//计算价格
			price = (daccountmny * dunitmny * dcoef + dprocefeemny * dprocecoefmny) * ddiscountmny/100 ;	
			mapPrice.put(price,daccountmny+"");
		}
		
		return mapPrice;
	}
	
	/**
	 * @Description: 根据品类获得子部件 
	 * @param 
	 * @return void
	 */
	public void getSubPartByPro(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeId = request.getParameter("typeId");
		//辨别是否为手机端请求
		String deviceType = request.getParameter("deviceType");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" sub.id,sub.vcode,sub.vname,sub.vdatasource,sub.vlinktype,pbb.masterid,sub.vsname,sub.bisgyxj,sub.bisbom");
		sql.append(" from fz_tem_proclass_bb pbb");
		/*sql.append(" left join ");
		sql.append(" fz_tem_proclass_b pb");
		sql.append(" on pbb.proclass_bid=pb.id");*/
		sql.append(" left join fz_tem_subpart sub");
		sql.append(" on pbb.subpartid=sub.id");
		sql.append(" where pbb.proclassid="+typeId);
		sql.append(" and ifnull(pbb.dr,0)=0");
		sql.append(" and ifnull(sub.dr,0)=0");
		if(deviceType != null && !"".equals(deviceType)){
			sql.append(" and sub.deviceType="+deviceType);
		}
		sql.append(" order by sub.isort asc");
		
		Integer total = getCountBySQL(sql.toString());
		List<SubPartVO> subPartList = (List<SubPartVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);
		List<SubPartVO> list = new ArrayList<SubPartVO>();
		if(subPartList != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(subPartList);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				SubPartVO dto = new SubPartVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setId(CommUtil.isNullOrEm(arry[0]) ? null : Integer.valueOf(arry[0].toString()));
				dto.setVcode(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVdatasource(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				dto.setVlinktype(CommUtil.isNullOrEm(arry[4]) ? null : arry[4].toString());
				dto.setMasterid(CommUtil.isNullOrEm(arry[5]) ? null : Integer.valueOf(arry[5].toString()));
				dto.setVsname(CommUtil.isNullOrEm(arry[6]) ? null : arry[6].toString());
				dto.setBisgyxj(CommUtil.isNullOrEm(arry[7]) ? null : Integer.valueOf(arry[7].toString()));
				dto.setBisbom(CommUtil.isNullOrEm(arry[8]) ? null : Integer.valueOf(arry[8].toString()));
				
				list.add(dto);
			}
		}
		
		String[] excludes = new String[]{"class","condmap","selected","userValue","ts","dr","vdef1","vdef2",
				"vdef3","vdef4","vdef5"};
		renderJson(list,JsonUtils.configJson(excludes));
	}
	
	/**
	 * @Description: 门店登录
	 * @param 
	 * @return 0:失败；1:成功
	 */
	public void login(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//用户名
		String userName = request.getParameter("userName");
		//密码
		String password = request.getParameter("password");
		
		StringBuffer wh = new StringBuffer(" ifnull(islock,0)=0");
		
		if(userName != null && !"".equals(userName) && password != null && !"".equals(password)){
			wh.append(" and vusername='"+userName+"' and vpwd='"+password+"'");
		}else{
			wh.append(" and 1=2");
		}
		Integer c = iHibernateDAO.getCountByHQL(SpUserVO.class, wh.toString());
		String msg = "0";
		if(c > 0){
			msg = "1";
		}
		renderText(msg);
	}
	/**
	 * @Description: 构建BOM信息
	 * @param 套装以“#”分割
	 * @return void
	 */
	@Transactional
	public void toBuildBom(){
		//String bomStr= "{\"diySubCont\":{\"name\":\"下摆\",\"vsname\":\"xiabai\",\"code\":\"XB\",\"selValue\":{\"name\":\"2\",\"code\":\"圆角\"}},\"diyFra\":{\"name\":\"毛和其它素色黑\",\"code\":\"11116110003\",\"id\":\"99\",\"specname\":\"23#1\"},\"diyPro\":\"19\",\"diyCode\":\"01\",\"diyLin\":{\"lining\":{\"name\":\"顺色/标配\",\"code\":\"SS\",\"specname\":\"\"},\"sleeveLining\":{\"name\":\"顺色/标配\",\"code\":\"SS\",\"specname\":\"\"}}}";
		//String bomStr = "[{\"diySubCont\":[{\"name\":\"下摆\",\"vsname\":\"xiabai\",\"selValue\":{\"name\":\"圆角\",\"appendCode\":\"\",\"code\":\"2\"},\"code\":\"XB\"},{\"name\":\"扣位数\",\"vsname\":\"kouweishu\",\"selValue\":{\"name\":\"单排两扣(1*2)\",\"appendCode\":\"\",\"code\":\"12\"},\"code\":\"K\"},{\"name\":\"下袋\",\"vsname\":\"xiadai\",\"selValue\":{\"name\":\"平口袋带袋盖\",\"appendCode\":\"\",\"code\":\"2\"},\"code\":\"XD\"},{\"name\":\"票袋\",\"vsname\":\"piaodai\",\"selValue\":{\"name\":\"无票袋\",\"appendCode\":\"\",\"code\":\"00\"},\"code\":\"PD\"},{\"name\":\"撞色部位\",\"vsname\":\"\",\"selValue\":{\"name\":\"驳头/胸袋\",\"appendCode\":\"11141191633\",\"code\":\"02/03\"},\"code\":\"ZW\"},{\"name\":\"贴布部位\",\"vsname\":\"\",\"selValue\":{\"name\":\"肘部\",\"appendCode\":\"11141191633\",\"code\":\"01\"},\"code\":\"TW\"},{\"name\":\"特殊锁眼\",\"vsname\":\"\",\"selValue\":{\"name\":\"驳头眼\",\"appendCode\":\"1302190420\",\"code\":\"01\"},\"code\":\"SY\"}],\"diyName\":\"西服上衣\",\"diyCode\":\"01\",\"diyFra\":{\"name\":\"毛绒素色蓝\",\"code\":\"11113150006\",\"id\":\"154\",\"specname\":\"1#jk\"},\"diyPro\":\"19\",\"diyLin\":{\"lining\":{\"name\":\"顺色/标配\",\"code\":\"SS\",\"specname\":\"\"},\"sleeveLining\":{\"name\":\"顺色/标配\",\"code\":\"SS\",\"specname\":\"\"}}}]";
		//String bomStr = "[{\"diyLin\":{\"lining\":{\"name\":\"顺色/标配\",\"code\":\"SS\",\"specname\":\"\"},\"sleeveLining\":{\"name\":\"顺色/标配\",\"code\":\"SS\",\"specname\":\"\"}},\"diyCode\":\"01\",\"diyPro\":\"19\",\"diySubCont\":[{\"selValue\":{\"name\":\"圆角\",\"appendCode\":\"\",\"code\":\"2\"},\"name\":\"下摆\",\"vsname\":\"xiabai\",\"code\":\"XB\"},{\"selValue\":{\"name\":\"单排两扣(1*2)\",\"appendCode\":\"\",\"code\":\"12\"},\"name\":\"扣位数\",\"vsname\":\"kouweishu\",\"code\":\"K\"},{\"selValue\":{\"name\":\"平口袋带袋盖\",\"appendCode\":\"\",\"code\":\"2\"},\"name\":\"下袋\",\"vsname\":\"xiadai\",\"code\":\"XD\"},{\"selValue\":{\"name\":\"无票袋\",\"appendCode\":\"\",\"code\":\"00\"},\"name\":\"票袋\",\"vsname\":\"piaodai\",\"code\":\"PD\"}],\"diyName\":\"西服上衣\",\"diyFra\":{\"name\":\"全羊绒条蓝\",\"code\":\"11111350014\",\"id\":\"107\",\"specname\":\"SUPER 130S 100%WV 255-265GR/MT *150CM\"}}]";
		//String bomStr = "[{\"diyFra\":{\"name\":\"毛绒素色黑\",\"code\":\"11113110002\",\"id\":\"152\",\"specname\":\"有效幅宽148CM# Gr.435g/m 36s/2*36s/2 W90% WS10%    (NATSUN 2015/I)\"},\"diyCode\":\"05\",\"diyLin\":{},\"diySubCont\":[{\"name\":\"撞色部位\",\"code\":\"ZW\",\"selValue\":{\"name\":\"领子/克夫\",\"appendCode\":\"11121481004\",\"code\":\"01/03\"},\"vsname\":\"zhuangse\"}],\"diyPro\":\"25\",\"diyName\":\"长袖  衬衣\"}]";
		//String bomStr = "[{\"diyFra\":{\"name\":\"全羊绒条蓝\",\"code\":\"11112351896\",\"id\":\"107\",\"specname\":\"SUPER 130S 100%WV 255-265GR/MT *150CM\"},\"diyCode\":\"01\",\"diyLin\":{\"lining\":{\"name\":\"涤粘花纹白\",\"code\":\"SS\",\"specname\":\"有效幅宽144CM#52%T 48%R 68D*120D\"},\"sleeveLining\":{\"name\":\"涤纶条白\",\"code\":\"SS\",\"specname\":\"有效幅宽148CM,100%涤纶 68D*68D\"}},\"diySubCont\":[{\"name\":\"扣位数\",\"code\":\"K\",\"selValue\":{\"name\":\"单排两扣(1*2)\",\"appendCode\":\"\",\"code\":\"12\"},\"vsname\":\"kouweishu\"},{\"name\":\"下袋\",\"code\":\"XD\",\"selValue\":{\"name\":\"平口袋带袋盖\",\"appendCode\":\"\",\"code\":\"2\"},\"vsname\":\"xiadai\"},{\"name\":\"票袋\",\"code\":\"PD\",\"selValue\":{\"name\":\"无票袋\",\"appendCode\":\"\",\"code\":\"00\"},\"vsname\":\"piaodai\"},{\"name\":\"撞色部位\",\"code\":\"ZW\",\"selValue\":{\"name\":\"领子\",\"appendCode\":\"11141131634\",\"code\":\"01\"},\"vsname\":\"zhuangse\"},{\"name\":\"贴布部位\",\"code\":\"TW\",\"selValue\":{\"name\":\"肘部\",\"appendCode\":\"11141131634\",\"code\":\"01\"},\"vsname\":\"tiebu\"},{\"name\":\"特殊锁眼\",\"code\":\"SY\",\"selValue\":{\"name\":\"驳头眼\",\"appendCode\":\"1302190433\",\"code\":\"01\"},\"vsname\":\"suoyan\"}],\"diyPro\":\"19\",\"diyName\":\"西服上衣\"}]";
		//String bomStr = "[{\"diyPro\":\"19\",\"diyCode\":\"01\",\"diyLin\":{\"lining\":{\"name\":\"顺色\",\"code\":\"SS\",\"specname\":\"\"},\"sleeveLining\":{\"name\":\"顺色\",\"code\":\"SS\",\"specname\":\"\"}},\"diyName\":\"西服上衣\",\"diySubCont\":[{\"name\":\"扣位数\",\"vsname\":\"kouweishu\",\"code\":\"K\",\"selValue\":{\"name\":\"单排两扣(1*2)\",\"appendCode\":\"\",\"code\":\"12\"}},{\"name\":\"下袋\",\"vsname\":\"xiadai\",\"code\":\"XD\",\"selValue\":{\"name\":\"平口袋带袋盖\",\"appendCode\":\"\",\"code\":\"2\"}},{\"name\":\"票袋\",\"vsname\":\"piaodai\",\"code\":\"PD\",\"selValue\":{\"name\":\"无票袋\",\"appendCode\":\"\",\"code\":\"00\"}}],\"diyFra\":{\"name\":\"全羊绒花纹灰\",\"code\":\"11111530001\",\"id\":\"101\",\"specname\":\"有效幅宽150CM# 100%WV Super130s 295-305GR/MT *150CM \"}}]";
		//String bomStr = "[{\"diyLin\":{\"lining\":{\"name\":\"顺色\",\"specname\":\"\",\"code\":\"SS\"},\"sleeveLining\":{\"name\":\"顺色\",\"specname\":\"\",\"code\":\"SS\"}},\"diyCode\":\"01\",\"diySubCont\":[{\"name\":\"扣位数\",\"code\":\"K\",\"selValue\":{\"name\":\"单排两扣(1*2)\",\"appendCode\":\"\",\"code\":\"12\"},\"vsname\":\"kouweishu\"},{\"name\":\"下袋\",\"code\":\"XD\",\"selValue\":{\"name\":\"平口袋带袋盖\",\"appendCode\":\"\",\"code\":\"2\"},\"vsname\":\"xiadai\"},{\"name\":\"票袋\",\"code\":\"PD\",\"selValue\":{\"name\":\"无票袋\",\"appendCode\":\"\",\"code\":\"00\"},\"vsname\":\"piaodai\"},{\"name\":\"纽扣号\",\"code\":\"NH\",\"selValue\":{\"name\":\"尿素扣蓝\",\"appendCode\":\"\",\"code\":\"1301350916\"},\"vsname\":\"niukouhao\"}],\"diyName\":\"西服上衣\",\"diyFra\":{\"name\":\"全羊绒条黑\",\"specname\":\"有效幅宽148CM#SUPER 110S 100%WV 255-265GR/MT *150CM\",\"code\":\"11111310005\",\"id\":\"109\"},\"diyPro\":\"19\"}]";
		HttpServletRequest request = ServletActionContext.getRequest();
		String bomInfo = request.getParameter("diyBom");
		
		String srcBom = bomInfo;
		String backInfo = null;//盛放返回信息
		StringBuffer newBom = new StringBuffer();//组织好BOM
		StringBuffer ids = new StringBuffer();//bom对应的表主键
		
		if(srcBom != null && !"".equals(srcBom)){
			//String[] arrayBom = srcBom.split(IConstant.BOM_3DTZ_SPLIT);
			JSONArray arrayBom = JSONArray.fromObject(srcBom);
			int len = arrayBom.size();
			for (int i = 0; i < len; i++) {
				String[] abom = getBomByCon(arrayBom.getJSONObject(i));
				newBom.append(CommUtil.isNull(abom[1]) ? "" : abom[1]).append(IConstant.BOM_SPLIT);
				ids.append(CommUtil.isNull(abom[0]) ? "" : abom[0]).append(IConstant.BOM_3D_SPLIT);
			}
			backInfo = (CommUtil.isNull(ids) ? "生成定制单表失败" : ids.toString().substring(0, ids.toString().length()-1))+IConstant.BOM_IDS_3D_SPLIT+ (CommUtil.isNull(newBom) ? "BOM信息生成失败" : newBom.toString().substring(0, newBom.toString().length()-3));
			
			if(!CommUtil.isNull(ids)){
				String[] adiyCode = (ids+"").split(IConstant.BOM_3D_SPLIT);
				for(int j = 0; j < adiyCode.length; j++){
					iHibernateDAO.delete(DiyInfoVO.class, Integer.valueOf(adiyCode[j]));
				}
			}
			renderJson(CommUtil.isNull(backInfo) ? "" : backInfo);
		}else{
			renderText("基础数据为空!");
		}
	}
	
	/**
	 * @Description: 根据基础数据组织BOM信息，并返回主键:新的BOM
	 * @param @param srcBom
	 * @return void
	 */
	public String[] getBomByCon(JSONObject srcBom){
		String[] backStr = new String[2];
		
		//BOM JSON对象信息
		//JSONObject  bomJson = JSONObject.fromObject(srcBom);
		JSONObject  bomJson = srcBom;
		Object diyPro = bomJson.get("diyPro");//品类id
		Object diyCode = bomJson.get("diyCode");//品类编码
		Object diyName = bomJson.get("diyName");//品类名称
		Object diyFra = bomJson.get("diyFra");//面料
		Object diyLin = bomJson.get("diyLin");//里料
		Object diySubCont = bomJson.get("diySubCont");//子部件
		
		/**
		 * 通过品类获得料件耗料信息
		 */
		List<CfeedBVO> list_cf = getCfeedByPro(diyPro+"");
		/**
		 * 通过品类获得BOM标配信息
		 */
		List<BtcconfigBVO> list_bom = getBOMByPro(diyPro+"");
		
		//面料 JSON对象信息--fabric 
		JSONObject fraJson = JSONObject.fromObject(diyFra);
		
		/**
		 * 里料 JSON对象信息
		 */
		JSONObject linJson = JSONObject.fromObject(diyLin);
		Object lining = linJson.get("lining");//衣里料、里布用料
		Object sleeveLining = linJson.get("sleeveLining");//袖里料
		Object backFabric = linJson.get("backFabric");//后背用料
		
		//衣里料、里布用料 JSON对象
		JSONObject lininJson = JSONObject.fromObject(lining);
		//袖里料 JSON对象
		JSONObject linSlvJson = JSONObject.fromObject(sleeveLining);
		//后背用料 JSON对象
		JSONObject linBackJson = JSONObject.fromObject(backFabric);
		
		/**组织BOM-start**/
		/**
		 * 子部件对象
		 */
		JSONArray subJson = JSONArray.fromObject(diySubCont);
		Integer size = subJson.size();
		for(int k = 0; k < size; k++){
			JSONObject objjson = subJson.getJSONObject(k);
			//①
			orgAccBom(list_bom,objjson,list_cf);
		}
		
		System.out.println("品类id:"+diyPro);
		System.out.println("品类编码:"+diyCode);
		System.out.println("面料:"+diyFra);
		System.out.println("里料:"+diyLin);
		System.out.println("子部件:"+diySubCont);
		
		//②面料
		orgBom(list_bom,fraJson,null,IConstant.BOM_JC_FABRIC,diyPro+"");
		//衣里料、里布用料
		orgBom(list_bom,lininJson,fraJson,IConstant.BOM_JC_LINING,diyPro+"");
		//袖里料
		orgBom(list_bom,linSlvJson,fraJson,IConstant.BOM_JC_SLEEVELINING,diyPro+"");
		//后背用料 
		orgBom(list_bom,linBackJson,fraJson,IConstant.BOM_JC_BACKFABRIC,diyPro+"");
		
		//③顺色部分-袋布、拉链
		orgSSBom(list_bom,fraJson,diyPro+"");
		/**组织BOM-end**/
		
		/**
		 * 组织好的BOM信息
		 */
		String newBom = "";
		//String newBom = "[{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330002\",\"tc_aah06\":\"1\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0001\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330003\",\"tc_aah06\":\"2\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330002\",\"tc_aah06\":\"1\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0001\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330003\",\"tc_aah06\":\"2\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330002\",\"tc_aah06\":\"1\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0001\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330003\",\"tc_aah06\":\"2\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330002\",\"tc_aah06\":\"1\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0001\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330003\",\"tc_aah06\":\"2\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330002\",\"tc_aah06\":\"1\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0001\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330003\",\"tc_aah06\":\"2\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330002\",\"tc_aah06\":\"1\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0001\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330003\",\"tc_aah06\":\"2\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330002\",\"tc_aah06\":\"1\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0001\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330003\",\"tc_aah06\":\"2\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330002\",\"tc_aah06\":\"1\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0001\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330003\",\"tc_aah06\":\"2\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330002\",\"tc_aah06\":\"1\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0001\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330003\",\"tc_aah06\":\"2\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330002\",\"tc_aah06\":\"1\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0001\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330003\",\"tc_aah06\":\"2\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330002\",\"tc_aah06\":\"1\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0001\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330003\",\"tc_aah06\":\"2\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330002\",\"tc_aah06\":\"1\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0001\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330003\",\"tc_aah06\":\"2\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"0002\"},{\"tc_aah01\":\"7EUC025S105DS-0000\",\"tc_aah02\":\"西服上衣\",\"tc_aah03\":\"\",\"tc_aah04\":\"PYT0022\",\"tc_aah05\":\"11121330004\",\"tc_aah06\":\"3\",\"tc_aah07\":\"2\",\"tc_aah08\":\"1\",\"tc_aah09\":\"00025555\"}]";
		//获得成品编码
		String vcode = getProCode(diyCode+"");
		
		List<BomVO> listbom = new ArrayList<BomVO>();
		//转换VO
		if(list_bom != null){
			for (BtcconfigBVO bvo : list_bom) {
				BomVO vo = new BomVO();
				vo.setTc_aah01(vcode);
				vo.setTc_aah02(diyName+"");
				vo.setTc_aah03(null);
				vo.setTc_aah04(bvo.getVdef1());
				vo.setTc_aah05(bvo.getVcode());
				vo.setTc_aah06(bvo.getVserial());
				vo.setTc_aah07(bvo.getNunitmny()+"");
				vo.setTc_aah08(1+"");
				vo.setTc_aah09(bvo.getVjobnum());
				listbom.add(vo);
			}
			JSONArray JSONArray = net.sf.json.JSONArray.fromObject(listbom,JsonUtils.configJson("yyyy-MM-dd"));
			newBom = JSONArray.toString();
		}
		DiyInfoVO diyInfoVO = new DiyInfoVO();
		diyInfoVO.setVcode(vcode);
		diyInfoVO.setVbom(newBom);
		try {
			Integer id = iHibernateDAO.saveReInt(diyInfoVO);
			backStr[0] = id+"";
			backStr[1] = newBom;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return backStr;
	}
	
	/**
	 * @Description: 子部件对应撞色、顺色、普通
	 * @param @param list_bom BOM标配
	 * @param @param json 
	 * @param list_cf 耗料表
	 * @return void
	 */
	public void orgAccBom(List<BtcconfigBVO> list_bom,JSONObject json,List<CfeedBVO> list_cf){
		Object vsname = json.get("vsname");
		Object vcode = json.get("code");
		Object vname = json.get("name");
		Object subType = json.get("selValue");//子部件选择内容项
		//子部件内容 JSON对象
		JSONObject subTypeJson = JSONObject.fromObject(subType);
		//类似撞色面料
		Object appendCode = subTypeJson.get("appendCode");
		Object code = subTypeJson.get("code");
		
		BtcconfigBVO bvo = new BtcconfigBVO();
		/**撞色、贴布、特殊锁眼、珠边{层级关系}**/
		if(!CommUtil.isNull(appendCode)){
			//根据vsname 区别撞色、贴布、特殊锁眼、珠边
			if(IConstant.PART_SNAMW_ZS.equals(vsname)){
				//撞色 部位--所有拼色面料耗料
				Double allM = getMaxValue(subTypeJson,list_cf,vname+"");
				//组织BOM
				bvo.setVcode(appendCode+"");
				bvo.setNunitmny(allM);
				bvo.setVjobnum(IConstant.VJOBNUM);
				bvo.setVserial("11");
				bvo.setVspec(getSpec(appendCode+"")+"");
				setBomEm(list_bom,vsname+"",bvo,"n");
			}else if(IConstant.PART_SNAMW_TB.equals(vsname)){
				//贴布
				Double allM = getMaxValue(subTypeJson,list_cf,"");
				//组织BOM
				bvo.setVcode(appendCode+"");
				bvo.setNunitmny(allM);
				bvo.setVserial("13");
				bvo.setVjobnum(IConstant.VJOBNUM);
				bvo.setVspec(getSpec(appendCode+"")+"");
				setBomEm(list_bom,vsname+"",bvo,"n");
			}else if(IConstant.PART_SNAMW_SY.equals(vsname) || IConstant.PART_SNAMW_ZB.equals(vsname)){
				bvo.setVcode(appendCode+"");
				setBomEm(list_bom,vsname+"",bvo,"y");
			}
		}else{//其他{不包含层级关系}
			//扣位数
			if(IConstant.PART_SNAMW_KWS.equals(vsname)){
				String numb = CommUtil.getNumbers((subTypeJson.get("name")+"").split("[*]")[0]);
				bvo.setNunitmny(Double.valueOf(numb)+1);
				setBomEm(list_bom,IConstant.BOM_JC_NIUKOU,bvo,"r");
			}else if(IConstant.PART_SNAMW_XKS.equals(vsname)){//袖扣数
				String numb = CommUtil.getNumbers(subTypeJson.get("name")+"");
				bvo.setNunitmny(Double.valueOf(numb)+1);
				setBomEm(list_bom,IConstant.BOM_JC_NIUKOU,bvo,"r");
			}else if(IConstant.PART_SNAMW_NKH.equals(vsname)){//纽扣号
				if(!CommUtil.isNullOrEm(code)){
					bvo.setVcode(code+"");
					setBomEm(list_bom,IConstant.BOM_JC_NIUKOU,bvo,"y");
				}
			}
		}
	}
	
	/**
	 * @Description: 最大耗料*耗料数
	 * @param @param subTypeJson
	 * @param @param list_cf
	 * @param @return
	 * @return Map<String,Double>
	 */
	public Double getMaxValue(JSONObject subTypeJson,List<CfeedBVO> list_cf,String name){
		Double num = new Double("0.0");
		TreeMap<String,Double> mapHL = new TreeMap<String,Double>();
		String[] codes = (subTypeJson.get("code")+"").split("/");
		if(codes != null){
			num = Double.valueOf(codes.length);
			for(int i = 0; i < codes.length; i++){
				String code = codes[i];
				for (CfeedBVO cfeedBVO : list_cf) {
					if(code.equals(cfeedBVO.getVcode())){
						String st = cfeedBVO.getVstate();
						if(!CommUtil.isNull(st)){
							if(name.contains(st)){
								mapHL.put(code, cfeedBVO.getNunitmny());
							}
						}else{
							mapHL.put(code, cfeedBVO.getNunitmny());
						}
					}
				}
			}
			//判断在多选撞色部位时，耗料以单个最多的耗料为定额标准
			Double maxValue = new Double("0.0");
	        String maxKey = null;
			if(mapHL != null){
				Iterator<Entry<String, Double>> it = mapHL.entrySet().iterator();
				for(int i=0;i<mapHL.size();i++){
		            Entry<String, Double> entry = it.next();
		            Double value = entry.getValue();
		            if(value > maxValue){
		                maxValue = value;
		                maxKey = entry.getKey().toString();
		                num = num * maxValue;
		            }
		        }
			}
		}
		return num;
	}
	
	/**
	 * @Description: 设置顺色到BOM
	 * @param 
	 * @return void
	 */
	public void orgSSBom(List<BtcconfigBVO> list_bom,JSONObject frajson,String proclassid){
		if(!CommUtil.isNull(frajson)){
			for (BtcconfigBVO btcconfigBVO : list_bom) {
				String sname = btcconfigBVO.getVsname();
				String code = null;
				String fracode = frajson.getString("code");//面料编码
				if("daibu".equals(sname)){
					//袋布
					code = getSSCode(proclassid,fracode,IConstant.BOM_JC_DAIBU);
					if(!CommUtil.isNullOrEm(code)){
						btcconfigBVO.setVcode(code);
						btcconfigBVO.setVspec(getSpec(btcconfigBVO.getVcode())+"");
					}
					setBomEm(list_bom,IConstant.BOM_JC_DAIBU,btcconfigBVO,"y");
				}else if("lalian".equals(sname)){
					//拉链
					code = getSSCode(proclassid,fracode,IConstant.BOM_JC_LALIAN);
					if(!CommUtil.isNullOrEm(code)){
						btcconfigBVO.setVcode(code);
					}
					btcconfigBVO.setVspec(getSpec(btcconfigBVO.getVcode())+"");
					setBomEm(list_bom,IConstant.BOM_JC_LALIAN,btcconfigBVO,"y");
				}
			}
		}
	}
	
	/**
	 * @Description: 设置耗料到BOM
	 * @param 
	 * @return void
	 */
	public void orgBom(List<BtcconfigBVO> list_bom,JSONObject json,JSONObject frajson,String vsn,String proclassid){
		if(!CommUtil.isNull(json)){
			BtcconfigBVO vo = new BtcconfigBVO();
			String code = json.getString("code");
			String spec = json.getString("specname");//面料、里料规格
			//处理顺色-start
			if(IConstant.PART_CODE_SS.equals(code)){
				String fracode = frajson.getString("code");//面料编码
				String tcode = getSSCode(proclassid,fracode,vsn);
				if(!CommUtil.isNullOrEm(tcode)){
					code = tcode;
				}else{
					code = null;
				}
				if(!CommUtil.isNull(code)){
					//String specsql = "select specname from fz_auxiliary where vmoduletype='"+IConstant.MOD_LINING+"' and vcode='"+code+"'";
					//spec = getStrBySQL(specsql) + "";
					spec = getSpec(code)+"";
				}else{
					spec = null;
				}
			}else if(IConstant.PART_CODE_T1.equals(code)){//同里料
				for (BtcconfigBVO btcconfigBVO : list_bom) {
					String sname = btcconfigBVO.getVsname();
					if("lining".equals(sname)){
						if(!CommUtil.isNull(btcconfigBVO.getVcode())){
							code = btcconfigBVO.getVcode();
						}
						if(!CommUtil.isNull(code)){
							spec = getSpec(code)+"";
						}
					}
				}
			}else{
				spec = getSpec(code)+"";
			}
			vo.setVcode(code);
			//处理顺色-end
			if(!CommUtil.isNull(spec)){
//				String[] aspec = spec.split(IConstant.SPEC_3D_SPLIT);
//				if(aspec.length == 2){
//					vo.setVspec(CommUtil.getNumbers(aspec[0]));
//				}else{
//					vo.setVspec("140");//默认规格
//				}
				vo.setVspec(spec);
				setBomEm(list_bom,vsn,vo,null);
			}
		}
	}
	
	/**
	 * @Description: 根据面料获取顺色数据
	 * @param @param fracode
	 * @param @param vsn
	 * @param @return
	 * @return String
	 */
	public String getSSCode(String proclassid,String fracode,String vsn){
		String sscode = null;
		//里料
		if(IConstant.BOM_JC_LINING.equals(vsn)){
			String sql = "select c.vcode from fz_auxiliary a left join fz_auxiliary_b b on a.id=b.auxiliaryId left join fz_auxiliary c on c.id=b.icisLining where a.vmoduletype='"+IConstant.MOD_FABRIC+"' and c.vmoduletype='"+IConstant.MOD_LINING+"' and a.vcode='"+fracode+"' and b.proclassid="+proclassid;
			sscode = getStrBySQL(sql)+"";
		}else if(IConstant.BOM_JC_SLEEVELINING.equals(vsn)){
			String sql = "select c.vcode from fz_auxiliary a left join fz_auxiliary_b b on a.id=b.auxiliaryId left join fz_auxiliary c on c.id=b.icisXLining where a.vmoduletype='"+IConstant.MOD_FABRIC+"' and c.vmoduletype='"+IConstant.MOD_LINING+"' and a.vcode='"+fracode+"' and b.proclassid="+proclassid;
			sscode = getStrBySQL(sql)+"";
		}else if(IConstant.BOM_JC_BACKFABRIC.equals(vsn)){
			String sql = "select c.vcode from fz_auxiliary a left join fz_auxiliary_b b on a.id=b.auxiliaryId left join fz_auxiliary c on c.id=b.icisHBLining where a.vmoduletype='"+IConstant.MOD_FABRIC+"' and c.vmoduletype='"+IConstant.MOD_LINING+"' and a.vcode='"+fracode+"' and b.proclassid="+proclassid;
			sscode = getStrBySQL(sql)+"";
		}else if(IConstant.BOM_JC_LALIAN.equals(vsn)){//拉链
			String sql = "select c.vcode from fz_auxiliary a left join fz_auxiliary_b b on a.id=b.auxiliaryId left join fz_auxiliary c on c.id=b.iciszipper where a.vmoduletype='"+IConstant.MOD_FABRIC+"' and c.vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and a.vcode='"+fracode+"' and b.proclassid="+proclassid;
			sscode = getStrBySQL(sql)+"";
		}else if(IConstant.BOM_JC_DAIBU.equals(vsn)){//袋布
			String sql = "select c.vcode from fz_auxiliary a left join fz_auxiliary_b b on a.id=b.auxiliaryId left join fz_auxiliary c on c.id=b.icisBagging where a.vmoduletype='"+IConstant.MOD_FABRIC+"' and c.vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and a.vcode='"+fracode+"' and b.proclassid="+proclassid;
			sscode = getStrBySQL(sql)+"";
		}
		return sscode;
	}
	
	/**
	 * @Description:更新BOM
	 * @param @param list_bom
	 * @param @param vsn
	 * @param @param btvo
	 * @param @param fg 标准中是否（y,n）r：替换单耗
	 * @return void
	 */
	public void setBomEm(List<BtcconfigBVO> list_bom,String vsn,BtcconfigBVO btvo,String fg){
		if(list_bom != null){
			if("n".equals(fg)){//撞色、贴布
				BtcconfigBVO cBVO = new BtcconfigBVO();
				cBVO.setVcode(btvo.getVcode());
				Double dh = btvo.getNunitmny();
				cBVO.setNunitmny(dh);
				//规格
				cBVO.setVspec(btvo.getVspec());
				list_bom.add(cBVO);
			}else{
				/*List<BtcconfigBVO> list_bom_tem = new ArrayList<BtcconfigBVO>();
				for (BtcconfigBVO btcconfigBVO : list_bom) {
					list_bom_tem.add(btcconfigBVO);
				}*/
				for (BtcconfigBVO btcconfigBVO : list_bom) {
					String vsname = btcconfigBVO.getVsname();
					if(vsn.equals(vsname) && fg == null){//本有面料、里料
						if(!CommUtil.isNull(btvo.getVcode())){
							btcconfigBVO.setVcode(btvo.getVcode());
						}
						//单耗（原幅宽*耗料/现幅宽）
						String vspec = btcconfigBVO.getVspec();//原幅宽
						Double dh = btcconfigBVO.getNunitmny();//耗料
						Double dspec = Double.valueOf(vspec);
						Double nspec = Double.valueOf(btvo.getVspec());//现幅宽
						btcconfigBVO.setNunitmny((dh * dspec)/nspec);
						//规格
						btcconfigBVO.setVspec(btvo.getVspec());
					}else if(!isContains(list_bom,vsn) && fg == null){//本无面料、里料
						BtcconfigBVO vo = new BtcconfigBVO();
						vo.setVsname(vsn);
						vo.setVcode(btvo.getVcode());
						Double nspec = Double.valueOf(btvo.getVspec());
						vo.setNunitmny(nspec);
						//规格
						vo.setVspec(btvo.getVspec());
						list_bom.add(vo);
						break;
					}else if(vsn.equals(vsname) &&"y".equals(fg)){//特殊锁眼、珠边（改变编码）
						if(!CommUtil.isNull(btvo.getVcode())){
							btcconfigBVO.setVcode(btvo.getVcode());
						}
					}else if(vsn.equals(vsname) &&"r".equals(fg)){//纽扣（替换单耗）
						btcconfigBVO.setNunitmny(btvo.getNunitmny());
					}
					//list_bom_tem.add(btcconfigBVO);
				}
				//list_bom = list_bom_tem;
			}
		}
	}
	
	/**
	 * @Description: 标准BOM是否含有此料件
	 * @param @return
	 * @return boolean
	 */
	public boolean isContains(List<BtcconfigBVO> list_bom,String vsn){
		boolean flag = false;
		for (BtcconfigBVO btcconfigBVO : list_bom) {
			String vsname = btcconfigBVO.getVsname();
			if(vsn.equals(vsname)){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * @Description: 品类编码
	 * @param @param proTypeCode
	 * @param @return
	 * @return String
	 */
	public String getProCode(String proTypeCode){
		StringBuffer code = new StringBuffer("3041");
		if(proTypeCode != null && !"".equals(proTypeCode)){
			code.append(CommUtil.getNumbers(proTypeCode));
		}else{
			code.append("00");
		}
		//量身定制
		code.append(IConstant.SYSTEMTYPE);
		//年月日
		String ymd = DateUtil.getCurDate("yyyyMMdd").substring(2);
		String num = ((Integer.valueOf("1"+getMaxProCode(ymd))+1)+"").substring(1);
		code.append(ymd);
		//连接符
		code.append("-");
		//流水号
		code.append(num);
		System.out.println(code.toString());
		return code.toString();
	}
	
	/**
	 * @Description: 获得当天最大流水号
	 * @param @param ymd
	 * @param @return
	 * @return String
	 */
	public String getMaxProCode(String ymd){
		String maxcode = "000000";
		String sql = "select max(substring(vcode,15)) from fz_diyInfo where substring(vcode,8,6) ='"+ymd+"'";
		Object max = getStrBySQL(sql); 
		
		if(max != null){
			maxcode = max.toString();
		}
		return maxcode;
	}
	
	/**
	 * @Description: 通过品类获得料件耗料信息
	 * @param @return
	 * @return List<CfeedBVO>
	 */
	public List<CfeedBVO> getCfeedByPro(String proclassid){
		if(proclassid == null || "".equals(proclassid)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" b.vcode,b.vname,b.vstate,b.nunitmny,b.vmemo");
		sql.append(" from fz_cfeed c");
		sql.append(" left join fz_cfeed_b b");
		sql.append(" on c.id=b.feedid");
		sql.append(" where ifnull(c.dr,0)=0");
		sql.append(" and c.proclassid="+proclassid);
		
		Integer total = getCountBySQL(sql.toString());
		
		List<CfeedBVO> list_b = (List<CfeedBVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);
		List<CfeedBVO> list = new ArrayList<CfeedBVO>();
		if(list_b != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(list_b);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				CfeedBVO dto = new CfeedBVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setVcode(CommUtil.isNullOrEm(arry[0]) ? null : arry[0].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVstate(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setNunitmny(CommUtil.isNullOrEm(arry[3]) ? null : Double.valueOf(arry[3].toString()));
				dto.setVmemo(CommUtil.isNullOrEm(arry[4]) ? null : arry[4].toString());
				
				list.add(dto);
			}
		}
		return list;
	}
	
	/**
	 * @Description: 通过品类获得BOM标配信息
	 * @param @return
	 * @return List<CfeedBVO>
	 */
	public List<BtcconfigBVO> getBOMByPro(String proclassid){
		if(proclassid == null || "".equals(proclassid)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" b.vcode,b.vname,b.vsname,b.vspec,b.nunitmny,b.vjobnum,b.vmemo,c.vproClass as vdef1,b.vserial");
		sql.append(" from fz_btcconfig c");
		sql.append(" left join fz_btcconfig_b b");
		sql.append(" on c.id=b.btcId");
		sql.append(" where ifnull(c.dr,0)=0");
		sql.append(" and c.proclassid="+proclassid);
		
		Integer total = getCountBySQL(sql.toString());
		
		List<BtcconfigBVO> list_b = (List<BtcconfigBVO>)iHibernateDAO.findListBySQL(sql.toString(), null, 0, total);
		List<BtcconfigBVO> list = new ArrayList<BtcconfigBVO>();
		if(list_b != null){
			/*
			 * 多表自定义SQL重组List<E>
			 */
			String jsonstr = ObjectToJSON.writeListJSON(list_b);
			JSONArray jsonArr = JSONArray.fromObject(jsonstr);
			int size = jsonArr.size();
			for (int i = 0; i < size; i++) {
				BtcconfigBVO dto = new BtcconfigBVO();
				Object[] arry = jsonArr.getJSONArray(i).toArray();
				dto.setVcode(CommUtil.isNullOrEm(arry[0]) ? null : arry[0].toString());
				dto.setVname(CommUtil.isNullOrEm(arry[1]) ? null : arry[1].toString());
				dto.setVsname(CommUtil.isNullOrEm(arry[2]) ? null : arry[2].toString());
				dto.setVspec(CommUtil.isNullOrEm(arry[3]) ? null : arry[3].toString());
				dto.setNunitmny(CommUtil.isNullOrEm(arry[4]) ? null : Double.valueOf(arry[4].toString()));
				dto.setVjobnum(CommUtil.isNullOrEm(arry[5]) ? null : arry[5].toString());
				dto.setVmemo(CommUtil.isNullOrEm(arry[6]) ? null : arry[6].toString());
				dto.setVdef1(CommUtil.isNullOrEm(arry[7]) ? null : arry[7].toString());
				dto.setVserial(CommUtil.isNullOrEm(arry[8]) ? null : arry[8].toString());
				list.add(dto);
			}
		}
		return list;
	}
	
	/**
	 * @Description: 更新保存定制信息
	 * @param 3D js form提交
	 * @return void 
	 */
	public void saveDiyInfo(){
		System.out.println("-------------------定制信息保存开始--------------------");
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String allInfo = request.getParameter("diyBom");
		
	//	allInfo = "{\"diyType\":\"2;;;3\",\"diyCode\":\"354#355\",\"diyCraft\":\"GL(内里型-01全内里)-BD(内里笔袋-02圆);;;-BX(版型-1常规版型)\",\"diyCount\":2,\"prodPrice\":\"4360;;;1960\",\"diyBom\":\"版型:1-常规版型 领驳:13-平驳头窄 下摆:2-圆角 扣位数:12-单排两扣(1*2) 后片:0-无开衩 袖衩:0-无袖叉 胸袋:5-船形 下袋:2-平口袋带袋盖 票袋:00-无票袋 内里型:01-全内里 内里挂面:1-直 内里笔袋:02-圆 衣里料:SS-顺色/标配 袖里料:SS-顺色/标配 袖扣工艺:01-叠扣 袖扣数:04-4粒 纽扣号:1301310336-尿素扣黑 绣字位置: 绣字线号: 绣字字体: 绣字内容: 撞色部位: 贴布部位: 特殊锁眼: 珠边工艺: 绣图部位: 绣图图案: 领带: 衬衣: ;;;版型:1-常规版型 前片:10-前无褶 腰头:1-宝剑头 腰里:3-普通腰里 后片:11-后单省 前口袋:10-斜插无牙条 后口袋:1-单开线 脚口:01-内折边（常规） 珠边工艺:00-无珠边 纽扣号:1301350918-尿素扣蓝 绣字位置: 绣字线号: 绣字字体: 绣字内容: \",\"formatType\":\"5;;;5\",\"diyBomDesc\":\"面料--品种:二件套 数量:1\\n名称:毛绒条灰 花型:条\\n成份:毛绒 色系:灰\\n编码:11113330001 品级:A\\n单价:4360 总价:5960\\n西服上衣--版型:常规版型 领驳:平驳头窄\\n下摆:圆角 扣位数:单排两扣(1*2)\\n后片:无开衩 袖衩:无袖叉\\n胸袋:船形 下袋:平口袋带袋盖\\n票袋:无票袋 内里型:全内里\\n内里挂面:直 内里笔袋:圆\\n衣里料:顺色/标配 袖里料:顺色/标配\\n袖扣工艺:叠扣 袖扣数:4粒\\n纽扣号:尿素扣黑 绣字位置:\\n绣字线号: 绣字字体:\\n绣字内容: 撞色部位:\\n贴布部位: 特殊锁眼:\\n珠边工艺: 绣图部位:\\n绣图图案: 领带:\\n衬衣: \\n面料--品种:二件套 数量:1\\n名称:毛绒条灰 花型:条\\n成份:毛绒 色系:灰\\n编码:11113330001 品级:A\\n单价:1960 总价:5960\\n;;;西裤--版型:常规版型 前片:前无褶\\n腰头:宝剑头 腰里:普通腰里\\n后片:后单省 前口袋:斜插无牙条\\n后口袋:单开线 脚口:内折边（常规）\\n珠边工艺:无珠边 纽扣号:尿素扣蓝\\n绣字位置: 绣字线号:\\n绣字字体: 绣字内容:\\n\",\"prodCode\":\"151011212131005201;;;151101101011111101\",\"diySize\":\"TT010:43;TT016:45;JJB:45;TT017:42;JJW:43;TT011:34;TT012:34;TT015:34;TT018:34;TT007:54;TT014:45;TT013:56;TT006:4;TT008:43;TT009:43;TT019:43;SHAPTP:A;SHAPDS:;DRESTP:b;DRESDS:;BUTNHB:e;BUTNDS:;JLG:1;JQK:1;JTD:2;JLB:1;JTX:2;JLJ:2;JXL1:2;FBRTP1:1;JSG:162;CUSTGD:M;MMSPAL:1\\n领围:43 前胸宽:45 胸围:45 肚围:42 腰围:43 臀围:34 总肩宽:34 袖长:34 后背宽:34 后中长:54 手臂围:45 手腕围:56 前衣长:4 前腰节长:43 后腰节长:43 肩斜:43 基本体型:体一般体型 基本体型说明: 穿着习惯:合体 穿着习惯说明: 纽扣习惯:扣一粒 纽扣习惯说明: 领高:轻 前胸领子发空:轻 凸肚:中 直背:轻 挺胸体:中 溜肩:中 袖笼变浅:中 身高:162 \\n3\\n01;;;TT007:43;TT008:34;TT006:34;TT009:23;TT018:12;TT016:34;TT019:34;TT020:43;SHAPTP:A;SHAPDS:;DRESTP:b;DRESDS:;BUTNHB:e;BUTNDS:;TXT:2;PTP:2;FBRTP1:1;TSG:162;CUSTGD:M;MMSPAL:1\\n腰围:43 臀围:34 裤长:34 大腿根围:23 腿肚围:12 通裆:34 前裤腰距腰节距离:34 后裤腰距腰节距离:43 基本体型:体一般体型 基本体型说明: 穿着习惯:合体 穿着习惯说明: 纽扣习惯:扣一粒 纽扣习惯说明: X型腿:中 平臀:中 身高:162 \\n3\\n10\",\"prodName\":\"西服上衣;;;西裤\",\"vbom\":\"[{\\\"tc_aah01\\\":\\\"304101151026-000009\\\",\\\"tc_aah02\\\":\\\"西服上衣\\\",\\\"tc_aah03\\\":\\\"\\\",\\\"tc_aah04\\\":\\\"T001\\\",\\\"tc_aah05\\\":\\\"11113330001\\\",\\\"tc_aah06\\\":\\\"1\\\",\\\"tc_aah07\\\":\\\"148.0\\\",\\\"tc_aah08\\\":\\\"1\\\",\\\"tc_aah09\\\":\\\"0001\\\"}];;;[{\\\"tc_aah01\\\":\\\"304110151026-000010\\\",\\\"tc_aah02\\\":\\\"西裤\\\",\\\"tc_aah03\\\":\\\"\\\",\\\"tc_aah04\\\":\\\"X009\\\",\\\"tc_aah05\\\":\\\"11113330001\\\",\\\"tc_aah06\\\":\\\"1\\\",\\\"tc_aah07\\\":\\\"148.0\\\",\\\"tc_aah08\\\":\\\"1\\\",\\\"tc_aah09\\\":\\\"0001\\\"}]\",\"diyImgUrl\":\"http://3d.deelkall.com/Clothes/userClothes/201510261412163392580_fron_01.jpg|http://3d.deelkall.com/Clothes/userClothes/201510261412177001008_left_01.jpg|http://3d.deelkall.com/Clothes/userClothes/201510261412199780675_back_01.jpg;;;http://3d.deelkall.com/Clothes/userClothes/201510261412203032670_fron_10.jpg|http://3d.deelkall.com/Clothes/userClothes/201510261412211518798_left_10.jpg|http://3d.deelkall.com/Clothes/userClothes/201510261412228331469_back_10.jpg\"}";
		
		JSONObject diyInfo = JSONObject.fromObject(allInfo);
		//id
		String diyCode = diyInfo.getString("diyCode");
		//产品编码
		String prodCode = diyInfo.getString("prodCode");
		//产品名称
		String prodName = diyInfo.getString("prodName");
		//定制信息code+名称
		String diyBom = diyInfo.getString("diyBom");
		//定制信息中文说明
		String diyBomDesc = diyInfo.getString("diyBomDesc");
		//3D图片路径
		String diyImgUrl = diyInfo.getString("diyImgUrl");
		//产品类型
		String diyType = diyInfo.getString("diyType");
		//产品尺码信息
		String diySize = diyInfo.getString("diySize");
		//产品工艺编码
		String diyCraft = diyInfo.getString("diyCraft");
		//商品单价
		String prodPrice = diyInfo.getString("prodPrice");
		//版型
		String formatType = diyInfo.getString("formatType");
		//BOM
		String vbom = diyInfo.getString("vbom");
		/*//id
		String diyCode = request.getParameter("diyCode");
		//产品编码
		String prodCode = request.getParameter("prodCode");
		//产品名称
		String prodName = request.getParameter("prodName");
		//定制信息code+名称
		String diyBom = request.getParameter("diyBom");
		//定制信息中文说明
		String diyBomDesc = request.getParameter("diyBomDesc");
		//3D图片路径
		String diyImgUrl = request.getParameter("diyImgUrl");
		//产品类型
		String diyType = request.getParameter("diyType");
		//产品尺码信息
		String diySize = request.getParameter("diySize");
		//产品工艺编码
		String diyCraft = request.getParameter("diyCraft");
		//商品单价
		String prodPrice = request.getParameter("prodPrice");
		//版型
		String formatType = request.getParameter("formatType");
		//BOM
		String vbom = request.getParameter("vbom");*/
		
		if(!CommUtil.isNull(diyCode)){
			String[] adiyCode = diyCode.split(IConstant.BOM_3D_SPLIT);
			int len = adiyCode.length;
			Map<String,String[]> map = new HashMap<String,String[]>();
			if(!CommUtil.isNull(prodCode)){
				String[] aprodCode = prodCode.split(IConstant.BOM_SPLIT);
				map.put("aprodCode", aprodCode);
			}
			if(!CommUtil.isNull(prodName)){
				String[] aprodName = prodName.split(IConstant.BOM_SPLIT);
				map.put("aprodName", aprodName);
			}
			if(!CommUtil.isNull(diyBom)){
				String[] adiyBom = diyBom.split(IConstant.BOM_SPLIT);
				map.put("adiyBom", adiyBom);
			}
			if(!CommUtil.isNull(diyBomDesc)){
				String[] adiyBomDesc = diyBomDesc.split(IConstant.BOM_SPLIT);
				map.put("adiyBomDesc", adiyBomDesc);
			}
			if(!CommUtil.isNull(diyImgUrl)){
				String[] adiyImgUrl = diyImgUrl.split(IConstant.BOM_SPLIT);
				map.put("adiyImgUrl", adiyImgUrl);
			}
			if(!CommUtil.isNull(diyType)){
				String[] adiyType = diyType.split(IConstant.BOM_SPLIT);
				map.put("adiyType", adiyType);
			}
			if(!CommUtil.isNull(diySize)){
				String[] adiySize = diySize.split(IConstant.BOM_SPLIT);
				map.put("adiySize", adiySize);
			}
			if(!CommUtil.isNull(diyCraft)){
				String[] adiyCraft = diyCraft.split(IConstant.BOM_SPLIT);
				map.put("adiyCraft", adiyCraft);
			}
			if(!CommUtil.isNull(prodPrice)){
				String[] aprodPrice = prodPrice.split(IConstant.BOM_SPLIT);
				map.put("aprodPrice", aprodPrice);
			}
			if(!CommUtil.isNull(formatType)){
				String[] aformatType = formatType.split(IConstant.BOM_SPLIT);
				map.put("aformatType", aformatType);
			}
			if(!CommUtil.isNull(vbom)){
				String[] avbom = vbom.split(IConstant.BOM_SPLIT);
				map.put("avbom", avbom);
			}
			try {
				for(int i = 0; i < len; i++){
					DiyInfoVO diyInfoVO = new DiyInfoVO();//iHibernateDAO.findFirst(DiyInfoVO.class, " id="+adiyCode[i]);
					if(!CommUtil.isNull(map.get("aprodCode"))){
						diyInfoVO.setProdCode(map.get("aprodCode")[i]);
					}
					if(!CommUtil.isNull(map.get("aprodName"))){
						diyInfoVO.setProdName(map.get("aprodName")[i]);
					}
					if(!CommUtil.isNull(map.get("adiyBom"))){
						diyInfoVO.setDiyBom(map.get("adiyBom")[i]);
					}
					if(!CommUtil.isNull(map.get("adiyBomDesc"))){
						diyInfoVO.setDiyBomDesc(map.get("adiyBomDesc")[i]);
					}
					if(!CommUtil.isNull(map.get("adiyImgUrl"))){
						diyInfoVO.setDiyImgUrl(map.get("adiyImgUrl")[i]);
					}
					if(!CommUtil.isNull(map.get("adiyType"))){
						diyInfoVO.setDiyType(map.get("adiyType")[i]);
					}
					if(!CommUtil.isNull(map.get("adiySize"))){
						diyInfoVO.setDiySize(map.get("adiySize")[i]);
					}
					if(!CommUtil.isNull(map.get("adiyCraft"))){
						diyInfoVO.setDiyCraft(map.get("adiyCraft")[i]);
					}
					if(!CommUtil.isNull(map.get("aprodPrice"))){
						diyInfoVO.setProdPrice(map.get("aprodPrice")[i]);
					}
					if(!CommUtil.isNull(map.get("aformatType"))){
						diyInfoVO.setFormatType(map.get("aformatType")[i]);
					}
					if(!CommUtil.isNull(map.get("avbom"))){
						diyInfoVO.setVbom(map.get("avbom")[i]);
					}
					
						//iHibernateDAO.delete(DiyInfoVO.class, Integer.valueOf(adiyCode[i]));
						//获取成品编码
						JSONArray reqbom = JSONArray.fromObject(diyInfoVO.getVbom());
						String tc_aah01 = reqbom.getJSONObject(0).get("tc_aah01")+"";
						diyInfoVO.setVcode(tc_aah01);
						diyInfoVO.setTs(new Timestamp(System.currentTimeMillis()));
						iHibernateDAO.save(diyInfoVO);
						System.out.println("-------------------定制信息保存结束--------------------");
				}
				renderText("定制信息保存成功!");
			} catch (Exception e) {
				renderText("定制信息保存异常!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @Description: 远程获取幅宽 根据料号
	 * @param @param vcode
	 * @param @return
	 * @return Integer
	 */
	public Integer getSpec(String vcode){
		String url = "http://3d.deelkall.com/remote/webservice/getSpec/"+vcode;
		//String url = "http://127.0.0.1:8080/remote/webservice/getSpec/"+vcode;
		String info = HttpTools.getDataByURL(url);
		
		if(info != null){
			return Integer.valueOf(info);
		}else{
			return  140;
		}
	}
	
	//测试
	public void test(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String xisuo = request.getParameter("xisuo");
		String[] prodCode = request.getParameterValues("prodCode[]");
		String[] prodName = request.getParameterValues("prodName[]");
		System.out.println(prodCode);
		System.out.println(getSpec("11112111046"));
		/*String price = request.getParameter("num");
		renderJs(CommUtil.toTurnPrice(Double.valueOf(price)));*/
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
	 * 根据sql获得count
	 * @param sql
	 * @return
	 */
	public Object getStrBySQL(String sql){
		return iHibernateDAO.getFirstBySQL("select * from ("+sql+") tb", null);
	}
	
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
}
