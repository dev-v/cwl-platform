package com.dber.generator.entity;

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
    String modelPath = "E:\\dber_work\\dber\\dber-code-generate-model";

    /**
     * 生成的工程存放目录
     */
    String projectDir = "E:\\dber_work\\";

    String dbSchemaName;

    String projectName;

    String packageName;

    List<Table> tables;

    List<ModelFile> modelFiles;

    public Project(String modelPath, String projectDir, String dbSchemaName, String projectName, String packageName) {
        this.modelPath = modelPath;
        this.projectDir = projectDir;
        this.dbSchemaName = dbSchemaName;
        this.projectName = projectName;
        this.packageName = packageName;
    }

    public String getJavaName() {
        return Character.toUpperCase(packageName.charAt(0)) + packageName.substring(1);
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