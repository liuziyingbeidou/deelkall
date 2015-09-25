package action.system;

import itf.pub.IConstant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import model.auxiliary.AuxiliaryVO;
import model.mold.ModelVO;
import model.size.SizeDocVO;
import model.sp.system.DiyInfoVO;
import model.special.SpecialVO;

import org.apache.struts2.ServletActionContext;

import util.CommUtil;
import utils.FileUtils;
import utils.JsonUtils;
import utils.ReadFile;
import action.BaseAction;
import dao.IHibernateDAO;
import dao.IMyHibernateDao;

public class SystemAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private IHibernateDAO iHibernateDAO;
	private IMyHibernateDao iMyHibernateDao;
	private String url = "./";
	private DiyInfoVO diyInfoVO;
	
	/**
	 * @Description: 自助清理界面
	 * @param @return
	 * @return String
	 */
	public String toClean(){
		this.setUrl("../files/system/autoclean.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 自助清理
	 * @param 
	 * @return void
	 */
	public void autoClean(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		String message = "请选择清理模块";
		if(type != null){
			Integer ty = Integer.valueOf(type);
			try {
				String splt = "/";
				switch(ty){
				//面料
				case 1:
					String savePathF = "/opt/nginx/html/3DRES/Clothes/fabricImage";//"D:\\Apache2.2\\htdocs\\Clothes\\fabricImage";//
					List<String> flist = ReadFile.readfile(savePathF);
					List<AuxiliaryVO> fraList = (List<AuxiliaryVO>)getInfo(AuxiliaryVO.class," vmoduletype='"+IConstant.MOD_FABRIC+"' and vfileupload is not null");
					if(flist != null){
						if(fraList != null){
							AuxiliaryVO[] arrayF = fraList.toArray(new AuxiliaryVO[fraList.size()]);
							Iterator it = flist.iterator();
							while(it.hasNext()){
								Object o = it.next();
								if(findData(arrayF,o)){
									it.remove();
								}
							}
						}
						for(int k = 0; k < flist.size(); k++){
							String file = flist.get(k);
							ReadFile.deletefile(savePathF+splt+file);
							int index = file.lastIndexOf(".");
					           if (index != -1) {
					              String minFile = file.substring(0,index) +"_min"+ file.substring(index);
					              ReadFile.deletefile(savePathF+splt+"min"+splt+minFile);
					           }
						}
					}
					break;
				//里料
				case 2:
					String savePathL = "/opt/nginx/html/3DRES/Clothes/liningImage";//"D:\\Apache2.2\\htdocs\\Clothes\\liningImage";//
					List<String> llist = ReadFile.readfile(savePathL);
					List<AuxiliaryVO> linList = (List<AuxiliaryVO>)getInfo(AuxiliaryVO.class," vmoduletype='"+IConstant.MOD_LINING+"' and vfileupload is not null");
					if(llist != null){
						if(linList != null){
							AuxiliaryVO[] arrayL = linList.toArray(new AuxiliaryVO[linList.size()]);
							Iterator it = llist.iterator();
							while(it.hasNext()){
								Object o = it.next();
								if(findData(arrayL,o)){
									it.remove();
								}
							}
						}
						for(int k = 0; k < llist.size(); k++){
							String file = llist.get(k);
							ReadFile.deletefile(savePathL+splt+file);
							int index = file.lastIndexOf(".");
					           if (index != -1) {
					              String minFile = file.substring(0,index) +"_min"+ file.substring(index);
					              ReadFile.deletefile(savePathL+splt+"min"+splt+minFile);
					           }
						}
					}
					break;
				//辅料
				case 3:
					String savePathA = "/opt/nginx/html/3DRES/Clothes/accImage";//"D:\\Apache2.2\\htdocs\\Clothes\\accImage";//
					List<String> alist = ReadFile.readfile(savePathA);
					List<AuxiliaryVO> accList = (List<AuxiliaryVO>)getInfo(AuxiliaryVO.class," vmoduletype='"+IConstant.MOD_ACCESSORIES+"' and vfileupload is not null");
					if(alist != null){
						if(accList != null){
							AuxiliaryVO[] arrayA = accList.toArray(new AuxiliaryVO[accList.size()]);
							Iterator it = alist.iterator();
							while(it.hasNext()){
								Object o = it.next();
								if(findData(arrayA,o)){
									it.remove();
								}
							}
						}
						for(int k = 0; k < alist.size(); k++){
							String file = alist.get(k);
							ReadFile.deletefile(savePathA+splt+file);
							int index = file.lastIndexOf(".");
					           if (index != -1) {
					              String minFile = file.substring(0,index) +"_min"+ file.substring(index);
					              ReadFile.deletefile(savePathA+splt+"min"+splt+minFile);
					           }
						}
					}
					break;
				//包装材料
				case 4:
					String savePathP = "/opt/nginx/html/3DRES/Clothes/packImage";//"D:\\Apache2.2\\htdocs\\Clothes\\packImage";//
					List<String> plist = ReadFile.readfile(savePathP);
					List<AuxiliaryVO> pacList = (List<AuxiliaryVO>)getInfo(AuxiliaryVO.class," vmoduletype='"+IConstant.MOD_PACKING+"' and vfileupload is not null");
					if(plist != null){
						if(pacList != null){
							AuxiliaryVO[] arrayP = pacList.toArray(new AuxiliaryVO[pacList.size()]);
							Iterator it = plist.iterator();
							while(it.hasNext()){
								Object o = it.next();
								if(findData(arrayP,o)){
									it.remove();
								}
							}
						}
						for(int k = 0; k < plist.size(); k++){
							String file = plist.get(k);
							ReadFile.deletefile(savePathP+splt+file);
							int index = file.lastIndexOf(".");
					           if (index != -1) {
					              String minFile = file.substring(0,index) +"_min"+ file.substring(index);
					              ReadFile.deletefile(savePathP+splt+"min"+splt+minFile);
					           }
						}
					}
					break;
				//配饰
				case 5:
					String savePathO = "/opt/nginx/html/3DRES/Clothes/outsrcImage";//"D:\\Apache2.2\\htdocs\\Clothes\\outsrcImage";//
					List<String> olist = ReadFile.readfile(savePathO);
					List<AuxiliaryVO> outList = (List<AuxiliaryVO>)getInfo(AuxiliaryVO.class," vmoduletype='"+IConstant.MOD_OUTSOURCE+"' and vfileupload is not null");
					if(olist != null){
						if(outList != null){
							AuxiliaryVO[] arrayO = outList.toArray(new AuxiliaryVO[outList.size()]);
							Iterator it = olist.iterator();
							while(it.hasNext()){
								Object o = it.next();
								if(findData(arrayO,o)){
									it.remove();
								}
							}
						}
						for(int k = 0; k < olist.size(); k++){
							String file = olist.get(k);
							ReadFile.deletefile(savePathO+splt+file);
							int index = file.lastIndexOf(".");
					           if (index != -1) {
					              String minFile = file.substring(0,index) +"_min"+ file.substring(index);
					              ReadFile.deletefile(savePathO+splt+"min"+splt+minFile);
					           }
						}
					}
					break;
				//特殊档案
				case 6:
					String savePathS = "/opt/nginx/html/3DRES/Clothes/DocImage";//"D:\\Apache2.2\\htdocs\\Clothes\\DocImage";//
					List<String> slist = ReadFile.readfile(savePathS);
					List<SpecialVO> speList = (List<SpecialVO>)getInfo(SpecialVO.class," vfileupload is not null");
					if(slist != null){
						if(speList != null){
							SpecialVO[] arrayS = speList.toArray(new SpecialVO[speList.size()]);
							Iterator it = slist.iterator();
							while(it.hasNext()){
								Object o = it.next();
								if(findData(arrayS,o)){
									it.remove();
								}
							}
						}
						for(int k = 0; k < slist.size(); k++){
							String file = slist.get(k);
							ReadFile.deletefile(savePathS+splt+file);
						}
					}
					break;
				//体型特征
				case 7:
					String savePathZ = "/opt/nginx/html/3DRES/Clothes/sizeImage";//"D:\\Apache2.2\\htdocs\\Clothes\\sizeImage";//
					List<String> zlist = ReadFile.readfile(savePathZ);
					List<SizeDocVO> ziList = (List<SizeDocVO>)getInfo(SizeDocVO.class," vfileupload is not null");
					if(zlist != null){
						if(ziList != null){
							SizeDocVO[] arrayZ = ziList.toArray(new SizeDocVO[ziList.size()]);
							Iterator it = zlist.iterator();
							while(it.hasNext()){
								Object o = it.next();
								if(findData(arrayZ,o)){
									it.remove();
								}
							}
						}
						for(int k = 0; k < zlist.size(); k++){
							String file = zlist.get(k);
							ReadFile.deletefile(savePathZ+splt+file);
						}
					}
					break;
				//模型
				case 8:
					String savePathM = "/opt/nginx/html/3DRES/Clothes/TypeModel";//"D:\\Apache2.2\\htdocs\\Clothes\\TypeModel";//
					List<String> mlist = ReadFile.readfile(savePathM);
					List<ModelVO> moList = (List<ModelVO>)getInfo(ModelVO.class," vfileupload is not null");
					if(mlist != null){
						if(moList != null){
							//所有品类下的基模
							String[] arrayInit = new String[]{"test.3ds"};
							ModelVO[] arrayM = moList.toArray(new ModelVO[moList.size()]);
							Iterator it = mlist.iterator();
							while(it.hasNext()){
								Object o = it.next();
								if(findData(arrayM,o) || CommUtil.check(o.toString())){
									it.remove();
								}
							}
						}
						for(int k = 0; k < mlist.size(); k++){
							String file = mlist.get(k);
							String[] paths = new String[]{savePathM+splt+"CasualPant"+splt+file,savePathM+splt+"ChineseCollar"+splt+file,
									savePathM+splt+"ChineseSuit"+splt+file,savePathM+splt+"Coat"+splt+file,
									savePathM+splt+"LongCoat"+splt+file,savePathM+splt+"LongShirt"+splt+file,
									savePathM+splt+"ShortCoat"+splt+file,savePathM+splt+"ShortShirt"+splt+file,
									savePathM+splt+"SuitJacket"+splt+file,savePathM+splt+"SuitPant"+splt+file,
									savePathM+splt+"WaistCost"+splt+file
									};
							ReadFile.deletefile(paths);
						}
					}
					break;
				}
				message = "清理完成";
			} catch (Exception ex) {
            }
		}
		renderText(message);
	}
	
	/**
	 * @Description: AuxiliaryVO判断数组中是否包含某一元素
	 * @param @param array
	 * @param @param o
	 * @param @return
	 * @return boolean
	 */
	public boolean findData(AuxiliaryVO[] array,Object o){
		boolean flag = false;
		for(int i = 0; i < array.length; i++){
			if(o.equals(array[i].getVfileupload())){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * @Description: SpecialVO判断数组中是否包含某一元素
	 * @param @param array
	 * @param @param o
	 * @param @return
	 * @return boolean
	 */
	public boolean findData(SpecialVO[] array,Object o){
		boolean flag = false;
		for(int i = 0; i < array.length; i++){
			if(o.equals(array[i].getVfileupload())){
				flag = true;
				break;
			}
		}
		return flag;
	}
	/**
	 * @Description: SizeDocVO判断数组中是否包含某一元素
	 * @param @param array
	 * @param @param o
	 * @param @return
	 * @return boolean
	 */
	public boolean findData(SizeDocVO[] array,Object o){
		boolean flag = false;
		for(int i = 0; i < array.length; i++){
			if(o.equals(array[i].getVfileupload())){
				flag = true;
				break;
			}
		}
		return flag;
	}
	/**
	 * @Description: ModelVO判断数组中是否包含某一元素
	 * @param @param array
	 * @param @param o
	 * @param @return
	 * @return boolean
	 */
	public boolean findData(ModelVO[] array,Object o){
		boolean flag = false;
		for(int i = 0; i < array.length; i++){
			if(o.equals(array[i].getVfileupload())){
				flag = true;
				break;
			}
		}
		return flag;
	}
	/**
	 * @Description: 获得表信息 
	 * @param @param clasz
	 * @param @param wh
	 * @param @return
	 * @return List
	 */
	public List getInfo(Class clasz,String wh){
		List list = iHibernateDAO.findAll(clasz, wh);
		return list;
	}
	
	/**
	 * @Description: 提取手机端面料
	 * @param @return
	 * @return String
	 */
	public void getMobFra(){
		String msg = "暂无手机端面料数据!";
		String wh = " deviceType=1";
		
		List<AuxiliaryVO> list = iHibernateDAO.findAll(AuxiliaryVO.class, wh);
		
		if(list != null){
			String srcpath = "/opt/nginx/html/3DRES/Clothes/fabricImage";
			String topath = "/opt/nginx/html/3DRES/Clothes/fabricImage_mob";
			//String srcpath = "D:\\Apache2.2\\htdocs\\Clothes\\fabricImage";
			//String topath = "D:\\Apache2.2\\htdocs\\Clothes\\fabricImage_mob";
			int size = list.size();
			String[] arrys = new String[size];
			for(int i = 0; i < size; i++){
				arrys[i] = list.get(i).getVfileupload();
			}
			try {
				FileUtils.GenerateToDirecory(arrys, srcpath, topath,"/");
				msg = "成功提取"+size+"张面料图!";
			} catch (Exception e) {
				e.printStackTrace();
				msg = "提取面料失败!";
			}
		}
		renderText(msg);
	}
	
	/**
	 * @Description: 定制信息
	 * @param @return
	 * @return String
	 */
	public String diyInfoList(){
		this.setUrl("../files/system/diyInfoList.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 定制信息
	 * @param 
	 * @return void
	 */
	public void getDiyInfoJson(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//输入查询
		String search_input = request.getParameter("searchinput");
		
		String wh = " 1=1";
		if(!CommUtil.isNull(search_input)){
			wh = " vcode ='"+search_input+"' or prodCode='"+search_input+"'";
		}
		Integer total = iHibernateDAO.getCountByHQL(DiyInfoVO.class, wh);
		List<DiyInfoVO> list = iHibernateDAO.findPage(DiyInfoVO.class, dgpage, rows, wh);
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		jsonmap.put("total", total);
		jsonmap.put("rows", list);
		renderJson(jsonmap,JsonUtils.configJson("yyyy-MM-dd hh:ss:mm"));
	}
	
	/**
	 * @Description: 查看具体定制单信息
	 * @param @return
	 * @return String
	 */
	public String findDiyInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		diyInfoVO = iHibernateDAO.findFirst(DiyInfoVO.class, " id="+id);
		this.setUrl("../files/system/diyInfoCard.jsp");
		return SUCCESS;
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

	public DiyInfoVO getDiyInfoVO() {
		return diyInfoVO;
	}

	public void setDiyInfoVO(DiyInfoVO diyInfoVO) {
		this.diyInfoVO = diyInfoVO;
	}
}
