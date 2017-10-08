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

	//1. �����ҳ��
	public String toAddPage() {
		List<Customer> list = customerService.findAll();
		request.setAttribute("list", list);
		return "toAddPage";
	}

	/**
	 * ��Ҫ�ϴ����ļ������� ��Ҫ�ϴ��ļ����� (1)��action�ĳ�Ա�����ж������(�����淶) һ����ʾ�ϴ��ļ� һ����ʾ�ϴ��ļ�����
	 * (2)���ɱ�����set��get���� ����һ���������ϴ��ļ���mime���ͣ��ɲ�д��
	 */
	// 1.�ϴ��ļ�
	// ����������Ҫ�Ǳ������ļ��ϴ����nameֵ
	private File upload;

	// 2.�ϴ��ļ����ƣ��������ļ��ϴ����nameֵFileName
	private String uploadFileName;

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
		System.out.println("�ϴ��ļ�");
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	// 2.��ӵ����ݿ�ķ���
	public String addLinkMan() throws IOException {
		System.out.println("add");
		if (upload != null) {
	         //�ڷ������ļ������洴���ļ�
				File serverFile = new File(ServletActionContext.getServletContext().getRealPath("/images")+"/"+uploadFileName);
			  //���ϴ��ļ����Ƶ��������ļ�����
				FileUtil.copyFile(upload, serverFile);
				
			}

		/**
		 * ��ģ���������Է�װ��ϵ�˻�����Ϣ ������cid�ǿͻ�idֵ����ֱ�ӷ�װ ��cid��װLinkManʵ��������customer��������
		 */
		String scid = request.getParameter("cid");
		int cid = Integer.parseInt(scid);
		// ����customer����
		Customer customer = new Customer();
		customer.setCid(cid);
		linkMan.setCustomer(customer);
		linkManService.add(linkMan);
		
		return "addLinkMan";
	}
	//3.��ʾ������ϵ��
	 public String list() {
		List<LinkMan> list = linkManService.listLinkMan(); 
		 request.setAttribute("list", list);
		 return "list";
	 }
	//4.����id��ʾ��Ϣ
	 public String showLinkMan() {
		 //ʹ��ģ�������õ�idֵ
		 int linkid = linkMan.getLinkid();
		 //����idֵ��ѯ��ϵ��
		 LinkMan link = linkManService.findOne(linkid);
		 request.setAttribute("linkman",link);
		 List<Customer> list = customerService.findAll();
	     request.setAttribute("list", list);
		 return "showLinkMan";
	 }
    //5.�޸���ϵ����Ϣ
	public String updateLinkMan() {
		String scid = request.getParameter("cid");
		int cid = Integer.parseInt(scid);
		// ����customer����
		Customer customer = new Customer();
		customer.setCid(cid);
		linkMan.setCustomer(customer);
		linkManService.update(linkMan);

		return "updateLinkMan";

	}
	//6.���ۺϲ�ѯҳ��
	public String toSelectPage() {
		List<Customer> list = customerService.findAll();
		request.setAttribute("list", list);
		return "toSelectPage";
	}
	//7.�ۺϲ�ѯ
	public String moreCondition() {
		List<LinkMan> list = linkManService.findMoreCondition(linkMan);
		request.setAttribute("list", list);
		return "moreCondition";
	}
	//��ϵ�������ͻ��ۺϲ�ѯ
	public String countCustomer() {
		List list = linkManService.findCountCustomer();
		request.setAttribute("list", list);
		return "countCustomer";
	}
	//������������json�ķ���
		public String linkManJson() throws IOException {
			//��ѯ���пͻ�������list���ϣ���list����ת����json
			//{"total":������"rows":[{},{}]}
			//ʹ��������ķ���JSON.toJSONString(object);
			//ʹ��response���з���
			List<LinkMan> list = linkManService.listLinkMan();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("total",list.size());
			map.put("rows", list);
			//��ֹѭ������
			String json = JSON.toJSONString(map,SerializerFeature.DisableCircularReferenceDetect);
			//����json��ʽʱ��д��application/json
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(json);		
			return NONE;
		}
}
