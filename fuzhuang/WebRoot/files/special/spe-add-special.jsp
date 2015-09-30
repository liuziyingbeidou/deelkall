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
	openScript('./special/upload.jsp',390,270); 
}

function openrefer()
{ 
	openScript('./special/spe-refer-special.jsp',650,300); 
}
function check()
{
		var theForm = document.form1;
		var errMsg = "";
		var setfocus = "";
		if (theForm['specialVO.vcode'].value == "") {
				errMsg = "编号为空";
				setfocus = "['specialVO.vcode']";
			}else if (theForm['specialVO.vname'].value == "") {
				errMsg = "名称为空";
				setfocus = "['specialVO.vname']";
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
	     url : "special/special!saveSpeSpecial",
	     data:$('#specialfrm').serialize(),
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
	var bisupload = $("input[name='specialVO.bisupload']:checked").val();
	if(bisupload == "1"){//有图
		$(".tupian").show();
	}else{//无图
		$(".tupian").hide();
	}
	$("input[name='specialVO.bisupload']").click(function(){
		var bisupload = $(this).val();
		if(bisupload == "1"){//有图
			$(".tupian").show();
		}else if(bisupload == "0"){//无图
			$(".tupian").hide();
		}
	});
});

function fielddocvarietyid(){
	var val = $(".docvarietyid option:selected").text();
	if(val == "==选择=="){
		$(".docvarietyname").val("");
	}else{
		$(".docvarietyname").val(val);
	}
}
</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
select{width:152px;}
.vmaterials{color:gray;}
.sign{color:red;}
-->
</style>
</head>
<body class="ContentBody" >
  <form id="specialfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" ><%=type %>特殊档案</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="left">
		</td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend><%=type %>特殊档案</legend>
				<input name="specialVO.id" type="hidden" value="${specialVO.id }">
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					  <tr>
					    <td nowrap align="right" width="11%">档案类别:</td>
					    <td width="27%">
					    <input type="hidden" name="specialVO.docvarietyname" class="docvarietyname" value="${specialVO.docvarietyname }"/>
					    <select name="specialVO.docvarietyid" class="docvarietyid" onchange="javascript:fielddocvarietyid()" id="docvarietyid">
							<option value="-1" selected="selected">==选择==</option>
							<c:forEach items="${spedocvarietyList}" var="bean">
								<option <c:if test='${specialVO.docvarietyid==bean.id}'>selected="selected"</c:if> value="${bean.id}">${bean.vname }</option>
							</c:forEach>
						</select>
					    </td>
					   </tr>
					  <tr>
					    <td nowrap align="right" width="11%">编码:</td>
					    <td width="27%"><input name='specialVO.vcode' id="vcodeid" maxlength="4" type="text" class="text" style="width:154px" value="${specialVO.vcode }" /></td>
					  </tr>
					  <tr>
					    <td nowrap align="right" width="11%">名称:</td>
					    <td width="27%"><input name='specialVO.vname' id="vnameid" type="text" class="text" style="width:154px" value="${specialVO.vname }" /></td>
					  </tr>
					  <tr>
					    <td nowrap align="right" width="11%">简称:</td>
					    <td width="27%"><input name='specialVO.vsname' id="vsname" type="text" class="text" style="width:154px" value="${specialVO.vsname }" /></td>
					  </tr>
					  <tr>
					    <td nowrap align="right" width="11%">坐标位:</td>
					    <td nowrap width="27%"><input name='specialVO.vtransform' id="vtransform" type="text" class="text" style="width:154px" value="${specialVO.vtransform }" />
					    <span class="sign">多值间以"/"隔开</span>
					    </td>
					  </tr>
					    <tr>
					    <td nowrap align="right" width="11%">是否默认数据:</td>
					    <td width="35%">
					    <input name="specialVO.bisdefault" type="radio" <c:if test='${specialVO.bisdefault==1}'>checked</c:if> value="1">是
					    <input name="specialVO.bisdefault" type="radio" <c:if test='${specialVO.bisdefault==0}'>checked</c:if> value="0">否
					    </td>
					    </tr>
					    <tr>
					    <td nowrap align="right" width="11%">是否有图:</td>
					    <td width="35%">
					    <input name="specialVO.bisupload" type="radio" <c:if test='${specialVO.bisupload==1}'>checked</c:if> value="1">是
					    <input name="specialVO.bisupload" type="radio" <c:if test='${specialVO.bisupload==0}'>checked</c:if> value="0">否
					    </td>
					    </tr>
					  <tr class="tupian">
					  <td align="right" width="10%">图片:</td>
					    <td width="31%" rowspan="1">
					    <input name="specialVO.vfileupload" id="filepath" value="${specialVO.vfileupload }" type="text" class="text"  readonly="readonly" size="20" maxlength="200">
	                    [<a href="JavaScript:openem()">上传图片</a> ]
					    </td>
					  </tr>
					  <tr class="tupian"><td></td><td><font color="red">*只允许上传.jpg、.jpeg和.gif类型图片文件</font></td></tr>
					   <tr>
					    <td width="14%" align="right" nowrap>关联档案:</td>
					    <td width="86%" colspan="3">
					    <input type="hidden" id="releids" name="specialVO.releids" value="${specialVO.releids }">
					    <input type="hidden" id="releNames" name="specialVO.releNames" value="${specialVO.releNames }">
					    <span id="releshow">${specialVO.releNames }</span>
					    [<a href="JavaScript:openrefer()">请选择</a> ]
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
