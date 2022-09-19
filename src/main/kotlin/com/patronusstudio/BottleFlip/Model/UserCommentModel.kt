package com.patronusstudio.BottleFlip.Model

import com.patronusstudio.BottleFlip.Base.BaseModel

data class UserCommentModel(
    val id: Int? = null,
    val username: String,
    val comment: String,
    val starCount: Int,
    val sendDate: Long,
    val appVersion: String,
    val deviceType: String,
    val deviceModel: String
) : BaseModel()