package com.#{packageName};

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationTest {

  public static void main(String[] args) {
    SpringApplication.run(ApplicationTest.class, args);
  }

  @Test
  public void test() {

  }
}
