package com.patronusstudio.BottleFlip.Temp.controllers

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Temp.services.DistrictService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/district")
@RestController
class DistrictController {

    @Autowired
    private lateinit var districtService: DistrictService

    @GetMapping("/getAllDistrict")
    fun fetchAllDistrict(): BaseResponse {
        return districtService.getDistricts()
    }

}