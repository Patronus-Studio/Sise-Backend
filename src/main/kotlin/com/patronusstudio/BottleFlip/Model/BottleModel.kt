package com.patronusstudio.BottleFlip.Model

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseModel

data class BottleModel(
    @JsonProperty("id") val id: Int? = null,
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("description") val description: String? = null,
    @JsonProperty("luckRatio") val luckRatio: Int? = null,
    @JsonProperty("bottleType") val bottleType: Int? = null,
    @JsonProperty("imageUrl") val imageUrl: String? = null,
    @JsonProperty("numberOfLike") val numberOfLike: Int? = 0,
    @JsonProperty("numberOfUnlike") val numberOfUnlike: Int? = 0,
    @JsonProperty("numberOfDownload") val numberOfDownload: Int? = 0,
    @JsonProperty("createdTime") val createdTime: Long? = null,
    @JsonProperty("updatedTime") val updatedTime: Long? = null,
    @JsonProperty("version") val version: Int? = 1,
) : BaseModel()
