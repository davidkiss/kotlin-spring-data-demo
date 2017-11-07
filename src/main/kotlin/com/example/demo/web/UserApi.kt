package com.example.demo.web

import com.example.demo.entity.User
import com.example.demo.repo.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserApi (val userRepository: UserRepository) {
    @GetMapping("/all")
    fun getAll() : List<User> = userRepository.findAll()
}

