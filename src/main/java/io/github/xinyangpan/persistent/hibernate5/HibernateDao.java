package io.github.xinyangpan.persistent.hibernate5;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.orm.hibernate5.HibernateTemplate;

import io.github.xinyangpan.persistent.dao.impl.AssemblableDao;
import io.github.xinyangpan.persistent.dao.impl.SimpleCrudBatch;

public class HibernateDao<T, K extends Serializable> extends AssemblableDao<T, K> {
	//
	protected HibernateTemplate hibernateTemplate;

	@PostConstruct
	public void init() {
		//
		HibernateCrud<T, K> hibernateCrud = new HibernateCrud<>();
		hibernateCrud.setHibernateTemplate(hibernateTemplate);
		HibernateSearch<T> hibernateSearch = new HibernateSearch<>();
		hibernateSearch.setHibernateTemplate(hibernateTemplate);
		//
		this.setCrud(hibernateCrud);
		this.setCrudBatch(new SimpleCrudBatch<>(hibernateCrud));
		this.setSearch(hibernateSearch);
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
