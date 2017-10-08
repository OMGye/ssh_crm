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
		// �����ѯ֮��û�н����list����û��ֵ������get�±�ȡ����ֵ�������±�Խ���쳣
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}
   //��ѯ�����û�
	public List<User> findAll() {
	
		return (List<User>) hibernateTemplate.find("from User");
	}

}
