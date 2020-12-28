package com.zmz.core.infrastructure.dao.impl;

import com.zmz.core.infrastructure.dao.Dao;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-05 23:04
 */
@Repository
public class DaoImpl<T> implements Dao<T> {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session ;
	private Transaction tx ;
	
	public Session initSession(){
		session = sessionFactory.getCurrentSession();
		tx = session.getTransaction();
		if(!tx.isActive()){
			session.beginTransaction();
		}
		return session;
	}

	@Override
	public T get(Class<T> clazz, long id) {
		initSession();
		return this.session.get(clazz, id);
	}

	@Override
	public Query createQuery(String hql) {
		initSession();
		return session.createQuery(hql);
	}

	@Override
	public void delete(T baseBean) {
		initSession();
		session.delete(baseBean);
		tx.commit();
	}

	@Override
	public void delete(String hql, Object... params) {
		initSession();
		Query query = session.createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		query.executeUpdate();
		tx.commit();
	}

	@Override
	public List<T> getList(String hql) {
		initSession();
		List<T> list = session.createQuery(hql).list();
		tx.commit();
		return list;
	}

	@Override
	public int getTotalCount(String hql, Object... params) {
		Query query = createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		Object obj = query.uniqueResult();
		return ((Long) obj).intValue();
	}

	@Override
	public List<T> getList(String hql, int firstResult, int maxResults,	Object... params) {
		Query query = createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		List<T> list = query.setFirstResult(firstResult).setMaxResults(maxResults).list();
		tx.commit();
		return list;
	}

	@Override
	public void save(T baseBean) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(baseBean);
		tx.commit();
	}

	@Override
	public void update(T baseBean) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(baseBean);
		tx.commit();
	}

	@Override
	public void saveOrUpdate(T baseBean) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(baseBean);
		tx.commit();
	}

	@Override
	public List<T> getList(String hql, Object... params) {
		Query query = createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		List<T> list = query.list();
		tx.commit();
		return list;
	}

	@Override
	public List<T> getAll(String hql, Integer pageNum, Integer pageCount,Object... params) {
		Query query = createQuery(hql);
		for (int i = 0; params != null && i < params.length; i++){
			query.setParameter(i, params[i]);
		}
		List<T> list = query.setFirstResult((pageNum-1)*pageCount).setMaxResults(pageCount).list();
		tx.commit();
		return list;
	}

}
