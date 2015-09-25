package model.scheme;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.IdEntity;


/** 
 * @Title: 服装系统
 * @ClassName: SchemeBVO 
 * @Description: 定价方案子表
 * @author liuzy
 * @date 2015-7-13 下午03:04:23  
 */
@Entity
@Table(name="fz_scheme_b")
public class SchemeBVO extends IdEntity {

	private static final long serialVersionUID = 1L;
	//	主表主键		int
	private Integer schemeid;
	//	最小面料采购单价		decimal(8,2)
	private Double dsmall_purchasemny;
	//	最大面料采购单价		decimal(8,2)
	private Double dbig_purchasemny;
	//	面料核算价		decimal(8,2)
	private Double daccountmny;
	//面料单耗(单位米)		decimal(8,2)
	private Double dunitmny;
	//面料系数
	private Double dcoefficientmny;
	//	工艺id		int
	private Integer basedocid;
	private String basedocName;
	//	工艺加工费(单位元)		decimal(8,2)
	private Double dprocefeemny;
	//	工艺加工系数		decimal(8,2)
	private Double dprocecoefmny;
	//	折扣(%)		decimal(8,2)
	private Double ddiscountmny;
	//成品价
	private Double dfproductmny;
	//备注		varchar(200)
	private String vmemo;
	//时间戳
	private Timestamp ts;
	//dr
	private Integer dr;
	//自定义项
	@Transient
	private String vdef1;//已使用品类id
	private String vdef2;//已使用工艺id
	private String vdef3;
	private String vdef4;
	private String vdef5;
	public Integer getSchemeid() {
		return schemeid;
	}
	public void setSchemeid(Integer schemeid) {
		this.schemeid = schemeid;
	}
	public Double getDsmall_purchasemny() {
		return dsmall_purchasemny;
	}
	public void setDsmall_purchasemny(Double dsmall_purchasemny) {
		this.dsmall_purchasemny = dsmall_purchasemny;
	}
	public Double getDbig_purchasemny() {
		return dbig_purchasemny;
	}
	public void setDbig_purchasemny(Double dbig_purchasemny) {
		this.dbig_purchasemny = dbig_purchasemny;
	}
	public Double getDaccountmny() {
		return daccountmny;
	}
	public void setDaccountmny(Double daccountmny) {
		this.daccountmny = daccountmny;
	}
	public Double getDunitmny() {
		return dunitmny;
	}
	public void setDunitmny(Double dunitmny) {
		this.dunitmny = dunitmny;
	}
	public Integer getBasedocid() {
		return basedocid;
	}
	public void setBasedocid(Integer basedocid) {
		this.basedocid = basedocid;
	}
	public Double getDprocefeemny() {
		return dprocefeemny;
	}
	public void setDprocefeemny(Double dprocefeemny) {
		this.dprocefeemny = dprocefeemny;
	}
	public Double getDprocecoefmny() {
		return dprocecoefmny;
	}
	public void setDprocecoefmny(Double dprocecoefmny) {
		this.dprocecoefmny = dprocecoefmny;
	}
	public Double getDdiscountmny() {
		return ddiscountmny;
	}
	public void setDdiscountmny(Double ddiscountmny) {
		this.ddiscountmny = ddiscountmny;
	}
	public Double getDcoefficientmny() {
		return dcoefficientmny;
	}
	public void setDcoefficientmny(Double dcoefficientmny) {
		this.dcoefficientmny = dcoefficientmny;
	}
	public Double getDfproductmny() {
		return dfproductmny;
	}
	public void setDfproductmny(Double dfproductmny) {
		this.dfproductmny = dfproductmny;
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
	public String getBasedocName() {
		return basedocName;
	}
	public void setBasedocName(String basedocName) {
		this.basedocName = basedocName;
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
