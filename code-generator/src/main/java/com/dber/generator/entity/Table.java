package com.dber.generator.entity;

import com.dber.tool.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * <li>文件名称: Table.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月23日
 */
public class Table {
    String tableName;

    String tableComment;

    Column primaryColumn;

    List<Column> indexColumns = new ArrayList<>();

    List<Column> otherColumns = new ArrayList<>();

    List<Column> columns;

    StringBuilder javaFields = new StringBuilder("\r\n");

    StringBuilder insertList;

    StringBuilder updateSet;

    StringBuilder saveOnDuplicateSet;

    StringBuilder queryList = new StringBuilder();

    StringBuilder where = new StringBuilder();

    public void setColumns(List<Column> columns) {
        this.columns = columns;
        for (Column column : columns) {
            if (column.isPrimaryKey()) {
                this.setPrimaryColumn(column);
            } else if (column.isIndex()) {
                this.indexColumns.add(column);
            } else {
                this.otherColumns.add(column);
            }

            if (column.getCharacterMaximumLength() == null || column.getCharacterMaximumLength() < 101) {
                queryList.append(column.getColumnName()).append(',');
            }

            setJavaFields(column);
        }
        queryList.setLength(queryList.length() - 1);
        setInsertList();
        setUpdateSet();
        setWhere();
    }

    private void setWhere() {
        try {
            for (Column column : getAllColumn()) {
                where.append("			<if test=\"").append(column.getJavaName())
                        .append(" != null\">\r\n				AND ").append(column.getColumnName()).append("=#{")
                        .append(column.getJavaName()).append("}\r\n			</if>\r\n");
            }
        } catch (Exception e) {
            System.out.println(this.getTableName());
        }
    }

    private List<Column> getAllColumn() {
        List<Column> columns = new ArrayList<>();
        columns.add(primaryColumn);
        columns.addAll(indexColumns);
        columns.addAll(otherColumns);
        return columns;
    }

    /**
     * <pre>
     * </pre>
     */
    private void setUpdateSet() {
        updateSet = new StringBuilder();
        saveOnDuplicateSet = new StringBuilder();
        for (Column column : getNoPrimaryColumn()) {
            if (column.isAutoGenerator()) {
                continue;
            }
            updateSet.append("			<if test=\"").append(column.getJavaName())
                    .append(" != null \">\r\n				").append(column.getColumnName()).append("=#{")
                    .append(column.getJavaName()).append("},\r\n			</if>\r\n");

            saveOnDuplicateSet.append("			<if test=\"").append(column.getJavaName())
                    .append(" != null \">\r\n				").append(column.getColumnName()).append("=values(")
                    .append(column.getColumnName()).append("),\r\n			</if>\r\n");
        }
    }

    public String getSaveOnDuplicateSet() {
        return saveOnDuplicateSet.toString();
    }

    /**
     * <pre>
     * <!-- 不包含主键和数据库类型为TIMESTAMP（由数据库自动管理）的列 需要使用if判断null -->
     * </pre>
     */
    private void setInsertList() {
        StringBuilder part1 = new StringBuilder("<trim suffixOverrides=\",\">\r\n");
        StringBuilder part2 = new StringBuilder("<trim suffixOverrides=\",\">\r\n");
        for (Column column : getAllColumn()) {
            if ((!column.isAutoGenerator()) || column.isPrimaryKey()) {
                part1.append("			<if test=\"").append(column.getJavaName()).append("!=null\">\r\n				")
                        .append(column.getColumnName())
                        .append(",\r\n			</if>\r\n");

                part2.append("			<if test=\"").append(column.getJavaName()).append("!=null\">\r\n				#{")
                        .append(column.getJavaName()).append("},\r\n			</if>\r\n");
            }
        }
        part1.append("		</trim>\r\n		) values (\r\n		");
        part2.append("		</trim>\r\n");

        insertList = new StringBuilder(part1).append(part2);
    }

    private void setJavaFields(Column column) {
        javaFields.append("	/**\r\n" + "	 * ").append(column.getColumnComment()).append("\r\n	 */\r\n")
                .append("	private ").append(column.getDataType().getJavaType()).append(" ")
                .append(column.getJavaName()).append(";\r\n\r\n");
    }

    public String getWhere() {
        return where.toString();
    }

    public String getQueryList() {
        return queryList.toString();
    }

    public String getJavaFields() {
        return javaFields.toString();
    }

    public String getUpdateSet() {
        return updateSet.toString();
    }

    public List<Column> getNoPrimaryColumn() {
        List<Column> columns = new ArrayList<>(indexColumns);
        columns.addAll(otherColumns);
        return columns;
    }

    public String getInsertList() {
        return insertList.toString();
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String getJavaName() {
        String string = Util.toJavaStyle(tableName);
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public Column getPrimaryColumn() {
        return primaryColumn;
    }

    public void setPrimaryColumn(Column primaryColumn) {
        this.primaryColumn = primaryColumn;
    }

}
