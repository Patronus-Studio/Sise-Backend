package com.patronusstudio.BottleFlip.Temp.services

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.Temp.models.CarTypeResponseModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CarTypeService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun getCarTypes(): BaseResponse {
        val sql = "Select * From pk_car_type order by id asc"
        val result = sqlRepo.getDataForListWithReturnList(sql, CarTypeResponseModel::class)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Araç türleri getirilemedi", HttpStatus.NOT_ACCEPTABLE)
        }
    }



}