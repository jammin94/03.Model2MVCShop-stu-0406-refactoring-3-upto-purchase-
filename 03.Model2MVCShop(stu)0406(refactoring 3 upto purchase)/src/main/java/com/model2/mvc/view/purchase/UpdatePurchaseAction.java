package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseAction extends Action{
	public String execute(	HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		
		Purchase purchase=purchaseService.getPurchase(Integer.parseInt(request.getParameter("tranNo")));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		
		purchaseService.updatePurchase(purchase);
		
		purchase=purchaseService.getPurchase(Integer.parseInt(request.getParameter("tranNo")));
	
		request.setAttribute("purchase",purchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}	

}