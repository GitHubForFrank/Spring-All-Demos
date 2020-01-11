package com.zmz.mybatis.helper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 当采用的是Mybatis的Mapper接口方式实现CRUD，会遇到事务提交回滚操作的操作不方便
 * 本类不采用 Mapper接口方式，通过泛型，将传值 sqlNamespace ，sqlId ， param 和来实现CRUD
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
public class RepositoryHelperImpl implements RepositoryHelper{

    /**
     * 使用final修饰,清楚地表明了这个类是单例
     */
    public static final RepositoryHelperImpl INSTANCE = new RepositoryHelperImpl();

    public static final String SQLNAME_SEPARATOR = ".";
    protected SqlSession sqlSession = null;
    protected SqlSessionFactory sqlSessionFactory = null;

    public RepositoryHelperImpl() {
    }

    public RepositoryHelperImpl(String mybatisConfigFilePath ) {
        try{
            InputStream inputStream = Resources.getResourceAsStream(mybatisConfigFilePath);
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            this.sqlSession = sqlSessionFactory.openSession();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected void isSqlSessionNull() {
        if (sqlSession == null) {
            System.out.println("the object of sqlSession is not init..");
        }
    }

    String getFullSqlId(String sqlNamespace, String sqlId) {
        if (sqlNamespace.endsWith(SQLNAME_SEPARATOR)) {
            return sqlNamespace + sqlId;
        }
        return sqlNamespace + SQLNAME_SEPARATOR + sqlId;
    }

    <E> String getNamespace(Class<E> clzz) {
        String nameSpace = clzz.getName()+"Mapper";
        nameSpace = nameSpace.replaceAll(".entity.",".mapper.");
        System.out.println("nameSpace :" + nameSpace );
        return nameSpace;
    }

    @Override
    public SqlSession getSqlSession() {
        return this.sqlSession;
    }

    @Override
    public <T> T selectOne(String sqlNamespace, String sqlId, Object param) {
        isSqlSessionNull();
        T obj = null;
        String fullSqlId = getFullSqlId(sqlNamespace, sqlId);

        System.out.println("queryForObject execute sqlId =" + fullSqlId);
        System.out.println("queryForObject execute param =" + param);
        try {
            obj = (T) sqlSession.selectOne(fullSqlId, param);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("queryForObject execute result =" + obj);
        return obj;
    }

    @Override
    public <E> E selectOne(Class<E> clzz, String sqlId, Object param) {
        return (E) selectOne(getNamespace(clzz), sqlId, param);
    }

    @Override
    public <E> List<E> selectList(Class<E> clzz, String sqlId, Object param) {
        return selectList(getNamespace(clzz), sqlId, param);
    }

    @Override
    public <E> List<E> selectList(String sqlNamespace, String sqlId,Object param) {
        List<Object> list = selectObjectList(sqlNamespace, sqlId, param);
        List<E> returnList = new ArrayList<E>();
        for (Object e : list) {
            returnList.add((E) e);
        }
        sqlSession.commit();
        return returnList;
    }

    @Override
    public <T> List<T> selectObjectList(String sqlNamespace, String sqlId, Object param) {
        isSqlSessionNull();
        List<T> data = null;
        System.out.println("queryForList execute Sql =" + sqlId);
        System.out.println("queryForList execute param =" + param);
        String fullSqlId = getFullSqlId(sqlNamespace, sqlId);
        try {
            data = sqlSession.selectList(fullSqlId, param);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("queryForList execute result =" + (data == null ? "0" : String.valueOf(data.size())));
        sqlSession.commit();
        return data;
    }

    @Override
    public int insertObject(String sqlNamespace, String sqlId, Object param) {
        isSqlSessionNull();
        int effectedRows = 0;
        String fullSqlId = getFullSqlId(sqlNamespace, sqlId);
        System.out.println("saveObject execute sqlId =" + fullSqlId);
        System.out.println("saveObject execute param =" + param);
        try {
            effectedRows = sqlSession.insert(fullSqlId, param);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("saveObject effectedRows =" + effectedRows);
        sqlSession.commit();
        return effectedRows;
    }

    @Override
    public int insertObject(Class<?> clzz, String sqlId, Object param) {
        return insertObject(getNamespace(clzz), sqlId, param);
    }

    @Override
    public int updateObject(String sqlNamespace, String sqlId, Object param) {
        isSqlSessionNull();
        int effectedRows = 0;
        String fullSqlId = getFullSqlId(sqlNamespace, sqlId);
        System.out.println("editObject execute sqlId =" + fullSqlId);
        System.out.println("editObject execute param =" + param);
        try {
            effectedRows = sqlSession.update(fullSqlId, param);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("editObject effectedRows =" + effectedRows);
        sqlSession.commit();
        return effectedRows;
    }

    @Override
    public int updateObject(Class<?> clzz, String sqlId, Object param) {
        return updateObject(getNamespace(clzz), sqlId, param);
    }

    @Override
    public int deleteObject(String sqlNamespace, String sqlId, Object param) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int deleteObject(Class<?> clzz, String sqlId, Object param) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected void finalize()throws Throwable{
        super.finalize();
        System.err.println("test class end");
    }

}
