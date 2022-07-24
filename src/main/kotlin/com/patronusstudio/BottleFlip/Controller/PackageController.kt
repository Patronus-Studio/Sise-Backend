package com.patronusstudio.BottleFlip.Controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PackageController {
    @GetMapping("hello")
    fun hello():String{
        return "Hello World"
    }
}