/**
 * 打开页面xl
 * @param url
 */
function winOpen(url,width,height) {
	if(width == undefined){
		width = 570;
	}
	if(height == undefined){
		height = 370;
	}
	
    window.open(url+"?dateTime="+(new Date()).getTime(),'_blank','width='+width+'px,height='+height+'px;toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no,screenX='+(window.screen.width/2-850/2)+',screenY='+(window.screen.height/2-550/2)); 
}

/**
 * EasyUI DataGrid根据字段动态合并单元格
 * @param fldList 要合并table的id
 * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
 */
 function MergeCells(tableID, fldList) {
     /*var Arr = fldList.split(",");
     var dg = $('#' + tableID);
     var fldName;
     var RowCount = dg.datagrid("getRows").length;
     var span;
     var PerValue = "";
     var CurValue = "";
     var length = Arr.length - 1;
     for (i = length; i >= 0; i--) {
         fldName = Arr[i];
         PerValue = "";
         span = 1;
         for (row = 0; row <= RowCount; row++) {
             if (row == RowCount) {
                 CurValue = "";
             }
             else {
                 CurValue = dg.datagrid("getRows")[row][fldName];
             }
             if (PerValue == CurValue) {
                 span += 1;
             }
             else {
                 var index = row - span;
                 dg.datagrid('mergeCells', {
                     index: index,
                     field: fldName,
                     rowspan: span,
                     colspan: null
                 });
                 span = 1;
                 PerValue = CurValue;
             }
         }
     }*/
 }

$(function () {
	 $(".number").keyup(function(){
         $(this).val($(this).val().replace(/[^0-9.]/g,''));
     }).bind("paste",function(){  //CTR+V事件处理    
         $(this).val($(this).val().replace(/[^0-9.]/g,''));     
     }).css("ime-mode", "disabled"); //CSS设置输入法不可用  
	 
	 //1、面料编码解析-花型；颜色；成份;名称
	 $("#frcode").focusout(function(){
		 var code = $(this).val();
		 if(code == ""){
			 return ;
		 }
		 var len = code.length;
		 if(len != 11){
			 alert("面料编码长度不正确!");
			 return ;
		 }
		 var ig = code.substr(3,2);//成份
		 var pat = code.substr(5,1);//花型
		 var cl = code.substr(6,1);//颜色
		 
		 $(".fr-ig option[em='"+ig+"']").attr("selected","selected");
		 $(".fr-pat option[em='"+pat+"']").attr("selected","selected");
		 $(".fr-cl option[em='"+cl+"']").attr("selected","selected");
		 
		 $("#frname").val($(".fr-ig option[em='"+ig+"']").text()+$(".fr-pat option[em='"+pat+"']").text()+$(".fr-cl option[em='"+cl+"']").text());
	 });
	 //2、里料编码解析-花型；颜色；成份;名称
	 $("#lncode").focusout(function(){
		 var code = $(this).val();
		 if(code == ""){
			 return ;
		 }
		 var len = code.length;
		 if(len != 11){
			 alert("里料编码长度不正确!");
			 return ;
		 }
		 var ig = code.substr(3,2);//成份
		 var pat = code.substr(5,1);//花型
		 var cl = code.substr(6,1);//颜色
		 
		 $(".ln-ig option[em='"+ig+"']").attr("selected","selected");
		 $(".ln-pat option[em='"+pat+"']").attr("selected","selected");
		 $(".ln-cl option[em='"+cl+"']").attr("selected","selected");
		
		 $("#lnname").val($(".ln-ig option[em='"+ig+"']").text()+$(".ln-pat option[em='"+pat+"']").text()+$(".ln-cl option[em='"+cl+"']").text());
	 });
	 
	 $("#accode").focusout(function(){
		 var type = $(".docvarietyid option:selected").text();
		 var code = $(this).val();
		 if(code == ""){
			 return ;
		 }
		 var len = code.length;
		 if(len != 10){
			 alert("辅料编码长度不正确!");
			 return ;
		 }
		//3-2、辅料料编码解析-颜色；品牌（商标）;名称
		 if("商标".indexOf(type) >= 0){
			 var brd = code.substr(4,1);//品牌
			 var cl = code.substr(5,1);//颜色
			 $(".ac-brd option[em='"+brd+"']").attr("selected","selected");
			 $(".ac-cl option[em='"+cl+"']").attr("selected","selected");
			 $("#acname").val($(".ac-cl option[em='"+cl+"']").text()+$(".ac-brd option[em='"+brd+"']").text());
		 }
		//3-1、辅料料编码解析-颜色；成份（纽扣、袋布、垫肩、填充类、领底呢、织带、拉链、配件类）;名称
		 if("纽扣、袋布、垫肩、填充类、领底呢、织带、拉链、配件类".indexOf(type) >= 0){
			 var ig = code.substr(4,1);//成份
			 var cl = code.substr(5,1);//颜色
			 $(".ac-ig option[em='"+ig+"']").attr("selected","selected");
			 $(".ac-cl option[em='"+cl+"']").attr("selected","selected");
			 $("#acname").val($(".ac-ig option[em='"+ig+"']").text()+$(".ac-cl option[em='"+cl+"']").text());
		 }
	    //3-2、辅料料编码解析-颜色；用途（线、衬）;名称
		 if("线".indexOf(type) >= 0){
			 var use = code.substr(4,1);//用途
			 var cl = code.substr(5,1);//颜色
			 $(".ac-use option[em='"+use+"']").attr("selected","selected");
			 $(".ac-cl option[em='"+cl+"']").attr("selected","selected");
			 $("#acname").val($(".ac-use option[em='"+use+"']").text()+$(".ac-cl option[em='"+cl+"']").text());
		 }
		 if("衬".indexOf(type) >= 0){
			 var usec = code.substr(4,2);//用途
			 var cl = code.substr(6,1);//颜色
			 $(".ac-use option[em='"+usec+"']").attr("selected","selected");
			 $(".ac-cl option[em='"+cl+"']").attr("selected","selected");
			 $("#acname").val($(".ac-use option[em='"+usec+"']").text()+$(".ac-cl option[em='"+cl+"']").text());
		 }
	 });
	 
	 //4、外购商品-配饰（领带、领结）
	 $("#oscode").focusout(function(){
		 var code = $(this).val();
		 if(code == ""){
			 return ;
		 }
		 var len = code.length;
		 if(len != 11){
			 alert("配饰编码长度不正确!");
			 return ;
		 }
		 var ig = code.substr(5,2);//成份
		 var cl = code.substr(7,1);//颜色
		 
		 $(".os-ig option[em='"+ig+"']").attr("selected","selected");
		 $(".os-cl option[em='"+cl+"']").attr("selected","selected");
		 
		 $("#osname").val($(".os-ig option[em='"+ig+"']").text()+$(".os-cl option[em='"+cl+"']").text());
	 });
	 
});

