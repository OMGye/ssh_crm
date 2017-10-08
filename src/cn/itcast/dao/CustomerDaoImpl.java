package cn.itcast.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.itcast.entity.Customer;

public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao {
    private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		super.hibernateTemplate = hibernateTemplate;
		this.hibernateTemplate = hibernateTemplate;
	}
//    //��ӿͻ�����
//	public void add(Customer customer) {
//		hibernateTemplate.save(customer);
//	}
//    //��ѯ���пͻ�
//	@SuppressWarnings("all")
//	public List<Customer> findAll() {
//		return (List<Customer>) hibernateTemplate.find("from Customer");
//	}
//    //����idֵ��ѯ�ͻ�
//	public Customer findOne(int cid) {
//		
//		return hibernateTemplate.get(Customer.class, cid);
//	}
//	//ɾ���ͻ�
//	public void delete(Customer customer) {
//		hibernateTemplate.delete(customer);
//		
//	}
//	//�޸Ŀͻ�
//	public void update(Customer customer) {
//		hibernateTemplate.update(customer);
//		
//	}
	//�ܼ�¼��
	@SuppressWarnings("all")
	public int findCount() {
		List<Object> list = (List<Object>) hibernateTemplate.find("select count(*) from Customer");
		if(list!=null&list.size()!=0){
		Object object = list.get(0);
		Long lobj = (Long) object;
		int count = lobj.intValue();
		 return count;
		}
		return 0;
	}
	//��ҳ��ѯ
	public List<Customer> findPage(int begin, int pageSize) {
		//��һ�� ʹ��hibernate�ײ����ʵ��
		
		/* SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Customer");
		query.setFirstResult(begin);
		query.setMaxResults(pageSize);
		List<Customer> list = query.list();
	   */
		//�ڶ��� ʹ�����߶����hibernateTemplate�ķ���ʵ��
		
		 DetachedCriteria  criteria = DetachedCriteria.forClass(Customer.class);
		 List<Customer> list = (List<Customer>) hibernateTemplate.findByCriteria(criteria,begin,pageSize);
		  return list;
	}
	//�����ܼ�¼��
	@SuppressWarnings("all")
	public int findConditionCount(String custName) {
		List<Object> list = (List<Object>) hibernateTemplate.find("select count(*) from Customer where custName like?","%"+custName+"%");
		if(list!=null&list.size()!=0){
		Object object = list.get(0);
		Long lobj = (Long) object;
		int count = lobj.intValue();
		 return count;
		}
		return 0;
	}//������ҳ��ѯ
	public List<Customer> findConditionPage(int begin, int pageSize,
			String custName) {
		//1.�������߶���
		DetachedCriteria  criteria = DetachedCriteria.forClass(Customer.class);
		//2.���ö�ʵ������Ǹ�����
		criteria.add(Restrictions.like("custName", "%"+custName+"%"));
		List<Customer> list = (List<Customer>) hibernateTemplate.findByCriteria(criteria,begin,pageSize);
		  return list;
	}
	
	//�ۺϲ�ѯ
	@SuppressWarnings("all")
	public List<Customer> findMoreCondition(Customer customer) {
//	  String hql="from Customer where 1=1";
//	  List<String> plist =new ArrayList<String>();
//	  if(customer.getCustName()!=null&!"".equals(customer.getCustName())) {
//		  hql+=" and custName=?";
//		  plist.add(customer.getCustName());
//	  }
//	  if(customer.getCustLevel()!=null&!"".equals(customer.getCustLevel())) {
//		  hql+=" and custLevel=?";
//		  plist.add(customer.getCustLevel());
//	  }
//	  if(customer.getCustSource()!=null&!"".equals(customer.getCustSource())) {
//		  hql+=" and custSource=?";
//		  plist.add(customer.getCustSource());
//	  }
//			  
//		return (List<Customer>) hibernateTemplate.find(hql, plist.toArray());
   //�ڶ��ַ�ʽ ʹ�����߶�����ʵ�ֶ�������ѯ
		//1.�������߶���
		DetachedCriteria  criteria = DetachedCriteria.forClass(Customer.class);
		if(customer.getCustName()!=null&!"".equals(customer.getCustName())) {
			criteria.add(Restrictions.eq("custName", customer.getCustName()));
		  }
		if(customer.getCustLevel()!=null&!"".equals(customer.getCustLevel())) {
			criteria.add(Restrictions.eq("custLevel", customer.getCustLevel()));
		  }
		if(customer.getCustSource()!=null&!"".equals(customer.getCustSource())) {
			criteria.add(Restrictions.eq("custSource", customer.getCustSource()));
		  }
		return (List<Customer>) hibernateTemplate.findByCriteria(criteria);
	}
	public List findCountSource() {
	   //д������佨���õײ�sql
	   //��Ҫ�õ�SQLQuery����
	   //1.�õ�SessionFactory
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		SQLQuery sqlQuery = session.createSQLQuery("select COUNT(*) num,custSource from t_customer GROUP BY custSource");
		/**
		 * ��Ϊ����ֵ�������ֶΣ�����û�ж�Ӧʵ����
		 * ���԰ѷ�������ת��map�ṹ
		 */
		//map��һ���ӿڣ���Ҫʵ����
		sqlQuery.setResultTransformer(Transformers.aliasToBean(HashMap.class));
		List list = sqlQuery.list();
		return list;
	}
}
