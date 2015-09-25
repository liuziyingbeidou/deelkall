<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>项目管理系统</title>
<link rel="stylesheet" rev="stylesheet" href="../css/style.css" type="text/css" media="all" />
<script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
function check1()
{
	    var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		var password = $("#password").val();
		var surepassword = $("#surepassword").val();
		
		if (password != surepassword){
			errMsg = "确认密码和密码不一致，请再次输入";
			$("#surepassword").val = "";
			setfocus = "['surepassword']";
		}
		if (errMsg != "") {
			alert(errMsg);
			eval("theForm" + setfocus + ".focus()");
		}
}

function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		var password = $("#password").val();
		var surepassword = $("#surepassword").val();
		if (password == "") {
				errMsg = "密码为空";
				setfocus = "['password']";
			}else if (theForm['surepassword'].value == "") {
				errMsg = "确认密码为空";
				setfocus = "['surepassword']";
			}else if (theForm['level'].value == "") {
				errMsg = "属性为空";
				setfocus = "['level']";
			}else
		if (password != surepassword) {
			errMsg = "确认密码和密码不一致，请再次输入";
			setfocus = "['surepassword']";
		}else{
		}
		if (errMsg != "") {
			alert(errMsg);
			eval("theForm" + setfocus + ".focus()");
		} else
			theForm.submit();	
}

</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
-->
</style>
</head>
<body class="ContentBody">
  <form onsubmit="check();return false;" action="method!changepwd2" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" >用户注册</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>用户注册</legend>
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">用户名:</td>
					    <td style="display: none;" width="27%"><input name="usercode" id="usercode" type="text" class="text" style="width:154px" value="${usersigle.usercode}" /></td>
					    <td width="27%"><label id="usercode" class="text" style="width:154px">${usersigle.usercode}</label></td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					  </tr>
					  <tr>
					    <td nowrap align="right" width="11%">密码:</td>
					    <td width="27%"><input name='password' id="password" type="password" class="text" style="width:154px" value="${usersigle.password}" /></td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					  </tr>
					  <tr>
					    <td nowrap align="right" width="11%">确认密码:</td>
					    <td width="27%"><input name='surepassword' id="surepassword" onchange="check1();" type="password" class="text" style="width:154px" value="" /></td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					  <tr>
					    <td nowrap align="right" width="11%">真实姓名:</td>
					    <td width="27%"><input name='username' id="username" type="text" class="text" style="width:154px" value="${usersigle.username}" /></td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					  <tr>
					    <td width="11%" align="right" nowrap>属性:</td>
					    <td colspan="3">
					    <select name="level" id="level">
							<option value="1" selected='<c:if test='${usersigle.level==1}'>selected</c:if>'>超级管理员</option>
						</select></td>
					    </tr>
					  <tr>
					    <td  width="11%" nowrap align="right">控制:</td>
					    <td colspan="3"><input type="checkbox" name="userlock" id="userlock" value="1" <c:if test='${usersigle.userlock==1}'>checked</c:if> />是否锁</td>
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
			<input type="submit"" name="Submit" value="保存" class="button"/>　
			<input type="reset" name="Submit2" value="返回" class="button" onclick="window.history.go(-1);"/></TD>
		</TR>
		</TABLE>
</div>
</form>
</body>
</html>
