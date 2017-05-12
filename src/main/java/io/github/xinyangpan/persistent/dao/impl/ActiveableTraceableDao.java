package io.github.xinyangpan.persistent.dao.impl;

import io.github.xinyangpan.persistent.dao.po.activeable.ActiveablePo;
import io.github.xinyangpan.persistent.dao.po.traceable.TraceablePo;

public class ActiveableTraceableDao<T extends ActiveablePo & TraceablePo<U>, K, U> extends EntityDaoAdaptor<T, K> {

	public ActiveableTraceableDao() {
		super();
	}

	public ActiveableTraceableDao(Class<T> parameterizedClass) {
		super(parameterizedClass);
	}

}
