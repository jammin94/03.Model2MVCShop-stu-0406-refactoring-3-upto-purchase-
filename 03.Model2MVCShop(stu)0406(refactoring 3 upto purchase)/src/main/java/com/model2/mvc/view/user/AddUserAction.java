package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;


public class AddUserAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		User user=new User();
		user.setUserId(request.getParameter("userId"));
		user.setPassword(request.getParameter("password"));
		user.setUserName(request.getParameter("userName"));
		user.setSsn(request.getParameter("ssn"));
		
		user.setAddr(request.getParameter("addr"));
		user.setPhone(request.getParameter("phone"));
		user.setEmail(request.getParameter("email"));
		
		System.out.println(user);
		
		UserService service=new UserServiceImpl(); //interface 기반 코딩
		service.addUser(user); //둘다 가지고 있는 addUser()가 오버라이딩 되어있으니, 하위인 UserServiceImpl의 addUser메소드 사용.
		
		return "redirect:/user/loginView.jsp";
	}
}