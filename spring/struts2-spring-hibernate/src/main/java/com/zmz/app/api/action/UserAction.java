package com.zmz.app.api.action;

import com.opensymphony.xwork2.ActionContext;
import com.zmz.app.application.service.UserService;
import com.zmz.app.domain.model.UserModel;
import com.zmz.core.api.action.BaseAction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ASNPHDG
 * @create 2020-01-04 19:46
 */
@Getter
@Setter
@Controller(value = "userAction")
public class UserAction extends BaseAction {

	private UserModel user;
	private List<UserModel> userList = new ArrayList<>();
	
	@Autowired
	private UserService userService;

	public String create() {
		System.out.println("======================");
		System.out.println(user.toString());
		System.out.println("======================");
		userService.insertUser(user);
		userList = userService.findAllUser();
		return "loginSuccess";
	}

	public String toUpdate() {
		//获取需要update的user的信息
		String id = getRequest().getParameter("id");
		user = userService.findUserByUserId(Long.parseLong(id));
		return "update";
	}

	public String doUpdate() {
		userService.updateUser(user);
		userList = userService.findAllUser();
		user = null;
		return "loginSuccess";
	}

	public String delete() {
		String id = getRequest().getParameter("id");
		userService.deleteUserByUserId(Long.parseLong(id));
		userList = userService.findAllUser();
		return "loginSuccess";
	}

	public String login(){
		userList = userService.findAllUser();
		return "loginSuccess";
	}
	
	public String logout(){
		Map<String, Object> session=ActionContext.getContext().getSession();
		session.remove("loginUser");
		return "logout";
	}

}
