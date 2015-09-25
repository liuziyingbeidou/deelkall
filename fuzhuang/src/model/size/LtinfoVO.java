package model.size;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import model.IdEntity;

/**
 * 
* @Title: 服装系统
* @ClassName: Ltinfo 
* @Description: 量体尺码信息表
* @author liuzy
* @date 2015-6-19 下午04:47:59
 */
@Entity
@Table(name="fz_size_ltinfo")
public class LtinfoVO extends IdEntity {

	private static final long serialVersionUID = 1L;
	//身高		numeric(20,8)
	private double nheightmny;	
	//体重		numeric(20,8)
	private double nweightmny;	
	//肩宽		numeric(20,8)
	private double nshouldermny;	
	//胸围		numeric(20,8)
	private double nbustmnyu;	
	//腰围		numeric(20,8)
	private double nwaistmny;	
	//臀围		numeric(20,8)
	private double nhipsmny	;
	//袖长		numeric(20,8)
	private double nsleevemny;	
	//上臀围		numeric(20,8)
	private double ntophipsmny;	
	//袖口围		numeric(20,8)
	private double ncuffmny	;
	//领围		numeric(20,8)
	private double nneckmny	;
	//后中长		numeric(20,8)
	private double ncentrebackmny;	
	//裤腰围		numeric(20,8)
	private double nkwaistmny	;
	//裤通档 		numeric(20,8)
	private double nktdmny	;
	//裤大腿围 		numeric(20,8)
	private double nkdtmny	;
	//小腿围 		numeric(20,8)
	private double nxtwmny	;
	//裤口围 		numeric(20,8)
	private double nkkwmny	;
	//裤长 		numeric(20,8)
	private double nkcmny	;
	//基本体型		int
	private Integer vbaseb	;
	//	颈		int
	private Integer vneedle;
	//肩斜		int
	private Integer vkickerx;
	//肩冲		int
	private Integer vkickerc;	
	//背		int
	private Integer vback;
	//胸		int
	private Integer vchest;
	//腹		int
	private Integer vabdomen;
	//腰		int
	private Integer vwaistline;
	//裤带		int
	private Integer vbelt;
	//臀		int
	private Integer vbutt;
	//腿长比例		int
	private Integer nlegmny;
	//宽松度		int
	private Integer vloosedeg;
	//照片url		varchar(255)
	private String vpictureurl	;
	//留言		varchar(255)
	private String vmessage	;
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
	
	public double getNheightmny() {
		return nheightmny;
	}
	public void setNheightmny(double nheightmny) {
		this.nheightmny = nheightmny;
	}
	public double getNweightmny() {
		return nweightmny;
	}
	public void setNweightmny(double nweightmny) {
		this.nweightmny = nweightmny;
	}
	public double getNshouldermny() {
		return nshouldermny;
	}
	public void setNshouldermny(double nshouldermny) {
		this.nshouldermny = nshouldermny;
	}
	public double getNbustmnyu() {
		return nbustmnyu;
	}
	public void setNbustmnyu(double nbustmnyu) {
		this.nbustmnyu = nbustmnyu;
	}
	public double getNwaistmny() {
		return nwaistmny;
	}
	public void setNwaistmny(double nwaistmny) {
		this.nwaistmny = nwaistmny;
	}
	public double getNhipsmny() {
		return nhipsmny;
	}
	public void setNhipsmny(double nhipsmny) {
		this.nhipsmny = nhipsmny;
	}
	public double getNsleevemny() {
		return nsleevemny;
	}
	public void setNsleevemny(double nsleevemny) {
		this.nsleevemny = nsleevemny;
	}
	public double getNtophipsmny() {
		return ntophipsmny;
	}
	public void setNtophipsmny(double ntophipsmny) {
		this.ntophipsmny = ntophipsmny;
	}
	public double getNcuffmny() {
		return ncuffmny;
	}
	public void setNcuffmny(double ncuffmny) {
		this.ncuffmny = ncuffmny;
	}
	public double getNneckmny() {
		return nneckmny;
	}
	public void setNneckmny(double nneckmny) {
		this.nneckmny = nneckmny;
	}
	public double getNcentrebackmny() {
		return ncentrebackmny;
	}
	public void setNcentrebackmny(double ncentrebackmny) {
		this.ncentrebackmny = ncentrebackmny;
	}
	public double getNkwaistmny() {
		return nkwaistmny;
	}
	public void setNkwaistmny(double nkwaistmny) {
		this.nkwaistmny = nkwaistmny;
	}
	public double getNktdmny() {
		return nktdmny;
	}
	public void setNktdmny(double nktdmny) {
		this.nktdmny = nktdmny;
	}
	public double getNkdtmny() {
		return nkdtmny;
	}
	public void setNkdtmny(double nkdtmny) {
		this.nkdtmny = nkdtmny;
	}
	public double getNxtwmny() {
		return nxtwmny;
	}
	public void setNxtwmny(double nxtwmny) {
		this.nxtwmny = nxtwmny;
	}
	public double getNkkwmny() {
		return nkkwmny;
	}
	public void setNkkwmny(double nkkwmny) {
		this.nkkwmny = nkkwmny;
	}
	public double getNkcmny() {
		return nkcmny;
	}
	public void setNkcmny(double nkcmny) {
		this.nkcmny = nkcmny;
	}
	public Integer getVbaseb() {
		return vbaseb;
	}
	public void setVbaseb(Integer vbaseb) {
		this.vbaseb = vbaseb;
	}
	public Integer getVneedle() {
		return vneedle;
	}
	public void setVneedle(Integer vneedle) {
		this.vneedle = vneedle;
	}
	public Integer getVkickerx() {
		return vkickerx;
	}
	public void setVkickerx(Integer vkickerx) {
		this.vkickerx = vkickerx;
	}
	public Integer getVkickerc() {
		return vkickerc;
	}
	public void setVkickerc(Integer vkickerc) {
		this.vkickerc = vkickerc;
	}
	public Integer getVback() {
		return vback;
	}
	public void setVback(Integer vback) {
		this.vback = vback;
	}
	public Integer getVchest() {
		return vchest;
	}
	public void setVchest(Integer vchest) {
		this.vchest = vchest;
	}
	public Integer getVabdomen() {
		return vabdomen;
	}
	public void setVabdomen(Integer vabdomen) {
		this.vabdomen = vabdomen;
	}
	public Integer getVwaistline() {
		return vwaistline;
	}
	public void setVwaistline(Integer vwaistline) {
		this.vwaistline = vwaistline;
	}
	public Integer getVbelt() {
		return vbelt;
	}
	public void setVbelt(Integer vbelt) {
		this.vbelt = vbelt;
	}
	public Integer getVbutt() {
		return vbutt;
	}
	public void setVbutt(Integer vbutt) {
		this.vbutt = vbutt;
	}
	public Integer getNlegmny() {
		return nlegmny;
	}
	public void setNlegmny(Integer nlegmny) {
		this.nlegmny = nlegmny;
	}
	public Integer getVloosedeg() {
		return vloosedeg;
	}
	public void setVloosedeg(Integer vloosedeg) {
		this.vloosedeg = vloosedeg;
	}
	public String getVpictureurl() {
		return vpictureurl;
	}
	public void setVpictureurl(String vpictureurl) {
		this.vpictureurl = vpictureurl;
	}
	public String getVmessage() {
		return vmessage;
	}
	public void setVmessage(String vmessage) {
		this.vmessage = vmessage;
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
