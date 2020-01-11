package com.zmz.mybatis.helper;

import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
public interface RepositoryHelper {

    SqlSession getSqlSession();

    public abstract <T> T selectOne(String sqlNamespace, String sqlId, Object param);
    public abstract <E> E selectOne(Class<E> clzz, String sqlId, Object param);

    public abstract <E> List<E> selectList(Class<E> clzz, String sqlId, Object param);
    public abstract <E> List<E> selectList(String sqlNamespace, String sqlId, Object param);
    <T> List<T> selectObjectList(String sqlNamespace, String sqlId, Object param);

    public abstract int insertObject(String sqlNamespace, String sqlId, Object param);
    public abstract int insertObject(Class<?> clzz, String sqlId, Object param);

    public abstract int updateObject(String sqlNamespace, String sqlId, Object param);
    public abstract int updateObject(Class<?> clzz, String sqlId, Object param);

    public abstract int deleteObject(String sqlNamespace, String sqlId, Object param);
    public abstract int deleteObject(Class<?> clzz, String sqlId, Object param);

}
