package model.auxiliary;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.IdEntity;


/** 
 * @Title: ��װϵͳ
 * @ClassName: AuxiliaryBVO 
 * @Description: ����-˳ɫ
 * @author liuzy
 * @date 2015-8-20 ����02:49:12  
 */
@Entity
@Table(name="fz_auxiliary_b")
public class AuxiliaryBVO extends IdEntity {

	//��������		int
	private Integer auxiliaryId;
	//��ʶ(0-���ϣ�1-���ϣ�2-���ϣ�3-��װ���ϣ�)		smallint
	private Integer iflag;	
	//Ʒ��id
	private Integer proclassid;
	//��		int
	private Integer icisLine;
	@Transient
	private String icisLineNM;
	//����		int
	private Integer icisButton;	
	@Transient
	private String icisButtonNM;
	//����		int
	private Integer icisLining;	
	@Transient
	private String icisLiningNM;
	//������		int
	private Integer icisXLining;
	@Transient
	private String icisXLiningNM;
	//������		int
	private Integer icisHBLining;
	@Transient
	private String icisHBLiningNM;
	//����		int
	private Integer icisBagging;	
	@Transient
	private String icisBaggingNM;
	//���		int
	private Integer icisComponent;	
	@Transient
	private String icisComponentNM;
	//����		int
	private Integer iciszipper;	
	@Transient
	private String iciszipperNM;
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
	public Integer getIcisLine() {
		return icisLine;
	}
	public void setIcisLine(Integer icisLine) {
		this.icisLine = icisLine;
	}
	public String getIcisLineNM() {
		return icisLineNM;
	}
	public void setIcisLineNM(String icisLineNM) {
		this.icisLineNM = icisLineNM;
	}
	public Integer getIcisButton() {
		return icisButton;
	}
	public void setIcisButton(Integer icisButton) {
		this.icisButton = icisButton;
	}
	public String getIcisButtonNM() {
		return icisButtonNM;
	}
	public void setIcisButtonNM(String icisButtonNM) {
		this.icisButtonNM = icisButtonNM;
	}
	public Integer getIcisLining() {
		return icisLining;
	}
	public void setIcisLining(Integer icisLining) {
		this.icisLining = icisLining;
	}
	public String getIcisLiningNM() {
		return icisLiningNM;
	}
	public void setIcisLiningNM(String icisLiningNM) {
		this.icisLiningNM = icisLiningNM;
	}
	public Integer getIcisXLining() {
		return icisXLining;
	}
	public void setIcisXLining(Integer icisXLining) {
		this.icisXLining = icisXLining;
	}
	public String getIcisXLiningNM() {
		return icisXLiningNM;
	}
	public void setIcisXLiningNM(String icisXLiningNM) {
		this.icisXLiningNM = icisXLiningNM;
	}
	public Integer getIcisHBLining() {
		return icisHBLining;
	}
	public void setIcisHBLining(Integer icisHBLining) {
		this.icisHBLining = icisHBLining;
	}
	public String getIcisHBLiningNM() {
		return icisHBLiningNM;
	}
	public void setIcisHBLiningNM(String icisHBLiningNM) {
		this.icisHBLiningNM = icisHBLiningNM;
	}
	public Integer getIcisBagging() {
		return icisBagging;
	}
	public void setIcisBagging(Integer icisBagging) {
		this.icisBagging = icisBagging;
	}
	public String getIcisBaggingNM() {
		return icisBaggingNM;
	}
	public void setIcisBaggingNM(String icisBaggingNM) {
		this.icisBaggingNM = icisBaggingNM;
	}
	public Integer getIcisComponent() {
		return icisComponent;
	}
	public void setIcisComponent(Integer icisComponent) {
		this.icisComponent = icisComponent;
	}
	public String getIcisComponentNM() {
		return icisComponentNM;
	}
	public void setIcisComponentNM(String icisComponentNM) {
		this.icisComponentNM = icisComponentNM;
	}
	public Integer getIciszipper() {
		return iciszipper;
	}
	public void setIciszipper(Integer iciszipper) {
		this.iciszipper = iciszipper;
	}
	public String getIciszipperNM() {
		return iciszipperNM;
	}
	public void setIciszipperNM(String iciszipperNM) {
		this.iciszipperNM = iciszipperNM;
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
	public Integer getProclassid() {
		return proclassid;
	}
	public void setProclassid(Integer proclassid) {
		this.proclassid = proclassid;
	}
	
}
