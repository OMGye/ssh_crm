package cn.itcast.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.UserDaoImpl;
import cn.itcast.entity.User;
@Transactional
public class UserService {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	//登录的方法
	public User login(User user){
		
		return userDao.loginUser(user);
		
	}
	//查询所有用户
	public List<User> findAll() {
		return userDao.findAll();
	}

}
