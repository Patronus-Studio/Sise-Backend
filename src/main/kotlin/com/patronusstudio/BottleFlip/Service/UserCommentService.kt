package com.patronusstudio.BottleFlip.Service

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Model.UserCommentModel
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class UserCommentService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun setNewUserReview(model:UserCommentModel):BaseResponse{
        val sql = "Insert into userComment(username,comment,starCount,sendDate,appVersion,deviceType,deviceModel) " +
                "VALUES(\"${model.username}\",\"${model.comment}\",${model.starCount},\"${Timestamp(System.currentTimeMillis())}\"," +
                "\"${model.appVersion}\",\"${model.deviceType}\",\"${model.deviceModel}\")"
        val sqlResult = sqlRepo.setData(sql)
        return if(sqlResult is BaseSealed.Error){
            ErrorResponse("Yorum gönderilirken bir hata oluştu.",HttpStatus.NOT_ACCEPTABLE)
        } else{
            SuccesResponse(data = "Yorum gönderildi.", status = HttpStatus.OK)
        }
    }
}