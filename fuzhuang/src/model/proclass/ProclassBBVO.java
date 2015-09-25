package model.proclass;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import model.IdEntity;

/**
 * 
* @Title: 服装系统
* @ClassName: ProclassBBVO 
* @Description: 品类孙表
* @author liuzy
* @date 2015-6-15 下午03:41:14
 */
@Entity
@Table(name="fz_tem_proclass_bb")
public class ProclassBBVO extends IdEntity {

	/** 
	* @Fields serialVersionUID : 序列号
	*/ 
	private static final long serialVersionUID = 1L;

	//主表id		int
	private Integer proclassid;
	//子表id		int
	private Integer proclass_bid;
	//主部件id		int
	private Integer masterid;
	//子部件id		int
	private Integer subpartid;
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
	public Integer getProclassid() {
		return proclassid;
	}
	public void setProclassid(Integer proclassid) {
		this.proclassid = proclassid;
	}
	public Integer getProclass_bid() {
		return proclass_bid;
	}
	public void setProclass_bid(Integer proclass_bid) {
		this.proclass_bid = proclass_bid;
	}
	
	public Integer getMasterid() {
		return masterid;
	}
	public void setMasterid(Integer masterid) {
		this.masterid = masterid;
	}
	public Integer getSubpartid() {
		return subpartid;
	}
	public void setSubpartid(Integer subpartid) {
		this.subpartid = subpartid;
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

}
