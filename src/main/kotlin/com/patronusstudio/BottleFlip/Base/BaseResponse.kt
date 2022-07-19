package com.patronusstudio.BottleFlip.Base

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus

abstract class BaseResponse() {
    @get:JsonProperty("status")
    abstract val status: HttpStatus
}