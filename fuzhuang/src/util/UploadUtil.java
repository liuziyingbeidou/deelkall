package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.struts2.ServletActionContext;


/**
 * 
 * �ϴ�ͼƬ�������� ��ͼƬ·��,����СͼƬ·��, ��ͼƬ�ļ���,����СͼƬ����, ����СͼƬ���,����СͼƬ�߶�, �Ƿ�ȱ�����(Ĭ��Ϊtrue))
 * 
 * @author Administrator
 * 
 */
public class UploadUtil
{
	private String imagePath = "/imageFile/" + new SimpleDateFormat("yyyyMMddHH").format(new Date()) + "";// ����ͼƬ·��

	/**
	 * 
	 * @param getUpload
	 *            ·��
	 * @param getUploadContentType
	 *            �ļ�����
	 * @param getUploadFileName
	 *            ԭ�ļ���
	 * @return
	 * @throws IOException
	 */
	public void uploadImage1(File getUpload, String getUploadContentType, String getUploadFileName) throws IOException
	{

		String getImagePath = ServletActionContext.getServletContext().getRealPath(imagePath);

		File image = new File(getImagePath);
		if (!image.exists())
		{
			image.mkdir();
		}

		// �õ��ϴ��ļ��ĺ�׺��
		String uploadName = getUploadContentType;
		System.out.println("ͼƬ���� ------------" + uploadName);

		String lastuploadName = uploadName.substring(uploadName.indexOf("/") + 1, uploadName.length());
		System.out.println("�õ��ϴ��ļ��ĺ�׺�� ------------" + lastuploadName);

		// �õ��ļ���������
		String fileNewName = generateFileName(getUploadFileName);
		System.out.println("// �õ��ļ��������� ------------" + fileNewName);

		// FileOutputStream fos = new FileOutputStream(getImagePath + "/" +
		// fileNewName);
		//		
		// FileInputStream fis = new FileInputStream(getUpload);
		// byte[] buffer = new byte[1024];
		// int len = 0;
		// while ((len = fis.read(buffer)) > 0) {
		// fos.write(buffer, 0, len);
		// }

		// ��󷵻�ͼƬ·��
		imagePath = imagePath + "/" + fileNewName;
		System.out.println(" ��ͼƬ·��   " + getUpload);
		System.out.println(" 		//��󷵻�ͼƬ·��   " + imagePath);

		BufferedImage srcBufferImage = ImageIO.read(getUpload);
		System.out.println(" w " + srcBufferImage.getWidth() + " w " + srcBufferImage.getHeight());
		BufferedImage scaledImage;
		ScaleImage scaleImage = ScaleImage.getInstance();
		int yw = srcBufferImage.getWidth();
		int yh = srcBufferImage.getHeight();
		int w = 110, h = 110;
		// ����ϴ�ͼƬ ��� �� ѹ����ҪС ��ѹ��
		if (w > yw && h > yh)
		{
			FileOutputStream fos = new FileOutputStream(getImagePath + "/" + fileNewName);

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
			FileOutputStream out = new FileOutputStream(getImagePath + "/" + fileNewName);
			ImageIO.write(scaledImage, "jpeg", out);

		}
	}

	/**
	 * ����ԭͼ���ƣ������һ����ʱ���ʽ��������
	 * 
	 * @param fileName
	 *            ��ԭͼ����
	 * @return
	 */
	private String generateFileName(String fileName)
	{
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String formatDate = format.format(new Date());
		int random = new Random().nextInt(10000);
		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);
		return formatDate + random + extension;
	}

	public String getImagepath()
	{
		return imagePath;
	}

}