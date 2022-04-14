package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class GetProductAction extends Action {

	public String execute(	HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("euc-kr");
		response.setCharacterEncoding("euc-kr");
		
		String prodNo = request.getParameter("prodNo");
		int no = Integer.parseInt(prodNo);
		
		String menu=request.getParameter("menu");
		
		ProductService service = new ProductServiceImpl(); 
		Product vo = service.getProduct(no);
		
		request.setAttribute("vo", vo);
		
		Cookie cookie = null;
		Cookie[] cookies = request.getCookies();
		
		if (cookies!=null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals("history")) {
					cookie=new Cookie("history",c.getValue()+"!"+prodNo);
					System.out.println(cookie.getValue());
				}	
			}
			
			if(cookie==null) {
				cookie=new Cookie("history",prodNo);
			}
		}
		
		response.addCookie(cookie);
	
		
		return "forward:/product/getProduct.jsp";
		
	}

}
