package com.patronusstudio.BottleFlip.Temp.models

import com.patronusstudio.BottleFlip.Base.BaseModel

data class DistrictModel(
    val ilce_id :Int,
    val ilce_adi:String,
    val lat:Double,
    val lng:Double
): BaseModel()