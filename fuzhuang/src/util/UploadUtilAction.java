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
    private File fileupload; // 和JSP中input标记name同名  
    private String imageUrl;  
    private String attachmentUrl;  
    private String fileRealName;  
    private HttpServletResponse response;  
    // Struts2拦截器获得的文件名,命名规则，File的名字+FileName  
    // 如此处为 'fileupload' + 'FileName' = 'fileuploadFileName'  
    private String fileuploadFileName; // 上传来的文件的名字  
    public String uploadFile() {  
        String extName = ""; // 保存文件拓展名  
        String newFileName = ""; // 保存新的文件名  
        String nowTimeStr = ""; // 保存当前时间  
        PrintWriter out = null;  
        SimpleDateFormat sDateFormat;  
        Random r = new Random();  
        String savePath = ServletActionContext.getServletContext().getRealPath(  
                ""); // 获取项目根路径  
        savePath = savePath + "/user2/photo/";  
        HttpServletResponse response = ServletActionContext.getResponse();  
        response.setCharacterEncoding("gbk"); // 务必，防止返回文件名是乱码  
        // 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）  
        int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数  
        sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式  
        nowTimeStr = sDateFormat.format(new Date()); // 当前时间  
        // 获取拓展名  
        if (fileuploadFileName.lastIndexOf(".") >= 0) {  
            extName = fileuploadFileName.substring(fileuploadFileName  
                    .lastIndexOf("."));  
        }  
        try {  
            out = response.getWriter();  
            newFileName = nowTimeStr + rannum + extName; // 文件重命名后的名字  
            String filePath = savePath + newFileName;  
            filePath = filePath.replace("//", "/");  
            //检查上传的是否是图片  
            if (UtilCommon.checkIsImage(extName)) {  
                FileUtils.copyFile(fileupload, new File(filePath));  
                out.print("<font color='red'>" + fileuploadFileName  
                        + "上传成功!</font>#" + filePath + "#" + fileuploadFileName);  
            } else {  
                out.print("<font color='red'>上传的文件类型错误，请选择jpg,jpeg,png和gif格式的图片!</font>");  
            }  
            out.flush();  
            out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
            out.print("上传失败，出错啦!");  
        }  
        return null;  
    }  
    public String showImage() throws Exception {  
        // 根据图片地址构造file对象  
        File file = new File(imageUrl);  
        InputStream is = new FileInputStream(file);  
        Image image = ImageIO.read(is);// 读图片  
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