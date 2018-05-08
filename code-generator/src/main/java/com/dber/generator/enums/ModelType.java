package com.dber.generator.enums;

/**
 * <li>文件名称: ModelType.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: 10个</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月24日
 */
public enum ModelType {
  entity, AppConfig, ServiceConfig, WebConfig, _mapper, _auto_mapper, // xml 映射文件
  Mapper, // java mapper 接口
  Service, ServiceImpl, Controller, Application, ApplicationTest, projectJavaNameClientTest,
  settings, build, application, projectJavaNameClient, projectJavaNameApiController, projectJavaNameApiConfig,
  projectJavaNameClientService, IprojectJavaNameClient, projectJavaNamePublicController,
  projectJavaNameApiApplicationTests, PackageInfo, projectJavaNameApplication,
  bootstrap, logback, ExampleApi, ExampleController;

  /**
   * 静态配置文件
   *
   * @return
   */
  public boolean isConfig() {
    return this == projectJavaNameApiController || this == AppConfig || this == ModelType.ServiceConfig
            || this == ModelType.WebConfig || this == projectJavaNameClientTest
            || this == Application || this == ApplicationTest || this == settings
            || this == ModelType.build || this == ModelType.application || this == ModelType.projectJavaNameClient
            || this == projectJavaNameClientService || this == IprojectJavaNameClient
            || this == projectJavaNamePublicController || this == projectJavaNameApiConfig
            || this == projectJavaNameApiApplicationTests || this == PackageInfo || this == projectJavaNameApplication
            || this == bootstrap || this == logback
            || this == ExampleApi || this == ExampleController
            ;
  }

  /**
   * <pre>
   * 文件名不需要工程java名前缀的
   * </pre>
   *
   * @return
   */
  public boolean isNotNeedJavaProjectName() {
    return ModelType.projectJavaNameClient == this || settings == this ||
            Application == this || ApplicationTest == this ||
            this == ModelType.build || this == ModelType.application || this == projectJavaNameClientTest ||
            this == projectJavaNameApiController
            || this == projectJavaNameClientService || this == IprojectJavaNameClient
            || this == projectJavaNamePublicController || this == projectJavaNameApiConfig
            || this == projectJavaNameApiApplicationTests || this == PackageInfo || this == projectJavaNameApplication
            || this == bootstrap || this == logback
            || this == ExampleApi || this == ExampleController
            ;
  }
}
