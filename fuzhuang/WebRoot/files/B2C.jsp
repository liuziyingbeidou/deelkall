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
<body onload="gcDouB2C_()">
	<h3>onGenerate</h3>
	<input type="text" id="datetimepicker8"/>
<hr />
<a href="test.jsp?backUrl=http://test.deelkall.com:82">三件套商品</a>
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
//套装
function gcDouB2C_(){
	var prodCode = new Array('2015081501','2015081502');
	var prodName = new Array('西服上衣','西裤');
	var diyBom = new Array('12rsdfsadf','12rsdfsadf');//3D中存放定制信息code erp使用
	var diyBomDesc = new Array('大身面料--面料:面料名称  纹样:纯色  品级:D\n领型--款式:LF01方领  面料:面料名称  纹样:纯色  品级:D\n克夫--款式:KZ01 7CM直角克夫  面料:面料名称  纹样:纯色  品级:D\n门襟--款式:MCBD6BA001  面料:面料名称  纹样:纯色  品级:D\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:',
	'大身面料--面料:面料名称  纹样:纯色  品级:D\n领型--款式:LF01方领  面料:面料名称  纹样:纯色  品级:D\n克夫--款式:KZ01 7CM直角克夫  面料:面料名称  纹样:纯色  品级:D\n门襟--款式:MCBD6BA001  面料:面料名称  纹样:纯色  品级:D\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:\n口袋--款式:DT02袋口拼接  面料:  纹样:  品级:');//3D中存放定制信息中文说明 
	var diyImgUrl = new Array('http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg','http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg|http://3d.deelkall.com/Clothes/fabricImage/0011330003.jpg');//3D图片路径   3张图片图片以‘|’分割
	var diyType = new Array('2','3');//3D产品类型  和ERP中间表3种类型一致  1:衬衣  2:上衣  3:裤子
	var diySize = new Array('MMSHLW:38;MMSHXW:93;MMSHYW:87;MMSHXB:90;MMSHJK:38.5;MMSHXC:57.5;MMSHYC:62;MMSHXK:20\n颈围: 38cm   净胸围: 93cm   净腰围: 87cm   臀围: 90cm   总肩宽: 38.5cm   袖长: 57.5cm   后中长: 62cm   手腕围: 20cm\n1',
	'MMSHLW:38;MMSHXW:93;MMSHYW:87;MMSHXB:90;MMSHJK:38.5;MMSHXC:57.5;MMSHYC:62;MMSHXK:20\n颈围: 38cm   净胸围: 93cm   净腰围: 87cm   臀围: 90cm   总肩宽: 38.5cm   袖长: 57.5cm   后中长: 62cm   手腕围: 20cm\n1');//3D产品尺码信息 
	var diyCraft = new Array('GL(内里型-01全内里)-BD(内里插笔袋-01直形)','GL(内里型-01全内里)-BD(内里插笔袋-01直形)');//3D产品工艺编码 --erp使用
	var prodPrice = new Array('33','33');//商品单价
	var formatType = new Array('3','3');//版型  1:合身  2：修身  3：合体  4：韩版
	var diyCode = new Array('2015081501','2015081502');//3D唯一编码 --erp用于关联BOM
	
	
	$.ajax({
            type: "POST",
            url: "http://test.deelkall.com:82/diy3orders.do",
            contentType: "text/plain", //必须有
            dataType: "jsonp", //表示返回值类型，不必须
            data: {
			 		prodCode: prodCode,//产品编码
			 		prodName: prodName,//产品名称
			 		diyBom: diyBom,//3D中存放定制信息code erp使用
			 		diyBomDesc: diyBomDesc,//3D中存放定制信息中文说明 
			 		diyImgUrl: diyImgUrl,//3D图片路径   3张图片图片以‘|’分割
			 		diyType: diyType,//3D产品类型  和ERP中间表3种类型一致  1:衬衣  2:上衣  3:裤子
			 		diySize: diySize,//3D产品尺码信息 
			 		diyCraft: diyCraft,//3D产品工艺编码 --erp使用
			 		prodPrice: prodPrice,//商品单价
			 		formatType: formatType,//版型  1:合身  2：修身  3：合体  4：韩版
			 		diyCode: diyCode,//3D唯一编码 --erp用于关联BOM
			 		diyCount: 2//套装单件的数量
				},  
            success: function (jsonResult) {
			}
        });
}

</script>
</html>

