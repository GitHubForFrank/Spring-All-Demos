package com.zmz.mybatis.helper;

import com.zmz.mybatis.constants.MybatisConstant;
import com.zmz.mybatis.infrastructure.dao.entity.UserEntity;
import com.zmz.mybatis.infrastructure.dao.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
public class RepositoryHelperImplTest {

    @Test
    public void can_run_original_01() throws IOException {
        //使用MyBatis提供的Resources类加载mybatis的配置文件
        Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml");
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sessionFactory.openSession();
        // session.getMapper(UserMapper.class); 其实也是根据UserMapper文件的包路径寻找Mapper.xml文件对应的
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        UserEntity user= mapper.selectByPrimaryKey(1L);
        System.out.println(user.toString());
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void can_run_original_02() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(MybatisConstant.MYBATIS_CONFIG_FILE_PATH);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Object obj = sqlSession.selectOne("com.zmz.mybatis.infrastructure.dao.mapper.UserMapper.selectByPrimaryKey", 1L);
        System.out.println(obj.toString());
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test_selectOne(){
        RepositoryHelper repositoryHelper = RepositoryHelperImpl.getInstance();
        UserEntity result = repositoryHelper.selectOne(UserEntity.class,"selectByPrimaryKey",1L);
        if(result!=null){
            System.out.println(result.toString());
        }else {
            System.out.println("查询失败");
        }

        System.out.println("========================");
        UserEntity result02 = repositoryHelper.selectOne("com.zmz.mybatis.infrastructure.dao.mapper.UserMapper","selectByPrimaryKey",1L);
        if(result!=null){
            System.out.println(result02.toString());
        }else {
            System.out.println("查询失败");
        }
    }

    @Test
    public void test_selectList(){
        RepositoryHelper repositoryHelper = RepositoryHelperImpl.getInstance();
        List<UserEntity> result = repositoryHelper.selectList(UserEntity.class,"queryAllUser",null);
        System.out.println(result.size());
    }


}