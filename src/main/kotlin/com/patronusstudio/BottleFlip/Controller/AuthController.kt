package com.patronusstudio.BottleFlip

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Authentication.MyUserDetailsService
import com.patronusstudio.BottleFlip.Authentication.TokenManager
import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.LoginRequest
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Model.UserModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenManager: TokenManager

    @Autowired
    private lateinit var userDetailsService: MyUserDetailsService

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): BaseResponse {
        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest.username, loginRequest.password
                )
            )
            val token = tokenManager.generateToken(loginRequest.username)
            SuccesResponse(token, HttpStatus.OK)
        } catch (e: Exception) {
            ErrorResponse(e.localizedMessage, HttpStatus.NOT_ACCEPTABLE)
        }
    }

    @PostMapping("/usernameControl")
    fun usernameControl(@RequestParam username: String): BaseResponse {
        return userDetailsService.usernameControl(username)
    }

    @PostMapping("/register")
    fun register(@RequestBody userModel: UserModel):BaseResponse{
        return userDetailsService.register(userModel)
    }
}
