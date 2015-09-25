package action.upload;

import java.util.ArrayList;


/** 
 * @Title: 服装系统
 * @ClassName: Result 
 * @Description: 表示上传的结果。
 * @author liuzy
 * @date 2015-7-30 上午10:18:42  
 */
public class Result {

	/**
	* 表示图片是否已上传成功。
	*/
	public Boolean success;
	public String userid;
	public String username;
	/**
	* 自定义的附加消息。
	*/
	public String msg;
	/**
	* 表示原始图片的保存地址。
	*/
	public String sourceUrl;
	/**
	* 表示所有头像图片的保存地址，该变量为一个数组。
	*/
	public ArrayList avatarUrls;
}
