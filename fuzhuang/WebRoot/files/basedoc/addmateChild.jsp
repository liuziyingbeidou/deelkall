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
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>服装管理系统</title>
<link rel="stylesheet" rev="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="all" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">

//选择大类
function fieldmate(){
	var val = $(".vmate option:selected").text();
	if(val == "==选择=="){
		$(".vmatename").val("");
	}else{
		$(".vmatename").val(val);
	}
}

function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['mateChildVO.vcode'].value == "") {
				errMsg = "编号为空";
				setfocus = "['mateChildVO.vcode']";
			}else if (theForm['mateChildVO.vname'].value == "") {
				errMsg = "名称为空";
				setfocus = "['mateChildVO.vname']";
			}
		if (errMsg != "") {
			alert(errMsg);
			eval("theForm" + setfocus + ".focus()");
			return true;
		} else
			theForm.submit();	
}

function save(){

	if(check()){
		return ;
	}
	$.ajax({
	     type : "POST",
	     url : "basedoc/method!savemateChild",
	     data:$('#mateChildfrm').serialize(),
	     async:false,
	     cache:false,
	     success : function(msg) {
	    	 alert(msg);
	    	 self.opener.location.reload();
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
-->
</style>
</head>
<body class="ContentBody">
  <form id="mateChildfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>料件子类</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>料件子类</legend>
				<input name="mateChildVO.id" type="hidden" value="${mateChildVO.id }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">料件大类:</td>
					    <td width="27%">
					    <input type="hidden" name="mateChildVO.matename" class="vmatename" value="${mateChildVO.matename }"/>
					    <select name="mateChildVO.mateid" class="vmate" onchange="javascript:fieldmate()" id="vmateid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${matelist}" var="bean">
								<option <c:if test='${mateChildVO.mateid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					  <tr>
					    <td nowrap align="right" width="11%">编号:</td>
					    <td width="27%"><input name='mateChildVO.vcode' id="shopcode" type="text" maxlength="1" class="text" style="width:154px" value="${mateChildVO.vcode }" /></td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					  <tr>
					    <td nowrap align="right" width="11%">名称:</td>
					    <td width="27%"><input name='mateChildVO.vname' id="shopname" type="text" class="text" style="width:154px" value="${mateChildVO.vname }" /></td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					 <tr>
					    <td width="14%" align="right" nowrap>备注:</td>
					    <td width="86%" colspan="3"><textarea name="mateChildVO.vmemo" cols="50" rows="5">${mateChildVO.vmemo }</textarea></td>
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
