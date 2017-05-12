package org.blueo.commons.persistent.dao.impl;

import java.io.Serializable;

import org.blueo.commons.persistent.dao.Crud;

import com.google.common.reflect.TypeToken;

import io.github.xinyangpan.commons.CommonUtils;

public abstract class AbstractCrud<T, K extends Serializable> implements Crud<T, K> {
	protected final Class<T> parameterizedClass;

	@SuppressWarnings("serial")
	public AbstractCrud() {
		parameterizedClass = CommonUtils.getParameterizedClass(new TypeToken<T>(this.getClass()) {});
	}
	
	public AbstractCrud(Class<T> parameterizedClass) {
		this.parameterizedClass = parameterizedClass;
	}
	
	@Override
	public void deleteById(K id) {
		T entity = this.getById(id);
		if (entity != null) {
			this.delete(entity);
		}
	}
	
}