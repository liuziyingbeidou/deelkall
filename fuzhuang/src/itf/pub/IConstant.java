package itf.pub;

/**
 * 
* @Title: 服装
* @ClassName: IConstant 
* @Description: 常量类
* @author lzy
* @date 2015-6-11 上午10:38:01
 */
public interface IConstant {

	public static final String SYSTEMTYPE = "M";
	
	/**总分类**/
	//男装
	public static final String MENSWEAR = "menswear";
	public static final String MENSWEAR_zh_CH = "男装";
	//女装
	public static final String LADIES = "ladies";
	public static final String LADIES_zh_CH = "女装";
	
	/**模块-start**/
	//面料-标识
	public static final String MOD_FABRIC = "mod_fabric";
	//里料-标识
	public static final String MOD_LINING = "mod_lining";
	//辅料-标识
	public static final String MOD_ACCESSORIES= "mod_accessories";
	//包装材料-标识
	public static final String MOD_PACKING = "mod_packing";
	//特殊档案-标识
	public static final String MOD_SPECIAL = "mod_special";
	//配饰管理-标识
	public static final String MOD_OUTSOURCE = "mod_outsource";
	/**模块-end**/
	
	/**档案类别-start**/
	//花型
	public static final String DOC_PATTERN = "doc_pattern";
	//色系
	public static final String DOC_COLOUR = "doc_colour";
	//品牌
	public static final String DOC_BRAND = "doc_brand";
	//面料品牌-add by liuzy 2015-08-26
	public static final String DOC_FAR_BRAND = "doc_far_brand";
	//工艺
	public static final String DOC_TECHNOLOGY = "doc_technology";
	//成份
	public static final String DOC_INGREDIENTS = "doc_ingredients";
	//材质
	public static final String DOC_MATERIAL = "doc_material";
	//用途
	public static final String DOC_USE = "doc_use";
	//规格
	public static final String DOC_SPEC = "doc_spec";
	/**档案类别-end**/
	
	/**子部件内容加载方式-start**/
	//加载
	public static final String SUB_LOAD = "load";
	//文本
	public static final String SUB_INPUT = "input";
	//无
	public static final String SUB_nothing = "nothing";
	/**子部件内容加载方式-end**/
	
	/**体型特征档案类别-start**/
	//基本体型
	public static final String  SIZE_BASEB = "baseb";
	//颈长
	public static final String  SIZE_NEEDLE = "needle";
	//肩斜
	public static final String  SIZE_KICKERX = "kickerx";
	//肩冲
	public static final String  SIZE_KICKERC = "kickerc";
	//背
	public static final String  SIZE_BACK = "back";
	//胸
	public static final String  SIZE_CHEST = "chest";
	//腹
	public static final String  SIZE_ABDOMEN = "abdomen";
	//腰
	public static final String  SIZE_WAISTLINE = "waistline";
	//裤带
	public static final String  SIZE_BELT = "belt";
	//臀
	public static final String  SIZE_BUTT = "butt";
	//腿长比例
	public static final String  SIZE_LEG = "leg";
	//宽松度
	public static final String  SIZE_LOOSEDEG = "loosedeg";
	
	//基本体型
	public static final String  SIZE_BASEB_TITLE = "基本体型档案";
	//颈长
	public static final String  SIZE_NEEDLE_TITLE = "颈长档案";
	//肩斜
	public static final String  SIZE_KICKERX_TITLE = "肩斜档案";
	//肩冲
	public static final String  SIZE_KICKERC_TITLE = "肩冲档案";
	//背
	public static final String  SIZE_BACK_TITLE = "背档案";
	//胸
	public static final String  SIZE_CHEST_TITLE = "胸档案";
	//腹
	public static final String  SIZE_ABDOMEN_TITLE = "腹档案";
	//腰
	public static final String  SIZE_WAISTLINE_TITLE = "腰档案";
	//裤带
	public static final String  SIZE_BELT_TITLE = "裤带档案";
	//臀
	public static final String  SIZE_BUTT_TITLE = "臀档案";
	//腿长比例
	public static final String  SIZE_LEG_TITLE = "腿长比例档案";
	//宽松度
	public static final String  SIZE_LOOSEDEG_TITLE = "宽松度档案";
	/**体型特征档案类别-end**/
	//子部件
	public static final String  SPEC_BX = "版型";
	public static final String  SPEC_QP = "前片";
	public static final String  SPEC_XK = "下口";
	
