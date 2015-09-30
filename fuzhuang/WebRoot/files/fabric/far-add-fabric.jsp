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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/themes/gray/easyui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" rev="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="all" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/blbasejs.js"></script>
<script type="text/javascript">

$(function(){
	
	$('#pttg_cis').datagrid({
		rownumbers:true,
		checkbox:true,
		height:260,
		singleSelect:true,
		collapsible:true,
		remoteSort:false,
		url:'${pageContext.request.contextPath}/fabric!getFarCisJson?auxiliaryId=${auxiliaryVO.id}',
		method:'get',
		pagination:false
	}); 
	
	
	//以下是将选择的品类和子部件，checked
	//关联的品类串
	var proclassids = "${auxiliaryVO.proclassids}";
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
function openScript(url, width, height)
{
var left = (screen.width-width)/2;
var top = (screen.height-height)/8;

var Win = window.open(url,"openScript",'width=' + width + ',height=' + height + ',top='+top+', left='+left+',resizable=1,scrollbars=yes,menubar=no,status=yes' );
}

function openem()
{ 
	openScript('http://3d.deelkall.com/fullatr/fullup.jsp?flag=far',700,530); 
	//openScript('http://192.168.1.116:8082/fullatr/fullup.jsp?flag=far',700,530); 
}

//赋值于面料图，后回调关闭子页面
function setFilePath(newName,callback){
 	if(newName == undefined || newName == "" || newName == null){
 		return ;
 	}
	$("#filepath").val(newName);
	if(typeof(callback)!=='undefined'){
		callback&&callback();
	}
}

//参照辅料档案
function openrefer(forward,type,id,nameId)
{ 
	//辅料
	if(forward == "acc"){
		openScript("./fabric/far-refer-accessories.jsp?type="+type+"&id="+id+"&nameId="+nameId,800,300); 
	}//里料
	else if(forward == "lining"){
		openScript("./fabric/far-refer-lining.jsp?type="+type+"&id="+id+"&nameId="+nameId,800,300); 
	}
}
function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['auxiliaryVO.vcode'].value == "") {
				errMsg = "编码为空";
				setfocus = "['auxiliaryVO.vcode']";
			}else if (theForm['auxiliaryVO.vname'].value == "") {
				errMsg = "名称为空";
				setfocus = "['auxiliaryVO.vname']";
			}else if (theForm['auxiliaryVO.specname'].value == "") {
				errMsg = "规格为空";
				setfocus = "['auxiliaryVO.specname']";
			}else if (theForm['auxiliaryVO.dpurchasemny'].value == "") {
				errMsg = "采购单价为空";
				setfocus = "['auxiliaryVO.dpurchasemny']";
			}else if (theForm['auxiliaryVO.colourid'].value == "-1") {
				errMsg = "编码错误，不能够正确解析颜色";
				setfocus = "['auxiliaryVO.vcode']";
			}else if (theForm['auxiliaryVO.ingredientid'].value == "-1") {
				errMsg = "编码错误，不能够正确解析成份";
				setfocus = "['auxiliaryVO.vcode']";
			}else if (theForm['auxiliaryVO.patternid'].value == "-1") {
				errMsg = "编码错误，不能够正确解析花型";
				setfocus = "['auxiliaryVO.vcode']";
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
	
	//主部件子表
	var tb_cis= $('#pttg_cis').datagrid('getRows');
	var cisdata = JSON.stringify(tb_cis); 
	
	$("#vmaterialsid").removeAttr("disabled");
	$("#vmateChildid").removeAttr("disabled");
	$(".fr-ty").removeAttr("disabled");
	$(".fr-ig").removeAttr("disabled");
	$(".fr-pat").removeAttr("disabled");
	$(".fr-cl").removeAttr("disabled");
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
	$("input[name='auxiliaryVO.proclassids']").val(proclassids);
	$.ajax({
	     type : "POST",
	     url : "fabric/fabric!saveFabric",
	     data:$('#fabricfrm').serialize()+"&cisdata="+cisdata,
	     async:false,
	     cache:false,
	     success : function(msg) {
	     	$("#vmaterialsid").attr("disabled","disabled");
	     	$("#vmateChildid").attr("disabled","disabled");
	     	$(".fr-ty").attr("disabled","disabled");
	     	$(".fr-ig").attr("disabled","disabled");
			$(".fr-pat").attr("disabled","disabled");
			$(".fr-cl").attr("disabled","disabled");
	    	 alert(msg);
	    	 self.opener.reloadGD();
	    	 $("#pttg_cis").datagrid('reload');  
	     },
	     error : function(e) {
	    	 $("#vmaterialsid").attr("disabled","disabled");
	    	 $("#vmateChildid").attr("disabled","disabled");
	    	 $(".fr-ty").attr("disabled","disabled");
	    	 $(".fr-ig").attr("disabled","disabled");
			 $(".fr-pat").attr("disabled","disabled");
			 $(".fr-cl").attr("disabled","disabled");
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
		url:'${pageContext.request.contextPath}/fabric/method!getmateChild',
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

//选中品类
function changeIcis(em,proclassid){
	var bisck = $(em).is(":checked");
	var flag = true;
	
	if(!bisck){
		delCisByProclass("pttg_cis",proclassid);
	}
	
	openIcis(proclassid,"该品类顺色关联将会失效");
}

//打开顺色页面
function openIcis(proclassid,msg)
{
	if(!$(".pr-"+proclassid).is(':checked')){
		alert(msg);
		return ;
	};
	
	//遍历顺色关联表取值赋予顺色关联字段
	var $dg = $('#pttg_cis');
	var all1 = $dg.datagrid('getData').rows;
	for ( var i = 0; i < all1.length; i++) {
		if (all1[i].proclassid == proclassid) {
			$("#icisLine").val(all1[i].icisLine);
			$("#icisLineNM").val(all1[i].icisLineNM);
			$("#icisLineNM-text").text(all1[i].icisLineNM);
			$("#icisButton").val(all1[i].icisButton);
			$("#icisButtonNM").val(all1[i].icisButtonNM);
			$("#icisButtonNM-text").text(all1[i].icisButtonNM);
			$("#icisLining").val(all1[i].icisLining);
			$("#icisLiningNM").val(all1[i].icisLiningNM);
			$("#icisLiningNM-text").text(all1[i].icisLiningNM);
			$("#icisXLining").val(all1[i].icisXLining);
			$("#icisXLiningNM").val(all1[i].icisXLiningNM);
			$("#icisXLiningNM-text").text(all1[i].icisXLiningNM);
			$("#icisHBLining").val(all1[i].icisHBLining);
			$("#icisHBLiningNM").val(all1[i].icisHBLiningNM);
			$("#icisHBLiningNM-text").text(all1[i].icisHBLiningNM);
			$("#icisBagging").val(all1[i].icisBagging);
			$("#icisBaggingNM").val(all1[i].icisBaggingNM);
			$("#icisBaggingNM-text").text(all1[i].icisBaggingNM);
			$("#icisComponent").val(all1[i].icisComponent);
			$("#icisComponentNM").val(all1[i].icisComponentNM);
			$("#icisComponentNM-text").text(all1[i].icisComponentNM);
			$("#iciszipper").val(all1[i].iciszipper);
			$("#iciszipperNM").val(all1[i].iciszipperNM);
			$("#iciszipperNM-text").text(all1[i].iciszipperNM);
			break;
		}
	}
	//赋值当前点击品类ID
	$("#cis-proclassid").val(proclassid);
	$('#w').window('open');
}

//取消
function sub_cancel(){
	$('#w').window('close');
	clearCis();
}

//顺色关联确定
function sub_ok(){
	$('#w').window('close');
	//当前品类id
	var proclassid= $("#cis-proclassid").val();
	var icisLine= $("#icisLine").val();
	var icisButton= $("#icisButton").val();
	var icisLining= $("#icisLining").val();
	var icisXLining= $("#icisXLining").val();
	var icisHBLining= $("#icisHBLining").val();
	var icisBagging= $("#icisBagging").val();
	var icisComponent= $("#icisComponent").val();
	var iciszipper= $("#iciszipper").val();
	
	var icisLineNM= $("#icisLineNM").val();
	var icisButtonNM= $("#icisButtonNM").val();
	var icisLiningNM= $("#icisLiningNM").val();
	var icisXLiningNM= $("#icisXLiningNM").val();
	var icisHBLiningNM= $("#icisHBLiningNM").val();
	var icisBaggingNM= $("#icisBaggingNM").val();
	var icisComponentNM= $("#icisComponentNM").val();
	var iciszipperNM= $("#iciszipperNM").val();
	
	if((icisLine == "" || icisLine == "0") && (icisButton == "" || icisButton == "0") && (icisLining == "" || icisLining == "0")
	 && (icisXLining == "" || icisXLining == "0") && (icisHBLining == "" || icisHBLining == "0") && (icisBagging == "" || icisBagging == "0") 
	 && (icisComponent == "" || icisComponent == "0") && (iciszipper == "" || iciszipper == "0")){
		delCisByProclass("pttg_cis",proclassid);
	}else{
		changeCis("pttg_cis",proclassid,icisLine,icisButton,icisLining,icisXLining,icisHBLining,icisBagging,icisComponent,iciszipper,
		icisLineNM,icisButtonNM,icisLiningNM,icisXLiningNM,icisHBLiningNM,icisBaggingNM,icisComponentNM,iciszipperNM);
	}
	//清空
	clearCis();
}

//清空
function clearCis(){
	$(".showcis").empty();
	$(".far-cis input[type=hidden]").each(function(){
		$(this).val("");
	});
}

//取消当前顺色值
function cancel_cis(id){
	$("#"+id).val("");
	$("#"+id+"NM").val("");
	$("#"+id+"NM-text").empty();
}

//删除该品类的顺色关联表关系
function delCisByProclass(em,proclassid){

	var $dg = $('#'+em);
	var all1 = $dg.datagrid('getData').rows;
	
	for ( var i = 0; i < all1.length; i++) {
		
		if (all1[i].proclassid == proclassid) {
			var index = $dg.datagrid('getRowIndex',all1[i]);
			//alert("删除："+index);
			$dg.datagrid('deleteRow', index);
			delCisByProclass(em,proclassid);
		}
	}
}

//选择顺色添加到表pttg_cis中
function changeCis(em,proclassid,icisLine,icisButton,icisLining,icisXLining,icisHBLining,icisBagging,icisComponent,iciszipper,icisLineNM,icisButtonNM,icisLiningNM,icisXLiningNM,icisHBLiningNM,icisBaggingNM,icisComponentNM,iciszipperNM){
	var flg = true;
	var $dg = $('#'+em);
	var all1 = $dg.datagrid('getData').rows;
	for ( var i = 0; i < all1.length; i++) {
		if (all1[i].proclassid == proclassid) {
			$dg.datagrid('updateRow',{
			index:$dg.datagrid('getRowIndex',all1[i]),
			row:{
					"icisLine" : icisLine,
					"icisButton" : icisButton,
					"icisLining" : icisLining,
					"icisXLining" : icisXLining,
					"icisHBLining" : icisHBLining,
					"icisBagging" : icisBagging,
					"icisComponent" : icisComponent,
					"iciszipper" : iciszipper,
					"icisLineNM" : icisLineNM,
					"icisButtonNM" : icisButtonNM,
					"icisLiningNM" : icisLiningNM,
					"icisXLiningNM" : icisXLiningNM,
					"icisHBLiningNM" : icisHBLiningNM,
					"icisBaggingNM" : icisBaggingNM,
					"icisComponentNM" : icisComponentNM,
					"iciszipperNM" : iciszipperNM
				}
			});
			flg = false;
			break;
		}
	}
	if(flg){
		var index = $dg.datagrid('getData').rows.length;
		$dg.datagrid('insertRow', {
				index:index,
				row:{
					"proclassid" : proclassid,
					"icisLine" : icisLine,
					"icisButton" : icisButton,
					"icisLining" : icisLining,
					"icisXLining" : icisXLining,
					"icisHBLining" : icisHBLining,
					"icisBagging" : icisBagging,
					"icisComponent" : icisComponent,
					"iciszipper" : iciszipper,
					"icisLineNM" : icisLineNM,
					"icisButtonNM" : icisButtonNM,
					"icisLiningNM" : icisLiningNM,
					"icisXLiningNM" : icisXLiningNM,
					"icisHBLiningNM" : icisHBLiningNM,
					"icisBaggingNM" : icisBaggingNM,
					"icisComponentNM" : icisComponentNM,
					"iciszipperNM" : iciszipperNM
				}
		});
	}
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
  <form id="fabricfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>面料</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		<input type="hidden" name="auxiliaryVO.ambient" value="${auxiliaryVO.ambient }"/>
		<input type="hidden" name="auxiliaryVO.specular" value="${auxiliaryVO.specular }"/>
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>面料</legend>
				<input name="auxiliaryVO.id" type="hidden" value="${auxiliaryVO.id }">
				<input name="auxiliaryVO.vmoduletype" type="hidden" value="${auxiliaryVO.vmoduletype }">
				<input name="auxiliaryVO.proclassids" type="hidden" value="${auxiliaryVO.proclassids }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					   <tr>
					    <td nowrap align="right" width="11%">编码:</td>
					    <td width="27%"><input name='auxiliaryVO.vcode' id="frcode" type="text" class="text" style="width:154px" value="${auxiliaryVO.vcode }" /></td>
					   	<td nowrap align="right" width="11%">名称:</td>
					    <td width="27%"><input name='auxiliaryVO.vname' id="frname" type="text" class="text" style="width:154px" value="${auxiliaryVO.vname }" /></td>
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
								<option <c:if test='${auxiliaryVO.matechildid==bean.id || "面料"==bean.vname}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    </tr>
					  <tr>
					    <td nowrap align="right" width="11%">面料细分类:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.iautype" class="iautype fr-ty" disabled id="iautype">
							<option <c:if test='${auxiliaryVO.iautype=="1"}'>selected="selected"</c:if> value="1">梭织面料</option>
							<option <c:if test='${auxiliaryVO.iautype=="2"}'>selected="selected"</c:if> value="2">针织面料</option>
							<option <c:if test='${auxiliaryVO.iautype=="3"}'>selected="selected"</c:if> value="3">毛皮类</option>
							<option <c:if test='${auxiliaryVO.iautype=="4"}'>selected="selected"</c:if> value="4">皮革类</option>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">颜色:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.colourid" disabled class="vcolour fr-cl" id="vcolourid">
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
					    <select name="auxiliaryVO.ingredientid" disabled class="vingredient fr-ig" id="ingredientid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${ingredientlist}" var="bean">
								<option <c:if test='${auxiliaryVO.ingredientid==bean.id}'>selected="selected"</c:if> em="${bean.vcode }" value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">花型:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.patternid" disabled class="vpattern fr-pat" id="vpatternid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${patternlist}" var="bean">
								<option <c:if test='${auxiliaryVO.patternid==bean.id}'>selected="selected"</c:if> em="${bean.vcode }" value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    </tr>
					    <tr>
					    <td nowrap align="right" width="11%">品级:</td>
					    <td width="35%">
					    <input name='auxiliaryVO.vrank' id="vrank" type="text" class="text" style="width:154px" value="${auxiliaryVO.vrank }" />
					    </td>
					    <td nowrap align="right" width="11%">规格:</td>
					    <td width="35%">
					    <input name='auxiliaryVO.specname' id="specname" type="text" class="text" style="width:154px" value="${auxiliaryVO.specname}" />
					    </td>
					    </tr>
					    <tr>
					    <td nowrap align="right" width="11%">品牌:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.basedocId" class="basedocId fr-doc" id="basedocId">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${farbrandlist}" var="bean">
								<option <c:if test='${auxiliaryVO.basedocId==bean.id}'>selected="selected"</c:if> em="${bean.vcode }" value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">采购单价:</td>
					    <td width="35%">
					    <input name='auxiliaryVO.dpurchasemny' id="dpurchasemny" type="text" class="text" style="width:154px" value="${auxiliaryVO.dpurchasemny }" />
					    </td>
					    </tr>
					    <tr>
					    <td nowrap align="right" width="11%">是否展示:</td>
					    <td width="35%">
					    <input name="auxiliaryVO.isClient" type="radio" <c:if test='${auxiliaryVO.isClient==1}'>checked</c:if> value="1">是
					    <input name="auxiliaryVO.isClient" type="radio" <c:if test='${auxiliaryVO.isClient==0}'>checked</c:if> value="0">否
					    </td>
					    <td nowrap align="right" width="11%">数据流向:</td>
					    <td width="35%">
					    <input name="auxiliaryVO.deviceType" type="checkbox" <c:if test='${auxiliaryVO.deviceType==1}'>checked</c:if> value="1">手机端
					    </td>
					    </tr>
					    <tr>
					    <td nowrap align="right" width="11%">是否撞色:</td>
					    <td width="35%">
					    <input name="auxiliaryVO.biscontrastcolor" type="radio" <c:if test='${auxiliaryVO.biscontrastcolor==1}'>checked</c:if> value="1">是
					    <input name="auxiliaryVO.biscontrastcolor" type="radio" <c:if test='${auxiliaryVO.biscontrastcolor==0}'>checked</c:if> value="0">否
					    </td>
					    <td nowrap align="right" width="11%">是否贴布:</td>
					    <td width="35%">
					    <input name="auxiliaryVO.bispatch" type="radio" <c:if test='${auxiliaryVO.bispatch==1}'>checked</c:if> value="1">是
					    <input name="auxiliaryVO.bispatch" type="radio" <c:if test='${auxiliaryVO.bispatch==0}'>checked</c:if> value="0">否
					    </td>
					    </tr>
					    <tr>
					    <td align="right" width="10%">面料图:</td>
					     <td nowrap width="31%" rowspan="1">
					    <input name="auxiliaryVO.vfileupload" id="filepath" value="${auxiliaryVO.vfileupload }" title="" type="text" class="text"  readonly="readonly" size="30" maxlength="200">
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
					  	<td width="100%"  colspan="4" align="left" nowrap>关联品类:&nbsp;&nbsp;[<a href="JavaScript:selectAll('link-proclass')">全选</a> ]&nbsp;&nbsp;[<a href="JavaScript:clear('link-proclass')">全清</a> ]</td>
					  </tr>
					   <tr class="content link-proclass">
					  	 <td width="14%" align="right" nowrap>品类：</td>
					    <td width="86%" colspan="3">
					    <c:forEach items="${proclasslist}" var="bean">
							<input name="proclassid" class="pr-${bean.id }" type="checkbox" onclick="javascript:changeIcis(this,'${bean.id }');" value="${bean.id }"><a onclick="javascript:openIcis('${bean.id }','请选择品类')">${bean.vname }</a>
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
<div id="w" class="easyui-window" title="顺色关联" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:650px;height:230px;padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<div class="subpart" data-options="region:'center'" style="padding:10px;">
			<input type="hidden" id="cis-proclassid" value="">
			<table class="far-cis" border="0" cellpadding="2" cellspacing="1" style="width:100%">
					   <tr class="content">
					    <td width="11%" align="right" nowrap>线:</td>
					    <td width="35%" nowrap>
					    <input type="hidden" id="icisLine" name="auxiliaryVO.icisLine" value="${auxiliaryVO.icisLine }">
					    <input type="hidden" id="icisLineNM" name="auxiliaryVO.vcisLineName" value="${auxiliaryVO.vcisLineName }">
					    <span class="showcis" id="icisLineNM-text">${auxiliaryVO.vcisLineName }</span>
					    [<a href="JavaScript:openrefer('acc','线','icisLine','icisLineNM')">请选择</a> ]
					    [<a href="JavaScript:cancel_cis('icisLine')">取消</a>]
					    </td>
					    <td width="11%" align="right" nowrap>扣子:</td>
					    <td width="35%" nowrap>
					    <input type="hidden" id="icisButton" name="auxiliaryVO.icisButton" value="${auxiliaryVO.icisButton }">
					    <input type="hidden" id="icisButtonNM" name="auxiliaryVO.vcisButtonName" value="${auxiliaryVO.vcisButtonName }">
					    <span class="showcis" id="icisButtonNM-text">${auxiliaryVO.vcisButtonName }</span>
					    [<a href="JavaScript:openrefer('acc','纽扣','icisButton','icisButtonNM')">请选择</a> ]
					    [<a href="JavaScript:cancel_cis('icisButton')">取消</a>]
					    </td>
					  </tr>
					   <tr class="content">
					    <td width="11%" align="right" nowrap>里料:</td>
					    <td width="35%" nowrap>
					    <input type="hidden" id="icisLining" name="auxiliaryVO.icisLining" value="${auxiliaryVO.icisLining }">
					    <input type="hidden" id="icisLiningNM" name="auxiliaryVO.vcisLiningName" value="${auxiliaryVO.vcisLiningName }">
					    <span class="showcis" id="icisLiningNM-text">${auxiliaryVO.vcisLiningName }</span>
					    [<a href="JavaScript:openrefer('lining','里料','icisLining','icisLiningNM')">请选择</a> ]
					    [<a href="JavaScript:cancel_cis('icisLining')">取消</a>]
					    </td>
					    <td width="11%" align="right" nowrap>袖里料:</td>
					    <td width="35%" nowrap>
					    <input type="hidden" id="icisXLining" name="auxiliaryVO.icisXLining" value="${auxiliaryVO.icisXLining }">
					    <input type="hidden" id="icisXLiningNM" name="auxiliaryVO.vcisXLiningName" value="${auxiliaryVO.vcisXLiningName }">
					    <span class="showcis" id="icisXLiningNM-text">${auxiliaryVO.vcisXLiningName }</span>
					    [<a href="JavaScript:openrefer('lining','袖里料','icisXLining','icisXLiningNM')">请选择</a> ]
					    [<a href="JavaScript:cancel_cis('icisXLining')">取消</a>]
					    </td>
					  </tr>
					   <tr class="content">
					   <td width="11%" align="right" nowrap>后背用料:</td>
					    <td width="35%" nowrap>
					    <input type="hidden" id="icisHBLining" name="auxiliaryVO.icisHBLining" value="${auxiliaryVO.icisHBLining }">
					    <input type="hidden" id="icisHBLiningNM" name="auxiliaryVO.vcisHBLiningName" value="${auxiliaryVO.vcisHBLiningName }">
					    <span class="showcis" id="icisHBLiningNM-text">${auxiliaryVO.vciszipperName }</span>
					    [<a href="JavaScript:openrefer('lining','后背用料','icisHBLining','icisHBLiningNM')">请选择</a> ]
					    [<a href="JavaScript:cancel_cis('icisHBLining')">取消</a>]
					    </td>
					   
					    <td width="11%" align="right" nowrap>袋布:</td>
					    <td width="35%" nowrap>
					    <input type="hidden" id="icisBagging" name="auxiliaryVO.icisBagging" value="${auxiliaryVO.icisBagging }">
					    <input type="hidden" id="icisBaggingNM" name="auxiliaryVO.vcisBaggingName" value="${auxiliaryVO.vcisBaggingName }">
					    <span class="showcis" id="icisBaggingNM-text">${auxiliaryVO.vcisBaggingName }</span>
					    [<a href="JavaScript:openrefer('acc','袋布','icisBagging','icisBaggingNM')">请选择</a> ]
					    [<a href="JavaScript:cancel_cis('icisBagging')">取消</a>]
					    </td>
					  </tr>
					  <tr class="content">
					    <td width="11%" align="right" nowrap>配件:</td>
					    <td width="35%" nowrap>
					    <input type="hidden" id="icisComponent" name="auxiliaryVO.icisComponent" value="${auxiliaryVO.icisComponent }">
					    <input type="hidden" id="icisComponentNM" name="auxiliaryVO.vcisComponentName" value="${auxiliaryVO.vcisComponentName }">
					    <span class="showcis" id="icisComponentNM-text">${auxiliaryVO.vcisComponentName }</span>
					    [<a href="JavaScript:openrefer('acc','配件','icisComponent','icisComponentNM')">请选择</a> ]
					    [<a href="JavaScript:cancel_cis('icisComponent')">取消</a>]
					    </td>
					    
					     <td width="11%" align="right" nowrap>拉链:</td>
					    <td width="35%" nowrap>
					    <input type="hidden" id="iciszipper" name="auxiliaryVO.iciszipper" value="${auxiliaryVO.iciszipper }">
					    <input type="hidden" id="iciszipperNM" name="auxiliaryVO.vciszipperName" value="${auxiliaryVO.vciszipperName }">
					    <span class="showcis" id="iciszipperNM-text">${auxiliaryVO.vciszipperName }</span>
					    [<a href="JavaScript:openrefer('acc','拉链','iciszipper','iciszipperNM')">请选择</a> ]
					    [<a href="JavaScript:cancel_cis('iciszipper')">取消</a>]
					    </td>
					  </tr>
					  </table>
				</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onClick="javascript:sub_ok()" style="width:80px">Ok</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:sub_cancel()" style="width:80px">Cancel</a>
			</div>
		</div>
</div>
<div style="display: none;">
<table id="pttg_cis">
	<thead>
		<tr>
			<th data-options="field:'id',hidden:'true'">id</th>
			<th data-options="field:'auxiliaryId',width:120,align:'center'" width="5%">主表id</th>
			<th data-options="field:'proclassid',width:120,align:'center'" width="5%">品类id</th>
			<th data-options="field:'iflag',width:120,align:'center'" width="5%">iflag</th>
			
			<th data-options="field:'icisLine',width:120,align:'center'" width="5%">icisLine</th>
			<th data-options="field:'icisLineNM',width:120,align:'center'" width="5%">icisLineNM</th>
			<th data-options="field:'icisButton',width:120,align:'center'" width="5%">icisButton</th>
			<th data-options="field:'icisButtonNM',width:120,align:'center'" width="5%">icisButtonNM</th>
			<th data-options="field:'icisLining',width:120,align:'center'" width="5%">icisLining</th>
			<th data-options="field:'icisLiningNM',width:120,align:'center'" width="5%">icisLiningNM</th>
			<th data-options="field:'icisXLining',width:120,align:'center'" width="5%">icisXLining</th>
			<th data-options="field:'icisXLiningNM',width:120,align:'center'" width="5%">icisXLiningNM</th>
			<th data-options="field:'icisHBLining',width:120,align:'center'" width="5%">icisHBLining</th>
			<th data-options="field:'icisHBLiningNM',width:120,align:'center'" width="5%">icisHBLiningNM</th>
			<th data-options="field:'icisBagging',width:120,align:'center'" width="5%">icisBagging</th>
			<th data-options="field:'icisBaggingNM',width:120,align:'center'" width="5%">icisBaggingNM</th>
			<th data-options="field:'icisComponent',width:120,align:'center'" width="5%">icisComponent</th>
			<th data-options="field:'icisComponentNM',width:120,align:'center'" width="5%">icisComponentNM</th>
			<th data-options="field:'iciszipper',width:120,align:'center'" width="5%">iciszipper</th>
			<th data-options="field:'iciszipperNM',width:120,align:'center'" width="5%">iciszipperNM</th>
		</tr>
	</thead>
</table>
</div>
<div class="btn-bottom">
		<input type="button" onclick="javascript:save()" name="Submit" value="保存" class="button"/>　
		<input type="reset" name="Submit2" value="关闭" class="button" onclick="javascript:window.close()"/>
</div>
</form>
</body>
</html>
