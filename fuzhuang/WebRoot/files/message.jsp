<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>上传成功</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
    <script language="javascript">
function uppic(){
	$("#filepath", opener.document).val($("#fileurl").val());
   // window.opener.document.getElementById('path').value=$("#fileurl").val();
    window.close();
}

window.onbeforeunload=onclose;
function onclose()
{ 
 	closewindow();
}
function closewindow(){
	$("#filepath", opener.document).val($("#fileurl").val());
	//window.opener.document.getElementById('path').value=document.getElementById('fileurl').value;
    //关闭窗口时,把上传后的图片名称放到父窗口的图片路径中
}
</script>
  </head>
  
  <body>
    <p align="center">上传成功！ &nbsp;<input name="close" type="button" value="关 闭" onclick="uppic()" /></p>
    <!-- ${pageContext.request.contextPath} tomcat部署路径，
          如：D:\apache-tomcat-6.0.18\webapps\struts2_upload\ ${pageContext.request.contextPath} -->
          <input type="hidden" id="fileurl" value="<s:property value="imageFileName"/>">
    <img id="filepath" src="<s:property value="showURL+'/'+imageFileName"/>">
  </body>
</html>