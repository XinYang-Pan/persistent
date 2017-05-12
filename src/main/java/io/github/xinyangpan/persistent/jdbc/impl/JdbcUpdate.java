package io.github.xinyangpan.persistent.jdbc.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import io.github.xinyangpan.persistent.entity.EntityTable;
import io.github.xinyangpan.persistent.jdbc.BlueoJdbcs;
import io.github.xinyangpan.persistent.jdbc.util.ColumnPpss;

public class JdbcUpdate<T, K> extends JdbcOperation<T, K> {
	//
	private String updateSql;
	private ParameterizedPreparedStatementSetter<T> updatePss;

	@PostConstruct
	public void init() {
		// update
		updateSql = BlueoJdbcs.buildUpdateSql(entityTable.getTableName(), EntityTable.getColumnNames(entityTable.getNoneIdCols()), entityTable.getIdCol().getColumnName());
		updatePss = new ColumnPpss<T>(entityTable.getAllCols());
	}

	public void update(T t) {
		this.update(Collections.singletonList(t));
	}

	public void update(List<T> entities) {
		jdbcTemplate.batchUpdate(updateSql, entities, this.batchSize(), updatePss);
	}

}
