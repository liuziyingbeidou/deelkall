<%@ page language="java" contentType="text/html; charset=GBK" isErrorPage="true" pageEncoding="GBK"%>
<%response.setStatus(HttpServletResponse.SC_OK);%>
<%
/**
* 本页面是在客户查找的页面无法找到的情况下调用的
*/
response.setStatus(HttpServletResponse.SC_OK);
%>
<HTML>
<HEAD>
<title>十分抱歉，您要查看的网页当前已过期，或已被更名或删除！</title>
<META http-equiv=Content-Type content="text/html;">
<!--<meta http-equiv="refresh" content="3;URL=/mainfra.html">
-->
<STYLE type=text/css>INPUT {
	FONT-SIZE: 12px
}
TD {
	FONT-SIZE: 12px
}
.p2 {
	FONT-SIZE: 12px
}
.p6 {
	FONT-SIZE: 12px; COLOR: #1b6ad8
}
A {
	COLOR: #1b6ad8; TEXT-DECORATION: none
}
A:hover {
	COLOR: red
}
</STYLE>

<META content="Microsoft FrontPage 5.0" name=GENERATOR></HEAD>
<BODY oncontextmenu="return false" onselectstart="return false">
<P align=center>　</P>
<P align=center>　</P>
<TABLE cellSpacing=0 cellPadding=0 width=540 align=center border=0>
  <TBODY>
  <TR>
    <TD vAlign=top height=270>
      <DIV align=center><BR><IMG height=211 src="errorpic/error.gif" 
      width=329><BR><BR>
      <TABLE cellSpacing=0 cellPadding=0 width="80%" border=0>
        <TBODY>
        <TR>
          <TD><FONT class=p2>&nbsp;&nbsp;&nbsp; <FONT color=#ff0000><IMG 
            height=13 src="errorpic/emessage.gif" 
            width=12>&nbsp;无法访问本页的原因是：</FONT></FONT></TD></TR>
        <TR>
          <TD height=8></TD>
        <TR>
          <TD>
            <P><FONT color=#000000><BR>　　您所请求的页面不存在</FONT>! 
      　</P></TD></TR></TBODY></TABLE></DIV></TD></TR>
  <TR>
    <TD height=5></TD>
  <TR>
    <TD align=middle>
      <CENTER>
      <TABLE cellSpacing=0 cellPadding=0 width=480 border=0>
        <TBODY>
        <TR>
          <TD width=6><IMG height=26 src="errorpic/left.gif" 
width=7></TD>
          <TD background=errorpic/bg.gif>
            <DIV align=center><FONT class=p6><A 
            href="../">返回首页</A>　 　|　　 <A 
            href="javascript:history.go(-1)">返回出错页</A>　 　|　　 <A 
            href="../">关闭本页</A></FONT> </DIV></TD>
          <TD width=7><IMG 
      src="errorpic/right.gif"></TD></TR></TBODY></TABLE></CENTER></TD></TR></TBODY></TABLE>
<P align=center>　</P>
<P align=center>　</P>
</BODY>
</HTML>