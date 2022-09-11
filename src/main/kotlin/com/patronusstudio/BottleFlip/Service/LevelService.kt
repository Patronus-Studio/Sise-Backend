package com.patronusstudio.BottleFlip.Service

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.LevelsModel
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class LevelService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun getAllLevel():BaseResponse{
        val getLevelsSql = "Select * from levels order by level asc"
        val getLevelsList = sqlRepo.getDataForList(getLevelsSql,LevelsModel::class.java)
        if(getLevelsList is BaseSealed.Error)
            return ErrorResponse("Leveller getirilirken bir hata oluştu.", HttpStatus.NOT_ACCEPTABLE)
        return SuccesResponse(status = HttpStatus.OK, data = (getLevelsList as BaseSealed.Succes).data)
    }
}