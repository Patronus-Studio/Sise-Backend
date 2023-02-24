package com.patronusstudio.BottleFlip.Temp.controllers

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Temp.models.CarTypeRequestModel
import com.patronusstudio.BottleFlip.Temp.services.CarTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping("/carType")
@RestController
class CarTypeController {

    @Autowired
    private lateinit var carTypeService: CarTypeService

    @GetMapping("/getCarType")
    fun getPrice(): BaseResponse {
        return carTypeService.getCarTypes()
    }

    @PostMapping("/addCarType")
    fun addNewCarType(@RequestBody carTypeRequestModel: CarTypeRequestModel): BaseResponse {
        return carTypeService.addNewCarType(carTypeRequestModel)
    }

    @PostMapping("/updateCarType")
    fun updateCarType(@RequestBody carTypeRequestModel: CarTypeRequestModel): BaseResponse {
        return carTypeService.updateCarType(carTypeRequestModel)
    }

}