<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
if((model.User)session.getAttribute("user")==null){
	response.sendRedirect("index.jsp");
	return;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>服装管理系统</title>
</head>

<frameset rows="0,*,30" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="files/top.html" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="213,*" frameborder="no" border="0" framespacing="0" name="mid">
    <frame src="files/left.jsp" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" title="leftFrame" />
    <frame src="files/mainfra.html" name="mainFrame" id="mainFrame" title="mainFrame" />
  </frameset>
  <frame src="files/foot.jsp" name="footFrame" scrolling="no" noresize="noresize" id="footFrame" title="footFrame" border="0" marginwidth="0" marginheight="0" />
</frameset>
<noframes><body>
</body>
</noframes></html>

