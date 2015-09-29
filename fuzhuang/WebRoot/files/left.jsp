<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>服装管理系统</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(../images/left.gif);
}
.subtree-tree{
	padding-top:0px;
	padding-bottom:0px;
	cellpadding:0px;
	cellspacing:0px;
}
.left-table-tree{
	margin-top:0px;
}
-->
</style>
<link href="../css/css.css" rel="stylesheet" type="text/css" />
</head>
<SCRIPT language=JavaScript>
function tupian(idt){
    var nametu="xiaotu"+idt;
    var tp = document.getElementById(nametu);
    tp.src="../images/ico05.gif";//图片ico04为白色的正方形
	
	for(var i=1;i<30;i++)
	{
	  var nametu2="xiaotu"+i;
	  if(i!=idt*1)
	  {
	    var tp2=$('#xiaotu'+i);
		if(tp2!=undefined)
	    {   
	    	if(tp2.attr('class') == "img-tree"){
	    		return;
	    	}
	    	tp2.attr("src","../images/ico06.gif");
	    }//图片ico06为蓝色的正方形
	  }
	}
}

function list(idstr){
	var name1="subtree"+idstr;
	var name2="img"+idstr;
	var objectobj=document.all(name1);
	var imgobj=document.all(name2);
	
	
	//alert(imgobj);
	
	if(objectobj.style.display=="none"){
		for(i=1;i<14;i++){
			var name3="img"+i;
			var name="subtree"+i;
			var o=document.all(name);
			if(o!=undefined){
				o.style.display="none";
				var image=document.all(name3);
				//alert(image);
				image.src="../images/ico04.gif";
			}
		}
		objectobj.style.display="";
		imgobj.src="../images/ico03.gif";
	}
	else{
		objectobj.style.display="none";
		imgobj.src="../images/ico04.gif";
	}
}

function list_tree(idstr){
	var name1="subtree"+idstr;//subtree1-1
	var name2="img"+idstr;//img1-1
	var objectobj=$("#"+name1);
	var imgobj=$("#"+name2);
	
	if(objectobj.is(":hidden")){
		objectobj.show();
		imgobj.attr("src","../images/ico03.gif");
	}
	else{
		objectobj.hide();
		imgobj.attr("src","../images/ico04.gif");
	}
}

</SCRIPT>

