package com.example.demo.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class User (@Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
            val name: String = "",
            val salary: Int = 2000)