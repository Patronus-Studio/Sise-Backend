package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Authentication.TokenManager
import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Service.UserGameInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/userGameInfo")
@RestController
class UserGameInfoController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenManager: TokenManager

    @Autowired
    private lateinit var userGameInfoService: UserGameInfoService

    @PostMapping("/getUserGameInfo")
    fun getUserInfoService(@RequestParam username: String): BaseResponse {
        return userGameInfoService.getUserGameInfo(username)
    }

    @PostMapping("/updateCurrentAvatar")
    fun updateCurrentAvatar(@RequestParam username: String,@RequestParam currentAvatar:Int):BaseResponse{
        return userGameInfoService.updateCurrentAvatar(username,currentAvatar)
    }

    @PostMapping("/updateBuyedAvatars")
    fun updateBuyedAvatars(@RequestParam username: String,@RequestParam buyedAvatars:Int):BaseResponse{
        return userGameInfoService.updateBuyedAvatars(username,buyedAvatars)
    }

    @PostMapping("/updateMyPackages")
    fun updateMyPackages(@RequestParam username: String,@RequestParam packageId:Int):BaseResponse{
        return userGameInfoService.updateMyPackages(username,packageId)
    }


}