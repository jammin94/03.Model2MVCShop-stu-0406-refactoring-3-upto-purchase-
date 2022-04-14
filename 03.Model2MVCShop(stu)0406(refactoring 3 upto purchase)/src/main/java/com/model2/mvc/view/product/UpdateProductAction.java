package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class UpdateProductAction extends Action {
	public String execute(	HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Product product = new Product();
		
		String prodNo=(String)request.getParameter("prodNo");
		
		product.setProdNo(Integer.parseInt(prodNo));	
		product.setProdName(request.getParameter("prodName"));	
		product.setProdDetail(request.getParameter("prodDetail"));	
		product.setManuDate(request.getParameter("manuDate"));	
		product.setPrice(Integer.parseInt(request.getParameter("price")));	
		
		ProductService service = new ProductServiceImpl();
		service.updateProduct(product);
		
		return "redirect:/getProduct.do?prodNo="+prodNo;
		
	}
	
}
