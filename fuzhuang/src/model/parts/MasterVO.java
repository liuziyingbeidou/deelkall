package model.parts;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import model.IdEntity;

/**
 * 
* @Title: 服装系统
* @ClassName: MasterVO 
* @Description: 主部件
* @author liuzy
* @date 2015-6-15 下午03:25:08
 */
@Entity
@Table(name="fz_tem_master")
public class MasterVO extends IdEntity {

	/** 
	* @Fields serialVersionUID : 序列号
	*/ 
	private static final long serialVersionUID = 1L;
	//编码		char(4)
	private String vcode;
	//主部件		varchar(30)
	private String vname;
	//时间戳
	private Timestamp ts;
	//dr
	private Integer dr;
	//备注
	private String vmemo;
	//自定义项
	private String vdef1;
	private String vdef2;
	private String vdef3;
	private String vdef4;
	private String vdef5;
	//排序
	private Integer isort;
	//简称vsname、varchar(30)
	private String vsname;
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
	public Integer getIsort() {
		return isort;
	}
	public void setIsort(Integer isort) {
		this.isort = isort;
	}
	public String getVsname() {
		return vsname;
	}
	public void setVsname(String vsname) {
		this.vsname = vsname;
	}
	

}
