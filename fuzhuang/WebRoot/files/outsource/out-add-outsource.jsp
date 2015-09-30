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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/blbasejs.js"></script>
<script type="text/javascript">

function openScript(url, width, height)
{
var left = (screen.width-width)/2;
var top = (screen.height-height)/8;

var Win = window.open(url,"openScript",'width=' + width + ',height=' + height + ',top='+top+', left='+left+',resizable=1,scrollbars=yes,menubar=no,status=yes' );
}

function openem()
{ 
openScript('./outsource/upload.jsp',390,270); 
}
function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['auxiliaryVO.vcode'].value == "") {
				errMsg = "编号为空";
				setfocus = "['auxiliaryVO.vcode']";
			}else if (theForm['auxiliaryVO.vname'].value == "") {
				errMsg = "名称为空";
				setfocus = "['auxiliaryVO.vname']";
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
	$("#vmaterialsid").removeAttr("disabled");
	$("#vmateChildid").removeAttr("disabled");
	$(".os-ig").removeAttr("disabled");
	$(".os-cl").removeAttr("disabled");
	
	$.ajax({
	     type : "POST",
	     url : "outsource/outsource!saveOutsource",
	     data:$('#liningfrm').serialize(),
	     async:false,
	     cache:false,
	     success : function(msg) {
	     	$("#vmaterialsid").attr("disabled","disabled");
	     	$("#vmateChildid").attr("disabled","disabled");
	     	$(".os-ig").attr("disabled","disabled");
			$(".os-cl").attr("disabled","disabled");
	    	 alert(msg);
	    	 self.opener.reloadGD();
	     },
	     error : function(e) {
	    	 $("#vmaterialsid").attr("disabled","disabled");
	    	 $("#vmateChildid").attr("disabled","disabled");
	    	 $(".os-ig").attr("disabled","disabled");
			 $(".os-cl").attr("disabled","disabled");
	     	alert("保存失败!");
	     }
	    });
}

//根据料件大类加载料件子类
function changematerials(){
	$('#vmateChildid').empty();
	$('#vmateChildid').append("<option>==请选择==</option>");
	$.ajax({
		type:"GET",
		url:'${pageContext.request.contextPath}/accessories/accessories!getmateChild',
		data:{"vmaterialsid":$('#vmaterialsid').val()},
		success:function(row){
			var data=row.rows;
			for(var i=0;i<data.length;i++){
				$('#vmateChildid').append("<option value='"+data[i].id+"'>"+data[i].vname+"</option>");
			}
		}
	});
}
//根据品种加载成份
function changedoctype(){
	$('#ingredientid').empty();
	$('#ingredientid').append("<option value='-1'>==请选择==</option>");
	$.ajax({
		type:"GET",
		url:'${pageContext.request.contextPath}/outsource/outsource!getIngByType',
		data:{"docvarietyid":$('#docvarietyid').val()},
		success:function(row){
			var data_ingredients=row.doc_ingredients;
		
			for(var i=0;i<data_ingredients.length;i++){
				$('#ingredientid').append("<option em='"+data_ingredients[i].vcode+"' value='"+data_ingredients[i].id+"'>"+data_ingredients[i].vname+"</option>");
			}
		}
	});
}

/*
//品种
function changedocvariety(){
	var val = $(".docvarietyid option:selected").text();
	if(val == "==选择=="){
		$(".docvarietyname").val("");
	}else{
		$(".docvarietyname").val(val);
	}
	changedoctype();
}

//成份
function changeingredient(){
	var val = $(".ingredientid option:selected").text();
	if(val == "==选择=="){
		$(".ingredientName").val("");
	}else{
		$(".ingredientName").val(val);
	}
}

//颜色
function changecolour(){
	var val = $(".vcolourid option:selected").text();
	if(val == "==选择=="){
		$(".colourName").val("");
	}else{
		$(".colourName").val(val);
	}
}*/

</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
select{width:152px;}
.vmaterials{color:gray;}
.hr hr{height:1px;border:none;border-top:1px dashed #0066CC;}
-->
</style>
</head>
<body class="ContentBody">
  <form id="liningfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>配饰</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>配饰</legend>
				<input name="auxiliaryVO.id" type="hidden" value="${auxiliaryVO.id }">
				<input name="auxiliaryVO.vmoduletype" type="hidden" value="${auxiliaryVO.vmoduletype }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">品种:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.docvarietyid" onChange="javascript:changedoctype();" class="docvarietyid" id="docvarietyid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${docvarietylist}" var="bean">
								<option <c:if test='${auxiliaryVO.docvarietyid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">是否默认数据:</td>
					    <td width="35%">
					    <input name="auxiliaryVO.bisdefault" type="radio" <c:if test='${auxiliaryVO.bisdefault==1}'>checked</c:if> value="1">是
					    <input name="auxiliaryVO.bisdefault" type="radio" <c:if test='${auxiliaryVO.bisdefault==0}'>checked</c:if> value="0">否
					    </td>
					    </tr>
					    <tr>
					  	<td width="100%"  colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					   <tr>
					    <td nowrap align="right" width="11%">编码:</td>
					    <td width="27%"><input name='auxiliaryVO.vcode' id="oscode" type="text" class="text" style="width:154px" value="${auxiliaryVO.vcode }" /></td>
					   <td nowrap align="right" width="11%">名称:</td>
					    <td width="27%"><input name='auxiliaryVO.vname' id="osname" type="text" class="text" style="width:154px" value="${auxiliaryVO.vname }" /></td>
					    </tr>
					    <tr>
					    <td nowrap align="right" width="11%">料件大类:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.materialsid" class="vmaterials" disabled onChange="javascript:changematerials();" id="vmaterialsid">
							<c:forEach items="${materialslist}" var="bean">
								<option <c:if test='${auxiliaryVO.materialsid==bean.id || "外购商品"==bean.vname}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">料件子类:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.matechildid" disabled class="vmateChild" id="vmateChildid" >
							<c:forEach items="${mateChildlist}" var="bean">
								<option <c:if test='${auxiliaryVO.matechildid==bean.id || "配饰"==bean.vname}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    </tr>
					    <tr>
					    <td nowrap align="right" width="11%">颜色:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.colourid" disabled class="vcolourid os-cl" id="vcolourid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${colourlist}" var="bean">
								<option <c:if test='${auxiliaryVO.colourid==bean.id}'>selected="selected"</c:if> em="${bean.vcode }" value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">成份:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.ingredientid" disabled class="ingredientid os-ig" id="ingredientid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${ingredientlist}" var="bean">
								<option <c:if test='${auxiliaryVO.ingredientid==bean.id}'>selected="selected"</c:if> em="${bean.vcode }" value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    </tr>
					    
					  <tr>
					  <td align="right" width="10%">配饰图:</td>
					    <td width="31%" rowspan="1">
					    <input name="auxiliaryVO.vfileupload" id="filepath" value="${auxiliaryVO.vfileupload }" type="text" class="text"  readonly="readonly" size="20" maxlength="200">
	                    [<a href="JavaScript:openem()">上传图片</a> ]
					    </td>
					  </tr>
					  <tr><td></td><td><font color="red">*只允许上传.jpg、.jpeg和.gif类型图片文件</font></td></tr>
					   <tr style="display: none;">
					    <td width="14%" align="right" nowrap>备注:</td>
					    <td width="86%" colspan="3"><textarea name="auxiliaryVO.vmemo" cols="50" rows="5">${auxiliaryVO.vmemo }</textarea></td>
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
