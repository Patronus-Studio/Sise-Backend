package com.patronusstudio.BottleFlip.Temp.controllers

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Temp.services.AirportService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/airport")
@RestController
class AirportController {

    @Autowired
    private lateinit var airportService: AirportService

    @GetMapping("/getAirports")
    fun fetchAllDistrict(): BaseResponse {
        return airportService.getAirports()
    }

}