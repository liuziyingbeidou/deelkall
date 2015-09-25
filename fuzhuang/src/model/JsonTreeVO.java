package model;

/**
 * @Title: 服装系统
 * @ClassName: JsonTreeVO 
 * @Description: TreeJson构建 
 * @author liuzy
 * @date 2015-7-24 下午03:29:04
 */
public class JsonTreeVO{
	private Integer id;
	/** 显示的节点文本 **/
	private String text;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}