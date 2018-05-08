package com.dber.generator.file;

import java.util.ArrayList;
import java.util.List;

/**
 * <li>文件名称: FileNameFilter.java</li>
 * <li>版权所有: 陈文龙(C)2015-2020</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 0.0.1
 * @since 2015年1月23日
 * @author cwl
 */
public abstract class AbstractFileNameFilter {
	/**
	 * <pre>
	 * 文件名称满足需要返回 true
	 * </pre>
	 * 
	 * @param fileName
	 * @return
	 */
	public abstract boolean filter(String fileName);

	/**
	 * <pre>
	 * 获取没有满足条件的文件路径
	 * </pre>
	 * 
	 * @return
	 */
	public List<String> getIngoreFilePaths() {
		return new ArrayList<>();
	}
}
