package io.github.xinyangpan.persistent.jdbc.impl;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.util.ReflectionUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import io.github.xinyangpan.persistent.entity.EntityColumn;
import io.github.xinyangpan.persistent.jdbc.util.ColumnRowMapper;

public class JdbcSelect<T, K> extends JdbcOperation<T, K> {
	//
	private ColumnRowMapper<T> rowMapper;

	@PostConstruct
	public void init() {
		rowMapper = new ColumnRowMapper<>(entityTable);
	}

	public T getById(K id) {
		String selectSqlBeforeWhere = rowMapper.getSelectSqlBeforeWhere();
		String idColumnName = entityTable.getIdCol().getColumnName();
		String sql = String.format("%s WHERE % = ?", selectSqlBeforeWhere, idColumnName);
		return Iterables.getOnlyElement(jdbcTemplate.query(sql, new Object[] { id }, rowMapper), null);
	}

	public List<T> getAll() {
		return jdbcTemplate.query(rowMapper.getSelectSqlBeforeWhere(), rowMapper);
	}

	public List<T> findByExample(T example) {
		Objects.requireNonNull(example);
		List<String> whereSqlPieces = Lists.newArrayList();
		List<Object> whereSqlValues = Lists.newArrayList();
		//
		for (EntityColumn entityColumn : entityTable.getAllCols()) {
			PropertyDescriptor pd = entityColumn.getPropertyDescriptor();
			Object value = ReflectionUtils.invokeMethod(pd.getReadMethod(), example);
			if (value != null) {
				whereSqlPieces.add(String.format("%s = ?", entityColumn.getColumnName()));
				whereSqlValues.add(value);
			}
		}
		// 
		String selectSqlBeforeWhere = rowMapper.getSelectSqlBeforeWhere();
		String sql = String.format("%s WHERE %s", selectSqlBeforeWhere, Joiner.on(", ").join(whereSqlPieces));
		return jdbcTemplate.query(sql, whereSqlValues.toArray(), rowMapper);
	}
	
}
