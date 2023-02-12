package com.patronusstudio.BottleFlip.Model

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseModel

open class PackageModel() : BaseModel() {
    @JsonProperty("id")
    var id: Int? = null
    @JsonProperty("username")
    var username: String? = null
    @JsonProperty("name")
    var name: String? = null
    @JsonProperty("description")
    var description: String? = null
    @JsonProperty("imageUrl")
    var imageUrl: String? = null
    @JsonProperty("createdTime")
    var createdTime: String? = null
    @JsonProperty("numberOfLike")
    var numberOfLike: Int? = 0
    @JsonProperty("numberOfUnlike")
    var numberOfUnlike: Int? = 0
    @JsonProperty("numberOfDownload")
    var numberOfDownload: Int? = 0
    @JsonProperty("questions")
    var questions: String? = null
    @JsonProperty("correct_answers")
    var correct_answers: String? = null
    @JsonProperty("version")
    var version: Int? = 1
    @JsonProperty("updatedTime")
    var updatedTime: String? = null
    @JsonProperty("packageCategory")
    var packageCategory: Int? = null

    constructor(
        id: Int,
        username: String,
        name: String,
        description: String,
        imageUrl: String,
        createdTime: String,
        numberOfLike: Int,
        numberOfUnlike: Int,
        numberOfDownload: Int,
        questions: String,
        correct_answers: String,
        version: Int,
        updatedTime: String,
        packageCategory: Int
    ) : this() {
        this.id = id
        this.username = username
        this.name = name
        this.description = description
        this.imageUrl = imageUrl
        this.createdTime = createdTime
        this.numberOfUnlike = numberOfUnlike
        this.numberOfDownload = numberOfDownload
        this.numberOfLike = numberOfLike
        this.questions = questions
        this.correct_answers = correct_answers
        this.version = version
        this.updatedTime = updatedTime
        this.packageCategory = packageCategory
    }
}


data class PackageResponseModel(
    var questionList: List<QuestionModel>? = null
) : PackageModel()