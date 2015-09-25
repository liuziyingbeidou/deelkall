package model.mold;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.IdEntity;
/**
 * 
* @Title: 服装系统
* @ClassName: ModelBVO 
* @Description: 模型管理子表
* @author liuzy
* @date 2015-6-29 上午11:20:09
 */
@Entity
@Table(name="fz_model_b")
public class ModelBVO extends IdEntity {

	private static final long serialVersionUID = 1L;
	//	主表主键		int
	private Integer modelid;
	//特殊档案id		int
	private Integer specialid;	
	//关联类型
	private String vlinktype;

	//特殊档案类别(只用于显示)
	@Transient
	private String vspecialTypeName;
	//特殊档案名称(只用于显示)
	@Transient
	private String vspecialName;
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
	//自定义项
	private Integer idef1;
	private Integer idef2;
	private Integer idef3;
	private Integer idef4;
	private Integer idef5;
	
	public Integer getModelid() {
		return modelid;
	}
	public void setModelid(Integer modelid) {
		this.modelid = modelid;
	}
	public Integer getSpecialid() {
		return specialid;
	}
	public void setSpecialid(Integer specialid) {
		this.specialid = specialid;
	}
	public String getVspecialTypeName() {
		return vspecialTypeName;
	}
	public void setVspecialTypeName(String vspecialTypeName) {
		this.vspecialTypeName = vspecialTypeName;
	}
	public String getVspecialName() {
		return vspecialName;
	}
	public void setVspecialName(String vspecialName) {
		this.vspecialName = vspecialName;
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
	public String getVlinktype() {
		return vlinktype;
	}
	public void setVlinktype(String vlinktype) {
		this.vlinktype = vlinktype;
	}
	public Integer getIdef1() {
		return idef1;
	}
	public void setIdef1(Integer idef1) {
		this.idef1 = idef1;
	}
	public Integer getIdef2() {
		return idef2;
	}
	public void setIdef2(Integer idef2) {
		this.idef2 = idef2;
	}
	public Integer getIdef3() {
		return idef3;
	}
	public void setIdef3(Integer idef3) {
		this.idef3 = idef3;
	}
	public Integer getIdef4() {
		return idef4;
	}
	public void setIdef4(Integer idef4) {
		this.idef4 = idef4;
	}
	public Integer getIdef5() {
		return idef5;
	}
	public void setIdef5(Integer idef5) {
		this.idef5 = idef5;
	}
	
}
