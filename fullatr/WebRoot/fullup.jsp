<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//标识
String flag = request.getParameter("flag");
String title = "面料";
if("lin".equals(flag)){
	title = "里料";
}
%>
<html>
    <head>
        <meta charset="utf-8"/>
        <title><%=title %>上传</title>
        <script type="text/javascript" src="scripts/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="scripts/swfobject.js"></script>
        <script type="text/javascript" src="scripts/fullAvatarEditor.js"></script>
    </head>
    <body>
		<div style="width:632px;margin: 0 auto;text-align:center">
			<h1 style="text-align:center"><%=title %>上传</h1>
			<div>
				<p id="swfContainer">
                    本组件需要安装Flash Player后才可使用，请从<a href="http://www.adobe.com/go/getflashplayer">这里</a>下载安装。
				</p>
			</div>
        </div>
		<script type="text/javascript">
            swfobject.addDomLoadEvent(function () {
				var swf = new fullAvatarEditor("fullAvatarEditor.swf", "expressInstall.swf", "swfContainer", {
					    id : 'swf',
						upload_url : 'upload.jsp?flag=<%=flag%>',	//上传接口
						method : 'get',	//传递到上传接口中的查询参数的提交方式。更改该值时，请注意更改上传接口中的查询参数的接收方式
						src_upload : 1,		//是否上传原图片的选项，有以下值：0-不上传；1-上传；2-显示复选框由用户选择
						avatar_box_border_width : 0,
						avatar_sizes : '80*80',
						avatar_sizes_desc : '80*80像素',
						src_size : '10MB',
						tab_visible : 'false',
						browse_tip : '仅支持JPG、JPEG、GIF、PNG格式的图片文件\n文件不能大于10MB'
					}, function (msg) {
					//debugger
						if (msg.code == 5)
					    {
					        switch(msg.type)
					        {
					            //表示图片上传成功。
					            case 0:
									 if(msg.content.sourceUrl)
									{
										//alert(msg.content.sourceUrl);
										alert("上传成功!");
										window.opener.setFilePath(msg.content.newName,function(){
										window.close();
										});
									}else{
										alert('原图片上传失败!');
									}
					            break;
					            case 1:
					                alert('头像上传失败，原因：上传的原图文件大小超出限值了！');//will output:头像上传失败，原因：
					            break;
					            case 2:
					                alert('头像上传失败，原因：指定的上传地址不存在或有问题！');
					            break;
					            case 3:
					                alert('头像上传失败，原因：发生了安全性错误！请添加crossdomain.xml到网站根目录。');
					            break;
					        }
					    }
					}
				);
            });
        </script>
    </body>
</html>