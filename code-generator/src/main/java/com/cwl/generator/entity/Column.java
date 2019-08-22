package com.cwl.generator.entity;

import com.cwl.generator.enums.DataType;
import com.cwl.tool.util.Util;
import lombok.Data;

/**
 * <li>文件名称: Column.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月23日
 * @author dev-v
 */
@Data
public class Column {
	String columnName;
	DataType dataType;

	Long characterMaximumLength;

	/**
	 * MUL PRI UNI
	 */
	String columnKey;
	String columnComment;

	/**
	 * auto_increment on update CURRENT_TIMESTAMP
	 * 
	 */
	String extra;

	public String getJavaName() {
		return Util.toJavaStyle(columnName);
	}

	public boolean isPrimaryKey() {
		return Util.contains(columnKey, "PRI");
	}

	public boolean isIndex() {
		return Util.contains(columnKey, "MUL") || Util.contains(columnKey, "UNI");
	}

	public void setDataType(String dataType) {
		this.dataType = DataType.valueOf(dataType.toUpperCase());
	}

	public boolean isAutoGenerator() {
		return Util.contains(columnName, "create_time")
				|| Util.contains(columnName, "modify_time") || (Util.contains(extra, "on")
						&& Util.contains(extra, "update") && Util.contains(extra, "current_timestamp"))
				|| Util.contains(extra, "auto_increment");
	}

}
