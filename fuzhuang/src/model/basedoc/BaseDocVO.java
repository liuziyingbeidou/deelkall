package model.basedoc;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import model.IdEntity;

/**
 * 
* @Title: ��װϵͳ
* @ClassName: BaseDocVO 
* @Description: ���͡�ɫϵ��Ʒ�ơ�����
* @author liuzy
* @date 2015-6-15 ����02:27:02
 */
@Entity
@Table(name="fz_base_doc")
public class BaseDocVO extends IdEntity {

	private static final long serialVersionUID = -8400393063460185068L;

	//�������		char(20)
	private String vdoctype;
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
	//����Ʒ��
	private String proclassids;
	//ԭ����id
	private Integer matechildid;
/*	//���ռӹ���		decimal(8,2)
	private Double dprocefeemny;
	//����ϵ��		decimal(8,2)
	private Double dprocecoefmny;	*/
	
	public String getVdoctype() {
		return vdoctype;
	}
	public void setVdoctype(String vdoctype) {
		this.vdoctype = vdoctype;
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
	public String getProclassids() {
		return proclassids;
	}
	public void setProclassids(String proclassids) {
		this.proclassids = proclassids;
	}
	public Integer getMatechildid() {
		return matechildid;
	}
	public void setMatechildid(Integer matechildid) {
		this.matechildid = matechildid;
	}
	
}
