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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/themes/gray/easyui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/blbasejs.js"></script>
<script type="text/javascript">

$(function(){
	// 数据表格
	$('#pttg').datagrid({
		rownumbers:true,
		checkbox:true,
		height:260,
		toolbar:"#tb",
		singleSelect:true,
		collapsible:true,
		remoteSort:false,
		url:'${pageContext.request.contextPath}/sch!getSchemeInfoJson?schemeId=${schemeVO.id}',
		method:'get',
		pagination:false,
		onClickRow: onClickRow
	});
	
/*	var table = $('#pttg').datagrid('getPager'); 
	$(table).pagination({ 
		layout:['list','sep','first','prev','links','next','last','sep','refresh']
	}); */
	
	$(window).resize(function () {
        $('#pttg').datagrid('resize', {
        	width: function(){return document.body.clientWidth;}
        }).datagrid('resize', {
        	width: function(){return document.body.clientWidth;}
        });
 
	});
	
});

		var editIndex = undefined;
		function endEditing(){
			if (editIndex == undefined){return true;}
			if ($('#pttg').datagrid('validateRow', editIndex)){
				var ed = $('#pttg').datagrid('getEditor', {index:editIndex,field:'basedocid'});
				var basedocname = $(ed.target).combobox('getText');
				$('#pttg').datagrid('getRows')[editIndex]['basedocName'] = basedocname;
				$('#pttg').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		function onClickRow(index){
			if (editIndex != index){
				if (endEditing()){
					$('#pttg').datagrid('selectRow', index)
							.datagrid('beginEdit', index);
					editIndex = index;
				} else {
					$('#pttg').datagrid('selectRow', editIndex);
				}
			}
		}
		
		function sel(){
			var jiedian=$('#pttg').datagrid('getSelections'); 
			if(jiedian.length <= 0){
				alert("提示信息","请选择一条数据");
				return;
			}
		}

		function append(){
			if (endEditing()){
				$('#pttg').datagrid('appendRow',{ddiscountmny:'100'});
				editIndex = $('#pttg').datagrid('getRows').length-1;
				
				$('#pttg').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);
			}
		}
		function removeit(){
			sel();
			if (editIndex == undefined){return;}
			$('#pttg').datagrid('cancelEdit', editIndex)
					.datagrid('deleteRow', editIndex);
			editIndex = undefined;
		}

function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['schemeVO.vcode'].value == "") {
				errMsg = "编号为空";
				setfocus = "['schemeVO.vcode']";
			}else if (theForm['schemeVO.vname'].value == "") {
				errMsg = "名称为空";
				setfocus = "['schemeVO.vname']";
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
	endEditing();
	//子表数据	
	var sub= $('#pttg').datagrid('getRows');
	var subdata = JSON.stringify(sub); 
	//alert(subdata);
	$.ajax({
	     type : "POST",
	     url : "scheme/sch!saveScheme",
	     data:$('#schfrm').serialize()+"&subdata="+subdata,
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

</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
select{width:152px;}
.vmaterials{color:gray;}
.menu-btn{cursor: pointer;}
-->
</style>
</head>
<body class="ContentBody" >
  <form id="schfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>定制价方案</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>定制价方案</legend>
				<input name="schemeVO.id" type="hidden" value="${schemeVO.id }">
				<input name="schemeVO.type" type="hidden" value="${schemeVO.type }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">编码:</td>
					    <td width="27%"><input name='schemeVO.vcode' id="vcode" maxlength="3" type="text" class="text" style="width:154px" value="${schemeVO.vcode }" /></td>				  
					    <td nowrap align="right" width="11%">方案名称:</td>
					    <td width="27%">
					   	<input name='schemeVO.vname' id="vname" type="text" class="text" style="width:154px" value="${schemeVO.vname }" />
					    </td>				  
					   </tr>
					    <tr>
					    <td nowrap align="right" width="11%">品类:</td>
					    <td width="27%">
					    <select name="schemeVO.proclassid" class="proclassid" id="proclassid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${sizeStList}" var="bean">
								<option <c:if test='${schemeVO.proclassid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    </tr>
					   <tr>
					  	<td width="100%" colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					  <tr>
					  	<td width="100%" colspan="4" align="left" nowrap>
					  	</td>
					  </tr>
					  <tr>
					    <td  width="100%" colspan="4" nowrap align="left">
					    <!-- 子表数据 -->
					    <div id="tb" style="height:28px;line-height: 28px;">
	                	<span style="float: right;">
	                	<a onclick="javascript:append()" class="menu-btn">【新增】</a>&nbsp;&nbsp;
	                	<a onclick="javascript:removeit()" class="menu-btn">【删除】</a>&nbsp;&nbsp;
	                	</span>
	                	</div>
					    <table id="pttg">
							<thead>
								<tr>
									<th data-options="field:'id',hidden:'true'">id</th>
									<th data-options="field:'schemeid',hidden:'true'">schemeId</th>
									<th data-options="field:'dsmall_purchasemny',width:120,align:'center',editor:{type:'numberbox',options:{precision:2}}" width="15%">最小面料采购单价</th>
									<th data-options="field:'dbig_purchasemny',width:120,align:'center',editor:{type:'numberbox',options:{precision:2}}" width="10%">最大面料采购单价</th>
									<th data-options="field:'daccountmny',width:120,align:'center',editor:{type:'numberbox',options:{precision:2}}" width="10%">面料核算价</th>
									<th data-options="field:'dunitmny',width:120,align:'center',editor:{type:'numberbox',options:{precision:2}}" width="10%">面料单耗(单位米)</th>
									<th data-options="field:'dcoefficientmny',width:120,align:'center',editor:{type:'numberbox',options:{precision:2}}" width="10%">面料系数</th>
									<th data-options="field:'basedocid',width:100,
									formatter:function(value,row){
												return row.basedocName;
											  },
									editor:{
										type:'combobox',
										options:{
											valueField:'id',
											textField:'vname',
											method:'get',
											url:'${pageContext.request.contextPath}/sch!getProcessComb',
											required:true
										}
									}
									">工艺名称</th>
									<th data-options="field:'dprocefeemny',width:120,align:'center',editor:{type:'numberbox',options:{precision:2}}" width="15%">工艺加工费(单位元)</th>
									<th data-options="field:'dprocecoefmny',width:120,align:'center',editor:{type:'numberbox',options:{precision:2}}" width="10%">工艺加工系数</th>
									<th data-options="field:'ddiscountmny',width:120,align:'center',editor:{type:'numberbox',options:{precision:2}}" width="10%">折扣(%)</th>
								</tr>
							</thead>
					</table>
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
