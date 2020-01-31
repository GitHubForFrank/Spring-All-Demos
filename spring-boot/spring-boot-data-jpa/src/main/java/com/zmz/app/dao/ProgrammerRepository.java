package com.zmz.app.dao;

import com.zmz.app.bean.Programmer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-31 13:20
 * @description : 查询接口继承自CrudRepository,CrudRepository 默认定义了部分增删改查方法
 */
public interface ProgrammerRepository extends CrudRepository<Programmer, Integer> {

    /**
     * 方法名遵循命名规范的查询 更多命名规范可以参考官方文档所列出的这张表格
     * https://docs.spring.io/spring-data/jpa/docs/2.1.3.RELEASE/reference/html/#jpa.query-methods.query-creation
     * @param name
     * @return
     */
    List<Programmer> findAllByName(String name);

    /**
     *分页排序查询
     * @param pageable
     * @return
     */
    Page<Programmer> findAll(Pageable pageable);

    /**
     * 占位符查询
     *
     * @param name
     * @param salary
     * @param order
     * @return
     */
    @Query(value = "select u from Programmer u where u.name = ?1 or u.salary =  ?2")
    List<Programmer> findByConditionAndOrder(String name, float salary, Sort.Order order);


    /**
     * 传入参数名称
     *
     * @param name
     * @param age
     * @return
     */
    @Query("select u from Programmer u where u.name = :name or u.age = :age")
    Programmer findByParam(@Param("name") String name, @Param("age") int age);

}
