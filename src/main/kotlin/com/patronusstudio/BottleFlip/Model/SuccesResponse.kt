package com.patronusstudio.BottleFlip.Model

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseResponse
import org.springframework.http.HttpStatus

data class SuccesResponse(
    @JsonProperty("token") var message: String? = null,
    override val status: HttpStatus,
    var data: Any? = null
) : BaseResponse()
