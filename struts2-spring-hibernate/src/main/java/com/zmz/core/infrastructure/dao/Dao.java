package com.zmz.core.infrastructure.dao;

import org.hibernate.Query;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-05 23:04
 */
public interface Dao<T> {
	
	T get(Class<T> clazz, long id);
	void save(T baseBean);
	void update(T baseBean);
	void saveOrUpdate(T baseBean);
	void delete(T baseBean);
	void delete(String hql, Object... params);
	List<T> getList(String hql);
	int getTotalCount(String hql, Object... params);
	List<T> getList(String hql, int firstResult, int maxSize, Object... params);
	Query createQuery(String hql);
	List<T> getList(String hql, Object... params);
	List<T> getAll(String hql, Integer pageNum, Integer pageCount, Object... params);

}
