package com.patronusstudio.BottleFlip.Model

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseModel
import java.sql.Date

data class UserModel(
    @JsonProperty("email") val email: String? = null,
    @JsonProperty("gender") val gender: String = "0",
    @JsonProperty("password") val password: String? = null,
    @JsonProperty("username") val username: String? = null,
    @JsonProperty("userType") val userType: Int = 0,
    @JsonProperty("createdTime") val createdTime: Date? = null,
    @JsonProperty("token") val token: String? = null,
):BaseModel()
