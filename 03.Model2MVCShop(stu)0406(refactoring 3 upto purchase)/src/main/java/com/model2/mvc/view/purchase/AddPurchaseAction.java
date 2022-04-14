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
		
		//�־��� �� =getParameter�� prodNo, buyerId, paymentOption,receiverName,receiverPhone,receiverAddr,receiverRequest,receiverDate
		//������ �ϴ� �� = product��, user
		//getProduct��, getUser�� �˾Ƴ��� ������.
		System.out.println("AddPurchaseAction���� ��");
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
		purchase.setTranCode("1");//���ſϷ�
		//0-> ���Ű���
		//1-> ���ſϷ�
		//2-> �����
		//3-> ��ۿϷ�
		
		System.out.println("�̰� ��?"+purchase);
		
		purchaseService.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		
		
		return "forward:/purchase/addPurchaseDone.jsp";
		
	}
}
