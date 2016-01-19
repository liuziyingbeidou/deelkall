/**   
 *
 * @version V1.0   
 */
package util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuzy
 * 
 */
public class CommUtil {
	
	/**
	 * 判断参数为null/""/"null"/"  " [],
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		boolean flag = true;
		if (obj != null) {
			String objs = obj.toString();
			if (!"null".equals(objs) && !"".equals(objs.trim())&&!"[]".equals(objs)) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 判断参数为null/""/"null"/"  "
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEm(Object obj) {
		boolean flag = true;
		if (obj != null) {
			String objs = obj.toString();
			if (!"null".equals(objs) && !"".equals(objs.trim())) {
				flag = false;
			}
		}
		return flag;
	}
	
	
	/**
	 * 数组中是否含有指定元素
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	public static boolean isContains(String[] arr, String targetValue) {
	    for (String s : arr) {
	        if (s.equals(targetValue)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * 用于解决乱码问题
	 * 转换编码
	 * 
	 * @param str
	 * @param encode "UTF-8"
	 * @return
	 */
	public static String toCharacterEncoding(String str,String encode){
		if(str == null) return null;
		String retStr = str;
		byte b[];
		try{ 
			b = str.getBytes("ISO8859_1");
			for(int i = 0; i < b.length; i++) {
				byte b1 = b[i];
				if (b1 == 63)
					break;//1
				else if(b1 > 0)
					continue;//2
				else if (b1 < 0) { //不可能为0，0为字符串结束符 
					 //小于0乱码
					retStr = new String(b, encode); 
					break; 
					}
				}
			} catch (UnsupportedEncodingException e) {
				//  e.printStackTrace();
				}
			return retStr;
	}
	
	/**
	 * @Description: Double To Integer
	 * @param @param price
	 * @param @return
	 * @return String
	 */
	public static String toInteger(Double price){
		if(price == null)
			return "0";
		return (int)Math.floor(price) + "";
	}
	
	/**
	 * @Description: 对价格进行处理 -算出的价格:
	 * 			个位数的抹去个位数，十位数为1～6的，自动变为6，十位数为0、7、8、9的，自动变为8；
	 * @param @param price 
	 * @param @return 
	 * @return String
	 */
	public static String toTurnPrice(Double price){
		
		String newPrice = (int)Math.floor(price) + "";
		
		Integer len = newPrice.length();
		
		if(len > 1){
			newPrice = newPrice.substring(0, len-1);
			if(newPrice.length() == 1){
				if("0123456".contains(newPrice)){
					return "60";
				}else if("789".contains(newPrice)){
					return "80";
				}
			}else{
				String c = newPrice.substring(len-2, len-1);
				if("0123456".contains(c)){
					return newPrice.substring(0, len-2) + "6" + "0";
				}else if("789".contains(c)){
					return newPrice.substring(0, len-2) + "8" + "0";
				}
			}
		}
		return newPrice;
	}

	/**
	 * @Description: 同款面料同时下订单，两件套或三件套的，按各单品价格相加之和。
	 * 例如：单西装上衣+西裤、单西装上衣+西裤+马夹。
	 * 相加后的总价个、十位数为00时，则在总数相加的基础上减掉20；
	 * 当总价个、十位数为20时，总数减掉40；当为40时，总数减掉60；为60和80时，不变。
	 * @param @param price
	 * @param @return
	 * @return String
	 */
	public static String toTurnPriceC(Double price){
		String newPrice = (int)Math.floor(price) + "";
		
		Integer len = newPrice.length();
		
		if(len > 1){
			String c = newPrice.substring(len-2, len);
			if("00".equals(c)){
				newPrice = (price - 20) + "";
			}else if("20".equals(c)){
				newPrice = (price - 40) + "";
			}else if("40".equals(c)){
				newPrice = (price - 60) + "";
			}
		}
		return newPrice;
	}
	
	/**
	 * 判断
	 */
	//1.判断字符串是否仅为数字:
	//	1>用JAVA自带的函数
		public static boolean isNumeric(String str){
		  for (int i = str.length();--i>=0;){   
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }
		//2>用正则表达式
		public static boolean isNumericByReg(String str){
		    Pattern pattern = Pattern.compile("[0-9]*");
		    return pattern.matcher(str).matches();   
		 }
		//3>用ascii码
		public static boolean isNumericByAscii(String str){
		   for(int i=str.length();--i>=0;){
		      int chr=str.charAt(i);
		      if(chr<48 || chr>57)
		         return false;
		   }
		   return true;
		}
		  
		//2.判断一个字符串的首字符是否为字母
		public   static   boolean   test(String   s)   
		  {   
		  char   c   =   s.charAt(0);   
		  int   i   =(int)c;   
		  if((i>=65&&i<=90)||(i>=97&&i<=122))   
		  {   
		  return   true;   
		  }   
		  else   
		  {   
		  return   false;   
		  }   
		  }
		  
		public     static   boolean   check(String   fstrData)   
		          {   
		                  char   c   =   fstrData.charAt(0);   
		                  if(((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z')))   
		                {   
		                        return   true;   
		                }else{   
		                        return   false;   
		                  }   
		          }
		  
		//3 .判断是否为汉字
		public static boolean vd(String str){
		   
		    char[] chars=str.toCharArray(); 
		    boolean isGB2312=false; 
		    for(int i=0;i<chars.length;i++){
		                byte[] bytes=(""+chars[i]).getBytes(); 
		                if(bytes.length==2){ 
		                            int[] ints=new int[2]; 
		                            ints[0]=bytes[0]& 0xff; 
		                            ints[1]=bytes[1]& 0xff; 
		                            
		  if(ints[0]>=0x81 && ints[0]<=0xFE &&  
		ints[1]>=0x40 && ints[1]<=0xFE){ 
		                                        isGB2312=true; 
		                                        break; 
		                            } 
		                } 
		    } 
		    return isGB2312; 
		}
		// 判断一个字符串是否都为数字  
		public static boolean isDigit(String strNum) {  
		    return strNum.matches("[0-9]{1,}");  
		}  
		  
		// 判断一个字符串是否都为数字  
		public static boolean isDigitByReg(String strNum) {  
		    Pattern pattern = Pattern.compile("[0-9]{1,}");  
		    Matcher matcher = pattern.matcher((CharSequence) strNum);  
		    return matcher.matches();  
		}  
		  
		   //截取数字  
		   public static String getNumbers(String content) {  
		       Pattern pattern = Pattern.compile("\\d+");  
		       Matcher matcher = pattern.matcher(content);  
		       while (matcher.find()) {  
		           return matcher.group(0);  
		       }  
		       return "";  
		   }  
		  
		// 截取非数字  
		public static String splitNotNumber(String content) {  
		    Pattern pattern = Pattern.compile("\\D+");  
		    Matcher matcher = pattern.matcher(content);  
		    while (matcher.find()) {  
		        return matcher.group(0);  
		    }
		    return "";  
		}  
		 
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2)
	{
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2)
	{
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 小数点以后10位，以后的数字四舍五入。
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2)
	{
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 定精度，以后的数字四舍五入。
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale)
	{
		if (scale < 0)
		{
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0)
		{
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
		
		public static void main(String[] args){
			System.out.println(getNumbers("123CM"));
		}
}
