package model.sp.system;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import model.IdEntity;


/** 
 * @Title: 服装系统
 * @ClassName: DiyInfoBO 
 * @Description: 存储DIY定制信息
 * @author liuzy
 * @date 2015-9-15 下午04:45:20  
 */
@Entity
@Table(name="fz_diyInfo")
public class DiyInfoVO extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	//成品编码
	private String vcode;
	//产品编码	varchar(50)
	private String prodCode;
	//产品名称	varchar(100)
	private String prodName;	
	//定制信息code+名称	varchar(300)
	private String diyBom;	
	//定制信息中文说明 	varchar(300)
	private String diyBomDesc;	
	//3D图片路径	varchar(300)
	private String diyImgUrl;	
	//产品类型	char(1)
	private String diyType;	
	//产品尺码信息	varchar(300)
	private String diySize;
	//产品工艺编码	varchar(300)
	private String diyCraft;	
	//商品单价	varchar(20)
	private String prodPrice;	
	//版型	char(1)
	private String formatType;	
	//BOM表信息	varchar(300)
	private String vbom;
	//时间戳
	private Timestamp ts;
	private Integer dr;
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getDiyBom() {
		return diyBom;
	}
	public void setDiyBom(String diyBom) {
		this.diyBom = diyBom;
	}
	public String getDiyBomDesc() {
		return diyBomDesc;
	}
	public void setDiyBomDesc(String diyBomDesc) {
		this.diyBomDesc = diyBomDesc;
	}
	public String getDiyImgUrl() {
		return diyImgUrl;
	}
	public void setDiyImgUrl(String diyImgUrl) {
		this.diyImgUrl = diyImgUrl;
	}
	public String getDiyType() {
		return diyType;
	}
	public void setDiyType(String diyType) {
		this.diyType = diyType;
	}
	public String getDiySize() {
		return diySize;
	}
	public void setDiySize(String diySize) {
		this.diySize = diySize;
	}
	public String getDiyCraft() {
		return diyCraft;
	}
	public void setDiyCraft(String diyCraft) {
		this.diyCraft = diyCraft;
	}
	public String getProdPrice() {
		return prodPrice;
	}
	public void setProdPrice(String prodPrice) {
		this.prodPrice = prodPrice;
	}
	public String getFormatType() {
		return formatType;
	}
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}
	public String getVbom() {
		return vbom;
	}
	public void setVbom(String vbom) {
		this.vbom = vbom;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	public Timestamp getTs() {
		return ts;
	}
	public void setTs(Timestamp ts) {
		this.ts = ts;
	}
	public Integer getDr() {
		return dr;
	}
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	
}
