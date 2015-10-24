<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>服装管理系统</title>
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
<script type="text/JavaScript">

</script>
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
}
-->
</style>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
</head>
<SCRIPT language=JavaScript>
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


	$(".deviceType").click(function(){
	   gridload('pttg');
	});
	
	$(".bisgyxj").click(function(){
	   gridload('pttg');
	});
	
	$(".bisbom").click(function(){
	   gridload('pttg');
	});

	function gridload(emid){
		var searchinput = $("input[name='search_cont']").val().trim();
		var bisgyxj = $("input[name='bisgyxj']:checked").val();
		var deviceType = $("input[name='deviceType']:checked").val();
		var bisbom = $("input[name='bisbom']:checked").val();
		$('#'+emid).datagrid('load',{
			searchinput:searchinput,
			bisgyxj:bisgyxj,
			bisbom:bisbom,
			deviceType:deviceType
		});
	}

	// 数据表格
	$('#pttg').datagrid({
		//title:'面料',
		rownumbers:true,
		checkbox:true,
		height:650,
		toolbar:'#tb',
		singleSelect:true,
		collapsible:true,
		remoteSort:false,
		url:'${pageContext.request.contextPath}/part!getSubPartJson',
		method:'get',
		pagination:true,
		onDblClickRow:
	           function () {
	               //单击行的时候，将单选按钮设置为选中
	               var selectRow = $('#pttg').datagrid("getSelected");
	               edit(selectRow.id);
	           },
	    onLoadSuccess:
	    	function(data){ 
	    		MergeCells('pttg','proclassName');
	    	}
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

function  htgl(val,row){
		var tocss = ' <div style="text-align:center;margin:0 auto;" > '
						+'<a class="col0070c0" href="#" onclick="del('+row.id+')">删除</a>'
					+'</div>';
		
		return tocss ;
	}	
	
//重新加载datagrid
function reloadGD(){
	$("#pttg").datagrid('reload');  
}
	
//新增
function add(){
	var url = "part!addSubPart?type=add&";//"basedoc/addcolour.jsp";
	winOpen(url,865,430);
}

//修改
function edit(id){
	var url = "part!addSubPart?id="+id+"&type=edit&";//"basedoc/addcolour.jsp";
	winOpen(url,865,430);
}

//删除
function del(id){
	if(confirm('确定要删除?')){
		//method!delcolour?id=${bean.id}
		$.ajax({
	     type : "POST",
	     url : "part!delSubPart?id="+id,
	     async:false,
	     cache:false,
	     success : function(msg) {
	    	alert(msg);
	    	reloadGD();
	     },
	     error : function(e) {
	     	alert("删除失败");
	     }
	    });
	}
}
</SCRIPT>

<body>
<form name="fom" id="fom" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="62" background="../images/nav04.gif">
          
		   <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
			<td style="display: none" width="24"><img src="../images/ico07.gif" width="20" height="18" /></td>
			<td width="519"></td>	
		  </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table id="subtree1" style="DISPLAY: " width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="40" class="font42">
                <!-- 表格界面 -->
                	<div id="tb" style="height:28px;line-height: 28px;">
                	<span style="font-size:15px;font-weight: bold">&nbsp;子部件列表</span>
                	<span>
                	&nbsp;&nbsp;
                	<input id="search_input" placeholder="品类/名称" name="search_cont" type="text">
                	&nbsp;&nbsp;
                	<span style="vertical-align: center">
		           	手机端：  <input type="radio" class="deviceType" name="deviceType" value="all" checked />全部
		                	<input type="radio" class="deviceType" name="deviceType" value="yes" />是
		            &nbsp;&nbsp;
		           	是否细节工艺：  <input type="radio" class="bisgyxj" name=bisgyxj value="all" checked />全部
		                	<input type="radio" class="bisgyxj" name="bisgyxj" value="yes" />是
		                	<input type="radio" class="bisgyxj" name="bisgyxj" value="no" />否
		             &nbsp;&nbsp;
		           	 是否BOM信息：<input type="radio" class="bisbom" name="bisbom" value="all" checked />全部
		                	<input type="radio" class="bisbom" name="bisbom" value="yes" />是
		                	<input type="radio" class="bisbom" name="bisbom" value="no" />否
		           &nbsp;&nbsp;
                	</span>
                	</span>
                	<span style="float: right;"><a onclick="javascript:add()" class="menu-btn">【新增】</a>&nbsp;&nbsp;</span>
                	</div>
					<table id="pttg">
							<thead>
								<tr>
									<th data-options="field:'id',hidden:'true'">id</th>
									<th data-options="field:'proclassName',width:120,align:'center'" width="18%">所属品类</th>
									<th data-options="field:'vcode',width:120,align:'center'" width="10%">编码</th>
									<th data-options="field:'vname',width:120,align:'center'" width="20%">名称</th>
									<th data-options="field:'vsname',width:120,align:'center'" width="10%">简称</th>
									<th data-options="field:'isort',width:120,align:'center'" width="5%">排序</th>
									<th data-options="field:'vdef1',width:120,align:'center'" width="10%">数据流向</th>
									<th data-options="field:'action',formatter:htgl,width:100,align:'center'" width='10%'>操作</th>
								</tr>
							</thead>
					</table>
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="6"><img src="../images/spacer.gif" width="1" height="1" /></td>
        </tr>
        <tr>
          <td height="33">
          ${pagerinfo }
          </td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
