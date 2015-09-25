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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/blbasejs.js"></script>
<script type="text/javascript">
$(function(){
	
	//以下是将选择的品类和子部件，checked
	//关联的子部件串
	var subpartids = "${auxiliaryVO.subpartids}";
	var arry_subids = {};
	arry_subids = subpartids.split(",");
	//子部件
	for(var i = 0; i < arry_subids.length; i++){
		$(".link-subpart input[type='checkbox']").each(function(){
			if($(this).val() == arry_subids[i]){
				this.checked = true;
			}
		});
	}
	
});
function openScript(url, width, height)
{
var left = (screen.width-width)/2;
var top = (screen.height-height)/8;

var Win = window.open(url,"openScript",'width=' + width + ',height=' + height + ',top='+top+', left='+left+',resizable=1,scrollbars=yes,menubar=no,status=yes' );
}

function openem()
{ 
	openScript('http://3d.deelkall.com/fullatr/fullup.jsp?flag=lin',700,530);
	//openScript('http://192.168.1.116:8082/fullatr/fullup.jsp?flag=lin',700,530);
}

//赋值于里料图，后回调关闭子页面
function setFilePath(newName,callback){
 	if(newName == undefined || newName == "" || newName == null){
 		return ;
 	}
	$("#filepath").val(newName);
	if(typeof(callback)!=='undefined'){
		callback&&callback();
	}
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
			}else if (theForm['auxiliaryVO.specname'].value == "") {
				errMsg = "规格为空";
				setfocus = "['auxiliaryVO.specname']";
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
	$(".ln-ty").removeAttr("disabled");
	$(".ln-ig").removeAttr("disabled");
	$(".ln-pat").removeAttr("disabled");
	$(".ln-cl").removeAttr("disabled");
	//关联子部件
	var subpartids = "";
	//将所关联的转换成字符串
	$(".link-subpart input[type='checkbox']").each(function(){
		if($(this).is(':checked')){
			if(subpartids == ""){
				subpartids += $(this).val();
			}else{
				subpartids += ","+$(this).val();
			}
		}
	});
	$("input[name='auxiliaryVO.subpartids']").val(subpartids);
	$.ajax({
	     type : "POST",
	     url : "lining/lining!saveLining",
	     data:$('#liningfrm').serialize(),
	     async:false,
	     cache:false,
	     success : function(msg) {
	     	$("#vmaterialsid").attr("disabled","disabled");
	     	$("#vmateChildid").attr("disabled","disabled");
	     	$(".ln-ty").attr("disabled","disabled");
	     	$(".ln-ig").attr("disabled","disabled");
			$(".ln-pat").attr("disabled","disabled");
			$(".ln-cl").attr("disabled","disabled");
	    	 alert(msg);
	    	 self.opener.reloadGD();
	     },
	     error : function(e) {
	    	 $("#vmaterialsid").attr("disabled","disabled");
	    	 $("#vmateChildid").attr("disabled","disabled");
	    	 $(".ln-ty").attr("disabled","disabled");
	    	 $(".ln-ig").attr("disabled","disabled");
			 $(".ln-pat").attr("disabled","disabled");
			 $(".ln-cl").attr("disabled","disabled");
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
		url:'${pageContext.request.contextPath}/lining/lining!getmateChild',
		data:{"vmaterialsid":$('#vmaterialsid').val()},
		success:function(row){
			var data=row.rows;
			for(var i=0;i<data.length;i++){
				$('#vmateChildid').append("<option value='"+data[i].id+"'>"+data[i].vname+"</option>");
			}
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
select{width:152px;}
.vmaterials{color:gray;}
.btn-bottom{position:fixed; bottom:0; left:0;width:100%;height:30px;background-color:#F8F8FF;text-align:center;line-height:30px;}
-->
</style>
</head>
<body class="ContentBody">
  <form id="liningfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>里料</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>里料</legend>
				<input name="auxiliaryVO.id" type="hidden" value="${auxiliaryVO.id }">
				<input name="auxiliaryVO.vmoduletype" type="hidden" value="${auxiliaryVO.vmoduletype }">
				<input name="auxiliaryVO.subpartids" type="hidden" value="${auxiliaryVO.subpartids }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					   <tr>
					    <td nowrap align="right" width="11%">编码:</td>
					    <td width="27%"><input name='auxiliaryVO.vcode' id="lncode" type="text" class="text" style="width:154px" value="${auxiliaryVO.vcode }" /></td>
					    <td nowrap align="right" width="11%">名称:</td>
					    <td width="27%"><input name='auxiliaryVO.vname' id="lnname" type="text" class="text" style="width:154px" value="${auxiliaryVO.vname }" /></td>
					    </tr>
					  <tr>
					    <td nowrap align="right" width="11%">料件大类:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.materialsid" class="vmaterials" disabled onChange="javascript:changematerials();" id="vmaterialsid">
							<c:forEach items="${materialslist}" var="bean">
								<option <c:if test='${auxiliaryVO.materialsid==bean.id || "原材料"==bean.vname}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					     <td nowrap align="right" width="11%">料件子类:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.matechildid" disabled class="vmateChild" id="vmateChildid" >
							<c:forEach items="${mateChildlist}" var="bean">
								<option <c:if test='${auxiliaryVO.matechildid==bean.id || "里料"==bean.vname}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    </tr>
					  <tr>
					    <td nowrap align="right" width="11%">里料细分类:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.iautype" class="iautype ln-ty" disabled id="iautype">
							<option <c:if test='${auxiliaryVO.iautype=="1"}'>selected="selected"</c:if> value="1">梭织里料</option>
							<option <c:if test='${auxiliaryVO.iautype=="2"}'>selected="selected"</c:if> value="2">针织里料</option>
							<option <c:if test='${auxiliaryVO.iautype=="3"}'>selected="selected"</c:if> value="3">网眼布</option>
							<option <c:if test='${auxiliaryVO.iautype=="4"}'>selected="selected"</c:if> value="4">裥棉里料</option>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">颜色:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.colourid" disabled class="vcolour ln-cl" id="vcolourid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${colourlist}" var="bean">
								<option <c:if test='${auxiliaryVO.colourid==bean.id}'>selected="selected"</c:if> em="${bean.vcode }" value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    </tr>
					     <tr>
					    <td nowrap align="right" width="11%">成分:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.ingredientid" disabled class="vingredient ln-ig" id="ingredientid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${ingredientlist}" var="bean">
								<option <c:if test='${auxiliaryVO.ingredientid==bean.id}'>selected="selected"</c:if> em="${bean.vcode }" value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">花型:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.patternid" disabled class="vpattern ln-pat" id="vpatternid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${patternlist}" var="bean">
								<option <c:if test='${auxiliaryVO.patternid==bean.id}'>selected="selected"</c:if> em="${bean.vcode }" value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    </tr>
					    <tr>
					    <td nowrap align="right" width="11%">规格:</td>
					    <td width="35%">
					    <input name='auxiliaryVO.specname' id="specname" type="text" class="text" style="width:154px" value="${auxiliaryVO.specname}" />
					    </td>
					  	<td nowrap align="right" width="11%">是否默认数据:</td>
					    <td width="35%">
					    <input name="auxiliaryVO.bisdefault" type="radio" <c:if test='${auxiliaryVO.bisdefault==1}'>checked</c:if> value="1">是
					    <input name="auxiliaryVO.bisdefault" type="radio" <c:if test='${auxiliaryVO.bisdefault==0}'>checked</c:if> value="0">否
					    </td>
					  </tr>
					  <tr>
					  <td align="right" width="10%">里料图:</td>
					    <td nowrap width="31%" rowspan="1">
					    <input name="auxiliaryVO.vfileupload" id="filepath" value="${auxiliaryVO.vfileupload }" type="text" class="text"  readonly="readonly" size="30" maxlength="200">
	                    [<a href="JavaScript:openem()">上传图片</a> ]
					    </td>
					    <td nowrap align="left" colspan="2" width="100%"><font color="red">*只允许上传.jpg、.jpeg和.gif类型图片文件</font></td>
					  </tr>
					   <tr style="display: none;">
					    <td width="14%" align="right" nowrap>备注:</td>
					    <td width="86%" colspan="3"><textarea name="auxiliaryVO.vmemo" cols="50" rows="5">${auxiliaryVO.vmemo }</textarea></td>
					  </tr>
					   <tr>
					  	<td width="100%"  colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					  <tr class="content">
					  	<td width="100%"  colspan="4" align="left" nowrap>关联子部件&nbsp;&nbsp;[<a href="JavaScript:selectAll('link-subpart')">全选</a> ]&nbsp;&nbsp;[<a href="JavaScript:clear('link-subpart')">全清</a> ]</td>
					  </tr>
					   <tr class="content link-subpart">
					  	 <td width="14%" align="right" nowrap>子部件：</td>
					    <td width="86%" colspan="3">
					    <c:forEach items="${subpartlist}" var="bean">
							<input name="subpartid" type="checkbox" value="${bean.id }">${bean.vdef1}-${bean.vname }
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
		<input type="button" onclick="javascript:save()" name="Submit" value="保存" class="button"/>　
		<input type="reset" name="Submit2" value="关闭" class="button" onclick="javascript:window.close()"/>
</div>
</form>
</body>
</html>
