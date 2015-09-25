package util;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
public class UploadUtilAction extends ActionSupport implements  
        ServletResponseAware {  
    private File fileupload; // ��JSP��input���nameͬ��  
    private String imageUrl;  
    private String attachmentUrl;  
    private String fileRealName;  
    private HttpServletResponse response;  
    // Struts2��������õ��ļ���,��������File������+FileName  
    // ��˴�Ϊ 'fileupload' + 'FileName' = 'fileuploadFileName'  
    private String fileuploadFileName; // �ϴ������ļ�������  
    public String uploadFile() {  
        String extName = ""; // �����ļ���չ��  
        String newFileName = ""; // �����µ��ļ���  
        String nowTimeStr = ""; // ���浱ǰʱ��  
        PrintWriter out = null;  
        SimpleDateFormat sDateFormat;  
        Random r = new Random();  
        String savePath = ServletActionContext.getServletContext().getRealPath(  
                ""); // ��ȡ��Ŀ��·��  
        savePath = savePath + "/user2/photo/";  
        HttpServletResponse response = ServletActionContext.getResponse();  
        response.setCharacterEncoding("gbk"); // ��أ���ֹ�����ļ���������  
        // ��������ļ�������ǰ������ʱ����+��λ�������Ϊ����ʵ����Ŀ�з�ֹ�ļ�ͬ�������еĴ���  
        int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // ��ȡ�����  
        sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // ʱ���ʽ���ĸ�ʽ  
        nowTimeStr = sDateFormat.format(new Date()); // ��ǰʱ��  
        // ��ȡ��չ��  
        if (fileuploadFileName.lastIndexOf(".") >= 0) {  
            extName = fileuploadFileName.substring(fileuploadFileName  
                    .lastIndexOf("."));  
        }  
        try {  
            out = response.getWriter();  
            newFileName = nowTimeStr + rannum + extName; // �ļ��������������  
            String filePath = savePath + newFileName;  
            filePath = filePath.replace("//", "/");  
            //����ϴ����Ƿ���ͼƬ  
            if (UtilCommon.checkIsImage(extName)) {  
                FileUtils.copyFile(fileupload, new File(filePath));  
                out.print("<font color='red'>" + fileuploadFileName  
                        + "�ϴ��ɹ�!</font>#" + filePath + "#" + fileuploadFileName);  
            } else {  
                out.print("<font color='red'>�ϴ����ļ����ʹ�����ѡ��jpg,jpeg,png��gif��ʽ��ͼƬ!</font>");  
            }  
            out.flush();  
            out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
            out.print("�ϴ�ʧ�ܣ�������!");  
        }  
        return null;  
    }  
    public String showImage() throws Exception {  
        // ����ͼƬ��ַ����file����  
        File file = new File(imageUrl);  
        InputStream is = new FileInputStream(file);  
        Image image = ImageIO.read(is);// ��ͼƬ  
        String imageType = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);  
        RenderedImage img = (RenderedImage) image;  
        OutputStream out = response.getOutputStream();  
        ImageIO.write(img, imageType, out);  
        out.flush();  
        out.close();  
        return null;  
    }  
    public File getFileupload() {  
        return fileupload;  
    }  
    public void setFileupload(File fileupload) {  
        this.fileupload = fileupload;  
    }  
    public String getImageUrl() {  
        return imageUrl;  
    }  
    public void setImageUrl(String imageUrl) {  
        this.imageUrl = imageUrl;  
    }  
    public String getAttachmentUrl() {  
        return attachmentUrl;  
    }  
    public void setAttachmentUrl(String attachmentUrl) {  
        this.attachmentUrl = attachmentUrl;  
    }  
    public String getFileRealName() {  
        return fileRealName;  
    }  
    public void setFileRealName(String fileRealName) {  
        this.fileRealName = fileRealName;  
    }  
    public void setServletResponse(HttpServletResponse response) {  
        this.response = response;  
    }  
    public String getFileuploadFileName() {  
        return fileuploadFileName;  
    }  
    public void setFileuploadFileName(String fileuploadFileName) {  
        this.fileuploadFileName = fileuploadFileName;  
    }  
      
}  