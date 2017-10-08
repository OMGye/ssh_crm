package cn.itcast.dao;

import java.util.List;

import cn.itcast.entity.Customer;

public interface CustomerDao extends BaseDao<Customer>{

//	void add(Customer customer);
//
//	List<Customer> findAll();
//
//	Customer findOne(int cid);
//
//	void delete(Customer customer);
//
//	void update(Customer customer);

	int findCount();

	List<Customer> findPage(int begin, int pageSize);

	int findConditionCount(String custName);

	List<Customer> findConditionPage(int begin, int pageSize, String custName);

	List<Customer> findMoreCondition(Customer customer);

	List findCountSource();

}
