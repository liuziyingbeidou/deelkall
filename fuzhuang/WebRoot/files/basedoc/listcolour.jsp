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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
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

//新增
function add(){
	var url = "basedoc!addColour?type=add&";//"basedoc/addcolour.jsp";
	winOpen(url);
}

//修改
function edit(id){
	var url = "basedoc!addColour?id="+id+"&type=edit&";//"basedoc/addcolour.jsp";
	winOpen(url);
}

//删除
function del(id){
	if(confirm('确定要删除?')){
		//method!delcolour?id=${bean.id}
		$.ajax({
	     type : "POST",
	     url : "basedoc!delColour?id="+id,
	     async:false,
	     cache:false,
	     success : function(msg) {
	    	alert(msg);
	    	window.location.reload();
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
          	 <tr style="display: none">
               <td height="20"><span class="newfont07">选择：<a href="#" class="right-font08" onclick="selectAll();">全选</a>-<a href="#" class="right-font08" onclick="unselectAll();">反选</a></span>
		           <input name="Submit" type="button" class="right-button08" value="删除所选类型" />
				   <input name="button" type="button" class="right-button08" value="添加记录" onclick="javascript:location.href='method!addshop'"/>
	              </td>
          </tr>
              <tr>
                <td height="40" class="font42"><table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#464646" class="newfont03">

					                  <tr>
                    <td height="20" colspan="9" align="center" bgcolor="#EEEEEE"class="tablestyle_title">色系信息列表 &nbsp;
                    <span class="menu"> 
                    	【<a onclick="javascript:add()" class="menu-btn">新增</a>】 &nbsp;
                    </span>
                    </td>
                    </tr>
                  <tr>
				    <td width="4%" align="center" bgcolor="#EEEEEE">序号</td>
                    <td width="10%" height="20" align="center" bgcolor="#EEEEEE">编号</td>
                    <td width="25%" align="center" bgcolor="#EEEEEE">名称</td>
                    <td width="" align="center" bgcolor="#EEEEEE">备注</td>
                    <td width="20%" align="center" bgcolor="#EEEEEE">操作</td>
                  </tr>
                  <c:forEach items="${list}" var="bean" varStatus="status">
                  <tr>
				    <td bgcolor="#FFFFFF"><div align="center">
				      ${status.index + 1 }
				      </div></td>
                    <td height="20" bgcolor="#FFFFFF"><div align="center">${bean.vcode}</div></td>
					<td height="20" bgcolor="#FFFFFF" align="center"><div align="center" class="STYLE1">${bean.vname}</div></td>
                    <td bgcolor="#FFFFFF"><div align="center">${bean.vmemo}</div></td>
                    <td bgcolor="#FFFFFF"><div align="center"><a onclick="javasctipt:edit(${bean.id})" href="javascript:void()">编辑</a> | <a onclick="javasctipt:del(${bean.id})" href="javascript:void()">删除</a></div></td>
                  </tr>
                  </c:forEach>
                </table></td>
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
