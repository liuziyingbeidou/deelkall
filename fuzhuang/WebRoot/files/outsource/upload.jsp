<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>文件上传</title>

        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <script language="JavaScript" type="text/javascript">
function check()
{
		var theForm = document.fom;
		var errMsg = "";
		var setfocus = "";
		if (theForm['image'].value == "") {
				errMsg = "请选择文件...";
				setfocus = "['image']";
		}
		if (errMsg != "") {
			alert(errMsg);
			eval("theForm" + setfocus + ".focus()");
		} else
			theForm.submit();	
}
</script>
    </head>

    <body>
        <form onsubmit="check();return false;" action="${pageContext.request.contextPath}/uploadoutsource.action" 
              enctype="multipart/form-data" method="post" name="fom">
            文件:<input type="file" name="image">
                <input type="submit"  value="上传" />
        </form>
        <br/>
        <s:fielderror />
    </body>
</html>