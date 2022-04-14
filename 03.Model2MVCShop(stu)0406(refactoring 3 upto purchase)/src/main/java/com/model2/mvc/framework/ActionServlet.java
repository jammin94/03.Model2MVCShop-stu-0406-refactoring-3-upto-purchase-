package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	//Field
	private RequestMapping mapper;

	//Method
	@Override
	//WASŰ�ڸ��� ������ �ٷ� �����ϵ��� �Ǿ��ֳ�!
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources");
		//web.xml�� �ִ� init-param�±� �� param-name="resources�� ���� �о��, 
		//param-value(com/model2/mvc/resources/actionmapping.properties)�� �о��
		mapper=RequestMapping.getInstance(resources);
		
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		
		//debug�� 
		System.out.println("url = "+url);
		System.out.println("path = " +path);
		System.out.println("contextPath = "+contextPath);
		
		try{
			Action action = mapper.getAction(path); //request�� �´� action�� ã�´�.
			action.setServletContext(getServletContext());
			
			String resultPage=action.execute(request, response); //execute�� ���� forward or redirect �ּҸ� ã�´�.
			String result=resultPage.substring(resultPage.indexOf(":")+1); //�����ϱ�
			
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			//Navigation ������ ���⼭ �ϳ�!
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}