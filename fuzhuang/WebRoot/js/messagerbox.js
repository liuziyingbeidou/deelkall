/**
 * 
 * @Autor lzy
 * 消息提示框集
 */

$(function($){
	$.messager.defaults={ok:"继续上架",cancel:"暂不上架"};

});

function alert_base(msg,title) {
	if(title  == undefined || title == ''){
		title = '消息';
	}
	$.messager.alert(title, msg);
}
function alert_error(msg,title) {
	if(title  == undefined || title == ''){
		title = '错误';
	}
	$.messager.alert(title, msg, 'error');
}
function alert_info(msg,title) {
	if(title  == undefined || title == ''){
		title = '消息';
	}
	$.messager.alert(title, msg, 'info');
}
function alert_question(msg,title) {
	if(title  == undefined || title == ''){
		title = '疑问';
	}
	$.messager.alert(title, msg, 'question');
}
function alert_warning(msg,title) {
	if(title  == undefined || title == ''){
		title = '警告';
	}
	$.messager.alert(title, msg, 'warning');
}

function confirm(msg,title,fn) {
	if(title  == undefined || title == ''){
		title = '提示';
	}
	if(fn == undefined){
		fn = ok;
	}
	$.messager.confirm(title, msg, fn);
}

//demo
function ok(r){
	if (r){
		//alert('提示: '+'测试');
	}
}

/**
 * 
 * @param msg
 * @param title
 * @param showtype show,slide,fade, 
 */
function topCenter(msg,title,showtype,showSpeed,timeout) {
	if(title  == undefined || title == ''){
		title = '提示';
	}
	if(showtype  == undefined || showtype == ''){
		showtype = 'slide';
	}
	if(showSpeed  == undefined || showSpeed == ''){
		showtype = '600';
	}
	if(timeout  == undefined || timeout == ''){
		showtype = '4000';
	}
	$.messager.show({
		title:title,
		msg:msg,
		showType:showtype,
		showSpeed:600,
		timeout:4000,
		style:{
			right:'',
			top:document.body.scrollTop+document.documentElement.scrollTop,
			bottom:''
		}
	});
}
