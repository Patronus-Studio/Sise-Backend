package com.patronusstudio.BottleFlip.Model

import com.google.gson.annotations.SerializedName
import com.patronusstudio.BottleFlip.Base.BaseModel
import com.patronusstudio.BottleFlip.enums.AchievementEnum

data class AchievementModel(
    @SerializedName("id")
    val id:Int,
    @SerializedName("title")
    val title:String,
    @SerializedName("content")
    val content:String,
    @SerializedName("imageUrl")
    val imageUrl:String,
    @SerializedName("award")
    val award:Int,
    @SerializedName("winnerCount")
    val winnerCount:Int = 0,
    var userAchievementEnum: AchievementEnum = AchievementEnum.NOT_REACHED
): BaseModel()
