package io.github.xinyangpan.persistent.hibernate5;

import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;

import io.github.xinyangpan.persistent.dao.Search;

public class HibernateSearch<T> implements Search<T> {
	//
	protected HibernateTemplate hibernateTemplate;

	@Override
	public List<T> findByExample(T t) {
		return hibernateTemplate.findByExample(t);
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
