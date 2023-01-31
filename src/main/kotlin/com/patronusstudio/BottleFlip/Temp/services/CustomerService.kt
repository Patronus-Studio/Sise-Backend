package com.patronusstudio.BottleFlip.Temp.services

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.Temp.models.CustomerRequestModel
import com.patronusstudio.BottleFlip.Temp.models.toLocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CustomerService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    @Autowired
    private lateinit var airportService: AirportService

    @Autowired
    private lateinit var districtService: DistrictService

    fun getAllCustomers(): BaseResponse {
        val sql = "Select * From pk_customer"
        return try {
            val result = sqlRepo.getList(sql)
            result.forEach {
                if (it.reservationCreatedTime != null)
                    it.reservationCreatedTime =
                        it.reservationCreatedTime!!.dropLast(2).toLocalDateTime().plusHours(3).toString()
                if (it.boardingDate != null)
                    it.boardingDate = it.boardingDate!!.dropLast(2).toLocalDateTime().plusHours(3).toString()
                it.whichAirport = airportService.getAirportName(it.whichAirport.toString())
                it.whichDistrinct = districtService.getDistrictName(it.whichDistrinct.toString())
            }
            SuccesResponse(data = result, status = HttpStatus.OK, message = null)
        } catch (e: Exception) {
            ErrorResponse(status = HttpStatus.NOT_ACCEPTABLE, message = "Sorguda bir hata oluştu")
        }
    }

    fun getAllActiveCustomers(): BaseResponse{
        val sql = "Select * From pk_customer where status = 1"
        return try {
            val result = sqlRepo.getList(sql)
            result.forEach {
                if (it.reservationCreatedTime != null)
                    it.reservationCreatedTime =
                        it.reservationCreatedTime!!.dropLast(2).toLocalDateTime().plusHours(3).toString()
                if (it.boardingDate != null)
                    it.boardingDate = it.boardingDate!!.dropLast(2).toLocalDateTime().plusHours(3).toString()
                it.whichAirport = airportService.getAirportName(it.whichAirport.toString())
                it.whichDistrinct = districtService.getDistrictName(it.whichDistrinct.toString())
            }
            SuccesResponse(data = result, status = HttpStatus.OK, message = null)
        } catch (e: Exception) {
            ErrorResponse(status = HttpStatus.NOT_ACCEPTABLE, message = "Sorguda bir hata oluştu")
        }
    }

    fun getAllDeactiveCustomers(): BaseResponse{
        val sql = "Select * From pk_customer where status = 0"
        return try {
            val result = sqlRepo.getList(sql)
            result.forEach {
                if (it.reservationCreatedTime != null)
                    it.reservationCreatedTime =
                        it.reservationCreatedTime!!.dropLast(2).toLocalDateTime().plusHours(3).toString()
                if (it.boardingDate != null)
                    it.boardingDate = it.boardingDate!!.dropLast(2).toLocalDateTime().plusHours(3).toString()
                it.whichAirport = airportService.getAirportName(it.whichAirport.toString())
                it.whichDistrinct = districtService.getDistrictName(it.whichDistrinct.toString())
            }
            SuccesResponse(data = result, status = HttpStatus.OK, message = null)
        } catch (e: Exception) {
            ErrorResponse(status = HttpStatus.NOT_ACCEPTABLE, message = "Sorguda bir hata oluştu")
        }
    }

    fun addNewCustomer(customerRequestModel: CustomerRequestModel):BaseSealed{
        val sql = "Insert Into pk_customer(boardingDate,email,flightNumber,nameSurname,numberOfChildSeats," +
                "numberOfCustomer,numberOfSuitcases,phoneNumber,startDestination,whichAirport,whichDistrinct) VALUES(" +
                "\"${customerRequestModel.boardingDate}\",\"${customerRequestModel.email}\",\"${customerRequestModel.flightNumber}\"," +
                "\"${customerRequestModel.nameSurname}\",\"${customerRequestModel.numberOfChildSeats}\",\"${customerRequestModel.numberOfCustomer}\"," +
                "\"${customerRequestModel.numberOfSuitcases}\",\"${customerRequestModel.phoneNumber}\",\"${customerRequestModel.startDestination}\"," +
                "\"${customerRequestModel.whichAirport}\",\"${customerRequestModel.whichDistrinct}\")"
        return sqlRepo.setData(sql)
    }

}