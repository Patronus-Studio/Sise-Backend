package com.patronusstudio.BottleFlip.Base

sealed class BaseSealed {
    class Succes(var data:Any? = null) : BaseSealed()
    class Error(val data: Map<String, Any>) : BaseSealed()
}