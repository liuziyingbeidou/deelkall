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
$(function(){
	
	//以下是将选择的品类和子部件，checked
	//关联的品类串
	var proclassids = "${baseDocVO.proclassids}";
	var arry_proids = {};
	arry_proids = proclassids.split(",");
	//品类
	for(var i = 0; i < arry_proids.length; i++){
		$(".link-proclass input[type='checkbox']").each(function(){
			if($(this).val() == arry_proids[i]){
				this.checked = true;
			}
		});
	}
});
function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['baseDocVO.vcode'].value == "") {
				errMsg = "编号为空";
				setfocus = "['baseDocVO.vcode']";
			}else if (theForm['baseDocVO.vname'].value == "") {
				errMsg = "名称为空";
				setfocus = "['baseDocVO.vname']";
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
	//关联品类
	var proclassids = "";
	//将所关联的转换成字符串
	$(".link-proclass input[type='checkbox']").each(function(){
		if($(this).is(':checked')){
			if(proclassids == ""){
				proclassids += $(this).val();
			}else{
				proclassids += ","+$(this).val();
			}
		}
	});
	$("input[name='baseDocVO.proclassids']").val(proclassids);
	$.ajax({
	     type : "POST",
	     url : "basedoc/basedoc!saveProcess",
	     data:$('#processfrm').serialize(),
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
//清除所选
function clear(em){
	$("."+em+" input:checked").removeAttr("checked");
}
//全选
function selectAll(em){
	$("."+em+" input[type='checkbox']").each(function(){
		this.checked = true;
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
  <form id="processfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>工艺</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>工艺</legend>
				<input name="baseDocVO.id" type="hidden" value="${baseDocVO.id }">
				<input name="baseDocVO.vdoctype" type="hidden" value="${baseDocVO.vdoctype }">
				<input name="baseDocVO.proclassids" type="hidden" value="${baseDocVO.proclassids }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">编号:</td>
					    <td width="27%"><input name='baseDocVO.vcode' id="shopcode" type="text" maxlength="3" class="text" style="width:154px" value="${baseDocVO.vcode }" /></td>
					    <td nowrap align="right" width="11%">名称:</td>
					    <td width="27%"><input name='baseDocVO.vname' id="shopname" type="text" class="text" style="width:154px" value="${baseDocVO.vname }" /></td>
					    </tr>
					 <tr style="display: none;">
					    <td width="14%" align="right" nowrap>备注:</td>
					    <td width="86%" colspan="3"><textarea name="baseDocVO.vmemo" cols="53" rows="5">${baseDocVO.vmemo }</textarea></td>
					  </tr>
					  <tr>
					  	<td width="100%"  colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					  <tr class="content">
					  	<td width="100%"  colspan="4" align="left" nowrap>关联品类:&nbsp;&nbsp;[<a href="JavaScript:selectAll('link-proclass')">全选</a> ]&nbsp;&nbsp;[<a href="JavaScript:clear('link-proclass')">全清</a> ]</td>
					  </tr>
					   <tr class="content link-proclass">
					  	 <td width="14%" align="right" nowrap>品类：</td>
					    <td width="86%" colspan="3">
					    <c:forEach items="${proclasslist}" var="bean">
							<input name="proclassid" type="checkbox" value="${bean.id }">${bean.vname }
						</c:forEach>
					    </td>
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
