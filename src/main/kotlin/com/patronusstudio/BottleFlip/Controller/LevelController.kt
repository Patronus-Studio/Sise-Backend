package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Service.LevelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("level")
class LevelController {

    @Autowired
    private lateinit var levelService: LevelService

    @GetMapping("/getAllLevel")
    fun getAllLevel(): BaseResponse {
        return levelService.getAllLevel()
    }
}