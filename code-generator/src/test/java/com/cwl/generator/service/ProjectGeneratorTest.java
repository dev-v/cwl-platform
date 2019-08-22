package com.cwl.generator.service;

import com.cwl.generator.config.ProjectGeneratorServiceConfig;
import com.cwl.generator.entity.Project;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * <li>文件名称: ProjectGeneratorTest.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ProjectGeneratorServiceConfig.class, initializers = ConfigFileApplicationContextInitializer.class)
@FixMethodOrder(MethodSorters.JVM)
public class ProjectGeneratorTest {
  private static final Log log = LogFactory.getLog(ProjectGeneratorTest.class);

  @Autowired
  ProjectGenerator generator;

  private String modelDir = "code-generate-model";
  private String modelPath;
  private String projectDir = "";

  @Before
  public void generatePath() {
//	env PWD=/Users/chenwenlong/IdeaProjects/dber-platform/code-generator
//  properties user.dir=/Users/chenwenlong/IdeaProjects/dber-platform/code-generator
    String temp = System.getProperty("user.dir") + File.separator;

    this.projectDir = temp + "out";
    log.info(this.projectDir);

    this.modelPath = temp + modelDir;
    log.info(this.modelPath);
  }

  @Test
  public void auth() {
    generator.createProject(new Project(modelPath, projectDir, "dber_auth", "auth", "auth"));
  }

  @Test
  public void demo() {
    generator.createProject(new Project(modelPath, projectDir, "dber_demo", "demo", "demo"));
  }

  @Test
  public void test1_generate_dber_plat() {
    generator.createProject(new Project(modelPath, projectDir, "dber_plat", "dber-plat", "plat"));
  }

  @Test
  public void test2_generate_dber_shop() {
    generator.createProject(new Project(modelPath, projectDir, "dber_shop", "dber-shop", "shop"));
  }

  @Test
  public void test2_generate_dber_uploader() {
    generator.createProject(new Project(modelPath, projectDir, "dber_plat", "dber-upload", "upload"));
  }

  @Test
  public void test2_generate_dber_customer() {
    generator.createProject(new Project(modelPath, projectDir, "dber_customer", "dber-customer", "customer"));
  }

  @Test
  public void test_stock() {
    generator.createProject(new Project(modelPath, projectDir, "stock", "stock", "cwl.stock"));
  }

}
