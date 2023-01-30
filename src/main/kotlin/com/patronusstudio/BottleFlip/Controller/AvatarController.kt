package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Service.AvatarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("avatar")
class AvatarController {

    @Autowired
    private lateinit var avatarService: AvatarService

    @GetMapping("/getAvatars")
    fun getAllAvatar(@RequestParam username:String):BaseResponse{
        return avatarService.getAllAvatar(username)
    }

}