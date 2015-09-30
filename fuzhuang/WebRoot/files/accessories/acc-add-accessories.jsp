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

	$('#pttg_def').datagrid({
		rownumbers:true,
		checkbox:true,
		height:260,
		singleSelect:true,
		collapsible:true,
		remoteSort:false,
		url:'${pageContext.request.contextPath}/accessories!getDefAccBy?auxiliaryId=${auxiliaryVO.id}',
		method:'get',
		pagination:false
	}); 
	
	//以下是将选择的品类和子部件，checked
	//关联的品类串
	var proclassids = "${auxiliaryVO.proclassids}";
	//关联的子部件串
	var subpartids = "${auxiliaryVO.subpartids}";
	var arry_proids = {};
	var arry_subids = {};
	arry_proids = proclassids.split(",");
	arry_subids = subpartids.split(",");
	//品类
	for(var i = 0; i < arry_proids.length; i++){
		$(".link-proclass input[type='checkbox']").each(function(){
			if($(this).val() == arry_proids[i]){
				this.checked = true;
			}
		});
	}
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
openScript('./accessories/upload.jsp',390,270); 
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
			}else if (theForm['auxiliaryVO.docvarietyid'].value == "-1") {
				errMsg = " 品种为空";
				setfocus = "['auxiliaryVO.docvarietyid']";
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
	var tb_def= $('#pttg_def').datagrid('getRows');
	var defdata = JSON.stringify(tb_def); 
	
	$("#vmaterialsid").removeAttr("disabled");
	$("#vmateChildid").removeAttr("disabled");
	$(".ac-cl").removeAttr("disabled");
	$(".ac-ig").removeAttr("disabled");
	$(".ac-brd").removeAttr("disabled");
	$(".ac-use").removeAttr("disabled");
	//关联品类
	var proclassids = "";
	//关联子部件
	var subpartids = "";
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
	$(".link-subpart input[type='checkbox']").each(function(){
		if($(this).is(':checked')){
			if(subpartids == ""){
				subpartids += $(this).val();
			}else{
				subpartids += ","+$(this).val();
			}
		}
	});
	$("input[name='auxiliaryVO.proclassids']").val(proclassids);
	$("input[name='auxiliaryVO.subpartids']").val(subpartids);
	$.ajax({
	     type : "POST",
	     url : "accessories/accessories!saveLining",
	     data:$('#liningfrm').serialize()+"&defdata="+defdata,
	     async:false,
	     cache:false,
	     success : function(msg) {
	     	$("#vmaterialsid").attr("disabled","disabled");
	     	$("#vmateChildid").attr("disabled","disabled");
	     	$(".ac-cl").attr("disabled","disabled");
			$(".ac-ig").attr("disabled","disabled");
			$(".ac-brd").attr("disabled","disabled");
			$(".ac-use").attr("disabled","disabled");
	    	 alert(msg);
	    	 self.opener.reloadGD();
	    	 $("#pttg_def").datagrid('reload'); 
	     },
	     error : function(e) {
	    	 $("#vmaterialsid").attr("disabled","disabled");
	    	 $("#vmateChildid").attr("disabled","disabled");
	    	 $(".ac-cl").attr("disabled","disabled");
			$(".ac-ig").attr("disabled","disabled");
			$(".ac-brd").attr("disabled","disabled");
			$(".ac-use").attr("disabled","disabled");
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
//根据品种加载成份、用途
function changedoctype(){
	$('#useid').empty();
	$('#useid').append("<option value='-1'>==请选择==</option>");
	$('#ingredientid').empty();
	$('#ingredientid').append("<option value='-1'>==请选择==</option>");
	$.ajax({
		type:"GET",
		url:'${pageContext.request.contextPath}/accessories/accessories!getIngUseByType',
		data:{"docvarietyid":$('#docvarietyid').val()},
		success:function(row){
			var data_ingredients=row.doc_ingredients;
			var data_use=row.doc_use;
		
			for(var i=0;i<data_use.length;i++){
				$('#useid').append("<option em='"+data_use[i].vcode+"' value='"+data_use[i].id+"'>"+data_use[i].vname+"</option>");
			}
			for(var i=0;i<data_ingredients.length;i++){
				$('#ingredientid').append("<option em='"+data_ingredients[i].vcode+"' value='"+data_ingredients[i].id+"'>"+data_ingredients[i].vname+"</option>");
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
function changeDef(em,proclassid){

	var isdef= $('input:radio[name="auxiliaryVO.bisdefault"]:checked').val();
	if(!(isdef == "1")){
		return null;
	}
	var bisck = $(em).is(":checked");
	if(!bisck){
		delDefByProclass("pttg_def",proclassid);
	}
	
	openDef(proclassid,"该品类默认值将会失效");
}

//打开默认值页面
function openDef(proclassid,msg)
{

	var isdef= $('input:radio[name="auxiliaryVO.bisdefault"]:checked').val();
	if(!(isdef == "1")){
		return null;
	}

	if(!$(".pr-"+proclassid).is(':checked')){
		alert(msg);
		return ;
	};
	//遍历
	var $dg = $('#pttg_def');
	var all1 = $dg.datagrid('getData').rows;
	for ( var i = 0; i < all1.length; i++) {
		if (all1[i].proclassid == proclassid) {
			if(all1[i].bisdefault == "1"){
				$("#ra-yes").prop("checked",true);
			}
			break;
		}
	}
	//赋值当前点击品类ID
	$("#def-proclassid").val(proclassid);
	$('#w').window('open');
}

//取消
function sub_cancel(){
	$('#w').window('close');
	setDef();
}

//默认值确定
function sub_ok(){
	$('#w').window('close');
	//当前品类id
	var proclassid= $("#def-proclassid").val();
	var bisdefault= $('input:radio[name="bisdefault"]:checked').val();
	
	if(bisdefault == "1"){
		changeToDef("pttg_def",proclassid,bisdefault);
	}else{
		delDefByProclass("pttg_def",proclassid);
	}
	setDef();
}

//取消默认值
function setDef(){
	$("#ra-yes").attr("checked",false);
	$("#ra-no").attr("checked",false);
}

//删除该品类的默认数据表关系
function delDefByProclass(em,proclassid){
	var $dg = $('#'+em);
	var all1 = $dg.datagrid('getData').rows;
	for ( var i = 0; i < all1.length; i++) {
		if (all1[i].proclassid == proclassid) {
			var index = $dg.datagrid('getRowIndex',all1[i]);
			$dg.datagrid('deleteRow', index);
			delDefByProclass(em,proclassid);
		}
	}
}
//选择默认值添加到表pttg_def中
function changeToDef(em,proclassid,bisdefault){
	var flg = true;
	var $dg = $('#'+em);
	var all1 = $dg.datagrid('getData').rows;
	for ( var i = 0; i < all1.length; i++) {
		if (all1[i].proclassid == proclassid) {
			$dg.datagrid('updateRow',{
			index:$dg.datagrid('getRowIndex',all1[i]),
			row:{
					"bisdefault" : bisdefault
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
					"bisdefault" : bisdefault
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
.hr hr{height:1px;border:none;border-top:1px dashed #0066CC;}
.btn-bottom{position:fixed; bottom:0; left:0;width:100%;height:30px;background-color:#F8F8FF;text-align:center;line-height:30px;}
-->
</style>
</head>
<body class="ContentBody">
  <form id="liningfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>辅料</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>辅料</legend>
				<input name="auxiliaryVO.id" type="hidden" value="${auxiliaryVO.id }">
				<input name="auxiliaryVO.vmoduletype" type="hidden" value="${auxiliaryVO.vmoduletype }">
				<input name="auxiliaryVO.proclassids" type="hidden" value="${auxiliaryVO.proclassids }">
				<input name="auxiliaryVO.subpartids" type="hidden" value="${auxiliaryVO.subpartids }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">品种:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.docvarietyid" class="docvarietyid" onChange="javascript:changedoctype();" id="docvarietyid">
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
					    <td width="27%"><input name='auxiliaryVO.vcode' id="accode" type="text" class="text" style="width:154px" value="${auxiliaryVO.vcode }" /></td>
					   <td nowrap align="right" width="11%">名称:</td>
					    <td width="27%"><input name='auxiliaryVO.vname' id="acname" type="text" class="text" style="width:154px" value="${auxiliaryVO.vname }" /></td>
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
								<option <c:if test='${auxiliaryVO.matechildid==bean.id || "辅料"==bean.vname}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    </tr>
					    <tr>
					    <td nowrap align="right" width="11%">颜色:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.colourid" disabled class="vcolourid ac-cl" id="vcolourid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${colourlist}" var="bean">
								<option <c:if test='${auxiliaryVO.colourid==bean.id}'>selected="selected"</c:if> em="${bean.vcode }" value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">成份:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.ingredientid" disabled class="ingredientid ac-ig" id="ingredientid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${ingredientlist}" var="bean">
								<option <c:if test='${auxiliaryVO.ingredientid==bean.id}'>selected="selected"</c:if> em="${bean.vcode }"  value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    </tr>
					    <tr class="doc_ingredients">
					     <td nowrap align="right" width="11%">品牌:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.brandsid" disabled class="brandsid ac-brd" id="brandsid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${brandlist}" var="bean">
								<option <c:if test='${auxiliaryVO.brandsid==bean.id}'>selected="selected"</c:if> em="${bean.vcode }"  value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">用途:</td>
					    <td width="35%">
					    <select name="auxiliaryVO.useid" disabled class="useid ac-use" id="useid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${uselist}" var="bean">
								<option <c:if test='${auxiliaryVO.useid==bean.id}'>selected="selected"</c:if> em="${bean.vcode }"  value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    </tr>
					  <tr>
					  <td align="right" width="10%">辅料图:</td>
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
					   <tr>
					  	<td width="100%"  colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					  <tr class="content">
					  	<td width="100%"  colspan="4" align="left" nowrap>关联品类&nbsp;&nbsp;[<a href="JavaScript:selectAll('link-proclass')">全选</a> ]&nbsp;&nbsp;[<a href="JavaScript:clear('link-proclass')">全清</a> ]</td>
					  </tr>
					   <tr class="content link-proclass">
					  	 <td width="14%" align="right" nowrap>品类：</td>
					    <td width="86%" colspan="3">
					    <c:forEach items="${proclasslist}" var="bean">
							<input name="proclassid" class="pr-${bean.id }" type="checkbox" onclick="javascript:changeDef(this,'${bean.id }');" value="${bean.id }"><a onclick="javascript:openDef('${bean.id }','请选择品类')">${bean.vname }</a>
						</c:forEach>
					    </td>
					  </tr>
					   <tr class="content hr">
					  	 <td width="14%" align="right" nowrap></td>
					  	<td width="86%"  colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					 <!-- 
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
					   -->
					  </table>
			  <br />
				</fieldset></TD>
		</TR>
		</TABLE>
	 </td>
  </tr>
		</TABLE>
</div>
<div id="w" class="easyui-window" title="默认值" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:350px;height:130px;padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<div class="subpart" data-options="region:'center'" style="padding:10px;">
			<input type="hidden" id="def-proclassid" value="">
			是否默认数据:
			<input id="ra-yes" name="bisdefault" type="radio" value="1">是
			<input id="ra-no" name="bisdefault" type="radio" value="0">否
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onClick="javascript:sub_ok()" style="width:80px">Ok</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:sub_cancel()" style="width:80px">Cancel</a>
			</div>
		</div>
</div>
<div style="display: none;">
<table id="pttg_def">
	<thead>
		<tr>
			<th data-options="field:'id',hidden:'true'">id</th>
			<th data-options="field:'auxiliaryId',width:120,align:'center'" width="5%">主表id</th>
			<th data-options="field:'proclassid',width:120,align:'center'" width="5%">品类id</th>
			<th data-options="field:'bisdefault',width:120,align:'center'" width="5%">是否默认</th>
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
