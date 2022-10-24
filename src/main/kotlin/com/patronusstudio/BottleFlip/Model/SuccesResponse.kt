package com.patronusstudio.BottleFlip.Model

import com.patronusstudio.BottleFlip.Base.BaseResponse
import org.springframework.http.HttpStatus

data class SuccesResponse(
    val message: String? = null,
    override val status: HttpStatus,
    val data: Any? = null
) : BaseResponse()
