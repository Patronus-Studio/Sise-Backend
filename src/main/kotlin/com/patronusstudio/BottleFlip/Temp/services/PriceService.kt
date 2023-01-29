package com.patronusstudio.BottleFlip.Temp.services

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.Temp.models.PriceModelRequest
import com.patronusstudio.BottleFlip.Temp.models.PriceModelResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class PriceService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun getPrice(model: PriceModelRequest): BaseResponse {
        val sql = "Select price From pk_price where airport_id = ${model.airport_id} and ilce_id = ${model.ilce_id}"
        val result = sqlRepo.getDataForObject(sql, PriceModelResponse::class.java)
        return if (result is BaseSealed.Succes) {
            SuccesResponse(data = result.data, status = HttpStatus.OK, message = null)
        } else {
            ErrorResponse("Mesafe ücreti bulunamadı", HttpStatus.NOT_ACCEPTABLE)
        }
    }

}