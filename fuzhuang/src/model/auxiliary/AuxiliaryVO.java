package model.auxiliary;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.IdEntity;

/**
 * 
* @Title: 服装系统
* @ClassName: AuxiliaryVO 
* @Description: 面料、里料、辅料、包装材料、外购
* @author liuzy
* @date 2015-6-15 下午02:47:00
 */
@Entity
@Table(name="fz_auxiliary")
public class AuxiliaryVO extends IdEntity {

	/** 
	* @Fields serialVersionUID : 序列号
	*/ 
	private static final long serialVersionUID = 1L;

	//模块类别		char(4)
	private String vmoduletype;
	//花型id		int
	private Integer patternid;
	//花型名称{仅用于DataGrid显示}
	private String patternName;
	//成份id		int
	private Integer ingredientid;	
	//成分名称{仅用于DataGrid显示}
	private String ingredientName;
	//色系id		int
	private Integer colourid;
	//色系名称{仅用于DataGrid显示}
	private String colourName;
	//料件大类id		int
	private Integer materialsid;
	//料件子类id		int
	private Integer matechildid;
	//编码		char(20)
	private String vcode;
	//名称		varchar(30)
	private String vname;
	//环境光		char(4)
	private String ambient;
	//高光		char(4)
	private String specular;
	//面、里料图URL		varchar(255)
	private String vfileupload;
	//是否上传		smallint
	private Integer bisupload;
	//是否撞色		smallint
	private Integer biscontrastcolor;
	//是否贴布		smallint
	private Integer bispatch;
	//辅料品种id/包装品名id/档案类别id/外购品种id		int
	private Integer docvarietyid;
	private String docvarietyname;
	//材质/用途id		int
	private Integer useid;
	private String usename;
	//品牌id		int
	private Integer brandsid;
	private String brandsname;
	//规格id		int
	private Integer specid;
	private String specname;
	//时间戳
	private Timestamp ts;
	//dr
	private Integer dr;
	//备注
	private String vmemo;
	//自定义项
	private String vdef1;//是否有图使用
	private String vdef2;//数据流向使用
	private String vdef3;
	private String vdef4;
	private String vdef5;
	//是否默认数据
	private Integer bisdefault;
	//关联品类
	private String proclassids;
	//关联子部件
	private String subpartids;
	/**面料-start**/
	//品级		varchar(30)
	private String vrank;
	//采购单价		double(8,2)
	private Double dpurchasemny;
	/*//核算价		double(8,2)
	private Double daccountmny;
	//面料系数		double(8,2)
	private Double dcoefficientmny;*/
	//面料来源		varchar(30)
	private String vfrom;
	//顺色线		smallint
	@Transient
	private Integer icisLine;
	@Transient
	private String vcisLineName;
	//顺色扣子		smallint
	@Transient
	private Integer icisButton;	
	@Transient
	private String vcisButtonName;
	//顺色配件		smallint
	@Transient
	private Integer icisComponent;
	@Transient
	private String vcisComponentName;
	//顺色里料		smallint
	@Transient
	private Integer icisLining;	
	@Transient
	private String vcisLiningName;
	//顺色袋布		smallint
	@Transient
	private Integer icisBagging;
	@Transient
	private String vcisBaggingName;
	//配色拉链
	@Transient
	private Integer iciszipper;
	@Transient
	private String vciszipperName;
	//顺色袖里料
	@Transient
	private Integer icisXLining;
	@Transient
	private String vcisXLiningName;
	//马甲后背用料
	@Transient
	private Integer icisHBLining;
	@Transient
	private String vcisHBLiningName;
	//简称vsname、varchar(30)
	private String vsname;
	//料件细分类		smallint
	private Integer iautype;
	/**面料-end**/
	
	/**手机端-start**/
	//设备标识	smallint	0 （PC端）、1（移动端）
	private Integer deviceType;
	/**手机端-end**/
	//面料品牌id		int
	private Integer basedocId;
	//是否前展		smallint
	private Integer isClient;

