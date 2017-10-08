package cn.itcast.dao;

import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.itcast.entity.User;

public class UserDaoImpl implements UserDao {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@SuppressWarnings("unchecked")
	public User loginUser(User user) {
		List<User> list = (List<User>) hibernateTemplate.find(
				"from User where username=? and password=?",
				user.getUsername(), user.getPassword());
		// 如果查询之后，没有结果，list里面没有值，根据get下标取不到值，出现下标越界异常
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}
   //查询所有用户
	public List<User> findAll() {
	
		return (List<User>) hibernateTemplate.find("from User");
	}

}
