package service.basedoc;

import itf.pub.IConstant;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import model.basedoc.BaseDocVO;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import service.impl.CommonService;
import dao.IMyHibernateDao;

public class BaseDocService extends CommonService {

	@Autowired @Qualifier("myHibernateDao")
	IMyHibernateDao mydao;
	
	public void save(BaseDocVO baseDocVO,String doctype){
		try {
			Integer id = baseDocVO.getId();
			HttpServletResponse response = ServletActionContext.getResponse();
			String msg = "保存失败";
			if(id != null){
				mydao.updateBean(baseDocVO);
				msg = "更新成功";
			}else{
				//标识是品牌档案
				baseDocVO.setVdoctype(doctype);
				mydao.insertBean(baseDocVO);
				msg = "新增成功";
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
