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
        val result = sqlRepo.setDataWithReturnPrimaryKey(sql)
        if (result is BaseSealed.Succes) {
            sendEmailInformation(customerRequestModel, result.data as Long)
            result.data = HttpStatus.OK
        } else (result as BaseSealed.Error).sqlErrorType = SqlErrorType.NOT_ACCEPTABLE
        return result
    }

    fun sendEmailInformation(customerRequestModel: CustomerRequestModel, reservationId: Long) {
        val districtName = districtService.getDistrictName(customerRequestModel.whichDistrinct)
        val airportName = airportService.getAirportName(customerRequestModel.whichAirport)
        val routeName = if (customerRequestModel.startDestination == "0") {
            "From:$districtName <br>To:$airportName"
        } else "From:$airportName <br>To:$districtName"
        val messageList = mutableListOf<String>()
        messageList.add(routeName)
        messageList.add("Your Entried Phone: ${customerRequestModel.phoneNumber}")
        if (customerRequestModel.numberOfCustomer != "0") messageList.add("Customer: ${customerRequestModel.numberOfCustomer}")
        if (customerRequestModel.numberOfSuitcases != "0") messageList.add("Suitcase: ${customerRequestModel.numberOfSuitcases}")
        if (customerRequestModel.numberOfChildSeats != "0") messageList.add("Child Seats: ${customerRequestModel.numberOfChildSeats}")
        val title = "You’re booked! Pack your bags – see you on ${customerRequestModel.boardingDate}"
        var message =
            "Hi ${customerRequestModel.nameSurname},<br>" +
                    "It’s confirmed, we’ll see you on ${customerRequestModel.boardingDate}! Thank you for booking with us." +
                    "You’ll find details of your reservation details enclosed below. We’ll contact you soon via whatsapp.<br>" +
                    "<br>"
        messageList.forEach {
            message += "<br>$it"
        }
        val username = customerRequestModel.nameSurname.replace(" ","%20")
        message += "<br><br>If you need to get in touch <a href=https://wa.me/905537645868>Contact US</a>. We look forward to welcoming you!<br>" +
                "<br>" +
                "Thanks again.<br><br>" +
                "<a href=https://wa.me/905537645868?text=Hello,%20I'm%20$username%20and%20I%20will%20take%20a%20" +
                "information%20for%20this%20reservation%20id:$reservationId>" +
                "<img src=https://scontent.fist4-1.fna.fbcdn.net/v/t39.8562-6/302524815_3448899778679909_2843186333341006023_" +
                "n.png?_nc_cat=104&ccb=1-7&_nc_sid=6825c5&_nc_ohc=FVbrY5Y40GkAX89olMD&_nc_ht=scontent.fist4-1.fna&oh=00_AfBQts" +
                "nX0burv1-0suK6pCbnYOKn_m_A57D1gHjSjVo7VA&oe=6400C48F alt=Air Transfer Turkey! width=180 height=40/><br><br>" +
                "</a>" +
                "<img src=https://firebasestorage.googleapis.com/v0/b/sise-cevirmece-2-0.appspot.com/o/att.png?alt=media alt=Air Transfer Turkey! " +
                "width=200 height=125/><br>"
        val emailDetails = EmailDetails().apply {
            this.recipient = customerRequestModel.email
            this.msgBody = message
            this.subject = title
        }
        emailService.sendSimpleMail(emailDetails)
    }
}