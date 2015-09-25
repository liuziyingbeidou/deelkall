package action;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import model.materials.MateChildVO;
import model.materials.MaterialsVO;

import org.apache.struts2.ServletActionContext;

import util.JsonTreeHelper;
import util.Pager;
import util.Util;
import dao.IHibernateDAO;
import dao.MateChildDao;
import dao.MaterialsDao;
import dao.UserDao;

public class ManageAction extends BaseAction{
	
	
	private static final long serialVersionUID = -4304509122548259589L;
	
	private UserDao userDao;
	
	private String url = "./";
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	//程序入口界面
	public String index(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Util.init(request);
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user==null){
			return "success2";
		}else{
			return "success1";
		}
	}
	//用户登录操作
	public String login() throws IOException {
		System.out.println("---------------------------");
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = request.getParameter("textfield");
		String password = request.getParameter("textfield2");
//		String role = request.getParameter("textfield4");
		User user = userDao.selectBean(" WHERE usercode = '"+username +"' and password= '"+password +"' and userlock=1 ");
		if (user!=null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			this.setUrl("index");
			return "redirect";
		} else {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response.getWriter().print("<script language=javascript>alert('用户名或者密码错误');window.location.href='index.jsp';</script>");
		}
		return null;
	}
	
	//用户退出操作
	public String loginout() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		this.setUrl("index.jsp");
		return SUCCESS;
	}
	
	//用户注册
	public void register() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String usercode = request.getParameter("usercode");
		String password = request.getParameter("password");
		String username = request.getParameter("username");
		String level = request.getParameter("level");
		String userlock = request.getParameter("userlock");
		String shop = request.getParameter("shop");
		User user = userDao.selectBean(" where usercode ='"+usercode+"'");
		if(user == null){
			user = new User();
			user.setUsercode(usercode);
			user.setPassword(password);
			user.setUsername(username);
			user.setPk_shop(shop);
			if(level!=null){
				user.setLevel(Integer.valueOf(level));
			}
			if(userlock!=null){
				user.setUserlock(Integer.valueOf(userlock));
			}else{
				user.setUserlock(0);
			}
			userDao.insertBean(user);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response.getWriter().print("<script language=javascript>alert('注册成功');window.location.href='method!registerpage';</script>");
		}else{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response.getWriter().print("<script language=javascript>alert('该用户已被注册,请重新注册!');window.location.href='method!registerpage';</script>");
		}
	}
	//跳转到添加 用户页面
	public String registerpage() {
		this.setUrl("../files/register.jsp");
		return SUCCESS;
	}
	//用户列表
	public String userlist()  {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		StringBuffer sb2 = new StringBuffer();
		sb2.append(" where ");
		
		sb.append(" 0=0");
		String where = sb.toString();
		sb2.append(" 0=0 ");
		String where2 = sb2.toString();
		
		int currentpage = 1;
		int pagesize =dgpage;
		if(request.getParameter("pagenum")!=null){
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = userDao.selectBeanCount(where2);
		request.setAttribute("list", userDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!userlist", "共有" + total + "条记录"));
		request.setAttribute("url", "method!userlist");
		this.setUrl("../files/listUsers.jsp");
		return SUCCESS;
	}
	
	//跳转到修改密码页面
	public String changepwd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pk_user = request.getParameter("id").toString();
		request.setAttribute("usersigle",userDao.selectBean(" where pk_user ='"+pk_user+"'"));
		this.setUrl("../files/password.jsp");
		return SUCCESS;
	}
	
	//修改密码操作
	public void changepwd2() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String usercode = request.getParameter("usercode");
		String password = request.getParameter("password");
		String username = request.getParameter("username");
		String level = request.getParameter("level");
		String userlock = request.getParameter("userlock");
		String shop = request.getParameter("shop");
		User user = userDao.selectBean(" where usercode ='"+usercode+"'");
		if(user!=null){
//			user.setUsercode(usercode);
			user.setPassword(password);
			user.setUsername(username);
			user.setPk_shop(shop);
			if(level!=null){
				user.setLevel(Integer.valueOf(level));
			}
			if(userlock!=null){
				user.setUserlock(Integer.valueOf(userlock));
			}else{
				user.setUserlock(0);
			}
			userDao.updateBean(user);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response.getWriter().print("<script language=javascript>alert('修改成功');window.location.href='method!userlist';</script>");
		}else{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
			response.getWriter().print("<script language=javascript>alert('修改失败!');window.history.go(-1);</script>");
		}
	}
	//删除用户
	public void userdelete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pk_user = request.getParameter("id").toString();
		User user = userDao.selectBean(" where pk_user= '"+pk_user+"'");
		userDao.deleteBean(user);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("gbk");response.setContentType("text/html; charset=gbk");
		response.getWriter().print("<script language=javascript>alert('操作成功');window.location.href='method!userlist';</script>");
	}
	
	/**=====================以下服装系统==================**/
	
	private MaterialsVO materialsVO;
	private MateChildVO mateChildVO;
	private MaterialsDao materialsDao;
	private MateChildDao mateChildDao;
	
	/********************************料件大类-start***************************/
	/**
	 * @Description: 料件大类档案列表
	 * @param @return
	 * @return String
	 */
	public String listmaterials()  {
		
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
		int total = materialsDao.selectBeanCount(where2);
		request.setAttribute("list", materialsDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!listmaterials", "共有" + total + "条记录"));
		request.setAttribute("url", "method!listmaterials");
		
		this.setUrl("../files/basedoc/listmaterials.jsp");
		return SUCCESS;
	}
	
	/**
	 * @Description: 新增料件大类
	 * @param @return
	 * @return String
	 */
	public String addmaterials(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			materialsVO = materialsDao.selectBean(" where id ="+id);
		}
		
		this.setUrl("../files/basedoc/addmaterials.jsp");
		return SUCCESS;
	}
	
	//跳转到修改料件大类页面
	public String changematerials() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		materialsVO = materialsDao.selectBean(" where id ="+id);
		this.setUrl("basedoc/method!addmaterials?type=edit");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 保存料件大类
	* @param 
	* @return void
	 */
	public void savematerials(){
		
		try {
			Integer id = materialsVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "保存失败";
			if(id != null){
				materialsDao.updateBean(materialsVO);
				msg = "更新成功";
			}else{
				materialsDao.insertBean(materialsVO);
				msg = "新增成功";
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//删除料件大类
	public void delmaterials() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//删除引用校验-start
		MateChildVO mateChildVO = mateChildDao.selectBean(" where mateid= "+id);
		if(mateChildVO == null){
			MaterialsVO materialsVO = materialsDao.selectBean(" where id= "+id);
			materialsDao.deleteBean(materialsVO);
			out.print("删除成功");
		}else{
			out.print("已被引用，不可删除!");
		}
		//删除引用校验-end
	}
	/********************************料件大类-end***************************/
	
	/********************************料件子类-start***************************/
	/**
	 * @Description: 料件子类档案列表
	 * @param @return
	 * @return String
	 */
	public String listmateChild()  {
		
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
		int total = mateChildDao.selectBeanCount(where2);
		request.setAttribute("list", mateChildDao.selectBeanList((currentpage - 1) * pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!listmateChild", "共有" + total + "条记录"));
		request.setAttribute("url", "method!listmateChild");
		
		this.setUrl("../files/basedoc/listmateChild.jsp");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 返回料件大类jsonstring
	* @param @return
	* @return String
	 */
	@SuppressWarnings("unchecked")
	public String getJsonString(){
		
		List mateChildList = mateChildDao.selectBeanList(" where 1=1 and ifnull(dr,0)=0");
		String[] members = new String[]{"id","vname","mateid"};
		String jsonstr = null;
		if(mateChildList != null){
			try {
				jsonstr = JsonTreeHelper.getTreeJsonStrNormal(members, mateChildList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonstr;
	}
	
	/**
	 * @Description: 新增料件子类
	 * @param @return
	 * @return String
	 */
	public String addmateChild(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(id != null){
			mateChildVO = mateChildDao.selectBean(" where ifnull(dr,0)=0 and id ="+id);
		}
		request.setAttribute("matelist",materialsDao.selectBeanList(" where 1=1 and ifnull(dr,0)=0"));
		this.setUrl("../files/basedoc/addmateChild.jsp");
		return SUCCESS;
	}
	
	//跳转到修改料件子类页面
	public String changemateChild() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		mateChildVO = mateChildDao.selectBean(" where id ="+id);
		this.setUrl("basedoc/method!addmateChild?type=edit");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Description: 保存料件子类
	* @param 
	* @return void
	 */
	public void savemateChild(){
		
		try {
			Integer id = mateChildVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "保存失败";
			if(id != null){
				mateChildDao.updateBean(mateChildVO);
				msg = "更新成功";
			}else{
				mateChildDao.insertBean(mateChildVO);
				msg = "新增成功";
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//删除料件子类
	public void delmateChild() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id").toString();
		
		MateChildVO mateChildVO = mateChildDao.selectBean(" where id= "+id);
		mateChildDao.deleteBean(mateChildVO);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("删除成功");
	}
	
	/********************************料件子类-end***************************/

	IHibernateDAO iHibernateDAO;
	
	

	public MaterialsDao getMaterialsDao() {
		return materialsDao;
	}

	public void setMaterialsDao(MaterialsDao materialsDao) {
		this.materialsDao = materialsDao;
	}

	public MateChildDao getMateChildDao() {
		return mateChildDao;
	}

	public void setMateChildDao(MateChildDao mateChildDao) {
		this.mateChildDao = mateChildDao;
	}

	public MaterialsVO getMaterialsVO() {
		return materialsVO;
	}

	public void setMaterialsVO(MaterialsVO materialsVO) {
		this.materialsVO = materialsVO;
	}

	public MateChildVO getMateChildVO() {
		return mateChildVO;
	}

	public void setMateChildVO(MateChildVO mateChildVO) {
		this.mateChildVO = mateChildVO;
	}

	public IHibernateDAO getiHibernateDAO() {
		return iHibernateDAO;
	}

	public void setiHibernateDAO(IHibernateDAO iHibernateDAO) {
		this.iHibernateDAO = iHibernateDAO;
	}

	public static void main(String[] args){
		//System.out.println(colourDao.getColorList());
	}
	
}
