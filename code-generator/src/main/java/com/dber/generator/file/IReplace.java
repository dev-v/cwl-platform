package com.dber.generator.file;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * <li>文件名称: Replace.java</li>
 * <li>版权所有: 陈文龙(C)2015-2020</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * @version  0.0.1
 * @since    2015年1月23日
 * @author   cwl
 */
public interface IReplace {
	/**
	 * <pre>
	 * 要将原始字符串替换成的目标字符串
	 * </pre>
	 * @param src 文件中的原始字符串
	 * @return 目标字符串
	 */
	String to(String src);
	
	/**
	 * <pre>
	 * 获取字符编码
	 * </pre>
	 * @return
	 */
	String getEncoding();

	/**
	 * <pre>
	 * 要替换的内容的模式
	 * </pre>
	 * @return
	 */
	Pattern getPattern();
	
	/**
	 * <pre>
	 * 从外界传递进来的参数
	 * </pre>
	 * @param params
	 */
	void setParams(Map<String,Object> params);
	
	/**
	 * <pre>
	 * 从外界传递进来的参数
	 * </pre>
	 * @param params
	 */
	void setParams(Object params);
}


