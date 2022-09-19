package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Model.UserCommentModel
import com.patronusstudio.BottleFlip.Service.UserCommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("userComment")
class UserCommentController {

    @Autowired
    private lateinit var userCommentService: UserCommentService

    @PostMapping("/setUserComment")
    fun setNewUserComment(@RequestBody model:UserCommentModel):BaseResponse{
        return userCommentService.setNewUserReview(model)
    }
    @PostMapping("/getUserCommentsByLast")
    fun getUserCommentsByLast(@RequestParam lastCommentSize:Int):BaseResponse{
        return userCommentService.getUserCommentByLast(lastCommentSize)
    }
    @PostMapping("/getUserCommentsByUsername")
    fun getUserCommentsByUsername(@RequestParam username:String):BaseResponse{
        return userCommentService.getUserCommentByUsername(username)
    }
    @PostMapping("/getUserCommentsByStarCount")
    fun getUserCommentsByStarCount(@RequestParam starCount:Int):BaseResponse{
        return userCommentService.getUserCommentByStarCount(starCount)
    }
    @PostMapping("/getUserCommentsByDeviceType")
    fun getUserCommentsByDeviceType(@RequestParam deviceType:String):BaseResponse{
        return userCommentService.getUserCommentsByDeviceType(deviceType)
    }
    @PostMapping("/getUserCommentsByAppVersion")
    fun getUserCommentsByAppVersion(@RequestParam appVersion:String):BaseResponse{
        return userCommentService.getUserCommentsByAppVersion(appVersion)
    }
}