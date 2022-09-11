package com.patronusstudio.BottleFlip.Model

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseModel
import com.patronusstudio.BottleFlip.enums.AchievementEnum

data class AchievementModel(
    @JsonProperty("id")
    val id:Int,
    @JsonProperty("title")
    val title:String,
    @JsonProperty("content")
    val content:String,
    @JsonProperty("imageUrl")
    val imageUrl:String,
    @JsonProperty("award")
    val award:Int,
    @JsonProperty("winnerCount")
    val winnerCount:Int = 0,
    var userAchievementEnum: AchievementEnum = AchievementEnum.NOT_REACHED
): BaseModel()
