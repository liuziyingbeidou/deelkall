<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>参照特殊档案</title>

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

	function gridload(emid){
		var searchinput = $("input[name='search_cont']").val().trim();
		var bisupload = $("input[name='bisupload']:checked").val();
		$('#'+emid).datagrid('load',{
			searchinput:searchinput,
			bisupload:bisupload
		});
	}

	// 数据表格
	$('#pttg').datagrid({
		//title:'面料',
		rownumbers:true,
		checkbox:true,
		height:260,
		toolbar:'#tb',
		singleSelect:false,
		collapsible:true,
		remoteSort:false,
		url:'${pageContext.request.contextPath}/special!getSpecialJson',
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
	var allSel= $dg.datagrid('getSelections');
	var selIds = "";
	var selName = "";
	
	for(var i=0;i<allSel.length;i++){
		selIds = selIds + ","+allSel[i].id;
		selName = selName + ","+allSel[i].docvarietyname+":"+allSel[i].vname;
	}
	//alert(opener.document.getElementById("vcodeid").value);
	if(selIds.length > 0){
		opener.document.getElementById("releids").value=selIds.substring(1,selIds.length);
		//$("#releids", opener.document).val();
	}
	if(selName.length > 0){
		opener.document.getElementById("releshow").innerText=selName.substring(1,selName.length);
		opener.document.getElementById("releNames").value=selName.substring(1,selName.length);
		//$("#releshow", opener.document).val(selName.substring(1,selName.length));
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
                	<span style="font-size:15px;font-weight: bold">&nbsp;特殊档案列表</span>
                	<span>
                	&nbsp;&nbsp;
                	<input id="search_input" placeholder="请输入档案类别" name="search_cont" type="text">
                	&nbsp;&nbsp;
                	<span style="vertical-align: center">
                	是否有图：<input type="radio" class="bisupload" name="bisupload" value="all" checked />全部
		                	<input type="radio" class="bisupload" name="bisupload" value="yes" />有
		                	<input type="radio" class="bisupload" name="bisupload" value="no" />无
                	</span>
                	</span>
                	<span style="float: right;"><a onclick="javascript:sure()" class="menu-btn">【确定】</a>&nbsp;&nbsp;</span>
                	</div>
					<table id="pttg">
							<thead>
								<tr>
									<th data-options="field:'id',hidden:'true'">id</th>
									<th data-options="field:'docvarietyname',width:120,align:'center'" width="20%">档案类别</th>
									<th data-options="field:'vcode',width:120,align:'center'" width="20%">编码</th>
									<th data-options="field:'vname',width:120,align:'center'" width="20%">名称</th>
									<th data-options="field:'vdef1',width:120,align:'center'" width="10%">是否有图</th>
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
