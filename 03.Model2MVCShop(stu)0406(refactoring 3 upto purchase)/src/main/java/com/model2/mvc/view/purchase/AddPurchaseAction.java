package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseAction extends Action {
	public String execute(	HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//주어진 것 =getParameter로 prodNo, buyerId, paymentOption,receiverName,receiverPhone,receiverAddr,receiverRequest,receiverDate
		//보내야 하는 것 = product와, user
		//getProduct로, getUser로 알아내서 보내기.
		System.out.println("AddPurchaseAction까지 옴");
		ProductService productService = new ProductServiceImpl();
		UserService userService = new UserServiceImpl();
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		Product product = new Product();
		User user = new User();
		Purchase purchase = new Purchase();
		
		product=productService.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		user=userService.getUser(request.getParameter("buyerId"));
		
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setTranCode("1");//구매완료
		//0-> 구매가능
		//1-> 구매완료
		//2-> 배송중
		//3-> 배송완료
		
		System.out.println("이거 됨?"+purchase);
		
		purchaseService.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		
		
		return "forward:/purchase/addPurchaseDone.jsp";
		
	}
}
