/**   
 *
 * @version V1.0   
 */
package util;

import java.io.UnsupportedEncodingException;
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
				if("123456".contains(newPrice)){
					return "60";
				}else if("0789".contains(newPrice)){
					return "80";
				}
			}else{
				String c = newPrice.substring(len-2, len-1);
				if("123456".contains(c)){
					return newPrice.substring(0, len-2) + "6" + "0";
				}else if("0789".contains(c)){
					return newPrice.substring(0, len-2) + "8" + "0";
				}
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
		
		public static void main(String[] args){
			System.out.println(getNumbers("123CM"));
		}
}
