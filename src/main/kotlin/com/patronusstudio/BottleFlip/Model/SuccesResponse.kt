package com.patronusstudio.BottleFlip.Model

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseResponse
import org.springframework.http.HttpStatus

data class SuccesResponse(
    @JsonProperty("token") val message: String,
    override val status: HttpStatus,
) : BaseResponse()
