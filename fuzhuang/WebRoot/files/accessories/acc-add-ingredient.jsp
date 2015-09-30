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
<meta http-equiv="Content-Type" content="text/html;" />
<title>服装管理系统</title>
<link rel="stylesheet" rev="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="all" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
function fieldvariety(){
	var val = $(".varietyid option:selected").text();
	if(val == "==选择=="){
		$(".varietyName").val("");
	}else{
		$(".varietyName").val(val);
	}
}

function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['baseDocSoVO.vcode'].value == "") {
				errMsg = "编号为空";
				setfocus = "['baseDocSoVO.vcode']";
			}else if (theForm['baseDocSoVO.vname'].value == "") {
				errMsg = "名称为空";
				setfocus = "['baseDocSoVO.vname']";
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
	     url : "accessories/basedoc!saveAccIngredient",
	     data:$('#ingredientfrm').serialize(),
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
  <form id="ingredientfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>成份</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>成份</legend>
				<input name="baseDocSoVO.id" type="hidden" value="${baseDocSoVO.id }">
				<input name="baseDocSoVO.vmoduletype" type="hidden" value="${baseDocSoVO.vmoduletype }">
				<input name="baseDocSoVO.vdoctype" type="hidden" value="${baseDocSoVO.vdoctype }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">品种:</td>
					    <td width="27%">
					    <input type="hidden" name="baseDocSoVO.varietyName" class="varietyName" value="${baseDocSoVO.varietyName }"/>
					    <select name="baseDocSoVO.varietyid" class="varietyid" onchange="javascript:fieldvariety()" id="varietyid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${docVarietyList}" var="bean">
								<option <c:if test='${baseDocSoVO.varietyid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					  <tr>
					    <td nowrap align="right" width="11%">编号:</td>
					    <td width="27%"><input name='baseDocSoVO.vcode' id="shopcode" type="text" maxlength="3" class="text" style="width:154px" value="${baseDocSoVO.vcode }" /></td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					  <tr>
					    <td nowrap align="right" width="11%">名称:</td>
					    <td width="27%"><input name='baseDocSoVO.vname' id="shopname" type="text" class="text" style="width:154px" value="${baseDocSoVO.vname }" /></td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					 <tr>
					    <td width="14%" align="right" nowrap>备注:</td>
					    <td width="86%" colspan="3"><textarea name="baseDocSoVO.vmemo" cols="50" rows="5">${baseDocSoVO.vmemo }</textarea></td>
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
