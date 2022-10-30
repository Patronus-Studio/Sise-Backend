package com.patronusstudio.BottleFlip.Service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.AvatarModel
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Model.UserGameInfo
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.enums.BuyedStatu
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam

@Service
class AvatarService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun getAllAvatar(username:String): BaseResponse {
        val customerAvatarSql = "Select buyedAvatars from userGameInfo where username = \"$username\""
        val buyedAvatarsResult = sqlRepo.getBasicData(customerAvatarSql)
        if(buyedAvatarsResult is BaseSealed.Error){
            ErrorResponse("Kullanıcı bilgileri getirilirken bir hata oluştu.",HttpStatus.NOT_ACCEPTABLE)
        }
        val sqlQuery = "Select * from avatars order by id asc"
        val sqlResult = sqlRepo.getDataForList(sqlQuery, AvatarModel::class.java)
        return if (sqlResult is BaseSealed.Succes) {
            val parsedList =
                Gson().fromJson<List<AvatarModel>>(Gson().toJson(sqlResult.data), object :
                    TypeToken<List<AvatarModel>>() {}.type)
            val selectableAvatars = parsedList.filter {
                it.isSelectable == 1
            }
            val avatars = ((buyedAvatarsResult as BaseSealed.Succes).data as String).split(";")
            selectableAvatars.forEach { selectableAvatar ->
                avatars.forEach {
                    if(selectableAvatar.id.toString() == it){
                        selectableAvatar.buyedStatu = BuyedStatu.BUYED
                    }
                }
                if(selectableAvatar.buyedStatu != BuyedStatu.BUYED){
                    selectableAvatar.buyedStatu = BuyedStatu.NON_BUYED
                }
            }
            SuccesResponse(data = selectableAvatars, status = HttpStatus.OK)
        } else ErrorResponse(
            status = HttpStatus.NOT_ACCEPTABLE,
            message = "Avatarlar getirilirken hata oluştu"
        )
    }

    fun insertAvatar(model: AvatarModel): BaseResponse {
        val insertSqlQuery = "Insert into avatars(imageUrl,isSelectable,starCount) " +
                "VALUES(\"${model.imageUrl}\",${model.isSelectable},${model.starCount})"
        val result = sqlRepo.setData(insertSqlQuery)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = "Yeni avatar eklendi.", status = HttpStatus.OK)
        } else ErrorResponse(
            "Avatar eklenirken bir hata oluştu.",
            status = HttpStatus.NOT_ACCEPTABLE
        )
    }

    fun buyAvatar(@RequestParam username: String, @RequestParam avatarId: Int): BaseResponse {
        val getUserInfoSql = "Select * from userGameInfo where username = \"$username\""
        val userInfo = sqlRepo.getDataForObject(getUserInfoSql, UserGameInfo::class.java)
        if (userInfo is BaseSealed.Error) {
            return ErrorResponse(
                "Kullanıcı bilgileri getirilirken bir hata oluştu.",
                HttpStatus.NOT_ACCEPTABLE
            )
        }
        val avatarsSql = "Select * from avatars where id = $avatarId"
        val avatar = sqlRepo.getDataForObject(avatarsSql, AvatarModel::class.java)
        if (avatar is BaseSealed.Error) {
            return ErrorResponse(
                "Avatarlar getirilirken bir hata oluştu.",
                HttpStatus.NOT_ACCEPTABLE
            )
        }
        val avatarModel = (avatar as BaseSealed.Succes).data as AvatarModel
        if (avatarModel.isSelectable == 0) {
            return ErrorResponse("Avatar şuan seçilemez.", HttpStatus.NOT_ACCEPTABLE)
        }
        val userInfoModel = (userInfo as BaseSealed.Succes).data as UserGameInfo
        if (avatarModel.starCount > (userInfoModel.starCount ?: 0)) {
            return ErrorResponse(
                "Avatarı almak için yıldız sayınız yetersiz. Biraz daha görev yaparak yıldız kazanın.",
                HttpStatus.NOT_ACCEPTABLE
            )
        }
        val isBeforeBuyed = userInfoModel.buyedAvatars?.split(";")?.any {
            it == avatarId.toString()
        }
        if (isBeforeBuyed == true) {
            return ErrorResponse(
                "Daha önce bu avatarı aldınız. Başka bir avatar almayı deneyin.",
                HttpStatus.NOT_ACCEPTABLE
            )
        }
        val buyedAvatar = if (userInfoModel.buyedAvatars == null) "$avatarId;"
        else userInfoModel.buyedAvatars + "$avatarId;"
        val updateUserBuyedAvatarSql =
            "Update userGameInfo SET buyedAvatars = \"$buyedAvatar\", starCount = ${userInfoModel.starCount!! - avatarModel.starCount}  Where username = \"$username\""
        val updateUserResult = sqlRepo.setData(updateUserBuyedAvatarSql)
        return if (updateUserResult is BaseSealed.Succes) {
            SuccesResponse(data = "Yeni avatarın süper oldu. Tebrikler", status = HttpStatus.OK)
        } else ErrorResponse(
            message = "Satın alma aşamasında bir hata oluştu.",
            status = HttpStatus.NOT_ACCEPTABLE
        )
    }

}