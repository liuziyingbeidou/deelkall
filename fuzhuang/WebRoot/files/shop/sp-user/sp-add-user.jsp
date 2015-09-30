<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String type = request.getParameter("type");
if("edit".equals(type)){
	type = "修改";
}else{
	type = "新增";
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; " />
<title>服装管理系统</title>
<link rel="stylesheet" rev="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="all" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">

function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['spUserVO.vusername'].value == "") {
				errMsg = "用户名为空";
				setfocus = "['spUserVO.vusername']";
			}else if (theForm['spUserVO.vpwd'].value == "") {
				errMsg = "用户密码为空";
				setfocus = "['spUserVO.vpwd']";
			}
		if (errMsg != "") {
			alert(errMsg);
			eval("theForm" + setfocus + ".focus()");
			return true;
		}
}

//保存
function save(){
	if(check()){
		return ;
	}
	$.ajax({
	     type : "POST",
	     url : "shop/sper!saveSpUser",
	     data:$('#moldfrm').serialize(),
	     async:false,
	     cache:false,
	     success : function(msg) {
	    	 alert(msg);
	    	 self.opener.reloadGD();
	     },
	     error : function(e) {
	    	
	     	alert("保存失败!");
	     }
	    });
}

</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
select{width:152px;}
.vmaterials{color:gray;}
-->
</style>
</head>
<body class="ContentBody" >
  <form id="moldfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>店员</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">

		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>店员</legend>
				<input name="spUserVO.id" type="hidden" value="${spUserVO.id }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">用户名:</td>
					    <td width="27%"><input name='spUserVO.vusername' id="vusername" maxlength="18" type="text" class="text" style="width:154px" value="${spUserVO.vusername }" /></td>
					  </tr>
					  <tr>
					    <td nowrap align="right" width="11%">真实姓名:</td>
					    <td width="27%"><input name='spUserVO.vtruename' id="vtruename" type="text" class="text" style="width:154px" value="${spUserVO.vtruename }" /></td>
					    </tr>
					    <tr>
					    <td nowrap align="right" width="11%">是否锁:</td>
					    <td width="27%">
					    <input name="spUserVO.islock" type="radio" <c:if test='${spUserVO.islock==1}'>checked</c:if> value="1">是
					    <input name="spUserVO.islock" type="radio" <c:if test='${spUserVO.islock==0}'>checked</c:if> value="0">否
					    </td>
					    </tr>
					    <tr>
					    <td nowrap align="right" width="11%">用户密码:</td>
					    <td width="27%"><input name='spUserVO.vpwd' id="vpwd" type="text" class="text" style="width:154px" value="${spUserVO.vpwd }" /></td>
					    </tr>
					  </table>
			  <br />
				</fieldset></TD>
		</TR>
		</TABLE>
	 </td>
  </tr>
		<TR>
			<TD colspan="2" align="center" height="50px">
			<input type="button" onclick="javascript:save()" name="Submit" value="保存" class="button"/>　
			<input type="reset" name="Submit2" value="关闭" class="button" onclick="javascript:window.close()"/></TD>
		</TR>
		</TABLE>
</div>
</form>
</body>
</html>
