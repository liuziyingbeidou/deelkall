package model.sp.system;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import model.IdEntity;

/**
 * @Title: ��װϵͳ
 * @ClassName: SpUser 
 * @Description: �ŵ��û�����
 * @author liuzy
 * @date 2015-8-27 ����09:34:33
 */

@Entity
@Table(name="fz_sp_user")
public class SpUserVO extends IdEntity {

	private static final long serialVersionUID = 1L;
	//�û���
	private String vusername;
	//��¼����
	private String vpwd;
	//�û�����
	private String vtruename;
	//�û�״̬
	private Integer islock;
	//ʱ���
	private Timestamp ts;
	//dr
	private Integer dr;
	//��ע
	private String vmemo;
	//�Զ�����
	private String vdef1;//��ʹ���û�״̬
	private String vdef2;
	private String vdef3;
	private String vdef4;
	private String vdef5;
	public String getVusername() {
		return vusername;
	}
	public void setVusername(String vusername) {
		this.vusername = vusername;
	}
	public String getVpwd() {
		return vpwd;
	}
	public void setVpwd(String vpwd) {
		this.vpwd = vpwd;
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
	public String getVtruename() {
		return vtruename;
	}
	public void setVtruename(String vtruename) {
		this.vtruename = vtruename;
	}
	public Integer getIslock() {
		return islock;
	}
	public void setIslock(Integer islock) {
		this.islock = islock;
	}
	
}
