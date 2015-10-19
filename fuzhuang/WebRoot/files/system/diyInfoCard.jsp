<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>服装管理系统</title>
<link rel="stylesheet" rev="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="all" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">

</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
.warn{color:red;}
-->
</style>
</head>
<body class="ContentBody">
  <form id="colourfrm" action="" method="post" name="form1" >
<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" >定制信息详情</th>
  </tr>
  <tr>
    <td class="CPanel">
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<tr><td align="center"><span class="warn">(*)此界面显示内容中含有"\n"将会转成换行显示</span></td></tr>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>时间戳</legend>
				 <!--<input class="readonly text" type="text" style="width:154px" value="${diyInfoVO.ts }">-->
				 <span><pre>${diyInfoVO.ts }</pre></span>
				</fieldset>
			</TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>成品编码</legend>
				 <!--<input class="readonly text" type="text" style="width:154px" value="${diyInfoVO.vcode }">-->
				 <span><pre>${diyInfoVO.vcode }</pre></span>
				</fieldset>
			</TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>产品编码</legend>
				 <!--<input class="readonly text" type="text" style="width:154px" value="${diyInfoVO.prodCode }">-->
				 <span><pre>${diyInfoVO.prodCode }</pre></span>
				</fieldset>
			</TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>产品名称</legend>
				 <!--<input class="readonly text" type="text" style="width:154px" value="${diyInfoVO.prodName }">-->
				 <span><pre>${diyInfoVO.prodName }</pre></span>
				</fieldset>
			</TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>产品类型</legend>
				 <!--<input class="readonly text" type="text" style="width:154px" value="${diyInfoVO.diyType }">-->
				 <span><pre>${diyInfoVO.diyType }</pre></span>
				</fieldset>
			</TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>版型</legend>
				 <!--<input class="readonly text" type="text" style="width:154px" value="${diyInfoVO.formatType }">-->
				 <span><pre>${diyInfoVO.formatType }</pre></span>
				</fieldset>
			</TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>商品单价</legend>
				 <!--<input class="readonly text" type="text" style="width:154px" value="${diyInfoVO.prodPrice }">-->
				 <span><pre>${diyInfoVO.prodPrice }</pre></span>
				</fieldset>
			</TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>定制信息code+名称</legend>
				<!--<input class="easyui-textbox" data-options="multiline:true" value="${diyInfoVO.diyBom }" style="width:100%;height:auto">-->
				<!--<span><pre>${diyInfoVO.diyBom }</pre></span>-->
				<textarea readonly="readonly" rows="10%" cols="200">${diyInfoVO.diyBom }</textarea>
				</fieldset>
			</TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>定制信息中文说明</legend>
				<!--<input class="easyui-textbox" data-options="multiline:true" value="${diyInfoVO.diyBomDesc }" style="width:100%;height:50px">-->
				<!--<span><pre>${diyInfoVO.diyBomDesc }</pre></span>-->
				<textarea readonly="readonly" rows="10%" cols="200">${diyInfoVO.diyBomDesc }</textarea>
				</fieldset>
			</TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>3D图片路径</legend>
				<!--<input class="easyui-textbox" data-options="multiline:true" value="${diyInfoVO.diyImgUrl }" style="width:100%;height:auto">-->
				<!--<span><pre>${diyInfoVO.diyImgUrl }</pre></span>-->
				<textarea readonly="readonly" rows="5%" cols="200">${diyInfoVO.diyImgUrl }</textarea>
				</fieldset>
			</TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>产品尺码信息</legend>
				<!--<input class="easyui-textbox" data-options="multiline:true" value="${diyInfoVO.diySize }" style="width:100%;height:auto">-->
				<!--<span><pre>${diyInfoVO.diySize }</pre></span>-->
				<textarea readonly="readonly" rows="10%" cols="200">${diyInfoVO.diySize }</textarea>
				</fieldset>
			</TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>产品工艺编码</legend>
				<!--<input class="easyui-textbox" data-options="multiline:true" value="${diyInfoVO.diyCraft }" style="width:100%;height:auto">-->
				<!--<span><pre>${diyInfoVO.diyCraft }</pre></span>-->
				<textarea readonly="readonly" rows="10%" cols="200">${diyInfoVO.diyCraft }</textarea>
				</fieldset>
			</TD>
		</TR>
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend>BOM表信息</legend>
				<!--<input class="easyui-textbox" data-options="multiline:true" value="${diyInfoVO.vbom }" style="width:1000px;height:300px">-->
				<!--<span><pre>${diyInfoVO.vbom }</pre></span>-->
				<textarea readonly="readonly" rows="20%" cols="200">${diyInfoVO.vbom }</textarea>
				</fieldset>
			</TD>
		</TR>
		</TABLE>
	 </td>
  </tr>
</TABLE>
</div>
</form>
</body>
</html>
