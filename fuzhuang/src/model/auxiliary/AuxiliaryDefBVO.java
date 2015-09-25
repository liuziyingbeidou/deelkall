package model.auxiliary;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.IdEntity;


/** 
 * @Title: ��װϵͳ
 * @ClassName: AuxiliaryBVO 
 * @Description: ����Ĭ������
 * @author liuzy
 * @date 2015-8-20 ����02:49:12  
 */
@Entity
@Table(name="fz_auxiliarydef_b")
public class AuxiliaryDefBVO extends IdEntity {

	private static final long serialVersionUID = 1L;
	//��������		int
	private Integer auxiliaryId;
	//��ʶ(0-���ϣ�1-���ϣ�2-���ϣ�3-��װ���ϣ�)		smallint
	private Integer iflag;	
	//Ʒ��id
	private Integer proclassid;
	//Ĭ��ֵ	int
	private Integer bisdefault;
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
