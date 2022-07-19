package com.patronusstudio.BottleFlip.Model

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseResponse
import org.springframework.http.HttpStatus

data class ErrorResponse(
    @JsonProperty("message") val message: String,
    override val status: HttpStatus
) : BaseResponse()