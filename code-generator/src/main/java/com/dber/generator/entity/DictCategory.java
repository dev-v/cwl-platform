package com.dber.generator.entity;

import lombok.Data;

/**
 * <li>文件名称: DictCategory.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月20日
 * @author dev-v
 */
@Data
public class DictCategory {
	
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 
	 */
	private String code;

	/**
	 * 字典类型名称
	 */
	private String category;

	/**
	 * 系统名称
	 */
	private String sys;

	/**
	 * 描述
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
