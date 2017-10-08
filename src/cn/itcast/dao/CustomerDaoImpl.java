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
//    //添加客户操作
//	public void add(Customer customer) {
//		hibernateTemplate.save(customer);
//	}
//    //查询所有客户
//	@SuppressWarnings("all")
//	public List<Customer> findAll() {
//		return (List<Customer>) hibernateTemplate.find("from Customer");
//	}
//    //根据id值查询客户
//	public Customer findOne(int cid) {
//		
//		return hibernateTemplate.get(Customer.class, cid);
//	}
//	//删除客户
//	public void delete(Customer customer) {
//		hibernateTemplate.delete(customer);
//		
//	}
//	//修改客户
//	public void update(Customer customer) {
//		hibernateTemplate.update(customer);
//		
//	}
	//总记录数
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
	//分页查询
	public List<Customer> findPage(int begin, int pageSize) {
		//第一种 使用hibernate底层代码实现
		
		/* SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Customer");
		query.setFirstResult(begin);
		query.setMaxResults(pageSize);
		List<Customer> list = query.list();
	   */
		//第二种 使用离线对象和hibernateTemplate的方法实现
		
		 DetachedCriteria  criteria = DetachedCriteria.forClass(Customer.class);
		 List<Customer> list = (List<Customer>) hibernateTemplate.findByCriteria(criteria,begin,pageSize);
		  return list;
	}
	//条件总记录数
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
	}//条件分页查询
	public List<Customer> findConditionPage(int begin, int pageSize,
			String custName) {
		//1.创建离线对象
		DetachedCriteria  criteria = DetachedCriteria.forClass(Customer.class);
		//2.设置对实体类的那个属性
		criteria.add(Restrictions.like("custName", "%"+custName+"%"));
		List<Customer> list = (List<Customer>) hibernateTemplate.findByCriteria(criteria,begin,pageSize);
		  return list;
	}
	
	//综合查询
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
   //第二种方式 使用离线对象来实现多条件查询
		//1.创建离线对象
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
	   //写复杂语句建议用底层sql
	   //需要用到SQLQuery对象
	   //1.得到SessionFactory
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		SQLQuery sqlQuery = session.createSQLQuery("select COUNT(*) num,custSource from t_customer GROUP BY custSource");
		/**
		 * 因为返回值有两个字段，由于没有对应实体类
		 * 所以把返回数据转换map结构
		 */
		//map是一个接口，需要实现类
		sqlQuery.setResultTransformer(Transformers.aliasToBean(HashMap.class));
		List list = sqlQuery.list();
		return list;
	}
}
