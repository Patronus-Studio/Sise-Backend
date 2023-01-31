package com.patronusstudio.BottleFlip.Temp.services

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.Temp.models.AirportModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class AirportService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun getAirports(): BaseResponse {
        val sql = "Select * From pk_airport"
        val result = sqlRepo.getDataForList(sql, AirportModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getAirportName(id:String):String{
        val sql = "Select airport_name from pk_airport where airport_id =$id"
        return (sqlRepo.getBasicData(sql) as BaseSealed.Succes).data as String
    }

}