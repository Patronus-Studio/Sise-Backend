package com.patronusstudio.BottleFlip

sealed class BaseSealed {
    class Succes(val data: Map<String, Any>) : BaseSealed()
    class Error(val data: Map<String, Any>) : BaseSealed()
}