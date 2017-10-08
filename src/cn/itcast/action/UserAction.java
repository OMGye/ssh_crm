package cn.itcast.action;

import cn.itcast.entity.User;
import cn.itcast.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends SuperAction {
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// 属性封装获取
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// 登录的方法
	public String login() {
		// 封装实体类对象
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		User userExist = userService.login(user);
		if (userExist != null) {
			// 成功
			session.setAttribute("user", userExist);
			return "loginsuccess";

		} else {
			// 失败
			return "login";
		}

	}
}
