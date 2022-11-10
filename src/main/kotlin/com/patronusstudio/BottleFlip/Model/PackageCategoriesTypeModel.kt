package com.patronusstudio.BottleFlip.Model

import com.patronusstudio.BottleFlip.Base.BaseModel

data class PackageCategoriesTypeModel(
    val id:Int = 0,
    val name:String,
    val activeBtnColor:String,
    val passiveBtnColor:String,
    val activeTextColor:String,
    val passiveTextColor:String,
    val isSelected:String
): BaseModel()
