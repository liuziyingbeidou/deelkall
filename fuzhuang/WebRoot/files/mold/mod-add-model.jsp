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
		toolbar:'#tb',
		singleSelect:true,
		collapsible:true,
		remoteSort:false,
		url:'${pageContext.request.contextPath}/mold!getModelInfoJson?modelid=${modelVO.id }',
		method:'get',
		pagination:false,
		onDblClickRow:
	           function () {
	               //单击行的时候，将单选按钮设置为选中
	               var selectRow = $('#pttg').datagrid("getSelected");
	               edit(selectRow.id);
	           }
	});  
	
	$(window).resize(function () {
        $('#pttg').datagrid('resize', {
        	width: function(){return document.body.clientWidth;}
        }).datagrid('resize', {
        	width: function(){return document.body.clientWidth;}
        });
 
	});
});

//选择特殊档案添加到子表
function appendToChild(datajson){
	//将返回的数据格式成JSON对象
	var rows = jQuery.parseJSON(datajson);
	var len = rows.length;
	//var rnum = getChildRows();
	var index = 0;
	
	/*	if(rnum != "" && rnum != null){
		index = rnum.length;
	}*/
	$('#pttg').datagrid('loadData',{total:0,rows:[]});
	for(var i = 1; i <= len; i++){
		$('#pttg').datagrid('insertRow', {
			index:i,
			row:rows[i-1]
		});
	}
}

//获得表体所有数据
function getChildRows(){
	return $('#pttg').datagrid('getRows');
}
	
//删除
function del(){
	if(confirm('确定要删除?')){
		var index = $('#pttg').datagrid('getRowIndex',$('#pttg').datagrid('getSelected'));
		$('#pttg').datagrid('deleteRow', index);
	}
}
	
function openScript(url, width, height)
{
var left = (screen.width-width)/2;
var top = (screen.height-height)/8;

var Win = window.open(url,"openScript",'width=' + width + ',height=' + height + ',top='+top+', left='+left+',resizable=1,scrollbars=yes,menubar=no,status=yes' );
}

function openem()
{ 
	var loc = $("#vfolder").find("option:selected").attr("loc");
	if(loc == undefined){
		alert("请选择存储位置!");
		return ;
	}
	openScript('./mold/upload.jsp?loc='+loc,390,270); 
}

//关联
function append(){
	openScript('./mold/mod-refer-special.jsp',650,520); 
}

function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['modelVO.vcode'].value == "") {
				errMsg = "编号为空";
				setfocus = "['modelVO.vcode']";
			}else if (theForm['modelVO.vname'].value == "") {
				errMsg = "名称为空";
				setfocus = "['modelVO.vname']";
			}
		if (errMsg != "") {
			alert(errMsg);
			eval("theForm" + setfocus + ".focus()");
		} else
			theForm.submit();	
}

//保存
function save(){
	//子表数据	
	var sub= $('#pttg').datagrid('getRows');
	var subdata = JSON.stringify(sub); 
	//alert(subdata);
	$.ajax({
	     type : "POST",
	     url : "mold/mold!saveModel",
	     data:$('#modelfrm').serialize()+"&subdata="+subdata,
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
  <form id="modelfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>模型</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">

		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>模型</legend>
				<input name="modelVO.id" type="hidden" value="${modelVO.id }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">模型分类:</td>
					    <td width="27%">
					    <select name="modelVO.typeid" class="typeid" id="typeid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${moldTypeList}" var="bean">
								<option <c:if test='${modelVO.typeid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					    <td nowrap align="right" width="11%">存储位置:</td>
					    <td width="27%">
					    <select name="modelVO.folderid" class="vfolder" id="vfolder">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${moldFolderList}" var="bean">
								<option <c:if test='${modelVO.folderid==bean.id}'>selected="selected"</c:if> loc="${bean.vname }"  value="${bean.id}">${bean.proclassName }</option>
							</c:forEach>
						</select>
					    </td>
					   </tr>
					    <tr>
					    <td nowrap align="right" width="11%">模型名称:</td>
					    <td width="27%"><input name='modelVO.vname' id="modlevname" type="text" class="text" style="width:154px" value="${modelVO.vname }" /></td>				  
					    <td align="right" width="10%">模型文件:</td>
					    <td width="31%" rowspan="1">
					    <input name="modelVO.vfileupload" id="filepath" value="${modelVO.vfileupload }" type="text" class="text"  readonly="readonly" size="20" maxlength="200">
	                    [<a href="JavaScript:openem()">上传文件</a> ]
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
	                	<a onclick="javascript:append()" class="menu-btn">【关联】</a>&nbsp;&nbsp;
	                	<a onclick="javascript:del()" class="menu-btn">【删除】</a>&nbsp;&nbsp;
	                	</span>
	                	</div>
					    <table id="pttg">
							<thead>
								<tr>
									<th data-options="field:'id',hidden:'true'">id</th>
									<th data-options="field:'specialid',hidden:'true'">specialid</th>
									<th data-options="field:'vlinktype',hidden:'true'">关联类型</th>
									<th data-options="field:'vspecialTypeName',width:120,align:'center'" width="25%">档案类别</th>
									<th data-options="field:'vspecialName',width:90,align:'center'" width="15%">档案名称</th>
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