	//过滤-加载默认值-主部件
	public static final String  MAIN_SPEC = "特殊";
	public static final String  MAIN_XZ = "绣字";
	public static final String  MAIN_DP = "配搭";
	//档案类别
	public static final String  ACCES_BUTTON = "纽扣";
	/****/
	/*****定价方案******/
	public static final String SCH_DZ = "sch_dz";
	public static final String SCH_CP = "sch_cp";
	//定价获取不到是默认：0
	public static final String MSG_SCH = "0";
	
	/**面料上传路径-start**/
	//PC面料原图
	public static final String UPLOAD_PC_FRA_SOURCE = "D:\\Apache2.2\\htdocs\\Clothes\\fabricImage";//"/usr/local/nginx/html/Clothes/fabricImage";//"/opt/nginx/html/3DRES/Clothes/fabricImage";//
	//PC面料小图80x80
	public static final String UPLOAD_PC_FRA_MIN = "D:\\Apache2.2\\htdocs\\Clothes\\fabricImage\\min";//"/usr/local/nginx/html/Clothes/fabricImage/min";//"/opt/nginx/html/3DRES/Clothes/fabricImage/min";//
	/**面料上传路径-end**/
	//西服上衣简称
	public static final String PRO_XIFUSHANGYI_ID = "xifushangyi";
	//BOM分隔符
	public static final String BOM_SPLIT = ";;;";
	//前台传入的BOM返回id间分割符
	public static final String BOM_3D_SPLIT = "#";
	//前台传入的BOM套装分割符
	public static final String BOM_3DTZ_SPLIT = "&";
	//返回前台时，id和BOm的分隔符
	public static final String BOM_IDS_3D_SPLIT = "@";
	//规格中 分隔符
	public static final String SPEC_3D_SPLIT = "#";
	
	/**以下是BOM表料件对应的简码**/
	//面料
	public static final String BOM_JC_FABRIC = "fabric";
	//里料
	public static final String BOM_JC_LINING = "lining";
	//袖里料
	public static final String BOM_JC_SLEEVELINING = "sleeveLining";
	//后背用料
	public static final String BOM_JC_BACKFABRIC = "backFabric";
	//纽扣
	public static final String BOM_JC_NIUKOU = "niukou";
	//拉链
	public static final String BOM_JC_LALIAN = "lalian";
	//袋布
	public static final String BOM_JC_DAIBU = "daibu";
	
	/**以下是子部件对应简码**/
	//顺色编码
	public static final String PART_CODE_SS = "SS";
	//同里料
	public static final String PART_CODE_T1 = "t1";
	//撞色
	public static final String PART_SNAMW_ZS = "zhuangse";
	//贴布
	public static final String PART_SNAMW_TB = "tiebu";
	//特殊锁眼-BOM简称
	public static final String PART_SNAMW_SY = "suoyan";
	//珠边--BOM简称
	public static final String PART_SNAMW_ZB = "zhubian";
	//扣位数
	public static final String PART_SNAMW_KWS = "kouweishu";
	//袖扣数
	public static final String PART_SNAMW_XKS = "xiukoushu";
	//纽扣号
	public static final String PART_SNAMW_NKH = "niukouhao";
	
	//耗料配色面料、肘部面料，作业编号为0001；特殊线、珠边线作业编号为0001；
	public static final String VJOBNUM = "0001";
	
}
