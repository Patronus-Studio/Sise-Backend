package com.patronusstudio.BottleFlip.Service

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.BottleModel
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class BottleService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun insertBottle(model: BottleModel): BaseResponse {
        val insertSql = "INSERT INTO bottles(name,description,luckRatio,bottleType,imageUrl,numberOfDownload," +
                "numberOfLike,numberOfUnlike,version) VALUES(\"${model.name}\",\"${model.description}\",\"${model.luckRatio}\"," +
                "\"${model.bottleType}\",\"${model.imageUrl}\",0,0,0,1)"
        val insertResult = sqlRepo.setData(insertSql)
        return if (insertResult is BaseSealed.Succes) {
            SuccesResponse("Şişe eklendi", HttpStatus.OK)
        } else {
            insertResult as BaseSealed.Error
            ErrorResponse(insertResult.data.toString(), HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getBottleByUsername(username: String): BaseResponse {
        val sql = "SELECT * FROM bottles where username = \"$username\""
        val userBottles = sqlRepo.getDataForList<BottleModel>(sql,BottleModel::class.java)
        return if (userBottles is BaseSealed.Succes) {
            SuccesResponse(data = userBottles.data, status = HttpStatus.OK, message = null)
        } else {
            userBottles as BaseSealed.Error
            ErrorResponse("Bu kullanıcının oluşturduğu şişe bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getBottleByBottleName(bottleName: String): BaseResponse {
        val sql = "SELECT * FROM bottles where name = \"$bottleName\""
        val userBottles = sqlRepo.getDataForList<BottleModel>(sql,BottleModel::class.java)
        return if (userBottles is BaseSealed.Succes) {
            SuccesResponse(data = userBottles.data, status = HttpStatus.OK, message = null)
        } else {
            userBottles as BaseSealed.Error
            ErrorResponse("Şişe bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getBottleFromMostLike():BaseResponse{
        val sql = "Select * from bottles order by numberOfLike desc"
        val result = sqlRepo.getDataForList<BottleModel>(sql,BottleModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getBottleFromMostDownload():BaseResponse{
        val sql = "Select * from bottles order by numberOfDownload desc"
        val result = sqlRepo.getDataForList<BottleModel>(sql,BottleModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }
}