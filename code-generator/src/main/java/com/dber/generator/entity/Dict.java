package com.dber.generator.entity;

import lombok.Data;

/**
 * <li>文件名称: Dict.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月20日
 * @author dev-v
 */
@Data
public class Dict {
	
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 
	 */
	private Integer categoryId;

	/**
	 * 代码中的枚举名称
	 */
	private String enumName;

	/**
	 * 
	 */
	private Integer value;

	/**
	 * 
	 */
	private String label;

	/**
	 * 字典项描述
	 */
	private String content;

	/**
	 * 
	 */
	private java.sql.Date createTime;

	/**
	 * 
	 */
	private java.sql.Timestamp modifyTime;


}
