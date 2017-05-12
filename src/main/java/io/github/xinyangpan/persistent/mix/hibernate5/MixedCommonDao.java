package io.github.xinyangpan.persistent.mix.hibernate5;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;

import io.github.xinyangpan.persistent.dao.CommonDao;
import io.github.xinyangpan.persistent.dao.EntityDao;
import io.github.xinyangpan.persistent.jdbc.impl.JdbcDao;

public class MixedCommonDao implements CommonDao {
	// DI
	private HibernateTemplate hibernateTemplate;
	private JdbcTemplate jdbcTemplate;
	// Internal Process
	private ConcurrentMap<Class<?>, EntityDao<?, ?>> daoMap = Maps.newConcurrentMap();

	// -----------------------------
	// ----- Single
	// -----------------------------

	@Override
	public <T> T getById(Class<T> entityClass, Serializable id) {
		return hibernateTemplate.get(entityClass, id);
	}

	@Override
	public <T> Serializable save(T t) {
		if (t == null) {
			return null;
		}
		return hibernateTemplate.save(t);
	}

	@Override
	public <T> void update(T t) {
		if (t == null) {
			return;
		}
		hibernateTemplate.update(t);
	}

	@Override
	public <T> void delete(T t) {
		if (t == null) {
			return;
		}
		hibernateTemplate.update(t);
	}

	@Override
	public <T> void deleteById(Class<T> entityClass, Serializable id) {
		T t = this.getById(entityClass, id);
		this.delete(t);
	}

	// -----------------------------
	// ----- Batch
	// -----------------------------

	@Override
	public <T> void saveAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		getEntityDao(list).saveAll(list);
	}

	@Override
	public <T> void updateAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		getEntityDao(list).updateAll(list);
	}

	@Override
	public <T> void deleteAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		getEntityDao(list).deleteAll(list);
	}

	@SuppressWarnings("unchecked")
	private <T> EntityDao<T, ?> getEntityDao(List<T> list) {
		Class<T> entityClass = (Class<T>) list.get(0).getClass();
		EntityDao<T, ?> entityDao = (EntityDao<T, ?>) daoMap.get(entityClass);
		if (entityDao != null) {
			return entityDao;
		}
		entityDao = doGetEntityDao(entityClass);
		daoMap.putIfAbsent(entityClass, entityDao);
		return (JdbcDao<T, ?>) daoMap.get(entityClass);
	}

	protected <T> EntityDao<T, ?> doGetEntityDao(Class<T> entityClass) {
		JdbcDao<T, ?> jdbcDao;
		jdbcDao = new JdbcDao<>(entityClass);
		jdbcDao.setJdbcTemplate(jdbcTemplate);
		jdbcDao.init();
		return jdbcDao;
	}

	// -----------------------------
	// ----- Search
	// -----------------------------

	@Override
	public <T> List<T> findByExample(T t) {
		return hibernateTemplate.findByExample(t);
	}

	// -----------------------------
	// ----- Getter Setter
	// -----------------------------

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
