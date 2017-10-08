package cn.itcast.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.entity.Customer;
import cn.itcast.entity.PageBean;
import cn.itcast.service.CustomerService;

public class CustomerAction extends SuperAction implements
		ModelDriven<Customer> {
	private CustomerService customerService;

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
		
	}

	private Customer customer = new Customer();

	// ģ��������װ����
	public Customer getModel() {
		return customer;
	}

	// 1.�����ҳ��
	public String toAddPage() {
		return "toAddPage";
	}

	// 2.��ӵķ���
	public String add() {
		// ����߼�
		customerService.add(customer);
		return "add";
	}

	// 3.�ͻ��б���
	public String list() {
		List<Customer> list = customerService.findAll();
		request.setAttribute("list", list);
		return "list";
	}

	// 4.�ͻ�ɾ������
	public String delete() {
		// ʹ��ģ��������ȡ���ύcidֵ
		int cid = customer.getCid();
		// ɾ���淶д�������ȸ���id��ѯ�����÷���ɾ��
		// ����id��ѯ
		Customer customer = customerService.findOne(cid);
		// �жϸ���idֵ��ѯ�����Ƿ�Ϊ��
		if (customer != null) {
			// ���÷���ɾ��
			customerService.delete(customer);
		}
		return "delete";
	}

	// 5.�޸Ĳ����������ݿͻ�id����ͻ�
	public String showCustomer() {
		int cid = customer.getCid();
		Customer customer = customerService.findOne(cid);
		request.setAttribute("customer", customer);
		return "showCustomer";
	}

	// 6.�޸Ĳ���
	public String update() {
		customerService.update(customer);
		return "update";
	}

	/**
	 * ʹ�����Է�װ�õ�currentPage
	 */
	private Integer currentPage;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {		
		this.currentPage = currentPage;
	}

	// ��ҳ����
	public String listPage() {
		// int
		// currentPage=Integer.parseInt(request.getParameter("currentPage"));
		/**
		 * Ϊ��ʹɾ�������޸Ĳ���֮���ܹ����ص���ҳҳ���� ���Լ�һ���ж���䣬�Ƿ�ͨ�����Է�װ�õ���ֵ
		 */
		if(currentPage==null) {
			currentPage = 1;
		}
		PageBean pageBean = customerService.listPage(currentPage);
		request.setAttribute("pageBean", pageBean);
		return "listPage";
	}

	// ��ѯ�ͻ���ķ�ҳ����
	public String listConditionPage() {
		/**
		 * ����ʹ��ģ������������customerName ���Ҫ��������ѯ��ҳ�������һҳ�Ĳ������Բ�����session��������
		 */
		if(currentPage==null) {
			currentPage = 1;
		}
	    // �ж�session�����custName��û�л�����û�иı�
		if (customer.getCustName() != null) {
			System.out.println(customer.getCustName()+"..");
			if (session.getAttribute("customer") == null
					| !customer.equals((Customer) session
							.getAttribute("customer"))) {
				session.setAttribute("customer", customer);
			}

				PageBean pageBean = customerService.listConditionPage(
						currentPage, customer.getCustName());
				request.setAttribute("pageBean", pageBean);
				

		} else {
			PageBean pageBean = customerService
					.listConditionPage(currentPage, ((Customer) session
							.getAttribute("customer")).getCustName());
			request.setAttribute("pageBean", pageBean);
		}
		return "listConditionPage";

	}
	//���ۺϲ�ѯҳ��
	public String toSelectPage() {
		return "toSelectPage";
	}
	//�ۺϲ�ѯ
	public String moreCondition() {
	   List<Customer> list = customerService.findMoreCondition(customer);
	   request.setAttribute("list", list);
		return "moreCondition";
	}
	//�ͻ���Դͳ��
	public String countSource() {
		List list = customerService.findCountSource();
		request.setAttribute("list", list);
		return "countSource";
	}
	//������������json�ķ���
	public String customerJson() throws IOException {
		//��ѯ���пͻ�������list���ϣ���list����ת����json
		//{"total":������"rows":[{},{}]}
		//ʹ��������ķ���JSON.toJSONString(object);
		//ʹ��response���з���
		List<Customer> list = customerService.findAll();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total",list.size());
		map.put("rows", list);
		String json = JSON.toJSONString(map);
		//����json��ʽʱ��д��application/json
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);		
		return NONE;
	}
	//�����Է�װ��ȡ��ǰҳ��ÿҳ��¼�����봫����������һ��
	
	private int page; //��ǰҳ
	private int rows; //ÿҳ��¼��
	
	//���ط�ҳ�ͻ�json���ݵķ���
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String customerPageJson() throws IOException {
		//���ط�ҳ���ݣ���Ҫtatal���ܼ�¼����rows����ҳ����json��ʽ
		//��ǰҳ
		//ÿҳ��¼��
		//��ʼλ��
	 int begin = (page-1)*rows;
	 //1.�õ���ҳ����
	 List<Customer> list = customerService.findPageJson(begin,rows);
	 //2.�õ��ܼ�¼��
	 int count = customerService.findCount();
	 //3.��list��count������Ҫjson��ʽ������ҳ��
	 Map<String,Object> map = new HashMap<String,Object>();
	 map.put("total",count);
	 map.put("rows", list);
	 String json = JSON.toJSONString(map);
	 //����json��ʽʱ��д��application/json
	 response.setContentType("application/json;charset=utf-8");
	 response.getWriter().write(json);		
	  return NONE;
	}
	
}
