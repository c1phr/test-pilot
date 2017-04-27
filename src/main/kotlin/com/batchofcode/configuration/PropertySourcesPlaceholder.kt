package com.batchofcode.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer



/**
 * Created by n0288764 on 4/26/17.
 */
@Bean
fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer {
    val p = PropertySourcesPlaceholderConfigurer()
    p.setIgnoreResourceNotFound(true)

    return p
}