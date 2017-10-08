package cn.itcast.action;

import cn.itcast.entity.User;
import cn.itcast.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends SuperAction {
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// ���Է�װ��ȡ
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

	// ��¼�ķ���
	public String login() {
		// ��װʵ�������
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		User userExist = userService.login(user);
		if (userExist != null) {
			// �ɹ�
			session.setAttribute("user", userExist);
			return "loginsuccess";

		} else {
			// ʧ��
			return "login";
		}

	}
}
