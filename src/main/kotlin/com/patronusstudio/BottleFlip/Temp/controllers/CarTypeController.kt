package com.patronusstudio.BottleFlip.Temp.controllers

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Temp.services.CarTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/carType")
@RestController
class CarTypeController {

    @Autowired
    private lateinit var carTypeService: CarTypeService

    @GetMapping("/getCarType")
    fun getPrice(): BaseResponse {
        return carTypeService.getCarTypes()
    }


}