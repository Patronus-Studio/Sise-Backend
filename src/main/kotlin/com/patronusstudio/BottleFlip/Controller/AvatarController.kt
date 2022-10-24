package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Model.AvatarModel
import com.patronusstudio.BottleFlip.Service.AvatarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("avatar")
class AvatarController {

    @Autowired
    private lateinit var avatarService: AvatarService

    @PostMapping("/getAvatars")
    fun getAllAvatar():BaseResponse{
        return avatarService.getAllAvatar()
    }

    @PostMapping("/insertAvatar")
    fun insertAvatar(@RequestBody model:AvatarModel):BaseResponse{
        return avatarService.insertAvatar(model)
    }

}