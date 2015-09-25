package model.mold;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.IdEntity;

/**
 * 
* @Title: ��װϵͳ
* @ClassName: ModelVO 
* @Description: ģ�͹�������
* @author liuzy
* @date 2015-6-29 ����11:15:32
 */
@Entity
@Table(name="fz_model")
public class ModelVO extends IdEntity {
	private static final long serialVersionUID = 1L;
	//ģ�ͷ���		int
	private Integer typeid;
	//ģ�ͷ�������
	@Transient
	private String typeName;
	//ģ������		varchar(10)
	private String vname;
	//ģ���ļ�		varchar(100)
	private String vfileupload;	
	//�Ƿ��ϴ�
	private Integer bisupload;
	//�洢λ��		varchar
	private String folderid;
	//ʱ���
	private Timestamp ts;
	//dr
	private Integer dr;
	//��ע
	private String vmemo;
	//�Զ�����
	private String vdef1;//ʹ��Ʒ������
	private String vdef2;
	private String vdef3;
	private String vdef4;
	private String vdef5;
	
	public Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getVfileupload() {
		return vfileupload;
	}
	public void setVfileupload(String vfileupload) {
		this.vfileupload = vfileupload;
	}
	public Integer getBisupload() {
		return bisupload;
	}
	public void setBisupload(Integer bisupload) {
		this.bisupload = bisupload;
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
	public String getFolderid() {
		return folderid;
	}
	public void setFolderid(String folderid) {
		this.folderid = folderid;
	}
	
}
