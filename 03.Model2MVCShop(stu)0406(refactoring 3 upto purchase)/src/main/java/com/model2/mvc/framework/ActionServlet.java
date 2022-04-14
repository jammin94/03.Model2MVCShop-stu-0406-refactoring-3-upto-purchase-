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
	//WAS키자마자 무조건 바로 실행하도록 되어있네!
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources");
		//web.xml에 있는 init-param태그 내 param-name="resources인 것을 읽어와, 
		//param-value(com/model2/mvc/resources/actionmapping.properties)를 읽어옴
		mapper=RequestMapping.getInstance(resources);
		
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		
		//debug용 
		System.out.println("url = "+url);
		System.out.println("path = " +path);
		System.out.println("contextPath = "+contextPath);
		
		try{
			Action action = mapper.getAction(path); //request에 맞는 action을 찾는다.
			action.setServletContext(getServletContext());
			
			String resultPage=action.execute(request, response); //execute를 통해 forward or redirect 주소를 찾는다.
			String result=resultPage.substring(resultPage.indexOf(":")+1); //가공하기
			
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			//Navigation 역할을 여기서 하네!
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}