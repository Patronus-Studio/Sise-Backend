package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Model.BottleModel
import com.patronusstudio.BottleFlip.Service.BottleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping("/bottle")
@RestController
class BottleController {

    @Autowired
    private lateinit var bottleService: BottleService

    @PostMapping("/insertBottle")
    fun insertBottle(@RequestBody model: BottleModel): BaseResponse {
        return bottleService.insertBottle(model)
    }

    @PostMapping("/getBottleByUsername")
    fun getBottleByUsername(@RequestParam username:String):BaseResponse{
        return bottleService.getBottleByUsername(username)
    }

    @PostMapping("/getBottleByBottleName")
    fun getBottleByBottleName(@RequestParam bottleName:String):BaseResponse{
        return bottleService.getBottleByBottleName(bottleName)
    }

    @PostMapping("/getPackageByMostLike")
    fun getBottleByMostLike():BaseResponse{
        return bottleService.getBottleFromMostLike()
    }

    @PostMapping("/getPackageByMostDownload")
    fun getBottleByMostDownload():BaseResponse{
        return bottleService.getBottleFromMostDownload()
    }
}