package com.patronusstudio.BottleFlip.Service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.AvatarModel
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

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
            SuccesResponse(data = selectableAvatars, status = HttpStatus.OK)
        } else ErrorResponse(
            status = HttpStatus.NOT_ACCEPTABLE,
            message = "Avatarlar getirilirken hata oluştu"
        )
    }
}