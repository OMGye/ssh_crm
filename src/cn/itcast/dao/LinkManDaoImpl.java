package cn.itcast.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.itcast.entity.Customer;
import cn.itcast.entity.LinkMan;

public class LinkManDaoImpl implements LinkManDao {
    private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
    //�����ϵ��
	public void add(LinkMan linkMan) {
		hibernateTemplate.save(linkMan);
		
	}
    //�鿴������ϵ��
	@SuppressWarnings("all")
	public List<LinkMan> listLinkMan() {
		
		return (List<LinkMan>) hibernateTemplate.find("from LinkMan");
	}
   //����idֵ��ѯ��ϵ��
	public LinkMan findOne(int linkid) {
		
		return hibernateTemplate.get(LinkMan.class, linkid);
	}
	//�޸���ϵ��
	public void update(LinkMan linkMan) {
		hibernateTemplate.update(linkMan);
	}
	//�ۺϲ�ѯ
	@SuppressWarnings("all")
	public List<LinkMan> findMoreCondition(LinkMan linkMan) {
//		String hql="from LinkMan where 1=1";
//		List<Object> plist = new ArrayList<Object>();
//		if(linkMan.getLkmName()!=null&!"".equals(linkMan.getLkmName())) {
//			hql+=" and lkmName=?";
//			plist.add(linkMan.getLkmName());
//		}
//		if(linkMan.getCustomer().getCid()!=null&linkMan.getCustomer().getCid()>0) {
//			hql+=" and customer.cid=?";
//			plist.add(linkMan.getCustomer().getCid());			
//		}
//		String hql = "from LinkMan where 1=1";
//		
//		if (linkMan.getLkmName() != null & !"".equals(linkMan.getLkmName())) {
//			hql += " and lkmName="+linkMan.getLkmName();
//			
//		}
//		if (linkMan.getCustomer().getCid() != null
//				& linkMan.getCustomer().getCid()>0) {
//			hql += " and customer.cid="+linkMan.getCustomer().getCid();
//			
//		}
//
//		return (List<LinkMan>) hibernateTemplate.find(hql);
	//�����ַ�ʽ �����߶����ѯ
		//1.�������߶���
	 DetachedCriteria  criteria = DetachedCriteria.forClass(LinkMan.class);
	 if(linkMan.getLkmName()!=null&!"".equals(linkMan.getLkmName())) {
			criteria.add(Restrictions.eq("lkmName",linkMan.getLkmName()));
		  }
	 if(linkMan.getCustomer().getCid()!=null&linkMan.getCustomer().getCid()>0) {
		 criteria.add(Restrictions.eq("customer.cid",linkMan.getCustomer().getCid()));
	 }
	 return (List<LinkMan>) hibernateTemplate.findByCriteria(criteria);
	}
	
	//��ϵ�������ͻ��ۺϲ�ѯ
	public List findCountCustomer() {
		//�õ��뱾���߳����󶨵�session
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		SQLQuery sqlQuery = session.createSQLQuery("select c.num,d.custName from (select count(*) num,clid from t_linkman group by clid) c,t_customer d where d.cid=c.clid");
		sqlQuery.setResultTransformer(Transformers.aliasToBean(HashMap.class));
		List list = sqlQuery.list();
		return list;
	}

}
