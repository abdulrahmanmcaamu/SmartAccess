package com.example.smartaccess.models

data class UserModel(
    val userType: String,
    val coolingStartTime: String,
    val coolingEndTime: String,
    val accessibleModules: List<String>
)
