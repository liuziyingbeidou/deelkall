<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>参照</title>

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
.btn-top{position:fixed; top:0; left:0;width:100%;height:35px;background-color:#F8F8FF;text-align:center;line-height:35px;}
-->
</style>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT type="text/javascript">
$(function(){
	
	// 数据树
	$('#tt').tree({
		url:'${pageContext.request.contextPath}/special!getSpecialJsonData',
		method:'get',
		animate:true,
		checkbox:true,
		onlyLeafCheck:true,
		onBeforeExpand:function(node){
                $("#tt").tree('options').url='';
                temp=node;
                var children=$('#tt').tree('getChildren',temp.target);
                if(children == ""){
                	//alert("该节点下无数据!");
                }
            },
        formatter:function(node){//增加条数显示
						var s = node.text;
						if (node.children){
							s += '&nbsp;<span style=\'color:blue\'>(' + node.children.length + ')</span>';
						}else{
							s += '&nbsp;<span style=\'color:blue\'>(' + 0 + ')</span>';
						}
						return s;
					},
		onLoadSuccess:function(node,data){
			setSelect();
		}
	});  

});
		//全部折叠
		function collapseAll(){
			$('#tt').tree('collapseAll');
		}
		//全部展开
		function expandAll(){
			$('#tt').tree('expandAll');
		}
		
		//获得选择的数据
		function getChecked_Data(){
			var nodes = $('#tt').tree('getChecked');
			var type = "mod_special";
			var s = '[';
			for(var i=0; i<nodes.length; i++){
				if (s != ''){
					var id = nodes[i].id;
					var node=$('#tt').tree('find',id);
					var o=$('#tt').tree('getParent',node.target);
					
					s += '{';
					if(o != null){
						/*s+="specialid:";
						s+=o.id.split('-')[1];;
						s += ',';*/
						s+="\"vspecialTypeName\":";
						s+="\""+o.text+"\"";
						s += ',';
						if(o.text == "纽扣"){
							type = "mod_accessories";
							id = (id.toString()).substring(3,(id.toString()).length);
						}else{
							type = "mod_special";
						}
						s+="\"vlinktype\":";
						s+="\""+type+"\"";
						s += ',';
					}
					s+="\"specialid\":";
					s+="\""+id+"\"";//77720保存真正id
					s += ',';
					s+="\"vspecialName\":";
					s+="\""+nodes[i].text+"\"";
					s += '},';
				};
			}
			s=s.substring(0, s.length-1);
			s=s+"]";
			return s;
		}

//确定，将选择关联的数据回写到父页面
function getChecked(){
	var datajson = getChecked_Data();
	window.opener.appendToChild(datajson);
	window.close();
}
//设置已有的数据
function setSelect(){
	Travel("tt");
}
//判断是含有
function isHave(rows,parentName,id){
	var len = rows.length;
	for(var i =0; i < len; i++){
		var specialid = rows[i].specialid;
		var type = rows[i].vlinktype;
		//辅料
		if(type == "mod_accessories"){
			if(parentName == "纽扣"){
	     		specialid = "777"+specialid;
	     		if(id == specialid){
					return true;
				}
	     	}
		}else{//特殊档案
			if(id == specialid){
				return true;
			}
		}
	}
}
//遍历树
function Travel(treeID){//参数为树的ID，注意不要添加#
   var rows = window.opener.getChildRows();
   if(rows == null || rows == "") return;
   var parentName;
   var roots=$('#'+treeID).tree('getRoots'),children,i,j;
   for(i=0;i<roots.length;i++){
     parentName = roots[i].text;//父节点名称
     children=$('#'+treeID).tree('getChildren',roots[i].target);
     for(j=0;j<children.length;j++){
     	var childId = children[j].id;
     	if(isHave(rows,parentName,childId)){
     		var node=$('#tt').tree('find',childId);
     		$('#tt').tree('check',node.target);
     		$('#tt').tree('expand',roots[i].target);
     	}
     }
   }
} 
</SCRIPT>
</head>
<body>
<form name="fom" id="fom" method="post">
<div class="btn-top">
		<a href="#" class="easyui-linkbutton" onClick="collapseAll()">全部折叠</a>
		<a href="#" class="easyui-linkbutton" onClick="expandAll()">全部展开</a>
		<a href="#" class="easyui-linkbutton" onClick="getChecked()">确定</a>
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <table id="subtree1" style="DISPLAY: " width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
          	  <tr><td height="40" class="font42"></td></tr>
              <tr>
              <td height="30" class="font42">
					请选择与上传模型相关联的的属性条件
              </td>
              </tr>
              <tr>
                <td height="40" class="font42">
				<div class="easyui-panel" style="padding:5px">
					<ul id="tt" class="easyui-tree"></ul>
				</div>
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
