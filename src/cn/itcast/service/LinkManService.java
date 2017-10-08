package cn.itcast.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.LinkManDao;
import cn.itcast.entity.LinkMan;
@Transactional
public class LinkManService {
   private LinkManDao linkManDao;

public void setLinkManDao(LinkManDao linkManDao) {
	this.linkManDao = linkManDao;
	
}

public void add(LinkMan linkMan) {
	linkManDao.add(linkMan);
	
}

public List<LinkMan> listLinkMan() {
	
	return linkManDao.listLinkMan();
}

public LinkMan findOne(int linkid) {
	
	return linkManDao.findOne(linkid);
}

public void update(LinkMan linkMan) {
	linkManDao.update(linkMan);
	
}

public List<LinkMan> findMoreCondition(LinkMan linkMan) {
	
	return linkManDao.findMoreCondition(linkMan);
}

public List findCountCustomer() {
	
	return linkManDao.findCountCustomer();
}

}
