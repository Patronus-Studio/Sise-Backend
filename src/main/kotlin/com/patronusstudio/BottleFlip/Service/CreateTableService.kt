package com.patronusstudio.BottleFlip.Service

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.enums.TableTypeEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CreateTableService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun createTablePackage(tableTypeEnum: TableTypeEnum):BaseResponse{
        val sql = when(tableTypeEnum){
            TableTypeEnum.USER -> TableTypeEnum.USER.getCreateSql()
            TableTypeEnum.USER_GAME_INFO -> TableTypeEnum.USER_GAME_INFO.getCreateSql()
            TableTypeEnum.LEVELS -> TableTypeEnum.LEVELS.getCreateSql()
            TableTypeEnum.PACKAGES -> TableTypeEnum.PACKAGES.getCreateSql()
            TableTypeEnum.BOTTLES -> TableTypeEnum.BOTTLES.getCreateSql()
            TableTypeEnum.ACHIEVEMENT -> TableTypeEnum.ACHIEVEMENT.getCreateSql()
            TableTypeEnum.PACKAGE_CATEGORIES_TYPE -> TableTypeEnum.PACKAGE_CATEGORIES_TYPE.getCreateSql()
            TableTypeEnum.USER_COMMENT -> TableTypeEnum.USER_COMMENT.getCreateSql()
            TableTypeEnum.AVATAR -> TableTypeEnum.AVATAR.getCreateSql()
        }
        val result = sqlRepo.setData(sql)
        return if(result is BaseSealed.Error){
            ErrorResponse(message = "${tableTypeEnum.name} tablosu oluşturulamadı",HttpStatus.NOT_ACCEPTABLE)
        }
        else{
            SuccesResponse(status = HttpStatus.OK, message = "Tablo oluşturuldu.")
        }
    }
}