	public String getVmoduletype() {
		return vmoduletype;
	}
	public void setVmoduletype(String vmoduletype) {
		this.vmoduletype = vmoduletype;
	}
	public Integer getPatternid() {
		return patternid;
	}
	public void setPatternid(Integer patternid) {
		this.patternid = patternid;
	}
	@Transient
	public String getPatternName() {
		return patternName;
	}
	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}
	public Integer getIngredientid() {
		return ingredientid;
	}
	public void setIngredientid(Integer ingredientid) {
		this.ingredientid = ingredientid;
	}
	@Transient
	public String getIngredientName() {
		return ingredientName;
	}
	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}
	public Integer getColourid() {
		return colourid;
	}
	public void setColourid(Integer colourid) {
		this.colourid = colourid;
	}
	@Transient
	public String getColourName() {
		return colourName;
	}
	public void setColourName(String colourName) {
		this.colourName = colourName;
	}
	public Integer getMaterialsid() {
		return materialsid;
	}
	public void setMaterialsid(Integer materialsid) {
		this.materialsid = materialsid;
	}
	public Integer getMatechildid() {
		return matechildid;
	}
	public void setMatechildid(Integer matechildid) {
		this.matechildid = matechildid;
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
	public String getAmbient() {
		return ambient;
	}
	public void setAmbient(String ambient) {
		this.ambient = ambient;
	}
	public String getSpecular() {
		return specular;
	}
	public void setSpecular(String specular) {
		this.specular = specular;
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
	public Integer getBiscontrastcolor() {
		return biscontrastcolor;
	}
	public void setBiscontrastcolor(Integer biscontrastcolor) {
		this.biscontrastcolor = biscontrastcolor;
	}
	public Integer getBispatch() {
		return bispatch;
	}
	public void setBispatch(Integer bispatch) {
		this.bispatch = bispatch;
	}
	public Integer getDocvarietyid() {
		return docvarietyid;
	}
	public void setDocvarietyid(Integer docvarietyid) {
		this.docvarietyid = docvarietyid;
	}
	public Integer getUseid() {
		return useid;
	}
	public void setUseid(Integer useid) {
		this.useid = useid;
	}
	public Integer getBrandsid() {
		return brandsid;
	}
	public void setBrandsid(Integer brandsid) {
		this.brandsid = brandsid;
	}
	public Integer getSpecid() {
		return specid;
	}
	public void setSpecid(Integer specid) {
		this.specid = specid;
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
	
	public String getDocvarietyname() {
		return docvarietyname;
	}
	public void setDocvarietyname(String docvarietyname) {
		this.docvarietyname = docvarietyname;
	}
	public String getUsename() {
		return usename;
	}
	public void setUsename(String usename) {
		this.usename = usename;
	}
	public String getBrandsname() {
		return brandsname;
	}
	public void setBrandsname(String brandsname) {
		this.brandsname = brandsname;
	}
	public String getSpecname() {
		return specname;
	}
	public void setSpecname(String specname) {
		this.specname = specname;
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
	public Integer getBisdefault() {
		return bisdefault;
	}
	public void setBisdefault(Integer bisdefault) {
		this.bisdefault = bisdefault;
	}
	public String getProclassids() {
		return proclassids;
	}
	public void setProclassids(String proclassids) {
		this.proclassids = proclassids;
	}
	public String getSubpartids() {
		return subpartids;
	}
	public void setSubpartids(String subpartids) {
		this.subpartids = subpartids;
	}
	public String getVrank() {
		return vrank;
	}
	public void setVrank(String vrank) {
		this.vrank = vrank;
	}
	public Double getDpurchasemny() {
		return dpurchasemny;
	}
	public void setDpurchasemny(Double dpurchasemny) {
		this.dpurchasemny = dpurchasemny;
	}
	public String getVfrom() {
		return vfrom;
	}
	public void setVfrom(String vfrom) {
		this.vfrom = vfrom;
	}
	public Integer getIcisLine() {
		return icisLine;
	}
	public void setIcisLine(Integer icisLine) {
		this.icisLine = icisLine;
	}
	public Integer getIcisButton() {
		return icisButton;
	}
	public void setIcisButton(Integer icisButton) {
		this.icisButton = icisButton;
	}
	public Integer getIcisComponent() {
		return icisComponent;
	}
	public void setIcisComponent(Integer icisComponent) {
		this.icisComponent = icisComponent;
	}
	public Integer getIcisLining() {
		return icisLining;
	}
	public void setIcisLining(Integer icisLining) {
		this.icisLining = icisLining;
	}
	public Integer getIcisBagging() {
		return icisBagging;
	}
	public void setIcisBagging(Integer icisBagging) {
		this.icisBagging = icisBagging;
	}
	public String getVcisLineName() {
		return vcisLineName;
	}
	public void setVcisLineName(String vcisLineName) {
		this.vcisLineName = vcisLineName;
	}
	public String getVcisButtonName() {
		return vcisButtonName;
	}
	public void setVcisButtonName(String vcisButtonName) {
		this.vcisButtonName = vcisButtonName;
	}
	public String getVcisComponentName() {
		return vcisComponentName;
	}
	public void setVcisComponentName(String vcisComponentName) {
		this.vcisComponentName = vcisComponentName;
	}
	public String getVcisLiningName() {
		return vcisLiningName;
	}
	public void setVcisLiningName(String vcisLiningName) {
		this.vcisLiningName = vcisLiningName;
	}
	public String getVcisBaggingName() {
		return vcisBaggingName;
	}
	public void setVcisBaggingName(String vcisBaggingName) {
		this.vcisBaggingName = vcisBaggingName;
	}
	public Integer getIciszipper() {
		return iciszipper;
	}
	public void setIciszipper(Integer iciszipper) {
		this.iciszipper = iciszipper;
	}
	public String getVciszipperName() {
		return vciszipperName;
	}
	public void setVciszipperName(String vciszipperName) {
		this.vciszipperName = vciszipperName;
	}
	public Integer getIcisXLining() {
		return icisXLining;
	}
	public void setIcisXLining(Integer icisXLining) {
		this.icisXLining = icisXLining;
	}
	public String getVcisXLiningName() {
		return vcisXLiningName;
	}
	public void setVcisXLiningName(String vcisXLiningName) {
		this.vcisXLiningName = vcisXLiningName;
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
	public Integer getIcisHBLining() {
		return icisHBLining;
	}
	public void setIcisHBLining(Integer icisHBLining) {
		this.icisHBLining = icisHBLining;
	}
	public String getVcisHBLiningName() {
		return vcisHBLiningName;
	}
	public void setVcisHBLiningName(String vcisHBLiningName) {
		this.vcisHBLiningName = vcisHBLiningName;
	}
	public Integer getIautype() {
		return iautype;
	}
	public void setIautype(Integer iautype) {
		this.iautype = iautype;
	}
	public Integer getBasedocId() {
		return basedocId;
	}
	public void setBasedocId(Integer basedocId) {
		this.basedocId = basedocId;
	}
	public Integer getIsClient() {
		return isClient;
	}
	public void setIsClient(Integer isClient) {
		this.isClient = isClient;
	}
	
}
