package com.cwl.generator.entity;

import com.cwl.generator.enums.ModelType;
import com.cwl.tool.util.Util;

import java.io.File;

/**
 * <li>文件名称: ModelFile.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月24日
 * @author dev-v
 */

public class ModelFile {
	String fileParentPath;
	String filePath;
	ModelType modelType;
	String fileContent;
	String fileName;
	File file;

	public void setFile(File file) {
		this.file = file;
		this.fileParentPath = file.getParent()+File.separator;
		this.filePath = file.getPath();
		this.fileName=file.getName();
		String type = fileName.split("\\.")[0].replace("javaClassName", "");
		if (Util.isBlank(type)) {
			this.modelType = ModelType.entity;
		} else {
			try {
				this.modelType = ModelType.valueOf(type);
			} catch (Exception e) {
				System.out.println(file.getPath());
				e.printStackTrace();
			}
		}
		this.fileContent = Util.getFileContent(filePath, "utf-8");
	}

	public String getFileParentPath() {
		return fileParentPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public ModelType getModelType() {
		return modelType;
	}

	public String getFileContent() {
		return fileContent;
	}

	public String getFileName() {
		return fileName;
	}

	public File getFile() {
		return file;
	}
}
