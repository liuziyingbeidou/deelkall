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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
function openScript(url, width, height)
{
var left = (screen.width-width)/2;
var top = (screen.height-height)/8;

var Win = window.open(url,"openScript",'width=' + width + ',height=' + height + ',top='+top+', left='+left+',resizable=1,scrollbars=yes,menubar=no,status=yes' );
}

function openem()
{ 
	openScript('./size/upload.jsp',390,270); 
}

function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['sizeDocVO.vcode'].value == "") {
				errMsg = "编号为空";
				setfocus = "['sizeDocVO.vcode']";
			}else if (theForm['sizeDocVO.vname'].value == "") {
				errMsg = "名称为空";
				setfocus = "['sizeDocVO.vname']";
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
	$.ajax({
	     type : "POST",
	     url : "size/size!saveSizeDoc",
	     data:$('#sizedocfrm').serialize(),
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

$(function(){
	var bisupload = $("input[name='sizeDocVO.bisupload']:checked").val();
	if(bisupload == "1"){//有图
		$(".tupian").show();
	}else{//无图
		$(".tupian").hide();
	}
	$("input[name='sizeDocVO.bisupload']").click(function(){
		var bisupload = $(this).val();
		if(bisupload == "1"){//有图
			$(".tupian").show();
		}else if(bisupload == "0"){//无图
			$(".tupian").hide();
		}
	});
});

</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
select{width:152px;}
.vmaterials{color:gray;}
-->
</style>
</head>
<body class="ContentBody" >
  <form id="sizedocfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>${docname }</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">

		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>${docname }</legend>
				<input name="sizeDocVO.id" type="hidden" value="${sizeDocVO.id }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">档案类型:</td>
					    <td width="27%">
					    <select name="sizeDocVO.doctypeid" class="doctypeid" onChange="javascript:changedoctype();" id="doctypeid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${doctypelist}" var="bean">
								<option <c:if test='${sizeDocVO.doctypeid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					  </tr>
					  <tr>
					    <td nowrap align="right" width="11%">编码:</td>
					    <td width="27%"><input name='sizeDocVO.vcode' id="vcodeid" maxlength="3" type="text" class="text" style="width:154px" value="${sizeDocVO.vcode }" /></td>
					  </tr>
					  <tr>
					    <td nowrap align="right" width="11%">名称:</td>
					    <td width="27%"><input name='sizeDocVO.vname' id="vnameid" type="text" class="text" style="width:154px" value="${sizeDocVO.vname }" /></td>
					    </tr>
					    <!--<tr>
					    <td nowrap align="right" width="11%">是否默认数据:</td>
					    <td width="35%">
					    <input name="sizeDocVO.bisdefault" type="radio" <c:if test='${sizeDocVO.bisdefault==1}'>checked</c:if> value="1">是
					    <input name="sizeDocVO.bisdefault" type="radio" <c:if test='${sizeDocVO.bisdefault==0}'>checked</c:if> value="0">否
					    </td>
					    </tr>
					    -->
					    <tr>
					    <td nowrap align="right" width="11%">是否有图:</td>
					    <td width="35%">
					    <input name="sizeDocVO.bisupload" type="radio" <c:if test='${sizeDocVO.bisupload==1}'>checked</c:if> value="1">是
					    <input name="sizeDocVO.bisupload" type="radio" <c:if test='${sizeDocVO.bisupload==0}'>checked</c:if> value="0">否
					    </td>
					    </tr>
					  <tr class="tupian">
					  <td align="right" width="10%">图片:</td>
					    <td width="31%" rowspan="1">
					    <input name="sizeDocVO.vfileupload" id="filepath" value="${sizeDocVO.vfileupload }" type="text" class="text"  readonly="readonly" size="20" maxlength="200">
	                    [<a href="JavaScript:openem()">上传图片</a> ]
					    </td>
					  </tr>
					  <tr class="tupian"><td></td><td><font color="red">*只允许上传.jpg、.jpeg和.gif类型图片文件</font></td></tr>
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
