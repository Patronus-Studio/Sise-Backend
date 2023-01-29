package com.patronusstudio.BottleFlip.Temp.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseModel

data class AirportModel(
    @JsonProperty("airport_id")
    val airport_id :Int,
    @JsonProperty("airport_name")
    val airport_name: String? = null,
    @JsonProperty("lat")
    val lat:Double,
    @JsonProperty("lng")
    val lng:Double
): BaseModel()