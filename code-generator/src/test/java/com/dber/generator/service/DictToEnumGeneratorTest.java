package com.dber.generator.service;

import com.dber.generator.config.DictGeneratorServiceConfig;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@ContextConfiguration(classes = DictGeneratorServiceConfig.class, initializers = ConfigFileApplicationContextInitializer.class)
@FixMethodOrder(MethodSorters.JVM)
public class DictToEnumGeneratorTest {

    @Autowired
    DictToEnumService dictToEnum;

    //模型文件路径
    private String resourcesPath = "E:\\dber_work\\dber-code-generator\\src\\test\\resources\\";

    @Test
    public void test1_generate_all() {
        dictToEnum.generate(resourcesPath);
    }


}
