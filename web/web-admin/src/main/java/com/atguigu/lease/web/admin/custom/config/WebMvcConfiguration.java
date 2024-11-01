package com.atguigu.lease.web.admin.custom.config;

import com.atguigu.lease.web.admin.custom.converter.StirngToBaseEnumConverterFactory;
import com.atguigu.lease.web.admin.custom.converter.StringToItemTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private StringToItemTypeConverter stringToItemTypeConverter;
    @Autowired
    private StirngToBaseEnumConverterFactory stirngToBaseEnumConverterFactory;

    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(stringToItemTypeConverter);
        registry.addConverterFactory(stirngToBaseEnumConverterFactory);
    }
}
