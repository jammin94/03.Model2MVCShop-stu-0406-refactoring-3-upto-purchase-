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
		//�α��� �õ��ϴ� user�� id, passwd ����
		
		
		UserService service=new UserServiceImpl();
		User dbVO=service.loginUser(user);
		//DB�� �ִ� user�� ���� �� ��ġ�ϸ� �α���
		
		HttpSession session=request.getSession();
		session.setAttribute("user", dbVO);
		//�α��� �Ǿ��ִ� User�� "user"��� key�� session�� ����. �α��� �Ǿ��ִ� ���´� user��� Ű�� �̿��Ͽ� �о �� �ִ�.
		
		System.out.println(user);
		return "redirect:/index.jsp";
	}
}