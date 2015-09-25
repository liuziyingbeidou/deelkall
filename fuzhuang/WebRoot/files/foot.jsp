<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

    <TABLE class=botbg cellSpacing=0 cellPadding=0 width="100%" border=0 >
        <TBODY>
        <TR height=26>
          <TD style="WIDTH: 38px" onclick=switchSysBar() align=middle><SPAN 
            class=navpoint id=switchPoint title=关闭/打开左栏><IMG alt="" 
            src="../images/right.gif"></SPAN></TD>
          <TD 
            style="FONT-SIZE: 11px; FONT-FAMILY: arial; TEXT-ALIGN: left">Copyright 
            Right &copy; 2015-2016 </TD>
          <TD style="PADDING-RIGHT: 20px; TEXT-ALIGN: right; FONT-SIZE: 13px; FONT-FAMILY: arial; ">登录成功...<input type="BUTTON" style="display: none" name="FullScreen" value="全屏显示" onClick="window.open(document.parent.location.href, 'mid', 'fullscreen')"></TD></TR></TBODY></TABLE>
<SCRIPT type=text/javascript>
<!--
var status = 1;
function switchSysBar(){
     if (1 == window.status){
		  window.status = 0;
          switchPoint.innerHTML = '<img src="../images/left_.gif"/>';
		  window.parent.document.getElementsByTagName("frameset")[1].cols="0,*"; 
     }
     else{
		  window.status = 1;
          switchPoint.innerHTML = '<img src="../images/right.gif"/>';
          window.parent.document.getElementsByTagName("frameset")[1].cols="213,*"; 
     }
}
//-->
</SCRIPT>