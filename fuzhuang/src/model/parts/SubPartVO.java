package model.parts;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.IdEntity;

/**
 * 
* @Title: ��װϵͳ
* @ClassName: SubPartVO 
* @Description: �Ӳ���
* @author liuzy
* @date 2015-6-15 ����03:29:51
 */
@Entity
@Table(name="fz_tem_subpart")
public class SubPartVO extends IdEntity {

	/** 
	* @Fields serialVersionUID : ���к�
	*/ 
	private static final long serialVersionUID = 1L;

	//����		char(4)	
	private String vcode;
	//�Ӳ���		varchar(30)	
	private String vname;
	//����Դ		varchar(10)	���أ��ı�����
	private String vdatasource;
	//ʱ���
	private Timestamp ts;
	//dr
	private Integer dr;
	//��ע
	private String vmemo;
	//�Զ�����
	private String vdef1;//�豸��ʹ��
	private String vdef2;//����-��;��ʹ��
	private String vdef3;
	private String vdef4;
	private String vdef5;
	//����Ʒ��		int
	private Integer proclassid;
	//����Ʒ��		varchar(255)-�����ô�
	@Transient
	private String proclassids;
	private String proclassName;
	//	��������		varchar(20)
	private String vlinktype;
	//����
	private Integer isort;
	//�豸��ʶ		smallint
	private Integer deviceType;
	//�Ƿ���ϸ��		smallint
	private Integer bisgyxj;
	//	�Ƿ���ΪBOM		smallint
	private Integer bisbom;

	//�������ڴ����ӱ�����
	@Transient
	private String vfabric;//����
	@Transient
	private String vlining;//����
	@Transient
	private Integer iaccessories;//����
	@Transient
	private Integer ispecial;//���⵵��
	@Transient
	private Integer ioutorn;//����
	//���vsname��varchar(30)
	private String vsname;
	//������
	@Transient
	private Integer masterid;
	
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
	public String getVdatasource() {
		return vdatasource;
	}
	public void setVdatasource(String vdatasource) {
		this.vdatasource = vdatasource;
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

	public String getVfabric() {
		return vfabric;
	}
	public void setVfabric(String vfabric) {
		this.vfabric = vfabric;
	}

	public String getVlining() {
		return vlining;
	}
	public void setVlining(String vlining) {
		this.vlining = vlining;
	}

	public Integer getIaccessories() {
		return iaccessories;
	}
	public void setIaccessories(Integer iaccessories) {
		this.iaccessories = iaccessories;
	}
	
	public Integer getIspecial() {
		return ispecial;
	}
	public void setIspecial(Integer ispecial) {
		this.ispecial = ispecial;
	}
	public Integer getProclassid() {
		return proclassid;
	}
	public void setProclassid(Integer proclassid) {
		this.proclassid = proclassid;
	}
	public String getVlinktype() {
		return vlinktype;
	}
	public void setVlinktype(String vlinktype) {
		this.vlinktype = vlinktype;
	}
	public Integer getIoutorn() {
		return ioutorn;
	}
	public void setIoutorn(Integer ioutorn) {
		this.ioutorn = ioutorn;
	}
	public String getProclassName() {
		return proclassName;
	}
	public void setProclassName(String proclassName) {
		this.proclassName = proclassName;
	}
	public Integer getIsort() {
		return isort;
	}
	public void setIsort(Integer isort) {
		this.isort = isort;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getVsname() {
		return vsname;
	}
	public void setVsname(String vsname) {
		this.vsname = vsname;
	}
	public Integer getMasterid() {
		return masterid;
	}
	public void setMasterid(Integer masterid) {
		this.masterid = masterid;
	}
	public String getProclassids() {
		return proclassids;
	}
	public void setProclassids(String proclassids) {
		this.proclassids = proclassids;
	}
	public Integer getBisgyxj() {
		return bisgyxj;
	}
	public void setBisgyxj(Integer bisgyxj) {
		this.bisgyxj = bisgyxj;
	}
	public Integer getBisbom() {
		return bisbom;
	}
	public void setBisbom(Integer bisbom) {
		this.bisbom = bisbom;
	}

}
