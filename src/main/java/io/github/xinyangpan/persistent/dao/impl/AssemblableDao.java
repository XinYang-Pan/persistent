package io.github.xinyangpan.persistent.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Iterables;

import io.github.xinyangpan.persistent.dao.Crud;
import io.github.xinyangpan.persistent.dao.CrudBatch;
import io.github.xinyangpan.persistent.dao.Search;

public class AssemblableDao<T, K> extends AbstractEntityDao<T, K> {
	private Crud<T, K> crud;
	private CrudBatch<T, K> crudBatch;
	private Search<T> search;

	public AssemblableDao() {
		super();
	}

	public AssemblableDao(Class<T> parameterizedClass) {
		super(parameterizedClass);
	}
	
	// -----------------------------
	// ----- CRUD
	// -----------------------------

	@Override
	public T getById(K id) {
		return crud.getById(id);
	}

	@Override
	public List<T> getAll() {
		return crud.getAll();
	}

	@Override
	public K save(T t) {
		return crud.save(t);
	}

	@Override
	public void update(T t) {
		crud.update(t);
	}

	@Override
	public void delete(T t) {
		crud.delete(t);
	}

	@Override
	public void deleteById(K id) {
		crud.deleteById(id);
	}

	// -----------------------------
	// ----- CRUD Batch
	// -----------------------------

	@Override
	public void saveAll(List<T> list) {
		crudBatch.saveAll(list);
	}

	@Override
	public void updateAll(List<T> list) {
		crudBatch.updateAll(list);
	}

	@Override
	public void deleteAll(List<T> list) {
		crudBatch.deleteAll(list);
	}

	// -----------------------------
	// ----- Search
	// -----------------------------

	@Override
	public List<T> findByExample(T t) {
		return search.findByExample(t);
	}

	public final T findByExample(T t, boolean nullableResult) {
		List<T> list = this.findByExample(t);
		if (nullableResult && CollectionUtils.isEmpty(list)) {
			return null;
		}
		return Iterables.getOnlyElement(list);
	}

	public final List<T> findAll() {
		T t = BeanUtils.instantiate(parameterizedClass);
		return this.findByExample(t);
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	public Crud<T, K> getCrud() {
		return crud;
	}

	public void setCrud(Crud<T, K> crud) {
		this.crud = crud;
	}

	public CrudBatch<T, K> getCrudBatch() {
		return crudBatch;
	}

	public void setCrudBatch(CrudBatch<T, K> crudBatch) {
		this.crudBatch = crudBatch;
	}

	public Search<T> getSearch() {
		return search;
	}

	public void setSearch(Search<T> search) {
		this.search = search;
	}

}
