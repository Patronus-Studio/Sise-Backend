package com.patronusstudio.BottleFlip.Model

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseModel
import java.time.LocalDateTime

data class BottleModel(
    @JsonProperty("id") val id: Int? = null,
    @JsonProperty("username") val username: String? = null,
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("description") val description: String? = null,
    @JsonProperty("imageUrl") val imageUrl: String? = null,
    @JsonProperty("luckRatio") val luckRatio: Int? = null,
    @JsonProperty("bottleType") val bottleType: Int? = null,
    @JsonProperty("numberOfLike") val numberOfLike: Int? = 0,
    @JsonProperty("numberOfUnlike") val numberOfUnlike: Int? = 0,
    @JsonProperty("numberOfDownload") val numberOfDownload: Int? = 0,
    @JsonProperty("createdTime") val createdTime: LocalDateTime? = null,
    @JsonProperty("updatedTime") val updatedTime: LocalDateTime? = null,
    @JsonProperty("version") val version: Int? = 1,
) : BaseModel()
