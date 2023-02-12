package com.patronusstudio.BottleFlip.Model

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseModel

open class PackageModel(
    @JsonProperty("id") var id: Int? = null,
    @JsonProperty("username") var username: String? = null,
    @JsonProperty("name") var name: String? = null,
    @JsonProperty("description") var description: String? = null,
    @JsonProperty("imageUrl") var imageUrl: String? = null,
    @JsonProperty("createdTime") var createdTime: Long? = null,
    @JsonProperty("numberOfLike") var numberOfLike: Int? = 0,
    @JsonProperty("numberOfUnlike") var numberOfUnlike: Int? = 0,
    @JsonProperty("numberOfDownload") var numberOfDownload: Int? = 0,
    @JsonProperty("questions") var questions: String? = null,
    @JsonProperty("correct_answers")
    var correct_answers: String? = null,
    @JsonProperty("version") var version: Int? = 1,
    @JsonProperty("updatedTime") var updatedTime: Long? = null,
    @JsonProperty("packageCategory") var packageCategory: Int? = null,
):BaseModel()

data class PackageResponseModel(
    var questionList:List<QuestionModel>? = null
):PackageModel()