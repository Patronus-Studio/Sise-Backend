package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Model.AvatarModel
import com.patronusstudio.BottleFlip.Service.AvatarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("avatar")
class AvatarController {

    @Autowired
    private lateinit var avatarService: AvatarService

    @GetMapping("/getAvatars")
    fun getAllAvatar():BaseResponse{
        return avatarService.getAllAvatar()
    }

    @PostMapping("/insertAvatar")
    fun insertAvatar(@RequestBody model:AvatarModel):BaseResponse{
        return avatarService.insertAvatar(model)
    }

    @PostMapping("/buyAvatar")
    fun buyAvatar(@RequestParam username:String,@RequestParam avatarId:Int):BaseResponse{
        return avatarService.buyAvatar(username,avatarId)
    }

}