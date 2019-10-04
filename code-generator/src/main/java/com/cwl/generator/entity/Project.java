package com.cwl.generator.entity;

import com.cwl.tool.util.Util;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * <li>文件名称: Project.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月23日
 */
@Data
public class Project {
  String modelPath;

  /**
   * 生成的工程存放目录
   */
  String projectDir;

  String dbSchemaName;

  String projectName;

  String packageName;

  List<Table> tables;

  List<ModelFile> modelFiles;

  int maxCharacterCount;

  public void setTables(List<Table> tables) {
    for (Table table : tables) {
      table.setProject(this);
    }
    this.tables = tables;
  }

  public Project(String modelPath, String projectDir, String dbSchemaName, String projectName, String packageName, int maxCharacterCount) {
    this.modelPath = modelPath;
    this.projectDir = projectDir;
    this.dbSchemaName = dbSchemaName;
    this.projectName = projectName;
    this.packageName = packageName;
    this.maxCharacterCount = maxCharacterCount;
  }

  public Project(String modelPath, String projectDir, String dbSchemaName, String projectName, String packageName) {
    this(modelPath, projectDir, dbSchemaName, projectName, packageName, 101);
  }

  public String getJavaName() {
    String tmp = Util.toJavaStyle(projectName);
    return Character.toUpperCase(tmp.charAt(0)) + tmp.substring(1);
  }

  public String getPackageDir() {
    String[] dirs = packageName.split("\\.");
    String dir = dirs[0];
    for (int i = 1, len = dirs.length; i < len; i++) {
      dir += File.separator + dirs[i];
    }
    return dir;
  }

  public String getUpperCaseName() {
    return packageName.toUpperCase();
  }

  /**
   * <pre>
   * 获取工程根路径
   * </pre>
   *
   * @return
   */
  public String getProjectPath() {
    return projectDir + File.separator + projectName + File.separator;
  }

}
