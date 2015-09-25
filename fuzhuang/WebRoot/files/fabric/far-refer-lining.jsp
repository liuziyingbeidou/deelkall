<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String type = request.getParameter("type");
String id = request.getParameter("id");
String nameId = request.getParameter("nameId");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>参照辅料档案</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/themes/gray/easyui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/blbasejs.js"></script>

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.tabfont01 {	
	font-family: "宋体";
	font-size: 9px;
	color: #555555;
	text-decoration: none;
	text-align: center;
}
.font051 {font-family: "宋体";
	font-size: 12px;
	color: #333333;
	text-decoration: none;
	line-height: 20px;
}
.font201 {font-family: "宋体";
	font-size: 12px;
	color: #FF0000;
	text-decoration: none;
}
.button {
	font-family: "宋体";
	font-size: 14px;
	height: 37px;
}
html { overflow-x: auto; overflow-y: auto; border:0;} 
-->
</style>

<link href="${pageContext.request.contextPath}/css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
.menu{
	font-family: "宋体";
	font-size:14px;
	float:right;
}
.menu-btn{
	cursor: pointer;
	font-size:14px;
	font-weight: bold
}
#tb{
	
}
-->
</style>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT type="text/javascript">
$(function(){
	$("#search_input").keyup(function(){
		 delay(function(){
			 gridload('pttg');
		 }, 500 );
	});
	
	var delay = (function(){
		 var timer = 0;
		 return function(callback, ms){
			 clearTimeout (timer);
			 timer = setTimeout(callback, ms);
		 };
	})();

	$(".bisupload").click(function(){
	   gridload('pttg');
	});
	
	$(".bisdefault").click(function(){
	   gridload('pttg');
	});

	function gridload(emid){
		var searchinput = $("input[name='search_cont']").val().trim();
		var bisupload = $("input[name='bisupload']:checked").val();
		var bisdefault = $("input[name='bisdefault']:checked").val();
		$('#'+emid).datagrid('load',{
			searchinput:searchinput,
			bisupload:bisupload,
			bisdefault:bisdefault
		});
	}

	// 数据表格
	$('#pttg').datagrid({
		rownumbers:true,
		checkbox:true,
		height:260,
		toolbar:'#tb',
		singleSelect:true,
		collapsible:true,
		remoteSort:false,
		url:'${pageContext.request.contextPath}/lining!getLiningJson',
		method:'get',
		pagination:true
	});  
	
	var table = $('#pttg').datagrid('getPager'); 
	//$('#pttg').sortBy("dthisdate", "desc"); 
	$(table).pagination({ 
		layout:['list','sep','first','prev','links','next','last','sep','refresh']
	}); 
	
	$(window).resize(function () {
        $('#pttg').datagrid('resize', {
        	width: function(){return document.body.clientWidth;}
        }).datagrid('resize', {
        	width: function(){return document.body.clientWidth;}
        });
 
	});
});
//确定，将选择关联的数据回写到父页面
function sure(){
	var $dg = $('#pttg');
	var row= $dg.datagrid('getSelected');
	var selId = "";
	var selName = "";
	var selCode = "";
	
	if(row){
		selId = row.id;
		selName = row.vname;
		selCode = row.vcode;
	}
	
	if(selId != ""){
		opener.document.getElementById('<%=id%>').value=selId;
	}
	if(selName != ""){
		opener.document.getElementById('<%=nameId%>-text').innerText=selCode + ":" + selName;
		opener.document.getElementById('<%=nameId%>').value=selCode + ":" + selName;
	}
	window.close();
}
</SCRIPT>
</head>
<body>
<form name="fom" id="fom" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <table id="subtree1" style="DISPLAY: " width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="40" class="font42">
                	<!-- 表格界面 -->
                	<div id="tb" style="height:28px;line-height: 28px;">
                	<span style="font-size:15px;font-weight: bold">&nbsp;里料信息列表</span>
                	<span>
                	&nbsp;&nbsp;
                	<input id="search_input" placeholder="里料编码" name="search_cont" type="text">
                	&nbsp;&nbsp;
                	<span style="vertical-align: center">
                	是否有图：<input type="radio" class="bisupload" name="bisupload" value="all" checked />全部
		                	<input type="radio" class="bisupload" name="bisupload" value="yes" />有
		                	<input type="radio" class="bisupload" name="bisupload" value="no" />无
		            &nbsp;&nbsp;&nbsp;&nbsp;
		                                是否默认数据：<input type="radio" class="bisdefault" name="bisdefault" value="-1" />全部
		                	<input type="radio" class="bisdefault" name="bisdefault" value="1" />是
                	</span>
                	</span>
                	<span style="float: right;"><a onclick="javascript:sure()" class="menu-btn">【确定】</a>&nbsp;&nbsp;</span>
                	</div>
					<table id="pttg">
							<thead>
								<tr>
									<th data-options="field:'id',hidden:'true'">id</th>
									<th data-options="field:'vcode',width:120,align:'center'" width="20%">编码</th>
									<th data-options="field:'vname',width:120,align:'center'" width="20%">名称</th>
									<th data-options="field:'patternName',width:120,align:'center'" width="20%">花型</th>
									<th data-options="field:'ingredientName',width:120,align:'center'" width="20%">成份</th>
									<th data-options="field:'colourName',width:120,align:'center'" width="10%">颜色</th>
								</tr>
							</thead>
					</table>
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
