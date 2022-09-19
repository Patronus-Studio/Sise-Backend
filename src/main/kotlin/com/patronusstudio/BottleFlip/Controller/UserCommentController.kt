package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Model.UserCommentModel
import com.patronusstudio.BottleFlip.Service.UserCommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("userComment")
class UserCommentController {

    @Autowired
    private lateinit var userCommentService: UserCommentService

    @PostMapping("/setUserComment")
    fun setNewUserComment(@RequestBody model:UserCommentModel):BaseResponse{
        return userCommentService.setNewUserReview(model)
    }
}