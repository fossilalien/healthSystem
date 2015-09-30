package com.tg.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet3 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pout = response.getWriter();
		try {
			// �õ�����ϴ��ļ�����ʵ·��
			String storePath = getServletContext()
					.getRealPath("/WEB-INF/files");

			// ���û���
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(new File(getServletContext().getRealPath("/temp")));//������ʱ���Ŀ¼
			// �ж�һ��form�Ƿ���enctype=multipart/form-data���͵�
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (!isMultipart) {
				System.out.println("��ɵ��");
				return;
			}
			// ServletFileUpload������
			ServletFileUpload upload = new ServletFileUpload(factory);
//			upload.setProgressListener(new ProgressListener() {
//				//pBytesRead����ǰ�Զ�ȡ�����ֽ���
//				//pContentLength���ļ��ĳ���
//				//pItems:�ڼ���
//				public void update(long pBytesRead, long pContentLength,
//						int pItems) {
//					System.out.println("�Ѷ�ȡ��"+pBytesRead+",�ļ���С��"+pContentLength+",�ڼ��"+pItems);
//				}
//				
//			});
//			upload.setFileSizeMax(4 * 1024 * 1024);// ���õ����ϴ��ļ��Ĵ�С
//			upload.setSizeMax(6 * 1024 * 1024);// �������ļ���С
			// ����
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					// ��ͨ�ֶ�
					String fieldName = item.getFieldName();
					String fieldValue = item.getString("UTF-8");
					System.out.println(fieldName + "=" + fieldValue);
				} else {
					// �õ�MIME����
					String mimeType = item.getContentType();
					// ֻ�����ϴ�ͼƬ
					 if(mimeType.startsWith("image")){
						// �ϴ��ֶ�
						InputStream in = item.getInputStream();
						// �ϴ����ļ���
						String fileName = item.getName();// C:\Documents and
						if(fileName==null||"".equals(fileName.trim())){
							continue;
						}
						// Settings\wzhting\桌面\a.txt
						// a.txt
						fileName = fileName
								.substring(fileName.lastIndexOf("\\") + 1);// a.txt
						fileName = UUID.randomUUID() + "_" + fileName;
						System.out.println(request.getRemoteAddr()+"=============="+fileName);
						// ���������
						// ��ɢ�洢Ŀ¼
						String newStorePath = makeStorePath(storePath, fileName);// ����
						// /WEB-INF/files���ļ���������һ���µĴ洢·��
						// /WEB-INF/files/1/12
						String storeFile = newStorePath + "\\" + fileName;// WEB-INF/files/1/2/sldfdslf.txt
	
						OutputStream out = new FileOutputStream(storeFile);
	
						byte b[] = new byte[1024];
						int len = -1;
						while ((len = in.read(b)) != -1) {
							out.write(b, 0, len);
						}
						out.close();
						in.close();
						item.delete();//ɾ����ʱ�ļ�
					}
				 }
			}
		} catch (org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException e) {
			// �����ļ�������Сʱ���쳣
			pout.write("�����ļ���С���ܳ���4M");
		} catch (org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException e) {
			// ���ļ�������Сʱ���쳣
			pout.write("���ļ���С���ܳ���6M");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ���� /WEB-INF/files���ļ���������һ���µĴ洢·�� /WEB-INF/files/1/12
	private String makeStorePath(String storePath, String fileName) {
		int hashCode = fileName.hashCode();
		int dir1 = hashCode & 0xf;// 0000~1111������0~15��16��
		int dir2 = (hashCode & 0xf0) >> 4;// 0000~1111������0~15��16��

		String path = storePath + "\\" + dir1 + "\\" + dir2; // WEB-INF/files/1/12
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();

		return path;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
