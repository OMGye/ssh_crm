package cn.itcast.dao;

import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;

import cn.itcast.entity.Visit;

public class VisitDaoImpl implements VisitDao{
   private HibernateTemplate hibernateTemplate;

public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
	this.hibernateTemplate = hibernateTemplate;
}
//ÃÌº”∞›∑√
public void add(Visit visit) {
	hibernateTemplate.save(visit);
	
}
//∞›∑√¡–±Ì
public List<Visit> findAll() {
	
	return (List<Visit>) hibernateTemplate.find("from Visit");
}
   
}
