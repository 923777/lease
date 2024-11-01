package com.atguigu.lease.web.admin.custom.converter;

import com.atguigu.lease.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class StirngToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum>{

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {

        return new Converter<String,T>(){

            @Override
            public T convert(String source) {
                for (T enumConstant : targetType.getEnumConstants()) {
                    if(enumConstant.getCode().equals(Integer.parseInt(source))){
                        return enumConstant;
                    }
                }
                throw new IllegalArgumentException("无法转换为枚举类型");
            }
        };
    }
}