<body>
<table width="198" border="0" cellpadding="0" cellspacing="0" class="left-table01">
  <tr>
    <TD>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr>
			<td width="207" height="55" background="../images/nav01.gif">
				<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
				  <tr>
					<td width="25%" rowspan="2"><img src="../images/ico02.gif" width="35" height="35" /></td>
					<td width="75%" height="22" class="left-font01">您好，<span class="left-font02">${user.usercode}</span></td>
				  </tr>
				  <tr>
					<td height="22" class="left-font01">
						[&nbsp;<a href="../method!loginout" target="_top" class="left-font01">退出</a>&nbsp;]</td>
				  </tr>
				</table>
			</td>
		  </tr>
		</table>


	  <!--  服装系统开始    -->
	  <!-- 基础档案开始 -->
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%" height="12"><img name="img1" id="img1" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('1');" >基础档案</a></td>
                </tr>
            </table></td>
          </tr>
      </table>
	  
	  <table id="subtree1" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
		<tr>
          <td width="9%" height="20" ><img id="xiaotu1" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listPattern" target="mainFrame" class="left-font03" onClick="tupian('1');">花型档案</a></td>
        </tr>
        
        <tr style="display: none">
          <td width="9%" height="20" ><img id="xiaotu2" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="method!listingredient" target="mainFrame" class="left-font03" onClick="tupian('2');">成分档案</a></td>
        </tr>
        
         <tr>
          <td width="9%" height="20" ><img id="xiaotu3" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listColour" target="mainFrame" class="left-font03" onClick="tupian('3');">色系档案</a></td>
        </tr>
        
        <tr>
          <td width="9%" height="20" ><img id="img1-1" class="img-tree" src="../images/ico04.gif" width="8" height="12" /></td>
          <td width="91%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list_tree('1-1');" >料件档案</a></td>
        </tr>
        <tr id="subtree1-1" style="DISPLAY: none">
	        <td class="subtree-tree" width="91%" colspan="2">
	       	 <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table-tree">
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu1-1" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="method!listmaterials" target="mainFrame" class="left-font03" onClick="tupian('1-1');">料件大类档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu1-2" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="method!listmateChild" target="mainFrame" class="left-font03" onClick="tupian('1-2');">料件子类档案</a></td>
		        </tr>
		      </table>
	        </td>
        </tr>
        
        <tr>
          <td width="9%" height="20" ><img id="xiaotu4" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listProcess" target="mainFrame" class="left-font03" onClick="tupian('4');">工艺档案</a></td>
        </tr>
        
        <tr>
          <td width="9%" height="20" ><img id="xiaotu5" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listBrand" target="mainFrame" class="left-font03" onClick="tupian('5');">品牌档案</a></td>
        </tr>
      </table>
	<!-- 基础档案结束 -->

	<!-- 面料管理开始 -->
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%" height="12"><img name="img2" id="img2" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('2');" >面料管理</a></td>
                </tr>
            </table></td>
          </tr>
      </table>
	  
	  <table id="subtree2" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
	 	<tr>
          <td width="9%" height="20" ><img id="xiaotu5" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listFarIngredient" target="mainFrame" class="left-font03" onClick="tupian('5');">成份档案</a></td>
        </tr>
        <tr>
          <td width="9%" height="20" ><img id="xiaotu28" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listFarBrand" target="mainFrame" class="left-font03" onClick="tupian('28');">面料品牌档案</a></td>
        </tr>
        <tr>
          <td width="9%" height="20" ><img id="xiaotu6" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="fabric!listFabric" target="mainFrame" class="left-font03" onClick="tupian('6');">面料管理</a></td>
        </tr>
      </table>
	  <!-- 面料管理结束 -->
		
	  <!-- 里料管理开始 -->
	   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%" height="12"><img name="img3" id="img3" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('3');" >里料管理</a></td>
                </tr>
            </table></td>
          </tr>
      </table>
	  
	  <table id="subtree3" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
	 	<tr>
          <td width="9%" height="20" ><img id="xiaotu7" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listLinIngredient" target="mainFrame" class="left-font03" onClick="tupian('7');">成份档案</a></td>
        </tr>
        <tr>
          <td width="9%" height="20" ><img id="xiaotu8" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="lining!listLining" target="mainFrame" class="left-font03" onClick="tupian('8');">里料管理</a></td>
        </tr>
      </table>
	  <!-- 里料管理结束 -->
		
	   <!-- 辅料管理开始 -->
	   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%" height="12"><img name="img4" id="img4" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('4');" >辅料管理</a></td>
                </tr>
            </table></td>
          </tr>
      </table>
	  
	  <table id="subtree4" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
	 	<tr>
          <td width="9%" height="20" ><img id="xiaotu11" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listAccDocVariety" target="mainFrame" class="left-font03" onClick="tupian('11');">品种档案</a></td>
        </tr>
        <tr>
          <td width="9%" height="20" ><img id="xiaotu12" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listAccIngredient" target="mainFrame" class="left-font03" onClick="tupian('12');">成份档案</a></td>
        </tr>
        <tr>
          <td width="9%" height="20" ><img id="xiaotu13" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listAccUse" target="mainFrame" class="left-font03" onClick="tupian('13');">用途档案</a></td>
        </tr>
        <tr>
          <td width="9%" height="20" ><img id="xiaotu14" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="accessories!listLining" target="mainFrame" class="left-font03" onClick="tupian('14');">辅料</a></td>
        </tr>
      </table>
	  <!-- 辅料管理结束 -->
	  
	   <!-- 包装材料管理开始 -->
	   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%" height="12"><img name="img5" id="img5" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('5');" >包装材料管理</a></td>
                </tr>
            </table></td>
          </tr>
      </table>
	  
	  <table id="subtree5" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
	 	<tr>
          <td width="9%" height="20" ><img id="xiaotu11" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listPaccDocVariety" target="mainFrame" class="left-font03" onClick="tupian('11');">品名档案</a></td>
        </tr>
        <tr>
          <td width="9%" height="20" ><img id="xiaotu12" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listPacSpec" target="mainFrame" class="left-font03" onClick="tupian('12');">规格档案</a></td>
        </tr>
        <tr>
          <td width="9%" height="20" ><img id="xiaotu13" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="basedoc!listPacIngredient" target="mainFrame" class="left-font03" onClick="tupian('13');">材质档案</a></td>
        </tr>
        <tr>
          <td width="9%" height="20" ><img id="xiaotu14" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="packing!listLining" target="mainFrame" class="left-font03" onClick="tupian('14');">包装材料</a></td>
        </tr>
      </table>
	  <!-- 包装材料管理结束 -->
		
	   <!-- 外购商品管理开始 -->
	  	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%" height="12"><img name="img12" id="img12" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('12');" >外购商品管理</a></td>
                </tr>
            </table></td>
          </tr>
      </table>
      
      <table id="subtree12" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
        <tr>
          <td width="9%" height="20" ><img id="img12-1" class="img-tree" src="../images/ico04.gif" width="8" height="12" /></td>
          <td width="91%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list_tree('12-1');" >配饰管理</a></td>
        </tr>
        <tr id="subtree12-1" style="DISPLAY: none">
	        <td class="subtree-tree" width="91%" colspan="2">
	       	 <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table-tree">
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu12-1" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="basedoc!listOutDocVariety" target="mainFrame" class="left-font03" onClick="tupian('12-1');">品种档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu12-2" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="basedoc!listOutIngredient" target="mainFrame" class="left-font03" onClick="tupian('12-2');">成份档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu12-3" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="outsource!listOutsource" target="mainFrame" class="left-font03" onClick="tupian('12-3');">配饰</a></td>
		        </tr>
		      </table>
	        </td>
        </tr>

      </table>
	   <!-- 外购商品管理结束 -->
		
	   <!-- 样板管理开始 -->
	   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%" height="12"><img name="img6" id="img6" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('6');" >样板管理</a></td>
                </tr>
            </table></td>
          </tr>
      </table>
	  
	  <table id="subtree6"  style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
	 	<tr>
          <td width="9%" height="20" ><img id="xiaotu15" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="temtype!listTemtype" target="mainFrame" class="left-font03" onClick="tupian('15');">总分类档案</a></td>
        </tr>
        <tr>
          <td width="9%" height="20" ><img id="xiaotu16" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="temtype!listTemSubClass" target="mainFrame" class="left-font03" onClick="tupian('16');">大品类档案</a></td>
        </tr>
        <tr>
          <td width="9%" height="20" ><img id="img6-1" class="img-tree" src="../images/ico04.gif" width="8" height="12" /></td>
          <td width="91%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list_tree('6-1');">特殊档案</a></td>
        </tr>
        <tr id="subtree6-1" style="DISPLAY: none">
	        <td class="subtree-tree" width="91%" colspan="2">
	       	 <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table-tree">
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu6-1-1" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="basedoc!listSpeDocVariety" target="mainFrame" class="left-font03" onClick="tupian('6-1-1');">档案类别</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu6-1-2" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="special!listSpeSpecial" target="mainFrame" class="left-font03" onClick="tupian('6-1-2');">特殊档案</a></td>
		        </tr>
		      </table>
	        </td>
        </tr>
        
        <tr>
          <td width="9%" height="20" ><img id="img6-2" class="img6-2" src="../images/ico04.gif" width="8" height="12" /></td>
          <td width="91%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list_tree('6-2');">部件管理</a></td>
        </tr>
        <tr id="subtree6-2" style="DISPLAY: none">
	        <td class="subtree-tree" width="91%" colspan="2">
	       	 <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table-tree">
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu6-2-1" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="part!listMainPart" target="mainFrame" class="left-font03" onClick="tupian('6-2-1');">主部件</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu6-2-2" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="part!listSubPart" target="mainFrame" class="left-font03" onClick="tupian('6-2-2');">子部件</a></td>
		        </tr>
		      </table>
	        </td>
        </tr>
        
        <tr>
          <td width="9%" height="20" ><img id="xiaotu17" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="proClass!listProClass" target="mainFrame" class="left-font03" onClick="tupian('17');">品类</a></td>
        </tr>
      </table>
	  <!-- 样板管理结束 -->
		
	  <!-- 尺码管理开始 -->
	   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%" height="12"><img name="img8" id="img8" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('8');" >尺码管理</a></td>
                </tr>
            </table></td>
          </tr>
      </table>
	  
	  <table id="subtree8"  style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
        <tr style="DISPLAY: none">
          <td width="9%" height="20" ><img id="img8-1" class="img-tree" src="../images/ico04.gif" width="8" height="12" /></td>
          <td width="91%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list_tree('8-1');">体型特征档案</a></td>
        </tr>
        <tr id="subtree8-1" style="DISPLAY: none">
	        <td class="subtree-tree" width="91%" colspan="2">
	       	 <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table-tree">
	       	 <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-1" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDocType" target="mainFrame" class="left-font03" onClick="tupian('8-1-1');">档案类型</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-2" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc" target="mainFrame" class="left-font03" onClick="tupian('8-1-2');">量体档案</a></td>
		        </tr>
		        
		        <!--
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-1" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc?doctype=baseb" target="mainFrame" class="left-font03" onClick="tupian('8-1-1');">基本体型档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-2" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc?doctype=needle" target="mainFrame" class="left-font03" onClick="tupian('8-1-2');">颈长档案</a></td>
		        </tr>
		        
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-3" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc?doctype=kickerx" target="mainFrame" class="left-font03" onClick="tupian('8-1-3');">肩斜档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-4" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc?doctype=kickerc" target="mainFrame" class="left-font03" onClick="tupian('8-1-4');">肩冲档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-5" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc?doctype=back" target="mainFrame" class="left-font03" onClick="tupian('8-1-5');">背档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-6" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc?doctype=chest" target="mainFrame" class="left-font03" onClick="tupian('8-1-6');">胸档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-7" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc?doctype=abdomen" target="mainFrame" class="left-font03" onClick="tupian('8-1-7');">腹档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-8" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc?doctype=waistline" target="mainFrame" class="left-font03" onClick="tupian('8-1-8');">腰档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-9" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc?doctype=belt" target="mainFrame" class="left-font03" onClick="tupian('8-1-9');">裤带档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-10" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc?doctype=butt" target="mainFrame" class="left-font03" onClick="tupian('8-1-10');">臀档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-11" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc?doctype=leg" target="mainFrame" class="left-font03" onClick="tupian('8-1-11');">腿长比例档案</a></td>
		        </tr>
		        <tr>
		          <td width="9%" height="20" ><img id="xiaotu8-1-12" src="../images/ico06.gif" width="8" height="12" /></td>
		          <td width="91%"><a href="size!listSizeDoc?doctype=loosedeg" target="mainFrame" class="left-font03" onClick="tupian('8-1-12');">宽松度档案</a></td>
		        </tr>
		        -->
		      </table>
	        </td>
        </tr>
        <tr>
          <td width="9%" height="20" ><img id="xiaotu20" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="size!listSizeStandard" target="mainFrame" class="left-font03" onClick="tupian('20');">标准尺码</a></td>
        </tr>
      </table>
	  <!-- 尺码管理结束 -->
	  
	  <!-- 模型管理开始 -->
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%"><img name="img9" id="img9" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('9');">模型管理</a></td>
                </tr>
            </table></td>
          </tr>
      </table>

	  <table id="subtree9" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
        <tr>
          <td width="9%" height="20"><img id="xiaotu21" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="mold!listMoldType" target="mainFrame" class="left-font03" onClick="tupian('21');">模型分类</a></td>
        </tr>
        <tr>
          <td height="20"><img id="xiaotu22" src="../images/ico06.gif" width="8" height="12" /></td>
          <td><a href="mold!listMoldFolder" target="mainFrame" class="left-font03" onClick="tupian('22');">存储位置</a></td>
        </tr>
        <tr>
          <td height="20"><img id="xiaotu23" src="../images/ico06.gif" width="8" height="12" /></td>
          <td><a href="mold!listModel" target="mainFrame" class="left-font03" onClick="tupian('23');">模型</a></td>
        </tr>
      </table>
	  <!-- 模型管理结束 -->
	  
	  <!-- 定价方案管理开始 -->
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%"><img name="img10" id="img10" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('10');">定价方案管理</a></td>
                </tr>
            </table></td>
          </tr>
      </table>

	  <table id="subtree10" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
        <tr>
          <td width="9%" height="20"><img id="xiaotu24" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="sch!listScheme" target="mainFrame" class="left-font03" onClick="tupian('24');">定制价方案</a></td>
        </tr>
         <tr>
          <td width="9%" height="20"><img id="xiaotu25" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="sch!listSchemeCP" target="mainFrame" class="left-font03" onClick="tupian('25');">成品价方案</a></td>
        </tr>
      </table>
	  <!-- 定价方案管理结束 -->
	  
	  <!-- BTC标准配置(BOM表) 开始-->
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%"><img name="img11" id="img11" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('11');">BTC标准(BOM表)配置管理</a></td>
                </tr>
            </table></td>
          </tr>
      </table>

	  <table id="subtree11" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
        <tr>
          <td width="9%" height="20"><img id="xiaotu26" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="bom!listFeed" target="mainFrame" class="left-font03" onClick="tupian('26');">元件料件耗料表</a></td>
        </tr>
        <tr>
          <td width="9%" height="20"><img id="xiaotu27" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="bom!listConfig" target="mainFrame" class="left-font03" onClick="tupian('27');">BTC标配(BOM表)</a></td>
        </tr>
      </table>
	  <!-- BTC标准配置(BOM表) 结束-->
	  
	  <!-- 门店系统开始 -->
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%"><img name="img13" id="img7" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('13');">门店管理</a></td>
                </tr>
            </table></td>
          </tr>
      </table>

	  <table id="subtree13" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
        <tr>
          <td width="9%" height="20"><img id="xiaotu29" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="sper!listSpUser" target="mainFrame" class="left-font03" onClick="tupian('29');">店员管理</a></td>
        </tr>
      </table>
	  <!-- 门店系统结束-->
	  
	  <!--  服装系统结束    -->
	  
	  <!-- 管理系统开始 -->
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="left-table03">
          <tr>
            <td height="29"><table width="85%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="8%"><img name="img7" id="img7" src="../images/ico04.gif" width="8" height="11" /></td>
                  <td width="92%"><a href="javascript:" target="mainFrame" class="left-font03" onClick="list('7');">系统管理</a></td>
                </tr>
            </table></td>
          </tr>
      </table>

	  <table id="subtree7" style="DISPLAY: none" width="80%" border="0" align="center" cellpadding="0" cellspacing="0" class="left-table02">
        <tr>
          <td width="9%" height="20"><img id="xiaotu18" src="../images/ico06.gif" width="8" height="12" /></td>
          <td width="91%"><a href="method!registerpage" target="mainFrame" class="left-font03" onClick="tupian('18');">添加用户</a></td>
        </tr>
        <tr>
          <td height="20"><img id="xiaotu19" src="../images/ico06.gif" width="8" height="12" /></td>
          <td><a href="method!userlist" target="mainFrame" class="left-font03" onClick="tupian('19');">用户列表</a></td>
        </tr>
        <tr>
          <td height="20"><img id="xiaotu30" src="../images/ico06.gif" width="8" height="12" /></td>
          <td><a href="sys!toClean" target="mainFrame" class="left-font03" onClick="tupian('30');">自助清理</a></td>
        </tr>
        <tr>
          <td height="20"><img id="xiaotu31" src="../images/ico06.gif" width="8" height="12" /></td>
          <td><a href="sys!diyInfoList" target="mainFrame" class="left-font03" onClick="tupian('31');">查看定制信息</a></td>
        </tr>
      </table>
	  <!-- 管理系统结束-->
	  </TD>
  </tr>
  
</table>
</body>
</html>
