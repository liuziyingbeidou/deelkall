package model.bomtb;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import model.IdEntity;


/** 
 * @Title: 服装系统
 * @ClassName: BtcconfigBVO 
 * @Description: BTC标配子表：fz_btcconfig_b
 * @author liuzy
 * @date 2015-8-4 下午03:35:12  
 */
@Entity
@Table(name="fz_btcconfig_b")
public class BtcconfigBVO extends IdEntity {

	private static final long serialVersionUID = 1L;
	//主表主键		int
	private Integer btcId;
	//材料编号		varchar（15）
	private String vcode;
	//材料名称		varchar（30）
	private String vname;	
	//简码		varchar（10）
	private String vsname;	
	//规格		varchar（15）
	private String vspec;	
	//单耗		varchar（10）
	private Double nunitmny;	
	//作业编号		varchar（10）
	private String vjobnum;	
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
	//项次		varchar（10）
	private String vserial;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBtcId() {
		return btcId;
	}
	public void setBtcId(Integer btcId) {
		this.btcId = btcId;
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
	public String getVsname() {
		return vsname;
	}
	public void setVsname(String vsname) {
		this.vsname = vsname;
	}
	public String getVspec() {
		return vspec;
	}
	public void setVspec(String vspec) {
		this.vspec = vspec;
	}
	public String getVjobnum() {
		return vjobnum;
	}
	public void setVjobnum(String vjobnum) {
		this.vjobnum = vjobnum;
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
	public Double getNunitmny() {
		return nunitmny;
	}
	public void setNunitmny(Double nunitmny) {
		this.nunitmny = nunitmny;
	}
	public String getVserial() {
		return vserial;
	}
	public void setVserial(String vserial) {
		this.vserial = vserial;
	}
	
}
