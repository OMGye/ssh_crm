package cn.itcast.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.CustomerDao;
import cn.itcast.entity.Customer;
import cn.itcast.entity.PageBean;

@Transactional
public class CustomerService {
	private CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	// 添加客户
	public void add(Customer customer) {
		customerDao.add(customer);

	}

	// 查看所有用户
	public List<Customer> findAll() {

		return customerDao.findAll();
	}

	public Customer findOne(int cid) {

		return customerDao.findOne(cid);
	}

	public void delete(Customer customer) {

		customerDao.delete(customer);
	}

	public void update(Customer customer) {
		customerDao.update(customer);

	}

	// 封装分页数据到pageBean对象里面
	public PageBean listPage(int currentPage) {
		// 创建pageBean对象
		PageBean pageBean = new PageBean();
		// 当前页
		pageBean.setCurrentPage(currentPage);
		// 总记录数
		int totalCount = customerDao.findCount();
		pageBean.setTotalCount(totalCount);
		// 每页显示记录数
		int pageSize = 3;
		/**
		 * 总页数 总记录数除以每页显示记录数 能够整除
		 */
		int totalPage = 0;
		if (totalCount % pageSize == 0) {
			// 整除
			totalPage = totalCount / pageSize;
		} else {
			totalPage = totalCount / pageSize + 1;
		}
		pageBean.setTotalPage(totalPage);
		// 开始位置
		int begin = (currentPage - 1) * 3;
		List<Customer> list = customerDao.findPage(begin, pageSize);
		pageBean.setList(list);
		return pageBean;
	}
	// 封装条件分页数据到pageBean对象里面
	public PageBean listConditionPage(Integer currentPage, String custName) {
		// 创建pageBean对象
		PageBean pageBean = new PageBean();
		// 当前页
		pageBean.setCurrentPage(currentPage);
		// 总记录数
		int totalCount = customerDao.findConditionCount(custName);
		pageBean.setTotalCount(totalCount);
		// 每页显示记录数
		int pageSize = 3;
		/**
		 * 总页数 总记录数除以每页显示记录数 能够整除
		 */
		int totalPage = 0;
		if (totalCount % pageSize == 0) {
			// 整除
			totalPage = totalCount / pageSize;
		} else {
			totalPage = totalCount / pageSize + 1;
		}
		pageBean.setTotalPage(totalPage);
		// 开始位置
		int begin = (currentPage - 1) * 3;
		List<Customer> list = customerDao.findConditionPage(begin, pageSize,custName);
		pageBean.setList(list);
		return pageBean;
	}
   //综合查询
	public List<Customer> findMoreCondition(Customer customer) {
		// TODO Auto-generated method stub
		return customerDao.findMoreCondition(customer);
	}

	public List findCountSource() {
		
		return customerDao.findCountSource();
	}

	public List<Customer> findPageJson(int begin, int rows) {
		
		return customerDao.findPage(begin, rows);
	}

	public int findCount() {
		// TODO Auto-generated method stub
		return customerDao.findCount();
	}
    
}
