package com.patronusstudio.BottleFlip.Service

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Model.UserGameInfo
import com.patronusstudio.BottleFlip.Model.UserModel
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserGameInfoService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun getUserGameInfo(authToken: String): BaseResponse {
        val getUserNameSql = "Select username,authToken from users where authToken = \"$authToken\""
        val usernameResult = sqlRepo.getDataForObject(getUserNameSql,UserModel::class.java)
        if(usernameResult is BaseSealed.Error){
            return  ErrorResponse("Kullanıcı adı bulunamadı.",HttpStatus.NOT_ACCEPTABLE)
        }
        val userModel = (usernameResult as BaseSealed.Succes).data as UserModel
        val sql = "Select * From userGameInfo Where username = \"${userModel.username}\""
        val result = sqlRepo.getDataForObject(sql, UserGameInfo::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Kullanıcı bilgisi bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun updateCurrentAvatar(username: String,currentAvatar:Int):BaseResponse{
        val sql = "Update userGameInfo SET currentAvatar= $currentAvatar Where username = \"$username\""
        val result = sqlRepo.setData(sql)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Avatar değiştirilirken bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun updateBuyedAvatars(username: String,buyedAvatars:Int):BaseResponse{
        val sql = "Select * From userGameInfo Where username = \"$username\""
        val result = sqlRepo.getDataForObject(sql, UserGameInfo::class.java)
        return if (result is BaseSealed.Succes) {
            val userGameInfo = result.data as UserGameInfo
            if(userGameInfo.buyedAvatars.isNullOrBlank().not() && userGameInfo.buyedAvatars.toString() !="null"){
                val allAvatars = userGameInfo.buyedAvatars + buyedAvatars+";"
                val buyedAvatarsSql = "Update userGameInfo SET buyedAvatars= \"$allAvatars\" Where username = \"$username\""
                val buyedAvatarsSqlResult = sqlRepo.setData(buyedAvatarsSql)
                return if (buyedAvatarsSqlResult is BaseSealed.Succes) {
                    SuccesResponse(data = buyedAvatarsSqlResult.data, status = HttpStatus.OK, message = null)
                } else {
                    ErrorResponse("Satın alma aşamasında bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
                }
            }
            else{
                val buyedAvatarsSql = "Update userGameInfo SET buyedAvatars= \"$buyedAvatars;\" Where username = \"$username\""
                val buyedAvatarsSqlResult = sqlRepo.setData(buyedAvatarsSql)
                return if (buyedAvatarsSqlResult is BaseSealed.Succes) {
                    SuccesResponse(data = buyedAvatarsSqlResult.data, status = HttpStatus.OK, message = null)
                } else {
                    ErrorResponse("Satın alma aşamasında bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
                }
            }
        } else {
            ErrorResponse("Satın alma aşamasında bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun updateMyPackages(username: String,packageId:Int):BaseResponse{
        val sql = "Select * From userGameInfo Where username = \"$username\""
        val result = sqlRepo.getDataForObject(sql, UserGameInfo::class.java)
        return if (result is BaseSealed.Succes) {
            val userGameInfo = result.data as UserGameInfo
            if(userGameInfo.myPackages.isNullOrBlank().not() && userGameInfo.myPackages.toString() !="null"){
                val allMyPackages = userGameInfo.myPackages + packageId+";"
                val myGettedPackagesSql = "Update userGameInfo SET myPackages= \"$allMyPackages\" Where username = \"$username\""
                val myGettedPackagesSqlResult = sqlRepo.setData(myGettedPackagesSql)
                return if (myGettedPackagesSqlResult is BaseSealed.Succes) {
                    SuccesResponse(data = myGettedPackagesSqlResult.data, status = HttpStatus.OK, message = null)
                } else {
                    ErrorResponse("Satın alma aşamasında bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
                }
            }
            else{
                val myGettedPackagesSql = "Update userGameInfo SET myPackages= \"$packageId;\" Where username = \"$username\""
                val myGettedPackagesSqlResult = sqlRepo.setData(myGettedPackagesSql)
                return if (myGettedPackagesSqlResult is BaseSealed.Succes) {
                    SuccesResponse(data = myGettedPackagesSqlResult.data, status = HttpStatus.OK, message = null)
                } else {
                    ErrorResponse("Satın alma aşamasında bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
                }
            }
        } else {
            ErrorResponse("Satın alma aşamasında bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
        }
    }
}