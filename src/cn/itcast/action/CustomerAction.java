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

	// 模型驱动封装对象
	public Customer getModel() {
		return customer;
	}

	// 1.到添加页面
	public String toAddPage() {
		return "toAddPage";
	}

	// 2.添加的方法
	public String add() {
		// 添加逻辑
		customerService.add(customer);
		return "add";
	}

	// 3.客户列表方法
	public String list() {
		List<Customer> list = customerService.findAll();
		request.setAttribute("list", list);
		return "list";
	}

	// 4.客户删除方法
	public String delete() {
		// 使用模型驱动获取表单提交cid值
		int cid = customer.getCid();
		// 删除规范写法：首先根据id查询，调用方法删除
		// 根据id查询
		Customer customer = customerService.findOne(cid);
		// 判断根据id值查询对象是否为空
		if (customer != null) {
			// 调用方法删除
			customerService.delete(customer);
		}
		return "delete";
	}

	// 5.修改操作——根据客户id查出客户
	public String showCustomer() {
		int cid = customer.getCid();
		Customer customer = customerService.findOne(cid);
		request.setAttribute("customer", customer);
		return "showCustomer";
	}

	// 6.修改操作
	public String update() {
		customerService.update(customer);
		return "update";
	}

	/**
	 * 使用属性封装得到currentPage
	 */
	private Integer currentPage;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {		
		this.currentPage = currentPage;
	}

	// 分页操作
	public String listPage() {
		// int
		// currentPage=Integer.parseInt(request.getParameter("currentPage"));
		/**
		 * 为了使删除或者修改操作之后能够返回到分页页面中 所以加一个判断语句，是否通过属性封装得到的值
		 */
		if(currentPage==null) {
			currentPage = 1;
		}
		PageBean pageBean = customerService.listPage(currentPage);
		request.setAttribute("pageBean", pageBean);
		return "listPage";
	}

	// 查询客户后的分页操作
	public String listConditionPage() {
		/**
		 * 可以使用模型驱动来接受customerName 如果要在条件查询分页后进行下一页的操作可以不妨用session域来保存
		 */
		if(currentPage==null) {
			currentPage = 1;
		}
	    // 判断session里面的custName有没有或者有没有改变
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
	//到综合查询页面
	public String toSelectPage() {
		return "toSelectPage";
	}
	//综合查询
	public String moreCondition() {
	   List<Customer> list = customerService.findMoreCondition(customer);
	   request.setAttribute("list", list);
		return "moreCondition";
	}
	//客户来源统计
	public String countSource() {
		List list = customerService.findCountSource();
		request.setAttribute("list", list);
		return "countSource";
	}
	//返回所有数据json的方法
	public String customerJson() throws IOException {
		//查询所有客户，返回list集合，把list集合转换成json
		//{"total":数量，"rows":[{},{}]}
		//使用类里面的方法JSON.toJSONString(object);
		//使用response进行返回
		List<Customer> list = customerService.findAll();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total",list.size());
		map.put("rows", list);
		String json = JSON.toJSONString(map);
		//返回json格式时候，写出application/json
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);		
		return NONE;
	}
	//用属性封装获取当前页和每页记录数，与传过来的名称一致
	
	private int page; //当前页
	private int rows; //每页记录数
	
	//返回分页客户json数据的方法
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
		//返回分页数据，需要tatal：总记录数；rows：分页数据json格式
		//当前页
		//每页记录数
		//开始位置
	 int begin = (page-1)*rows;
	 //1.得到分页集合
	 List<Customer> list = customerService.findPageJson(begin,rows);
	 //2.得到总记录数
	 int count = customerService.findCount();
	 //3.把list和count构造需要json格式，返回页面
	 Map<String,Object> map = new HashMap<String,Object>();
	 map.put("total",count);
	 map.put("rows", list);
	 String json = JSON.toJSONString(map);
	 //返回json格式时候，写出application/json
	 response.setContentType("application/json;charset=utf-8");
	 response.getWriter().write(json);		
	  return NONE;
	}
	
}
