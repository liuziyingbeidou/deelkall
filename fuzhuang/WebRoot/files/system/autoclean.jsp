<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>服装管理系统</title>
<link rel="stylesheet" rev="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="all" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">

//自助清理
function autoclean(){
	var type = $(".clean").val();
	$.ajax({
	     type : "POST",
	     url : "system/sys!autoClean",
	     data:"type="+type,
	     async:false,
	     cache:false,
	     success : function(msg) {
	     	//debugger;
	    	 alert(msg);
	     },
	     error : function(e) {
	     	alert("清理失败!");
	     }
	    });
}

//提取手机端面料
function getMobFra(){
	$.ajax({
	     type : "POST",
	     url : "system/sys!getMobFra",
	     data:"",
	     async:false,
	     cache:false,
	     success : function(msg) {
	    	 alert(msg);
	     },
	     error : function(e) {
	     	alert("提取失败!");
	     }
	    });
}

</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
.warn{color:red;}
-->
</style>
</head>
<body class="ContentBody">
  <form id="colourfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" >自助清理</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>自助清理</legend>
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">清理模块:</td>
					    <td width="27%">
					    <select name="model" class="clean" id="clean">
							<option value="1">面料</option>
							<option value="2">里料</option>
							<option value="3">辅料</option>
							<option value="4">包装材料</option>
							<option value="5">配饰</option>
							<option value="6">特殊档案</option>
							<option value="7">体型特征</option>
							<option value="8">模型</option>
						</select>
						<input type="button" onclick="javascript:autoclean()" name="Submit" value="自助清理" class="button"/>
						<span class="warn">*谨慎使用</span>
					    </td>
					    <td align="right" width="25%">
					    </td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					  </table>
			  <br />
				</fieldset></TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>提取手机端素材</legend>
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">提取面料:</td>
					    <td width="27%">
						<input type="button" onclick="javascript:getMobFra()" name="Submit" value="提取" class="button"/>
					    </td>
					    <td align="right" width="25%">
					    </td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					  </table>
			  <br />
				</fieldset></TD>
		</TR>
		</TABLE>
	 </td>
  </tr>
</TABLE>
</div>
</form>
</body>
</html>
