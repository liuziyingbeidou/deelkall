package model.proclass;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.IdEntity;

/**
 * 
* @Title: ��װϵͳ
* @ClassName: ProclassVO 
* @Description: Ʒ���
* @author liuzy
* @date 2015-6-15 ����03:37:20
 */
@Entity
@Table(name="fz_tem_proclass")
public class ProclassVO extends IdEntity {

	/** 
	* @Fields serialVersionUID : ���к�
	*/ 
	private static final long serialVersionUID = 1L;

	//�ܷ���id		int
	private Integer classid;
	@Transient
	private String className;
	//��Ʒ��id		int
	private Integer subclassid;
	@Transient
	private String subclassName;
	//����		char(4)
	private String vcode;
	//����		varchar(30)
	private String vname;
	//ʱ���
	private Timestamp ts;
	//dr
	private Integer dr;
	//��ע
	private String vmemo;
	//�Զ�����
	private String vdef1;
	private String vdef2;
	private String vdef3;
	private String vdef4;
	private String vdef5;
	//����
	private Integer isort;
	/**�ֻ���-start**/
	//�豸��ʶ	smallint	0 ��PC�ˣ���1���ƶ��ˣ�
	private Integer deviceType;
	//��Ʒͼ		varchar(50)
	private String vfdrawing;
	/**�ֻ���-end**/
	//���vsname��varchar(30)
	private String vsname;
	public Integer getClassid() {
		return classid;
	}
	public void setClassid(Integer classid) {
		this.classid = classid;
	}
	public Integer getSubclassid() {
		return subclassid;
	}
	public void setSubclassid(Integer subclassid) {
		this.subclassid = subclassid;
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
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSubclassName() {
		return subclassName;
	}
	public void setSubclassName(String subclassName) {
		this.subclassName = subclassName;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getVfdrawing() {
		return vfdrawing;
	}
	public void setVfdrawing(String vfdrawing) {
		this.vfdrawing = vfdrawing;
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
