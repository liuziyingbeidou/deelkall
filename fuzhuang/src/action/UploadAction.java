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

    // ��װ�ϴ��ļ��������
    private File image;
    // ��װ�ϴ��ļ����͵�����
    private String imageContentType;
    // ��װ�ϴ��ļ���������
    private String imageFileName;
    // ��������ע�������
    private String savePath;
    private String saveMinPath;//����ͼ����·��
    //msg
    private String msg;
    //��ʾͼƬURL
    private String showURL;
    //�Ƿ���������ͼ
    private String isZoom;
    //ԭͼƬ����
    private String imageOldName;
    
    @Override
    public String execute() {
    	
    	HttpServletRequest request = ServletActionContext.getRequest();
    	//�ϴ�λ��
		String loc = request.getParameter("loc");
    	
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            // �����ļ������
            System.out.println(getSavePath());
            String uploadFileName = getImageFileName();
            String newFileName = null;
            // �õ���ǰʱ����1970��1��1��0ʱ0��0�뿪ʼ�߹��ĺ�����
            long now = System.currentTimeMillis();
            // �õ������ϴ��ļ���Ŀ¼����ʵ·��
            //File dir = new File(ServletActionContext.getServletContext().getRealPath(savePath));
            if(loc != null){//ģ��
            	savePath = savePath + "/" + loc;//linux�£�/�� ;windows�£�\\��
            }
            File dir = new File(savePath);
            // �����Ŀ¼�����ڣ��ʹ���
            if (!dir.exists()) {
               dir.mkdirs();
            }
            // Ϊ���������ļ����ǣ��ж��ϴ��ļ��Ƿ�����չ������ʱ�����Ϊ�µ��ļ���
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
            // �����ļ��ϴ���
            fis = new FileInputStream(getImage());
            
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            // ����   
            if("true".equals(isZoom)){
            	toZoom(imageFile); 
            }
        } catch (Exception e) {
            System.out.println("�ļ��ϴ�ʧ��");
            e.printStackTrace();
        } finally {
            close(fos, fis);
        }
        return SUCCESS;
    }
    
    /**
    * @Description: ��������������ͼ
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
    	
     // ����ϴ�ͼƬ ��� �� ѹ����ҪС ��ѹ��
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
    * @Description: ��������ͼ
    * @param @param imageFile
    * @param @throws Exception
    * @return void
     */
    public void zoom(File imageFile) throws Exception {   
        try {   
  
        	String minFileName = null;
            // ����ͼ���·��   
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
            java.io.File file = new java.io.File(saveurl); //����ղ��ϴ����ļ�
            Image src = javax.imageio.ImageIO.read(file); //����Image����
    		float tagsize = 80;
    		int old_w = src.getWidth(null); //�õ�Դͼ��
    		int old_h = src.getHeight(null);
    		int new_w = 0;
    		int new_h = 0; //�õ�Դͼ��
    		int tempsize;
    		float tempdouble;
    		if (old_w > old_h) {
    			tempdouble = old_w / tagsize;
    		} else {
    			tempdouble = old_h / tagsize;
    		}
    		new_w = Math.round(old_w / tempdouble);
    		new_h = Math.round(old_h / tempdouble);//������ͼ����
    		BufferedImage tag = new BufferedImage(new_w, new_h,
    				BufferedImage.TYPE_INT_RGB);
    		tag.getGraphics().drawImage(src, 0, 0, new_w, new_h,
    				null); //������С���ͼ
    		FileOutputStream newimage = new FileOutputStream(sImg); //������ļ���
    		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
    		encoder.encode(tag); //��JPEG����
    		newimage.close();
        } catch (IOException e) {  
        	System.out.println("�ļ��ϴ�ʧ��");
            e.printStackTrace();   
        }   
    }   

    /**
     * �����ϴ��ļ��ı���λ��
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
                System.out.println("FileInputStream�ر�ʧ��");
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                System.out.println("FileOutputStream�ر�ʧ��");
                e.printStackTrace();
            }
        }
    }

}