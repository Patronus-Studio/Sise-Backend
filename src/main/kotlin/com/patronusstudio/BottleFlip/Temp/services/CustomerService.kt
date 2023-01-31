package com.patronusstudio.BottleFlip.Temp.services

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.Temp.models.toLocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CustomerService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun getAllCustomers(): BaseResponse {
        val sql = "Select * From pk_customer"
        return try {
            val result = sqlRepo.getList(sql)
            result.forEach {
                if (it.reservation_create_time != null)
                    it.reservation_create_time =
                        it.reservation_create_time!!.dropLast(2).toLocalDateTime().plusHours(3).toString()
                if (it.boarding_date != null)
                    it.boarding_date = it.boarding_date!!.dropLast(2).toLocalDateTime().plusHours(3).toString()
            }
            SuccesResponse(data = result, status = HttpStatus.OK, message = null)
        }catch (e:Exception){
            ErrorResponse(status = HttpStatus.NOT_ACCEPTABLE, message = "Sorguda bir hata oluştu")
        }
    }
}