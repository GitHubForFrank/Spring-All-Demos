package com.zmz.api.action;

import com.opensymphony.xwork2.ActionContext;
import com.zmz.application.service.UserService;
import com.zmz.domain.model.UserModel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * @author ASNPHDG
 * @create 2020-01-04 19:46
 */
@Slf4j
@Getter
@Setter
@Controller(value = "userAction")
public class UserAction extends BaseAction{

	private UserModel user;
	
	@Autowired
	private UserService userService;

	public String toUpdate() {
		user = new UserModel();
		user.setId(1);
		user.setName("name01");
		user.setDept("dept01");
		user.setPhone("phone01");
		user.setWebsite("website01");
		return "update";
	}

	public String doUpdate() {
		log.info("======================");
		log.info("createAndUpdate");
		log.info(user.toString());
		log.info("======================");
		userService.create(user);
		return "update";
	}

	public String login(){
		return "loginSuccess";
	}
	
	public String logout(){
		Map<String, Object> session=ActionContext.getContext().getSession();
		session.remove("loginUser");
		return "logout";
	}

}
