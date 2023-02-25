package com.patronusstudio.BottleFlip.Temp.services

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Model.ErrorResponse
import com.patronusstudio.BottleFlip.Model.SuccesResponse
import com.patronusstudio.BottleFlip.Repository.SqlRepo
import com.patronusstudio.BottleFlip.Temp.emails.EmailDetails
import com.patronusstudio.BottleFlip.Temp.emails.EmailService
import com.patronusstudio.BottleFlip.Temp.models.CustomerRequestModel
import com.patronusstudio.BottleFlip.Temp.models.toLocalDateTime
import com.patronusstudio.BottleFlip.enums.SqlErrorType
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

    @Autowired
    private lateinit var emailService: EmailService

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

    fun getAllActiveCustomers(): BaseResponse {
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

    fun getAllDeactiveCustomers(): BaseResponse {
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

    fun addNewCustomer(customerRequestModel: CustomerRequestModel): BaseSealed {
        val sql = "Insert Into pk_customer(boardingDate,email,flightNumber,nameSurname,numberOfChildSeats," +
                "numberOfCustomer,numberOfSuitcases,phoneNumber,startDestination,whichAirport,whichDistrinct,carType) VALUES(" +
                "\"${customerRequestModel.boardingDate}\",\"${customerRequestModel.email}\",\"${customerRequestModel.flightNumber}\"," +
                "\"${customerRequestModel.nameSurname}\",\"${customerRequestModel.numberOfChildSeats}\",\"${customerRequestModel.numberOfCustomer}\"," +
                "\"${customerRequestModel.numberOfSuitcases}\",\"${customerRequestModel.phoneNumber}\",\"${customerRequestModel.startDestination}\"," +
                "\"${customerRequestModel.whichAirport}\",\"${customerRequestModel.whichDistrinct}\",${customerRequestModel.carType})"
        val result = sqlRepo.setData(sql)
        if (result is BaseSealed.Succes) {
            sendEmailInformation(customerRequestModel)
            result.data = HttpStatus.OK
        } else (result as BaseSealed.Error).sqlErrorType = SqlErrorType.NOT_ACCEPTABLE
        return result
    }

    fun sendEmailInformation(customerRequestModel: CustomerRequestModel){
        val districtName = districtService.getDistrictName(customerRequestModel.whichDistrinct)
        val airportName = airportService.getAirportName(customerRequestModel.whichAirport)
        val routeName = if (customerRequestModel.startDestination == "0") {
            "From:$districtName To:$airportName"
        } else "From:$airportName To:$districtName"
        val title = "Reservation Info"
        val message =
            "Hello ${customerRequestModel.nameSurname},\n\nWe created your reservation where $routeName.Your flight " +
                    "flight number is:${customerRequestModel.flightNumber}.\n\nIf you have any problem, contact us.Thank you"
        val emailDetails = EmailDetails().apply {
            this.recipient = customerRequestModel.email
            this.msgBody = message
            this.subject = title
        }
        emailService.sendSimpleMail(emailDetails)
    }
}