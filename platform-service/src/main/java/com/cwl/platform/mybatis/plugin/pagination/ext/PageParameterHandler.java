package com.cwl.platform.mybatis.plugin.pagination.ext;

import com.alibaba.fastjson.util.TypeUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <li>文件名称: PageParameterHandler.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2017年12月21日
 */
public class PageParameterHandler {

    private static final String page_params_key_cache = "_page_params_key_cache_";

    @SuppressWarnings("unchecked")
    public static final void setParameters(PreparedStatement statement, BoundSql boundSql) {
        List<PageParameter> parameters = (List<PageParameter>) boundSql.getAdditionalParameter(page_params_key_cache);
        if (parameters != null) {
            for (int i = 0; i < parameters.size(); i++) {
                PageParameter parameter = parameters.get(i);
                try {
                    parameter.getTypeHandler().setParameter(statement, i + 1, parameter.getValue(),
                            parameter.getJdbcType());
                } catch (TypeException e) {
                    throw new TypeException("Could not set parameters for mapping: " + parameter + ". Cause: " + e, e);
                } catch (SQLException e) {
                    throw new TypeException("Could not set parameters for mapping: " + parameter + ". Cause: " + e, e);
                }
            }
        }
    }

    /**
     * <pre>
     * </pre>
     *
     * @param ms
     * @param boundSql
     * @return 直接返回key
     */
    public static final String parseKeyAndParameters(MappedStatement ms, BoundSql boundSql) {
        StringBuilder sb = new StringBuilder(ms.getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            TypeHandlerRegistry typeHandlerRegistry = ms.getConfiguration().getTypeHandlerRegistry();
            Configuration configuration = ms.getConfiguration();
            Object parameterObject = boundSql.getParameterObject();
            int size = parameterMappings.size();
            List<PageParameter> parameters = new ArrayList<>(size);
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            Object value;
            ParameterMapping parameterMapping;
            for (int i = 0; i < size; i++) {
                parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    value = null;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        value = metaObject.getValue(propertyName);
                    }
                    TypeHandler<?> typeHandler = parameterMapping.getTypeHandler();
                    JdbcType jdbcType = parameterMapping.getJdbcType();

                    sb.append(propertyName);
                    if (value != null) {
                        sb.append(TypeUtils.castToString(value).hashCode());
                        if (jdbcType == null) {
                            jdbcType = JdbcType.OTHER;
                        }
                    }

                    parameters.add(new PageParameter(propertyName, value, jdbcType, typeHandler));
                }
            }
            boundSql.setAdditionalParameter(page_params_key_cache, parameters);
        }

        return sb.toString();
    }
}
