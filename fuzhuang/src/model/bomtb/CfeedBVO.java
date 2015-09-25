package model.bomtb;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import model.IdEntity;


/** 
 * @Title: ��װϵͳ
 * @ClassName: CfeedBVO 
 * @Description: �����ӱ�fz_cfeed_b 
 * @author liuzy
 * @date 2015-8-4 ����03:29:17  
 */
@Entity
@Table(name="fz_cfeed_b")
public class CfeedBVO extends IdEntity {

	private static final long serialVersionUID = 1L;
	//��������		int
	private Integer feedid;
	//ƴɫ��λ����		varchar(15)
	private String vcode;	
	//ƴɫ��λ		varchar(30)
	private String vname;	
	//��λ״̬		varchar(5)
	private String vstate;	
	//���ϣ���λ���ף�		varchar(10)
	private Double nunitmny;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFeedid() {
		return feedid;
	}
	public void setFeedid(Integer feedid) {
		this.feedid = feedid;
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
	public String getVstate() {
		return vstate;
	}
	public void setVstate(String vstate) {
		this.vstate = vstate;
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
	public Double getNunitmny() {
		return nunitmny;
	}
	public void setNunitmny(Double nunitmny) {
		this.nunitmny = nunitmny;
	}
	
}
