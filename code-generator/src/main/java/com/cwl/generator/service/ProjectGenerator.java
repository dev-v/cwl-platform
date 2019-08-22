package com.cwl.generator.service;

import com.cwl.generator.entity.ModelFile;
import com.cwl.generator.entity.Project;
import com.cwl.generator.entity.Table;
import com.cwl.generator.file.AbstractFileNameFilter;
import com.cwl.generator.file.AllFileNameFileter;
import com.cwl.generator.file.FileUtil;
import com.cwl.tool.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <li>文件名称: ParserDatabase.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月23日
 */
@Service
public class ProjectGenerator {

  @Autowired
  private ProjectParser parser;

  public void createProject(Project project) {
    parser.parse(project);

    this.ready(project);
  }

  /**
   * <pre>
   * 创建目录
   * 缓存模板文件
   * </pre>
   *
   * @param project
   */
  private void ready(Project project) {
    // 首先清空目录
    for (String file : FileUtil.getFilePaths(project.getProjectPath(), new AllFileNameFileter(), true)) {
      new File(file).delete();
    }

    // copy模板到根路径
    String src = project.getModelPath();
    String des = project.getProjectPath();
    List<String> files = FileUtil.getFilePaths(src, new AllFileNameFileter(), true);
    for (String path : files) {
      File file = new File(path);
      String destPath = path.replace(src, des)
              .replace("projectName", project.getProjectName())
              .replace("packageDir", project.getPackageDir());
      if (file.isDirectory()) {
        new File(destPath).mkdirs();
      } else {
        new File(new File(destPath).getParent()).mkdirs();
        Util.writeFileContent(destPath, Util.getFileContent(path, "utf-8"));
      }
    }

    // 解析模型文件
    List<ModelFile> modelFiles = parseModel(project);
    for (ModelFile modelFile : modelFiles) {
      // 首先删除原始模型文件
      modelFile.getFile().delete();
      if (modelFile.getModelType().isConfig()) {
        String newContent = modelFile.getFileContent()
                .replaceAll("#\\{projectName\\}", project.getProjectName())
                .replaceAll("#\\{packageName\\}", project.getPackageName())
                .replaceAll("#\\{projectJavaName\\}", project.getJavaName())
                .replaceAll("#\\{projectUpperCaseName\\}", project.getUpperCaseName());
        // 写出配置文件
        String newFilePath;
        if (modelFile.getModelType().isNotNeedJavaProjectName()) {
          newFilePath = modelFile.getFileParentPath() + modelFile.getFileName();
        } else {
          newFilePath = modelFile.getFileParentPath() + project.getJavaName() + modelFile.getFileName();
        }
        Util.writeFileContent(
                newFilePath.replace("projectJavaName", project.getJavaName()),
                newContent);
      } else {
        handEntityModel(modelFile, project);
      }
    }
  }

  private void handEntityModel(ModelFile modelFile, Project project) {
    for (Table table : project.getTables()) {
      // 生成新的文件名称
      String fileName = modelFile.getFileParentPath()
              + modelFile.getFileName().replace("javaClassName", table.getJavaName());
      // 生成内容
      String newContent = modelFile.getFileContent().replaceAll("#\\{javaClassName\\}", table.getJavaName())
              .replaceAll("#\\{javaFields\\}", table.getJavaFields())
              .replaceAll("#\\{tableName\\}", table.getTableName())
              .replaceAll("#\\{packageName\\}", project.getPackageName())
              .replaceAll("#\\{insertList\\}", table.getInsertList())
              .replaceAll("#\\{updateSet\\}", table.getUpdateSet())
              .replaceAll("#\\{saveOnDuplicateSet\\}", table.getSaveOnDuplicateSet())
              .replaceAll("#\\{queryList\\}", table.getQueryList()).replaceAll("#\\{where\\}", table.getWhere());

      // 写出文件
      Util.writeFileContent(fileName, newContent);
    }
  }

  private List<ModelFile> parseModel(Project project) {

    // 获取模型文件
    ModelFilter modelFilter = new ModelFilter();
    FileUtil.getFilePaths(project.getProjectPath(), modelFilter, false);
    List<String> models = modelFilter.getIngoreFilePaths();
    List<ModelFile> modelFiles = new ArrayList<>();
    project.setModelFiles(modelFiles);
    for (String model : models) {
      ModelFile modelFile = new ModelFile();
      modelFile.setFile(new File(model));
      modelFiles.add(modelFile);
    }

    return modelFiles;
  }

  private static final class ModelFilter extends AbstractFileNameFilter {
    List<String> notFilters = new ArrayList<>();

    @Override
    public boolean filter(String fileName) {
      if (fileName.endsWith(".java") || fileName.endsWith("_mapper.xml") || fileName.endsWith("settings.gradle")
              || fileName.endsWith("build.gradle") || fileName.endsWith(".yml") || fileName.endsWith("logback.xml")) {
        notFilters.add(fileName);
        return true;
      }
      return true;
    }

    @Override
    public List<String> getIngoreFilePaths() {
      return notFilters;
    }
  }
}
