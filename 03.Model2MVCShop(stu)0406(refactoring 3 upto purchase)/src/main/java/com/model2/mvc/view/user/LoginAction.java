package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;


public class LoginAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		User user=new User();
		user.setUserId(request.getParameter("userId"));
		user.setPassword(request.getParameter("password"));
		//로그인 시도하는 user의 id, passwd 세팅
		
		
		UserService service=new UserServiceImpl();
		User dbVO=service.loginUser(user);
		//DB에 있는 user와 대조 후 일치하면 로그인
		
		HttpSession session=request.getSession();
		session.setAttribute("user", dbVO);
		//로그인 되어있는 User를 "user"라는 key로 session에 저장. 로그인 되어있는 상태는 user라는 키를 이용하여 읽어낼 수 있다.
		
		System.out.println(user);
		return "redirect:/index.jsp";
	}
}