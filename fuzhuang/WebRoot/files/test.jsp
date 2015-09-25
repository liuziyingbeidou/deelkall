<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
<link rel="stylesheet" type="text/css" href="../css/jquery.datetimepicker.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
</head>
<body>
	<h3>onGenerate</h3>
	<input type="text" id="datetimepicker8"/>
<hr />
<a href="javascript:gcB2C_T()">单件商品</a><br />
<a href="javascript:gcDouB2C_T()">二件套商品</a>
<a href="javascript:gcDouB3C_T()">三件套商品</a>

<br />
	</body>
<script src="../js/jquery.datetimepicker.js"></script>
<script>
$('#datetimepicker8').datetimepicker({
	onGenerate:function( ct ){
		$(this).find('.xdsoft_date')
			.toggleClass('xdsoft_disabled');
	},
	minDate:'-1970/01/2',
	maxDate:'+1970/01/2',
	timepicker:false
});

</script>
<script type="text/javascript">
//接口url
var itfUrl = "www.deelkall.com";
 $(function () {
     (function ($) {
         $.getUrlParam = function (name) {
	         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	         var r = window.location.search.substr(1).match(reg);
	             if (r != null) return unescape(r[2]); return null;
	         };
         })(jQuery);
	  
      var parse = $.getUrlParam('backUrl');
      if(parse != null && parse != undefined && parse != ""){
      	itfUrl = parse;
      }
});
//单件商品-测试
function gcB2C_T(){
	itfUrl = "http://127.0.0.1:8080/fuzhuang/interface!";
	var to = itfUrl + "saveDiyInfo";
	postwith(
		to,
		{
			'prodCode':'21851232',
	 		'prodName':'定制衬衣',
	 		'diyBom':'12rsdfsadf',
			'diyBomDesc':"大身面料--面料:面料名称  纹样:纯色  品级:D\n领型--款式:LF01方领  面料:面料名称  纹样:纯色  品级:D\n克夫--款式:KZ01 7CM直角克夫  面料:面料名称  纹样:纯色  品级:D\n门襟--款式:MCBD6BA001  面料:面料名称  纹样:纯色  品级:D\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:",
	 		'diyImgUrl':'http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg',
	 		'diyType':'1',
	 		'diySize': "MMSHLW:38;MMSHXW:93;MMSHYW:87;MMSHXB:90;MMSHJK:38.5;MMSHXC:57.5;MMSHYC:62;MMSHXK:20\n颈围: 38cm   净胸围: 93cm   净腰围: 87cm   臀围: 90cm   总肩宽: 38.5cm   袖长: 57.5cm   后中长: 62cm   手腕围: 20cm\n1",
	 		'diyCraft':'GL(内里型-01全内里)-BD(内里插笔袋-01直形)' ,
	 		'prodPrice': '390.8',
	 		'formatType': '2',
	 		'diyCode': '13'
		}
	);
}

//套装商品-测试
function gcDouB2C_T(){
	
	var to = itfUrl + "/diy3orders.do";
	postwith(
		to,
		{
			'prodCode':'22222;;;33333',
	 		'prodName':'定制西服;;;定制西裤',
	 		'diyBom':'12rsdfsadf;;;12rsdfsadf',
			'diyBomDesc':"大身面料--面料:面料名称  纹样:纯色  品级:D\n领型--款式:LF01方领  面料:面料名称  纹样:纯色  品级:D\n克夫--款式:KZ01 7CM直角克夫  面料:面料名称  纹样:纯色  品级:D\n门襟--款式:MCBD6BA001  面料:面料名称  纹样:纯色  品级:D\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:;;;大身面料--面料:面料名称  纹样:纯色  品级:D\n领型--款式:LF01方领  面料:面料名称  纹样:纯色  品级:D\n克夫--款式:KZ01 7CM直角克夫  面料:面料名称  纹样:纯色  品级:D\n门襟--款式:MCBD6BA001  面料:面料名称  纹样:纯色  品级:D\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:",
	 		'diyImgUrl':'http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg;;;http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg',
	 		'diyType':'1;;;2',
	 		//'diySize': "MMSHLW:38;MMSHXW:93;MMSHYW:87;MMSHXB:90;MMSHJK:38.5;MMSHXC:57.5;MMSHYC:62;MMSHXK:20\n颈围: 38cm   净胸围: 93cm   净腰围: 87cm   臀围: 90cm   总肩宽: 38.5cm   袖长: 57.5cm   后中长: 62cm   手腕围: 20cm\n1;;;MMSHLW:38;MMSHXW:93;MMSHYW:87;MMSHXB:90;MMSHJK:38.5;MMSHXC:57.5;MMSHYC:62;MMSHXK:20\n颈围: 38cm   净胸围: 93cm   净腰围: 87cm   臀围: 90cm   总肩宽: 38.5cm   袖长: 57.5cm   后中长: 62cm   手腕围: 20cm\n1",
	 		'diySize':"LW:0XW:96YW:84TW:0ZJK:42.8XC:59HZC:73SWW:0\n领围:0胸围:96腰围:84臀围:0总肩宽:42.8袖长:59后中长:73手腕围:0\n1;;;X:23T:34H:35\n胸围:23臀围:34后中长:35\n1",
	 		'diyCraft':'GL(内里型-01全内里)-BD(内里插笔袋-01直形);;;GL(内里型-01全内里)-BD(内里插笔袋-01直形)' ,
	 		'prodPrice': '390.8;;;390.8',
	 		'formatType': '2;;;3',
	 		'diyCode': '21851232151;;;21851232151',
	 		'diyCount': 2
		}
	);
}

