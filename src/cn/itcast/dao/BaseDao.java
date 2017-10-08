package cn.itcast.dao;

import java.util.List;

/**
 *���������ӿ�
 *���crud 
 * @author OMGؼ����
 *
 * @param <T>
 */

public interface BaseDao<T> {

	// ��������
	void add(T t);

	// �޸�����
	void update(T t);

	// ɾ������
	void delete(T t);

	// ��ѯ��������
	List<T> findAll();

	// ����id��ѯ����
	T findOne(int id);
}
