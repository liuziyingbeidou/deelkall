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
function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['subPartVO.vcode'].value == "") {
				errMsg = "编号为空";
				setfocus = "['subPartVO.vcode']";
			}else if (theForm['subPartVO.vname'].value == "") {
				errMsg = "名称为空";
				setfocus = "['subPartVO.vname']";
			}
		if (errMsg != "") {
			alert(errMsg);
			eval("theForm" + setfocus + ".focus()");
			return true;
		}
}

function save(){
	if(check()){
		return ;
	}
	
	$.ajax({
	     type : "POST",
	     url : "parts/part!saveSubPart",
	     data:$('#partfrm').serialize(),
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
$(function(){
	var datasource = $("input[name='subPartVO.vdatasource']:checked").val();
	if(datasource == "load"){//加载
		$(".content").show();
	}else{//文本、无
		$(".content").hide();
	}
	$("input[name='subPartVO.vdatasource']").click(function(){
		var dsource = $(this).val();
		if(dsource == "load"){//加载
			$(".content").show();
		}else{//文本、无
			$(".content").hide();
		}
	});
});
//清除
function clear(){
	$(".content input:checked").removeAttr("checked");
}
//根据品类加载特殊档案（类别）
function changepro(){
	if(!confirm("切换后特殊档案将丢失选择项，是否切换?"))
		return null;
	$('.td-spe').empty();
	$.ajax({
		type:"GET",
		url:'${pageContext.request.contextPath}/parts/part!getSpeTypeByPro',
		data:{"proclassid":$('#proclassid :selected').val()},
		success:function(row){
			var data=row.rows;
			for(var i=0;i<data.length;i++){
				$('.td-spe').append("<span><input name='subPartVO.ispecial' type='radio' value='"+data[i].id+"'>"+data[i].vname+"</span>");
			}
		}
	});
}
</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
.hr hr{height:1px;border:none;border-top:1px dashed #0066CC;}
.specical{color: #FF0000;font-weight:bold;}
.btn-bottom{position:fixed; bottom:0; left:0;width:100%;height:30px;background-color:#F8F8FF;text-align:center;line-height:30px;}
-->
</style>
</head>
<body class="ContentBody">
  <form id="partfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>子部件</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>子部件</legend>
				<input name="subPartVO.id" type="hidden" value="${subPartVO.id }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">编号:</td>
					    <td width="27%"><input name='subPartVO.vcode' id="shopcode" type="text" maxlength="3" class="text" style="width:154px" value="${subPartVO.vcode }" /></td>
					    <td align="right" width="25%">&nbsp;</td>
					    <td width="37%">&nbsp;</td>
					  </tr>
					  <tr>
					    <td nowrap align="right" width="11%">名称:</td>
					    <td width="27%"><input name='subPartVO.vname' id="shopname" type="text" class="text" style="width:154px" value="${subPartVO.vname }" /></td>
					    <td align="right" width="25%">简称:</td>
					    <td width="37%"><input name='subPartVO.vsname' id="vsname" type="text" class="text" style="width:120px" value="${subPartVO.vsname }" /></td>
					  </tr>
					  <tr>
					    <td nowrap align="right" width="11%">品类:</td>
					    <td width="27%">
					    <select name="subPartVO.proclassid" class="proclassid" id="proclassid" onChange="javascript:changepro();">
							<option value="-1" selected="selected"></option>
							<c:forEach items="${proclasslist}" var="bean">
								<option <c:if test='${subPartVO.proclassid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					   </tr>
					   <tr>
					    <td align="right" width="11%">排序:</td>
					    <td width="20%">
					    <input name='subPartVO.isort' id=isort type="text" class="text" style="width:154px" value="${subPartVO.isort }" />
					    </td>
					    <td align=right width="11%">数据流向:</td>
					    <td width="37%">
					    <input name="subPartVO.deviceType" type="checkbox" <c:if test='${subPartVO.deviceType==1}'>checked</c:if> value="1">手机端
					    </td>
					 </tr>
					 <tr>
					    <td align="right" width="11%">是否工艺细节:</td>
					    <td width="20%">
					    <input name="subPartVO.bisgyxj" type="radio" <c:if test='${subPartVO.bisgyxj==1}'>checked</c:if> value="1">是
					    <input name="subPartVO.bisgyxj" type="radio" <c:if test='${subPartVO.bisgyxj==0}'>checked</c:if> value="0">否
					    </td>
					    <td align=right width="11%">是否BOM信息:</td>
					    <td width="37%">
					    <input name="subPartVO.bisbom" type="radio" <c:if test='${subPartVO.bisbom==1}'>checked</c:if> value="1">是
					    <input name="subPartVO.bisbom" type="radio" <c:if test='${subPartVO.bisbom==0}'>checked</c:if> value="0">否
					    </td>
					 </tr>
					 <tr>
					    <td width="14%" align="right" nowrap>内容:</td>
					    <td width="86%" colspan="3">
					    <input name="subPartVO.vdatasource" type="radio" <c:if test='${subPartVO.vdatasource=="load"}'>checked</c:if> value="load">加载
					    <input name="subPartVO.vdatasource" type="radio" <c:if test='${subPartVO.vdatasource=="input"}'>checked</c:if> value="input">文本
					    <input name="subPartVO.vdatasource" type="radio" <c:if test='${subPartVO.vdatasource=="nothing"}'>checked</c:if> value="nothing">无
					    </td>
					  </tr>
					  <tr class="content">
					  	<td width="100%"  colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					  <tr class="content">
					  	<td width="100%"  colspan="4" align="left" nowrap>加载数据档案:&nbsp;&nbsp;[<a href="JavaScript:clear()">全部清除</a> ]</td>
					  </tr>
					  <tr class="content">
					  	 <td width="14%" align="right" nowrap>辅料:</td>
					    <td width="86%" colspan="3">
					    <c:forEach items="${acclist}" var="bean">
							<input name="subPartVO.iaccessories" type="radio" <c:if test='${subPartVO.iaccessories==bean.id}'>checked</c:if> value="${bean.id }">${bean.vname }
						</c:forEach>
					    </td>
					  </tr>
					  <tr class="content hr">
					  	 <td width="14%" align="right" nowrap></td>
					  	<td width="86%"  colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					  <tr class="content">
					  	 <td width="14%" align="right" nowrap>特殊档案:</td>
					    <td class="td-spe" width="86%" colspan="3">
					    
					    
					    <c:forEach items="${speclist}" var="bean" varStatus="status">
							<span <c:if test='${subPartVO.ispecial==bean.id}'>class='specical'</c:if>>
							<input name="subPartVO.ispecial" type="radio" <c:if test='${subPartVO.ispecial==bean.id}'>checked</c:if> value="${bean.id }">${bean.vname }
							</span>
							<!--
							<input name="subPartVO.ispecial" type="radio" <c:if test='${subPartVO.ispecial==bean.id}'>checked</c:if> value="${bean.id }"><c:if test='${bean.proclassName!=null && bean.proclassName!=""}'>${bean.proclassName}.</c:if>${bean.vname }
							-->
						</c:forEach>
					    </td>
					  </tr>
					  <tr class="content hr">
					  	 <td width="14%" align="right" nowrap></td>
					  	<td width="86%"  colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					  <tr class="content">
					  	 <td width="14%" align="right" nowrap>面料:</td>
					    <td width="86%" colspan="3">
					    <input name="subPartVO.vfabric" type="radio" <c:if test='${subPartVO.vfabric=="all"}'>checked</c:if> value="all">全部
					    <input name="subPartVO.vfabric" type="radio" <c:if test='${subPartVO.vfabric=="c"}'>checked</c:if> value="c">撞色
					    <input name="subPartVO.vfabric" type="radio" <c:if test='${subPartVO.vfabric=="p"}'>checked</c:if> value="p">贴布
					    <input name="subPartVO.vfabric" type="radio" <c:if test='${subPartVO.vfabric=="cp"}'>checked</c:if> value="cp">撞色和贴布
					    <input name="subPartVO.vfabric" type="radio" <c:if test='${subPartVO.vfabric=="no"}'>checked</c:if> value="no">无
					    </td>
					  </tr>
					  <tr class="content hr">
					    <td width="14%" align="right" nowrap></td>
					  	<td width="86%"  colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					  <tr class="content">
					  	<td width="14%" align="right" nowrap>里料:</td>
					    <td width="86%" colspan="3">
					    <input name="subPartVO.vlining" type="radio" <c:if test='${subPartVO.vlining=="all"}'>checked</c:if> value="all">全部
					    <input name="subPartVO.vlining" type="radio" <c:if test='${subPartVO.vlining=="no"}'>checked</c:if> value="no">无
					    </td>
					  </tr>
					  <tr class="content hr">
					  	 <td width="14%" align="right" nowrap></td>
					  	<td width="86%"  colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					   <tr class="content">
					  	<td width="14%" align="right" nowrap>配饰:</td>
					    <td width="86%" colspan="3">
					    <c:forEach items="${outlist}" var="bean">
							<input name="subPartVO.ioutorn" type="radio" <c:if test='${subPartVO.ioutorn==bean.id}'>checked</c:if> value="${bean.id }">${bean.vname }
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
		
		</TABLE>
</div>
<div class="btn-bottom">
		<input type="button" onclick="javascript:save()" name="Submit" value="保存" class="button"/>　&nbsp;&nbsp;
		<input type="reset" name="Submit2" value="关闭" class="button" onclick="javascript:window.close()"/>
</div>
</form>
</body>
</html>
