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

//选择大类
function fieldclass(){
	var val = $(".classid option:selected").text();
	if(val == "==选择=="){
		$(".subclass").val("");
	}else{
		$(".subclass").val(val);
	}
}

function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['temSubclassVO.vcode'].value == "") {
				errMsg = "编号为空";
				setfocus = "['temSubclassVO.vcode']";
			}else if (theForm['temSubclassVO.vname'].value == "") {
				errMsg = "名称为空";
				setfocus = "['temSubclassVO.vname']";
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
	     url : "temtype/temtype!saveTemSubClass",
	     data:$('#temtypefrm').serialize(),
	     async:false,
	     cache:false,
	     success : function(msg) {
	    	 alert(msg+"");
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
  <form id="temtypefrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>大品类</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>大品类</legend>
				<input name="temSubclassVO.id" type="hidden" value="${temSubclassVO.id }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">总分类:</td>
					    <td width="27%">
					    <input type="hidden" name="temSubclassVO.className" class="subclass" value="${temSubclassVO.className }"/>
					    <select name="temSubclassVO.classid" class="classid" onchange="javascript:fieldclass()" id="classid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${typelist}" var="bean">
								<option <c:if test='${temSubclassVO.classid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					  <tr>
					    <td nowrap align="right" width="11%">编号:</td>
					    <td width="27%"><input name='temSubclassVO.vcode' id="shopcode" type="text" maxlength="3" class="text" style="width:154px" value="${temSubclassVO.vcode }" /></td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					  <tr>
					    <td nowrap align="right" width="11%">名称:</td>
					    <td width="27%"><input name='temSubclassVO.vname' id="shopname" type="text" class="text" style="width:154px" value="${temSubclassVO.vname }" /></td>
					    <td align="right" width="25%">简称:</td>
					    <td width="37%"><input name='temSubclassVO.vsname' id="vsname" type="text" class="text" style="width:120px" value="${temSubclassVO.vsname }" /></td>
					    </tr>
					    <tr>
					    <td nowrap align="right" width="11%">英文名称:</td>
					    <td width="27%"><input name='temSubclassVO.vname_eg' id="shopname" type="text" class="text" style="width:154px" value="${temSubclassVO.vname_eg }" /></td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					    </tr>
					  <tr>
					    <td nowrap align="right" width="11%">排序</td>
					    <td width="27%">
					    <input name='temSubclassVO.isort' id=isort type="text" class="text" style="width:154px" value="${temSubclassVO.isort }" />
					    </td>
					    <td align="right" width="25%">数据流向:</td>
					    <td width="37%">
					    <input name="temSubclassVO.deviceType" type="checkbox" <c:if test='${temSubclassVO.deviceType==1}'>checked</c:if> value="1">手机端
					    </td>
					 </tr>
					 <tr>
					    <td width="14%" align="right" nowrap>备注:</td>
					    <td width="86%" colspan="3"><textarea name="temSubclassVO.vmemo" cols="50" rows="5">${temSubclassVO.vmemo }</textarea></td>
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
