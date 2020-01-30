package com.zmz.app;

import com.zmz.app.domain.model.UserModel;
import com.zmz.app.domain.repository.UserRepository;
import com.zmz.app.domain.repository.UserRepository01;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-16 22:53
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MultipleDataSourceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepository01 userRepository01;

    /**
     * 没有@Transactional的情况下测试两个数据库查数据
     */
    @Test
    public void test_query(){
        System.out.println("=======================================");
        System.out.println("主数据源-测试查询功能 ......");
        List<UserModel> list = userRepository.queryAllUser();
        if(list!=null){
            for (UserModel userModel : list) {
                System.out.println(userModel);
            }
        }else{
            System.out.println("主数据源 没有查询到数据。");
        }

        System.out.println("=======================================");
        System.out.println("从数据源01-测试查询功能 ......");
        List<UserModel> list01 = userRepository01.queryAllUser();
        if(list01!=null){
            for (UserModel userModel : list01) {
                System.out.println(userModel);
            }
        }else{
            System.out.println("从数据源01 没有查询到数据。");
        }
    }

    /**
     * 测试没有@Transactional的情况
     */
//    @Test
    public void test_create(){
        System.out.println("=======================================");
        System.out.println("主数据源-测试创建功能 ......");
        UserModel userModel = new UserModel("master-xiaoming","IT","123456","abc");
        userRepository.create(userModel);

        System.out.println("=======================================");
        System.out.println("从数据源01-测试创建功能 ......");
        UserModel userModel01 = new UserModel("slave1-xiaoming","IT","123456","abc");
        userRepository01.create(userModel01);

    }

    /**
     * 测试分布式事务
     *
     * userRepository.updateUser()添加了事务的注解，在userRepository01.updateUser()之前执行，
     * 当userRepository01.updateUser()报错，userRepository.updateUser()正常的话，userRepository.updateUser()应该是提交事务
     *
     * userRepository01.updateUser()添加了事务的注解，当报错的时候，应该进行事务回滚，即不Update数据库
     */
//    @Test
    public void test_update(){
        System.out.println("=======================================");
        System.out.println("主数据源-测试 Update ......");
        List<UserModel> list = userRepository.queryAllUser();
        if(list!=null){
            UserModel userModel = list.get(0);
            System.out.println("将要更新User信息："+userModel);
            userModel.setName("tx-s-master-xiaoming");
            userRepository.updateUser(userModel);
        }else{
            System.out.println("主数据源 没有查询到数据。");
        }

        System.out.println("=======================================");
        System.out.println("从数据源01-测试 Update ......");
        List<UserModel> list01 = userRepository01.queryAllUser();
        if(list01!=null){
            UserModel userModel = list01.get(0);
            System.out.println("将要更新User信息："+userModel);
            userModel.setName("tx-f-slave1-xiaoming");
            userRepository01.updateUser(userModel);
        }else{
            System.out.println("从数据源01 没有查询到数据。");
        }
    }
}
