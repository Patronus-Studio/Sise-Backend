package com.patronusstudio.BottleFlip.Authentication

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Model.UserModel
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.Utils.isEmailValid
import com.patronusstudio.BottleFlip.enums.CreateTableSqlEnum
import com.patronusstudio.BottleFlip.enums.SqlErrorType
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
        val res = sqlRepo.getDataForObject(sql, UserModel::class.java)
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
        val res = sqlRepo.getBasicData(sql)
        return when (res) {
            is BaseSealed.Error -> {
                if (res.sqlErrorType == SqlErrorType.EMPTY_RESULT_DATA) {
                    SuccesResponse(status = HttpStatus.OK, data = "Kullanıcı adı kullanılabilir.")
                } else{
                    ErrorResponse(
                        "Bu kullanıcı adı kullanılmaktadır.",
                        HttpStatus.NOT_ACCEPTABLE
                    )
                }
            }
            is BaseSealed.Succes -> SuccesResponse(status = HttpStatus.OK, message = null)
        }
    }

    fun emailControl(email: String?): BaseResponse {
        if(isEmailValid(email ?: "").not()){
            return ErrorResponse("Email formatı geçersiz.",HttpStatus.NOT_ACCEPTABLE)
        }
        val sql = "SELECT * FROM users WHERE email = \"$email\""
        val res = sqlRepo.getBasicData(sql)
        return when (res) {
            is BaseSealed.Error -> {
                if (res.sqlErrorType == SqlErrorType.EMPTY_RESULT_DATA) {
                    SuccesResponse(status = HttpStatus.OK, data = "Email kullanılabilir.")
                } else{
                    ErrorResponse(
                        "Bu email kullanılmaktadır.",
                        HttpStatus.NOT_ACCEPTABLE
                    )
                }
            }
            is BaseSealed.Succes -> SuccesResponse(status = HttpStatus.OK, message = null)
        }
    }

    fun register(userModel: UserModel): BaseResponse {
        val est = sqlRepo.setData(CreateTableSqlEnum.USER.getCreateSql())
        return if (est is BaseSealed.Succes) {
            val usernameResponse = usernameControl(userModel.username)
            if(usernameResponse is ErrorResponse){
                return usernameResponse
            }
            val emailResponse = emailControl(userModel.email)
            if(emailResponse is ErrorResponse){
                return emailResponse
            }
            val insertSql = CreateTableSqlEnum.USER.getDefaultInsertSql(
                userModel.username, userModel.email, userModel.gender,
                userModel.password, userModel.userType, userModel.token
            )
            val res = sqlRepo.setData(insertSql)
            if (res is BaseSealed.Succes) {
                userGameInfoSetData(userModel.username)
                SuccesResponse(status = HttpStatus.OK, message = "Kayıt başarılı.")
            } else {
                ErrorResponse("HATA.", HttpStatus.NOT_ACCEPTABLE)
            }
        } else {
            ErrorResponse("Tablo oluşturulurken bir hatayla karşılaşıldı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    private fun userGameInfoSetData(username: String): BaseResponse {
        val tableCreateSql = CreateTableSqlEnum.USER_GAME_INFO.getCreateSql()
        val result = sqlRepo.setData(tableCreateSql)
        return if (result is BaseSealed.Succes) {
            val tableInsertSql = CreateTableSqlEnum.USER_GAME_INFO.getDefaultInsertSql(username)
            val insertDataResult = sqlRepo.setData(tableInsertSql)
            if (insertDataResult is BaseSealed.Succes)
                return SuccesResponse(status = HttpStatus.OK, message = null)
            else ErrorResponse(
                "Tablo oluşturulurken bir hatayla karşılaşıldı", HttpStatus.NOT_ACCEPTABLE
            )
        } else ErrorResponse(
            "Tablo oluşturulurken bir hatayla karşılaşıldı", HttpStatus.NOT_ACCEPTABLE
        )
    }

}