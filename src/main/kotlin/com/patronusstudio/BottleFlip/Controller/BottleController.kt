package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Model.BottleModel
import com.patronusstudio.BottleFlip.Service.BottleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/bottle")
@RestController
class BottleController {

    @Autowired
    private lateinit var bottleService: BottleService

    @PostMapping("/insertBottle")
    fun insertBottle(@RequestBody model: BottleModel): BaseResponse {
        return bottleService.insertBottle(model)
    }
}