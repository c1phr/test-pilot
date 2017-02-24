package com.batchofcode.configuration

import org.h2.server.web.WebServlet
import org.springframework.context.annotation.Configuration
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate


/**
 * Created by ryanbatchelder on 2/20/17.
 */
@Configuration
class RestTemplateConfig {
    @Bean
    fun restTemplate() = RestTemplate()
}