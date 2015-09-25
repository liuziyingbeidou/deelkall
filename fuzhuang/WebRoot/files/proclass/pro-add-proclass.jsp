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
Integer count = (Integer)application.getAttribute("count");
    if(count==null){
     count=1;
     application.setAttribute("count",count);
    }else{
     count++;
     application.setAttribute("count",count);
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>服装管理系统</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/themes/gray/easyui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" rev="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="all" />
<script type="text/javascript">

$(function(){
	$('#pttg_b').datagrid({
		rownumbers:true,
		checkbox:true,
		height:100,
		singleSelect:true,
		collapsible:true,
		remoteSort:false,
		url:'${pageContext.request.contextPath}/proClass!getMainPartByProClassJson',
		method:'get',
		onLoadSuccess : function() {
             onLoadSubPart();
        }
	}); 
	
	$('#pttg_bb').datagrid({
		rownumbers:true,
		checkbox:true,
		height:100,
		singleSelect:true,
		collapsible:true,
		remoteSort:false,
		url:'${pageContext.request.contextPath}/proClass!getSubPartByProClassJson',
		method:'get'
	}); 
	
});

//根据加载子表选择主部件
function onLoadSubPart(){
	$(".mainpart input[type='checkbox']").each(function(){
		var mainid = $(this).val();
		var $dg = $('#pttg_b');
		var all1 = $dg.datagrid('getData').rows;
		for ( var i = 0; i < all1.length; i++) {
			if (all1[i].masterid == mainid) {
					//Jquery版本兼容
					this.checked = true;
				}
		}
	});
}

function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['proclassVO.vcode'].value == "") {
				errMsg = "编号为空";
				setfocus = "['proclassVO.vcode']";
			}else if (theForm['proclassVO.vname'].value == "") {
				errMsg = "名称为空";
				setfocus = "['proclassVO.vname']";
			}
		if (errMsg != "") {
			alert(errMsg);
			eval("theForm" + setfocus + ".focus()");
			return true;
		} 
}

function save(){
	if(check()){
		return ;
	}
	//主部件子表
	var tb_main_part= $('#pttg_b').datagrid('getRows');
	var mainpartlist = JSON.stringify(tb_main_part); 
	//子部件孙表
	var tb_sub_part= $('#pttg_bb').datagrid('getRows');
	var subpartlist = JSON.stringify(tb_sub_part); 
	$.ajax({
	     type : "POST",
	     url : "proclass/proClass!saveProClass",
	     data:$('#proclassfrm').serialize()+"&mainpartlist="+mainpartlist+"&subpartlist="+subpartlist,
	     async:false,
	     cache:true,
	     success : function(msg) {
	    	 alert(msg+"");
	    	 self.opener.reloadGD();
	     },
	     error : function(e) {
	     	alert("保存失败!");
	     }
	    });
}


//根据总分类加载大品类
function changeClass(){
	var classid = $("input[name='proclassVO.classid']:checked").val();
	$('#subclass').empty();
	$.ajax({
		type:"GET",
		url:'${pageContext.request.contextPath}/proClass/proClass!getProClass',
		data:{"classid":classid},
		success:function(row){
			var data=row.rows;
			for(var i=0;i<data.length;i++){
				$('#subclass').append("<input name='proclassVO.subclassid' type='radio' value='"+data[i].id+"'>"+data[i].vname);
			}
		}
	});
}

//选择主部件时，添加到表pttg_b中
function changeMainPart(em,mainid){
	var bisck = $(em).is(":checked");
	var $dg = $('#pttg_b');
	var flag = true;
	var all1 = $dg.datagrid('getData').rows;
	
	for ( var i = 0; i < all1.length; i++) {
		if (all1[i].masterid == mainid) {
			if(!bisck){
				var bid = all1[i].id;
				if(isHaveGrandchildren(bid)){//有孙表数据
					em.checked = true;
					alert("该主部件已绑定子部件，不可取消！");
				}else{
					//删除该主部件下的关联的子部件
					delSubByMainPart("pttg_bb",mainid);
					//删除对应子表
					var index = $dg.datagrid('getRowIndex',all1[i]);
					$dg.datagrid('deleteRow', index);
				}
			}
			flag = false;
		}
	}
	
	var i = 0;
	if(flag){
		$dg.datagrid('insertRow', {
			index:i++,
			row:{
				"masterid" : mainid
			}
		});
		openSubPart(mainid);
	}
}

