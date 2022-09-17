package com.patronusstudio.BottleFlip.Base

import com.patronusstudio.BottleFlip.enums.SqlErrorType

sealed class BaseSealed {
    class Succes(var data:Any? = null) : BaseSealed()
    class Error(val data: Map<String, Any>, val sqlErrorType: SqlErrorType) : BaseSealed()
}