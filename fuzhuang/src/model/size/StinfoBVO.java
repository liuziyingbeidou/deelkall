package model.size;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import model.IdEntity;
@Entity
@Table(name="fz_size_stinfo_b")
public class StinfoBVO extends IdEntity {

	private static final long serialVersionUID = 1L;
	//主表主键
	private Integer stinfoId;
	//编码
	private String vcode;
	//名称
	private String vname;
	//标准尺码
	private String standarmny;
	//下浮
	private double downmny;
	//上浮
	private double upmny;
	//是否可调
	private Integer bisadjust;
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
	
	public Integer getStinfoId() {
		return stinfoId;
	}
	public void setStinfoId(Integer stinfoId) {
		this.stinfoId = stinfoId;
	}
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	
	public String getStandarmny() {
		return standarmny;
	}
	public void setStandarmny(String standarmny) {
		this.standarmny = standarmny;
	}
	public Integer getBisadjust() {
		return bisadjust;
	}
	public void setBisadjust(Integer bisadjust) {
		this.bisadjust = bisadjust;
	}
	public double getDownmny() {
		return downmny;
	}
	public void setDownmny(double downmny) {
		this.downmny = downmny;
	}
	public double getUpmny() {
		return upmny;
	}
	public void setUpmny(double upmny) {
		this.upmny = upmny;
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
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	
	
}