//打开子部件页面
function openSubPart(mainid)
{
	if(!$(".mp-"+mainid).is(':checked')){
		alert("请选择主部件...");
		return ;
	};
	$("#sub-mainpart").val(mainid);
	//去除不包括此主部件的其他选择主部件的所有的子部件
	$(".subpart input[type='checkbox']").each(function(){
		var subid = $(this).val();
		var $dg = $('#pttg_bb');
		var all1 = $dg.datagrid('getData').rows;
		for ( var i = 0; i < all1.length; i++) {
			if(all1[i].masterid != mainid){
				if (all1[i].subpartid == subid) {
					$(".mycheckbox-"+subid).hide();
				}
			}else{
				if (all1[i].subpartid == subid) {
				 	//alert($(this).val());
					//$(this).attr("checked",'true');
					//Jquery版本兼容
					this.checked = true;
				}
			}
		}
	});
	$('#w').window('open');
}

//判断子表某条数据是否对应有孙表数据
function isHaveGrandchildren(bid){
	var backValue = false;
	if(bid == null){
		backValue = false;
	}else{
		$.ajax({
		type:"GET",
		url:'${pageContext.request.contextPath}/proClass/proClass!isHaveGrandchildren',
		async:false,
		cache:false,
		data:{"proclass_bid":bid},
		success:function(row){
			var data=row.rows;
			if(data.length > 0){
				backValue = true;
			}else{
				backValue = false;
			}
		},
		error:function(){backValue = false;}  
	});
	}
	return backValue;
}

//子部件取消
function sub_cancel(){
	$('#w').window('close');
	showSubPart();
}

function sub_ok(){
	$('#w').window('close');
	var mainid = $("#sub-mainpart").val();
	//删除该主部件下的关联的子部件
	delSubByMainPart("pttg_bb",mainid);
	
	$(".subpart input:checked").each(function(){
		//alert($(this).val());
		changeSubPart("pttg_bb",mainid,$(this).val());
	});
	showSubPart();
}

//取消所有选择
function noChecked(){
	$(".subpart input:checked").removeAttr("checked");
}

//显示所有的子部件
function showSubPart(){
	$(".subpart input[type='checkbox']").each(function(){
		//alert("显示："+$(this).val());
		$(".mycheckbox-"+$(this).val()).show();
		$(this).removeAttr("checked");
	});
}

//删除该主部件下的关联的子部件
function delSubByMainPart(em,mainid){

	var $dg = $('#'+em);
	var all1 = $dg.datagrid('getData').rows;
	
	for ( var i = 0; i < all1.length; i++) {
		
		if (all1[i].masterid == mainid) {
			var index = $dg.datagrid('getRowIndex',all1[i]);
			//alert("删除："+index);
			$dg.datagrid('deleteRow', index);
			delSubByMainPart(em,mainid);
		}
	}
}

