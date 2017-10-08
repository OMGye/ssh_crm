package cn.itcast.action;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.entity.User;
import cn.itcast.entity.Visit;

import cn.itcast.entity.Customer;
import cn.itcast.service.CustomerService;
import cn.itcast.service.UserService;
import cn.itcast.service.VisitService;

public class VisitAction extends SuperAction implements ModelDriven<Visit>{
    private VisitService visitService;
  
	public void setVisitService(VisitService visitService) {
		this.visitService = visitService;
	}
	private Visit visit = new Visit();
	public Visit getModel() {
		
		return visit;
	}
	//ע��customerService
	private CustomerService customerService;
	
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
   //ע��userService
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
    //1.�����ҳ��
	public String toAddPage() {
		List<Customer> listCustomer = customerService.findAll();
		List<User> listUser = userService.findAll();
		request.setAttribute("listCustomer", listCustomer);
		request.setAttribute("listUser", listUser);
		return "toAddPage";
	}
    //2.�������
	public String addVisit() {
		visitService.add(visit);
		return "addVisit";
	}
   //3.�ݷ��б�
	public String list() {
		List<Visit> list = visitService.findAll();
		request.setAttribute("list", list);
		return "list";
	}
}
