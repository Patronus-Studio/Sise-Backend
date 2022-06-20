package com.patronusstudio.BottleFlip.Model

import com.fasterxml.jackson.annotation.JsonProperty
import java.sql.Date

data class UserModel(
    @JsonProperty("id") val id: Int,
    @JsonProperty("email") val email: String,
    @JsonProperty("gender") val gender: String,
    @JsonProperty("password") val password: String,
    @JsonProperty("username") val username: String,
    @JsonProperty("userType") val userType: Int,
    @JsonProperty("createdTime") val createdTime: Date,
    @JsonProperty("token") val token: String,
) : BaseModel()
