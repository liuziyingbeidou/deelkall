package config;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;


public class Constant {
    
    
    public static final String INDEXURL = "frame!index.do";

    public static final String LOGINURL = "index.jsp";

    public static final int MENU_MAXLEVEL = 2;

    public static final int EXE_STATUS_SUCCESS = 1;

    public static final int EXE_STATUS_FALIURE = 0;
    
    /**
     * 设置的日志记录 名称yu 对应的URL
     * 用户日志显示用
     */
    public static Map<String,String> LOGDEFINE_MAP = new LinkedHashMap<String, String>();
    
    public static String LOGDEFINE_LASTDATE = "";

    // 系统常量
    public static final String LOGININFO = "LOGININFO"; 

    public static final String ROLE_CODE_ADMIN = "ROLE_ADMIN";

    public static final String ROLE_NAME_ADMIN = "管理员";

    public static final String USER_SA_LOGIN_NAME = "sa";

    public static final String USER_SA_USER_NAME = "超级管理员";

    public static final String USER_SA_PASSWORD = "bigbucks";

    public static final String FALSE = "false";

    public static final String TRUE = "true";

    public static final String YES = "y";

    public static final String NO = "n";

    public static final int INT_TRUE = 1;

    public static final int INT_FALSE = 0;

    public static final String UNLIMIT = "unlimit";  

    public static boolean PAGE_ENCODING = false;

    public static boolean SERVER_ENCODING = false;

    public static boolean SHOW_ALL_CATEGORG = false;

    public static boolean IGNORE_BAD_URL_FUNC = false;

    public static Map<String, String> serverInfoMap = new HashMap<String, String>();

    // 系统变量
    public static String CONFIG_PATH;

    public static int PAGE_SIZE = 15;
    public static Integer[] PAGE_SELECT = new Integer[]{5,10,15,20,25,30};
    

    public static Map<String, String> zoneCache = new HashMap<String, String>();

    public static Map<String, Map<String, String>> dictCache = new HashMap<String, Map<String, String>>();

    // 系统引用
    public static ApplicationContext AppContext;

    public static MessageSource MESSAGE;

    public static String TEMP_PATH = null;

    public static boolean enableMsgSender = false;

    public static boolean isDebugMode = true;

    public static String ServerInfo;

    // 提交提示信息
    public static String SUBMIT_MESSAGE = "系统建议的操作步骤是输入完毕先点击保存，等确认无误后再点击提交。您确定要提交吗？";

    // 统计分析统一的开始时间
    public static int STATISTIC_START_DATE = 2014;

    /**
     * 应用名称
     */
    public static final String DEFAULT_APP_NAME = "招标采购系统";

    // 用于走OA待办的标识，审批通过后不刷新父页面
    public static final String APP = "oa";

    // 评标类型(综合评分法)
    public static final String PB_TYPE_ZHPJF = "zhpjf";

    // 比较运算符 小于等于(用于技术权重比较)
    public static final String COMPARE_OPERATOR_LE = "<=";

    // 比较运算符 小于(用于技术权重比较)
    public static final String COMPARE_OPERATOR_LT = "<";

    // 比较运算符 等于(用于技术权重比较)
    public static final String COMPARE_OPERATOR_EQ = "=";

    // 比较运算符 大于等于(用于技术权重比较)
    public static final String COMPARE_OPERATOR_GE = ">=";

    // 比较运算符 大于(用于技术权重比较)
    public static final String COMPARE_OPERATOR_GT = ">";

    // 计价方式(固定总价)
    public static final String CALC_PRICE_TYPE_GDZJ = "ZJBG";

    // 入围供方总数30%
    public static final double ENROLL_HXSYJ_SUPPLIER_PERCENT = 0.3;

    // 招标类型(邀请)
    public static final int ZB_TYPE_INVITE = 1;

    // 招标类型(直委)
    public static final int ZB_TYPE_ENTRUST = 2;

    // 招标类型(协议)
    public static final int ZB_TYPE_PROTOCAL = 3;

    // 招标类型(集中使用)
    public static final int ZB_TYPE_CENTRAL = 4;

    // 最高价高次高价15%
    public static final double ZGJ_CGJ_PERCNET = 0.15;

    // 材料设备&行政财务采购金额≤10万元
    public static final int CLXZ_CGJE = 100000;

    // 工程施工类类采购金额≤30万元
    public static final int GCSG_CGJE = 300000;

    // 计价方式为固定总价
    public static final String JJFS_GDZJ = "ZJBG";

    // 计价方式为固定单价
    public static final String JJFS_GDDJ = "ZHDJBG";

    // 计价方式为费率
    public static final String JJFS_FL = "FL";
    
    /**
     * 技术分合格最低分数线
     */
    public static final double JSF_LIMIT = 60;
    
    /**
     * 全体供方明细
     */
    public static final String ALL_SUPPLIER_DETAIL = "all_supplier_detail";
    
    /**
     * 通过入围标准的供方明细
     */
    public static final String ENROLL_STANDARD_PASS_SUPPLIER_DETAIL = "enroll_standard_pass_supplier_detail";
    
    /**
     * 未通过入围标准的供方明细
     */
    public static final String ENROLL_STANDARD_UN_PASS_SUPPLIER_DETAIL = "enroll_standard_un_pass_supplier_detail";
    
}