//套装商品-测试
function gcDouB3C_T(){
	postwith(
		'http://test.deelkall.com:82/diy3orders.do',
		{
			'prodCode':'22222;;;33333;;;55555',
	 		'prodName':'定制西服;;;定制西裤;;;定制马甲',
	 		'diyBom':'12rsdfsadf;;;12rsdfsadf;;;12rsdfsadf',
			'diyBomDesc':"大身面料--面料:面料名称  纹样:纯色  品级:D\n领型--款式:LF01方领  面料:面料名称  纹样:纯色  品级:D\n克夫--款式:KZ01 7CM直角克夫  面料:面料名称  纹样:纯色  品级:D\n门襟--款式:MCBD6BA001  面料:面料名称  纹样:纯色  品级:D\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:;;;大身面料--面料:面料名称  纹样:纯色  品级:D\n领型--款式:LF01方领  面料:面料名称  纹样:纯色  品级:D\n克夫--款式:KZ01 7CM直角克夫  面料:面料名称  纹样:纯色  品级:D\n门襟--款式:MCBD6BA001  面料:面料名称  纹样:纯色  品级:D\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:;;;大身面料--面料:面料名称  纹样:纯色  品级:D\n领型--款式:LF01方领  面料:面料名称  纹样:纯色  品级:D\n克夫--款式:KZ01 7CM直角克夫  面料:面料名称  纹样:纯色  品级:D\n门襟--款式:MCBD6BA001  面料:面料名称  纹样:纯色  品级:D\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:",
	 		'diyImgUrl':'http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg;;;http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg;;;http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg',
	 		'diyType':'1;;;2;;;2',
	 		//'diySize': "MMSHLW:38;MMSHXW:93;MMSHYW:87;MMSHXB:90;MMSHJK:38.5;MMSHXC:57.5;MMSHYC:62;MMSHXK:20\n颈围: 38cm   净胸围: 93cm   净腰围: 87cm   臀围: 90cm   总肩宽: 38.5cm   袖长: 57.5cm   后中长: 62cm   手腕围: 20cm\n1;;;MMSHLW:38;MMSHXW:93;MMSHYW:87;MMSHXB:90;MMSHJK:38.5;MMSHXC:57.5;MMSHYC:62;MMSHXK:20\n颈围: 38cm   净胸围: 93cm   净腰围: 87cm   臀围: 90cm   总肩宽: 38.5cm   袖长: 57.5cm   后中长: 62cm   手腕围: 20cm\n1",
	 		'diySize':"LW:0XW:96YW:84TW:0ZJK:42.8XC:59HZC:73SWW:0\n领围:0胸围:96腰围:84臀围:0总肩宽:42.8袖长:59后中长:73手腕围:0\n1;;;X:23T:34H:35\n胸围:23臀围:34后中长:35\n1;;;L:0X:108Y:96T:0Z:46.4X:62H:77S:0\n领围:0胸围:108腰围:96臀围:0总肩宽:46.4袖长:62后中长:77手腕围:0\n1",
	 		'diyCraft':'GL(内里型-01全内里)-BD(内里插笔袋-01直形);;;GL(内里型-01全内里)-BD(内里插笔袋-01直形);;;GL(内里型-01全内里)-BD(内里插笔袋-01直形)' ,
	 		'prodPrice': '390.8;;;390.8;;;390.8',
	 		'formatType': '2;;;3;;;3',
	 		'diyCode': '21851232151;;;21851232151;;;21851232151',
	 		'diyCount': 3
		}
	);
}

//单件商品
function gcB2C(p) {
	var to = "http://test.deelkall.com:82/diy3order.do";
	postwith_area(
		to,
		{
			'prodCode':p.prodCode,
	 		'prodName':p.prodName,
	 		'diyBom':p.diyBom,
			'diyBomDesc':p.diyBomDesc,
	 		'diyImgUrl':p.diyImgUrl,
	 		'diyType':p.diyType,
	 		'diySize': p.diySize,
	 		'diyCraft':p.diyCraft ,
	 		'prodPrice': p.prodPrice,
	 		'formatType': p.formatType,
	 		'diyCode': p.diyCode
		}
	);
}

//套装商品
function gcDouB2C(p) {
	var to = "http://test.deelkall.com:82/diy3orders.do";
	postwith(
		to,
		{
			'prodCode':p.prodCode,
	 		'prodName':p.prodName,
	 		'diyBom':p.diyBom,
			'diyBomDesc':p.diyBomDesc,
	 		'diyImgUrl':p.diyImgUrl,
	 		'diyType':p.diyType,
	 		'diySize': p.diySize,
	 		'diyCraft':p.diyCraft ,
	 		'prodPrice': p.prodPrice,
	 		'formatType': p.formatType,
	 		'diyCode': p.diyCode,
	 		'diyCount': p.diyCount
		}
	);
}

//整合form
function postwith(to, p) {
	
	var myForm = document.createElement("form");
	myForm.method = "post";
	myForm.action = to;
	for ( var k in p) {
		var myInput = document.createElement("input");
		myInput.setAttribute("type","hidden");
		myInput.setAttribute("name", k);
		myInput.setAttribute("value", p[k]);
		myForm.appendChild(myInput);
	}
	document.body.appendChild(myForm);
	myForm.submit();
	document.body.removeChild(myForm);
}

//整合form
function postwith_area(to, p) {
	
	var myForm = document.createElement("form");
	myForm.method = "post";
	myForm.action = to;
	for ( var k in p) {
		var myTextarea = document.createElement("textarea");
		myTextarea.type="hidden";
		myTextarea.name=k;
		myTextarea.innerHTML=p[k];
		myForm.appendChild(myTextarea);
	}
	document.body.appendChild(myForm);
	myForm.submit();
	document.body.removeChild(myForm);
}

</script>
</html>

