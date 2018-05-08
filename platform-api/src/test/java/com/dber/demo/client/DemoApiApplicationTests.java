package com.dber.demo.client;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoClientConfig.class})
public class DemoApiApplicationTests {

  private static final Log log = LogFactory.getLog(DemoApiApplicationTests.class);

  @Autowired
  private UserClient userClient;

  @Test
  public void contextLoads() {
  }

  @Test
  public void get() {
    log.info("*********************************************************************************************************************");
    log.info(JSON.toJSONString(userClient.get()));
    log.info("*********************************************************************************************************************");
  }
}
