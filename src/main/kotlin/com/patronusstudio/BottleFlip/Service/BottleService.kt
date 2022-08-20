package com.patronusstudio.BottleFlip.Service

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.BottleModel
import com.patronusstudio.BottleFlip.Model.ErrorResponse
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

    fun insertBottle(model:BottleModel): BaseResponse {
        val sql = CreateTableSqlEnum.BOTTLES.getCreateSql()
        val bottleTable = sqlRepo.setData(sql)
        return if (bottleTable is BaseSealed.Succes) {
            val insertSql = CreateTableSqlEnum.BOTTLES.getDefaultInsertSql(
                model.username ?: "",model.name ?: "",model.description ?: "",model.luckRatio.toString(),
                model.bottleType.toString(),model.imageUrl ?: ""
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
}