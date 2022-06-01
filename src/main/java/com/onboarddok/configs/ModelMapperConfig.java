/*
 * Copyright (C) 2022 Olasunkanmi Olayinka Code
 * All rights reserved
 */

package com.onboarddok.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    /**
     * Creating a ModelMapper as a Bean.
     *
     * @return mapper, an instance of a new mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setFieldMatchingEnabled(true);

        mapper.getConfiguration().setSkipNullEnabled(true);
        return mapper;
    }
}
