package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Service.LevelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("level")
class LevelController {

    @Autowired
    private lateinit var levelService: LevelService

    @GetMapping("/getAllLevel")
    fun getAllLevel(): BaseResponse {
        return levelService.getAllLevel()
    }

    @PostMapping("/addLevel")
    fun addNewLevel(@RequestParam level:Int,@RequestParam star:Int):BaseResponse{
        return levelService.addNewLevel(level,star)
    }
}