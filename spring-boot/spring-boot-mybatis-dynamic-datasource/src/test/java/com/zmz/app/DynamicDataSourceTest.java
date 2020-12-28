package com.zmz.app;

import com.zmz.app.domain.model.UserModel;
import com.zmz.app.domain.repository.UserRepository;
import com.zmz.app.domain.repository.UserRepository01;
import com.zmz.app.domain.repository.UserRepository02;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-16 21:28
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DynamicDataSourceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepository01 userRepository01;
    @Autowired
    private UserRepository02 userRepository02;

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

        System.out.println("=======================================");
        System.out.println("从数据源02-测试查询功能 ......");
        List<UserModel> list02 = userRepository02.queryAllUser();
        if(list02!=null){
            for (UserModel userModel : list02) {
                System.out.println(userModel);
            }
        }else{
            System.out.println("从数据源02 没有查询到数据。");
        }
    }

    @Test
    public void test_create(){
        System.out.println("=======================================");
        System.out.println("主数据源-测试创建功能 ......");
        UserModel userModel = new UserModel("master-xiaoming","IT","123456","abc");
        userRepository.create(userModel);

        System.out.println("=======================================");
        System.out.println("从数据源01-测试创建功能 ......");
        UserModel userModel01 = new UserModel("slave1-xiaoming","IT","123456","abc");
        userRepository01.create(userModel01);

        System.out.println("=======================================");
        System.out.println("从数据源02-测试创建功能 ......");
        UserModel userModel02 = new UserModel("slave2-xiaoming","IT","123456","abc");
        userRepository02.create(userModel02);
    }

}
