package com.batchofcode

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class TestPilotApplication

fun main(args: Array<String>) {
    SpringApplication.run(TestPilotApplication::class.java, *args)
}
