package com.atguigu.lease.web.admin.custom.converter;

import com.atguigu.lease.model.enums.ItemType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.lang.Integer.parseInt;

@Component
public class StringToItemTypeConverter  implements Converter<String, ItemType> {


    @Override
    public ItemType convert(String source) {
        System.out.println("转换器执行了...");
        for (ItemType value : ItemType.values()) {
            System.out.println("枚举值：");
            if (value.getCode().equals(Integer.valueOf(source))){
                return value;
            }

        }
        throw new IllegalArgumentException("参数异常");
    }
}
