package com.example.demo

import com.example.demo.entity.User
import com.example.demo.repo.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DemoApplication : SpringBootServletInitializer() {
    @Bean
    fun init(repository: UserRepository) = CommandLineRunner {
        repository.save(User(name = "Jack", salary = 1000))
        repository.save(User(name = "Chloe", salary = 2000))
        repository.save(User(name = "Kim", salary = 3000))
        repository.save(User(name = "David", salary = 4000))
        repository.save(User(name = "Michelle", salary = 5000))
    }

    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder = application.sources(DemoApplication::class.java)
}

fun main(args: Array<String>) {
    SpringApplication.run(DemoApplication::class.java, *args)
}
