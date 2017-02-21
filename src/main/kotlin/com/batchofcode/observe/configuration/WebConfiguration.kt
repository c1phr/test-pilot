package com.batchofcode.observe.configuration

import org.h2.server.web.WebServlet
import org.springframework.context.annotation.Configuration
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean


/**
 * Created by ryanbatchelder on 2/20/17.
 */
@Configuration
class WebConfiguration {
    @Bean
    fun h2servletRegistration(): ServletRegistrationBean<*> {
        val registrationBean = ServletRegistrationBean(WebServlet())
        registrationBean.addUrlMappings("/console/*")
        return registrationBean
    }
}