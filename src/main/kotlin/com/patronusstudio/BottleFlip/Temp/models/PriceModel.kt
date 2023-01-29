package com.patronusstudio.BottleFlip.Temp.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseModel

data class PriceModelRequest(
    @JsonProperty("airport_id")
    val airport_id :Int,
    @JsonProperty("ilce_id")
    val ilce_id :Int,
)

data class PriceModelResponse(
    @JsonProperty("price")
    val price:Double
): BaseModel()