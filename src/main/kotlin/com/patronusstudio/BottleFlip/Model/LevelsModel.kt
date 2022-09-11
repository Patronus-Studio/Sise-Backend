package com.patronusstudio.BottleFlip.Model

import com.google.gson.annotations.SerializedName
import com.patronusstudio.BottleFlip.Base.BaseModel

data class LevelsModel(
    @SerializedName("id")
    val id:Int,
    @SerializedName("level")
    val level:Int,
    @SerializedName("star")
    val star:Int,
    @SerializedName("winnerCount")
    val winnerCount:Int
): BaseModel()
