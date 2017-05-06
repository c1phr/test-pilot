package com.batchofcode.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@Bean
fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer {
    val p = PropertySourcesPlaceholderConfigurer()
    p.setIgnoreResourceNotFound(true)

    return p
}