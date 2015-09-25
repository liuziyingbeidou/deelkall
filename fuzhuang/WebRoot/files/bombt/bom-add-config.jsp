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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
		url:'${pageContext.request.contextPath}/bom!getConfigInfoJson?btcId=${btcconfigVO.id}',
		method:'get',
		pagination:false,
		onClickRow: onClickRow
	});
	
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
				/*var ed = $('#pttg').datagrid('getEditor', {index:editIndex,field:'basedocid'});
				var basedocname = $(ed.target).combobox('getText');
				$('#pttg').datagrid('getRows')[editIndex]['basedocName'] = basedocname;*/
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
				$('#pttg').datagrid('appendRow',{});
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
		if (theForm['btcconfigVO.proclassid'].value == "-1") {
				errMsg = "品类为空";
				setfocus = "['btcconfigVO.proclassid']";
		}
		if (errMsg != "") {
			alert(errMsg);
			eval("theForm" + setfocus + ".focus()");
		} else
			theForm.submit();	
}

//保存
function save(){
	endEditing();
	//子表数据	
	var sub= $('#pttg').datagrid('getRows');
	var configdata = JSON.stringify(sub); 
	//alert(subdata);
	$.ajax({
	     type : "POST",
	     url : "bombt/bom!saveConfig",
	     data:$('#configfrm').serialize()+"&configdata="+configdata,
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
  <form id="configfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>BOM标配表</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>BOM标配表</legend>
				<input name="btcconfigVO.id" type="hidden" value="${btcconfigVO.id }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					    <tr>
					    <td nowrap align="right" width="5%">品类:</td>
					    <td width="27%">
					    <select name="btcconfigVO.proclassid" class="proclassid" id="proclassid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${proList}" var="bean">
								<option <c:if test='${btcconfigVO.proclassid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td nowrap align="right" width="5%">分类码:</td>
					    <td width="27%">
					    <input name='btcconfigVO.vproClass' id="vproClass" type="text" class="text" style="width:154px" value="${btcconfigVO.vproClass }" />
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
									<th data-options="field:'btcId',hidden:'true'">btcId</th>
									<th data-options="field:'vcode',width:120,align:'center',editor:'textbox'" width="15%">材料编码</th>
									<th data-options="field:'vname',width:120,align:'center',editor:'textbox'" width="20%">材料名称</th>
									<th data-options="field:'vsname',width:120,align:'center',editor:'textbox'" width="10%">简码</th>
									<th data-options="field:'vspec',width:120,align:'center',editor:'textbox'" width="10%">规格</th>
									<th data-options="field:'nunitmny',width:120,align:'center',editor:{type:'numberbox',options:{precision:2}}" width="10%">单耗</th>
									<th data-options="field:'vjobnum',width:120,align:'center',editor:'textbox'" width="10%">作业编号</th>
									<th data-options="field:'vserial',width:120,align:'center',editor:'textbox'" width="10%">项次</th>
									<th data-options="field:'vmemo',width:120,align:'center',editor:'textbox'" width="15%">备注</th>
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
