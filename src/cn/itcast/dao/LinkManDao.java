package cn.itcast.dao;

import java.util.List;

import cn.itcast.entity.LinkMan;

public interface LinkManDao {

	void add(LinkMan linkMan);

	List<LinkMan> listLinkMan();

	LinkMan findOne(int linkid);

	void update(LinkMan linkMan);

	List<LinkMan> findMoreCondition(LinkMan linkMan);

	List findCountCustomer();

}
