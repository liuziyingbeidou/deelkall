<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="org.apache.commons.fileupload.util.*"%>
<%@ page import="com.alibaba.fastjson.*"%>
<%
	String contentType = request.getContentType();

	//取服务器时间+8位随机码作为部分文件名，确保文件名无重复。
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssS"); 
	String fileName = simpleDateFormat.format(new Date());
	Random random = new Random();
	String randomCode = "";
	for ( int i = 0; i < 8; i++ )
	{
		randomCode += Integer.toString(random.nextInt(36), 36);
	}
	fileName = fileName + randomCode;
	
	//信息载体
	Result result = new Result();
	String locUrl = "http://3d.deelkall.com/Clothes/";//"http://192.168.1.116/Clothes/";////图片URL
	String srcClUrl = "/opt/nginx/html/3DRES/Clothes/userClothes/";//"D:\\Apache2.2\\htdocs\\Clothes\\userClothes\\";//成品
	//默认尺码上传路径
	String srcUrl = "/opt/nginx/html/3DRES/Clothes/userSize/";//"D:\\Apache2.2\\htdocs\\Clothes\\userSize\\";//
	
	//尺码图片上传
if ( contentType.indexOf("multipart/form-data") >= 0 )
{
	DiskFileItemFactory factory = new DiskFileItemFactory();
	factory.setSizeThreshold(1024*10*10);
	ServletFileUpload upload = new ServletFileUpload(factory);
	FileItemIterator fileItems = upload.getItemIterator(request);
	//定义一个变量用以储存当前头像的序号
	int avatarNumber = 1;
	
	//基于原图的初始化参数
	String initParams = "";
	BufferedInputStream	inputStream = null;
	BufferedOutputStream outputStream = null;
	
	try{
		//遍历表单域
		while( fileItems.hasNext() )
		{
			FileItemStream fileItem = fileItems.next();
			String fieldName = fileItem.getFieldName();
			File dir = new File(srcUrl);
	        // 如果该目录不存在，就创建
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
			String virtualPath = "";
			//原始图片（默认的 file 域的名称是__source，可在插件配置参数中自定义。参数名：src_field_name）。
			String sourceFileName = fileItem.getName();	
			
			if(sourceFileName == null){
			 	continue;
			}
			
			//原始文件的扩展名(不包含“.”)
			String sourceExtendName = sourceFileName.substring(sourceFileName.lastIndexOf('.') + 1);
			result.sourceUrl = virtualPath = String.format(srcUrl+"%s.%s", fileName, sourceExtendName);
			result.newName = fileName + "." + sourceExtendName;
			result.upUrl =  locUrl + "userSize/";//返回URL   + fileName + "." + sourceExtendName
			inputStream = new BufferedInputStream(fileItem.openStream());
			outputStream = new BufferedOutputStream(new FileOutputStream(virtualPath));//virtualPath.replace("/", "\\"))
			Streams.copy(inputStream, outputStream, true);
	        break;
		}
		result.success = true;
		result.msg = "Success!";
		if ( result.sourceUrl != null )
		{
			result.sourceUrl += initParams;
		}
	}catch(IOException e){
		result.success = false;
		result.msg = "Failure!";
		e.printStackTrace();
	}finally{
		try{
			inputStream.close();
	   	 	outputStream.flush();
	    	outputStream.close();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			//返回图片的保存结果（返回内容为json字符串，可自行构造，该处使用fastjson构造）
			//out.write(JSON.toJSONString(result));
			response.setContentType("text/json;charset=UTF-8");
			response.getWriter().write(JSON.toJSONString(result));
			response.getWriter().close();
		}
	}
//DIY 定制截图上传
}else if(contentType.indexOf("application/octet-stream") >= 0){
	
	//正-1,反-2,侧-3
	String loc = request.getParameter("loc");
	
	if(loc == null || "".equals(loc)){
		loc = fileName + ".jpg ";
	}
	
	ServletInputStream in = null;
	BufferedInputStream fileIn = null;
	FileOutputStream ot = null;
	try{
		int   n,total=0; 
		File dir = new File(srcClUrl);
        // 如果该目录不存在，就创建
        if (!dir.exists()) {
            dir.mkdirs();
        }
		String path = srcClUrl + loc;	//设置上传图片的路径 
		result.sourceUrl = srcClUrl;
		result.newName = loc;
		result.upUrl =  locUrl + "userClothes/";//返回URL  + loc
		in = request.getInputStream();	//获得文件输入流 
		fileIn = new BufferedInputStream(in); 
		File file = new File(path); 
		ot = new FileOutputStream(file); 
		byte[] b=new byte[10240];
		while((n=fileIn.read(b))> 0){ 
			total   =   total   +   n;	//文件大小 
			ot.write(b,0,n); 
		}
		result.success = true;
		result.msg = "Success!";
	} catch (IOException e) {
		result.success = false;
		result.msg = "Failure!";
		e.printStackTrace();
	}finally{
		try{
			ot.close(); 
			fileIn.close(); 
			in.close();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			//out.write(JSON.toJSONString(result));
			response.setContentType("text/json;charset=UTF-8");
			response.getWriter().write(JSON.toJSONString(result));
			response.getWriter().close();
		}
	}
}
%>
<%!
/**
* 表示上传的结果。
*/
private class Result
{
	/**
	* 表示图片是否已上传成功。
	*/
	public Boolean success;
	public String userid;
	public String username;
	/**
	* 自定义的附加消息。
	*/
	public String msg;
	/**
	* 表示原始图片的保存地址。
	*/
	public String sourceUrl;
	/**
	* 表示所有头像图片的保存地址，该变量为一个数组。
	*/
	public ArrayList avatarUrls;
	public String newName;
	public String upUrl;
}
%>