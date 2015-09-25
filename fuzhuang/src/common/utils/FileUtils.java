package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/** 
 * @Title: 服装系统
 * @ClassName: FileUtils 
 * @Description: 文件操作类
 * @author liuzy
 * @date 2015-9-14 下午04:18:12  
 */
public class FileUtils {
	
	
	public static void GenerateToDirecory(String[] files,String srcpath,String topath,String split) throws Exception{
		/** 
         * 循环将指定文件copy到相应的目录下面 
         */  
        for (int l = 0; l < files.length; l++) {
        	if(files[l] == null){
        		continue;
        	}
            File f = new File(topath+split+files[l]); 
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs(); 
            copyFile(new File(srcpath,files[l]),f); 
            int index = files[l].lastIndexOf(".");
	        if (index != -1) {
	           String minFile = files[l].substring(0,index) +"_min"+ files[l].substring(index);
	           File fm = new File(topath+split+"min"+split+minFile); 
	           if (!fm.getParentFile().exists())
	               fm.getParentFile().mkdirs(); 
	           copyFile(new File(srcpath+split+"min",minFile),fm);
	        }
        }  
	}
	
	/** 
     *  
     * @param fileIn 要被copy的文件 
     * @param fileOutPut 将文件copy到那个目录下面 
     * @throws Exception 
     */  
    private static void copyFile(File fileIn,File fileOutPut) throws Exception  
    {  
        FileInputStream fileInputStream=new FileInputStream(fileIn);  
        FileOutputStream fileOutputStream=new FileOutputStream(fileOutPut);  
        byte[] by=new byte[1024];  
        int len;  
        while((len=fileInputStream.read(by))!=-1)  
        {  
            fileOutputStream.write(by, 0, len);  
        }  
        fileInputStream.close();  
        fileOutputStream.close();  
    }  
}
