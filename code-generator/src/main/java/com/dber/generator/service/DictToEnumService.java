package com.dber.generator.service;

import com.dber.tool.util.Util;
import com.dber.generator.entity.Dict;
import com.dber.generator.entity.DictCategory;
import com.dber.generator.file.FileUtil;
import com.dber.generator.mapper.IDictCategoryMapper;
import com.dber.generator.mapper.IDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2018/1/11
 */
@Service
public class DictToEnumService {
    @Autowired
    private IDictCategoryMapper categoryMapper;

    @Autowired
    private IDictMapper dictMapper;

    public void generate(int categoryId) {

    }

    public void generate(String resourcesPath) {
        Collection<DictCategory> categories = categoryMapper.query(null);
        System.out.println(categories);
        for (DictCategory category : categories) {
            generate(category, resourcesPath);
        }
    }

    private void generate(DictCategory category, String resourcesPath) {
        Dict queryDict = new Dict();
        queryDict.setCategoryId(category.getId());
        Collection<Dict> dicts = dictMapper.query(queryDict);

        String categoryName = category.getCategory();
        String comment = category.getContent();
        if (comment == null) {
            comment = "";
        }
        String code = category.getCode();
        String className = Character.toUpperCase(code.charAt(0)) + code.substring(1);
        StringBuilder enums = new StringBuilder();
        for (Dict dict : dicts) {
            enums.append("\r\n    ").append(dict.getEnumName()).append('(').append(dict.getValue())
                    .append(",\"").append(dict.getLabel()).append("\"),");
        }
        if (enums.length() > 0) {
            enums.setLength(enums.length() - 1);
        }
        String model = Util.getFileContent(resourcesPath + "DictEnumModel", "utf-8");
        String content = model.replaceAll("#\\{categoryName\\}", categoryName)
                .replaceAll("#\\{comment\\}", comment)
                .replaceAll("#\\{className\\}", className)
                .replaceAll("#\\{enums\\}", enums.toString());
        Util.writeFileContent(resourcesPath + "enums/" + className + ".java", content);
    }

}
