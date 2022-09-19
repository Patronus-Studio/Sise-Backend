package com.patronusstudio.BottleFlip.Service

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Model.UserCommentModel
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.enums.SqlErrorType
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

    fun getUserCommentByLast(lastCommentSize:Int):BaseResponse{
        val sql = "Select * from userComment order by id desc LIMIT $lastCommentSize"
        val sqlResult = sqlRepo.getDataForList(sql,UserCommentModel::class.java)
        return if(sqlResult is BaseSealed.Error){
            if(sqlResult.sqlErrorType == SqlErrorType.EMPTY_RESULT_DATA){
                ErrorResponse("Son yorum listesi boş.",HttpStatus.NOT_ACCEPTABLE)
            }else{
                ErrorResponse("Son $lastCommentSize yorum getirilirken bir hata oluştu.",HttpStatus.NOT_ACCEPTABLE)
            }
        }
        else{
            val userComments = (sqlResult as BaseSealed.Succes).data
            SuccesResponse(data = userComments, status = HttpStatus.OK)
        }
    }

    fun getUserCommentByUsername(username:String):BaseResponse{
        val sql = "Select * from userComment where username = \"$username\" order by id desc"
        val sqlResult = sqlRepo.getDataForList(sql,UserCommentModel::class.java)
        return if(sqlResult is BaseSealed.Error){
            if(sqlResult.sqlErrorType == SqlErrorType.EMPTY_RESULT_DATA){
                ErrorResponse("Bu kullanıcıya ait yorum bulunamadı.",HttpStatus.NOT_ACCEPTABLE)
            }else{
                ErrorResponse("Kullanıcı adına göre yorumlar getirilirken bir hata oluştu.",HttpStatus.NOT_ACCEPTABLE)
            }
        }
        else{
            val userComments = (sqlResult as BaseSealed.Succes).data
            SuccesResponse(data = userComments, status = HttpStatus.OK)
        }
    }

    fun getUserCommentByStarCount(starCount:Int):BaseResponse{
        val sql = "Select * from userComment where starCount = \"$starCount\" order by id desc"
        val sqlResult = sqlRepo.getDataForList(sql,UserCommentModel::class.java)
        return if(sqlResult is BaseSealed.Error){
            if(sqlResult.sqlErrorType == SqlErrorType.EMPTY_RESULT_DATA){
                ErrorResponse("Bu yıldız sayısına uygun yorum bulunamadı.",HttpStatus.NOT_ACCEPTABLE)
            }else{
                ErrorResponse("Yıldıza göre kullanıcı yorumları getirilirken bir hata oluştu.",HttpStatus.NOT_ACCEPTABLE)
            }
        }
        else{
            val userComments = (sqlResult as BaseSealed.Succes).data
            SuccesResponse(data = userComments, status = HttpStatus.OK)
        }
    }

    fun getUserCommentsByDeviceType(deviceType:String):BaseResponse{
        val sql = "Select * from userComment where deviceType = \"$deviceType\" order by id desc"
        val sqlResult = sqlRepo.getDataForList(sql,UserCommentModel::class.java)
        return if(sqlResult is BaseSealed.Error){
            if(sqlResult.sqlErrorType == SqlErrorType.EMPTY_RESULT_DATA){
                ErrorResponse("Bu cihaz tipine uygun yorum bulunamadı.",HttpStatus.NOT_ACCEPTABLE)
            }else{
                ErrorResponse("Cihaz tipine göre kullanıcı yorumları getirilirken bir hata oluştu.",HttpStatus.NOT_ACCEPTABLE)
            }
        }
        else{
            val userComments = (sqlResult as BaseSealed.Succes).data
            SuccesResponse(data = userComments, status = HttpStatus.OK)
        }
    }

    fun getUserCommentsByAppVersion(appVersion:String):BaseResponse{
        val sql = "Select * from userComment where appVersion = \"$appVersion\" order by id desc"
        val sqlResult = sqlRepo.getDataForList(sql,UserCommentModel::class.java)
        return if(sqlResult is BaseSealed.Error){
            if(sqlResult.sqlErrorType == SqlErrorType.EMPTY_RESULT_DATA){
                ErrorResponse("Bu uygulama sürümüne uygun yorum bulunamadı.",HttpStatus.NOT_ACCEPTABLE)
            }else{
                ErrorResponse("Uygulama sürümüne göre kullanıcı yorumları getirilirken bir hata oluştu.",HttpStatus.NOT_ACCEPTABLE)
            }
        }
        else{
            val userComments = (sqlResult as BaseSealed.Succes).data
            SuccesResponse(data = userComments, status = HttpStatus.OK)
        }
    }
}