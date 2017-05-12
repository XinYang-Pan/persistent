package io.github.xinyangpan.persistent.jdbc.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import io.github.xinyangpan.persistent.entity.EntityTable;
import io.github.xinyangpan.persistent.jdbc.ColumnPpss;
import io.github.xinyangpan.persistent.util.PersistentUtils;

public class JdbcInsert<T, K> extends JdbcOperation<T, K> {
	//
	private String insertSql;
	private ParameterizedPreparedStatementSetter<T> insertPss;

	@PostConstruct
	public void init() {
		String tableName = entityTable.getTableName();
		List<String> columnNames = EntityTable.getColumnNames(entityTable.getNoneGenValueCols());
		if (entityTable.isSequenceGenerated()) {
			String seqName = entityTable.getSeqName();
			String columnName = entityTable.getIdCol().getColumnName();
			insertSql = PersistentUtils.buildSeqInsertSql(tableName, seqName, columnName, columnNames);
		} else {
			insertSql = PersistentUtils.buildInsertSql(tableName, columnNames);
		}
		insertPss = new ColumnPpss<T>(entityTable.getNoneGenValueCols());
	}

	public K insert(T t) {
		if (!entityTable.getIdCol().isGeneratedValue()) {
			this.insert(Collections.singletonList(t));
			return getIdHandler().getId(t);
		}
		this.insert(Collections.singletonList(t));
		return null;
	}

	public void insert(List<T> entities) {
		jdbcTemplate.batchUpdate(insertSql, entities, this.batchSize(), insertPss);
	}

}
