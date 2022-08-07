package com.patronusstudio.BottleFlip.Authentication

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Model.UserModel
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.enums.CreateTableSqlEnum
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
    private lateinit var sqlRepo: SqlRepo

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    override fun loadUserByUsername(username: String?): UserDetails {
        val sql = "SELECT * From users Where username = \"$username\""
        val res = sqlRepo.getDataForObject<UserModel>(sql, UserModel())
        return when (res) {
            is BaseSealed.Succes -> User(
                username,
                passwordEncoder.encode((res.data as UserModel).password.toString()),
                arrayListOf()
            )
            is BaseSealed.Error -> throw Exception("No user found")
        }
    }

    fun usernameControl(username: String?): BaseResponse {
        val sql = "SELECT * FROM users WHERE username = \"$username\""
        val res = sqlRepo.getDataForObject(sql, UserModel())
        return when (res) {
            is BaseSealed.Succes -> ErrorResponse(
                "Bu kullanıcı adı kullanılmaktadır.",
                HttpStatus.NOT_ACCEPTABLE
            )
            is BaseSealed.Error -> SuccesResponse(status = HttpStatus.OK, message = null)
        }
    }

    fun emailControl(email: String?): BaseResponse {
        val sql = "SELECT * FROM users WHERE email = \"$email\""
        val res = sqlRepo.getDataForObject(sql, UserModel())
        return when (res) {
            is BaseSealed.Succes -> ErrorResponse(
                "Bu email kullanılmaktadır.",
                HttpStatus.NOT_ACCEPTABLE
            )
            is BaseSealed.Error -> SuccesResponse(status = HttpStatus.OK, message = null)
        }
    }

    fun register(userModel: UserModel): BaseResponse {
        val est = sqlRepo.setData(CreateTableSqlEnum.USER.getCreateSql())
        return if (est is BaseSealed.Succes) {
            val usernameResponse = usernameControl(userModel.username)
            val emailResponse = emailControl(userModel.email)
            if (emailResponse is SuccesResponse && usernameResponse is SuccesResponse) {
                val insertSql = CreateTableSqlEnum.USER.getInsertSql(
                    userModel.username!!, userModel.email!!, userModel.gender,
                    userModel.password!!, userModel.userType, userModel.token ?: ""
                )
                val res = sqlRepo.setData(insertSql)
                if (res is BaseSealed.Succes) {
                    userGameInfoSetData(userModel.username)
                    SuccesResponse(status = HttpStatus.OK, message = "Kayıt başarılı.")
                } else {
                    ErrorResponse("HATA.", HttpStatus.NOT_ACCEPTABLE)
                }
            } else if (emailResponse is ErrorResponse && usernameResponse is SuccesResponse) emailResponse
            else if (emailResponse is SuccesResponse && usernameResponse is ErrorResponse) usernameResponse
            else ErrorResponse("Kullanıcı adı ve email adresi kullanılmaktadır.", HttpStatus.NOT_ACCEPTABLE)
        } else {
            ErrorResponse(CreateTableSqlEnum.USER.createTableErrorMessage(), HttpStatus.NOT_ACCEPTABLE)
        }
    }

    private fun userGameInfoSetData(username: String): BaseResponse {
        val tableCreateSql = CreateTableSqlEnum.USER_GAME_INFO.getCreateSql()
        val result = sqlRepo.setData(tableCreateSql)
        return if (result is BaseSealed.Succes) {
            val tableInsertSql = CreateTableSqlEnum.USER_GAME_INFO.getInsertSql(username)
            val insertDataResult = sqlRepo.setData(tableInsertSql)
            if (insertDataResult is BaseSealed.Succes)
                return SuccesResponse(status = HttpStatus.OK, message = null)
            else ErrorResponse(
                CreateTableSqlEnum.USER_GAME_INFO.insertDataErrorMessage(), HttpStatus.NOT_ACCEPTABLE
            )
        } else ErrorResponse(
            CreateTableSqlEnum.USER_GAME_INFO.createTableErrorMessage(), HttpStatus.NOT_ACCEPTABLE
        )
    }

}