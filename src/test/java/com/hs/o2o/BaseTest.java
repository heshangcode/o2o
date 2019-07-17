package com.hs.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring和Junit整合，Junit启动时加载SpringIOC容器
 */
//这个意思是告诉Spring，我们要用这个SpringJUnit4ClassRunner类来跑这个单元测试
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件的位置
//在类启动的时候，会加载这些文件
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest {
}
