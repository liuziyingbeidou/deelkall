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
		url:'${pageContext.request.contextPath}/size!getStandardInfoJson?stinfoId=${stinfoVO.id}',
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
				/*var ed = $('#pttg').datagrid('getEditor', {index:editIndex,field:'productid'});
				var productname = $(ed.target).combobox('getText');
				$('#pttg').datagrid('getRows')[editIndex]['productname'] = productname;*/
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
				$('#pttg').datagrid('appendRow',{bisadjust:'1'});
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
		if (theForm['stinfoVO.proclassid'].value == "-1") {
				errMsg = "请选择品类";
				setfocus = "['stinfoVO.proclassid']";
			}else if (theForm['stinfoVO.specid'].value == "") {
				errMsg = "版型为空";
				setfocus = "['stinfoVO.specid']";
			}else if (theForm['stinfoVO.vsize'].value == "") {
				errMsg = "尺码为空";
				setfocus = "['stinfoVO.vsize']";
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
		return;
	}
	endEditing();
	//子表数据	
	var sub= $('#pttg').datagrid('getRows');
	var subdata = JSON.stringify(sub); 
	//alert(subdata);
	$.ajax({
	     type : "POST",
	     url : "size/size!saveSizeStandard",
	     data:$('#sizefrm').serialize()+"&subdata="+subdata,
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
//根据品类加载版型
function changeproclass(){
	$('#specid').empty();
	$('#specid').append("<option value='-1'>==请选择==</option>");
	$('#spec_qpid').empty();
	$('#spec_qpid').append("<option value='-1'>==请选择==</option>");
	$('#spec_xkid').empty();
	$('#spec_xkid').append("<option value='-1'>==请选择==</option>");
	$.ajax({
		type:"GET",
		url:'${pageContext.request.contextPath}/size/size!getBxByPro',
		data:{"proclassid":$('#proclassid').val()},
		success:function(row){
			var data_specbx=row.specbx;//版型
			var data_specqp=row.specqp;//前片
			var data_specxk=row.specxk;//下口
			for(var i=0;i<data_specbx.length;i++){
				$('#specid').append("<option value='"+data_specbx[i].id+"'>"+data_specbx[i].vname+"</option>");
			}
			for(var i=0;i<data_specqp.length;i++){
				$('#spec_qpid').append("<option value='"+data_specqp[i].id+"'>"+data_specqp[i].vname+"</option>");
			}
			for(var i=0;i<data_specxk.length;i++){
				$('#spec_xkid').append("<option value='"+data_specxk[i].id+"'>"+data_specxk[i].vname+"</option>");
			}
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
  <form id="sizefrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>标准尺码</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">

		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>标准尺码</legend>
				<input name="stinfoVO.id" type="hidden" value="${stinfoVO.id }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">品类:</td>
					    <td width="27%">
					    <select name="stinfoVO.proclassid" class="proclassid" onChange="javascript:changeproclass();" id="proclassid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${sizeStList}" var="bean">
								<option <c:if test='${stinfoVO.proclassid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">版型:</td>
					    <td width="27%">
					    <select name="stinfoVO.specid" class="specid" id="specid" >
					    <c:forEach items="${bxList}" var="bean">
								<option <c:if test='${stinfoVO.specid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>				  
					   </tr>
					   <tr>
					    <td nowrap align="right" width="11%">前片:</td>
					    <td width="27%">
					    <select name="stinfoVO.spec_qpid" class="spec_qpid" id="spec_qpid" >
					    <c:forEach items="${qpList}" var="bean">
								<option <c:if test='${stinfoVO.spec_qpid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">下口:</td>
					    <td width="27%">
					    <select name="stinfoVO.spec_xkid" class="spec_xkid" id="spec_xkid" >
					    <c:forEach items="${xkList}" var="bean">
								<option <c:if test='${stinfoVO.spec_xkid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					   </tr>
					    <tr>
					    <td nowrap align="right" width="11%">尺码:</td>
					    <td width="27%"><input name='stinfoVO.vsize' id="vcodeid" maxlength="3" type="text" class="text" style="width:154px" value="${stinfoVO.vsize }" /></td>				  
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
									<th data-options="field:'stinfoId',hidden:'true'">stinfoId</th>
									<th data-options="field:'vcode',width:120,align:'center',editor:'textbox'" width="15%">编码</th>
									<th data-options="field:'vname',width:120,align:'center',editor:'textbox'" width="25%">名称</th>
									<th data-options="field:'standarmny',width:90,align:'center',editor:'textbox'" width="15%">标码数</th>
									<th data-options="field:'bisadjust',width:80,align:'center',editor:{type:'checkbox',options:{on:'1',off:'0'}}" width="10%">是否可调</th>
									<th data-options="field:'downmny',width:120,align:'center',editor:{type:'numberbox',options:{precision:1}}" width="15%">上浮</th>
									<th data-options="field:'upmny',width:120,align:'center',editor:{type:'numberbox',options:{precision:1}}" width="15%">下浮</th>
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
