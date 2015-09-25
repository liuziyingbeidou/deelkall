package model.parts;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import model.IdEntity;

/**
 * 
* @Title: ��װϵͳ
* @ClassName: SubPartBVO 
* @Description: �Ӳ����ӱ�
* @author liuzy
* @date 2015-6-15 ����03:32:17
 */
@Entity
@Table(name="fz_tem_subpart_b")
public class SubPartBVO extends IdEntity {

	/** 
	* @Fields serialVersionUID : ���к�
	*/ 
	private static final long serialVersionUID = 1L;

	//����id		int
	private Integer subpartid;
	//ģ�����		char(4)
	private String vmoduletype;
	//�������id�����⵵�����id		int
	private Integer docvarietyid;
	//����ѡ��
	private String vcolorOrpatch;
	//����ѡ��
	private String vlin;
	
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
	
	public String getVcolorOrpatch() {
		return vcolorOrpatch;
	}
	public void setVcolorOrpatch(String vcolorOrpatch) {
		this.vcolorOrpatch = vcolorOrpatch;
	}
	public String getVlin() {
		return vlin;
	}
	public void setVlin(String vlin) {
		this.vlin = vlin;
	}
	public Integer getSubpartid() {
		return subpartid;
	}
	public void setSubpartid(Integer subpartid) {
		this.subpartid = subpartid;
	}
	
	
	public String getVmoduletype() {
		return vmoduletype;
	}
	public void setVmoduletype(String vmoduletype) {
		this.vmoduletype = vmoduletype;
	}
	public Integer getDocvarietyid() {
		return docvarietyid;
	}
	public void setDocvarietyid(Integer docvarietyid) {
		this.docvarietyid = docvarietyid;
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
