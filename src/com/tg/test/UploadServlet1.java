package com.tg.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadServlet1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String username = request.getParameter("username");
//		String f1 = request.getParameter("f1");
//		System.out.println(username);
//		System.out.println(f1);
		
		InputStream in = request.getInputStream();//��������ʽ��ȡ���������е�����.�����������ĵ����ݣ���һ������
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String data = null;
		while((data=br.readLine())!=null){
			System.out.println(data);
		}
		br.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
