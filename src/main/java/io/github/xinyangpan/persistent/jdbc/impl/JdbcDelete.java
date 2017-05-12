package io.github.xinyangpan.persistent.jdbc.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import io.github.xinyangpan.persistent.jdbc.ColumnPpss;
import io.github.xinyangpan.persistent.util.PersistentUtils;

public class JdbcDelete<T, K> extends JdbcOperation<T, K> {
	//
	private String deleteSql;
	private ParameterizedPreparedStatementSetter<T> deletePss;

	@PostConstruct
	public void init() {
		// delete
		deleteSql = PersistentUtils.buildDeleteSql(entityTable.getTableName(), entityTable.getIdCol().getColumnName());
		deletePss = new ColumnPpss<T>(Collections.singletonList(entityTable.getIdCol()));
	}

	public void delete(T t) {
		this.delete(Collections.singletonList(t));
	}

	public void deleteById(K id) {
		T t = BeanUtils.instantiate(entityTable.getParameterizedClass());
		getIdHandler().setId(t, id);
		this.delete(t);
	}

	public void delete(List<T> entities) {
		jdbcTemplate.batchUpdate(deleteSql, entities, this.batchSize(), deletePss);
	}

}
