package com.dber.generator.file;

import java.util.ArrayList;
import java.util.List;

/**
 * <li>文件名称: AllFileNameFileter.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月24日
 * @author dev-v
 */
public class AllFileNameFileter extends AbstractFileNameFilter {

	@Override
	public boolean filter(String fileName) {
		return true;
	}

	@Override
	public List<String> getIngoreFilePaths() {
		return new ArrayList<>();
	}

}
