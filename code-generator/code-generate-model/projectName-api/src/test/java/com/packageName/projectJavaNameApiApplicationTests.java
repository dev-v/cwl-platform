package com.cwl.#{packageName};


import com.cwl.#{packageName}.api.#{projectJavaName}ApiConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {#{projectJavaName}ApiConfig.class})
public class #{projectJavaName}ApiApplicationTests {

  private static final Log log = LogFactory.getLog(#{projectJavaName}ApiApplicationTests.class);

  @Test
  public void contextLoads() {
    log.info("*********************************************************************************************************************");
    log.info("*********************************************************************************************************************");

  }
}
