package com.patronusstudio.BottleFlip.Model

import com.google.gson.annotations.SerializedName
import com.patronusstudio.BottleFlip.Base.BaseModel
import com.patronusstudio.BottleFlip.enums.GenderEnum

data class UserModel(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String? = null,
    @SerializedName("gender") val gender: GenderEnum? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("userType") val userType: String = "0",
    @SerializedName("createdTime") val createdTime: Long? = null,
    @SerializedName("authToken") val authToken: String? = null,
    @SerializedName("pushToken") val pushToken: String? = null,
):BaseModel()
