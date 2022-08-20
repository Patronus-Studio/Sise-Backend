package com.patronusstudio.BottleFlip.Model

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseModel

data class UserGameInfo(
    @JsonProperty("id") val id: Int? = null,
    @JsonProperty("username") val username: String,
    @JsonProperty("bottleFlipCount") val bottleFlipCount: Int? = 0,
    @JsonProperty("level") val level: Int? = 0,
    @JsonProperty("starCount") val starCount: Int? = 0,
    @JsonProperty("myPackages") val myPackages: String? = null,
    @JsonProperty("myBottles") val myBottles: String? = null,
    @JsonProperty("imageUrl") val imageUrl: String? = null,
) : BaseModel()