package com.patronusstudio.BottleFlip.Temp.controllers

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Temp.models.PriceModelRequest
import com.patronusstudio.BottleFlip.Temp.models.PriceUpdateRequest
import com.patronusstudio.BottleFlip.Temp.services.PriceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping("/price")
@RestController
class PriceController {

    @Autowired
    private lateinit var priceService: PriceService

    @PostMapping("/getPrice")
    fun getPrice(@RequestBody model: PriceModelRequest): BaseResponse {
        return priceService.getPrice(model)
    }

    @PostMapping("/updateAllPrice")
    fun updateAllPrice(@RequestParam increaseParam:Int): BaseResponse{
        return priceService.updatePrice(increaseParam)
    }

    @PostMapping("/updateSinglePrice")
    fun updateAllPrice(@RequestBody model: PriceUpdateRequest): BaseResponse{
        return priceService.updateSinglePrice(model)
    }

}