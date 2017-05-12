package io.github.xinyangpan.persistent.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import io.github.xinyangpan.persistent.dao.CommonDao;
import io.github.xinyangpan.persistent.dao.EntityDao;

public class DelegateCommonDao implements CommonDao {
	// 
	protected ConcurrentMap<Class<?>, EntityDao<?, ?>> map = Maps.newConcurrentMap();

	@SuppressWarnings("rawtypes")
	public void init(Map<String, EntityDao> entityDaoMap) {
		if (entityDaoMap != null) {
			for (EntityDao entityDao : entityDaoMap.values()) {
				Class<?> enityClass = entityDao.getEntityClass();
				map.put(enityClass, entityDao);
			}
		}
	}

	// -----------------------------
	// ----- Single
	// -----------------------------

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getById(Class<T> entityClass, Serializable id) {
		EntityDao<T, Serializable> entityDao = (EntityDao<T, Serializable>) this.getEntityDao(entityClass);
		return entityDao.getById(id);
	}

	@Override
	public <T> Serializable save(T t) {
		if (t == null) {
			return null;
		}
		return (Serializable) this.getEntityDao(t).save(t);
	}

	@Override
	public <T> void update(T t) {
		if (t == null) {
			return;
		}
		this.getEntityDao(t).update(t);
	}

	@Override
	public <T> void delete(T t) {
		if (t == null) {
			return;
		}
		this.getEntityDao(t).update(t);
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
	protected <T> EntityDao<T, ?> getEntityDao(List<T> list) {
		Preconditions.checkNotNull(list);
		Class<T> entityClass = (Class<T>) list.get(0).getClass();
		return getEntityDao(entityClass);
	}

	@SuppressWarnings("unchecked")
	protected <T> EntityDao<T, ?> getEntityDao(T t) {
		Objects.requireNonNull(t);
		return (EntityDao<T, ?>) getEntityDao(t.getClass());
	}

	@SuppressWarnings("unchecked")
	protected <T> EntityDao<T, ?> getEntityDao(Class<T> entityClass) {
		return (EntityDao<T, ?>) Objects.requireNonNull(map.get(entityClass));
	}

	// -----------------------------
	// ----- Search
	// -----------------------------

	@Override
	public <T> List<T> findByExample(T t) {
		return this.getEntityDao(t).findByExample(t);
	}

}
