package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import model.SelInfoVO;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class Util {

	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return sdf.format(date.getTime());
	}
	
	public static String getRiqi() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return sdf.format(date.getTime());
	}
	
	public static String getYuefen() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		return sdf.format(date.getTime());
	}

	// 上传文件/复制文件。
	public static void copyFile(File src, File dst) {
		try {
			int BUFFER_SIZE = 16 * 1024;
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				for (int byteRead = 0; (byteRead = in.read(buffer)) > 0;) {
					out.write(buffer, 0, byteRead);
				}

			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param type 料型
	 * @param infovo 所选择的信息
	 * @return 根据以上选择的信息进行自动生成编码
	 */
	public static String getCodeBy(String type,SelInfoVO infovo){
		
		StringBuffer code = new StringBuffer();
		//料件大类
		String mate = infovo.getMate();
		if(mate != null && !"".equals(mate)){
			code.append(mate);
		}else{
			code.append(0);
		}
		//料件子类
		String mateChild = infovo.getMateChild();
		if(mateChild != null && !"".equals(mateChild)){
			code.append(mateChild);
		}else{
			code.append(0);
		}
		//成分
		String ingre = infovo.getIngre();
		if(ingre != null && !"".equals(ingre)){
			code.append(ingre);
		}else{
			code.append(0);
		}
		//花型
		String pattern = infovo.getPattern();
		if(pattern != null && !"".equals(pattern)){
			code.append(pattern);
		}else{
			code.append(0);
		}
		//色系
		String colour = infovo.getColour();
		if(colour != null && !"".equals(colour)){
			code.append(colour);
		}else{
			code.append(0);
		}
		//固定预留号
		code.append("0");
		return code != null ? code.toString() : null;
	}

	public static void createZip(String src, String nilename, String path)
			throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(bos);
		zipOut.setEncoding("gbk");

		File file = new File(path, src);
		byte[] buffer = new byte[4096];
		int bytes_read;
		InputStream fis = new FileInputStream(file);
		zipOut.putNextEntry(new ZipEntry(src));
		while ((bytes_read = fis.read(buffer)) != -1) {
			zipOut.write(buffer, 0, bytes_read);
		}
		zipOut.closeEntry();
		fis.close();

		zipOut.close();
		FileOutputStream fout = new FileOutputStream(new File(path, nilename));
		bos.writeTo(fout);
		fout.flush();
		fout.close();
	}
	
	//初始化系统
	public static void init(HttpServletRequest request){
//		  WebApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
//          UserDao userDao = (UserDao)app.getBean("userDao");
//          User user = userDao.selectBean(" where usercode='admin' and level=0  ");
//  		if(user==null){
//  			user = new User();
//  			user.setPassword("111111");
//  			user.setLevel(0);
//  			user.setUsercode("admin");
//  			user.setUsername("admin");
//  			userDao.insertBean(user);
//  		}
	}

}
