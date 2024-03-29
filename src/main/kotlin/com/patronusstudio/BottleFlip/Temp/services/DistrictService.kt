package com.patronusstudio.BottleFlip.Temp.services

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.Temp.models.DistrictModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class DistrictService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun getDistricts(): BaseResponse {
        val sql = "Select * From pk_distrinct"
        val result = sqlRepo.getDataForList(sql, DistrictModel::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Paket bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

    fun getDistrictName(id:String):String{
        val sql = "Select ilce_adi from pk_distrinct where ilce_id =$id"
        return (sqlRepo.getBasicData(sql) as BaseSealed.Succes).data as String
    }
}