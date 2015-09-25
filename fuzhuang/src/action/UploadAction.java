package action;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import util.ScaleImage;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings("serial")
public class UploadAction extends ActionSupport {

    // 封装上传文件域的属性
    private File image;
    // 封装上传文件类型的属性
    private String imageContentType;
    // 封装上传文件名的属性
    private String imageFileName;
    // 接受依赖注入的属性
    private String savePath;
    private String saveMinPath;//缩略图保存路径
    //msg
    private String msg;
    //显示图片URL
    private String showURL;
    //是否生成缩略图
    private String isZoom;
    //原图片名称
    private String imageOldName;
    
    @Override
    public String execute() {
    	
    	HttpServletRequest request = ServletActionContext.getRequest();
    	//上传位置
		String loc = request.getParameter("loc");
    	
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            // 建立文件输出流
            System.out.println(getSavePath());
            String uploadFileName = getImageFileName();
            String newFileName = null;
            // 得到当前时间自1970年1月1日0时0分0秒开始走过的毫秒数
            long now = System.currentTimeMillis();
            // 得到保存上传文件的目录的真实路径
            //File dir = new File(ServletActionContext.getServletContext().getRealPath(savePath));
            if(loc != null){//模型
            	savePath = savePath + "/" + loc;//linux下（/） ;windows下（\\）
            }
            File dir = new File(savePath);
            // 如果该目录不存在，就创建
            if (!dir.exists()) {
               dir.mkdirs();
            }
            // 为避免重名文件覆盖，判断上传文件是否有扩展名，以时间戳作为新的文件名
            int index = uploadFileName.lastIndexOf(".");
            if (index != -1) {
               newFileName = now + uploadFileName.substring(index);
               setImageContentType(uploadFileName.substring(index));
               setImageOldName(uploadFileName.substring(0,index));
            } else {
               newFileName = Long.toString(now);
            }
            
            setImageFileName(newFileName);
            
//            fos = new FileOutputStream(getSavePath() + "\\" + getImageFileName());
            File imageFile = new File(dir, newFileName);
            
            fos = new FileOutputStream(imageFile);
            // 建立文件上传流
            fis = new FileInputStream(getImage());
            
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            // 缩放   
            if("true".equals(isZoom)){
            	toZoom(imageFile); 
            }
        } catch (Exception e) {
            System.out.println("文件上传失败");
            e.printStackTrace();
        } finally {
            close(fos, fis);
        }
        return SUCCESS;
    }
    
    /**
    * @Description: 高清晰生成缩略图
    * @param 
    * @return void
     * @throws IOException 
     */
    public void toZoom(File getUpload) throws IOException{
    	String minFileName = null;
    	
    	BufferedImage srcBufferImage = ImageIO.read(getUpload);
    	BufferedImage scaledImage;
    	ScaleImage scaleImage = ScaleImage.getInstance();
		int yw = srcBufferImage.getWidth();
		int yh = srcBufferImage.getHeight();
		int w = 80, h = 80;
		
		String uploadFileName = getImageFileName();
        int index = uploadFileName.lastIndexOf(".");
        if (index != -1) {
        	minFileName = uploadFileName.substring(0, index) + "_min" + uploadFileName.substring(index);
        } else {
        	minFileName = uploadFileName+"_min";
        }
    	
     // 如果上传图片 宽高 比 压缩的要小 则不压缩
		if (w > yw && h > yh)
		{
			FileOutputStream fos = new FileOutputStream(saveMinPath + "/" + minFileName);

			FileInputStream fis = new FileInputStream(getUpload);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0)
			{
				fos.write(buffer, 0, len);
			}
		}
		else
		{
			scaledImage = scaleImage.imageZoomOut(srcBufferImage, w, h);
			FileOutputStream out = new FileOutputStream(saveMinPath + "/" + minFileName);
			ImageIO.write(scaledImage, "jpeg", out);
		}
    	
    }
    
    /**
     * 
    * @Description: 生成缩略图
    * @param @param imageFile
    * @param @throws Exception
    * @return void
     */
    public void zoom(File imageFile) throws Exception {   
        try {   
  
        	String minFileName = null;
            // 缩略图存放路径   
            //File todir = new File(ServletActionContext.getServletContext().getRealPath(saveMinPath));   
            File todir = new File(saveMinPath);
            if (!todir.exists()) {   
                todir.mkdir();   
            }   
  
            if (!imageFile.isFile())  
           // if (imageFile == null || "".equals(imageFile))   
                throw new Exception(imageFile + " is not image file error in CreateThumbnail!");   
  
            String uploadFileName = getImageFileName();
            int index = uploadFileName.lastIndexOf(".");
            if (index != -1) {
            	minFileName = uploadFileName.substring(0, index) + "_min" + uploadFileName.substring(index);
            } else {
            	minFileName = uploadFileName+"_min";
            }
            
            File sImg = new File(todir, minFileName);  
            
            String saveurl = getSavePath() + "/" + uploadFileName;
            java.io.File file = new java.io.File(saveurl); //读入刚才上传的文件
            Image src = javax.imageio.ImageIO.read(file); //构造Image对象
    		float tagsize = 80;
    		int old_w = src.getWidth(null); //得到源图宽
    		int old_h = src.getHeight(null);
    		int new_w = 0;
    		int new_h = 0; //得到源图长
    		int tempsize;
    		float tempdouble;
    		if (old_w > old_h) {
    			tempdouble = old_w / tagsize;
    		} else {
    			tempdouble = old_h / tagsize;
    		}
    		new_w = Math.round(old_w / tempdouble);
    		new_h = Math.round(old_h / tempdouble);//计算新图长宽
    		BufferedImage tag = new BufferedImage(new_w, new_h,
    				BufferedImage.TYPE_INT_RGB);
    		tag.getGraphics().drawImage(src, 0, 0, new_w, new_h,
    				null); //绘制缩小后的图
    		FileOutputStream newimage = new FileOutputStream(sImg); //输出到文件流
    		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
    		encoder.encode(tag); //近JPEG编码
    		newimage.close();
        } catch (IOException e) {  
        	System.out.println("文件上传失败");
            e.printStackTrace();   
        }   
    }   

    /**
     * 返回上传文件的保存位置
     * 
     * @return
     */
    public String getSavePath() throws Exception{
       // return ServletActionContext.getServletContext().getRealPath(savePath); 
        return this.savePath; 
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
    
    public String getSaveMinPath() {
		return saveMinPath;
	}

	public void setSaveMinPath(String saveMinPath) {
		this.saveMinPath = saveMinPath;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getIsZoom() {
		return isZoom;
	}

	public void setIsZoom(String isZoom) {
		this.isZoom = isZoom;
	}

	public String getShowURL() {
		return showURL;
	}

	public void setShowURL(String showURL) {
		this.showURL = showURL;
	}

	public String getImageOldName() {
		return imageOldName;
	}

	public void setImageOldName(String imageOldName) {
		this.imageOldName = imageOldName;
	}

	private void close(FileOutputStream fos, FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                System.out.println("FileInputStream关闭失败");
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                System.out.println("FileOutputStream关闭失败");
                e.printStackTrace();
            }
        }
    }

}