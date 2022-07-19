package com.patronusstudio.BottleFlip.Authentication

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Model.UserModel
import com.patronusstudio.BottleFlip.Repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepo: UserRepo

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    override fun loadUserByUsername(username: String?): UserDetails {
        val sql = "SELECT * From users Where username = \"$username\""
        val res = userRepo.getData<UserModel>(sql,UserModel())
        return when (res) {
            is BaseSealed.SuccesWithData -> User(
                username,
                passwordEncoder.encode((res.data as UserModel).password.toString()),
                arrayListOf()
            )
            is BaseSealed.Error -> throw Exception("No user found")
            else -> throw Exception("No user found")
        }
    }

    fun usernameControl(username: String?): BaseResponse {
        val sql = "SELECT * FROM users WHERE username = \"$username\""
        val res = userRepo.getData(sql,UserModel())
        return when (res) {
            is BaseSealed.SuccesWithData -> ErrorResponse(
                "Bu kullanıcı adı kullanılmaktadır.",
                HttpStatus.NOT_ACCEPTABLE
            )
            is BaseSealed.Error -> SuccesResponse(status = HttpStatus.OK, message = null)
            else -> ErrorResponse("Bu kullanıcı adı kullanılmaktadır.", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun emailControl(email: String?): BaseResponse {
        val sql = "SELECT * FROM users WHERE email = \"$email\""
        val res = userRepo.getData(sql,UserModel())
        return when (res) {
            is BaseSealed.SuccesWithData -> ErrorResponse(
                "Bu email kullanılmaktadır.",
                HttpStatus.NOT_ACCEPTABLE
            )
            is BaseSealed.Error -> SuccesResponse(status = HttpStatus.OK, message = null)
            else -> ErrorResponse("Bu email kullanılmaktadır.", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun register(userModel: UserModel): BaseResponse {
        val usernameResponse = usernameControl(userModel.username)
        val emailResponse = emailControl(userModel.email)
        return if (emailResponse is SuccesResponse && usernameResponse is SuccesResponse) {
            val sql = "INSERT INTO users(email,gender,password,username,userType,token) VALUES(" +
                    "\"${userModel.email}\",\"${userModel.gender}\",\"${userModel.password}\"," +
                    "\"${userModel.username}\",\"${userModel.userType}\",\"${userModel.token}}\")"
            val res = userRepo.setData(sql)
            when (res) {
                is BaseSealed.SuccesWithNoData -> SuccesResponse(
                    status = HttpStatus.OK,
                    message = "Kayıt başarılı."
                )
                is BaseSealed.Error -> ErrorResponse("HATA.", HttpStatus.NOT_ACCEPTABLE)
                else -> ErrorResponse("HATA ELSE.", HttpStatus.NOT_ACCEPTABLE)
            }
        } else if (emailResponse is ErrorResponse && usernameResponse is SuccesResponse) emailResponse
        else if (emailResponse is SuccesResponse && usernameResponse is ErrorResponse) usernameResponse
        else ErrorResponse("Kullanıcı adı ve email adresi kullanılmaktadır.", HttpStatus.NOT_ACCEPTABLE)
    }
    //TODO REFRESH TOKEN EKLENECEK
}