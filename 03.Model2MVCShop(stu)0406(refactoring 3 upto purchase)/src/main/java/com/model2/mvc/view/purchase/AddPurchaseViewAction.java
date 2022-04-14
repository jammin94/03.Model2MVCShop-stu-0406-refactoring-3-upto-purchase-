package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseViewAction extends Action{
	public String execute(	HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//주어진 것 = prodNo
		//보내야 하는 것 = product와, user
		//getProduct로, getUser로 알아내서 보내기.
		ProductService productService = new ProductServiceImpl();
		UserService userService = new UserServiceImpl();
		
		Product product = new Product();
		User user = new User();
		
		product=productService.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		
		request.setAttribute("product", product);
		
		return "forward:/purchase/addPurchaseView.jsp";
		
	}
}
