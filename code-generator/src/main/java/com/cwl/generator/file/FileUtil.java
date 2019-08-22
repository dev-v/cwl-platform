package com.cwl.generator.file;

import com.cwl.tool.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * <li>文件名称: FileUtil.java</li>
 * <li>版权所有: 陈文龙(C)2015-2020</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 0.0.1
 * @since 2015年1月23日
 * @author cwl
 */
public class FileUtil {
	public static final void fileReplace(String rootPath, AbstractFileNameFilter fileNameFilter, IReplace replace) {
		List<String> filePaths = getFilePaths(rootPath, fileNameFilter, false);
		for (String filePath : filePaths) {
			fileReplace(filePath, replace);
		}
	}

	public static final void copy(String src, String des, boolean includeDir) {
		List<String> files = getFilePaths(src, new AllFileNameFileter(), includeDir);
		for (String path : files) {
			File file = new File(path);
			String destPath = path.replace(src, des);
			if (file.isDirectory()) {
				new File(destPath).mkdirs();
			} else {
				Util.writeFileContent(destPath, Util.getFileContent(path, "utf-8"));
			}
		}
	}

	/**
	 * <pre>
	 * 读取目录下所有满足过滤条件的文件目录列表
	 * </pre>
	 * 
	 * @param rootPath
	 * @param fileNameFilter
	 * @return
	 */
	public static final List<String> getFilePaths(String rootPath, AbstractFileNameFilter fileNameFilter,
			boolean includeDir) {
		List<String> filePaths = new ArrayList<String>();
		read(new File(rootPath), fileNameFilter, filePaths, includeDir);
		return filePaths;
	}

	/**
	 * <pre>
	 * 将文件列表中的内容合并到目标文件中
	 * </pre>
	 * 
	 * @param srcFilePaths
	 * @param toFilePath
	 * @param encoding
	 *            文件内容编码
	 * @param delimit
	 *            合并文件时，文件分割内容
	 */
	public static final void mergerFileContent(List<String> srcFilePaths, String toFilePath, String encoding,
			IFileDelimit delimit) {
		StringBuilder sb = new StringBuilder(8192);
		for (String srcFilePath : srcFilePaths) {
			sb.append(delimit.delimit(srcFilePath));
			sb.append(Util.getFileContent(srcFilePath, encoding));
		}
		Util.writeFileContent(toFilePath, sb.toString());
	}

	public static final void fileReplace(String filePath, IReplace replace) {
		String str = Util.getFileContent(filePath, replace.getEncoding());
		Matcher matcher = replace.getPattern().matcher(str);
		String s;
		while (matcher.find()) {
			s = matcher.group();
			str = str.replace(s, replace.to(s));
		}
		Util.writeFileContent(filePath, str);
	}

	private static final void read(File file, AbstractFileNameFilter fileNameFilter, List<String> filePaths,
			boolean includeDir) {
		String name = file.getPath();
		if (fileNameFilter.filter(name)) {
			if (file.isDirectory()) {
				if (includeDir) {
					filePaths.add(name);
				}
				for (File p : file.listFiles()) {
					read(p, fileNameFilter, filePaths, includeDir);
				}
			} else {
				filePaths.add(name);
			}
		}
	}

}
