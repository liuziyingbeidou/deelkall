package model.size;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.IdEntity;

/**
 * 
* @Title: 服装系统
* @ClassName: Stinfo 
* @Description: 标准尺码信息
* @author liuzy
* @date 2015-6-19 下午04:36:09
 */
@Entity
@Table(name="fz_size_stinfo")
public class StinfoVO extends IdEntity {

	private static final long serialVersionUID = 1L;
	//品类id
	private Integer proclassid;
	@Transient
	private String proclassName;
	//版型
	private Integer specid;
	//前片
	private Integer spec_qpid;
	//下口
	private Integer spec_xkid;
	
	//尺码号
	private String vsize;
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
	
	public String getProclassName() {
		return proclassName;
	}
	public void setProclassName(String proclassName) {
		this.proclassName = proclassName;
	}
	public String getVsize() {
		return vsize;
	}
	public void setVsize(String vsize) {
		this.vsize = vsize;
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
	public Integer getSpecid() {
		return specid;
	}
	public void setSpecid(Integer specid) {
		this.specid = specid;
	}
	public Integer getSpec_qpid() {
		return spec_qpid;
	}
	public void setSpec_qpid(Integer spec_qpid) {
		this.spec_qpid = spec_qpid;
	}
	public Integer getSpec_xkid() {
		return spec_xkid;
	}
	public void setSpec_xkid(Integer spec_xkid) {
		this.spec_xkid = spec_xkid;
	}
	
}
