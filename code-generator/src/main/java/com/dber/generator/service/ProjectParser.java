package com.dber.generator.service;

import com.dber.generator.entity.Column;
import com.dber.generator.entity.Project;
import com.dber.generator.entity.Table;
import com.dber.generator.mapper.TableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <li>文件名称: ParseProject.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月24日
 * @author dev-v
 */
@Service
public class ProjectParser {

	@Autowired
	TableMapper mapper;

	public void parse(Project project) {
		List<Table> tables = mapper.getTables(project.getDbSchemaName());
		project.setTables(tables);

		for (Table table : tables) {
			List<Column> columns = mapper.getColumns(project.getDbSchemaName(), table.getTableName());
			table.setColumns(columns);
		}
	}

	public void parseProject(Project project) {
		this.parse(project);

	}

}
