package com.patronusstudio.BottleFlip.Base

sealed class BaseSealed {
    class Succes(val data:Any? = null) : BaseSealed()
    class Error(val data: Map<String, Any>) : BaseSealed()
}