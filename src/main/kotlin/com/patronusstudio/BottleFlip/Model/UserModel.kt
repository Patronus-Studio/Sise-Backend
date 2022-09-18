package com.patronusstudio.BottleFlip.Model

import com.google.gson.annotations.SerializedName
import com.patronusstudio.BottleFlip.Base.BaseModel

data class UserModel(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("password") val password: String,
    @SerializedName("userType") val userType: String = "0",
    @SerializedName("createdTime") val createdTime: Long? = null,
    @SerializedName("token") val token: String,
):BaseModel()
