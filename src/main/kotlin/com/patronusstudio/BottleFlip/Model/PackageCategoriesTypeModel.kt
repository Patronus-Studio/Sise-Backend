package com.patronusstudio.BottleFlip.Model

import com.google.gson.annotations.SerializedName
import com.patronusstudio.BottleFlip.Base.BaseModel

data class PackageCategoriesTypeModel(
    @SerializedName("id")
    val id:Int,
    @SerializedName("type")
    val type:String
): BaseModel()