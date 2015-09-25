package model.auxiliary;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.IdEntity;


/** 
 * @Title: 服装系统
 * @ClassName: AuxiliaryBVO 
 * @Description: 辅料默认数据
 * @author liuzy
 * @date 2015-8-20 下午02:49:12  
 */
@Entity
@Table(name="fz_auxiliarydef_b")
public class AuxiliaryDefBVO extends IdEntity {

	private static final long serialVersionUID = 1L;
	//主表主键		int
	private Integer auxiliaryId;
	//标识(0-面料；1-里料；2-辅料；3-包装材料；)		smallint
	private Integer iflag;	
	//品类id
	private Integer proclassid;
	//默认值	int
	private Integer bisdefault;
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
	public Integer getAuxiliaryId() {
		return auxiliaryId;
	}
	public void setAuxiliaryId(Integer auxiliaryId) {
		this.auxiliaryId = auxiliaryId;
	}
	public Integer getIflag() {
		return iflag;
	}
	public void setIflag(Integer iflag) {
		this.iflag = iflag;
	}
	public Integer getProclassid() {
		return proclassid;
	}
	public void setProclassid(Integer proclassid) {
		this.proclassid = proclassid;
	}
	public Integer getBisdefault() {
		return bisdefault;
	}
	public void setBisdefault(Integer bisdefault) {
		this.bisdefault = bisdefault;
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
