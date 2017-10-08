package cn.itcast.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;

public class BaseDaoImpl<T> implements BaseDao<T> {
	protected HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.itcast.dao.BaseDao#add(java.lang.Object) дһ�����췽�����õ�ʵ�����Ͳ���<T>�����T
	 */
	Class pClass;

	public BaseDaoImpl() {
		// ��һ�� �õ���ǰ������Class
		Class clazz = this.getClass();
		// �ڶ��� �õ������ุ�����������BaseDaoImpl<T>
		Type type = clazz.getGenericSuperclass();
		// ������ ʹ��type�ӽӿ�ParameterizedType
		ParameterizedType ptype = (ParameterizedType) type;
		// ���Ĳ� �õ�ʵ�����Ͳ���<T>�����T
		Type[] types = ptype.getActualTypeArguments();
		Class tClass = (Class) types[0];
		pClass = tClass;
	}

	public void add(T t) {
		hibernateTemplate.save(t);

	}

	public void update(T t) {
		hibernateTemplate.update(t);

	}

	public void delete(T t) {
		hibernateTemplate.delete(t);

	}

	@SuppressWarnings("all")
	public List<T> findAll() {
		hibernateTemplate.find("from "+pClass.getSimpleName());
		return (List<T>) hibernateTemplate.find("from "+pClass.getSimpleName());
	}

	@SuppressWarnings("all")
	public T findOne(int id) {
		
		return (T) hibernateTemplate.get(pClass, id);
	}

}
