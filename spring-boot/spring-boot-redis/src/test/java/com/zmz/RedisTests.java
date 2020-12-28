package com.zmz;

import com.zmz.app.SpringBootRedisApplication;
import com.zmz.core.redis.RedisOperation;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * @author ASNPHDG
 * @create 2020-01-27 12:29
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, classes={SpringBootRedisApplication.class})
public class RedisTests {

    @Autowired
    private RedisOperation redisOperation;

    @Test
    public void StringOperation() {
        redisOperation.StringSet("hello", "redis");
        String s = redisOperation.StringGet("hello");
        Assert.assertEquals(s, "redis");
    }

    @Test
    public void ListOperation() {
        redisOperation.ListSet("skill", Arrays.asList("java", "oracle", "vue"));
        String s = redisOperation.ListLeftPop("skill");
        Assert.assertEquals(s, "vue");
    }

    /*
     * 需要注意的是Redis的集合（set）不仅不允许有重复元素，并且集合中的元素是无序的，
     * 不能通过索引下标获取元素。哪怕你在java中传入的集合是有序的newLinkedHashSet，但是实际在Redis存储的还是无序的集合
     */
    @Test
    public void SetOperation() {
        redisOperation.SetSet("skillSet", Sets.newLinkedHashSet("java", "oracle", "vue"));
        String s = redisOperation.SetPop("skillSet");
        Assert.assertNotNull(s);
    }

}

