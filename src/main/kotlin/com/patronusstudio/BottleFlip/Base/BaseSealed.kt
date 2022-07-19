package com.patronusstudio.BottleFlip.Base

sealed class BaseSealed {
    class SuccesWithData(val data:Any) : BaseSealed()
    class SuccesWithNoData() : BaseSealed()
    class Error(val data: Map<String, Any>) : BaseSealed()
}