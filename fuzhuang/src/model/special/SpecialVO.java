package model.special;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.IdEntity;

/**
 * 
* @Title: 服装系统
* @ClassName: SpecialVO 
* @Description: 特殊档案
* @author liuzy
* @date 2015-6-17 上午11:46:02
 */
@Entity
@Table(name="fz_special")
public class SpecialVO extends IdEntity {

	private static final long serialVersionUID = 1L;

	//编码		char(20)
	private String vcode;
	//名称		varchar(30)
	private String vname;
	//图URL		varchar(255)
	private String vfileupload;
	//是否上传		smallint
	private Integer bisupload;
	//档案类别id		int
	private Integer docvarietyid;
	//档案类别名称		varchar(30)
	@Transient
	private String docvarietyname;
	//关联ids		varchar(30)
	private String releids;
	private String releNames;
	//时间戳
	private Timestamp ts;
	//dr
	private Integer dr;
	//备注
	private String vmemo;
	//自定义项
	private String vdef1;//使用-是否有图
	private String vdef2;//使用-是否默认数据
	private String vdef3;
	private String vdef4;
	private String vdef5;
	//是否默认数据
	private Integer bisdefault;
	//简称vsname、varchar(30)
	private String vsname;
	//坐标位		varchar(255)
	private String vtransform;
	
	public String getVsname() {
		return vsname;
	}
	public void setVsname(String vsname) {
		this.vsname = vsname;
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
	
	public String getReleNames() {
		return releNames;
	}
	public void setReleNames(String releNames) {
		this.releNames = releNames;
	}
	public String getVmemo() {
		return vmemo;
	}
	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}
	public String getVdef1() {
		return vdef1;
	}
	public void setVdef1(String vdef1) {
		this.vdef1 = vdef1;
	}
	public String getVdef2() {
		return vdef2;
	}
	public void setVdef2(String vdef2) {
		this.vdef2 = vdef2;
	}
	public String getVdef3() {
		return vdef3;
	}
	public void setVdef3(String vdef3) {
		this.vdef3 = vdef3;
	}
	public String getVdef4() {
		return vdef4;
	}
	public void setVdef4(String vdef4) {
		this.vdef4 = vdef4;
	}
	public String getVdef5() {
		return vdef5;
	}
	public void setVdef5(String vdef5) {
		this.vdef5 = vdef5;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	public String getVfileupload() {
		return vfileupload;
	}
	public void setVfileupload(String vfileupload) {
		this.vfileupload = vfileupload;
	}
	public Integer getBisupload() {
		return bisupload;
	}
	public void setBisupload(Integer bisupload) {
		this.bisupload = bisupload;
	}
	public Integer getDocvarietyid() {
		return docvarietyid;
	}
	public void setDocvarietyid(Integer docvarietyid) {
		this.docvarietyid = docvarietyid;
	}
	public String getDocvarietyname() {
		return docvarietyname;
	}
	public void setDocvarietyname(String docvarietyname) {
		this.docvarietyname = docvarietyname;
	}
	public String getReleids() {
		return releids;
	}
	public void setReleids(String releids) {
		this.releids = releids;
	}
	public Integer getBisdefault() {
		return bisdefault;
	}
	public void setBisdefault(Integer bisdefault) {
		this.bisdefault = bisdefault;
	}
	public String getVtransform() {
		return vtransform;
	}
	public void setVtransform(String vtransform) {
		this.vtransform = vtransform;
	}
	
}
