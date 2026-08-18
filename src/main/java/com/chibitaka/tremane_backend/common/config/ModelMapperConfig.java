package com.chibitaka.tremane_backend.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** ModelMapper設定クラス */
@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
