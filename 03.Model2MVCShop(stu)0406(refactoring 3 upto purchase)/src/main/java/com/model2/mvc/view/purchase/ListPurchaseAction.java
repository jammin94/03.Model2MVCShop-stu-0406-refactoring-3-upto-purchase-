package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class ListPurchaseAction extends Action{
	@Override
	public String execute(	HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		Search search=new Search();
		
		int currentPage=1;
		if(request.getParameter("currentPage")!=null)
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition")); //view에서 얻기
		search.setSearchKeyword(request.getParameter("searchKeyword")); //view에서 얻기
		
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		User buyerUser=(User)(request.getSession().getAttribute("user"));
		String buyerId=buyerUser.getUserId();
		
		System.out.println(buyerId);
		Map<String,Object> map=null;
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		if(buyerId.equals("admin")||buyerId.equals("manager")) {
			map=purchaseService.getSaleList(search);
		}else {
			map=purchaseService.getPurchaseList(search,buyerId);
		}
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction ::"+resultPage);

		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}	
}
