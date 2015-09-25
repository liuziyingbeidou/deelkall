package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ��װϵͳ
 * @ClassName: ReadFile 
 * @Description: ��ȡ�ļ��µ������ļ�
 * @author liuzy
 * @date 2015-9-2 ����05:28:18
 */
public class ReadFile {
	    static List<String> flist = new ArrayList<String>();
        public ReadFile() {
        }
        
        /**
         * ��ȡĳ���ļ����µ������ļ�
         */
        public static List<String> readfile(String filepath) throws FileNotFoundException, IOException {
                try {
                        File file = new File(filepath);
                        if (!file.isDirectory()) {
                                System.out.println("absolutepath=" + file.getAbsolutePath());
                                System.out.println("�ļ�1��name=" + file.getName());

                        } else if (file.isDirectory()) {
                                String[] filelist = file.list();
                                for (int i = 0; i < filelist.length; i++) {
                                		if("min".equals(filelist[i])){
                                			continue;
                                		}
                                        File readfile = new File(filepath + "\\" + filelist[i]);
                                        if (!readfile.isDirectory()) {
                                                System.out.println("absolutepath="
                                                                + readfile.getAbsolutePath());
                                                System.out.println("Tname=" + readfile.getName());
                                                flist.add(readfile.getName());
                                        } else if (readfile.isDirectory()) {
                                                readfile(filepath + "\\" + filelist[i]);
                                        }
                                }
                        }
                } catch (Exception e) {
                        System.out.println("readfile()   Exception:" + e.getMessage());
                }
                return flist;
        }

        /**
         * ɾ��ĳ���ļ����µ������ļ��к��ļ�
         */
        public static boolean deletefile(String delpath)
                        throws FileNotFoundException, IOException {
                try {
                        File file = new File(delpath);
                        if (!file.isDirectory()) {
                            file.delete();
                        } /*else if (file.isDirectory()) {
                                System.out.println("2");
                                String[] filelist = file.list();
                                for (int i = 0; i < filelist.length; i++) {
                                        File delfile = new File(delpath + "\\" + filelist[i]);
                                        if (!delfile.isDirectory()) {
                                                System.out.println("absolutepath="
                                                                + delfile.getAbsolutePath());
                                                System.out.println("name=" + delfile.getName());
                                                delfile.delete();
                                                System.out.println("ɾ���ļ��ɹ�");
                                        } else if (delfile.isDirectory()) {
                                                deletefile(delpath + "\\" + filelist[i]);
                                        }
                                }
                                file.delete();
                        }*/
                } catch (Exception e) {
                        System.out.println("deletefile()   Exception:" + e.getMessage());
                }
                return true;
        }
        
        /**
         * ɾ��ĳ���ļ����µ������ļ��к��ļ�
         */
        public static boolean deletefile(String[] delpaths)
                        throws FileNotFoundException, IOException {
                try {
                		if(delpaths != null){
                			for(int i = 0; i < delpaths.length; i++){
                				 File file = new File(delpaths[i]);
                                 if (!file.isDirectory()) {
                                     file.delete();
                                 }
                			}
                		}
                } catch (Exception e) {
                        System.out.println("deletefile()   Exception:" + e.getMessage());
                }
                return true;
        }
        
        public static void main(String[] args) {
                try {
                        readfile("D:\\Apache2.2\\htdocs\\Clothes");
                        deletefile("D:\\Apache2.2\\htdocs\\Clothes\\liningImage\\min\\20150818135049437ze1tc130_min.jpg");
                } catch (FileNotFoundException ex) {
                } catch (IOException ex) {
                }
                System.out.println("ok");
        }

}
