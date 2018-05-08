package com.dber.#{packageName}.mapper;

import org.springframework.stereotype.Repository;

import com.dber.platform.mapper.IMapper;
import com.dber.#{packageName}.api.entity.#{javaClassName};

/**
 * <li>文件名称: I#{javaClassName}Mapper.java</li>
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 * 
 * @version 1.0
 * @since 2017年12月20日
 * @author dev-v
 */
@Repository
public interface #{javaClassName}Mapper extends IMapper<#{javaClassName}> {
}
