package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Model.AchievementModel
import com.patronusstudio.BottleFlip.Service.AchievementService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("achievement")
class AchievementController {

    @Autowired
    private lateinit var achievementService: AchievementService

    @PostMapping("/insertAchievement")
    fun insertAchievement(@RequestBody model: AchievementModel):BaseResponse{
        return achievementService.insertAchievement(model)
    }

    @GetMapping("/getAllAchievements")
    fun getAllAchievements():BaseResponse{
        return achievementService.getAllAchievements()
    }

    @GetMapping("/getCustomerRewards")
    fun getCustomerRewards(@RequestParam username:String){
        achievementService
    }

    @GetMapping("/getUserAwards")
    fun getUserAwards(@RequestParam username:String):BaseResponse{
        return achievementService.getUserAwards(username)
    }

    @GetMapping("/winAward")
    fun winAward(@RequestParam username: String,@RequestParam winningAchievementId: Int):BaseResponse{
        return achievementService.winAward(username,winningAchievementId)
    }
}