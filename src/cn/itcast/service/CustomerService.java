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

	// ��ӿͻ�
	public void add(Customer customer) {
		customerDao.add(customer);

	}

	// �鿴�����û�
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

	// ��װ��ҳ���ݵ�pageBean��������
	public PageBean listPage(int currentPage) {
		// ����pageBean����
		PageBean pageBean = new PageBean();
		// ��ǰҳ
		pageBean.setCurrentPage(currentPage);
		// �ܼ�¼��
		int totalCount = customerDao.findCount();
		pageBean.setTotalCount(totalCount);
		// ÿҳ��ʾ��¼��
		int pageSize = 3;
		/**
		 * ��ҳ�� �ܼ�¼������ÿҳ��ʾ��¼�� �ܹ�����
		 */
		int totalPage = 0;
		if (totalCount % pageSize == 0) {
			// ����
			totalPage = totalCount / pageSize;
		} else {
			totalPage = totalCount / pageSize + 1;
		}
		pageBean.setTotalPage(totalPage);
		// ��ʼλ��
		int begin = (currentPage - 1) * 3;
		List<Customer> list = customerDao.findPage(begin, pageSize);
		pageBean.setList(list);
		return pageBean;
	}
	// ��װ������ҳ���ݵ�pageBean��������
	public PageBean listConditionPage(Integer currentPage, String custName) {
		// ����pageBean����
		PageBean pageBean = new PageBean();
		// ��ǰҳ
		pageBean.setCurrentPage(currentPage);
		// �ܼ�¼��
		int totalCount = customerDao.findConditionCount(custName);
		pageBean.setTotalCount(totalCount);
		// ÿҳ��ʾ��¼��
		int pageSize = 3;
		/**
		 * ��ҳ�� �ܼ�¼������ÿҳ��ʾ��¼�� �ܹ�����
		 */
		int totalPage = 0;
		if (totalCount % pageSize == 0) {
			// ����
			totalPage = totalCount / pageSize;
		} else {
			totalPage = totalCount / pageSize + 1;
		}
		pageBean.setTotalPage(totalPage);
		// ��ʼλ��
		int begin = (currentPage - 1) * 3;
		List<Customer> list = customerDao.findConditionPage(begin, pageSize,custName);
		pageBean.setList(list);
		return pageBean;
	}
   //�ۺϲ�ѯ
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
