package com.patronusstudio.BottleFlip.Temp.controllers

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Temp.models.PriceModelRequest
import com.patronusstudio.BottleFlip.Temp.services.PriceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/price")
@RestController
class PriceController {

    @Autowired
    private lateinit var priceService: PriceService

    @PostMapping("/getPrice")
    fun getPrice(@RequestBody model: PriceModelRequest): BaseResponse {
        return priceService.getPrice(model)
    }


}