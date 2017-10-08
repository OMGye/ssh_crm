package cn.itcast.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.entity.Customer;
import cn.itcast.entity.LinkMan;
import cn.itcast.service.CustomerService;
import cn.itcast.service.LinkManService;

public class LinkManAction extends SuperAction implements ModelDriven<LinkMan> {
	private LinkManService linkManService;

	public void setLinkManService(LinkManService linkManService) {
		this.linkManService = linkManService;
	}

	private CustomerService customerService;

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	private LinkMan linkMan = new LinkMan();

	public LinkMan getModel() {

		return linkMan;
	}

	//1. 到添加页面
	public String toAddPage() {
		List<Customer> list = customerService.findAll();
		request.setAttribute("list", list);
		return "toAddPage";
	}

	/**
	 * 需要上传的文件（流） 需要上传文件名称 (1)在action的成员变量中定义变量(命名规范) 一个表示上传文件 一个表示上传文件名称
	 * (2)生成变量的set和get方法 还有一个变量，上传文件的mime类型（可不写）
	 */
	// 1.上传文件
	// 变量名称需要是表单里面文件上传项的name值
	private File upload;

	// 2.上传文件名称，表单里面文件上传项的name值FileName
	private String uploadFileName;

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
		System.out.println("上传文件");
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	// 2.添加到数据库的方法
	public String addLinkMan() throws IOException {
		System.out.println("add");
		if (upload != null) {
	         //在服务器文件夹里面创建文件
				File serverFile = new File(ServletActionContext.getServletContext().getRealPath("/images")+"/"+uploadFileName);
			  //把上传文件复制到服务器文件里面
				FileUtil.copyFile(upload, serverFile);
				
			}

		/**
		 * 用模型驱动可以封装联系人基本信息 但是有cid是客户id值不能直接封装 把cid封装LinkMan实体类里面customer对象里面
		 */
		String scid = request.getParameter("cid");
		int cid = Integer.parseInt(scid);
		// 创建customer对象
		Customer customer = new Customer();
		customer.setCid(cid);
		linkMan.setCustomer(customer);
		linkManService.add(linkMan);
		
		return "addLinkMan";
	}
	//3.显示所有联系人
	 public String list() {
		List<LinkMan> list = linkManService.listLinkMan(); 
		 request.setAttribute("list", list);
		 return "list";
	 }
	//4.根据id显示信息
	 public String showLinkMan() {
		 //使用模型驱动得到id值
		 int linkid = linkMan.getLinkid();
		 //根据id值查询联系人
		 LinkMan link = linkManService.findOne(linkid);
		 request.setAttribute("linkman",link);
		 List<Customer> list = customerService.findAll();
	     request.setAttribute("list", list);
		 return "showLinkMan";
	 }
    //5.修改联系人信息
	public String updateLinkMan() {
		String scid = request.getParameter("cid");
		int cid = Integer.parseInt(scid);
		// 创建customer对象
		Customer customer = new Customer();
		customer.setCid(cid);
		linkMan.setCustomer(customer);
		linkManService.update(linkMan);

		return "updateLinkMan";

	}
	//6.到综合查询页面
	public String toSelectPage() {
		List<Customer> list = customerService.findAll();
		request.setAttribute("list", list);
		return "toSelectPage";
	}
	//7.综合查询
	public String moreCondition() {
		List<LinkMan> list = linkManService.findMoreCondition(linkMan);
		request.setAttribute("list", list);
		return "moreCondition";
	}
	//联系人所属客户综合查询
	public String countCustomer() {
		List list = linkManService.findCountCustomer();
		request.setAttribute("list", list);
		return "countCustomer";
	}
	//返回所有数据json的方法
		public String linkManJson() throws IOException {
			//查询所有客户，返回list集合，把list集合转换成json
			//{"total":数量，"rows":[{},{}]}
			//使用类里面的方法JSON.toJSONString(object);
			//使用response进行返回
			List<LinkMan> list = linkManService.listLinkMan();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("total",list.size());
			map.put("rows", list);
			//禁止循环调用
			String json = JSON.toJSONString(map,SerializerFeature.DisableCircularReferenceDetect);
			//返回json格式时候，写出application/json
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(json);		
			return NONE;
		}
}
