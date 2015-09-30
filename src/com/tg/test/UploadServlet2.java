package com.tg.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
//���Ű���
public class UploadServlet2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//�õ�����ϴ��ļ�����ʵ·��
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			
			//���û���
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//�ж�һ��form�Ƿ���enctype=multipart/form-data���͵�
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(!isMultipart){
				System.out.println("��ɵ��");
				return;
			}
			//ServletFileUpload������
			ServletFileUpload upload = new ServletFileUpload(factory);
			//����
			List<FileItem> items = upload.parseRequest(request);
			for(FileItem item:items){
				if(item.isFormField()){
					//��ͨ�ֶ�
					String fieldName = item.getFieldName();
					String fieldValue = item.getString();
					System.out.println(fieldName+"="+fieldValue);
				}else{
					//�ϴ��ֶ�
					InputStream in = item.getInputStream();
					//�ϴ����ļ���
					String fileName = item.getName();//   C:\Documents and Settings\wzhting\桌面\a.txt        a.txt
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);//   a.txt
					//���������
					String storeFile = path+"\\"+fileName;
					OutputStream out = new FileOutputStream(storeFile);
					
					byte b[] = new byte[1024];
					int len = -1;
					while((len=in.read(b))!=-1){
						out.write(b,0,len);
					}
					out.close();
					in.close();
				}
			}
		} catch (FileUploadException e) {
			throw new RuntimeException("������æ");
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
