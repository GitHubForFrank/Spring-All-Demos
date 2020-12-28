package com.zmz.app;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ASNPHDG
 * @create 2020-01-27 17:34
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, classes={SpringBootWebsocketApplication.class})
public class SpringBootWebsocketApplicationTests {

    @Test
    public void contextLoads() {
    }

}

