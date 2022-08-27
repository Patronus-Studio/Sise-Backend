package com.patronusstudio.BottleFlip.Service

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.BottleModel
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.PackageModel
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.enums.CreateTableSqlEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class BottleService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun insertBottle(model: BottleModel): BaseResponse {
        val sql = CreateTableSqlEnum.BOTTLES.getCreateSql()
        val bottleTable = sqlRepo.setData(sql)
        return if (bottleTable is BaseSealed.Succes) {
            val insertSql = CreateTableSqlEnum.BOTTLES.getDefaultInsertSql(
                model.username ?: "", model.name ?: "", model.description ?: "", model.luckRatio.toString(),
                model.bottleType.toString(), model.imageUrl ?: ""
            )
            val insertResult = sqlRepo.setData(insertSql)
            if (insertResult is BaseSealed.Succes) {
                SuccesResponse("Şişe eklendi", HttpStatus.OK)
            } else {
                insertResult as BaseSealed.Error
                ErrorResponse(insertResult.data.toString(), HttpStatus.NOT_ACCEPTABLE)
            }
        } else {
            ErrorResponse("Db oluşturulurken bir hata oluştur", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getBottleByUsername(username: String): BaseResponse {
        val sql = "SELECT * FROM bottles where username = \"$username\""
        val userBottles = sqlRepo.getDataForList(sql, BottleModel())
        return if (userBottles is BaseSealed.Succes) {
            SuccesResponse(data = userBottles.data, status = HttpStatus.OK, message = null)
        } else {
            userBottles as BaseSealed.Error
            ErrorResponse("Bu kullanıcının oluşturduğu şişe bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getBottleByBottleName(bottleName: String): BaseResponse {
        val sql = "SELECT * FROM bottles where name = \"$bottleName\""
        val userBottles = sqlRepo.getDataForList(sql, BottleModel())
        return if (userBottles is BaseSealed.Succes) {
            SuccesResponse(data = userBottles.data, status = HttpStatus.OK, message = null)
        } else {
            userBottles as BaseSealed.Error
            ErrorResponse("Şişe bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getBottleFromMostLike():BaseResponse{
        val sql = "Select * from bottles order by numberOfLike desc"
        val result = sqlRepo.getDataForList(sql, PackageModel())
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getBottleFromMostDownload():BaseResponse{
        val sql = "Select * from bottles order by numberOfDownload desc"
        val result = sqlRepo.getDataForList(sql, PackageModel())
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }
}