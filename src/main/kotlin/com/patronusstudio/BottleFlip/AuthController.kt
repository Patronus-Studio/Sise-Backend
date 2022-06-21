package com.patronusstudio.BottleFlip

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
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

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest.username, loginRequest.password
                )
            )
            return ResponseEntity.ok(tokenManager.generateToken(loginRequest.username))
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    @PostMapping("/message")
    fun showMessage(@RequestParam message: String): ResponseEntity<String> {
        return ResponseEntity.ok("Hello $message!!\nWelcome to my World!!")
    }
}

data class LoginRequest(
    @JsonProperty("username") val username: String,
    @JsonProperty("password") val password: String
)