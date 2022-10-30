package com.patronusstudio.BottleFlip.Model

import com.patronusstudio.BottleFlip.Base.BaseResponse
import org.springframework.http.HttpStatus

data class SuccesResponse(
    var message: String? = null,
    override val status: HttpStatus,
    var data: Any? = null
) : BaseResponse()