//选择子部件添加到表pttg_bb中
function changeSubPart(em,mainid,subid){
	
	var $dg = $('#'+em);
	var index = $dg.datagrid('getData').rows.length;
	$dg.datagrid('insertRow', {
			index:index,
			row:{
				"masterid" : mainid,
				"subpartid" : subid
			}
		});
}
</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
a{
	cursor: pointer;
}
-->
</style>
</head>
<body class="ContentBody">
  <form id="proclassfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>品类</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>品类</legend>
				<!-- 记录访问次数- -->
				<input name="proclassVO.vdef1" type="hidden" value="${proclassVO.vdef1 }">
				<input name="proclassVO.id" type="hidden" value="${proclassVO.id }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr class="content">
					  	<td width="100%"  colspan="4" align="left" nowrap>所属域:</td>
					  </tr>
					  <tr>
					  <td nowrap align="right" width="14%">总分类:</td>
					    <td width="86%" colspan="3" align="left" nowrap>
					    <c:forEach items="${classlist}" var="bean">
							<input name="proclassVO.classid" type="radio" onclick="javascript:changeClass();" <c:if test='${proclassVO.classid==bean.id}'>checked</c:if> value="${bean.id }">${bean.vname }
						</c:forEach>
					    </td>
					  </tr>
					   <tr>
					  <td nowrap align="right" width="14%">大品类:</td>
					    <td width="86%" colspan="3" align="left" id="subclass" nowrap>
					    <c:forEach items="${subclasslist}" var="bean">
							<input name="proclassVO.subclassid" type="radio" <c:if test='${proclassVO.subclassid==bean.id}'>checked</c:if> value="${bean.id }">${bean.vname }
						</c:forEach>
					    </td>
					  </tr>
					  <tr>
					  	<td width="100%"  colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					  <tr>
					    <td nowrap align="right" width="11%">编号:</td>
					    <td width="27%"><input name='proclassVO.vcode' id="shopcode" type="text" maxlength="3" class="text" style="width:154px" value="${proclassVO.vcode }" /></td>
					    <td nowrap align="right" width="11%">名称:</td>
					    <td width="27%"><input name='proclassVO.vname' id="shopname" type="text" class="text" style="width:154px" value="${proclassVO.vname }" /></td>
					    </tr>
					  <tr>
					  	<td nowrap align="right" width="11%">样板图:</td>
					    <td width="27%"><input name='proclassVO.vfdrawing' id="vfdrawing" type="text" class="text" style="width:154px" value="${proclassVO.vfdrawing }" /></td>
					    <td align="right" width="25%">数据流向:</td>
					    <td width="37%">
					    <input name="proclassVO.deviceType" type="checkbox" <c:if test='${proclassVO.deviceType==1}'>checked</c:if> value="1">手机端
					    </td>
					  </tr>
					  <tr>
					  	<td nowrap align="right" width="11%">排序:</td>
					    <td width="27%"><input name='proclassVO.isort' id="isort" type="text" class="text" style="width:154px" value="${proclassVO.isort }" /></td>
					    <td nowrap align="right" width="11%">简称:</td>
					    <td width="27%"><input name='proclassVO.vsname' id="vsname" type="text" class="text" style="width:154px" value="${proclassVO.vsname }" /></td>
					  </tr>
					  <tr>
					  	<td width="100%"  colspan="4" align="left" nowrap><hr /></td>
					  </tr>
					  <tr>
					  	<td width="100%"  colspan="4" align="left" nowrap>主部件域:</td>
					  </tr>
					  <tr class="content">
					    <td width="100%" class="mainpart" colspan="4" align="left" nowrap>
					    <c:forEach items="${mainpartlist}" var="bean">
							<input name="mainpart" class="mp-${bean.id }" type="checkbox" onclick="javascript:changeMainPart(this,'${bean.id }');" value="${bean.id }"><a onclick="javascript:openSubPart('${bean.id }')">${bean.vname }</a>
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
		<TR>
			<TD colspan="2" align="center" height="50px">
			<input type="button" onclick="javascript:save()" name="Submit" value="保存" class="button"/>　
			<input type="reset" name="Submit2" value="关闭" class="button" onclick="javascript:window.close()"/></TD>
		</TR>
		</TABLE>
</div>
<div style="display: none;">
<table id="pttg_b">
							<thead>
								<tr>
									<th data-options="field:'id',hidden:'true'">id</th>
									<th data-options="field:'proclassid',width:120,align:'center'" width="18%">主表id</th>
									<th data-options="field:'masterid',width:120,align:'center'" width="18%">主部件id</th>
								</tr>
							</thead>
					</table>
<table id="pttg_bb">
							<thead>
								<tr>
									<th data-options="field:'id',hidden:'true'">id</th>
									<th data-options="field:'proclassid',width:120,align:'center'" width="18%">主表id</th>
									<th data-options="field:'proclass_bid',width:120,align:'center'" width="18%">子表id</th>
									<th data-options="field:'masterid',width:120,align:'center'" width="18%">主部件id</th>
									<th data-options="field:'subpartid',width:120,align:'center'" width="18%">子部件id</th>
								</tr>
							</thead>
					</table>
</div>
<div id="w" class="easyui-window" title="子部件域" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:200px;padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<input type="hidden" id="sub-mainpart" value="">
			<div class="subpart" data-options="region:'center'" style="padding:10px;">
				<c:forEach items="${subpartlist}" var="bean">
					<span class="mycheckbox-${bean.id }"><input name="subpart" class="sp" type="checkbox"  onclick="javascript:changeSubPart(this,'${bean.id }');" value="${bean.id }">${bean.vdef1}-${bean.vname }</span>
				</c:forEach>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onClick="javascript:sub_ok()" style="width:80px">Ok</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:sub_cancel()" style="width:80px">Cancel</a>
			</div>
		</div>
</div>
</form>
</body>
</html>
